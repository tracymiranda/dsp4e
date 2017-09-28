package org.eclipse.dsp4e.debugmodel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.debug.core.DebugEvent;
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

public class Thread implements IThread {
	private final DebugTarget debugTarget;
	private final org.eclipse.dsp4j.DebugProtocol.Thread thread;

	public Thread(DebugTarget debugTarget, org.eclipse.dsp4j.DebugProtocol.Thread thread) {
		this.debugTarget = debugTarget;
		this.thread = thread;
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
		DebugTarget.getAndPrint(debugTarget.debugProtocolServer.next(new NextArguments().setThreadId(thread.id)));
		debugTarget.fireResumeEvent(DebugEvent.STEP_OVER);
		debugTarget.fireSuspendEvent(DebugEvent.STEP_OVER);
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
		DebugTarget
				.getAndPrint(debugTarget.debugProtocolServer.continue_(new ContinueArguments().setThreadId(thread.id)));
		debugTarget.fireResumeEvent(0);
	}

	@Override
	public boolean isSuspended() {
		return getDebugTarget().isSuspended();
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
		return debugTarget.getModelIdentifier();
	}

	@Override
	public ILaunch getLaunch() {
		return debugTarget.getLaunch();
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
		return getStackFrames()[0];
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		CompletableFuture<org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body> future = debugTarget.debugProtocolServer
				.stackTrace(new StackTraceArguments().setThreadId(1).setStartFrame(0).setLevels(20));
		org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body stackTraceResposeBody;
		try {
			stackTraceResposeBody = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw debugTarget.newTargetRequestFailedException("Can't get frames", e);
		}
		IStackFrame[] frames = new IStackFrame[stackTraceResposeBody.stackFrames.length];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new StackFrame(this, stackTraceResposeBody.stackFrames[i]);
		}

		return frames;
	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public String getName() throws DebugException {
		return thread.name;
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return new IBreakpoint[0];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((debugTarget == null) ? 0 : debugTarget.hashCode());
		result = prime * result + ((thread == null) ? 0 : thread.hashCode());
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
		Thread other = (Thread) obj;
		if (debugTarget == null) {
			if (other.debugTarget != null)
				return false;
		} else if (!debugTarget.equals(other.debugTarget))
			return false;
		if (thread == null) {
			if (other.thread != null)
				return false;
		} else if (!thread.equals(other.thread))
			return false;
		return true;
	}
	
	
}