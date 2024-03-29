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

import org.eclipse.imp.prefspecs.compiler.codegen.BooleanFieldCodeGenerator;
import org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator;
import org.eclipse.ui.console.MessageConsoleStream;

public class BooleanFieldInfo extends FieldInfo {
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected boolean defaultValue = false;

	public BooleanFieldInfo(IPageMemberContainer parent, String name) {
		super(parent, name);
	}
	
	public BooleanFieldInfo(PageInfo parentPage, String name, boolean defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

    @Override
    public FieldCodeGenerator getCodeGenerator() {
        return new BooleanFieldCodeGenerator(this);
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
    @Override
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "defaultValue    = " + defaultValue);
	}
}
