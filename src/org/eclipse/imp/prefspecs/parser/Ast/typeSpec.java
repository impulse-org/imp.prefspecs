
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
 *<li>Rule 14:  typeSpec ::= CHOICETYPE$ identifier {$ staticOrDynamicValues }$
 *</b>
 */
public class typeSpec extends ASTNode implements ItypeSpec
{
    private identifier _identifier;
    private IstaticOrDynamicValues _staticOrDynamicValues;

    public identifier getidentifier() { return _identifier; }
    public IstaticOrDynamicValues getstaticOrDynamicValues() { return _staticOrDynamicValues; }

    public typeSpec(IToken leftIToken, IToken rightIToken,
                    identifier _identifier,
                    IstaticOrDynamicValues _staticOrDynamicValues)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._staticOrDynamicValues = _staticOrDynamicValues;
        ((ASTNode) _staticOrDynamicValues).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_identifier);
        list.add(_staticOrDynamicValues);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof typeSpec)) return false;
        if (! super.equals(o)) return false;
        typeSpec other = (typeSpec) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _staticOrDynamicValues.equals(other._staticOrDynamicValues)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_staticOrDynamicValues.hashCode());
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
            _identifier.accept(v);
            _staticOrDynamicValues.accept(v);
        }
        v.endVisit(this);
    }
}


