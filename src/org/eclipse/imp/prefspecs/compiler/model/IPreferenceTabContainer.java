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

/**
 * This represents an entity that can contain a tabs specification, e.g., a
 * page or the top-level preferences specification itself.
 * @author rfuhrer
 */
public interface IPreferenceTabContainer {
    public boolean hasTabInfo(String tabName);
    public void addTabInfo(PreferencesTabInfo info);
    public void removeTabInfo(PreferencesTabInfo tab);
    public Iterator<PreferencesTabInfo> getTabInfos();
    public PreferencesTabInfo getTabInfo(String name);
    public boolean hasTabInfo(PreferencesTabInfo tab);
}
