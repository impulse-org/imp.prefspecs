package prefspecs.safari.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PreferencesPageInfo {

	private String name = null;

	private List<PreferencesTabInfo> tabs = new ArrayList(4);

	private List<VirtualFieldInfo> virtualFields = new ArrayList();
	
	
	public PreferencesPageInfo(String name) {
		this.name = name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

	public void addTab(PreferencesTabInfo tab) {
		if (tab == null || tabs	.contains(tab))
			return;
		tabs.add(tab);
	}
	
	public void removeTab(PreferencesTabInfo tab) {
		if (tab == null)
			return;
		tabs.remove(tab);
	}
	
	public Iterator getTabs() {
		return tabs.iterator(); 
	}
	
	public PreferencesTabInfo getTab(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.getTab(String):  given string is null; not allowed");
		}
		for (int i = 0; i < tabs.size(); i++) {
			PreferencesTabInfo tab = tabs.get(i);
			if (tab == null) continue;
			String tabName = tab.getName();
			if (name.equals(name))
				return tab;
		}
		return null;
	}
	
	public boolean hasTab(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.hasTab(String):  given string is null; not allowed");
		}	
		for (int i = 0; i < tabs.size(); i++) {
			PreferencesTabInfo tab = tabs.get(i);
			if (tab == null) continue;
			String tabName = tab.getName();
			if (name.equals(tabName))
				return true;
		}
		return false;
	}

	public boolean hasTab(PreferencesTabInfo tab) {
		if (tab == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.hasTab(PreferencesTabInfo):  given tab is null; not allowed");
		}
		return tabs.contains(tab);
	}
	
	
	public void addVirtualField(VirtualFieldInfo vField) {
		if (vField == null || virtualFields.contains(vField))
			return;
		virtualFields.add(vField);
	}
	
	public void removeVirtualField(VirtualFieldInfo vField) {
		if (vField == null)
			return;
		virtualFields.remove(vField);
	}
	
	public Iterator getVirtualFields() {
		return virtualFields.iterator(); 
	}
	
	public boolean hasVirtualField(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.hasVirtualField(String):  given name is null; not allowed");
		}
		for (int i = 0; i < virtualFields.size(); i++) {
			VirtualFieldInfo field = virtualFields.get(i);
			if (field == null) continue;
			String fieldName = field.getName();
			if (name.equals(fieldName))
				return true;
		}
		return false;
	}
	
	public boolean hasVirtualField(VirtualFieldInfo vField) {
		if (vField == null) {
			throw new IllegalArgumentException(
					"PreferencePageInfo.hasVirtualField(VirtualFieldInfo):  given field is null; not allowed");
		}
		return virtualFields.contains(vField);
	}
	
	
	
	//
	// For reporting on the contents of the page
	//

	public static String INDENT = "";
	
	public void dump() {
		String indent = "  ";
		System.out.println("\n\t%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("PreferencesPageInfo:  '" + getName() + "'");
		
		System.out.println("Virtual fields:");
		Iterator vFields = getVirtualFields();
		while (vFields.hasNext()) {
			((VirtualFieldInfo)vFields.next()).dump(indent);
		}

		System.out.println("Tabs:");
		Iterator tabs = getTabs();
		while (tabs.hasNext()) {
			((PreferencesTabInfo)tabs.next()).dump(indent);
		}
		System.out.println("\t%%%%\t%%%%\t%%%%\n");
	}
	
}
