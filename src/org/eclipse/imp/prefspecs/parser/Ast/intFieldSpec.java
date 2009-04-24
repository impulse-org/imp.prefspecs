
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
 *<li>Rule 63:  intFieldSpec ::= INT$ identifier intFieldPropertySpecs optConditionalSpec
 *</b>
 */
public class intFieldSpec extends ASTNode implements IintFieldSpec
{
    private identifier _identifier;
    private intFieldPropertySpecs _intFieldPropertySpecs;
    private optConditionalSpec _optConditionalSpec;

    public identifier getidentifier() { return _identifier; }
    /**
     * The value returned by <b>getintFieldPropertySpecs</b> may be <b>null</b>
     */
    public intFieldPropertySpecs getintFieldPropertySpecs() { return _intFieldPropertySpecs; }
    /**
     * The value returned by <b>getoptConditionalSpec</b> may be <b>null</b>
     */
    public optConditionalSpec getoptConditionalSpec() { return _optConditionalSpec; }

    public intFieldSpec(IToken leftIToken, IToken rightIToken,
                        identifier _identifier,
                        intFieldPropertySpecs _intFieldPropertySpecs,
                        optConditionalSpec _optConditionalSpec)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._intFieldPropertySpecs = _intFieldPropertySpecs;
        if (_intFieldPropertySpecs != null) ((ASTNode) _intFieldPropertySpecs).setParent(this);
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
        list.add(_intFieldPropertySpecs);
        list.add(_optConditionalSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof intFieldSpec)) return false;
        if (! super.equals(o)) return false;
        intFieldSpec other = (intFieldSpec) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (_intFieldPropertySpecs == null)
            if (other._intFieldPropertySpecs != null) return false;
            else; // continue
        else if (! _intFieldPropertySpecs.equals(other._intFieldPropertySpecs)) return false;
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
        hash = hash * 31 + (_intFieldPropertySpecs == null ? 0 : _intFieldPropertySpecs.hashCode());
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
            if (_intFieldPropertySpecs != null) _intFieldPropertySpecs.accept(v);
            if (_optConditionalSpec != null) _optConditionalSpec.accept(v);
        }
        v.endVisit(this);
    }
}


