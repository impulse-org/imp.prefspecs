
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
 *<li>Rule 121:  comboCustomSpec ::= columnsSpec typeOrValuesSpec
 *</b>
 */
public class comboCustomSpec extends ASTNode implements IcomboCustomSpec
{
    private columnsSpec _columnsSpec;
    private ItypeOrValuesSpec _typeOrValuesSpec;

    /**
     * The value returned by <b>getcolumnsSpec</b> may be <b>null</b>
     */
    public columnsSpec getcolumnsSpec() { return _columnsSpec; }
    public ItypeOrValuesSpec gettypeOrValuesSpec() { return _typeOrValuesSpec; }

    public comboCustomSpec(IToken leftIToken, IToken rightIToken,
                           columnsSpec _columnsSpec,
                           ItypeOrValuesSpec _typeOrValuesSpec)
    {
        super(leftIToken, rightIToken);

        this._columnsSpec = _columnsSpec;
        if (_columnsSpec != null) ((ASTNode) _columnsSpec).setParent(this);
        this._typeOrValuesSpec = _typeOrValuesSpec;
        ((ASTNode) _typeOrValuesSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_columnsSpec);
        list.add(_typeOrValuesSpec);
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
        if (! (o instanceof comboCustomSpec)) return false;
        comboCustomSpec other = (comboCustomSpec) o;
        if (_columnsSpec == null)
            if (other._columnsSpec != null) return false;
            else; // continue
        else if (! _columnsSpec.equals(other._columnsSpec)) return false;
        if (! _typeOrValuesSpec.equals(other._typeOrValuesSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_columnsSpec == null ? 0 : _columnsSpec.hashCode());
        hash = hash * 31 + (_typeOrValuesSpec.hashCode());
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
            if (_columnsSpec != null) _columnsSpec.accept(v);
            _typeOrValuesSpec.accept(v);
        }
        v.endVisit(this);
    }
}


