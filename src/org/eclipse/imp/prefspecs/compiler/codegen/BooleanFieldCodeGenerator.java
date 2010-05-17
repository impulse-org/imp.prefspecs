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

import org.eclipse.imp.prefspecs.compiler.model.BooleanFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PageInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class BooleanFieldCodeGenerator extends ScalarFieldCodeGenerator {
    private final BooleanFieldInfo fBooleanFieldInfo;

    public BooleanFieldCodeGenerator(BooleanFieldInfo fieldInfo) {
        super(fieldInfo);
        fBooleanFieldInfo= fieldInfo;
    }

    @Override
    public String getFieldEditorTypeName() {
        return "BooleanFieldEditor";
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator#getPreferenceInitializer()
     */
    @Override
    public void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName) {
        srcText.append("\t\tservice.setBooleanPreference(IPreferencesService.DEFAULT_LEVEL, " +
                prefKeysClassName + "." + getPreferenceKey() + ", " +
                fBooleanFieldInfo.getDefaultValue() + ");\n");        // TODO Auto-generated method stub
    }

    public void genTextToCreateField(StringBuilder srcText, PageInfo pageInfo, String tabLevel, String parentComposite) {
        genTextToCreateScalarField(srcText, pageInfo, tabLevel, parentComposite, "false", "BooleanField", ".getChangeControl().getParent()");
    }

    @Override
    public void genTextToEnableField(StringBuilder srcText, String enablementExpr) {
        srcText.append("\t\t\t\t" + fFieldInfo.getName() + ".getChangeControl().setEnabled(" + enablementExpr + ");\n");
    }
}
