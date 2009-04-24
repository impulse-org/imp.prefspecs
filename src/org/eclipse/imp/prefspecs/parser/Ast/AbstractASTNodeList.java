
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

public abstract class AbstractASTNodeList extends ASTNode
{
    private boolean leftRecursive;
    private java.util.ArrayList list;
    public int size() { return list.size(); }
    public ASTNode getElementAt(int i) { return (ASTNode) list.get(leftRecursive ? i : list.size() - 1 - i); }
    public java.util.ArrayList getArrayList()
    {
        if (! leftRecursive) // reverse the list 
        {
            for (int i = 0, n = list.size() - 1; i < n; i++, n--)
            {
                Object ith = list.get(i),
                       nth = list.get(n);
                list.set(i, nth);
                list.set(n, ith);
            }
            leftRecursive = true;
        }
        return list;
    }
    public void add(ASTNode element)
    {
        list.add(element);
        if (leftRecursive)
             rightIToken = element.getRightIToken();
        else leftIToken = element.getLeftIToken();
    }

    public AbstractASTNodeList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken);
        this.leftRecursive = leftRecursive;
        list = new java.util.ArrayList();
    }

    public AbstractASTNodeList(ASTNode element, boolean leftRecursive)
    {
        this(element.getLeftIToken(), element.getRightIToken(), leftRecursive);
        list.add(element);
    }

    /**
     * Make a copy of the list and return it. Note that we obtain the local list by
     * invoking getArrayList so as to make sure that the list we return is in proper order.
     */
    public java.util.ArrayList getAllChildren()
    {
        return (java.util.ArrayList) getArrayList().clone();
    }

}


