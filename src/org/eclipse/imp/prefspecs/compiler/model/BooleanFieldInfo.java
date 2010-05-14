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

package org.eclipse.imp.prefspecs.compiler.model;

import org.eclipse.ui.console.MessageConsoleStream;

public class BooleanFieldInfo extends FieldInfo {
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected boolean defaultValue = false;

	public BooleanFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public BooleanFieldInfo(PreferencesPageInfo parentPage, String name, boolean defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public BooleanFieldInfo(PreferencesPageInfo parentPage, String name,
			boolean defValue, boolean hasSpecialValue, boolean specialValue)
	{
		this(parentPage, name, defValue);
	}

	public void setDefaultValue(boolean b) {
		defaultValue = b;
	}
	
	public boolean getDefaultValue() {
		return defaultValue;
	}

	/*
	 * For reporting on the contents of the virtual field
	 */
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "defaultValue    = " + defaultValue);
	}
}
