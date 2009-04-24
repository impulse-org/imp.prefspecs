
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
 *<li>Rule 2:  optPackageSpec ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 3:  optPackageSpec ::= PACKAGE$ packageName ;$
 *</b>
 */
public class optPackageSpec extends ASTNode implements IoptPackageSpec
{
    private IpackageName _packageName;

    public IpackageName getpackageName() { return _packageName; }

    public optPackageSpec(IToken leftIToken, IToken rightIToken,
                          IpackageName _packageName)
    {
        super(leftIToken, rightIToken);

        this._packageName = _packageName;
        ((ASTNode) _packageName).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_packageName);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof optPackageSpec)) return false;
        if (! super.equals(o)) return false;
        optPackageSpec other = (optPackageSpec) o;
        if (! _packageName.equals(other._packageName)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_packageName.hashCode());
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
            _packageName.accept(v);
        v.endVisit(this);
    }
}


