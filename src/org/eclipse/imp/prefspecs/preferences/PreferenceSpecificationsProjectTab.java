package org.eclipse.imp.prefspecs.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.imp.preferences.ProjectPreferencesTab;
import org.eclipse.imp.preferences.TabbedPreferencesPage;
import org.eclipse.imp.preferences.fields.BooleanFieldEditor;
import org.eclipse.imp.preferences.fields.FieldEditor;
import org.eclipse.imp.preferences.fields.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.osgi.service.prefs.Preferences;


/**
 * The project level preferences tab.
 */
public class PreferenceSpecificationsProjectTab extends ProjectPreferencesTab {

	public PreferenceSpecificationsProjectTab(IPreferencesService prefService) {
		super(prefService, false);
	}

	/**
	 * Creates specific preference fields with settings appropriate to
	 * the project preferences level.
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
			"project", "tabWidth", "tab width",
			"",
			parent,
			false, false,
			false, "0",
			true);
		fields.add(tabWidth);

		Link tabWidthDetailsLink = fPrefUtils.createDetailsLink(parent, tabWidth, tabWidth.getTextControl().getParent(), "Details ...");

		tabWidthDetailsLink.setEnabled(false);
		fDetailsLinks.add(tabWidthDetailsLink);


		BooleanFieldEditor spacesForTabs = fPrefUtils.makeNewBooleanField(
			page, this, fPrefService,
			"project", "spacesForTabs", "spaces for tabs",
			"",
			parent,
			false, false,
			false, false,
			true);
		fields.add(spacesForTabs);

		Link spacesForTabsDetailsLink = fPrefUtils.createDetailsLink(parent, spacesForTabs, spacesForTabs.getChangeControl().getParent(), "Details ...");

		spacesForTabsDetailsLink.setEnabled(false);
		fDetailsLinks.add(spacesForTabsDetailsLink);


		BooleanFieldEditor emitBuildDiagnostics = fPrefUtils.makeNewBooleanField(
			page, this, fPrefService,
			"project", "emitBuildDiagnostics", "emit build diagnostics",
			"",
			parent,
			false, false,
			false, false,
			true);
		fields.add(emitBuildDiagnostics);

		Link emitBuildDiagnosticsDetailsLink = fPrefUtils.createDetailsLink(parent, emitBuildDiagnostics, emitBuildDiagnostics.getChangeControl().getParent(), "Details ...");

		emitBuildDiagnosticsDetailsLink.setEnabled(false);
		fDetailsLinks.add(emitBuildDiagnosticsDetailsLink);

		return fields.toArray(new FieldEditor[fields.size()]);
	}


	protected void addressProjectSelection(IPreferencesService.ProjectSelectionEvent event, Composite composite)
	{
		boolean haveCurrentListeners = false;

		Preferences oldNode = event.getPrevious();
		Preferences newNode = event.getNew();

		if (oldNode == null && newNode == null) {
			// Happens sometimes when you clear the project selection.
			// Nothing, really, to do in this case ...
			return;
		}

		// If oldeNode is not null, we want to remove any preference-change listeners from it
		if (oldNode != null && oldNode instanceof IEclipsePreferences && haveCurrentListeners) {
			removeProjectPreferenceChangeListeners();
			haveCurrentListeners = false;
		} else {
			// Print an advisory message if you want to
		}

		// Declare local references to the fields
		IntegerFieldEditor tabWidth = (IntegerFieldEditor) fFields[0];
		Link tabWidthDetailsLink = (Link) fDetailsLinks.get(0);
		BooleanFieldEditor spacesForTabs = (BooleanFieldEditor) fFields[1];
		Link spacesForTabsDetailsLink = (Link) fDetailsLinks.get(1);
		BooleanFieldEditor emitBuildDiagnostics = (BooleanFieldEditor) fFields[2];
		Link emitBuildDiagnosticsDetailsLink = (Link) fDetailsLinks.get(2);

		// If we have a new project preferences node, then do various things
		// to set up the project's preferences
		if (newNode != null && newNode instanceof IEclipsePreferences) {
			// If the containing composite is not disposed, then set field values
			// and make them enabled and editable (as appropriate to the type of field)

			if (!composite.isDisposed()) {
				// Note:  Where there are toggles between fields, it is a good idea to set the
				// properties of the dependent field here according to the values they should have
				// based on the independent field.  There should be listeners to take care of 
				// that sort of adjustment once the tab is established, but when properties are
				// first initialized here, the properties may not always be set correctly through
				// the toggle.  I'm not entirely sure why that happens, except that there may be
				// a race condition between the setting of the dependent values by the listener
				// and the setting of those values here.  If the values are set by the listener
				// first (which might be surprising, but may be possible) then they will be
				// overwritten by values set here--so the values set here should be consistent
				// with what the listener would set.

				fPrefUtils.setField(tabWidth, tabWidth.getHolder());
				tabWidth.getTextControl().setEditable(true);
				tabWidth.getTextControl().setEnabled(true);
				tabWidth.setEnabled(true, tabWidth.getParent());
				tabWidthDetailsLink.setEnabled(selectedProjectCombo.getText().length() > 0);

				fPrefUtils.setField(spacesForTabs, spacesForTabs.getHolder());
				spacesForTabs.getChangeControl().setEnabled(true);
				spacesForTabsDetailsLink.setEnabled(selectedProjectCombo.getText().length() > 0);

				fPrefUtils.setField(emitBuildDiagnostics, emitBuildDiagnostics.getHolder());
				emitBuildDiagnostics.getChangeControl().setEnabled(true);
				emitBuildDiagnosticsDetailsLink.setEnabled(selectedProjectCombo.getText().length() > 0);

				clearModifiedMarksOnLabels();
			}

			// Add property change listeners
			if (tabWidth.getHolder() != null) addProjectPreferenceChangeListeners(tabWidth, "tabWidth", tabWidth.getHolder());
			if (spacesForTabs.getHolder() != null) addProjectPreferenceChangeListeners(spacesForTabs, "spacesForTabs", spacesForTabs.getHolder());
			if (emitBuildDiagnostics.getHolder() != null) addProjectPreferenceChangeListeners(emitBuildDiagnostics, "emitBuildDiagnostics", emitBuildDiagnostics.getHolder());

			haveCurrentListeners = true;
		}

		// Or if we don't have a new project preferences node ...
		if (newNode == null || !(newNode instanceof IEclipsePreferences)) {
			// May happen when the preferences page is first brought up, or
			// if we allow the project to be deselected\nn			// Clear the preferences from the store
			fPrefService.clearPreferencesAtLevel(IPreferencesService.PROJECT_LEVEL);

			// Disable fields and make them non-editable
			if (!composite.isDisposed()) {
				tabWidth.getTextControl().setEditable(false);
				tabWidth.getTextControl().setEnabled(false);
				tabWidth.setEnabled(false, tabWidth.getParent());

				spacesForTabs.getChangeControl().setEnabled(false);

				emitBuildDiagnostics.getChangeControl().setEnabled(false);

			}

			// Remove listeners
			removeProjectPreferenceChangeListeners();
			haveCurrentListeners = false;
			// To help assure that field properties are established properly
			performApply();
		}
	}


}
