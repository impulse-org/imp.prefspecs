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

package org.eclipse.imp.prefspecs.compiler.model;


public abstract class FieldVisitor {
    public void visit(IPageMemberContainer parent) {
        for(IPageMember m: parent.getChildren()) {
            if (m instanceof FieldInfo) {
                visitField((FieldInfo) m);
            } else if (m instanceof FieldGroup) {
                visit((IPageMemberContainer) m);
            }
        }
    }

    public abstract void visitField(FieldInfo fieldInfo);
}