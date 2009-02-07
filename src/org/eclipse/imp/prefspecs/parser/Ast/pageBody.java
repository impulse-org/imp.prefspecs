
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
 *<li>Rule 19:  pageBody ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 20:  pageBody ::= tabsSpec fieldsSpec optionalSpecs
 *</b>
 */
public class pageBody extends ASTNode implements IpageBody
{
    private tabsSpec _tabsSpec;
    private fieldsSpec _fieldsSpec;
    private optionalSpecs _optionalSpecs;

    /**
     * The value returned by <b>gettabsSpec</b> may be <b>null</b>
     */
    public tabsSpec gettabsSpec() { return _tabsSpec; }
    public fieldsSpec getfieldsSpec() { return _fieldsSpec; }
    public optionalSpecs getoptionalSpecs() { return _optionalSpecs; }

    public pageBody(IToken leftIToken, IToken rightIToken,
                    tabsSpec _tabsSpec,
                    fieldsSpec _fieldsSpec,
                    optionalSpecs _optionalSpecs)
    {
        super(leftIToken, rightIToken);

        this._tabsSpec = _tabsSpec;
        if (_tabsSpec != null) ((ASTNode) _tabsSpec).setParent(this);
        this._fieldsSpec = _fieldsSpec;
        ((ASTNode) _fieldsSpec).setParent(this);
        this._optionalSpecs = _optionalSpecs;
        ((ASTNode) _optionalSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_tabsSpec);
        list.add(_fieldsSpec);
        list.add(_optionalSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof pageBody)) return false;
        if (! super.equals(o)) return false;
        pageBody other = (pageBody) o;
        if (_tabsSpec == null)
            if (other._tabsSpec != null) return false;
            else; // continue
        else if (! _tabsSpec.equals(other._tabsSpec)) return false;
        if (! _fieldsSpec.equals(other._fieldsSpec)) return false;
        if (! _optionalSpecs.equals(other._optionalSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_tabsSpec == null ? 0 : _tabsSpec.hashCode());
        hash = hash * 31 + (_fieldsSpec.hashCode());
        hash = hash * 31 + (_optionalSpecs.hashCode());
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
            if (_tabsSpec != null) _tabsSpec.accept(v);
            _fieldsSpec.accept(v);
            _optionalSpecs.accept(v);
        }
        v.endVisit(this);
    }
}


