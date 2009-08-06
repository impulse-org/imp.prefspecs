
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
 *<b>
 *<li>Rule 195:  typeCustomSpecs ::= stringSpecialSpec stringEmptySpec
 *</b>
 */
public class typeCustomSpecs1 extends ASTNode implements ItypeCustomSpecs
{
    private stringSpecialSpec _stringSpecialSpec;
    private IstringEmptySpec _stringEmptySpec;

    public stringSpecialSpec getstringSpecialSpec() { return _stringSpecialSpec; }
    public IstringEmptySpec getstringEmptySpec() { return _stringEmptySpec; }

    public typeCustomSpecs1(IToken leftIToken, IToken rightIToken,
                            stringSpecialSpec _stringSpecialSpec,
                            IstringEmptySpec _stringEmptySpec)
    {
        super(leftIToken, rightIToken);

        this._stringSpecialSpec = _stringSpecialSpec;
        ((ASTNode) _stringSpecialSpec).setParent(this);
        this._stringEmptySpec = _stringEmptySpec;
        ((ASTNode) _stringEmptySpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_stringSpecialSpec);
        list.add(_stringEmptySpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof typeCustomSpecs1)) return false;
        if (! super.equals(o)) return false;
        typeCustomSpecs1 other = (typeCustomSpecs1) o;
        if (! _stringSpecialSpec.equals(other._stringSpecialSpec)) return false;
        if (! _stringEmptySpec.equals(other._stringEmptySpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_stringSpecialSpec.hashCode());
        hash = hash * 31 + (_stringEmptySpec.hashCode());
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
            _stringSpecialSpec.accept(v);
            _stringEmptySpec.accept(v);
        }
        v.endVisit(this);
    }
}


