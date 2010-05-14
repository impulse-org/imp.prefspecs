
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
import org.eclipse.imp.parser.SymbolTable;
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
 *<li>Rule 67:  colorFieldPropertySpecs ::= {$ colorSpecificSpecs }$
 *</b>
 */
public class colorFieldPropertySpecs extends ASTNode implements IcolorFieldPropertySpecs
{
    private colorSpecificSpecList _colorSpecificSpecs;

    public colorSpecificSpecList getcolorSpecificSpecs() { return _colorSpecificSpecs; }

    public colorFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                   colorSpecificSpecList _colorSpecificSpecs)
    {
        super(leftIToken, rightIToken);

        this._colorSpecificSpecs = _colorSpecificSpecs;
        ((ASTNode) _colorSpecificSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_colorSpecificSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof colorFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        colorFieldPropertySpecs other = (colorFieldPropertySpecs) o;
        if (! _colorSpecificSpecs.equals(other._colorSpecificSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_colorSpecificSpecs.hashCode());
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
            _colorSpecificSpecs.accept(v);
        v.endVisit(this);
    }
}


