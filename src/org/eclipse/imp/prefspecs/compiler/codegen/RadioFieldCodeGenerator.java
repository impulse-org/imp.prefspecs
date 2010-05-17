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
import org.eclipse.imp.prefspecs.compiler.model.PageInfo;
import org.eclipse.imp.prefspecs.compiler.model.RadioFieldInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class RadioFieldCodeGenerator extends EnumFieldCodeGenerator {
    private final RadioFieldInfo fRadioFieldInfo;

    /**
     * @param fieldInfo
     */
    public RadioFieldCodeGenerator(RadioFieldInfo fieldInfo) {
        super(fieldInfo);
        fRadioFieldInfo= fieldInfo;
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator#genPreferenceInitializer(java.lang.StringBuilder, java.lang.String)
     */
    @Override
    public void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName) {
        srcText.append("\t\tservice.setStringPreference(IPreferencesService.DEFAULT_LEVEL, " +
                prefKeysClassName + "." + getPreferenceKey() + ", " +
                getEnumDefaultValueExpr(fRadioFieldInfo.getValueSource()) + ");\n");
    }

    @Override
    public String getFieldEditorTypeName() {
        return "RadioGroupFieldEditor";
    }

    @Override
    public void genTextToCreateField(StringBuilder srcText, PageInfo pageInfo, String tabLevel, String parentComposite) {
        boolean editable = tabLevel.equals(PreferencesService.PROJECT_LEVEL) ? false : true;    //fieldInfo.getIsEditable();
        String label = (fFieldInfo.getLabel() != null) ? fFieldInfo.getLabel() : createLabelFor(fFieldInfo.getName());
        String toolTip = fFieldInfo.getToolTipText();
        
        srcText.append("\n");

        if (fRadioFieldInfo.getValueSource() instanceof DynamicEnumValueSource) {
            DynamicEnumValueSource evs= (DynamicEnumValueSource) fRadioFieldInfo.getValueSource();
            srcText.append("\t\tIEnumValueProvider evp = new " + evs.getQualClassName() + "();\n");
        }

        srcText.append("\t\tRadioGroupFieldEditor " + fFieldInfo.getName() + " = fPrefUtils.makeNewRadioGroupField(\n");
        srcText.append("\t\t\tpage, this, fPrefService,\n");
        srcText.append("\t\t\t\"" + tabLevel + "\", \"" + fFieldInfo.getName() + "\", \"" + label + "\",\n");
        srcText.append("\t\t\t\"" + (toolTip != null ? toolTip : "") + "\",\n");
        srcText.append("\t\t\t" + fRadioFieldInfo.getNumColumns() + ",\n");
        srcText.append("\t\t\t" + getValueStringsExpr(fRadioFieldInfo.getValueSource()) + ",\n");
        srcText.append("\t\t\t" + getLabelStringsExpr(fRadioFieldInfo.getValueSource()) + ",\n");
        srcText.append("\t\t\t" + parentComposite + ",\n");
        srcText.append("\t\t\ttrue,\n");
        srcText.append("\t\t\t" + editable + ",\n");
        srcText.append("\t\t\t" + fFieldInfo.getIsRemovable() + ");\n");   // false for default tab but not necessarily any others

        srcText.append("\t\tfields.add(" + fFieldInfo.getName() + ");\n\n");
        
        if (!pageInfo.getNoDetails()) {
            String linkName = fFieldInfo.getName() + "DetailsLink";
            srcText.append("\t\tLink " + linkName + " = fPrefUtils.createDetailsLink(parent, " +
                    fFieldInfo.getName() + ", " + fFieldInfo.getName() + ".getRadioBoxControl().getParent()" + ", \"Details ...\");\n\n");
            srcText.append("\t\t" + linkName + ".setEnabled(" + editable + ");\n");
            srcText.append("\t\tfDetailsLinks.add(" + linkName + ");\n\n");
        }
    }
}
