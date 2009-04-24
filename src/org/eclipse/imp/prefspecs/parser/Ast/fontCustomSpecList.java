
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
 *<li>Rule 128:  fontCustomSpecs ::= fontCustomSpec
 *<li>Rule 129:  fontCustomSpecs ::= fontCustomSpecs fontCustomSpec
 *</b>
 */
public class fontCustomSpecList extends AbstractASTNodeList implements IfontCustomSpecs
{
    public IfontCustomSpec getfontCustomSpecAt(int i) { return (IfontCustomSpec) getElementAt(i); }

    public fontCustomSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public fontCustomSpecList(IfontCustomSpec _fontCustomSpec, boolean leftRecursive)
    {
        super((ASTNode) _fontCustomSpec, leftRecursive);
        ((ASTNode) _fontCustomSpec).setParent(this);
    }

    public void add(IfontCustomSpec _fontCustomSpec)
    {
        super.add((ASTNode) _fontCustomSpec);
        ((ASTNode) _fontCustomSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof fontCustomSpecList)) return false;
        if (! super.equals(o)) return false;
        fontCustomSpecList other = (fontCustomSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IfontCustomSpec element = getfontCustomSpecAt(i);
            if (! element.equals(other.getfontCustomSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getfontCustomSpecAt(i).hashCode());
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
                IfontCustomSpec element = getfontCustomSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


