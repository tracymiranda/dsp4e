package org.eclipse.dsp4e.debugmodel;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.dsp4j.DebugProtocol.ContinuedEvent;
import org.eclipse.dsp4j.DebugProtocol.DisconnectArguments;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsResponse;
import org.eclipse.dsp4j.DebugProtocol.Source;
import org.eclipse.dsp4j.DebugProtocol.SourceBreakpoint;
import org.eclipse.dsp4j.DebugProtocol.StoppedEvent;
import org.eclipse.dsp4j.DebugProtocol.TerminatedEvent;
import org.eclipse.dsp4j.DebugProtocol.Thread;
import org.eclipse.dsp4j.IDebugProtocolClient;
import org.eclipse.dsp4j.IDebugProtocolServer;
import org.eclipse.dsp4j.jsonrpc.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

public class DSPDebugTarget extends DSPDebugElement
		implements IBreakpointManagerListener, IDebugTarget, IDebugProtocolClient {

	private ILaunch launch;
	private Process process;

	private boolean fTerminated = false;
	private boolean fSuspended = false;
	private String targetName = null;

	private Map<Integer, DSPThread> threads = new HashMap<>();

	private Future<?> debugProtocolFuture;
	IDebugProtocolServer debugProtocolServer;
	private Map<Source, List<SourceBreakpoint>> targetBreakpoints = new HashMap<>();

	public DSPDebugTarget(ILaunch launch, Process process, InputStream in, OutputStream out,
			Map<String, Object> launchArguments) throws CoreException {
		super(null);
		this.launch = launch;
		this.process = process;

		DebugLauncher<IDebugProtocolServer> debugProtocolLauncher = DebugLauncher.createLauncher(this,
				IDebugProtocolServer.class, in, out, true, new PrintWriter(System.out));

		debugProtocolFuture = debugProtocolLauncher.startListening();
		debugProtocolServer = debugProtocolLauncher.getRemoteProxy();

		complete(debugProtocolServer.initialize(new InitializeRequestArguments().setClientID("lsp4e")
				.setAdapterID((String) launchArguments.get("type")).setPathFormat("path")));

		Object object = launchArguments.get("program");
		targetName = Objects.toString(object, "Debug Adapter Target");

		complete(debugProtocolServer.launch(Either.forLeft(launchArguments)));
		complete(debugProtocolServer.configurationDone());
		IBreakpointManager breakpointManager = getBreakpointManager();
		breakpointManager.addBreakpointListener(this);
		breakpointManager.addBreakpointManagerListener(this);
		breakpointManagerEnablementChanged(breakpointManager.isEnabled());
	}

	/**
	 * Throws a debug exception with a status code of
	 * <code>TARGET_REQUEST_FAILED</code>.
	 *
	 * @param message
	 *            exception message
	 * @param e
	 *            underlying exception or <code>null</code>
	 * @throws DebugException
	 *             if a problem is encountered
	 */
	@Override
	protected void requestFailed(String message, Throwable e) throws DebugException {
		throw newTargetRequestFailedException(message, e);
	}

	@Override
	public DSPDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean canTerminate() {
		return true;
	}

	@Override
	public boolean isTerminated() {
		return fTerminated || (process != null && !process.isAlive());
	}

	@Override
	public void terminated(TerminatedEvent.Body body) {
		terminated();
	}

	@Override
	public void terminate() throws DebugException {
		complete(debugProtocolServer.disconnect(new DisconnectArguments().setTerminateDebuggee(true)));
		terminated();
	}

	private void terminated() {
		fTerminated = true;
		IBreakpointManager breakpointManager = getBreakpointManager();
		breakpointManager.removeBreakpointListener(this);
		breakpointManager.removeBreakpointManagerListener(this);
		fireTerminateEvent();
	}

	@Override
	public void continued(ContinuedEvent.Body body) {
		fSuspended = false;
		fireResumeEvent(DebugEvent.UNSPECIFIED);
	}

	@Override
	public void stopped(StoppedEvent.Body body) {
		fSuspended = true;
		DSPDebugElement source = null;
		if (body.threadId != null) {
			source = getThread(body.threadId);
		}
		if (source == null) {
			source = this;
		}
		fireEvent(new DebugEvent(source, DebugEvent.SUSPEND, calcDetail(body.reason)));
	}

	private int calcDetail(String reason) {
		if (reason.equals("breakpoint")) { //$NON-NLS-1$
			return DebugEvent.BREAKPOINT;
		} else if (reason.equals("step")) { //$NON-NLS-1$
			return DebugEvent.STEP_OVER;
			// } else if (reason.equals("exception")) { //$NON-NLS-1$
			// return DebugEvent.STEP_RETURN;
		} else if (reason.equals("pause")) { //$NON-NLS-1$
			return DebugEvent.CLIENT_REQUEST;
			// } else if (reason.equals("event")) { //$NON-NLS-1$
			// return DebugEvent.BREAKPOINT;
		} else {
			return DebugEvent.UNSPECIFIED;
		}
	}

	@Override
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	@Override
	public boolean canSuspend() {
		return !isTerminated() && !isSuspended();
	}

	@Override
	public boolean isSuspended() {
		return fSuspended;
	}

	@Override
	public void resume() throws DebugException {
	}

	@Override
	public void suspend() throws DebugException {
	}

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public void disconnect() throws DebugException {
	}

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	@Override
	public IProcess getProcess() {
		return null;
	}

	@Override
	public synchronized DSPThread[] getThreads() throws DebugException {
		// TODO use thread event in combination to keep track
		Thread[] body = complete(debugProtocolServer.threads()).threads;
		for (Thread thread : body) {
			DSPThread dspThread = threads.computeIfAbsent(thread.id, id -> new DSPThread(this, thread));
			dspThread.update(thread);
		}
		Collection<DSPThread> values = threads.values();
		return values.toArray(new DSPThread[values.size()]);
	}

	public DSPThread getThread(Integer threadId) {
		return threads.get(threadId);
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return true;
	}

	@Override
	public String getName() {
		return targetName;
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		if (!isTerminated() && breakpoint instanceof ILineBreakpoint) {
			return true;
		}
		return false;
	}

	/**
	 * When the breakpoint manager disables, remove all registered breakpoints
	 * requests from the VM. When it enables, reinstall them.
	 */
	@Override
	public void breakpointManagerEnablementChanged(boolean enabled) {
		try {
			IBreakpoint[] breakpoints = getBreakpointManager().getBreakpoints(getModelIdentifier());
			for (IBreakpoint breakpoint : breakpoints) {
				if (supportsBreakpoint(breakpoint)) {
					if (enabled) {
						addBreakpointToMap(breakpoint);
					} else {
						deleteBreakpointFromMap(breakpoint);
					}
				}
			}
			sendBreakpoints();
		} catch (CoreException e) {
			// TODO
			e.printStackTrace();
		}
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				if ((breakpoint.isEnabled() && getBreakpointManager().isEnabled()) || !breakpoint.isRegistered()) {
					addBreakpointToMap(breakpoint);
					sendBreakpoints();
				}
			} catch (CoreException e) {
				// TODO
				e.printStackTrace();
			}
		}
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				deleteBreakpointFromMap(breakpoint);
				sendBreakpoints();
			} catch (CoreException e) {
				// TODO
				e.printStackTrace();
			}
		}
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				if (breakpoint.isEnabled() && getBreakpointManager().isEnabled()) {
					breakpointAdded(breakpoint);
				} else {
					breakpointRemoved(breakpoint, null);
				}
			} catch (CoreException e) {
			}
		}
	}

	private void addBreakpointToMap(IBreakpoint breakpoint) throws CoreException {
		Assert.isTrue(supportsBreakpoint(breakpoint) && breakpoint instanceof ILineBreakpoint);
		if (breakpoint instanceof ILineBreakpoint) {
			ILineBreakpoint lineBreakpoint = (ILineBreakpoint) breakpoint;
			IResource resource = lineBreakpoint.getMarker().getResource();
			IPath location = resource.getLocation();
			String path = location.toOSString();
			String name = location.lastSegment();
			int lineNumber = lineBreakpoint.getLineNumber();

			Source source = new Source().setName(name).setPath(path);

			List<SourceBreakpoint> sourceBreakpoints = targetBreakpoints.computeIfAbsent(source,
					s -> new ArrayList<>());
			sourceBreakpoints.add(new SourceBreakpoint().setLine(lineNumber));
		}
	}

	private void deleteBreakpointFromMap(IBreakpoint breakpoint) throws CoreException {
		Assert.isTrue(supportsBreakpoint(breakpoint) && breakpoint instanceof ILineBreakpoint);
		if (breakpoint instanceof ILineBreakpoint) {
			ILineBreakpoint lineBreakpoint = (ILineBreakpoint) breakpoint;
			IResource resource = lineBreakpoint.getMarker().getResource();
			IPath location = resource.getLocation();
			String path = location.toOSString();
			String name = location.lastSegment();
			int lineNumber = lineBreakpoint.getLineNumber();
			for (Entry<Source, List<SourceBreakpoint>> entry : targetBreakpoints.entrySet()) {
				Source source = entry.getKey();
				if (Objects.equals(name, source.name) && Objects.equals(path, source.path)) {
					List<SourceBreakpoint> bps = entry.getValue();
					for (Iterator<SourceBreakpoint> iterator = bps.iterator(); iterator.hasNext();) {
						SourceBreakpoint sourceBreakpoint = (SourceBreakpoint) iterator.next();
						if (Objects.equals(lineNumber, sourceBreakpoint.line)) {
							iterator.remove();
						}
					}
				}
			}
		}
	}

	private void deleteAllBreakpointsFromMap() {
		for (Entry<Source, List<SourceBreakpoint>> entry : targetBreakpoints.entrySet()) {
			entry.getValue().clear();
		}
	}

	private void sendBreakpoints() throws DebugException {
		for (Iterator<Entry<Source, List<SourceBreakpoint>>> iterator = targetBreakpoints.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<Source, List<SourceBreakpoint>> entry = iterator.next();

			Source source = entry.getKey();
			List<SourceBreakpoint> bps = entry.getValue();
			Integer[] lines = bps.stream().map(sb -> sb.line).toArray(Integer[]::new);
			SourceBreakpoint[] sourceBps = bps.toArray(new SourceBreakpoint[bps.size()]);

			CompletableFuture<SetBreakpointsResponse.Body> future = debugProtocolServer
					.setBreakpoints(new SetBreakpointsArguments().setSource(source).setLines(lines)
							.setBreakpoints(sourceBps).setSourceModified(false));
			// TODO handle install info about breakpoint
			complete(future);

			// Once we told adapter there are no breakpoints for a source file, we can stop
			// tracking that file
			if (bps.isEmpty()) {
				iterator.remove();
			}
		}
	}
}
