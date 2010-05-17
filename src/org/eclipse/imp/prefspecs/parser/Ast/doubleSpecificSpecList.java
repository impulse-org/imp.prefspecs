
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
 *<li>Rule 112:  doubleSpecificSpecs ::= doubleSpecificSpec
 *<li>Rule 113:  doubleSpecificSpecs ::= doubleSpecificSpecs doubleSpecificSpec
 *</b>
 */
public class doubleSpecificSpecList extends AbstractASTNodeList implements IdoubleSpecificSpecs
{
    public IdoubleSpecificSpec getdoubleSpecificSpecAt(int i) { return (IdoubleSpecificSpec) getElementAt(i); }

    public doubleSpecificSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public doubleSpecificSpecList(IdoubleSpecificSpec _doubleSpecificSpec, boolean leftRecursive)
    {
        super((ASTNode) _doubleSpecificSpec, leftRecursive);
        ((ASTNode) _doubleSpecificSpec).setParent(this);
    }

    public void add(IdoubleSpecificSpec _doubleSpecificSpec)
    {
        super.add((ASTNode) _doubleSpecificSpec);
        ((ASTNode) _doubleSpecificSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof doubleSpecificSpecList)) return false;
        if (! super.equals(o)) return false;
        doubleSpecificSpecList other = (doubleSpecificSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IdoubleSpecificSpec element = getdoubleSpecificSpecAt(i);
            if (! element.equals(other.getdoubleSpecificSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getdoubleSpecificSpecAt(i).hashCode());
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
                IdoubleSpecificSpec element = getdoubleSpecificSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


