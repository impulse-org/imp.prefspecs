/**
 * 
 */
package org.eclipse.imp.prefspecs.pageinfo;

/**
 * @author sutton
 *
 */
public class VirtualDirListFieldInfo extends VirtualStringFieldInfo {

	
	public VirtualDirListFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualDirListFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public VirtualDirListFieldInfo(PreferencesPageInfo parentPage, String name,
			String defValue, boolean hasSpecial, String special)
	{
		this(parentPage, name, defValue);
		this.hasSpecialValue = hasSpecial;
		this.specialValue = special;
	}
	

	
	
}
