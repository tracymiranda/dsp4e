package org.eclipse.dsp4e.debugmodel;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.dsp4j.DebugProtocol.StackTraceResponse.Body;

public class StackFrame implements IStackFrame {
	private Thread thread;
	private org.eclipse.dsp4j.DebugProtocol.StackFrame stackFrame;

	/**
	 *
	 */

	public StackFrame(Thread thread, org.eclipse.dsp4j.DebugProtocol.StackFrame stackFrame) {
		this.thread = thread;
		this.stackFrame = stackFrame;
	}

	@Override
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}

	@Override
	public boolean isTerminated() {
		return getDebugTarget().isTerminated();
	}

	@Override
	public boolean canTerminate() {
		return getDebugTarget().canTerminate();
	}

	@Override
	public void suspend() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() throws DebugException {
		getThread().resume();
	}

	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return getDebugTarget().isSuspended();
	}

	@Override
	public boolean canSuspend() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canResume() {
		return getThread().canResume();
	}

	@Override
	public void stepReturn() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepOver() throws DebugException {
		getThread().stepOver();
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
		return getThread().canStepOver();
	}

	@Override
	public boolean canStepInto() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		System.out.println("StackFrame adapters: " + adapter);
		return null;
	}

	@Override
	public String getModelIdentifier() {
		return getDebugTarget().getModelIdentifier();
	}

	@Override
	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return getThread().getDebugTarget();
	}

	@Override
	public boolean hasVariables() throws DebugException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
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
		return thread;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() throws DebugException {
		return stackFrame.name;
	}

	@Override
	public int getLineNumber() throws DebugException {
		return stackFrame.line;
	}

	@Override
	public int getCharStart() throws DebugException {
		return -1;
	}

	@Override
	public int getCharEnd() throws DebugException {
		return -1;
	}

	public String getSourceName() {
		// TODO implement sourcereferences
		return stackFrame.source.path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stackFrame == null) ? 0 : stackFrame.hashCode());
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
		StackFrame other = (StackFrame) obj;
		if (stackFrame == null) {
			if (other.stackFrame != null)
				return false;
		} else if (!stackFrame.equals(other.stackFrame))
			return false;
		if (thread == null) {
			if (other.thread != null)
				return false;
		} else if (!thread.equals(other.thread))
			return false;
		return true;
	}

	
}