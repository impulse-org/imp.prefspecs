
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
 *<li>Rule 26:  tabsSpec ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 27:  tabsSpec ::= TABS$ {$ tabSpecs }$
 *</b>
 */
public class tabsSpec extends ASTNode implements ItabsSpec
{
    private tabSpecs _tabSpecs;

    /**
     * The value returned by <b>gettabSpecs</b> may be <b>null</b>
     */
    public tabSpecs gettabSpecs() { return _tabSpecs; }

    public tabsSpec(IToken leftIToken, IToken rightIToken,
                    tabSpecs _tabSpecs)
    {
        super(leftIToken, rightIToken);

        this._tabSpecs = _tabSpecs;
        if (_tabSpecs != null) ((ASTNode) _tabSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_tabSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        //
        // The super call test is not required for now because an Ast node
        // can only extend the root Ast, AstToken and AstList and none of
        // these nodes contain additional children.
        //
        // if (! super.equals(o)) return false;
        //
        if (! (o instanceof tabsSpec)) return false;
        tabsSpec other = (tabsSpec) o;
        if (_tabSpecs == null)
            if (other._tabSpecs != null) return false;
            else; // continue
        else if (! _tabSpecs.equals(other._tabSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_tabSpecs == null ? 0 : _tabSpecs.hashCode());
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
            if (_tabSpecs != null) _tabSpecs.accept(v);
        v.endVisit(this);
    }
}


