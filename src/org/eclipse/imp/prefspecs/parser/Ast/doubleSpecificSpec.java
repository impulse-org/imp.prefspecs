
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
 *<li>Rule 155:  doubleSpecificSpec ::= doubleCustomSpec doubleDefValueSpec
 *</b>
 */
public class doubleSpecificSpec extends ASTNode implements IdoubleSpecificSpec
{
    private doubleRangeSpec _doubleCustomSpec;
    private doubleDefValueSpec _doubleDefValueSpec;

    /**
     * The value returned by <b>getdoubleCustomSpec</b> may be <b>null</b>
     */
    public doubleRangeSpec getdoubleCustomSpec() { return _doubleCustomSpec; }
    /**
     * The value returned by <b>getdoubleDefValueSpec</b> may be <b>null</b>
     */
    public doubleDefValueSpec getdoubleDefValueSpec() { return _doubleDefValueSpec; }

    public doubleSpecificSpec(IToken leftIToken, IToken rightIToken,
                              doubleRangeSpec _doubleCustomSpec,
                              doubleDefValueSpec _doubleDefValueSpec)
    {
        super(leftIToken, rightIToken);

        this._doubleCustomSpec = _doubleCustomSpec;
        if (_doubleCustomSpec != null) ((ASTNode) _doubleCustomSpec).setParent(this);
        this._doubleDefValueSpec = _doubleDefValueSpec;
        if (_doubleDefValueSpec != null) ((ASTNode) _doubleDefValueSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_doubleCustomSpec);
        list.add(_doubleDefValueSpec);
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
        if (! (o instanceof doubleSpecificSpec)) return false;
        doubleSpecificSpec other = (doubleSpecificSpec) o;
        if (_doubleCustomSpec == null)
            if (other._doubleCustomSpec != null) return false;
            else; // continue
        else if (! _doubleCustomSpec.equals(other._doubleCustomSpec)) return false;
        if (_doubleDefValueSpec == null)
            if (other._doubleDefValueSpec != null) return false;
            else; // continue
        else if (! _doubleDefValueSpec.equals(other._doubleDefValueSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_doubleCustomSpec == null ? 0 : _doubleCustomSpec.hashCode());
        hash = hash * 31 + (_doubleDefValueSpec == null ? 0 : _doubleDefValueSpec.hashCode());
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
            if (_doubleCustomSpec != null) _doubleCustomSpec.accept(v);
            if (_doubleDefValueSpec != null) _doubleDefValueSpec.accept(v);
        }
        v.endVisit(this);
    }
}


