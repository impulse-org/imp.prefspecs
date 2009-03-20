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

public class VirtualStringFieldInfo extends VirtualFieldInfo {

	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected String specialValue = null;
	
	
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected String defaultValue = "\"\"";
	
	/**
	 * True iff this field has an "empty value" specification; if not, the values of
	 * emptyValueAllowed and emptyValue are irrelevant and should be ignored.
	 */
	protected boolean hasEmptyValueSpec = false;

	/**
	 * Whether a concrete instance of the field
	 * can take on a designated "empty" value
	 */
	protected boolean emptyValueAllowed = false;
	
	
	/**
	 * The designated "empty" value for this field
	 */
	protected String emptyValue = "";
	

	protected String validatorQualClass = null;
	
	
	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name,
			String defValue, boolean hasSpecial, String special)
	{
		this(parentPage, name, defValue);
		this.hasSpecialValue = hasSpecial;
		this.specialValue = special;
	}
	

	public void setDefaultValue(String s) {
		defaultValue = s;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	
	public void setSpecialValue(String s) {
		specialValue = s;
	}
	
	public String getSpecialValue() {
		return specialValue;
	}


	public boolean hasEmptyValueSpec() {
	    return hasEmptyValueSpec;
	}

	public void setEmptyValueAllowed(boolean b) {
		emptyValueAllowed = b;
		hasEmptyValueSpec = true;
	}
	
	public boolean getEmptyValueAllowed() {
		return emptyValueAllowed;
	}

	
	public void setEmptyValue(String s) {
		if (!getHasSpecialValue())
			throw new IllegalStateException(
				"VirtualStringFieldInfo.setSpecialValue(String):  attempt to set special value when field has no special value");
		emptyValue = s;
		hasEmptyValueSpec = true;
	}
	
	public String getEmptyValue() {
		return emptyValue != null ? emptyValue : "";
	}


    public String getValidatorQualClass() {
        return validatorQualClass;
    }

    public void setValidatorQualClass(String qualClass) {
        this.validatorQualClass= qualClass;
    }

	/*
	 * For reporting on the contents of the virtual field
	 */
    public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "hasSpecial  = " + hasSpecialValue);
		out.println(indent + "special     = " + specialValue);
		out.println(indent + "defaultValue    = " + defaultValue);
	}
}
