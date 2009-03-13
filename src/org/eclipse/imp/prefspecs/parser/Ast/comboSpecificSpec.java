
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
import java.util.Hashtable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *<b>
 *<li>Rule 120:  comboSpecificSpec ::= comboCustomSpec comboDefValueSpec
 *</b>
 */
public class comboSpecificSpec extends ASTNode implements IcomboSpecificSpec
{
    private comboCustomSpec _comboCustomSpec;
    private comboDefValueSpec _comboDefValueSpec;

    public comboCustomSpec getcomboCustomSpec() { return _comboCustomSpec; }
    /**
     * The value returned by <b>getcomboDefValueSpec</b> may be <b>null</b>
     */
    public comboDefValueSpec getcomboDefValueSpec() { return _comboDefValueSpec; }

    public comboSpecificSpec(IToken leftIToken, IToken rightIToken,
                             comboCustomSpec _comboCustomSpec,
                             comboDefValueSpec _comboDefValueSpec)
    {
        super(leftIToken, rightIToken);

        this._comboCustomSpec = _comboCustomSpec;
        ((ASTNode) _comboCustomSpec).setParent(this);
        this._comboDefValueSpec = _comboDefValueSpec;
        if (_comboDefValueSpec != null) ((ASTNode) _comboDefValueSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_comboCustomSpec);
        list.add(_comboDefValueSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof comboSpecificSpec)) return false;
        if (! super.equals(o)) return false;
        comboSpecificSpec other = (comboSpecificSpec) o;
        if (! _comboCustomSpec.equals(other._comboCustomSpec)) return false;
        if (_comboDefValueSpec == null)
            if (other._comboDefValueSpec != null) return false;
            else; // continue
        else if (! _comboDefValueSpec.equals(other._comboDefValueSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_comboCustomSpec.hashCode());
        hash = hash * 31 + (_comboDefValueSpec == null ? 0 : _comboDefValueSpec.hashCode());
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
            _comboCustomSpec.accept(v);
            if (_comboDefValueSpec != null) _comboDefValueSpec.accept(v);
        }
        v.endVisit(this);
    }
}


