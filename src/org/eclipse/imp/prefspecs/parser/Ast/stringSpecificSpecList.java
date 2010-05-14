
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
 *<li>Rule 154:  stringSpecificSpecs ::= stringSpecificSpec
 *<li>Rule 155:  stringSpecificSpecs ::= stringSpecificSpecs stringSpecificSpec
 *</b>
 */
public class stringSpecificSpecList extends AbstractASTNodeList implements IstringSpecificSpecs
{
    public IstringSpecificSpec getstringSpecificSpecAt(int i) { return (IstringSpecificSpec) getElementAt(i); }

    public stringSpecificSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public stringSpecificSpecList(IstringSpecificSpec _stringSpecificSpec, boolean leftRecursive)
    {
        super((ASTNode) _stringSpecificSpec, leftRecursive);
        ((ASTNode) _stringSpecificSpec).setParent(this);
    }

    public void add(IstringSpecificSpec _stringSpecificSpec)
    {
        super.add((ASTNode) _stringSpecificSpec);
        ((ASTNode) _stringSpecificSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof stringSpecificSpecList)) return false;
        if (! super.equals(o)) return false;
        stringSpecificSpecList other = (stringSpecificSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IstringSpecificSpec element = getstringSpecificSpecAt(i);
            if (! element.equals(other.getstringSpecificSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getstringSpecificSpecAt(i).hashCode());
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
                IstringSpecificSpec element = getstringSpecificSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


