
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
 *<li>Rule 40:  fieldsSpec ::= FIELDS$ {$ fieldSpecs }$
 *</b>
 */
public class fieldsSpec extends ASTNode implements IfieldsSpec
{
    private IfieldSpecs _fieldSpecs;

    /**
     * The value returned by <b>getfieldSpecs</b> may be <b>null</b>
     */
    public IfieldSpecs getfieldSpecs() { return _fieldSpecs; }

    public fieldsSpec(IToken leftIToken, IToken rightIToken,
                      IfieldSpecs _fieldSpecs)
    {
        super(leftIToken, rightIToken);

        this._fieldSpecs = _fieldSpecs;
        if (_fieldSpecs != null) ((ASTNode) _fieldSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_fieldSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof fieldsSpec)) return false;
        if (! super.equals(o)) return false;
        fieldsSpec other = (fieldsSpec) o;
        if (_fieldSpecs == null)
            if (other._fieldSpecs != null) return false;
            else; // continue
        else if (! _fieldSpecs.equals(other._fieldSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_fieldSpecs == null ? 0 : _fieldSpecs.hashCode());
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
            if (_fieldSpecs != null) _fieldSpecs.accept(v);
        v.endVisit(this);
    }
}


