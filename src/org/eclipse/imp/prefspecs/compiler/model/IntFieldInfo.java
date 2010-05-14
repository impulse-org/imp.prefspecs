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

public class IntFieldInfo extends FieldInfo {
	/**
	 * The default value associated with this field
	 */
	protected int defaultValue = 0;

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
	protected int rangeLow 	= Integer.MIN_VALUE;

	public IntFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}

	public IntFieldInfo(PreferencesPageInfo parentPage, String name, int defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public void setDefaultValue(int i) {
		if (i < getRangeLow() || i > getRangeHigh())
			throw new IllegalArgumentException(
				"IntFieldInfo.setDefaultValue(int):  attempt to set default value = " + i +
				" outside of range = " + getRangeLow() + ".." + getRangeHigh());
		defaultValue = i;
	}

	public int getDefaultValue() {
		return defaultValue;
	}

	public boolean hasRangeSpec() {
	    return hasRangeSpec;
	}

	public void setRange(int low, int high) {
		if (high < low) {
			throw new IllegalArgumentException(
				"IntFieldInfo.setRange(int,int):  given high value = " + high +
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
	 * For reporting on the contents of the field
	 */
	public void dump(String prefix, MessageConsoleStream out) {
		super.dump(prefix, out);
		String indent = prefix + "  ";
		out.println(indent + "defaultVallue    = " + getDefaultValue());
	}
}
