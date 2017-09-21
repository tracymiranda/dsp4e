package org.eclipse.dsp4e.sourcelookup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.dsp4e.debugmodel.StackFrame;

public class DSPSourceLookupParticipant extends AbstractSourceLookupParticipant {

	@Override
	public String getSourceName(Object object) throws CoreException {
		if (object instanceof StackFrame) {
			return ((StackFrame) object).getSourceName();
		}
		return null;
	}

}
