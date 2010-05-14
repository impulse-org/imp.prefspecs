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

import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * An abstract base class representing an enumerated field, e.g. a combo box or group of radio buttons.
 * @author rfuhrer@watson.ibm.com
 */
public abstract class EnumFieldInfo extends FieldInfo {
    private static final int DEFAULT_NUM_COLS= 2;

    private IEnumValueSource fValueSource;

    private int numCols;

    private boolean hasNumCols= false;

    public EnumFieldInfo(PreferencesPageInfo parentPage, String name) {
        super(parentPage, name);
    }

    public EnumFieldInfo(PreferencesPageInfo parentPage, String name, IEnumValueSource valueSource) {
        super(parentPage, name);
        fValueSource= valueSource;
    }

    public void setValueSource(IEnumValueSource valueSource) {
        fValueSource= valueSource;
    }

    public IEnumValueSource getValueSource() {
        return fValueSource;
    }

    public void setNumColumns(int numCols) {
        this.numCols= numCols;
        this.hasNumCols= true;
    }

    public boolean hasNumColumns() {
        return hasNumCols;
    }

    public int getNumColumns() {
        return hasNumCols ? numCols : DEFAULT_NUM_COLS;
    }

    /*
     * For reporting on the contents of the virtual field
     */
    public void dump(String prefix, MessageConsoleStream out) {
        super.dump(prefix, out);
        out.println(fValueSource.toString());
    }
}
