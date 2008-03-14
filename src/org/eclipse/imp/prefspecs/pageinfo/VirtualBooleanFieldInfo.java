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

public class VirtualBooleanFieldInfo extends VirtualFieldInfo {

	/**
	 * Whether a concrete instance of the boolean field
	 * can by default take on on a "special" value
	 */
	protected boolean hasSpecialValue = false;
	
	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected boolean specialValue = false;
	
	
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected boolean defaultValue = true;
	
	
	
	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name, boolean defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name,
			boolean defValue, boolean hasSpecialValue, boolean specialValue)
	{
		this(parentPage, name, defValue);
		this.hasSpecialValue = hasSpecialValue;
		this.specialValue = specialValue;
	}
	
	
	
	public void setHasSpecialValue(boolean b) {
		hasSpecialValue = b;
	}
	
	public boolean getHasSpecialValue() {
		return hasSpecialValue;
	}

	
	public void setSpecialValue(boolean b) {
		if (!getHasSpecialValue())
			throw new IllegalStateException(
				"VirtualBooleanFieldInfo.setSpecialValue(boolean):  attempt to set special value when field has no special value");
		specialValue = b;
	}
	
	public boolean getSpecialValue() {
		return specialValue;
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
	
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecial  = " + hasSpecialValue);
		System.out.println(indent + "special     = " + specialValue);
		System.out.println(indent + "defaultValue    = " + defaultValue);
	}
	
}
