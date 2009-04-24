
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
 *<em>
 *<li>Rule 80:  fontFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 81:  fontFieldPropertySpecs ::= {$ fontSpecificSpecs }$
 *</b>
 */
public class fontFieldPropertySpecs extends ASTNode implements IfontFieldPropertySpecs
{
    private fontSpecificSpecList _fontSpecificSpecs;

    public fontSpecificSpecList getfontSpecificSpecs() { return _fontSpecificSpecs; }

    public fontFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                  fontSpecificSpecList _fontSpecificSpecs)
    {
        super(leftIToken, rightIToken);

        this._fontSpecificSpecs = _fontSpecificSpecs;
        ((ASTNode) _fontSpecificSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_fontSpecificSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof fontFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        fontFieldPropertySpecs other = (fontFieldPropertySpecs) o;
        if (! _fontSpecificSpecs.equals(other._fontSpecificSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_fontSpecificSpecs.hashCode());
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
            _fontSpecificSpecs.accept(v);
        v.endVisit(this);
    }
}


