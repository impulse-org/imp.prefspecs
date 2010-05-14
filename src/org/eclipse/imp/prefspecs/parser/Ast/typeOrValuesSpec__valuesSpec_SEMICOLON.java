
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
 *<li>Rule 144:  typeOrValuesSpec ::= valuesSpec ;$
 *</b>
 */
public class typeOrValuesSpec__valuesSpec_SEMICOLON extends ASTNode implements ItypeOrValuesSpec
{
    private valuesSpec _valuesSpec;

    public valuesSpec getvaluesSpec() { return _valuesSpec; }

    public typeOrValuesSpec__valuesSpec_SEMICOLON(IToken leftIToken, IToken rightIToken,
                                                  valuesSpec _valuesSpec)
    {
        super(leftIToken, rightIToken);

        this._valuesSpec = _valuesSpec;
        ((ASTNode) _valuesSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_valuesSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof typeOrValuesSpec__valuesSpec_SEMICOLON)) return false;
        if (! super.equals(o)) return false;
        typeOrValuesSpec__valuesSpec_SEMICOLON other = (typeOrValuesSpec__valuesSpec_SEMICOLON) o;
        if (! _valuesSpec.equals(other._valuesSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_valuesSpec.hashCode());
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
            _valuesSpec.accept(v);
        v.endVisit(this);
    }
}


