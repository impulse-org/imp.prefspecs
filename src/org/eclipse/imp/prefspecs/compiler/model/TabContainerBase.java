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


public class TabContainerBase implements ITabContainer {
    protected List<TabInfo> fTabs;

    public TabContainerBase() {
        fTabs = new ArrayList<TabInfo>(4);
    }

    public void addTabInfo(TabInfo tab) {
        if (tab == null || fTabs.contains(tab))
            return;
        fTabs.add(tab);
    }
    
    public void removeTabInfo(TabInfo tab) {
        if (tab == null)
            return;
        fTabs.remove(tab);
    }
    
    public Iterator<TabInfo> getTabInfos() {
        return fTabs.iterator(); 
    }

    public TabInfo getTabInfo(String name) {
        if (name == null) {
            throw new IllegalArgumentException("PreferencePageInfo.getTab(String): given tab name is null; not allowed");
        }
        for (int i = 0; i < fTabs.size(); i++) {
            TabInfo tab = fTabs.get(i);
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
            TabInfo tab = fTabs.get(i);
            if (tab == null) continue;
            String tabName = tab.getName();
            if (name.equals(tabName))
                return true;
        }
        return false;
    }
}
