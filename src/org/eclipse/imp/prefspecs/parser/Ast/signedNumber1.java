
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
 *<li>Rule 176:  signedNumber ::= sign INTEGER
 *</b>
 */
public class signedNumber1 extends ASTNode implements IsignedNumber
{
    private Isign _sign;
    private ASTNodeToken _INTEGER;

    public Isign getsign() { return _sign; }
    public ASTNodeToken getINTEGER() { return _INTEGER; }

    public signedNumber1(IToken leftIToken, IToken rightIToken,
                         Isign _sign,
                         ASTNodeToken _INTEGER)
    {
        super(leftIToken, rightIToken);

        this._sign = _sign;
        ((ASTNode) _sign).setParent(this);
        this._INTEGER = _INTEGER;
        ((ASTNode) _INTEGER).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_sign);
        list.add(_INTEGER);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof signedNumber1)) return false;
        if (! super.equals(o)) return false;
        signedNumber1 other = (signedNumber1) o;
        if (! _sign.equals(other._sign)) return false;
        if (! _INTEGER.equals(other._INTEGER)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_sign.hashCode());
        hash = hash * 31 + (_INTEGER.hashCode());
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
            _sign.accept(v);
            _INTEGER.accept(v);
        }
        v.endVisit(this);
    }
}


