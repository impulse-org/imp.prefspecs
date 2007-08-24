/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.compiler.codegen;

import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.imp.preferences.PreferencesTab;
import org.eclipse.imp.preferences.TabbedPreferencesPage;
import org.eclipse.imp.preferences.fields.FieldEditor;
import org.eclipse.swt.widgets.Composite;

public interface IPreferencesFactory
{

//	public IFile generatePreferencesConstants(
//		String qualifiedClassName, String packageFolderName, Map subs, ISourceProject project, 	IProgressMonitor mon);
//
//	public IFile generatePreferencesInitializers(
//			String qualifiedClassName, String packageFolderName, Map subs, ISourceProject project, 	IProgressMonitor mon);


	//public SafariFieldEditor[] createFields(Composite parent, String tab);

	public FieldEditor[] createFields(
			TabbedPreferencesPage page,
			PreferencesTab tab,
			String level,
			Composite parent,
			IPreferencesService prefsService);
	
}
