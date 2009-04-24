
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
 *<li>Rule 109:  colorDefValueSpec ::= DEFVALUE$ INTEGER$red ,$ INTEGER$green ,$ INTEGER$blue ;$
 *</b>
 */
public class colorDefValueSpec extends ASTNode implements IcolorDefValueSpec
{
    private ASTNodeToken _red;
    private ASTNodeToken _green;
    private ASTNodeToken _blue;

    public ASTNodeToken getred() { return _red; }
    public ASTNodeToken getgreen() { return _green; }
    public ASTNodeToken getblue() { return _blue; }

    public colorDefValueSpec(IToken leftIToken, IToken rightIToken,
                             ASTNodeToken _red,
                             ASTNodeToken _green,
                             ASTNodeToken _blue)
    {
        super(leftIToken, rightIToken);

        this._red = _red;
        ((ASTNode) _red).setParent(this);
        this._green = _green;
        ((ASTNode) _green).setParent(this);
        this._blue = _blue;
        ((ASTNode) _blue).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_red);
        list.add(_green);
        list.add(_blue);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof colorDefValueSpec)) return false;
        if (! super.equals(o)) return false;
        colorDefValueSpec other = (colorDefValueSpec) o;
        if (! _red.equals(other._red)) return false;
        if (! _green.equals(other._green)) return false;
        if (! _blue.equals(other._blue)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_red.hashCode());
        hash = hash * 31 + (_green.hashCode());
        hash = hash * 31 + (_blue.hashCode());
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
            _red.accept(v);
            _green.accept(v);
            _blue.accept(v);
        }
        v.endVisit(this);
    }
}


