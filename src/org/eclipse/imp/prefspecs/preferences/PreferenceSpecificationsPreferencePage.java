/******************************************/
/* WARNING: GENERATED FILE - DO NOT EDIT! */
/******************************************/
package org.eclipse.imp.prefspecs.preferences;

import org.eclipse.swt.widgets.TabFolder;import org.eclipse.imp.preferences.IPreferencesService;import org.eclipse.imp.preferences.PreferencesInitializer;import org.eclipse.imp.preferences.PreferencesTab;import org.eclipse.imp.preferences.TabbedPreferencesPage;import org.eclipse.imp.prefspecs.PrefspecsPlugin;

/**
 * A preference page class.
 */
public class PreferenceSpecificationsPreferencePage extends TabbedPreferencesPage {
	public PreferenceSpecificationsPreferencePage() {
		super();
		prefService = PrefspecsPlugin.getInstance().getPreferencesService();
	}

	protected PreferencesTab[] createTabs(IPreferencesService prefService,
		TabbedPreferencesPage page, TabFolder tabFolder) {
		PreferencesTab[] tabs = new PreferencesTab[3];

		PreferenceSpecificationsConfigurationTab configurationTab = new PreferenceSpecificationsConfigurationTab(prefService);
		configurationTab.createTabContents(page, tabFolder);
		tabs[0] = configurationTab;

		PreferenceSpecificationsInstanceTab instanceTab = new PreferenceSpecificationsInstanceTab(prefService);
		instanceTab.createTabContents(page, tabFolder);
		tabs[1] = instanceTab;

		PreferenceSpecificationsProjectTab projectTab = new PreferenceSpecificationsProjectTab(prefService);
		projectTab.createTabContents(page, tabFolder);
		tabs[2] = projectTab;

		return tabs;
	}

	public PreferencesInitializer getPreferenceInitializer() {
		return new PreferenceSpecificationsInitializer();
	}
}
