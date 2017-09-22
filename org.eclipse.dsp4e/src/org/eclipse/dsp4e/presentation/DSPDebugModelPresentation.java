package org.eclipse.dsp4e.presentation;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugEditorPresentation;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.dsp4e.debugmodel.DebugTarget;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class DSPDebugModelPresentation extends LabelProvider implements IDebugModelPresentation {

	@Override
	public String getText(Object element) {
		if (element instanceof DebugTarget) {
			return getTargetText((DebugTarget) element);
		}
		return DebugUIPlugin.getDefaultLabelProvider().getText(element);
	}

	/**
	 * Returns a label for the given debug target
	 *
	 * @param target
	 *            debug target
	 * @return a label for the given debug target
	 */
	private String getTargetText(DebugTarget target) {
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
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(element.toString()));
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = root.findFilesForLocationURI(fileStore.toURI());
		if (files != null) {
			for (IFile file : files) {
				if (file.exists()) {
					return new FileEditorInput(file);
				}
			}
		}
		return new FileStoreEditorInput(fileStore);
	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		return EditorsUI.DEFAULT_TEXT_EDITOR_ID;
	}

	@Override
	public void setAttribute(String attribute, Object value) {
	}

	@Override
	public void computeDetail(IValue value, IValueDetailListener listener) {
		// TODO Auto-generated method stub

	}

}
