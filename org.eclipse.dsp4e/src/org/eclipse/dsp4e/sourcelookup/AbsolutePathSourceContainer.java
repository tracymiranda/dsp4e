package org.eclipse.dsp4e.sourcelookup;

import java.nio.file.Paths;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainer;

public class AbsolutePathSourceContainer extends AbstractSourceContainer implements ISourceContainer {

	@Override
	public Object[] findSourceElements(String name) throws CoreException {
		if (name != null) {
			if (Paths.get(name).isAbsolute()) {
				return new Object[] {name};
			}
		}
		return new Object[0];
	}

	@Override
	public String getName() {
		return "Absolute Path";
	}

	@Override
	public ISourceContainerType getType() {
		return null;
	}

}
