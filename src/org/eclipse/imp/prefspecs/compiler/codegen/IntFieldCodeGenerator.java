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

import org.eclipse.imp.prefspecs.compiler.model.IntFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PreferencesPageInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class IntFieldCodeGenerator extends ScalarFieldCodeGenerator {
    private final IntFieldInfo fIntFieldInfo;

    /**
     * @param fieldInfo
     */
    public IntFieldCodeGenerator(IntFieldInfo fieldInfo) {
        super(fieldInfo);
        fIntFieldInfo= fieldInfo;
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator#genPreferenceInitializer(java.lang.StringBuilder, java.lang.String)
     */
    @Override
    public void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName) {
        srcText.append("\t\tservice.setIntPreference(IPreferencesService.DEFAULT_LEVEL, " +
                prefKeysClassName + "." + getPreferenceKey() + ", " +
                fIntFieldInfo.getDefaultValue() + ");\n");
    }

    @Override
    public String getFieldEditorTypeName() {
        return "IntegerFieldEditor";
    }

    @Override
    public void genTextToCreateField(StringBuilder srcText, PreferencesPageInfo pageInfo, String tabLevel) {
        genTextToCreateScalarField(srcText, pageInfo, tabLevel, "\"0\"", "IntegerField", ".getTextControl().getParent()");
    }
}
