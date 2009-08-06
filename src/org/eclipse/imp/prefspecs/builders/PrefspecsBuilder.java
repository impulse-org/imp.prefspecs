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

package org.eclipse.imp.prefspecs.builders;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.builder.BuilderBase;
import org.eclipse.imp.language.Language;
import org.eclipse.imp.language.LanguageRegistry;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;
import org.eclipse.imp.prefspecs.compiler.PrefspecsCompiler;
import org.eclipse.imp.runtime.PluginBase;

/**
 * @author
 */
public class PrefspecsBuilder extends BuilderBase {
    protected static final String PREFSPECS_CONSOLE_NAME= "Pref Specs Builder";

    /**
     * Extension ID of the Prefspecs builder. Must match the ID in the corresponding
     * extension definition in plugin.xml.
     * SMS 22 Mar 2007:  If that ID is set through the NewBuilder wizard, then so must this one be.
     */
	// SMS 28 Mar 2007:  Make plugin class name totally parameterized
	public static final String BUILDER_ID= PrefspecsPlugin.kPluginID + ".builder";

	// SMS 28 Mar 2007:  Make problem id parameterized (rather than just ".problem") so that
	// it can be given a build-specific value (not simply composed here using the builder id
	// because the problem id is also needed in ExtensionPointEnabler for adding the marker
	// extension to the plugin.xml file)
    public static final String PROBLEM_MARKER_ID= PrefspecsPlugin.kPluginID + ".problem";
    
    public static final Language LANGUAGE = LanguageRegistry.findLanguage(PrefspecsPlugin.kLanguageID);

    @Override
    protected PluginBase getPlugin() {
        return PrefspecsPlugin.getInstance();
    }

    @Override
    protected String getErrorMarkerID() {
        return PROBLEM_MARKER_ID;
    }

    @Override
    protected String getWarningMarkerID() {
        return PROBLEM_MARKER_ID;
    }

    @Override
    protected String getInfoMarkerID() {
        return PROBLEM_MARKER_ID;
    }


    // SMS 11 May 2006
    // Incorporated realistic handling of filename extensions
    // using information recorded in the language registry
    @Override
    protected boolean isSourceFile(IFile file) {
        IPath path= file.getRawLocation();
        if (path == null) return false;

        String pathString = path.toString();
        if (pathString.indexOf("/bin/") != -1) return false;
        
        return LANGUAGE.hasExtension(path.getFileExtension());
    }

    /**
     * @return true iff the given file is a source file that this builder should scan
     * for dependencies, but not compile as a top-level compilation unit.<br>
     * <code>isNonRootSourceFile()</code> and <code>isSourceFile()</code> should never
     * return true for the same file.
     */
    @Override
    protected boolean isNonRootSourceFile(IFile resource) {
        // Nothing to do - the prefspecs language does not define anything like "include" files.
        return false;
    }

    @Override
    protected boolean isOutputFolder(IResource resource) {
        return resource.getFullPath().lastSegment().equals("bin");
    }

    /**
     * Collects compilation-unit dependencies for the given file, and records
     * them via calls to <code>fDependency.addDependency()</code>.
     */
    @Override
    protected void collectDependencies(IFile file) {
        // Nothing to do - the prefspecs language does not support inter-file references.
        return;
    }

    @Override
    protected String getConsoleName() {
        return PREFSPECS_CONSOLE_NAME;
    }

    @Override
    protected void compile(final IFile file, IProgressMonitor monitor) {
        try {
            getConsoleStream().println("Builder.compile with file = " + file.getName());

            PrefspecsCompiler compiler= new PrefspecsCompiler(PROBLEM_MARKER_ID, getConsoleStream());

            compiler.compile(file, monitor);

            // TODO Should actually refresh the folder corresponding to the prefspecs file's declared package...
            doRefresh(file.getParent());
        } catch (Exception e) {
            getPlugin().writeErrorMsg(e.getMessage());

            e.printStackTrace();
        }
    }
}
