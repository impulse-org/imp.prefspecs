package org.eclipse.imp.prefspecs.compiler.codegen;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uide.model.ISourceProject;
import org.eclipse.uide.preferences.fields.SafariFieldEditor;

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
