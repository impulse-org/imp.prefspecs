
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
 *<li>Rule 121:  doubleCustomSpecs ::= doubleCustomSpec
 *<li>Rule 122:  doubleCustomSpecs ::= doubleCustomSpecs doubleCustomSpec
 *</b>
 */
public class doubleCustomSpecList extends AbstractASTNodeList implements IdoubleCustomSpecs
{
    public IdoubleCustomSpec getdoubleCustomSpecAt(int i) { return (IdoubleCustomSpec) getElementAt(i); }

    public doubleCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public doubleCustomSpecList(IdoubleCustomSpec _doubleCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _doubleCustomSpec, leftRecursive);
        ((ASTNode) _doubleCustomSpec).setParent(this);
    }

    public void add(IdoubleCustomSpec _doubleCustomSpec)
    {
        super.add((ASTNode) _doubleCustomSpec);
        ((ASTNode) _doubleCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof doubleCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        doubleCustomSpecList other = (doubleCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IdoubleCustomSpec element = getdoubleCustomSpecAt(i);
            if (! element.equals(other.getdoubleCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getdoubleCustomSpecAt(i).hashCode());
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
                IdoubleCustomSpec element = getdoubleCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


