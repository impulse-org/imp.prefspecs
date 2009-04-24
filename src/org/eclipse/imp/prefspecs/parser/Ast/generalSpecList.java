
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
 *<li>Rule 88:  generalSpecs ::= $Empty
 *<li>Rule 89:  generalSpecs ::= generalSpecs generalSpec
 *</b>
 */
public class generalSpecList extends AbstractASTNodeList implements IgeneralSpecs
{
    public IgeneralSpec getgeneralSpecAt(int i) { return (IgeneralSpec) getElementAt(i); }

    public generalSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public generalSpecList(IgeneralSpec _generalSpec, boolean leftRecursive)
    {
        super((ASTNode) _generalSpec, leftRecursive);
        ((ASTNode) _generalSpec).setParent(this);
    }

    public void add(IgeneralSpec _generalSpec)
    {
        super.add((ASTNode) _generalSpec);
        ((ASTNode) _generalSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof generalSpecList)) return false;
        if (! super.equals(o)) return false;
        generalSpecList other = (generalSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IgeneralSpec element = getgeneralSpecAt(i);
            if (! element.equals(other.getgeneralSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getgeneralSpecAt(i).hashCode());
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
                IgeneralSpec element = getgeneralSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


