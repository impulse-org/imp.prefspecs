/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.pageinfo;

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
	
	
	public void setHasSpecial(boolean b) {
		hasSpecialValue = b;
	}
	
	public boolean getHasSpecial() {
		return hasSpecialValue;
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
	
	
	public void setRangeHigh(int i) {
		if (i < rangeLow) {
			throw new IllegalArgumentException(
				"VirtualIntField.setRangeHigh(int):  given value = " + i +
				" is less than low value for range = " + rangeLow);
		}
		rangeHigh = i;
	}
	
	public int getRangeHigh() {
		return rangeHigh;
	}
	
	
	public void setRangeLow(int i) {
		if (i > rangeHigh) {
			throw new IllegalArgumentException(
				"VirtualIintField.setRangeLow(int):  given value = " + i +
				" is greater than high value for range = " + rangeHigh);
		}
		rangeLow = i;
	}
	
	public int getRangeLow() {
		return rangeLow;
	}

	
	/*
	 * For reporting on the contents of the virtual field
	 */
	
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecialValue  = " + getHasSpecialValue());
		System.out.println(indent + "specialValue     = " + getSpecialValue());
		System.out.println(indent + "defaultVallue    = " + getDefaultValue());
	}
	
}
