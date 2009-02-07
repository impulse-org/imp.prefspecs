
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
 *<li>Rule 147:  intSpecificSpec ::= intCustomSpec intDefValueSpec
 *</b>
 */
public class intSpecificSpec extends ASTNode implements IintSpecificSpec
{
    private intCustomSpec _intCustomSpec;
    private intDefValueSpec _intDefValueSpec;

    public intCustomSpec getintCustomSpec() { return _intCustomSpec; }
    /**
     * The value returned by <b>getintDefValueSpec</b> may be <b>null</b>
     */
    public intDefValueSpec getintDefValueSpec() { return _intDefValueSpec; }

    public intSpecificSpec(IToken leftIToken, IToken rightIToken,
                           intCustomSpec _intCustomSpec,
                           intDefValueSpec _intDefValueSpec)
    {
        super(leftIToken, rightIToken);

        this._intCustomSpec = _intCustomSpec;
        ((ASTNode) _intCustomSpec).setParent(this);
        this._intDefValueSpec = _intDefValueSpec;
        if (_intDefValueSpec != null) ((ASTNode) _intDefValueSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_intCustomSpec);
        list.add(_intDefValueSpec);
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
        if (! (o instanceof intSpecificSpec)) return false;
        intSpecificSpec other = (intSpecificSpec) o;
        if (! _intCustomSpec.equals(other._intCustomSpec)) return false;
        if (_intDefValueSpec == null)
            if (other._intDefValueSpec != null) return false;
            else; // continue
        else if (! _intDefValueSpec.equals(other._intDefValueSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_intCustomSpec.hashCode());
        hash = hash * 31 + (_intDefValueSpec == null ? 0 : _intDefValueSpec.hashCode());
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
            _intCustomSpec.accept(v);
            if (_intDefValueSpec != null) _intDefValueSpec.accept(v);
        }
        v.endVisit(this);
    }
}


