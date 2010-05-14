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

package org.eclipse.imp.prefspecs.compiler.model;

public class DirectoryFieldInfo extends StringFieldInfo {
    public DirectoryFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public DirectoryFieldInfo(PreferencesPageInfo parentPage, String name, String defValue) {
        super(parentPage, name, defValue);
    }
}
