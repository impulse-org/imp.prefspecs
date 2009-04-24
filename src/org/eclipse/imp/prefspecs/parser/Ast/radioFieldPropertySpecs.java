
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
 *<li>Rule 84:  radioFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 85:  radioFieldPropertySpecs ::= {$ radioSpecificSpecs }$
 *</b>
 */
public class radioFieldPropertySpecs extends ASTNode implements IradioFieldPropertySpecs
{
    private radioSpecificSpecList _radioSpecificSpecs;

    public radioSpecificSpecList getradioSpecificSpecs() { return _radioSpecificSpecs; }

    public radioFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                   radioSpecificSpecList _radioSpecificSpecs)
    {
        super(leftIToken, rightIToken);

        this._radioSpecificSpecs = _radioSpecificSpecs;
        ((ASTNode) _radioSpecificSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_radioSpecificSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof radioFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        radioFieldPropertySpecs other = (radioFieldPropertySpecs) o;
        if (! _radioSpecificSpecs.equals(other._radioSpecificSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_radioSpecificSpecs.hashCode());
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
            _radioSpecificSpecs.accept(v);
        v.endVisit(this);
    }
}


