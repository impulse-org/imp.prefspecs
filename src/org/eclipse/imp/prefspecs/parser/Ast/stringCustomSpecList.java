
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
 *<li>Rule 163:  stringCustomSpecs ::= stringCustomSpec
 *<li>Rule 164:  stringCustomSpecs ::= stringCustomSpecs stringCustomSpec
 *</b>
 */
public class stringCustomSpecList extends AbstractASTNodeList implements IstringCustomSpecs
{
    public IstringCustomSpec getstringCustomSpecAt(int i) { return (IstringCustomSpec) getElementAt(i); }

    public stringCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public stringCustomSpecList(IstringCustomSpec _stringCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _stringCustomSpec, leftRecursive);
        ((ASTNode) _stringCustomSpec).setParent(this);
    }

    public void add(IstringCustomSpec _stringCustomSpec)
    {
        super.add((ASTNode) _stringCustomSpec);
        ((ASTNode) _stringCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof stringCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        stringCustomSpecList other = (stringCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IstringCustomSpec element = getstringCustomSpecAt(i);
            if (! element.equals(other.getstringCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getstringCustomSpecAt(i).hashCode());
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
                IstringCustomSpec element = getstringCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


