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

public class VirtualDoubleFieldInfo extends VirtualFieldInfo {
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected double defaultValue = 0.0;
		
	/**
	 * True iff this field has a range specification; if false, rangeHigh and rangeLow
	 * are irrelevant and should be ignored.
	 */
	protected boolean hasRangeSpec = false;

	/**
	 * Values that delimit the range that the field may
	 * take on
	 */
	protected double rangeHigh = Double.MAX_VALUE;
	protected double rangeLow = Double.MIN_VALUE;



	public VirtualDoubleFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualDoubleFieldInfo(PreferencesPageInfo parentPage, String name, double defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}


	public void setDefaultValue(double d) {
		if (d < getRangeLow() || d > getRangeHigh())
			throw new IllegalArgumentException(
				"VirtualDoubleFieldInfo.setDefaultValue(double):  attempt to set default value = " + d +
				" outside of range = " + getRangeLow() + ".." + getRangeHigh());
		defaultValue = d;
	}
	
	public double getDefaultValue() {
		return defaultValue;	
	}

	public boolean hasRangeSpec() {
	    return hasRangeSpec;
	}

	public void setRange(double low, double high) {
		if (high < low) {
			throw new IllegalArgumentException(
				"VirtualDoubleFieldInfo.setRange(double,double):  given high value = " + high +
				" is less than low value " + low);
		}
		hasRangeSpec = true;
		rangeLow = low;
		rangeHigh = high;
	}
	
	public double getRangeHigh() {
		return rangeHigh;
	}
	
	public double getRangeLow() {
		return rangeLow;
	}


	/*
	 * For reporting on the contents of the virtual field
	 */
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "defaultVallue    = " + getDefaultValue());
	}
}
