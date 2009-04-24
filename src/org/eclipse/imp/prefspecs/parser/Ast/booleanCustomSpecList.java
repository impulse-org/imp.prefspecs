
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
 *<li>Rule 99:  booleanCustomSpecs ::= booleanCustomSpec
 *<li>Rule 100:  booleanCustomSpecs ::= booleanCustomSpecs booleanCustomSpec
 *</b>
 */
public class booleanCustomSpecList extends AbstractASTNodeList implements IbooleanCustomSpecs
{
    public IbooleanCustomSpec getbooleanCustomSpecAt(int i) { return (IbooleanCustomSpec) getElementAt(i); }

    public booleanCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public booleanCustomSpecList(IbooleanCustomSpec _booleanCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _booleanCustomSpec, leftRecursive);
        ((ASTNode) _booleanCustomSpec).setParent(this);
    }

    public void add(IbooleanCustomSpec _booleanCustomSpec)
    {
        super.add((ASTNode) _booleanCustomSpec);
        ((ASTNode) _booleanCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof booleanCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        booleanCustomSpecList other = (booleanCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IbooleanCustomSpec element = getbooleanCustomSpecAt(i);
            if (! element.equals(other.getbooleanCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getbooleanCustomSpecAt(i).hashCode());
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
                IbooleanCustomSpec element = getbooleanCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


