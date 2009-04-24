
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
 *<li>Rule 16:  pageName ::= pagePath identifier$name
 *</b>
 */
public class pageName extends ASTNode implements IpageName
{
    private pagePath _pagePath;
    private identifier _name;

    /**
     * The value returned by <b>getpagePath</b> may be <b>null</b>
     */
    public pagePath getpagePath() { return _pagePath; }
    public identifier getname() { return _name; }

    public pageName(IToken leftIToken, IToken rightIToken,
                    pagePath _pagePath,
                    identifier _name)
    {
        super(leftIToken, rightIToken);

        this._pagePath = _pagePath;
        if (_pagePath != null) ((ASTNode) _pagePath).setParent(this);
        this._name = _name;
        ((ASTNode) _name).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_pagePath);
        list.add(_name);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof pageName)) return false;
        if (! super.equals(o)) return false;
        pageName other = (pageName) o;
        if (_pagePath == null)
            if (other._pagePath != null) return false;
            else; // continue
        else if (! _pagePath.equals(other._pagePath)) return false;
        if (! _name.equals(other._name)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_pagePath == null ? 0 : _pagePath.hashCode());
        hash = hash * 31 + (_name.hashCode());
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
            if (_pagePath != null) _pagePath.accept(v);
            _name.accept(v);
        }
        v.endVisit(this);
    }
}


