
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
import org.eclipse.imp.parser.SymbolTable;
import java.util.Hashtable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *<b>
 *<li>Rule 140:  radioSpecificSpecs ::= radioSpecificSpec
 *<li>Rule 141:  radioSpecificSpecs ::= radioSpecificSpecs radioSpecificSpec
 *</b>
 */
public class radioSpecificSpecList extends AbstractASTNodeList implements IradioSpecificSpecs
{
    public IradioSpecificSpec getradioSpecificSpecAt(int i) { return (IradioSpecificSpec) getElementAt(i); }

    public radioSpecificSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public radioSpecificSpecList(IradioSpecificSpec _radioSpecificSpec, boolean leftRecursive)
    {
        super((ASTNode) _radioSpecificSpec, leftRecursive);
        ((ASTNode) _radioSpecificSpec).setParent(this);
    }

    public void add(IradioSpecificSpec _radioSpecificSpec)
    {
        super.add((ASTNode) _radioSpecificSpec);
        ((ASTNode) _radioSpecificSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof radioSpecificSpecList)) return false;
        if (! super.equals(o)) return false;
        radioSpecificSpecList other = (radioSpecificSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IradioSpecificSpec element = getradioSpecificSpecAt(i);
            if (! element.equals(other.getradioSpecificSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getradioSpecificSpecAt(i).hashCode());
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
                IradioSpecificSpec element = getradioSpecificSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


