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

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		List<String> commandList = new ArrayList<String>();
		
		commandList.add("C:\\Program Files\\nodejs\\node.exe \"C:\\Users\\tracy\\.vscode-insiders\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js\"");
		File workingDir = new File("C:\\Users\\tracy");
		
		String[] commandLine = commandList.toArray(new String[commandList.size()]);
		Process process = DebugPlugin.exec(commandLine, workingDir);
		IProcess p = DebugPlugin.newProcess(launch, process, "Launch mock debug");

		//create a debug target
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			IDebugTarget target;
			try {
				target = new ReadmeDebugTarget(launch, p,
						new InputStreamReader(process.getInputStream()),
						new OutputStreamWriter(process.getOutputStream()));
				launch.addDebugTarget(target);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
