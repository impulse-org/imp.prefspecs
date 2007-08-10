package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualBooleanFieldInfo extends VirtualFieldInfo {

	/**
	 * Whether a concrete instance of the boolean field
	 * can by default take on on a "special" value
	 */
	protected boolean hasSpecial = false;
	
	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected boolean special = false;
	
	
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected boolean defValue = true;
	
	
	
	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name, boolean defValue) {
		this(parentPage, name);
		this.defValue = defValue;
	}

	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name,
			boolean defValue, boolean hasSpecial, boolean special)
	{
		this(parentPage, name, defValue);
		this.hasSpecial = hasSpecial;
		this.special = special;
	}
	
	
	
	public void setHasSpecial(boolean b) {
		hasSpecial = b;
	}
	
	public boolean getHasSpecial() {
		return hasSpecial;
	}

	
	public void setSpecial(boolean b) {
		special = b;
	}
	
	public boolean getSpecial() {
		return special;
	}
	
	
	public void setDefValue(boolean b) {
		defValue = b;
	}
	
	public boolean getDefValue() {
		return defValue;
	}
	
	
	/*
	 * For reporting on the contents of the virtual field
	 */
	
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecial  = " + hasSpecial);
		System.out.println(indent + "special     = " + special);
		System.out.println(indent + "defValue    = " + defValue);
	}
	
}
