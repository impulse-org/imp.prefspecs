
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
 *<li>Rule 72:  doubleFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 73:  doubleFieldPropertySpecs ::= {$ generalSpecs doubleSpecificSpec }$
 *</b>
 */
public class doubleFieldPropertySpecs extends ASTNode implements IdoubleFieldPropertySpecs
{
    private generalSpecs _generalSpecs;
    private doubleSpecificSpec _doubleSpecificSpec;

    public generalSpecs getgeneralSpecs() { return _generalSpecs; }
    public doubleSpecificSpec getdoubleSpecificSpec() { return _doubleSpecificSpec; }

    public doubleFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                    generalSpecs _generalSpecs,
                                    doubleSpecificSpec _doubleSpecificSpec)
    {
        super(leftIToken, rightIToken);

        this._generalSpecs = _generalSpecs;
        ((ASTNode) _generalSpecs).setParent(this);
        this._doubleSpecificSpec = _doubleSpecificSpec;
        ((ASTNode) _doubleSpecificSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_generalSpecs);
        list.add(_doubleSpecificSpec);
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
        if (! (o instanceof doubleFieldPropertySpecs)) return false;
        doubleFieldPropertySpecs other = (doubleFieldPropertySpecs) o;
        if (! _generalSpecs.equals(other._generalSpecs)) return false;
        if (! _doubleSpecificSpec.equals(other._doubleSpecificSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_generalSpecs.hashCode());
        hash = hash * 31 + (_doubleSpecificSpec.hashCode());
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
            _doubleSpecificSpec.accept(v);
        }
        v.endVisit(this);
    }
}


