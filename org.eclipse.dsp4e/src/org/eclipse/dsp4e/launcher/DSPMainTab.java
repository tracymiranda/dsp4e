package org.eclipse.dsp4e.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.dsp4e.DSPPlugin;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class DSPMainTab extends AbstractLaunchConfigurationTab {

	private Text debugCommandText;
	private Text debugArgsText;
	private Text jsonText;

	private static final String NODE_DEBUG_CMD = "/scratch/node/node-v6.11.0-linux-x64/bin/node";
	private static final String MOCK_DEBUG_ARG = "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.20.0/out/mockDebug.js";

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), getHelpContextId());
		comp.setLayout(new GridLayout(1, true));
		comp.setFont(parent.getFont());

		createVerticalSpacer(comp, 3);
		createDebugAdapterComponent(comp);
		createDebugJSonComponent(comp);

	}

	private void createDebugAdapterComponent(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText("Debug Adapter Settings");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label debugText = new Label(group, SWT.NONE);
		debugText.setText(
				"We launch specific debug adapters using these settings. In future this could be handled by an extension point.");

		debugText.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).create());

		Label programLabel = new Label(group, SWT.NONE);
		programLabel.setText("&Command:");
		programLabel.setLayoutData(new GridData(GridData.BEGINNING));

		debugCommandText = new Text(group, SWT.SINGLE | SWT.BORDER);
		debugCommandText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		debugCommandText.setText(NODE_DEBUG_CMD);

		Label argsLabel = new Label(group, SWT.NONE);
		argsLabel.setText("&Arguments:");
		argsLabel.setLayoutData(new GridData(GridData.BEGINNING));

		debugArgsText = new Text(group, SWT.SINGLE | SWT.BORDER);
		debugArgsText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		debugArgsText.setText(MOCK_DEBUG_ARG);

		debugCommandText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		debugArgsText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	private void createDebugJSonComponent(Composite parent) {
		Composite comp = new Group(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label jsonLabel = new Label(comp, SWT.NONE);
		jsonLabel.setText("&Launch Parameters (Json):");
		jsonLabel.setLayoutData(new GridData(GridData.BEGINNING));

		jsonText = new Text(comp, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		jsonText.setLayoutData(new GridData(GridData.FILL_BOTH));
		jsonText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String debugCmd = configuration.getAttribute(DSPPlugin.ATTR_DSP_CMD, (String) null);
			if (debugCmd != null) {
				debugCommandText.setText(debugCmd);
			}

			String debugArgs = configuration.getAttribute(DSPPlugin.ATTR_DSP_ARGS, (String) null);
			if (debugArgs != null) {
				debugArgsText.setText(debugArgs);
			}
			
			jsonText.setText(configuration.getAttribute(DSPPlugin.ATTR_DSP_PARAM, ""));
		} catch (CoreException e) {
			setErrorMessage(e.getMessage());
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DSPPlugin.ATTR_DSP_CMD, getAttributeValueFrom(debugCommandText));
		configuration.setAttribute(DSPPlugin.ATTR_DSP_ARGS, getAttributeValueFrom(debugArgsText));	
		configuration.setAttribute(DSPPlugin.ATTR_DSP_PARAM, getAttributeValueFrom(jsonText));

	}
	
	/**
	 * Returns the string in the text widget, or <code>null</code> if empty.
	 * 
	 * @return text or <code>null</code>
	 */
	protected String getAttributeValueFrom(Text text) {
		String value = text.getText().trim();
		if (!value.isEmpty()) {
			return value;
		}
		return null;
	}

	@Override
	public String getName() {
		return "Debug Adapter";
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		setMessage(null);
		String text = debugCommandText.getText();

		if (text.length() > 0) {
			return true;
		} else {
			setMessage("Specify a debug adapter command");
			return false;
		}
	}

}
