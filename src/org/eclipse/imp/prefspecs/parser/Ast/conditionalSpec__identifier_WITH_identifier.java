
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
 *<li>Rule 180:  conditionalSpec ::= identifier WITH identifier
 *</b>
 */
public class conditionalSpec__identifier_WITH_identifier extends ASTNode implements IconditionalSpec
{
    private identifier _identifier;
    private ASTNodeToken _WITH;
    private identifier _identifier3;

    public identifier getidentifier() { return _identifier; }
    public ASTNodeToken getWITH() { return _WITH; }
    public identifier getidentifier3() { return _identifier3; }

    public conditionalSpec__identifier_WITH_identifier(IToken leftIToken, IToken rightIToken,
                                                       identifier _identifier,
                                                       ASTNodeToken _WITH,
                                                       identifier _identifier3)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._WITH = _WITH;
        ((ASTNode) _WITH).setParent(this);
        this._identifier3 = _identifier3;
        ((ASTNode) _identifier3).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_identifier);
        list.add(_WITH);
        list.add(_identifier3);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof conditionalSpec__identifier_WITH_identifier)) return false;
        if (! super.equals(o)) return false;
        conditionalSpec__identifier_WITH_identifier other = (conditionalSpec__identifier_WITH_identifier) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _WITH.equals(other._WITH)) return false;
        if (! _identifier3.equals(other._identifier3)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_WITH.hashCode());
        hash = hash * 31 + (_identifier3.hashCode());
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
            _WITH.accept(v);
            _identifier3.accept(v);
        }
        v.endVisit(this);
    }
}


