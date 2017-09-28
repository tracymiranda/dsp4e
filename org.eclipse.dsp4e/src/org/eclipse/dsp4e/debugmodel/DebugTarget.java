package org.eclipse.dsp4e.debugmodel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.dsp4j.DebugProtocol.ContinuedEvent;
import org.eclipse.dsp4j.DebugProtocol.DisconnectArguments;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.StoppedEvent;
import org.eclipse.dsp4j.DebugProtocol.TerminatedEvent;
import org.eclipse.dsp4j.DebugProtocol.ThreadsResponse.Body;
import org.eclipse.dsp4j.DebugProtocol.VariablesArguments;
import org.eclipse.dsp4j.IDebugProtocolClient;
import org.eclipse.dsp4j.IDebugProtocolServer;
import org.eclipse.lsp4j.jsonrpc.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

public class DebugTarget extends DSPDebugElement implements IDebugTarget, IDebugProtocolClient {
	private static AtomicInteger count = new AtomicInteger();
	private int id = count.getAndIncrement();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DebugTarget other = (DebugTarget) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private ILaunch launch;
	private Process process;

	private boolean fTerminated = false;
	private boolean fSuspended = false;
	private String targetName = null;

	private IThread[] threads;

	private Future<?> debugProtocolFuture;
	IDebugProtocolServer debugProtocolServer;

	public DebugTarget(ILaunch launch, Process process, InputStream in, OutputStream out,
			Map<String, Object> launchArguments) throws CoreException {
		super(null);
		this.launch = launch;
		this.process = process;

		DebugLauncher<IDebugProtocolServer> debugProtocolLauncher = DebugLauncher.createLauncher(this,
				IDebugProtocolServer.class, in, out);

		debugProtocolFuture = debugProtocolLauncher.startListening();
		debugProtocolServer = debugProtocolLauncher.getRemoteProxy();

		getAndPrint(debugProtocolServer.initialize(new InitializeRequestArguments().setClientID("lsp4e")
				.setAdapterID((String) launchArguments.get("type")).setPathFormat("path")));

		// setBreakpoints({"source":{"path":"/scratch/debug/examples/mockdebug/readme.md","name":"readme.md"},"lines":[2],"breakpoints":[{"line":2}],"sourceModified":false})
		// getAndPrint(debugProtocolServer.setBreakpoints(new SetBreakpointsArguments()
		// .setSource(new
		// Source().setPath("/scratch/debug/examples/mockdebug/readme.md").setName("readme.md"))
		// .setLines(new Integer[] { 3 })
		// .setBreakpoints(
		// new DebugProtocol.SourceBreakpoint[] { new
		// DebugProtocol.SourceBreakpoint().setLine(3) })
		// .setSourceModified(false)));
		Object object = launchArguments.get("program");
		targetName = Objects.toString(object, "Debug Adapter Target");

		getAndPrint(debugProtocolServer.launch(Either.forLeft(launchArguments)));
		getAndPrint(debugProtocolServer.configurationDone());
		getAndPrint(debugProtocolServer.variables(new VariablesArguments().setVariablesReference(1001)));

	}

	/**
	 * Gets the response from the debug command and prints some debug info
	 *
	 * @param future
	 */
	static void getAndPrint(CompletableFuture<?> future) {
		try {
			System.out.println(future.get());
			System.out.println("Printed future.get");

		} catch (InterruptedException | ExecutionException e) {
			String message = e.getMessage();
			String lines[] = message.split("\\r\\n");
			System.err.println(lines[0]);
		}
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

	DebugException newTargetRequestFailedException(String message, Throwable e) {
		return new DebugException(new Status(IStatus.ERROR, DebugPlugin.getUniqueIdentifier(),
				DebugException.TARGET_REQUEST_FAILED, message, e));
	}

	@Override
	public IDebugTarget getDebugTarget() {
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
	/*
	 * 
	 */
	public void terminated(TerminatedEvent.Body body) {
		fTerminated = true;
		fireTerminateEvent();
	}

	@Override
	public void terminate() throws DebugException {
		getAndPrint(debugProtocolServer.disconnect(new DisconnectArguments().setTerminateDebuggee(true)));
		fTerminated = true;
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
		fireSuspendEvent(calcDetail(body.reason));
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
	public void breakpointAdded(IBreakpoint breakpoint) {
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
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
	public IThread[] getThreads() throws DebugException {
		CompletableFuture<Body> threadsFuture = debugProtocolServer.threads();
		Body body;
		try {
			body = threadsFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			throw newTargetRequestFailedException("Can't get threads", e);
		}
		threads = new IThread[body.threads.length];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(this, body.threads[i]);
		}
		return threads;
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
		return false;
	}

}
