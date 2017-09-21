package org.eclipse.dsp4e.launcher;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
import org.eclipse.dsp4e.DSPPlugin;
import org.eclipse.dsp4e.debugmodel.ReadmeDebugTarget;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReadmeLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		List<String> command = new ArrayList<>();
		String debugCmd = configuration.getAttribute(DSPPlugin.ATTR_DSP_CMD, (String) null);

		if (debugCmd == null) {
			abort("Debug command unspecified.", null); //$NON-NLS-1$
		}
		command.add(debugCmd);
		String debugArgs = configuration.getAttribute(DSPPlugin.ATTR_DSP_ARGS, (String) null);
		if (debugArgs != null && !debugArgs.isEmpty()) {
			command.add(debugArgs);
		}

		ProcessBuilder processBuilder = new ProcessBuilder(command);

		String launchArgumentJson = configuration.getAttribute(DSPPlugin.ATTR_DSP_PARAM, (String) null);
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> launchArguments = gson.fromJson(launchArgumentJson, type);

		try {
			Process process = processBuilder.start();
			// Socket process = new Socket("127.0.0.1", 4711);
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				IDebugTarget target;
				target = new ReadmeDebugTarget(launch, process, process.getInputStream(), process.getOutputStream(),
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
		throw new CoreException(new Status(IStatus.ERROR, DSPPlugin.PLUGIN_ID, 0, message, e));
	}

}
