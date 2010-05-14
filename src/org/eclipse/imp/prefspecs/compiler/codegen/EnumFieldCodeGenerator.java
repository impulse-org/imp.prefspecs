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

import java.util.List;

import org.eclipse.imp.prefspecs.compiler.DynamicEnumValueSource;
import org.eclipse.imp.prefspecs.compiler.IEnumValueSource;
import org.eclipse.imp.prefspecs.compiler.LiteralEnumValueSource;
import org.eclipse.imp.prefspecs.compiler.model.FieldInfo;

/**
 * @author rfuhrer@watson.ibm.com
 */
public abstract class EnumFieldCodeGenerator extends FieldCodeGenerator {
    /**
     * @param fieldInfo
     */
    public EnumFieldCodeGenerator(FieldInfo fieldInfo) {
        super(fieldInfo);
    }

    protected String getEnumDefaultValueExpr(IEnumValueSource vs) {
        if (vs instanceof LiteralEnumValueSource) {
            LiteralEnumValueSource levs= (LiteralEnumValueSource) vs;
            return "\"" + levs.getDefaultKey() + "\"";
        } else if (vs instanceof DynamicEnumValueSource) {
            DynamicEnumValueSource devs= (DynamicEnumValueSource) vs;
            return "new " + devs.getQualClassName() + "().getDefaultLabel()";
        }
        return "";
    }

    public String getValueStringsExpr(IEnumValueSource vs) {
        if (vs instanceof LiteralEnumValueSource) {
            LiteralEnumValueSource levs= (LiteralEnumValueSource) vs;
            List<String> labels= levs.getValues();

            return toStringArrayLiteral(labels);
        } else if (vs instanceof DynamicEnumValueSource) {
            // Uses local variable 'evp' generated earlier by the caller
            return "evp.getValues()";
        }
        throw new IllegalStateException("Unexpected type of enum value source: " + vs.getClass().getCanonicalName());
    }

    public String getLabelStringsExpr(IEnumValueSource vs) {
        StringBuilder sb= new StringBuilder();

        if (vs instanceof LiteralEnumValueSource) {
            LiteralEnumValueSource levs= (LiteralEnumValueSource) vs;
            List<String> values= levs.getValues();
            List<String> labels= levs.getLabels();
            
            sb.append("new String[] { ");
            for(int i=0; i < labels.size(); i++) {
                if (i > 0) { sb.append(", "); }
                if (labels.get(i) != null) {
                    appendWithQuotes(labels.get(i), sb);
                } else {
                    appendWithQuotes(createLabelFor(values.get(i)), sb);
                }
            }
            sb.append(" }");
        } else if (vs instanceof DynamicEnumValueSource) {
//          DynamicEnumValueSource devs= (DynamicEnumValueSource) vs;
//
//          sb.append("new ");
//          sb.append(stripQuotes(devs.getQualClassName()));
            sb.append("evp.getLabels()");
        }
        return sb.toString();
    }
}
