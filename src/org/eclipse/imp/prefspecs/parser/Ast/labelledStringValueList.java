
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
 *<li>Rule 152:  labelledStringValueList ::= labelledStringValue
 *<li>Rule 153:  labelledStringValueList ::= labelledStringValueList ,$ labelledStringValue
 *</b>
 */
public class labelledStringValueList extends AbstractASTNodeList implements IlabelledStringValueList
{
    public labelledStringValue getlabelledStringValueAt(int i) { return (labelledStringValue) getElementAt(i); }

    public labelledStringValueList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public labelledStringValueList(labelledStringValue _labelledStringValue, boolean leftRecursive)
    {
        super((ASTNode) _labelledStringValue, leftRecursive);
        ((ASTNode) _labelledStringValue).setParent(this);
    }

    public void add(labelledStringValue _labelledStringValue)
    {
        super.add((ASTNode) _labelledStringValue);
        ((ASTNode) _labelledStringValue).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof labelledStringValueList)) return false;
        if (! super.equals(o)) return false;
        labelledStringValueList other = (labelledStringValueList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            labelledStringValue element = getlabelledStringValueAt(i);
            if (! element.equals(other.getlabelledStringValueAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getlabelledStringValueAt(i).hashCode());
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
            for (int i = 0; i < size(); i++)
            {
                labelledStringValue element = getlabelledStringValueAt(i);
                if (! v.preVisit(element)) continue;
                element.enter(v);
                v.postVisit(element);
            }
        }
        v.endVisit(this);
    }
}


