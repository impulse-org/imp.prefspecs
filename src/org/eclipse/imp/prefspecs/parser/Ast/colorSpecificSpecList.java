
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
 *<li>Rule 101:  colorSpecificSpecs ::= colorSpecificSpec
 *<li>Rule 102:  colorSpecificSpecs ::= colorSpecificSpecs colorSpecificSpec
 *</b>
 */
public class colorSpecificSpecList extends AbstractASTNodeList implements IcolorSpecificSpecs
{
    public IcolorSpecificSpec getcolorSpecificSpecAt(int i) { return (IcolorSpecificSpec) getElementAt(i); }

    public colorSpecificSpecList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public colorSpecificSpecList(IcolorSpecificSpec _colorSpecificSpec, boolean leftRecursive)
    {
        super((ASTNode) _colorSpecificSpec, leftRecursive);
        ((ASTNode) _colorSpecificSpec).setParent(this);
    }

    public void add(IcolorSpecificSpec _colorSpecificSpec)
    {
        super.add((ASTNode) _colorSpecificSpec);
        ((ASTNode) _colorSpecificSpec).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof colorSpecificSpecList)) return false;
        if (! super.equals(o)) return false;
        colorSpecificSpecList other = (colorSpecificSpecList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            IcolorSpecificSpec element = getcolorSpecificSpecAt(i);
            if (! element.equals(other.getcolorSpecificSpecAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getcolorSpecificSpecAt(i).hashCode());
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
                IcolorSpecificSpec element = getcolorSpecificSpecAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


