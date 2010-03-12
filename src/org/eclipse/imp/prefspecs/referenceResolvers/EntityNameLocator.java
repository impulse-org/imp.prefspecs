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

package org.eclipse.imp.prefspecs.referenceResolvers;

import org.eclipse.imp.prefspecs.parser.Ast.ItabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.colorFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.directoryFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.doubleFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fontFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldSpec;
import org.eclipse.imp.services.IEntityNameLocator;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class EntityNameLocator implements IEntityNameLocator {
    public Object getName(Object srcEntity) {
        if (srcEntity instanceof pageSpec) {
            return ((pageSpec) srcEntity).getpageName();
        }
        if (srcEntity instanceof ItabSpec) {
            return ((ItabSpec) srcEntity).getLeftIToken();
        }
        if (srcEntity instanceof booleanFieldSpec) {
            return ((booleanFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof colorFieldSpec) {
            return ((colorFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof comboFieldSpec) {
            return ((comboFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof directoryFieldSpec) {
            return ((directoryFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof dirListFieldSpec) {
            return ((dirListFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof doubleFieldSpec) {
            return ((doubleFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof fileFieldSpec) {
            return ((fileFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof fontFieldSpec) {
            return ((fontFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof radioFieldSpec) {
            return ((radioFieldSpec) srcEntity).getidentifier();
        }
        if (srcEntity instanceof stringFieldSpec) {
            return ((stringFieldSpec) srcEntity).getidentifier();
        }
        return null;
    }
}
