/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation.
 * All rights reserved. This program and the accompanying material
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.imp.prefspecs;

import org.eclipse.imp.runtime.PluginBase;
import org.osgi.framework.BundleContext;

/*
 * SMS 27 Mar 2007:  Deleted creation of preferences cache (now obsolete)
 * SMS 28 Mar 2007:
 * 	- Plugin class name now totally parameterized
 *  - Plugin package made a separate parameter
 * SMS 19 Jun 2007:
 * 	- Added kLanguageName (may be used by later updates to the template)
 * 	- Added field and method related to new preferences service; deleted
 *	  code for initializing preference store from start(..) method
 */

public class PrefspecsPlugin extends PluginBase {
	public static final String kPluginID = "org.eclipse.imp.prefspecs";

	public static final String kLanguageID = "prefspecs";

	/**
	 * The unique instance of this plugin class
	 */
	protected static PrefspecsPlugin sPlugin;

	public static PrefspecsPlugin getInstance() {
		// SMS 11 Jul 2007
		// Added conditional call to constructor in case the plugin
		// class has not been auto-started
		if (sPlugin == null)
			new PrefspecsPlugin();
		return sPlugin;
	}

	public PrefspecsPlugin() {
		super();
		sPlugin = this;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);

	}

	public String getID() {
		return kPluginID;
	}

	@Override
	public String getLanguageID() {
	    return kLanguageID;
	}

	// Definitions for image management

	public static final org.eclipse.core.runtime.IPath ICONS_PATH = new org.eclipse.core.runtime.Path("icons/"); //$NON-NLS-1$

	protected void initializeImageRegistry(
			org.eclipse.jface.resource.ImageRegistry reg) {
		org.eclipse.core.runtime.IPath path = ICONS_PATH
				.append("prefspecs_default_image.gif");//$NON-NLS-1$
		org.eclipse.jface.resource.ImageDescriptor imageDescriptor = createImageDescriptor(
				getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.PREFSPECS_DEFAULT_IMAGE, imageDescriptor);

		path = ICONS_PATH.append("prefspecs_default_outline_item.gif");//$NON-NLS-1$
		imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.PREFSPECS_DEFAULT_OUTLINE_ITEM,
				imageDescriptor);

		path = ICONS_PATH.append("prefspecs_file.gif");//$NON-NLS-1$
		imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.PREFSPECS_FILE, imageDescriptor);

		path = ICONS_PATH.append("prefspecs_file_warning.gif");//$NON-NLS-1$
		imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.PREFSPECS_FILE_WARNING, imageDescriptor);

		path = ICONS_PATH.append("prefspecs_file_error.gif");//$NON-NLS-1$
		imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.PREFSPECS_FILE_ERROR, imageDescriptor);

		path = ICONS_PATH.append("boolean_field.gif");//$NON-NLS-1$
		imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.BOOLEAN_FIELD, imageDescriptor);

        path = ICONS_PATH.append("color_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.COLOR_FIELD, imageDescriptor);

        path = ICONS_PATH.append("combo_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.COMBO_FIELD, imageDescriptor);

        path = ICONS_PATH.append("directory_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.DIRECTORY_FIELD, imageDescriptor);

        path = ICONS_PATH.append("dir_list_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.DIRLIST_FIELD, imageDescriptor);

        path = ICONS_PATH.append("double_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.DOUBLE_FIELD, imageDescriptor);

        path = ICONS_PATH.append("file_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.FILE_FIELD, imageDescriptor);

        path = ICONS_PATH.append("font_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.FONT_FIELD, imageDescriptor);

		path = ICONS_PATH.append("int_field.gif");//$NON-NLS-1$
		imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
		reg.put(IPrefspecsResources.INT_FIELD, imageDescriptor);

		path = ICONS_PATH.append("radio_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.RADIO_FIELD, imageDescriptor);
        
        path = ICONS_PATH.append("string_field.gif");//$NON-NLS-1$
        imageDescriptor = createImageDescriptor(getInstance().getBundle(), path);
        reg.put(IPrefspecsResources.STRING_FIELD, imageDescriptor);
	}

	public static org.eclipse.jface.resource.ImageDescriptor createImageDescriptor(
			org.osgi.framework.Bundle bundle,
			org.eclipse.core.runtime.IPath path) {
		java.net.URL url = org.eclipse.core.runtime.FileLocator.find(bundle,
				path, null);
		if (url != null) {
			return org.eclipse.jface.resource.ImageDescriptor
					.createFromURL(url);
		}
		return null;
	}
}
