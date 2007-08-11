package org.eclipse.imp.prefspecs.pageinfo;

import org.eclipse.imp.preferences.IPreferencesService;


public class VirtualFieldInfo
{
	/**
	 * The preferences page with which this virtual field
	 * is associated
	 */
	protected PreferencesPageInfo parentPage = null;
	
	/**
	 * The name of the virtual field
	 */
	protected String name = null;
	
	
	/**
	 * Whether concrete instances of this field be edited by default
	 */
	private boolean isEditable = true;
	
	/**
	 * Whether concrete instance of this field be removed by default
	 */
	private boolean isRemovable = false;



	public VirtualFieldInfo(PreferencesPageInfo parentPage, String name)
	{
		// All VirtualFieldInfos must have a parentPageInfo
		if (parentPage == null) {
			throw new IllegalArgumentException(
				"VirtualFieldInfo(..):  parent page is null; not allowed");		
		}
		
		// All VirtualFieldInfos must have a non-null name
		if (name == null) {
			throw new IllegalArgumentException(
				"PreferencesIabInfo(..):  name is null; not allowed");		
		}

		// Don't worry about other conditions, such as uniqueness of field
		// name within page; may be checked before or after this point
		
		// Okay
		this.parentPage = parentPage;
		this.name = name;
		parentPage.addVirtualFieldInfo(this);
	}
	
	
	// TODO:  Consider adding other constructors to enable attribute values to be set
	
	//
	// Name is only defined through the constuctor,
	// so only "get" methods are defined for those
	//
	
	public PreferencesPageInfo getParentPage() {
		return parentPage;
	}
	
	public String getName() {
		return name;
	}
	
	

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable	;
	}
	
	public boolean getIsEditable() {
		return 	isEditable;
	}

	/**
	 * Constraint:  For the default tab "isRemovable" must be false
	 * 
	 * @param isRemovable
	 */
	public void setIsRemovable(boolean isRemovable) {
		if (getName().equals(IPreferencesService.DEFAULT_LEVEL))
			if (isRemovable) {
				throw new IllegalArgumentException(
					"PreferenceIabInfo.setIsRemovable(..):  cannot set isRemovable ");
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
	 * @return	False for the default tab; the set value of isUsed otherwise
	 */
	public boolean getIsRemovable() {
		if (getName().equals(IPreferencesService.DEFAULT_LEVEL))
			return false;
		return isRemovable;
	}
	
	
	//
	// For reporting on the contents of the field
	//
	
	public void dump(String prefix) {
		String indent = prefix + "  ";
		System.out.println(prefix + "Field '" + getName() + "'");
		System.out.println(indent + "parent page = " + getParentPage().getPageName());
		System.out.println(indent + "isEditable  = " + isEditable);
		System.out.println(indent + "isRemovable = " + isRemovable);
	}
	
}
