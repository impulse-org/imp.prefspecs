
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
 *<li>Rule 4:  packageName ::= identifier
 *</em>
 *<p>
 *<b>
 *<li>Rule 5:  packageName ::= packageName .$ identifier
 *</b>
 */
public class packageName extends ASTNode implements IpackageName
{
    private IpackageName _packageName;
    private identifier _identifier;

    public IpackageName getpackageName() { return _packageName; }
    public identifier getidentifier() { return _identifier; }

    public packageName(IToken leftIToken, IToken rightIToken,
                       IpackageName _packageName,
                       identifier _identifier)
    {
        super(leftIToken, rightIToken);

        this._packageName = _packageName;
        ((ASTNode) _packageName).setParent(this);
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
        list.add(_packageName);
        list.add(_identifier);
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
        if (! (o instanceof packageName)) return false;
        packageName other = (packageName) o;
        if (! _packageName.equals(other._packageName)) return false;
        if (! _identifier.equals(other._identifier)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_packageName.hashCode());
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
            _packageName.accept(v);
            _identifier.accept(v);
        }
        v.endVisit(this);
    }
}


