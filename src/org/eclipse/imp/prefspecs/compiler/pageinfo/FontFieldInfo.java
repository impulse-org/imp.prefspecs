/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.compiler.pageinfo;

import org.eclipse.swt.SWT;
import org.eclipse.ui.console.MessageConsoleStream;

public class FontFieldInfo extends FieldInfo {
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected String defaultName= "courier";

	protected int defaultHeight= 10;

	protected int defaultStyle= SWT.NORMAL;

	public FontFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public FontFieldInfo(PreferencesPageInfo parentPage, String name, String defName, int defHeight, int defStyle) {
		this(parentPage, name);
		this.defaultName= defName;
		this.defaultHeight= defHeight;
		this.defaultStyle= defStyle;
	}

	public void setDefaultName(String nm) {
		defaultName= nm;
	}
	
	public String getDefaultName() {
		return defaultName;
	}

	/*
	 * For reporting on the contents of the virtual field
	 */
	
	public int getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight= defaultHeight;
    }

    public int getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(int defaultStyle) {
        this.defaultStyle= defaultStyle;
    }

    public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
        out.println(indent + "defaultName   = " + defaultName);
        out.println(indent + "defaultHeight = " + defaultHeight);
        out.println(indent + "defaultStyle  = " + defaultStyle);
    }
}
