
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
 *<li>Rule 82:  stringFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 83:  stringFieldPropertySpecs ::= {$ generalSpecs stringSpecificSpec }$
 *</b>
 */
public class stringFieldPropertySpecs extends ASTNode implements IstringFieldPropertySpecs
{
    private generalSpecs _generalSpecs;
    private stringSpecificSpec _stringSpecificSpec;

    public generalSpecs getgeneralSpecs() { return _generalSpecs; }
    public stringSpecificSpec getstringSpecificSpec() { return _stringSpecificSpec; }

    public stringFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                    generalSpecs _generalSpecs,
                                    stringSpecificSpec _stringSpecificSpec)
    {
        super(leftIToken, rightIToken);

        this._generalSpecs = _generalSpecs;
        ((ASTNode) _generalSpecs).setParent(this);
        this._stringSpecificSpec = _stringSpecificSpec;
        ((ASTNode) _stringSpecificSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_generalSpecs);
        list.add(_stringSpecificSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof stringFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        stringFieldPropertySpecs other = (stringFieldPropertySpecs) o;
        if (! _generalSpecs.equals(other._generalSpecs)) return false;
        if (! _stringSpecificSpec.equals(other._stringSpecificSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_generalSpecs.hashCode());
        hash = hash * 31 + (_stringSpecificSpec.hashCode());
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
            _stringSpecificSpec.accept(v);
        }
        v.endVisit(this);
    }
}


