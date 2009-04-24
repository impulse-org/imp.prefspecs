
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
 *<li>Rule 37:  projectTabSpec ::= PROJECT$ inout {$ generalSpecs }$
 *</b>
 */
public class projectTabSpec extends ASTNode implements IprojectTabSpec
{
    private Iinout _inout;
    private generalSpecList _generalSpecs;

    public Iinout getinout() { return _inout; }
    public generalSpecList getgeneralSpecs() { return _generalSpecs; }

    public projectTabSpec(IToken leftIToken, IToken rightIToken,
                          Iinout _inout,
                          generalSpecList _generalSpecs)
    {
        super(leftIToken, rightIToken);

        this._inout = _inout;
        ((ASTNode) _inout).setParent(this);
        this._generalSpecs = _generalSpecs;
        ((ASTNode) _generalSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_inout);
        list.add(_generalSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof projectTabSpec)) return false;
        if (! super.equals(o)) return false;
        projectTabSpec other = (projectTabSpec) o;
        if (! _inout.equals(other._inout)) return false;
        if (! _generalSpecs.equals(other._generalSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_inout.hashCode());
        hash = hash * 31 + (_generalSpecs.hashCode());
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
            _inout.accept(v);
            _generalSpecs.accept(v);
        }
        v.endVisit(this);
    }
}


