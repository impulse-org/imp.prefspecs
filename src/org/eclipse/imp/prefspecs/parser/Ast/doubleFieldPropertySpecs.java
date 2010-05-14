
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
 *<li>Rule 74:  doubleFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 75:  doubleFieldPropertySpecs ::= {$ doubleSpecificSpecs }$
 *</b>
 */
public class doubleFieldPropertySpecs extends ASTNode implements IdoubleFieldPropertySpecs
{
    private doubleSpecificSpecList _doubleSpecificSpecs;

    public doubleSpecificSpecList getdoubleSpecificSpecs() { return _doubleSpecificSpecs; }

    public doubleFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                    doubleSpecificSpecList _doubleSpecificSpecs)
    {
        super(leftIToken, rightIToken);

        this._doubleSpecificSpecs = _doubleSpecificSpecs;
        ((ASTNode) _doubleSpecificSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_doubleSpecificSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof doubleFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        doubleFieldPropertySpecs other = (doubleFieldPropertySpecs) o;
        if (! _doubleSpecificSpecs.equals(other._doubleSpecificSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_doubleSpecificSpecs.hashCode());
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
            _doubleSpecificSpecs.accept(v);
        v.endVisit(this);
    }
}


