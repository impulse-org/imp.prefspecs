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
import org.eclipse.imp.prefspecs.compiler.DynamicEnumValueSource;
import org.eclipse.imp.prefspecs.compiler.model.ComboFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PreferencesPageInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class ComboFieldCodeGenerator extends EnumFieldCodeGenerator {
    private final ComboFieldInfo fComboFieldInfo;

    public ComboFieldCodeGenerator(ComboFieldInfo fieldInfo) {
        super(fieldInfo);
        fComboFieldInfo= fieldInfo;
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator#genPreferenceInitializer(java.lang.StringBuilder, java.lang.String)
     */
    @Override
    public void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName) {
        srcText.append("\t\tservice.setStringPreference(IPreferencesService.DEFAULT_LEVEL, " +
                prefKeysClassName + "." + getPreferenceKey() + ", " +
                getEnumDefaultValueExpr(fComboFieldInfo.getValueSource()) + ");\n");
    }

    @Override
    public String getFieldEditorTypeName() {
        return "ComboFieldEditor";
    }

    @Override
    public void genTextToCreateField(StringBuilder srcText, PreferencesPageInfo pageInfo, String tabLevel) {
        boolean editable = tabLevel.equals(PreferencesService.PROJECT_LEVEL) ? false : true;    //fieldInfo.getIsEditable();
        String label = (fFieldInfo.getLabel() != null) ? fFieldInfo.getLabel() : createLabelFor(fFieldInfo.getName());
        String toolTip = fFieldInfo.getToolTipText();

        srcText.append("\n");

        if (fComboFieldInfo.getValueSource() instanceof DynamicEnumValueSource) {
            DynamicEnumValueSource evs= (DynamicEnumValueSource) fComboFieldInfo.getValueSource();
            srcText.append("\t\tIEnumValueProvider evp = new " + evs.getQualClassName() + "();\n");
        }

        srcText.append("\t\tComboFieldEditor " + fFieldInfo.getName() + " = fPrefUtils.makeNewComboField(\n");
        srcText.append("\t\t\tpage, this, fPrefService,\n");
        srcText.append("\t\t\t\"" + tabLevel + "\", \"" + fFieldInfo.getName() + "\", \"" + label + "\",\n");
        srcText.append("\t\t\t\"" + (toolTip != null ? toolTip : "") + "\",\n");
        srcText.append("\t\t\t" + fComboFieldInfo.getNumColumns() + ",\n");
        srcText.append("\t\t\t" + getValueStringsExpr(fComboFieldInfo.getValueSource()) + ",\n"); // values
        srcText.append("\t\t\t" + getLabelStringsExpr(fComboFieldInfo.getValueSource()) + ",\n"); // labels
        srcText.append("\t\t\tparent,\n");
        srcText.append("\t\t\t" + editable + ",\n");
        srcText.append("\t\t\t" + fFieldInfo.getIsRemovable() + ");\n");   // false for default tab but not necessarily any others
        srcText.append("\t\tfields.add(" + fFieldInfo.getName() + ");\n\n");
        
        if (!pageInfo.getNoDetails()) {
            String linkName = fFieldInfo.getName() + "DetailsLink";
            srcText.append("\t\tLink " + linkName + " = fPrefUtils.createDetailsLink(parent, " +
                    fFieldInfo.getName() + ", " + fFieldInfo.getName() + ".getComboBoxControl().getParent()" + ", \"Details ...\");\n\n");
            srcText.append("\t\t" + linkName + ".setEnabled(" + editable + ");\n");
            srcText.append("\t\tfDetailsLinks.add(" + linkName + ");\n\n");
        }
    }
}
