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

import org.eclipse.imp.preferences.fields.ColorFieldEditor;
import org.eclipse.imp.prefspecs.compiler.codegen.ColorFieldCodeGenerator;
import org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator;
import org.eclipse.ui.console.MessageConsoleStream;

public class ColorFieldInfo extends FieldInfo {
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected String defaultColor= ColorFieldEditor.COLOR_DEFAULT_SPEC;

	public ColorFieldInfo(IPageMemberContainer parentPage, String name) {
		this(parentPage, name, null);
	}
	
	public ColorFieldInfo(IPageMemberContainer parentPage, String name, String defColor) {
		super(parentPage, name);
		this.defaultColor= defColor;
	}

    @Override
    public FieldCodeGenerator getCodeGenerator() {
        return new ColorFieldCodeGenerator(this);
    }

	public void setDefaultColor(String nm) {
		defaultColor= nm;
	}
	
	public String getDefaultColor() {
		return defaultColor;
	}

	/*
	 * For reporting on the contents of the virtual field
	 */
    public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
        out.println(indent + "defaultColor = " + defaultColor);
    }
}
