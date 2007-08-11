/**
 * 
 */
package org.eclipse.imp.prefspecs.pageinfo;

/**
 * @author sutton
 *
 */
public class VirtualFileFieldInfo extends VirtualStringFieldInfo {

	
	public VirtualFileFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualFileFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public VirtualFileFieldInfo(PreferencesPageInfo parentPage, String name,
			String defValue, boolean hasSpecial, String special)
	{
		this(parentPage, name, defValue);
		this.hasSpecial = hasSpecial;
		this.special = special;
	}
	

	
	
}
