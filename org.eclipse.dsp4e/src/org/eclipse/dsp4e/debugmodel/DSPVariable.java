package org.eclipse.dsp4e.debugmodel;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

final class DSPVariable extends DSPDebugElement implements IVariable {

	private Integer variablesReference;
	private String name;
	private String value;

	public DSPVariable(DSPDebugElement parent, Integer variablesReference, String name, String value) {
		super(parent.getDebugTarget());
		this.variablesReference = variablesReference;
		this.name = name;
		this.value = value;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		// TODO
	}

	@Override
	public void setValue(IValue value) throws DebugException {
		// TODO
	}

	@Override
	public boolean supportsValueModification() {
		// TODO
		return false;
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		// TODO
		return false;
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		// TODO
		return false;
	}

	@Override
	public IValue getValue() throws DebugException {
		return new DSPValue(this, variablesReference, name, value);
	}

	@Override
	public String getName() throws DebugException {
		return name;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		// TODO
		return name;
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		return false;
	}
}
