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

package org.eclipse.imp.prefspecs.compiler.codegen;

import org.eclipse.imp.prefspecs.compiler.model.DoubleFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PageInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class DoubleFieldCodeGenerator extends ScalarFieldCodeGenerator {
    private final DoubleFieldInfo fDoubleFieldInfo;

    /**
     * @param fieldInfo
     */
    public DoubleFieldCodeGenerator(DoubleFieldInfo fieldInfo) {
        super(fieldInfo);
        fDoubleFieldInfo= fieldInfo;
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator#genPreferenceInitializer(java.lang.StringBuilder, java.lang.String)
     */
    @Override
    public void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName) {
        srcText.append("\t\tservice.setDoublePreference(IPreferencesService.DEFAULT_LEVEL, " +
                prefKeysClassName + "." + getPreferenceKey() + ", " +
                fDoubleFieldInfo.getDefaultValue() + ");\n");
    }

    @Override
    public String getFieldEditorTypeName() {
        return "DoubleFieldEditor";
    }

    @Override
    public void genTextToCreateField(StringBuilder srcText, PageInfo pageInfo, String tabLevel, String parentComposite) {
        genTextToCreateScalarField(srcText, pageInfo, tabLevel, parentComposite, "\"0\"", "DoubleField", ".getTextControl().getParent()");
    }
}
