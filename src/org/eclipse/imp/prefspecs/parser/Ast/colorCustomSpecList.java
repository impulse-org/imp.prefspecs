
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
 *<li>Rule 107:  colorCustomSpecs ::= colorCustomSpec
 *<li>Rule 108:  colorCustomSpecs ::= colorCustomSpecs colorCustomSpec
 *</b>
 */
public class colorCustomSpecList extends AbstractASTNodeList implements IcolorCustomSpecs
{
    public IcolorCustomSpec getcolorCustomSpecAt(int i) { return (IcolorCustomSpec) getElementAt(i); }

    public colorCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public colorCustomSpecList(IcolorCustomSpec _colorCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _colorCustomSpec, leftRecursive);
        ((ASTNode) _colorCustomSpec).setParent(this);
    }

    public void add(IcolorCustomSpec _colorCustomSpec)
    {
        super.add((ASTNode) _colorCustomSpec);
        ((ASTNode) _colorCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof colorCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        colorCustomSpecList other = (colorCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IcolorCustomSpec element = getcolorCustomSpecAt(i);
            if (! element.equals(other.getcolorCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getcolorCustomSpecAt(i).hashCode());
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
                IcolorCustomSpec element = getcolorCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


