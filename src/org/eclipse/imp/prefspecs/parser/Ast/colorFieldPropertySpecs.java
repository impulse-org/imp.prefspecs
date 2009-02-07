
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
 *<li>Rule 66:  colorFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 67:  colorFieldPropertySpecs ::= {$ generalSpecs colorSpecificSpec }$
 *</b>
 */
public class colorFieldPropertySpecs extends ASTNode implements IcolorFieldPropertySpecs
{
    private generalSpecs _generalSpecs;
    private colorDefValueSpec _colorSpecificSpec;

    public generalSpecs getgeneralSpecs() { return _generalSpecs; }
    /**
     * The value returned by <b>getcolorSpecificSpec</b> may be <b>null</b>
     */
    public colorDefValueSpec getcolorSpecificSpec() { return _colorSpecificSpec; }

    public colorFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                   generalSpecs _generalSpecs,
                                   colorDefValueSpec _colorSpecificSpec)
    {
        super(leftIToken, rightIToken);

        this._generalSpecs = _generalSpecs;
        ((ASTNode) _generalSpecs).setParent(this);
        this._colorSpecificSpec = _colorSpecificSpec;
        if (_colorSpecificSpec != null) ((ASTNode) _colorSpecificSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_generalSpecs);
        list.add(_colorSpecificSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof colorFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        colorFieldPropertySpecs other = (colorFieldPropertySpecs) o;
        if (! _generalSpecs.equals(other._generalSpecs)) return false;
        if (_colorSpecificSpec == null)
            if (other._colorSpecificSpec != null) return false;
            else; // continue
        else if (! _colorSpecificSpec.equals(other._colorSpecificSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_generalSpecs.hashCode());
        hash = hash * 31 + (_colorSpecificSpec == null ? 0 : _colorSpecificSpec.hashCode());
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
            if (_colorSpecificSpec != null) _colorSpecificSpec.accept(v);
        }
        v.endVisit(this);
    }
}


