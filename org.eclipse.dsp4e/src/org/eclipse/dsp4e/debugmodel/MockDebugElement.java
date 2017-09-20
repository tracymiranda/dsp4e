package org.eclipse.dsp4e.debugmodel;

import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

public class MockDebugElement extends DebugElement {

	public MockDebugElement(IDebugTarget target) {
		super(target);
	}

	public static final String ID_DSP_DEBUG_MODEL = "dsp.debugModel";

	@Override
	public String getModelIdentifier() {
		return ID_DSP_DEBUG_MODEL;
	}

}
