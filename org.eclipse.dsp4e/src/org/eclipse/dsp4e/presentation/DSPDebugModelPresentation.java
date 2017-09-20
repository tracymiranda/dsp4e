package org.eclipse.dsp4e.presentation;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.dsp4e.debugmodel.ReadmeDebugTarget;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IEditorInput;

public class DSPDebugModelPresentation extends LabelProvider implements IDebugModelPresentation {

	@Override
	public String getText(Object element) {
		if (element instanceof ReadmeDebugTarget) {
			return getTargetText((ReadmeDebugTarget) element);
		}
		return "Model presentation default text";
	}

	/**
	 * Returns a label for the given debug target
	 * 
	 * @param target
	 *            debug target
	 * @return a label for the given debug target
	 */
	private String getTargetText(ReadmeDebugTarget target) {
		// try {
		// String pgmPath =
		// target.getLaunch().getLaunchConfiguration().getAttribute(DebugCorePlugin.ATTR_PDA_PROGRAM,
		// (String)null);
		// if (pgmPath != null) {
		// IPath path = new Path(pgmPath);
		// String label = ""; //$NON-NLS-1$
		// if (target.isTerminated()) {
		// label = "<terminated>"; //$NON-NLS-1$
		// }
		// return label + "PDA [" + path.lastSegment() + "]"; //$NON-NLS-1$
		// //$NON-NLS-2$
		// }
		// } catch (CoreException e) {
		// }
		return "DSP Debug Target Text []"; //$NON-NLS-1$

	}

	@Override
	public IEditorInput getEditorInput(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String attribute, Object value) {
	}

	@Override
	public void computeDetail(IValue value, IValueDetailListener listener) {
		// TODO Auto-generated method stub

	}

}
