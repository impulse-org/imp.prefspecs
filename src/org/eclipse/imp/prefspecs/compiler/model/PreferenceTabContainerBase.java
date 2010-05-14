/*******************************************************************************
* Copyright (c) 2010 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.compiler.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PreferenceTabContainerBase implements IPreferenceTabContainer {
    protected List<PreferencesTabInfo> fTabs;

    public PreferenceTabContainerBase() {
        fTabs = new ArrayList<PreferencesTabInfo>(4);
    }

    public void addTabInfo(PreferencesTabInfo tab) {
        if (tab == null || fTabs.contains(tab))
            return;
        fTabs.add(tab);
    }
    
    public void removeTabInfo(PreferencesTabInfo tab) {
        if (tab == null)
            return;
        fTabs.remove(tab);
    }
    
    public Iterator<PreferencesTabInfo> getTabInfos() {
        return fTabs.iterator(); 
    }

    public PreferencesTabInfo getTabInfo(String name) {
        if (name == null) {
            throw new IllegalArgumentException("PreferencePageInfo.getTab(String): given tab name is null; not allowed");
        }
        for (int i = 0; i < fTabs.size(); i++) {
            PreferencesTabInfo tab = fTabs.get(i);
            if (tab == null) continue;
            String tabName = tab.getName();
            if (name.equals(tabName))
                return tab;
        }
        return null;
    }

    public boolean hasTabInfo(String name) {
        if (name == null) {
            throw new IllegalArgumentException("PreferencePageInfo.hasTab(String): given tab name is null; not allowed");
        }   
        for (int i = 0; i < fTabs.size(); i++) {
            PreferencesTabInfo tab = fTabs.get(i);
            if (tab == null) continue;
            String tabName = tab.getName();
            if (name.equals(tabName))
                return true;
        }
        return false;
    }

    public boolean hasTabInfo(PreferencesTabInfo tab) {
        if (tab == null) {
            throw new IllegalArgumentException("PreferencePageInfo.hasTabInfo(): given tab is null; not allowed");
        }
        return fTabs.contains(tab);
    }
}
