/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.builders;

import org.eclipse.core.resources.IProject;
import org.eclipse.imp.builder.ProjectNatureBase;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;
import org.eclipse.imp.runtime.IPluginLog;

import org.eclipse.imp.smapifier.builder.SmapiProjectNature;

public class PrefspecsNature extends ProjectNatureBase {
	// SMS 28 Mar 2007:  plugin class now totally parameterized
	public static final String k_natureID = PrefspecsPlugin.kPluginID + ".imp.nature";
 
    public String getNatureID() {
        return k_natureID;
    }

    public String getBuilderID() {
        return PrefspecsBuilder.BUILDER_ID;
    }
    

    public void addToProject(IProject project) {
        super.addToProject(project);
        new SmapiProjectNature("prefspecs").addToProject(project);
    };

    protected void refreshPrefs() {
        // TODO implement preferences and hook in here
    }

    public IPluginLog getLog() {
    	// SMS 28 Mar 2007:  plugin class now totally parameterized
        return PrefspecsPlugin.getInstance();
    }

    protected String getDownstreamBuilderID() {
        // TODO Auto-generated method stub
        return null;
    }
}
