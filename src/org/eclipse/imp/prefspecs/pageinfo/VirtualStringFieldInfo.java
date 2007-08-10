package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualStringFieldInfo extends VirtualFieldInfo {

	/**
	 * Whether a concrete instance of the boolean field
	 * can by default take on on a "special" value
	 */
	protected boolean hasSpecialValue = false;
	
	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected String specialValue = "";
	
	
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected String defaultValue = "";
	
	
	
	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name, String defaultValue) {
		this(parentPage, name);
		this.defaultValue = defaultValue;
	}

	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name,
			String defaultValue, boolean hasSpecial, String special)
	{
		this(parentPage, name, defaultValue);
		this.hasSpecialValue = hasSpecial;
		this.specialValue = special;
	}
	
	
	
	public void setHasSpecialValue(boolean b) {
		hasSpecialValue = b;
	}
	
	public boolean getHasSpecialValue() {
		return hasSpecialValue;
	}

	
	public void setSpecialValue(String s) {
		specialValue = s;
	}
	
	public String getSpecialValue() {
		return specialValue;
	}
	
	
	public void setDefaultValue(String s) {
		defaultValue = s;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	
	/*
	 * For reporting on the contents of the virtual field
	 */
	
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecial  = " + hasSpecialValue);
		System.out.println(indent + "special     = " + specialValue);
		System.out.println(indent + "defValue    = " + defaultValue);
	}
	
}
