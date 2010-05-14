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

import org.eclipse.imp.preferences.PreferencesService;
import org.eclipse.imp.prefspecs.compiler.model.FieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PreferencesPageInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public abstract class ScalarFieldCodeGenerator extends FieldCodeGenerator {
    /**
     * @param fieldInfo
     */
    public ScalarFieldCodeGenerator(FieldInfo fieldInfo) {
        super(fieldInfo);
    }

    protected void genTextToCreateScalarField(StringBuilder srcText, PreferencesPageInfo pageInfo,
            String tabLevel, String emptyValue, String fieldEditorTypeName, String fieldHolderExpr)
    {
        boolean editable = tabLevel.equals(PreferencesService.PROJECT_LEVEL) ? false : true; // fieldInfo.getIsEditable();
        String label = (fFieldInfo.getLabel() != null) ? fFieldInfo.getLabel() : createLabelFor(fFieldInfo.getName());
        String toolTip = fFieldInfo.getToolTipText();

        srcText.append("\n");
        srcText.append("\t\t" + fieldEditorTypeName + "Editor " + fFieldInfo.getName() + " = fPrefUtils.makeNew" + fieldEditorTypeName + "(\n");
        srcText.append("\t\t\tpage, this, fPrefService,\n");
        srcText.append("\t\t\t\"" + tabLevel + "\", \"" + fFieldInfo.getName() + "\", \"" + label + "\",\n");
        srcText.append("\t\t\t\"" + (toolTip != null ? toolTip : "") + "\",\n");
        srcText.append("\t\t\tparent,\n");
        srcText.append("\t\t\t" + editable + ", " + editable + ",\n"); // enabled, editable
        srcText.append("\t\t\t" + (emptyValue != null) + ", " + (emptyValue != null ? emptyValue : "") + ",\n");
        srcText.append("\t\t\t" + fFieldInfo.getIsRemovable() + ");\n"); // false for default tab but not necessarily any others\n";
        srcText.append("\t\tfields.add(" + fFieldInfo.getName() + ");\n\n");

        if (!pageInfo.getNoDetails()) {
            String linkName = fFieldInfo.getName() + "DetailsLink";
            srcText.append("\t\tLink " + linkName + " = fPrefUtils.createDetailsLink(parent, " +
                           fFieldInfo.getName() + ", " + fFieldInfo.getName() + fieldHolderExpr + ", \"Details ...\");\n\n");
            srcText.append("\t\t" + linkName + ".setEnabled(" + editable + ");\n");
            srcText.append("\t\tfDetailsLinks.add(" + linkName + ");\n\n");
        }
    }
}
