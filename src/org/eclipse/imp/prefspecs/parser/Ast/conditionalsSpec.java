
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
 *<li>Rule 173:  conditionalsSpec ::= CONDITIONALS$ {$ conditionalSpecs }$
 *</b>
 */
public class conditionalsSpec extends ASTNode implements IconditionalsSpec
{
    private IconditionalSpecs _conditionalSpecs;

    /**
     * The value returned by <b>getconditionalSpecs</b> may be <b>null</b>
     */
    public IconditionalSpecs getconditionalSpecs() { return _conditionalSpecs; }

    public conditionalsSpec(IToken leftIToken, IToken rightIToken,
                            IconditionalSpecs _conditionalSpecs)
    {
        super(leftIToken, rightIToken);

        this._conditionalSpecs = _conditionalSpecs;
        if (_conditionalSpecs != null) ((ASTNode) _conditionalSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_conditionalSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof conditionalsSpec)) return false;
        if (! super.equals(o)) return false;
        conditionalsSpec other = (conditionalsSpec) o;
        if (_conditionalSpecs == null)
            if (other._conditionalSpecs != null) return false;
            else; // continue
        else if (! _conditionalSpecs.equals(other._conditionalSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_conditionalSpecs == null ? 0 : _conditionalSpecs.hashCode());
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
            if (_conditionalSpecs != null) _conditionalSpecs.accept(v);
        v.endVisit(this);
    }
}


