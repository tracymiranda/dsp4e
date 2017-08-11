package org.eclipse.dsp4e.example.readme.launcher;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.dsp43.example.readme.debugmodel.ReadmeDebugTarget;
import org.eclipse.dsp4e.example.readme.Activator;

public class ReadmeLaunchDelegate implements ILaunchConfigurationDelegate {
	// private static final String NODE_DEBUG_CMD = "C:\\Program
	// Files\\nodejs\\node.exe";
	// private static final String NODE_DEBUG_ARG =
	// "C:\\Users\\jonah\\.vscode\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";

	private static final String NODE_DEBUG_CMD = "C:\\\\Program Files\\\\nodejs\\\\node.exe";
	private static final String NODE_DEBUG_ARG = "C:\\\\Users\\\\artke\\\\.vscode\\\\extensions\\\\andreweinand.mock-debug-0.19.0\\\\out\\\\mockDebug.js";

	// private static final String NODE_DEBUG_CMD = "C:\\Program
	// Files\\nodejs\\node.exe";
	// private static final String NODE_DEBUG_ARG =
	// "C:\\Users\\tracy\\.vscode-insiders\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD, NODE_DEBUG_ARG);
		Process process;
		try {
			process = processBuilder.start();

			// IProcess p = DebugPlugin.newProcess(launch, process, "Launch mock debug");
			// create a debug target
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				IDebugTarget target;
				target = new ReadmeDebugTarget(launch, null, process.getInputStream(), process.getOutputStream());
				launch.addDebugTarget(target);
			}
		} catch (IOException e1) {
			abort("Failed to launch debug process", e1);
		}
	}

	/**
	 * Throws an exception with a new status containing the given message and
	 * optional exception.
	 *
	 * @param message
	 *            error message
	 * @param e
	 *            underlying exception
	 * @throws CoreException
	 */
	private void abort(String message, Throwable e) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, message, e));
	}

}
