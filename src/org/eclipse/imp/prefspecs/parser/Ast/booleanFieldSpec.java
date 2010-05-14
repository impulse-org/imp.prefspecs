
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
 *<li>Rule 53:  booleanFieldSpec ::= BOOLEAN$ identifier booleanFieldPropertySpecs optConditionalSpec
 *</b>
 */
public class booleanFieldSpec extends ASTNode implements IbooleanFieldSpec
{
    private identifier _identifier;
    private booleanFieldPropertySpecs _booleanFieldPropertySpecs;
    private optConditionalSpec _optConditionalSpec;

    public identifier getidentifier() { return _identifier; }
    /**
     * The value returned by <b>getbooleanFieldPropertySpecs</b> may be <b>null</b>
     */
    public booleanFieldPropertySpecs getbooleanFieldPropertySpecs() { return _booleanFieldPropertySpecs; }
    /**
     * The value returned by <b>getoptConditionalSpec</b> may be <b>null</b>
     */
    public optConditionalSpec getoptConditionalSpec() { return _optConditionalSpec; }

    public booleanFieldSpec(IToken leftIToken, IToken rightIToken,
                            identifier _identifier,
                            booleanFieldPropertySpecs _booleanFieldPropertySpecs,
                            optConditionalSpec _optConditionalSpec)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._booleanFieldPropertySpecs = _booleanFieldPropertySpecs;
        if (_booleanFieldPropertySpecs != null) ((ASTNode) _booleanFieldPropertySpecs).setParent(this);
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
        list.add(_booleanFieldPropertySpecs);
        list.add(_optConditionalSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof booleanFieldSpec)) return false;
        if (! super.equals(o)) return false;
        booleanFieldSpec other = (booleanFieldSpec) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (_booleanFieldPropertySpecs == null)
            if (other._booleanFieldPropertySpecs != null) return false;
            else; // continue
        else if (! _booleanFieldPropertySpecs.equals(other._booleanFieldPropertySpecs)) return false;
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
        hash = hash * 31 + (_booleanFieldPropertySpecs == null ? 0 : _booleanFieldPropertySpecs.hashCode());
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
            if (_booleanFieldPropertySpecs != null) _booleanFieldPropertySpecs.accept(v);
            if (_optConditionalSpec != null) _optConditionalSpec.accept(v);
        }
        v.endVisit(this);
    }
}


