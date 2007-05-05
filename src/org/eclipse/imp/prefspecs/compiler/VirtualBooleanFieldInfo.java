package prefspecs.safari.compiler;

public class VirtualBooleanFieldInfo extends VirtualFieldInfo {

	/**
	 * Whether a concrete instance of the boolean field
	 * can by default take on on a "special" value
	 */
	protected boolean hasSpecial = false;
	
	/**
	 * A defualt "special" value that may be associated
	 * with a concrete instance of this field
	 */
	protected boolean special = false;
	
	
	public VirtualBooleanFieldInfo(PreferencesPageInfo parentPage, String name) {
		super(parentPage, name);
	}
	
	
	public void setHasSpecial(boolean b) {
		hasSpecial = b;
	}
	
	public boolean getHasSpecial(boolean b) {
		return hasSpecial;
	}

	
	
	public void setSpecial(boolean b) {
		special = b;
	}
	
	public boolean getSpecial(boolean b) {
		return special;
	}
	
	
	//
	// For reporting on the contents of the virtual field
	//
	
	public void dump(String prefix) {
		super.dump(prefix);
		String indent = prefix + "  ";
		System.out.println(indent + "hasSpecial  = " + hasSpecial);
		System.out.println(indent + "special     = " + special);
	}
	
}
