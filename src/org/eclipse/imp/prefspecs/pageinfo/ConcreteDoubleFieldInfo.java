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

public class ConcreteDoubleFieldInfo
	extends ConcreteFieldInfo
{
	/*
	 * Local copy of the virtual field on which this concrete
	 * field is based.  The purpose of the copy is to avoid the
	 * need to repeatedly typecast the copy of the field contained
	 * in the supertype.
	 */
	protected VirtualDoubleFieldInfo vDoubleFieldInfo = null;
	
	
	/*
	 * Fields that can be set in a specific concrete double field
	 * independently of the corresponding virtual field
	 */
	

	/**
	 * Values that delimit the range that the field may
	 * take on
	 */
	protected double rangeHigh = Double.MAX_VALUE;
	protected double rangeLow = Double.MIN_VALUE;
	
	
	/**
	 * Constructor to use for concrete fields that are associated
	 * with a virtual, as happens during the processing of the prefspecs
	 * AST and the elaboration of field attributes.
	 * 
	 * @param vField
	 * @param parentTab
	 */
	public ConcreteDoubleFieldInfo(
		VirtualDoubleFieldInfo vField, PreferencesTabInfo parentTab)
	{
		// Super gets page and name from associated virtual field;
		// will serve as a check that given values aren't null
		super(vField, parentTab);
	}

	
	public double getDefaultValue() {
		return vDoubleFieldInfo.getDefaultValue();
	}
	
	
	public void setRangeHigh(double d) {
		if (d < rangeLow) {
			throw new IllegalArgumentException(
				"VirtualIintField.setRangeHigh(int):  given value = " + d +
				" is less than low value for range = " + rangeLow);
		}
		rangeHigh = d;
	}
	
	public double getRangeHigh() {
		return rangeHigh;
	}
	
	
	public void setRangeLow(double d) {
		if (d > rangeHigh) {
			throw new IllegalArgumentException(
				"VirtualIintField.setRangeLow(int):  given value = " + d +
				" is greater than high value for range = " + rangeHigh);
		}
		rangeLow = d;
	}
	
	public double getRangeLow() {
		return rangeLow;
	}


	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "rangeLow = " + getRangeLow());
		out.println(indent + "rangeHigh = " + getRangeHigh());
	}
}
