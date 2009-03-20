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

public class ConcreteStringFieldInfo
	extends ConcreteFieldInfo
{
	/*
	 * Local copy of the virtual field on which this concrete
	 * field is based.  The purpose of the copy is to avoid the
	 * need to repeatedly typecast the copy of the field contained
	 * in the supertype.
	 */
	protected VirtualStringFieldInfo vStringFieldInfo = null;
	
	
	/*
	 * Fields that can be set in a specific concrete boolean field
	 * independently of the corresponding virtual field
	 */
	
	/**
	 * A "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected String specialValue = "\"Unspecified\"";
	
	/**
	 * Whether an concrete instance of this field can
	 * be assigned an "empty" value
	 */
	protected boolean emptyValueAllowed = true;
	
	/**
	 * An "empty" value that may be associated
	 * a concrete instance of this field
	 */
	protected String emptyValue = "";
	
	
	/**
	 * Constructor to use for concrete fields that are associated
	 * with a virtual, as happens during the processing of the prefspecs
	 * AST and the elaboration of field attributes.
	 * 
	 * @param vField
	 * @param parentTab
	 */
	public ConcreteStringFieldInfo(
		VirtualStringFieldInfo vFieldInfo, PreferencesTabInfo parentTab)
	{
		// Super gets page and name from associated virtual field;
		// will serve as a check that given values aren't null
		super(vFieldInfo, parentTab);
		this.vStringFieldInfo = vFieldInfo;
			
	}

	
	/**
	 * Constructor to use for concrete fields that don't need to be
	 * associated with a virtual field, as happens when the field
	 * attributes are fully elaborated.
	 * 
	 * @param parentTab
	 */
//	public ConcreteStringFieldInfo(PreferencesTabInfo parentTab, String name)
//	{
//		super(parentTab, name);
//	}
	

		
	public String getDefaultValue() {
		return vStringFieldInfo.getDefaultValue();
	}
	
	
	public String getSpecialValue() {
		return specialValue;
	}
	
	public void setSpecialValue(String s) {
		if (!getHasSpecialValue())
			throw new IllegalStateException(
				"ConcreteStringFieldInfo.setSpecialValue(String):  attempt to set special value when field has no special value");	
		specialValue = s;
	}
	
	
	public void setEmptyValueAllowed(boolean b) {
		emptyValueAllowed = b;
	}
	
	public boolean getEmptyValueAllowed() {
		return emptyValueAllowed;
	}

	
	public void setEmptyValue(String s) {
		emptyValue = s != null ? s : "";
	}
	
	public String getEmptyValue() {
		return vStringFieldInfo.getEmptyValue();
	}

	public String getValidatorQualClass() {
	    return vStringFieldInfo.getValidatorQualClass();
	}
	
	
	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "default value = " + vStringFieldInfo.getDefaultValue());	
		if (getHasSpecialValue()) {
			out.println(indent + "special = " + getSpecialValue());
		} else {
			out.println(indent + "no special value defined");
		}
		out.println(indent + "emptyValueAllowed = " + getEmptyValueAllowed());
		if (getEmptyValueAllowed()) {
			out.println(indent + "empty value = " + getEmptyValue());
		} else {
			out.println(indent + "no empty value defined");
		}
	}
}
