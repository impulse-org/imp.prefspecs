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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.ui.console.MessageConsoleStream;

/**
 * @author rfuhrer@watson.ibm.com
 */
public class FieldGroup implements IPageMember, IPageMemberContainer {
    private final IPageMemberContainer fParent;
    private final List<IPageMember> fChildren= new LinkedList<IPageMember>();
    private final String fLabel;

    public FieldGroup(String label, IPageMemberContainer parent) {
        fParent= parent;
        fLabel= label;
        fParent.addChild(this);
    }

    public String getLabel() {
        return fLabel;
    }

    public String getName() {
        return fLabel;
    }

    public IPageMemberContainer getParent() {
        return fParent;
    }

    public void addChild(IPageMember member) {
        fChildren.add(member);
    }

    public List<IPageMember> getChildren() {
        return Collections.unmodifiableList(fChildren);
    }

    public void dump(String prefix, MessageConsoleStream out) {
        
    }

    @Override
    public String toString() {
        return "group " + fLabel;
    }
}
