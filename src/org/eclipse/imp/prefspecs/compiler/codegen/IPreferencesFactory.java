/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation

*******************************************************************************/

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
