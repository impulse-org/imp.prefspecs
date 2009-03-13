
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
 *<li>Rule 17:  pagePath ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 18:  pagePath ::= pagePath identifier .$
 *</b>
 */
public class pagePath extends ASTNode implements IpagePath
{
    private pagePath _pagePath;
    private identifier _identifier;

    /**
     * The value returned by <b>getpagePath</b> may be <b>null</b>
     */
    public pagePath getpagePath() { return _pagePath; }
    public identifier getidentifier() { return _identifier; }

    public pagePath(IToken leftIToken, IToken rightIToken,
                    pagePath _pagePath,
                    identifier _identifier)
    {
        super(leftIToken, rightIToken);

        this._pagePath = _pagePath;
        if (_pagePath != null) ((ASTNode) _pagePath).setParent(this);
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
        list.add(_pagePath);
        list.add(_identifier);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof pagePath)) return false;
        if (! super.equals(o)) return false;
        pagePath other = (pagePath) o;
        if (_pagePath == null)
            if (other._pagePath != null) return false;
            else; // continue
        else if (! _pagePath.equals(other._pagePath)) return false;
        if (! _identifier.equals(other._identifier)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_pagePath == null ? 0 : _pagePath.hashCode());
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
            if (_pagePath != null) _pagePath.accept(v);
            _identifier.accept(v);
        }
        v.endVisit(this);
    }
}


