
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
 *<li>Rule 139:  intDefValueSpec ::= DEFVALUE$ signedNumber ;$
 *</b>
 */
public class intDefValueSpec extends ASTNode implements IintDefValueSpec
{
    private IsignedNumber _signedNumber;

    public IsignedNumber getsignedNumber() { return _signedNumber; }

    public intDefValueSpec(IToken leftIToken, IToken rightIToken,
                           IsignedNumber _signedNumber)
    {
        super(leftIToken, rightIToken);

        this._signedNumber = _signedNumber;
        ((ASTNode) _signedNumber).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_signedNumber);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof intDefValueSpec)) return false;
        if (! super.equals(o)) return false;
        intDefValueSpec other = (intDefValueSpec) o;
        if (! _signedNumber.equals(other._signedNumber)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_signedNumber.hashCode());
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
            _signedNumber.accept(v);
        v.endVisit(this);
    }
}


