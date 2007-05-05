package prefspecs.safari.compiler;

import java.util.Iterator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.uide.preferences.DefaultPreferencesTab;
import org.eclipse.uide.preferences.SafariPreferencesService;
import org.eclipse.uide.preferences.SafariPreferencesTab;
import org.eclipse.uide.preferences.SafariPreferencesUtilities;
import org.eclipse.uide.preferences.SafariTabbedPreferencesPage;
import org.eclipse.uide.preferences.fields.SafariBooleanFieldEditor;
import org.eclipse.uide.preferences.fields.SafariFieldEditor;

public class TabFieldsFactory {

	protected SafariPreferencesUtilities prefUtils;

	
	
	public static SafariFieldEditor[] giveMeTheFields(
		SafariTabbedPreferencesPage page,
		SafariPreferencesTab tab,
		SafariPreferencesService service,
		Composite composite, PreferencesPageInfo pageInfo)
	{
		// TODO:  Add checks on parameters
		
		if (tab instanceof DefaultPreferencesTab) 
			return createFieldsForDefaultLevel(composite, pageInfo.getTab(SafariPreferencesService.DEFAULT_LEVEL));	
	
		return null;
	}


	protected static SafariFieldEditor[] createFieldsForDefaultLevel(
		Composite composite, PreferencesTabInfo tabInfo)
	{
		SafariFieldEditor[] fields = null;		
		SafariFieldEditor field = null;
		
		Iterator fieldsInfo = tabInfo.getConcreteFields();
		while (fieldsInfo.hasNext()) {
			VirtualFieldInfo fieldInfo = (VirtualFieldInfo) fieldsInfo.next();
			if (fieldInfo instanceof ConcreteBooleanFieldInfo) {
				field = createBooleanFieldEditor(composite, (ConcreteBooleanFieldInfo) fieldInfo);
			}
			
		}
		
		return fields;
	}
	
	
	
	
	protected static SafariBooleanFieldEditor createBooleanFieldEditor(
		Composite composite, ConcreteBooleanFieldInfo fieldInfo)
	{
		/*
		 * Need:
		 * PreferencePage
		 * PreferenceTab
		 * PreferenceService
		 */
//		SafariBooleanFieldEditor result = 
//			SafariPreferencesUtilities.makeNewBooleanField(
//					prefPage, this,
//					prefService, ISafariPreferencesService.DEFAULT_LEVEL, PreferenceConstants.P_USE_DEFAULT_EXEC, "Use default generator executable?",
//					composite, true, true, true, PreferenceInitializer.getDefaultUseDefaultExecutable(), false, false, false);
		
		return null;
	}
	
	
}
