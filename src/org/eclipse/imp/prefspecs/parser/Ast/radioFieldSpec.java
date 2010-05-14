
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
 *<li>Rule 62:  radioFieldSpec ::= RADIO$ identifier radioFieldPropertySpecs optConditionalSpec
 *</b>
 */
public class radioFieldSpec extends ASTNode implements IradioFieldSpec
{
    private identifier _identifier;
    private radioFieldPropertySpecs _radioFieldPropertySpecs;
    private optConditionalSpec _optConditionalSpec;

    public identifier getidentifier() { return _identifier; }
    /**
     * The value returned by <b>getradioFieldPropertySpecs</b> may be <b>null</b>
     */
    public radioFieldPropertySpecs getradioFieldPropertySpecs() { return _radioFieldPropertySpecs; }
    /**
     * The value returned by <b>getoptConditionalSpec</b> may be <b>null</b>
     */
    public optConditionalSpec getoptConditionalSpec() { return _optConditionalSpec; }

    public radioFieldSpec(IToken leftIToken, IToken rightIToken,
                          identifier _identifier,
                          radioFieldPropertySpecs _radioFieldPropertySpecs,
                          optConditionalSpec _optConditionalSpec)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._radioFieldPropertySpecs = _radioFieldPropertySpecs;
        if (_radioFieldPropertySpecs != null) ((ASTNode) _radioFieldPropertySpecs).setParent(this);
        this._optConditionalSpec = _optConditionalSpec;
        if (_optConditionalSpec != null) ((ASTNode) _optConditionalSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_identifier);
        list.add(_radioFieldPropertySpecs);
        list.add(_optConditionalSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof radioFieldSpec)) return false;
        if (! super.equals(o)) return false;
        radioFieldSpec other = (radioFieldSpec) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (_radioFieldPropertySpecs == null)
            if (other._radioFieldPropertySpecs != null) return false;
            else; // continue
        else if (! _radioFieldPropertySpecs.equals(other._radioFieldPropertySpecs)) return false;
        if (_optConditionalSpec == null)
            if (other._optConditionalSpec != null) return false;
            else; // continue
        else if (! _optConditionalSpec.equals(other._optConditionalSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_radioFieldPropertySpecs == null ? 0 : _radioFieldPropertySpecs.hashCode());
        hash = hash * 31 + (_optConditionalSpec == null ? 0 : _optConditionalSpec.hashCode());
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
            _identifier.accept(v);
            if (_radioFieldPropertySpecs != null) _radioFieldPropertySpecs.accept(v);
            if (_optConditionalSpec != null) _optConditionalSpec.accept(v);
        }
        v.endVisit(this);
    }
}


