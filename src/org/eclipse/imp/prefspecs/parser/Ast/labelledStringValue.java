
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
 *<li>Rule 151:  labelledStringValue ::= identifier optLabel
 *</b>
 */
public class labelledStringValue extends ASTNode implements IlabelledStringValue
{
    private identifier _identifier;
    private stringValue _optLabel;

    public identifier getidentifier() { return _identifier; }
    /**
     * The value returned by <b>getoptLabel</b> may be <b>null</b>
     */
    public stringValue getoptLabel() { return _optLabel; }

    public labelledStringValue(IToken leftIToken, IToken rightIToken,
                               identifier _identifier,
                               stringValue _optLabel)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._optLabel = _optLabel;
        if (_optLabel != null) ((ASTNode) _optLabel).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_identifier);
        list.add(_optLabel);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof labelledStringValue)) return false;
        if (! super.equals(o)) return false;
        labelledStringValue other = (labelledStringValue) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (_optLabel == null)
            if (other._optLabel != null) return false;
            else; // continue
        else if (! _optLabel.equals(other._optLabel)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_optLabel == null ? 0 : _optLabel.hashCode());
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
            if (_optLabel != null) _optLabel.accept(v);
        }
        v.endVisit(this);
    }
}


