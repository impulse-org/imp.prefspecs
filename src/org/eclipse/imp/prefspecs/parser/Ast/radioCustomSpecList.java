
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
 *<li>Rule 147:  radioCustomSpecs ::= radioCustomSpec
 *<li>Rule 148:  radioCustomSpecs ::= radioCustomSpecs radioCustomSpec
 *</b>
 */
public class radioCustomSpecList extends AbstractASTNodeList implements IradioCustomSpecs
{
    public IradioCustomSpec getradioCustomSpecAt(int i) { return (IradioCustomSpec) getElementAt(i); }

    public radioCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public radioCustomSpecList(IradioCustomSpec _radioCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _radioCustomSpec, leftRecursive);
        ((ASTNode) _radioCustomSpec).setParent(this);
    }

    public void add(IradioCustomSpec _radioCustomSpec)
    {
        super.add((ASTNode) _radioCustomSpec);
        ((ASTNode) _radioCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof radioCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        radioCustomSpecList other = (radioCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IradioCustomSpec element = getradioCustomSpecAt(i);
            if (! element.equals(other.getradioCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getradioCustomSpecAt(i).hashCode());
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
                IradioCustomSpec element = getradioCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


