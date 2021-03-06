package org.eclipse.dsp4e;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DSPPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dsp4e"; //$NON-NLS-1$

	// Unique identifier for the DSP debug model launch config
	public static final String ID_DSP_DEBUG_MODEL = "dsp.debugModel";

	// Launch configuration attribute keys
	public static final String ATTR_DSP_CMD = ID_DSP_DEBUG_MODEL + ".ATTR_DSP_CMD";
	public static final String ATTR_DSP_ARGS = ID_DSP_DEBUG_MODEL + ".ATTR_DSP_ARGS";
	public static final String ATTR_DSP_PARAM = ID_DSP_DEBUG_MODEL + ".ATTR_DSP_PARAM";

	// The shared instance
	private static DSPPlugin plugin;

	/**
	 * The constructor
	 */
	public DSPPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DSPPlugin getDefault() {
		return plugin;
	}

}
