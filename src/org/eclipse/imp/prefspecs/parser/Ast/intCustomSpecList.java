
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
 *<li>Rule 137:  intCustomSpecs ::= intCustomSpec
 *<li>Rule 138:  intCustomSpecs ::= intCustomSpecs intCustomSpec
 *</b>
 */
public class intCustomSpecList extends AbstractASTNodeList implements IintCustomSpecs
{
    public IintCustomSpec getintCustomSpecAt(int i) { return (IintCustomSpec) getElementAt(i); }

    public intCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public intCustomSpecList(IintCustomSpec _intCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _intCustomSpec, leftRecursive);
        ((ASTNode) _intCustomSpec).setParent(this);
    }

    public void add(IintCustomSpec _intCustomSpec)
    {
        super.add((ASTNode) _intCustomSpec);
        ((ASTNode) _intCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof intCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        intCustomSpecList other = (intCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IintCustomSpec element = getintCustomSpecAt(i);
            if (! element.equals(other.getintCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getintCustomSpecAt(i).hashCode());
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
                IintCustomSpec element = getintCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


