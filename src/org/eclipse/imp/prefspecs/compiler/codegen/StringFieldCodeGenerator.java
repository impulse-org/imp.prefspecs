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
import org.eclipse.imp.prefspecs.compiler.model.DirListFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.DirectoryFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.FileFieldInfo;
import org.eclipse.imp.prefspecs.compiler.model.PreferencesPageInfo;
import org.eclipse.imp.prefspecs.compiler.model.StringFieldInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class StringFieldCodeGenerator extends FieldCodeGenerator {
    private final StringFieldInfo fStringFieldInfo;

    public StringFieldCodeGenerator(StringFieldInfo fieldInfo) {
        super(fieldInfo);
        fStringFieldInfo= fieldInfo;
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.prefspecs.compiler.codegen.FieldCodeGenerator#genPreferenceInitializer(java.lang.StringBuilder, java.lang.String)
     */
    @Override
    public void genPreferenceInitializer(StringBuilder srcText, String prefKeysClassName) {
        // This should handle sub-types as well...
        srcText.append("\t\tservice.setStringPreference(IPreferencesService.DEFAULT_LEVEL, " +
                prefKeysClassName + "." + getPreferenceKey() + ", " +
                fStringFieldInfo.getDefaultValue() + ");\n");
    }

    @Override
    public String getFieldEditorTypeName() {
        return "StringFieldEditor";
    }

    /**
     * @return the name of the appropriate method on the IMP runtime class PreferencesUtilities to
     * create a field of this type
     */
    protected String getFactoryMethodName() {
        return "makeNewStringField";
    }

    @Override
    public void genTextToCreateField(StringBuilder srcText, PreferencesPageInfo pageInfo, String tabLevel) {
        boolean editable = tabLevel.equals(PreferencesService.PROJECT_LEVEL) ? false : true;    //fieldInfo.getIsEditable();
        String label = (fFieldInfo.getLabel() != null) ? fFieldInfo.getLabel() : createLabelFor(fFieldInfo.getName());
        String toolTip = fFieldInfo.getToolTipText();

        srcText.append("\n");

        srcText.append("\t\t" + getFieldEditorTypeName() + " " + fFieldInfo.getName() + " = fPrefUtils." + getFactoryMethodName() + "(\n");
        srcText.append("\t\t\tpage, this, fPrefService,\n");
        srcText.append("\t\t\t\"" + tabLevel + "\", \"" + fFieldInfo.getName() + "\", \"" + label + "\",\n");
        srcText.append("\t\t\t\"" + (toolTip != null ? toolTip : "") + "\",\n");
        srcText.append("\t\t\tparent,\n");
        srcText.append("\t\t\t" + editable + ", " + editable + ",\n");
        srcText.append("\t\t\t" + fStringFieldInfo.getEmptyValueAllowed() + ", \"" + stripQuotes(fStringFieldInfo.getEmptyValue()) + "\",\n"); // empty allowed, empty value
        srcText.append("\t\t\t" + fFieldInfo.getIsRemovable() + ");\n"); // false for default tab but not necessarily any others

        if (fStringFieldInfo.getValidatorQualClass() != null && fStringFieldInfo.getValidatorQualClass().length() > 0) {
            srcText.append("\t\t" + fFieldInfo.getName() + ".setValidator(new " + fStringFieldInfo.getValidatorQualClass() + "());\n");
        }
        srcText.append("\t\tfields.add(" + fFieldInfo.getName() + ");\n\n");
        
        if (!pageInfo.getNoDetails()) {
            String linkName = fFieldInfo.getName() + "DetailsLink";
            srcText.append("\t\tLink " + linkName + " = fPrefUtils.createDetailsLink(parent, " +
                    fFieldInfo.getName() + ", " + fFieldInfo.getName() + ".getTextControl().getParent()" + ", \"Details ...\");\n\n");
            srcText.append("\t\t" + linkName + ".setEnabled(" + editable + ");\n");
            srcText.append("\t\tfDetailsLinks.add(" + linkName + ");\n\n");
        }
    }
}
