
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
 *<li>Rule 114:  booleanSpecificSpec ::= booleanCustomSpec booleanDefValueSpec
 *</b>
 */
public class booleanSpecificSpec extends ASTNode implements IbooleanSpecificSpec
{
    private booleanSpecialSpec _booleanCustomSpec;
    private booleanDefValueSpec _booleanDefValueSpec;

    /**
     * The value returned by <b>getbooleanCustomSpec</b> may be <b>null</b>
     */
    public booleanSpecialSpec getbooleanCustomSpec() { return _booleanCustomSpec; }
    /**
     * The value returned by <b>getbooleanDefValueSpec</b> may be <b>null</b>
     */
    public booleanDefValueSpec getbooleanDefValueSpec() { return _booleanDefValueSpec; }

    public booleanSpecificSpec(IToken leftIToken, IToken rightIToken,
                               booleanSpecialSpec _booleanCustomSpec,
                               booleanDefValueSpec _booleanDefValueSpec)
    {
        super(leftIToken, rightIToken);

        this._booleanCustomSpec = _booleanCustomSpec;
        if (_booleanCustomSpec != null) ((ASTNode) _booleanCustomSpec).setParent(this);
        this._booleanDefValueSpec = _booleanDefValueSpec;
        if (_booleanDefValueSpec != null) ((ASTNode) _booleanDefValueSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_booleanCustomSpec);
        list.add(_booleanDefValueSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        //
        // The super call test is not required for now because an Ast node
        // can only extend the root Ast, AstToken and AstList and none of
        // these nodes contain additional children.
        //
        // if (! super.equals(o)) return false;
        //
        if (! (o instanceof booleanSpecificSpec)) return false;
        booleanSpecificSpec other = (booleanSpecificSpec) o;
        if (_booleanCustomSpec == null)
            if (other._booleanCustomSpec != null) return false;
            else; // continue
        else if (! _booleanCustomSpec.equals(other._booleanCustomSpec)) return false;
        if (_booleanDefValueSpec == null)
            if (other._booleanDefValueSpec != null) return false;
            else; // continue
        else if (! _booleanDefValueSpec.equals(other._booleanDefValueSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_booleanCustomSpec == null ? 0 : _booleanCustomSpec.hashCode());
        hash = hash * 31 + (_booleanDefValueSpec == null ? 0 : _booleanDefValueSpec.hashCode());
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
            if (_booleanCustomSpec != null) _booleanCustomSpec.accept(v);
            if (_booleanDefValueSpec != null) _booleanDefValueSpec.accept(v);
        }
        v.endVisit(this);
    }
}


