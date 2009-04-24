
////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2007 IBM Corporation.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
//Contributors:
//    Stan Sutton (suttons@us.ibm.com) - initial API and implementation
//    Robert Fuhrer (rfuhrer@watson.ibm.com)
////////////////////////////////////////////////////////////////////////////////

package org.eclipse.imp.prefspecs.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import java.util.Hashtable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *<b>
 *<li>Rule 113:  comboCustomSpecs ::= comboCustomSpec
 *<li>Rule 114:  comboCustomSpecs ::= comboCustomSpecs comboCustomSpec
 *</b>
 */
public class comboCustomSpecList extends AbstractASTNodeList implements IcomboCustomSpecs
{
    public IcomboCustomSpec getcomboCustomSpecAt(int i) { return (IcomboCustomSpec) getElementAt(i); }

    public comboCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public comboCustomSpecList(IcomboCustomSpec _comboCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _comboCustomSpec, leftRecursive);
        ((ASTNode) _comboCustomSpec).setParent(this);
    }

    public void add(IcomboCustomSpec _comboCustomSpec)
    {
        super.add((ASTNode) _comboCustomSpec);
        ((ASTNode) _comboCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof comboCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        comboCustomSpecList other = (comboCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IcomboCustomSpec element = getcomboCustomSpecAt(i);
            if (! element.equals(other.getcomboCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getcomboCustomSpecAt(i).hashCode());
        return hash;
    }

    public void accept(IAstVisitor v)
    {
        if (! v.preVisit(this)) return;
        enter((Visitor) v);
        v.postVisit(this);
    }
    public void enter(Visitor v)
    {
        boolean checkChildren = v.visit(this);
        if (checkChildren)
        {
            for (int i = 0; i < size(); i++)
            {
                IcomboCustomSpec element = getcomboCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


