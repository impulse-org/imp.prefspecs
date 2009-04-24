
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
 *<li>Rule 15:  pageSpec ::= PAGE$ pageName {$ pageBody }$
 *</b>
 */
public class pageSpec extends ASTNode implements IpageSpec
{
    private pageName _pageName;
    private pageBody _pageBody;

    public pageName getpageName() { return _pageName; }
    /**
     * The value returned by <b>getpageBody</b> may be <b>null</b>
     */
    public pageBody getpageBody() { return _pageBody; }

    public pageSpec(IToken leftIToken, IToken rightIToken,
                    pageName _pageName,
                    pageBody _pageBody)
    {
        super(leftIToken, rightIToken);

        this._pageName = _pageName;
        ((ASTNode) _pageName).setParent(this);
        this._pageBody = _pageBody;
        if (_pageBody != null) ((ASTNode) _pageBody).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_pageName);
        list.add(_pageBody);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof pageSpec)) return false;
        if (! super.equals(o)) return false;
        pageSpec other = (pageSpec) o;
        if (! _pageName.equals(other._pageName)) return false;
        if (_pageBody == null)
            if (other._pageBody != null) return false;
            else; // continue
        else if (! _pageBody.equals(other._pageBody)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_pageName.hashCode());
        hash = hash * 31 + (_pageBody == null ? 0 : _pageBody.hashCode());
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
            _pageName.accept(v);
            if (_pageBody != null) _pageBody.accept(v);
        }
        v.endVisit(this);
    }
}


