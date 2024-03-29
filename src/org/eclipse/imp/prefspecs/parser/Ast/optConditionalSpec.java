
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
 *<em>
 *<li>Rule 161:  optConditionalSpec ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 162:  optConditionalSpec ::= conditionType identifier
 *</b>
 */
public class optConditionalSpec extends ASTNode implements IoptConditionalSpec
{
    private IconditionType _conditionType;
    private identifier _identifier;

    public IconditionType getconditionType() { return _conditionType; }
    public identifier getidentifier() { return _identifier; }

    public optConditionalSpec(IToken leftIToken, IToken rightIToken,
                              IconditionType _conditionType,
                              identifier _identifier)
    {
        super(leftIToken, rightIToken);

        this._conditionType = _conditionType;
        ((ASTNode) _conditionType).setParent(this);
        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_conditionType);
        list.add(_identifier);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof optConditionalSpec)) return false;
        if (! super.equals(o)) return false;
        optConditionalSpec other = (optConditionalSpec) o;
        if (! _conditionType.equals(other._conditionType)) return false;
        if (! _identifier.equals(other._identifier)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_conditionType.hashCode());
        hash = hash * 31 + (_identifier.hashCode());
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
            _conditionType.accept(v);
            _identifier.accept(v);
        }
        v.endVisit(this);
    }
}


