package org.eclipse.dsp4e.debugmodel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.dsp4e.DSPPlugin;

abstract public class DSPDebugElement extends DebugElement {

	public DSPDebugElement(DSPDebugTarget target) {
		super(target);
	}

	@Override
	public String getModelIdentifier() {
		return DSPPlugin.ID_DSP_DEBUG_MODEL;
	}

	@Override
	public DSPDebugTarget getDebugTarget() {
		return (DSPDebugTarget) super.getDebugTarget();
	}

	/**
	 * Returns the breakpoint manager
	 * 
	 * @return the breakpoint manager
	 */
	protected IBreakpointManager getBreakpointManager() {
		return DebugPlugin.getDefault().getBreakpointManager();
	}

	/**
	 * Gets the response from the debug command and prints some debug info
	 *
	 * @param future
	 * @throws DebugException
	 */
	static <T> T complete(CompletableFuture<T> future) throws DebugException {
		try {
			return future.get();

		} catch (InterruptedException | ExecutionException e) {
			throw newTargetRequestFailedException("Failed to get result from target", e);
		}
	}

	static DebugException newTargetRequestFailedException(String message, Throwable e) {
		return new DebugException(new Status(IStatus.ERROR, DebugPlugin.getUniqueIdentifier(),
				DebugException.TARGET_REQUEST_FAILED, message, e));
	}

}
