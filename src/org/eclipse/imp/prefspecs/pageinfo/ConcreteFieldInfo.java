package org.eclipse.uide.preferences.pageinfo;

import org.eclipse.uide.preferences.ISafariPreferencesService;
import org.eclipse.uide.preferences.SafariTabbedPreferencesPage;


public class ConcreteFieldInfo
{
	/**
	 * The virtual field for which the concrete field
	 * is a representation
	 */
	protected VirtualFieldInfo vField = null;
	
	
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
	private boolean isEditable = true;
	
	/**
	 * Whether instances of this field (that is, their values)
	 * can be removed by default
	 */
	private boolean isRemovable = false;


	
	public ConcreteFieldInfo(VirtualFieldInfo vField, PreferencesTabInfo parentTab)
	{
		if (vField == null) {
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
		this.vField = vField;
		this.name = vField.getName();
		parentTab.add(this);
	}
	
	
	public ConcreteFieldInfo(PreferencesTabInfo parentTab, String name) {
		if (parentTab == null) {
			throw new IllegalArgumentException(
				"ConcreteFieldInfo(..):  name is null; not allowed");		
		}
		if (parentTab == null) {
			throw new IllegalArgumentException(
				"ConcreteFieldInfo(..):  parent tab is null; not allowed");		
		}
		
		// Okay
		this.parentTab = parentTab;
		this.name = name;
		parentTab.add(this);	// why not???
		
	}
	
	
	
	// TODO:  Consider adding other constructors to enable attribute values to be set
	
	//
	// Name is only defined through the constuctor,
	// so only "get" methods are defined for those
	//
	
	public PreferencesTabInfo getParentTab() {
		return parentTab;
	}
	
	public String getName() {
		return name;
	}
	
	

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	public boolean getIsEditable() {
		return 	isEditable;
	}

	/**
	 * Constraint:  For the default tab isRemovable	 must be false
	 * 
	 * @param isRemovable
	 */
	public void setIsRemovable(boolean isRemovable) {
		if (getName().equals(ISafariPreferencesService.DEFAULT_LEVEL))
			if (isRemovable) {
				throw new IllegalArgumentException(
					"ConcreteFieldInfo.setIsRemovable(..):  cannot set isRemovable ");
			}
		this.isRemovable = isRemovable;
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
		if (getName().equals(ISafariPreferencesService.DEFAULT_LEVEL))
			return false;
		return isRemovable;
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
	}
	
}
