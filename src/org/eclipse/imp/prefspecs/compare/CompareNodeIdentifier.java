/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
 *******************************************************************************/

package org.eclipse.imp.prefspecs.compare;

import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.colorFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.configurationTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.defaultTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.directoryFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.doubleFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fontFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.instanceTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optConditionalSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optLabelSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optPackageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optToolTipSpec;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.projectTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.ICompareNodeIdentifier;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class CompareNodeIdentifier implements ICompareNodeIdentifier {
    private static final Class<?>[] nodeTypes= {
        booleanFieldSpec.class, colorFieldSpec.class, comboFieldSpec.class, dirListFieldSpec.class,
        directoryFieldSpec.class, doubleFieldSpec.class, fileFieldSpec.class, fontFieldSpec.class,
        optConditionalSpec.class, optLabelSpec.class, optPackageSpec.class, optToolTipSpec.class,
        pageSpec.class, radioFieldSpec.class, stringFieldSpec.class, fieldsSpec.class, fieldSpecs.class,
        tabsSpec.class, defaultTabSpec.class, configurationTabSpec.class, projectTabSpec.class, instanceTabSpec.class
    };

    public CompareNodeIdentifier() { }

    /* (non-Javadoc)
     * @see org.eclipse.imp.services.ICompareNodeIdentifier#getID(java.lang.Object)
     */
    public String getID(Object o) {
        if (o instanceof pageSpec) {
            return ((pageSpec) o).getpageName().getname().toString();
        }
        if (o instanceof booleanFieldSpec) {
            return ((booleanFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof colorFieldSpec) {
            return ((colorFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof comboFieldSpec) {
            return ((comboFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof directoryFieldSpec) {
            return ((directoryFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof doubleFieldSpec) {
            return ((doubleFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof fileFieldSpec) {
            return ((fileFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof fontFieldSpec) {
            return ((fontFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof radioFieldSpec) {
            return ((radioFieldSpec) o).getidentifier().toString();
        }
        if (o instanceof stringFieldSpec) {
            return ((stringFieldSpec) o).getidentifier().toString();
        }
        for(int i=0; i < nodeTypes.length; i++) {
            if (nodeTypes[i].isInstance(o)) {
                return nodeTypes[i].getName();
            }
        }
        return o.toString();
    }

    /* (non-Javadoc)
     * @see org.eclipse.imp.services.ICompareNodeIdentifier#getTypeCode(java.lang.Object)
     */
    public int getTypeCode(Object o) {
        for(int i=0; i < nodeTypes.length; i++) {
            if (nodeTypes[i].isInstance(o)) {
                return i;
            }
        }
        return -1;
    }
}
