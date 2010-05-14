
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
 *<li>Rule 120:  fontSpecificSpecs ::= fontSpecificSpec
 *<li>Rule 121:  fontSpecificSpecs ::= fontSpecificSpecs fontSpecificSpec
 *</b>
 */
public class fontSpecificSpecList extends AbstractASTNodeList implements IfontSpecificSpecs
{
    public IfontSpecificSpec getfontSpecificSpecAt(int i) { return (IfontSpecificSpec) getElementAt(i); }

    public fontSpecificSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public fontSpecificSpecList(IfontSpecificSpec _fontSpecificSpec, boolean leftRecursive)
    {
        super((ASTNode) _fontSpecificSpec, leftRecursive);
        ((ASTNode) _fontSpecificSpec).setParent(this);
    }

    public void add(IfontSpecificSpec _fontSpecificSpec)
    {
        super.add((ASTNode) _fontSpecificSpec);
        ((ASTNode) _fontSpecificSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof fontSpecificSpecList)) return false;
        if (! super.equals(o)) return false;
        fontSpecificSpecList other = (fontSpecificSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IfontSpecificSpec element = getfontSpecificSpecAt(i);
            if (! element.equals(other.getfontSpecificSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getfontSpecificSpecAt(i).hashCode());
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
                IfontSpecificSpec element = getfontSpecificSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


