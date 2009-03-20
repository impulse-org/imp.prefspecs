
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
 *<b>
 *<li>Rule 130:  valuesSpec ::= VALUES$ {$ labelledStringValueList }$
 *</b>
 */
public class valuesSpec extends ASTNode implements IvaluesSpec
{
    private labelledStringValueList _labelledStringValueList;

    public labelledStringValueList getlabelledStringValueList() { return _labelledStringValueList; }

    public valuesSpec(IToken leftIToken, IToken rightIToken,
                      labelledStringValueList _labelledStringValueList)
    {
        super(leftIToken, rightIToken);

        this._labelledStringValueList = _labelledStringValueList;
        ((ASTNode) _labelledStringValueList).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_labelledStringValueList);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof valuesSpec)) return false;
        if (! super.equals(o)) return false;
        valuesSpec other = (valuesSpec) o;
        if (! _labelledStringValueList.equals(other._labelledStringValueList)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_labelledStringValueList.hashCode());
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
            _labelledStringValueList.accept(v);
        v.endVisit(this);
    }
}

