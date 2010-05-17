
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
 *<li>Rule 158:  stringEmptySpec ::= EMPTYALLOWED$ TRUE stringValue ;$
 *</b>
 */
public class stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON extends ASTNode implements IstringEmptySpec
{
    private ASTNodeToken _TRUE;
    private stringValue _stringValue;

    public ASTNodeToken getTRUE() { return _TRUE; }
    public stringValue getstringValue() { return _stringValue; }

    public stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON(IToken leftIToken, IToken rightIToken,
                                                                    ASTNodeToken _TRUE,
                                                                    stringValue _stringValue)
    {
        super(leftIToken, rightIToken);

        this._TRUE = _TRUE;
        ((ASTNode) _TRUE).setParent(this);
        this._stringValue = _stringValue;
        ((ASTNode) _stringValue).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_TRUE);
        list.add(_stringValue);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON)) return false;
        if (! super.equals(o)) return false;
        stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON other = (stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON) o;
        if (! _TRUE.equals(other._TRUE)) return false;
        if (! _stringValue.equals(other._stringValue)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_TRUE.hashCode());
        hash = hash * 31 + (_stringValue.hashCode());
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
            _TRUE.accept(v);
            _stringValue.accept(v);
        }
        v.endVisit(this);
    }
}


