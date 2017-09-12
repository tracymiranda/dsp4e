package org.eclipse.dsp4e.example.readme.debugmodel;

import java.util.concurrent.CompletableFuture;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.dsp4j.DebugProtocol.StackTraceArguments;
import org.eclipse.dsp4j.DebugProtocol.ThreadsResponse.Body;
import org.eclipse.dsp4j.IDebugProtocolServer;

final class StackFrame implements IStackFrame {
	/**
	 *
	 */
	private final ReadmeDebugTarget debugTarget;
	private final org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body body2;
	private final int i;
	private final int f;
	private final IThread[] threads;

	public StackFrame(ReadmeDebugTarget readmeDebugTarget,
			org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body body2, int i, int f, IThread[] threads) {
		debugTarget = readmeDebugTarget;
		this.body2 = body2;
		this.i = i;
		this.f = f;
		this.threads = threads;
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
	public void suspend() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSuspend() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canResume() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stepReturn() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepOver() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepInto() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStepping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepReturn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepInto() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModelIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILaunch getLaunch() {
		// TODO Auto-generated method stub
		return debugTarget.getLaunch();
	}

	@Override
	public IDebugTarget getDebugTarget() {
		// TODO Auto-generated method stub
		return debugTarget;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		// TODO Auto-generated method stub
		IVariable[] myArr = new IVariable[2];
		DebugVariable debugVariable = new DebugVariable("Art");
		DebugVariable debugVariable2 = new DebugVariable("KC");
		myArr[1] = debugVariable2;
		myArr[0] = debugVariable;
		return myArr;
	}

	@Override
	public IThread getThread() {
		// TODO Auto-generated method stub
		return threads[i];
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() throws DebugException {
		return body2.stackFrames[f].name;
	}

	@Override
	public int getLineNumber() throws DebugException {
		return body2.stackFrames[f].line;
	}

	@Override
	public int getCharStart() throws DebugException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharEnd() throws DebugException {
		// TODO Auto-generated method stub
		return 0;
	}
}