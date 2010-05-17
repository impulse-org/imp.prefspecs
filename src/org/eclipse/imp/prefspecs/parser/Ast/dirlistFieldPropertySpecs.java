
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
 *<li>Rule 73:  dirlistFieldPropertySpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 74:  dirlistFieldPropertySpecs ::= {$ stringSpecificSpecs }$
 *</b>
 */
public class dirlistFieldPropertySpecs extends ASTNode implements IdirlistFieldPropertySpecs
{
    private stringSpecificSpecList _stringSpecificSpecs;

    public stringSpecificSpecList getstringSpecificSpecs() { return _stringSpecificSpecs; }

    public dirlistFieldPropertySpecs(IToken leftIToken, IToken rightIToken,
                                     stringSpecificSpecList _stringSpecificSpecs)
    {
        super(leftIToken, rightIToken);

        this._stringSpecificSpecs = _stringSpecificSpecs;
        ((ASTNode) _stringSpecificSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_stringSpecificSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof dirlistFieldPropertySpecs)) return false;
        if (! super.equals(o)) return false;
        dirlistFieldPropertySpecs other = (dirlistFieldPropertySpecs) o;
        if (! _stringSpecificSpecs.equals(other._stringSpecificSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_stringSpecificSpecs.hashCode());
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
            _stringSpecificSpecs.accept(v);
        v.endVisit(this);
    }
}


