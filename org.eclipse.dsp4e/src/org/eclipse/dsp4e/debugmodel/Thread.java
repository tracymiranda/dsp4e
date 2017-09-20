package org.eclipse.dsp4e.debugmodel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.dsp4j.DebugProtocol.ContinueArguments;
import org.eclipse.dsp4j.DebugProtocol.NextArguments;
import org.eclipse.dsp4j.DebugProtocol.StackTraceArguments;
import org.eclipse.dsp4j.DebugProtocol.ThreadsResponse.Body;

final class Thread implements IThread {
	/**
	 *
	 */
	private final ReadmeDebugTarget debugTarget;
	private final Body body;
	private final IThread[] threads;
	private final int i;

	public Thread(ReadmeDebugTarget readmeDebugTarget, Body body, IThread[] threads, int i) {
		debugTarget = readmeDebugTarget;
		this.body = body;
		this.threads = threads;
		this.i = i;
	}

	@Override
	public void terminate() throws DebugException {
		debugTarget.terminate();
	}

	@Override
	public boolean isTerminated() {
		return debugTarget.isTerminated();
	}

	@Override
	public boolean canTerminate() {
		return debugTarget.canTerminate();
	}

	@Override
	public void stepReturn() throws DebugException {
	}

	@Override
	public void stepOver() throws DebugException {
		ReadmeDebugTarget
				.getAndPrint(debugTarget.debugProtocolServer.next(new NextArguments().setThreadId(body.threads[i].id)));
		debugTarget.fireResumeEvent(0);
		debugTarget.fireSuspendEvent(0);
	}

	@Override
	public void stepInto() throws DebugException {
	}

	@Override
	public boolean isStepping() {
		return false;
	}

	@Override
	public boolean canStepReturn() {
		return false;
	}

	@Override
	public boolean canStepOver() {
		return true;
	}

	@Override
	public boolean canStepInto() {
		return false;
	}

	@Override
	public void suspend() throws DebugException {
	}

	@Override
	public void resume() throws DebugException {
		ReadmeDebugTarget.getAndPrint(
				debugTarget.debugProtocolServer.continue_(new ContinueArguments().setThreadId(body.threads[i].id)));
		debugTarget.fireResumeEvent(0);
	}

	@Override
	public boolean isSuspended() {
		return false;
	}

	@Override
	public boolean canSuspend() {
		return false;
	}

	@Override
	public boolean canResume() {
		return true;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public String getModelIdentifier() {
		return null;
	}

	@Override
	public ILaunch getLaunch() {
		return null;
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return debugTarget;
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return true;
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		return null;
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		CompletableFuture<org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body> future = debugTarget.debugProtocolServer
				.stackTrace(new StackTraceArguments().setThreadId(1).setStartFrame(0).setLevels(20));
		org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body body2;
		try {
			body2 = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw debugTarget.newTargetRequestFailedException("Can't get frames", e);
		}
		IStackFrame[] frames = new IStackFrame[body2.stackFrames.length];
		for (int iv = 0; iv < frames.length; iv++) {
			final int f = iv;
			frames[f] = new StackFrame(debugTarget, body2, i, f, threads);
		}

		return frames;

	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public String getName() throws DebugException {
		return body.threads[i].name;
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return null;
	}
}