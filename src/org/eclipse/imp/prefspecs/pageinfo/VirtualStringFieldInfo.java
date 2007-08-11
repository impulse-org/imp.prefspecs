package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualStringFieldInfo extends VirtualFieldInfo {

	/**
	 * Whether a concrete instance of the field
	 * can take on on a "special" value
	 */
	protected boolean hasSpecial = false;
	
	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected String special = null;
	
	
	/**
	 * The default value associated with this field
	 * (used to set the value of the concrete instance of
	 * this field on the default level)
	 */
	protected String defaultValue = "";
	
	
	/**
	 * Whether a concrete instance of the field
	 * can take on a designated "empty" value
	 */
	protected boolean emptyValueAllowed = false;
	
	
	/**
	 * The designated "empty" value for this field
	 */
	protected String emptyValue = "";
	
	
	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
		this(parentPage, name);
		this.defaultValue = defValue;
	}

	public VirtualStringFieldInfo(PreferencesPageInfo parentPage, String name,
			String defValue, boolean hasSpecial, String special)
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

	
	public void setSpecial(String s) {
		special = s;
	}
	
	public String getSpecial() {
		return special;
	}
	
	
	public void setDefaultValue(String s) {
		defaultValue = s;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	
	public void setEmptyValueAllowed(boolean b) {
		emptyValueAllowed = b;
	}
	
	public boolean getEmptyValueAllowed() {
		return emptyValueAllowed;
	}

	
	public void setEmptyValue(String s) {
		if (!getHasSpecialValue())
			throw new IllegalStateException(
				"VirtualStringFieldInfo.setSpecialValue(String):  attempt to set special value when field has no special value");
		emptyValue = s;
	}
	
	public String getEmptyValue() {
		return emptyValue != null ? emptyValue : "";
	}
	

	
	/*
	 * For reporting on the contents of the virtual field
	 */
	
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecial  = " + hasSpecial);
		System.out.println(indent + "special     = " + special);
		System.out.println(indent + "defaultValue    = " + defaultValue);
	}
	
}
