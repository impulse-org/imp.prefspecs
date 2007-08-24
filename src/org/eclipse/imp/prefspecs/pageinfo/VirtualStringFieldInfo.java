/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.pageinfo;

public class VirtualStringFieldInfo extends VirtualFieldInfo {

	/**
	 * Whether a concrete instance of the field
	 * can take on on a "special" value
	 */
	protected boolean hasSpecialValue = false;
	
	/**
	 * The "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected String specialValue = null;
	
	
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
		this.hasSpecialValue = hasSpecial;
		this.specialValue = special;
	}
	
	
	
	public void setDefaultValue(String s) {
		defaultValue = s;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	
	public void setSpecialValue(String s) {
		specialValue = s;
	}
	
	public String getSpecialValue() {
		return specialValue;
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
		System.out.println(indent + "hasSpecial  = " + hasSpecialValue);
		System.out.println(indent + "special     = " + specialValue);
		System.out.println(indent + "defaultValue    = " + defaultValue);
	}
	
}
