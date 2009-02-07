
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
 *<em>
 *<li>Rule 159:  doubleDefValueSpec ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 160:  doubleDefValueSpec ::= DEFVALUE$ DECIMAL ;$
 *</b>
 */
public class doubleDefValueSpec extends ASTNode implements IdoubleDefValueSpec
{
    private ASTNodeToken _DECIMAL;

    public ASTNodeToken getDECIMAL() { return _DECIMAL; }

    public doubleDefValueSpec(IToken leftIToken, IToken rightIToken,
                              ASTNodeToken _DECIMAL)
    {
        super(leftIToken, rightIToken);

        this._DECIMAL = _DECIMAL;
        ((ASTNode) _DECIMAL).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_DECIMAL);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof doubleDefValueSpec)) return false;
        if (! super.equals(o)) return false;
        doubleDefValueSpec other = (doubleDefValueSpec) o;
        if (! _DECIMAL.equals(other._DECIMAL)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_DECIMAL.hashCode());
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
            _DECIMAL.accept(v);
        v.endVisit(this);
    }
}


