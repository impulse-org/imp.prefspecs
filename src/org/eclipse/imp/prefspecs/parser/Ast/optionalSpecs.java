
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
 *<li>Rule 21:  optionalSpecs ::= customSpecOption conditionalsSpecOption
 *</b>
 */
public class optionalSpecs extends ASTNode implements IoptionalSpecs
{
    private customSpec _customSpecOption;
    private conditionalsSpec _conditionalsSpecOption;

    /**
     * The value returned by <b>getcustomSpecOption</b> may be <b>null</b>
     */
    public customSpec getcustomSpecOption() { return _customSpecOption; }
    /**
     * The value returned by <b>getconditionalsSpecOption</b> may be <b>null</b>
     */
    public conditionalsSpec getconditionalsSpecOption() { return _conditionalsSpecOption; }

    public optionalSpecs(IToken leftIToken, IToken rightIToken,
                         customSpec _customSpecOption,
                         conditionalsSpec _conditionalsSpecOption)
    {
        super(leftIToken, rightIToken);

        this._customSpecOption = _customSpecOption;
        if (_customSpecOption != null) ((ASTNode) _customSpecOption).setParent(this);
        this._conditionalsSpecOption = _conditionalsSpecOption;
        if (_conditionalsSpecOption != null) ((ASTNode) _conditionalsSpecOption).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_customSpecOption);
        list.add(_conditionalsSpecOption);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof optionalSpecs)) return false;
        if (! super.equals(o)) return false;
        optionalSpecs other = (optionalSpecs) o;
        if (_customSpecOption == null)
            if (other._customSpecOption != null) return false;
            else; // continue
        else if (! _customSpecOption.equals(other._customSpecOption)) return false;
        if (_conditionalsSpecOption == null)
            if (other._conditionalsSpecOption != null) return false;
            else; // continue
        else if (! _conditionalsSpecOption.equals(other._conditionalsSpecOption)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_customSpecOption == null ? 0 : _customSpecOption.hashCode());
        hash = hash * 31 + (_conditionalsSpecOption == null ? 0 : _conditionalsSpecOption.hashCode());
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
            if (_customSpecOption != null) _customSpecOption.accept(v);
            if (_conditionalsSpecOption != null) _conditionalsSpecOption.accept(v);
        }
        v.endVisit(this);
    }
}


