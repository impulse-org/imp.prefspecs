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

public class VirtualIntFieldInfo extends VirtualFieldInfo {

	
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected int defaultValue = 0;
		
	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected int specialValue = 0;

	/**
	 * True iff this field has a range specification; if false, rangeHigh and rangeLow
	 * are irrelevant and should be ignored.
	 */
	protected boolean hasRangeSpec = false;

	/**
	 * Values that delimit the range that the field may
	 * take on
	 */
	protected int rangeHigh = Integer.MAX_VALUE;
	protected int rangeLow 	= Integer.MIN_VALUE	;
	
	
	
	public VirtualIntFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualIntFieldInfo(PreferencesPageInfo parentPage, String name, int defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public VirtualIntFieldInfo(PreferencesPageInfo parentPage, String name,
			int defValue, boolean hasSpecialValue, int specialValue)
	{
		this(parentPage, name, defValue);
		this.hasSpecialValue = hasSpecialValue;
		this.specialValue = specialValue;
	}
	
	
	
	public void setDefaultValue(int i) {
		if (i < getRangeLow() || i > getRangeHigh())
			throw new IllegalArgumentException(
				"VirtualIntFieldInfo.setDefaultValue(int):  attempt to set default value = " + i +
				" outside of range = " + getRangeLow() + ".." + getRangeHigh());
		defaultValue = i;
	}
	
	public int getDefaultValue() {
		return defaultValue;	
	}


	public void setSpecialValue(int i) {
		if (!getHasSpecialValue())
			throw new IllegalArgumentException(
				"VirtualIntFieldInfo.setSpecialValue(int):  attempt to set special value when field has no special value");
		if (i < getRangeLow() || i > getRangeHigh())
			throw new IllegalArgumentException(
				"VirtualIntFieldInfo.setSpecialValue(int):  attempt to set special value = " + i +
				" outside of range = " + getRangeLow() + ".." + getRangeHigh());
		specialValue = i;
	}
	
	public int getSpecialValue() {
		return specialValue;
	}
	

	public boolean hasRangeSpec() {
	    return hasRangeSpec;
	}

	public void setRange(int low, int high) {
		if (high < low) {
			throw new IllegalArgumentException(
				"VirtualIntField.setRange(int,int):  given high value = " + high +
				" is less than low value " + low);
		}
		hasRangeSpec = true;
		rangeLow = low;
		rangeHigh = high;
	}
	
	public int getRangeHigh() {
		return rangeHigh;
	}
	
	public int getRangeLow() {
		return rangeLow;
	}


	/*
	 * For reporting on the contents of the virtual field
	 */
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "hasSpecialValue  = " + getHasSpecialValue());
		out.println(indent + "specialValue     = " + getSpecialValue());
		out.println(indent + "defaultVallue    = " + getDefaultValue());
	}
}
