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

import java.util.Iterator;

import org.eclipse.imp.preferences.IPreferencesService;

/**
 * This represents an entity that can contain a tabs specification, e.g., a
 * page or the top-level preferences specification itself.
 * @author rfuhrer
 */
public interface ITabContainer {
    /**
     * @param tabName one of {@link IPreferencesService#DEFAULT_LEVEL},
     * {@link IPreferencesService#CONFIGURATION_LEVEL},
     * {@link IPreferencesService#INSTANCE_LEVEL}, or
     * {@link IPreferencesService#PROJECT_LEVEL}
     * @return true if this container has a tab with the given name
     */
    public boolean hasTabInfo(String tabName);

    /**
     * Adds the given TabInfo to this container
     */
    public void addTabInfo(TabInfo info);

    /**
     * Removes the given TabInfo from this container
     */
    public void removeTabInfo(TabInfo tab);

    /**
     * Returns an Iterator over the set of tabs in this container
     * @return
     */
    public Iterator<TabInfo> getTabInfos();

    /**
     * @param name one of {@link IPreferencesService#DEFAULT_LEVEL},
     * {@link IPreferencesService#CONFIGURATION_LEVEL},
     * {@link IPreferencesService#INSTANCE_LEVEL}, or
     * {@link IPreferencesService#PROJECT_LEVEL}
     * @return the TabInfo for the tab with the given name
     */
    public TabInfo getTabInfo(String name);
}
