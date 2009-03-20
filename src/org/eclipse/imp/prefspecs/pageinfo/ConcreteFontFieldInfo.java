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

package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.ui.console.MessageConsoleStream;

public class ConcreteFontFieldInfo extends ConcreteFieldInfo {
	/*
	 * Local copy of the virtual field on which this concrete
	 * field is based.  The purpose of the copy is to avoid the
	 * need to repeatedly typecast the copy of the field contained
	 * in the supertype.
	 */
	protected VirtualFontFieldInfo vFontFieldInfo = null;


	/**
	 * Constructor to use for concrete fields that are associated
	 * with a virtual, as happens during the processing of the prefspecs
	 * AST and the elaboration of field attributes.
	 * 
	 * @param vField
	 * @param parentTab
	 */
	public ConcreteFontFieldInfo(VirtualFontFieldInfo vFieldInfo, PreferencesTabInfo parentTab)
	{
		// Super gets page and name from associated virtual field;
		// will serve as a check that given values aren't null
		super(vFieldInfo, parentTab);
		this.vFontFieldInfo = vFieldInfo;
	}

	
	public String getDefaultName() {
		return vFontFieldInfo.getDefaultName();
	}
	
	
	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "default name   = " + vFontFieldInfo.getDefaultName());	
        out.println(indent + "default height = " + vFontFieldInfo.getDefaultHeight());   
        out.println(indent + "default style  = " + vFontFieldInfo.getDefaultStyle());   
	}
}
