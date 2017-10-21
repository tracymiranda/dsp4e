package org.eclipse.dsp4e.debugmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.dsp4j.DebugProtocol.Scope;
import org.eclipse.dsp4j.DebugProtocol.ScopesArguments;
import org.eclipse.dsp4j.DebugProtocol.StackFrame;

public class DSPStackFrame extends DSPDebugElement implements IStackFrame {
	private DSPThread thread;
	private org.eclipse.dsp4j.DebugProtocol.StackFrame stackFrame;
	private int depth;

	public DSPStackFrame(DSPThread thread, org.eclipse.dsp4j.DebugProtocol.StackFrame stackFrame, int depth) {
		super(thread.getDebugTarget());
		this.thread = thread;
		this.stackFrame = stackFrame;
		this.depth = depth;
	}

	public DSPStackFrame replace(StackFrame newStackFrame, int newDepth) {
		if (newDepth == depth && Objects.equals(newStackFrame.source, stackFrame.source)) {
			stackFrame = newStackFrame;
			return this;
		}
		return new DSPStackFrame(thread, newStackFrame, newDepth);
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
		Scope[] scopes = complete(
				getDebugTarget().debugProtocolServer.scopes(new ScopesArguments().setFrameId(stackFrame.id))).scopes;
		List<DSPVariable> vars = new ArrayList<>();
		for (Scope scope : scopes) {
			DSPVariable variable = new DSPVariable(this, scope.variablesReference, scope.name, "");
			vars.add(variable);
		}
		return vars.toArray(new IVariable[vars.size()]);
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
	public String toString() {
		return "StackFrame [depth=" + depth + ", line=" + stackFrame.line + ", thread=" + thread + ", stackFrame="
				+ stackFrame + "]";
	}

}
