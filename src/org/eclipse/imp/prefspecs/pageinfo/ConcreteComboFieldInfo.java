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


public class ConcreteComboFieldInfo extends ConcreteEnumFieldInfo {
    /**
     * Constructor to use for concrete fields that are associated
     * with a virtual, as happens during the processing of the prefspecs
     * AST and the elaboration of field attributes.
     * 
     * @param vField
     * @param parentTab
     */
    public ConcreteComboFieldInfo(VirtualComboFieldInfo vComboFieldInfo, PreferencesTabInfo parentTab) {
        // Super gets page and name from associated virtual field;
        // will serve as a check that given values aren't null
        super(vComboFieldInfo, parentTab);
    }
}
