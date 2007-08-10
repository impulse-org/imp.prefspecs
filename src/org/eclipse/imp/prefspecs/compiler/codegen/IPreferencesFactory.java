package org.eclipse.imp.prefspecs.compiler.codegen;

import org.eclipse.imp.preferences.ISafariPreferencesService;
import org.eclipse.imp.preferences.SafariPreferencesTab;
import org.eclipse.imp.preferences.SafariTabbedPreferencesPage;
import org.eclipse.imp.preferences.fields.SafariFieldEditor;
import org.eclipse.swt.widgets.Composite;

public interface IPreferencesFactory
{

//	public IFile generatePreferencesConstants(
//		String qualifiedClassName, String packageFolderName, Map subs, ISourceProject project, 	IProgressMonitor mon);
//
//	public IFile generatePreferencesInitializers(
//			String qualifiedClassName, String packageFolderName, Map subs, ISourceProject project, 	IProgressMonitor mon);


	//public SafariFieldEditor[] createFields(Composite parent, String tab);
	public SafariFieldEditor[] createFields(
			SafariTabbedPreferencesPage page,
			SafariPreferencesTab tab,
			String level,
			Composite parent,
			ISafariPreferencesService prefsService);
	
}
