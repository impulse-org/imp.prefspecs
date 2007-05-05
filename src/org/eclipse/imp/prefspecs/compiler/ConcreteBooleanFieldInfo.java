package prefspecs.safari.compiler;

public class ConcreteBooleanFieldInfo
	extends VirtualBooleanFieldInfo
	implements IConcreteFieldInfo
{
	private VirtualBooleanFieldInfo vField = null;
	private PreferencesTabInfo parentTab = null;
	
	public ConcreteBooleanFieldInfo(
		VirtualBooleanFieldInfo vField, PreferencesTabInfo parentTab)
	{
		// Super gets page and name from associated virtual field;
		// will serve as a check that given values aren't null
		super(vField.getParentPage(), vField.getName());

		// All ConcreteFieldInfos must have a parentTabInfo
		if (parentTab == null) {
			throw new IllegalArgumentException(
				"ConcreteBooleanFieldInfo(..):  parent tab is null; not allowed");		
		}
		
		if (!parentPage.hasTab(parentTab)) {
			throw new IllegalArgumentException(
			"ConcreteBooleanFieldInfo(..):  parent tab does not belong to parent page; not allowed");	
		}

		// Get hooked up with the containing tab
		this.parentTab = parentTab;
		parentTab.add(this);	
	}

	
	public PreferencesTabInfo getParentTab() {
		return parentTab;
	}

	
	//
	// For reporting on the contents of the field
	//
	

}
