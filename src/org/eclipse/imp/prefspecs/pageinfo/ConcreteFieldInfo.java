package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.preferences.IPreferencesService;


public class ConcreteFieldInfo
{
	/**
	 * The virtual field for which the concrete field
	 * is a representation
	 */
	protected VirtualFieldInfo vFieldInfo = null;
	
	
	/**
	 * The preferences tab on which this field occurs
	 */
	protected PreferencesTabInfo parentTab = null;
	

	/**
	 * The name of the field
	 */
	protected String name = null;
	
	/**
	 * Whether instances of this field can be edited by default
	 */
	protected boolean isEditable = true;
	
	/**
	 * Whether instances of this field (that is, their values)
	 * can be removed by default
	 */
	protected boolean isRemovable = false;


	/**
	 * Whether instances of this field have a "special" value
	 */
	protected boolean hasSpecialValue = false;
	
	
	
	public ConcreteFieldInfo(VirtualFieldInfo vFieldInfo, PreferencesTabInfo parentTab)
	{
		if (vFieldInfo == null) {
			throw new IllegalArgumentException(
				"ConcreteFieldInfo(..	):  virtual field is null; not allowed");		
		}
		if (parentTab == null) {
			throw new IllegalArgumentException(
				"ConcreteFieldInfo(..):  parent tab is null; not allowed");		
		}
		
		// Don't worry about other conditions, such as uniqueness of field
		// name within page; may be checked before or after this point
		
		// Okay
		this.parentTab = parentTab;
		this.vFieldInfo = vFieldInfo;
		this.name = vFieldInfo.getName();
		
		this.isEditable = vFieldInfo.getIsEditable();
		this.isRemovable = vFieldInfo.getIsRemovable();
		this.hasSpecialValue = vFieldInfo.getHasSpecialValue();
			
		parentTab.add(this);
	}
	
	
//	public ConcreteFieldInfo(PreferencesTabInfo parentTab, String name) {
//		if (parentTab == null) {
//			throw new IllegalArgumentException(
//				"ConcreteFieldInfo(..):  name is null; not allowed");		
//		}
//		if (parentTab == null) {
//			throw new IllegalArgumentException(
//				"ConcreteFieldInfo(..):  parent tab is null; not allowed");		
//		}
//		
//		// Okay
//		this.parentTab = parentTab;
//		this.name = name;
//		parentTab.add(this);	// why not???
//		
//	}
	
	
	
	// TODO:  Consider adding other constructors to enable attribute values to be set
	
	//
	// Name and parent tab are only defined through the constuctor,
	// so only "get" methods are defined for those
	//
	
	public PreferencesTabInfo getParentTab() {
		return parentTab;
	}
	
	public String getName() {
		return name;
	}
	
	
	
	public boolean getIsEditable() {
		return 	isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}


	
	/**
	 * Whether the values of fields on this tab can, by default, be
	 * removed.  Removal of a field value on one tab triggers inheritance
	 * of the value from the corresponding field on the next higher level.
	 * Individual fields may override this setting, except that it is
	 * always false on the default tab (from which there is no higher tab).
	 * 
	 * @return	False for the default tab; the set value of isRemovable otherwise
	 */
	public boolean getIsRemovable() {
		if (getName().equals(IPreferencesService.DEFAULT_LEVEL))
			return false;
		return isRemovable;
	}
	
	
	/**
	 * Constraint:  For the default tab isRemovable	 must be false
	 * 
	 * @param isRemovable
	 */
	public void setIsRemovable(boolean isRemovable) {
		if (getName().equals(IPreferencesService.DEFAULT_LEVEL))
			if (isRemovable) {
				throw new IllegalArgumentException(
					"ConcreteFieldInfo.setIsRemovable(..):  cannot set isRemovable ");
			}
		this.isRemovable = isRemovable;
	}
	
	
	public boolean getHasSpecialValue() {
		return 	hasSpecialValue;
	}

	public void setHasSpecialValue(boolean hasSpecialValue) {
		this.hasSpecialValue = hasSpecialValue;
	}


	
	
	//
	// For reporting on the contents of the field
	//
	
	public void dump(String prefix) {
		String indent = prefix + "  ";
		System.out.println(prefix + "Field '" + getName() + "'");
		System.out.println(indent + "parent page = " + getParentTab().getName());
		System.out.println(indent + "isEditable  = " + isEditable);
		System.out.println(indent + "isRemovable = " + isRemovable);
		System.out.println(indent + "hasSpecial = " + hasSpecialValue);
	}
	
}
