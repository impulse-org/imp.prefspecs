
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
 *<em>
 *<li>Rule 68:  comboFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 69:  comboFieldPropertySpecs ::= {$ generalSpecs comboSpecificSpec }$
 *</b>
 */
public class comboFieldPropertySpecs extends ASTNode implements IcomboFieldPropertySpecs
{
    private generalSpecs _generalSpecs;
    private comboSpecificSpec _comboSpecificSpec;

    public generalSpecs getgeneralSpecs() { return _generalSpecs; }
    public comboSpecificSpec getcomboSpecificSpec() { return _comboSpecificSpec; }

    public comboFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                   generalSpecs _generalSpecs,
                                   comboSpecificSpec _comboSpecificSpec)
    {
        super(leftIToken, rightIToken);

        this._generalSpecs = _generalSpecs;
        ((ASTNode) _generalSpecs).setParent(this);
        this._comboSpecificSpec = _comboSpecificSpec;
        ((ASTNode) _comboSpecificSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_generalSpecs);
        list.add(_comboSpecificSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof comboFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        comboFieldPropertySpecs other = (comboFieldPropertySpecs) o;
        if (! _generalSpecs.equals(other._generalSpecs)) return false;
        if (! _comboSpecificSpec.equals(other._comboSpecificSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_generalSpecs.hashCode());
        hash = hash * 31 + (_comboSpecificSpec.hashCode());
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
            _generalSpecs.accept(v);
            _comboSpecificSpec.accept(v);
        }
        v.endVisit(this);
    }
}


