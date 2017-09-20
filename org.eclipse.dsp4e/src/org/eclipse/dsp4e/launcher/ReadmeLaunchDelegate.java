package org.eclipse.dsp4e.launcher;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.dsp4e.Activator;
import org.eclipse.dsp4e.debugmodel.ReadmeDebugTarget;

public class ReadmeLaunchDelegate implements ILaunchConfigurationDelegate {
	// private static final String NODE_DEBUG_CMD = "C:\\Program
	// Files\\nodejs\\node.exe";
	// private static final String NODE_DEBUG_ARG =
	// "C:\\Users\\jonah\\.vscode\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";

	private static final String NODE_DEBUG_CMD = "/scratch/node/node-v6.11.0-linux-x64/bin/node";
	private static final String MOCK_DEBUG_ARG = "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.20.0/out/mockDebug.js";
	private static final String NATIVE_DEBUG_ARG = "/home/jonah/.vscode/extensions/webfreak.debug-0.21.2/out/src/gdb.js";
	private static final String CWD = "/scratch/debug/examples/nativedebug";
	private static final String README_MD = "/scratch/debug/examples/mockdebug/readme.md";

	// private static final String NODE_DEBUG_CMD = "C:\\Program
	// Files\\nodejs\\node.exe";
	// private static final String NODE_DEBUG_ARG =
	// "C:\\Users\\tracy\\.vscode-insiders\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD, MOCK_DEBUG_ARG);
		Map<String, Object> launchArguments = new HashMap<>();
		// launchArguments.put("name", "Debug");
		// launchArguments.put("type", "gdb");
		// launchArguments.put("request", "launch");
		// launchArguments.put("target", "./main");
		// launchArguments.put("cwd", CWD);
		launchArguments.put("type", "mock");
		launchArguments.put("request", "launch");
		launchArguments.put("name", "Mock Debug");
		launchArguments.put("program", README_MD);
		launchArguments.put("stopOnEntry", true);
		launchArguments.put("trace", false);
		launchArguments.put("noDebug", false);

		// try (Socket process = new Socket("127.0.0.1", 4711)){
		try {
			Process process = processBuilder.start();

			// IProcess p = DebugPlugin.newProcess(launch, process, "Launch mock debug");
			// create a debug target
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				IDebugTarget target;
				target = new ReadmeDebugTarget(launch, null, process.getInputStream(), process.getOutputStream(),
						launchArguments);
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
