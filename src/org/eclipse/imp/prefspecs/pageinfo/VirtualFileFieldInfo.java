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

package org.eclipse.imp.prefspecs.pageinfo;

/**
 * @author sutton
 */
public class VirtualFileFieldInfo extends VirtualStringFieldInfo {
    public VirtualFileFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public VirtualFileFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
        super(parentPage, name, defValue);
    }

    public VirtualFileFieldInfo(PreferencesPageInfo parentPage, String name, String defValue, boolean hasSpecial, String special) {
        super(parentPage, name, defValue, hasSpecial, special);
    }
}
