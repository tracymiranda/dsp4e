package org.eclipse.dsp4e.debugmodel;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

abstract public class DSPDebugElement extends DebugElement {

	public DSPDebugElement(IDebugTarget target) {
		super(target);
	}

	public static final String ID_DSP_DEBUG_MODEL = "dsp.debugModel";

	@Override
	public String getModelIdentifier() {
		return ID_DSP_DEBUG_MODEL;
	}

	@Override
	public DebugTarget getDebugTarget() {
		return (DebugTarget) super.getDebugTarget();
	}

	DebugException newTargetRequestFailedException(String message, Throwable e) {
		return new DebugException(new Status(IStatus.ERROR, DebugPlugin.getUniqueIdentifier(),
				DebugException.TARGET_REQUEST_FAILED, message, e));
	}

}
