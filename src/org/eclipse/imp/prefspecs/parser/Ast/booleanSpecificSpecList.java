
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
 *<li>Rule 95:  booleanSpecificSpecs ::= booleanSpecificSpec
 *<li>Rule 96:  booleanSpecificSpecs ::= booleanSpecificSpecs booleanSpecificSpec
 *</b>
 */
public class booleanSpecificSpecList extends AbstractASTNodeList implements IbooleanSpecificSpecs
{
    public IbooleanSpecificSpec getbooleanSpecificSpecAt(int i) { return (IbooleanSpecificSpec) getElementAt(i); }

    public booleanSpecificSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public booleanSpecificSpecList(IbooleanSpecificSpec _booleanSpecificSpec, boolean leftRecursive)
    {
        super((ASTNode) _booleanSpecificSpec, leftRecursive);
        ((ASTNode) _booleanSpecificSpec).setParent(this);
    }

    public void add(IbooleanSpecificSpec _booleanSpecificSpec)
    {
        super.add((ASTNode) _booleanSpecificSpec);
        ((ASTNode) _booleanSpecificSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof booleanSpecificSpecList)) return false;
        if (! super.equals(o)) return false;
        booleanSpecificSpecList other = (booleanSpecificSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IbooleanSpecificSpec element = getbooleanSpecificSpecAt(i);
            if (! element.equals(other.getbooleanSpecificSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getbooleanSpecificSpecAt(i).hashCode());
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
                IbooleanSpecificSpec element = getbooleanSpecificSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


