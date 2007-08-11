package org.eclipse.imp.prefspecs.pageinfo;

public class ConcreteIntFieldInfo
	extends ConcreteFieldInfo
{
	
	
	/*
	 * Local copy of the virtual field on which this concrete
	 * field is based.  The purpose of the copy is to avoid the
	 * need to repeatedly typecast the copy of the field contained
	 * in the supertype.
	 */
	protected VirtualIntFieldInfo vIntFieldInfo = null;
	
	
	/*
	 * Fields that can be set in a specific concrete int field
	 * independently of the corresponding virtual field
	 */
	

	/**
	 * A "special" value that may be associated
	 * with a concrete instance of this field
	 */
	int specialValue = 0;
	
	
	/**
	 * Values that delimit the range that the field may
	 * take on
	 */
	protected int rangeHigh = Integer.MAX_VALUE;
	protected int rangeLow 	= Integer.MIN_VALUE	;
	
	
	/**
	 * Constructor to use for concrete fields that are associated
	 * with a virtual, as happens during the processing of the prefspecs
	 * AST and the elaboration of field attributes.
	 * 
	 * @param vField
	 * @param parentTab
	 */
	public ConcreteIntFieldInfo(
		VirtualIntFieldInfo vField, PreferencesTabInfo parentTab)
	{
		// Super gets page and name from associated virtual field;
		// will serve as a check that given values aren't null
		super(vField, parentTab);
	}

	
	/**
	 * Constructor to use for concrete fields that don't need to be
	 * associated with a virtual field, as happens when the field
	 * attributes are fully elaborated.
	 * 
	 * @param parentTab
	 */
//	public ConcreteIntFieldInfo(PreferencesTabInfo parentTab, String name)
//	{
//		super(parentTab, name);
//	}
//	

	
	public PreferencesTabInfo getParentTab() {
		return parentTab;
	}

	
	public int getDefaultValue() {
		return vIntFieldInfo.getDefaultValue();
	}
	
	
	public int getSpecialValue() {
		return specialValue;
	}

	
	public void setSpecialValue(int i) {
		if (!getHasSpecialValue())
			throw new IllegalArgumentException(
				"ConcreteIntFieldInfo.setSpecialValue(int):  attempt to set special value when field has no special value");
		if (i < getRangeLow() || i > getRangeHigh())
			throw new IllegalArgumentException(
				"ConcreteIntFieldInfo.setSpecialValue(int):  attempt to set special value = " + i +
				" outside of range = " + getRangeLow() + ".." + getRangeHigh());
		specialValue = i;
	}
	
	
	
	public void setRangeHigh(int i) {
		if (i < rangeLow) {
			throw new IllegalArgumentException(
				"VirtualIintField.setRangeHigh(int):  given value = " + i +
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

	

	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix) {
		super.dump(prefix);	
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecialValue = " + getHasSpecialValue());
		System.out.println(indent + "specialValue = " + getSpecialValue());
		System.out.println(indent + "rangeLow = " + getRangeLow());
		System.out.println(indent + "rangeHigh = " + getRangeHigh());
	}


}
