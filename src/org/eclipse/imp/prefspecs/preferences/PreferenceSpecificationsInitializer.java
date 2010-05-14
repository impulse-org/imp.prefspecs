package org.eclipse.imp.prefspecs.preferences;

import org.eclipse.imp.preferences.PreferencesInitializer;
import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;

/**
 * Initializations of default values for preferences.
 */
public class PreferenceSpecificationsInitializer extends PreferencesInitializer {
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferencesService service = PrefspecsPlugin.getInstance().getPreferencesService();

		service.setIntPreference(IPreferencesService.DEFAULT_LEVEL, PreferenceSpecificationsConstants.P_TABWIDTH, 4);
		service.setBooleanPreference(IPreferencesService.DEFAULT_LEVEL, PreferenceSpecificationsConstants.P_SPACESFORTABS, false);
		service.setBooleanPreference(IPreferencesService.DEFAULT_LEVEL, PreferenceSpecificationsConstants.P_EMITBUILDDIAGNOSTICS, true);
	}

	/*
	 * Clear (remove) any preferences set on the given level.
	 */
	public void clearPreferencesOnLevel(String level) {
		IPreferencesService service = PrefspecsPlugin.getInstance().getPreferencesService();
		service.clearPreferencesAtLevel(level);

	}
}
