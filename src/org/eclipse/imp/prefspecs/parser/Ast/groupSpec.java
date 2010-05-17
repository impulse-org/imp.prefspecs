
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
 *<li>Rule 64:  groupSpec ::= GROUP$ STRING_LITERAL {$ fieldSpecs }$
 *</b>
 */
public class groupSpec extends ASTNode implements IgroupSpec
{
    private ASTNodeToken _STRING_LITERAL;
    private IfieldSpecs _fieldSpecs;

    public ASTNodeToken getSTRING_LITERAL() { return _STRING_LITERAL; }
    public IfieldSpecs getfieldSpecs() { return _fieldSpecs; }

    public groupSpec(IToken leftIToken, IToken rightIToken,
                     ASTNodeToken _STRING_LITERAL,
                     IfieldSpecs _fieldSpecs)
    {
        super(leftIToken, rightIToken);

        this._STRING_LITERAL = _STRING_LITERAL;
        ((ASTNode) _STRING_LITERAL).setParent(this);
        this._fieldSpecs = _fieldSpecs;
        ((ASTNode) _fieldSpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_STRING_LITERAL);
        list.add(_fieldSpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof groupSpec)) return false;
        if (! super.equals(o)) return false;
        groupSpec other = (groupSpec) o;
        if (! _STRING_LITERAL.equals(other._STRING_LITERAL)) return false;
        if (! _fieldSpecs.equals(other._fieldSpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_STRING_LITERAL.hashCode());
        hash = hash * 31 + (_fieldSpecs.hashCode());
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
            _STRING_LITERAL.accept(v);
            _fieldSpecs.accept(v);
        }
        v.endVisit(this);
    }
}


