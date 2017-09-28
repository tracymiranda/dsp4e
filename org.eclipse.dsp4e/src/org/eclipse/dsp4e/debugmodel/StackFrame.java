package org.eclipse.dsp4e.debugmodel;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

public class StackFrame extends DSPDebugElement implements IStackFrame {
	private Thread thread;
	private org.eclipse.dsp4j.DebugProtocol.StackFrame stackFrame;
	private int depth;

	public StackFrame(Thread thread, org.eclipse.dsp4j.DebugProtocol.StackFrame stackFrame, int depth) {
		super(thread.getDebugTarget());
		this.thread = thread;
		this.stackFrame = stackFrame;
		this.depth = depth;
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
	}

	@Override
	public void resume() throws DebugException {
		getThread().resume();
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
		return getThread().canResume();
	}

	@Override
	public void stepReturn() throws DebugException {
	}

	@Override
	public void stepOver() throws DebugException {
		getThread().stepOver();
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
		return getThread().canStepOver();
	}

	@Override
	public boolean canStepInto() {
		return false;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return true;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
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
		return stackFrame.source.path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depth;
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
		if (depth != other.depth)
			return false;
		if (thread == null) {
			if (other.thread != null)
				return false;
		} else if (!thread.equals(other.thread))
			return false;
		return true;
	}


	
}