package org.eclipse.imp.prefspecs.pageinfo;

public class ConcreteBooleanFieldInfo
	extends ConcreteFieldInfo
{
	/*
	 * Local copy of the virtual field on which this concrete
	 * field is based.  The purpose of the copy is to avoid the
	 * need to repeatedly typecast the copy of the field contained
	 * in the supertype.
	 */
	protected VirtualBooleanFieldInfo vBooleanFieldInfo = null;
	
	
	/*
	 * Fields that can be set in a specific concrete boolean field
	 * independently of the corresponding virtual field
	 */
	

	/**
	 * A "special" value that may be associated
	 * with a concrete instance of this field
	 */
	boolean specialValue = true;
	
	
	/**
	 * Constructor to use for concrete fields that are associated
	 * with a virtual, as happens during the processing of the prefspecs
	 * AST and the elaboration of field attributes.
	 * 
	 * @param vField
	 * @param parentTab
	 */
	public ConcreteBooleanFieldInfo(
		VirtualBooleanFieldInfo vBooleanFieldInfo, PreferencesTabInfo parentTab)
	{
		// Super gets page and name from associated virtual field;
		// will serve as a check that given values aren't null
		super(vBooleanFieldInfo, parentTab);
		this.vBooleanFieldInfo = vBooleanFieldInfo;
		this.specialValue = vBooleanFieldInfo.getSpecialValue();
	}

	
	/**
	 * Constructor to use for concrete fields that don't need to be
	 * associated with a virtual field, as happens when the field
	 * attributes are fully elaborated.
	 * 
	 * @param parentTab
	 */
//	public ConcreteBooleanFieldInfo(PreferencesTabInfo parentTab, String name)
//	{
//		super(parentTab, name);
//	}

	
	public boolean getDefaultValue() {
		return vBooleanFieldInfo.getDefaultValue();
	}

	
	public boolean getSpecialValue() {
		return specialValue;
	}
	
	public void setSpecialValue(boolean b) {
		if (!getHasSpecialValue())
			throw new IllegalStateException(
				"ConcreteBooleanFieldInfo.setSpecial(boolean):  attempt to set special value when field has no special value");
		vBooleanFieldInfo.setSpecialValue(b);
	}
	

	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix) {
		super.dump(prefix);	
		String indent = prefix + "  ";
		System.out.println(indent + "default value = " + getDefaultValue());
		if (getHasSpecialValue()) {
			System.out.println(indent + "special = " + getSpecialValue());
		} else {
			System.out.println(indent + "no special value defined");
		}
	}


}
