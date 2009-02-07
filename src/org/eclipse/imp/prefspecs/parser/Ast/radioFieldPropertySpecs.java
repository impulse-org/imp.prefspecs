
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
 *<li>Rule 80:  radioFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 81:  radioFieldPropertySpecs ::= {$ generalSpecs radioSpecificSpec }$
 *</b>
 */
public class radioFieldPropertySpecs extends ASTNode implements IradioFieldPropertySpecs
{
    private generalSpecs _generalSpecs;
    private radioSpecificSpec _radioSpecificSpec;

    public generalSpecs getgeneralSpecs() { return _generalSpecs; }
    public radioSpecificSpec getradioSpecificSpec() { return _radioSpecificSpec; }

    public radioFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                   generalSpecs _generalSpecs,
                                   radioSpecificSpec _radioSpecificSpec)
    {
        super(leftIToken, rightIToken);

        this._generalSpecs = _generalSpecs;
        ((ASTNode) _generalSpecs).setParent(this);
        this._radioSpecificSpec = _radioSpecificSpec;
        ((ASTNode) _radioSpecificSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_generalSpecs);
        list.add(_radioSpecificSpec);
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
        if (! (o instanceof radioFieldPropertySpecs)) return false;
        radioFieldPropertySpecs other = (radioFieldPropertySpecs) o;
        if (! _generalSpecs.equals(other._generalSpecs)) return false;
        if (! _radioSpecificSpec.equals(other._radioSpecificSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_generalSpecs.hashCode());
        hash = hash * 31 + (_radioSpecificSpec.hashCode());
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
            _radioSpecificSpec.accept(v);
        }
        v.endVisit(this);
    }
}


