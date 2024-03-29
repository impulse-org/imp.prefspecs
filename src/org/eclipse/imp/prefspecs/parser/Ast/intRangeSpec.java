
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
 *<li>Rule 132:  intRangeSpec ::= RANGE$ signedNumber$low DOTS$ signedNumber$high ;$
 *</b>
 */
public class intRangeSpec extends ASTNode implements IintRangeSpec
{
    private IsignedNumber _low;
    private IsignedNumber _high;

    public IsignedNumber getlow() { return _low; }
    public IsignedNumber gethigh() { return _high; }

    public intRangeSpec(IToken leftIToken, IToken rightIToken,
                        IsignedNumber _low,
                        IsignedNumber _high)
    {
        super(leftIToken, rightIToken);

        this._low = _low;
        ((ASTNode) _low).setParent(this);
        this._high = _high;
        ((ASTNode) _high).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_low);
        list.add(_high);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof intRangeSpec)) return false;
        if (! super.equals(o)) return false;
        intRangeSpec other = (intRangeSpec) o;
        if (! _low.equals(other._low)) return false;
        if (! _high.equals(other._high)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_low.hashCode());
        hash = hash * 31 + (_high.hashCode());
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
            _low.accept(v);
            _high.accept(v);
        }
        v.endVisit(this);
    }
}


