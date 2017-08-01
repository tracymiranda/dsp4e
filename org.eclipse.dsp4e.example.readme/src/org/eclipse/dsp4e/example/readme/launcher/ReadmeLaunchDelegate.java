package org.eclipse.dsp4e.example.readme.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.dsp43.example.readme.debugmodel.ReadmeDebugTarget;

public class ReadmeLaunchDelegate implements ILaunchConfigurationDelegate {
	private static final String NODE_DEBUG_CMD = "C:\\\\Program Files\\\\nodejs\\\\node.exe";
	private static final String NODE_DEBUG_ARG = "C:\\\\Users\\\\artke\\\\.vscode\\\\extensions\\\\andreweinand.mock-debug-0.19.0\\\\out\\\\mockDebug.js";

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD, NODE_DEBUG_ARG);
		Process process;
		try {
			process = processBuilder.start();

			IProcess p = DebugPlugin.newProcess(launch, process, "Launch mock debug");
			// create a debug target
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				IDebugTarget target;
				try {
					target = new ReadmeDebugTarget(launch, p, new InputStreamReader(process.getInputStream()),
							new OutputStreamWriter(process.getOutputStream()));
					launch.addDebugTarget(target);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
