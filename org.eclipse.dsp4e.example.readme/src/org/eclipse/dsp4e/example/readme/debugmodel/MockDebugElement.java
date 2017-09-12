package org.eclipse.dsp4e.example.readme.debugmodel;

import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

public class MockDebugElement extends DebugElement {

	public MockDebugElement(IDebugTarget target) {
		super(target);
	}


	public static final String ID_MOCK_DEBUG_MODEL="mock.debugModel";

	
	@Override
	public String getModelIdentifier() {
		return ID_MOCK_DEBUG_MODEL;
	}

}
