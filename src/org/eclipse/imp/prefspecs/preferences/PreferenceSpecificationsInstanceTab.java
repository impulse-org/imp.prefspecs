package org.eclipse.imp.prefspecs.preferences;

import java.util.List;
import java.util.ArrayList;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.imp.preferences.*;
import org.eclipse.imp.preferences.fields.*;
import org.osgi.service.prefs.Preferences;


/**
 * The instance level preferences tab.
 */
public class PreferenceSpecificationsInstanceTab extends InstancePreferencesTab {

	public PreferenceSpecificationsInstanceTab(IPreferencesService prefService) {
		super(prefService, false);
	}

	/**
	 * Creates specific preference fields with settings appropriate to
	 * the instance preferences level.
	 *
	 * Overrides an unimplemented method in PreferencesTab.
	 *
	 * @return    An array that contains the created preference fields
	 *
	 */
	protected FieldEditor[] createFields(TabbedPreferencesPage page, Composite parent)
	{
		List<FieldEditor> fields = new ArrayList<FieldEditor>();

		IntegerFieldEditor tabWidth = fPrefUtils.makeNewIntegerField(
			page, this, fPrefService,
			"instance", "tabWidth", "tab width",
			"",
			parent,
			true, true,
			false, "0",
			true);
		fields.add(tabWidth);

		Link tabWidthDetailsLink = fPrefUtils.createDetailsLink(parent, tabWidth, tabWidth.getTextControl().getParent(), "Details ...");

		tabWidthDetailsLink.setEnabled(true);
		fDetailsLinks.add(tabWidthDetailsLink);


		BooleanFieldEditor spacesForTabs = fPrefUtils.makeNewBooleanField(
			page, this, fPrefService,
			"instance", "spacesForTabs", "spaces for tabs",
			"",
			parent,
			true, true,
			false, false,
			true);
		fields.add(spacesForTabs);

		Link spacesForTabsDetailsLink = fPrefUtils.createDetailsLink(parent, spacesForTabs, spacesForTabs.getChangeControl().getParent(), "Details ...");

		spacesForTabsDetailsLink.setEnabled(true);
		fDetailsLinks.add(spacesForTabsDetailsLink);


		BooleanFieldEditor emitBuildDiagnostics = fPrefUtils.makeNewBooleanField(
			page, this, fPrefService,
			"instance", "emitBuildDiagnostics", "emit build diagnostics",
			"",
			parent,
			true, true,
			false, false,
			true);
		fields.add(emitBuildDiagnostics);

		Link emitBuildDiagnosticsDetailsLink = fPrefUtils.createDetailsLink(parent, emitBuildDiagnostics, emitBuildDiagnostics.getChangeControl().getParent(), "Details ...");

		emitBuildDiagnosticsDetailsLink.setEnabled(true);
		fDetailsLinks.add(emitBuildDiagnosticsDetailsLink);

		return fields.toArray(new FieldEditor[fields.size()]);
	}
}
