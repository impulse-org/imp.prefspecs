
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
 *<li>Rule 194:  typeCustomSpecs ::= intRangeSpec intSpecialSpec
 *</b>
 */
public class typeCustomSpecs0 extends ASTNode implements ItypeCustomSpecs
{
    private intRangeSpec _intRangeSpec;
    private intSpecialSpec _intSpecialSpec;

    public intRangeSpec getintRangeSpec() { return _intRangeSpec; }
    public intSpecialSpec getintSpecialSpec() { return _intSpecialSpec; }

    public typeCustomSpecs0(IToken leftIToken, IToken rightIToken,
                            intRangeSpec _intRangeSpec,
                            intSpecialSpec _intSpecialSpec)
    {
        super(leftIToken, rightIToken);

        this._intRangeSpec = _intRangeSpec;
        ((ASTNode) _intRangeSpec).setParent(this);
        this._intSpecialSpec = _intSpecialSpec;
        ((ASTNode) _intSpecialSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_intRangeSpec);
        list.add(_intSpecialSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof typeCustomSpecs0)) return false;
        if (! super.equals(o)) return false;
        typeCustomSpecs0 other = (typeCustomSpecs0) o;
        if (! _intRangeSpec.equals(other._intRangeSpec)) return false;
        if (! _intSpecialSpec.equals(other._intSpecialSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_intRangeSpec.hashCode());
        hash = hash * 31 + (_intSpecialSpec.hashCode());
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
            _intRangeSpec.accept(v);
            _intSpecialSpec.accept(v);
        }
        v.endVisit(this);
    }
}


