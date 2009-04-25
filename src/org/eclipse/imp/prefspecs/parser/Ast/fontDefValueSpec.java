
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
 *<li>Rule 128:  fontDefValueSpec ::= DEFVALUE$ stringValue$name INTEGER$height fontStyle$style ;$
 *</b>
 */
public class fontDefValueSpec extends ASTNode implements IfontDefValueSpec
{
    private stringValue _name;
    private ASTNodeToken _height;
    private IfontStyle _style;

    public stringValue getname() { return _name; }
    public ASTNodeToken getheight() { return _height; }
    public IfontStyle getstyle() { return _style; }

    public fontDefValueSpec(IToken leftIToken, IToken rightIToken,
                            stringValue _name,
                            ASTNodeToken _height,
                            IfontStyle _style)
    {
        super(leftIToken, rightIToken);

        this._name = _name;
        ((ASTNode) _name).setParent(this);
        this._height = _height;
        ((ASTNode) _height).setParent(this);
        this._style = _style;
        ((ASTNode) _style).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_name);
        list.add(_height);
        list.add(_style);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof fontDefValueSpec)) return false;
        if (! super.equals(o)) return false;
        fontDefValueSpec other = (fontDefValueSpec) o;
        if (! _name.equals(other._name)) return false;
        if (! _height.equals(other._height)) return false;
        if (! _style.equals(other._style)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_name.hashCode());
        hash = hash * 31 + (_height.hashCode());
        hash = hash * 31 + (_style.hashCode());
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
            _name.accept(v);
            _height.accept(v);
            _style.accept(v);
        }
        v.endVisit(this);
    }
}


