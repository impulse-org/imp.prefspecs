package org.eclipse.imp.prefspecs.pageinfo;

public class ConcreteBooleanFieldInfo
	extends ConcreteFieldInfo
{
	
	/**
	 * Constructor to use for concrete fields that are associated
	 * with a virtual, as happens during the processing of the prefspecs
	 * AST and the elaboration of field attributes.
	 * 
	 * @param vField
	 * @param parentTab
	 */
	public ConcreteBooleanFieldInfo(
		VirtualBooleanFieldInfo vField, PreferencesTabInfo parentTab)
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
	public ConcreteBooleanFieldInfo(PreferencesTabInfo parentTab, String name)
	{
		super(parentTab, name);
	}
	

	
	public PreferencesTabInfo getParentTab() {
		return parentTab;
	}


	/**
	 * Whether a concrete instance of the boolean field
	 * can by default take on on a "special" value
	 */
	protected boolean hasSpecialValue = false;
	
	/**
	 * A defualt "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected boolean specialVallue = false;
	
	
	public boolean getHasSpecialValue() {
		return hasSpecialValue;
	}
	
	
	public void setHasSpecialValue(boolean b) {
		hasSpecialValue = b;
	}
	
	
	public boolean getSpecialValue() {
		return specialVallue;
	}
	
	public void setSpecialValue(boolean b	) {
		specialVallue = b;
	}
	

	//
	// For reporting on the contents of the field
	//
	public void dump(String prefix) {
		super.dump(prefix);	
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecial = " + hasSpecialValue);
		System.out.println(indent + "special = " + specialVallue);
	}


}
