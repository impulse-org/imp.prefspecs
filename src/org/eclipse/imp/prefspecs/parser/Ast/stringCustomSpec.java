
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
 *<li>Rule 162:  stringCustomSpec ::= stringSpecialSpec stringEmptySpec
 *</b>
 */
public class stringCustomSpec extends ASTNode implements IstringCustomSpec
{
    private stringSpecialSpec _stringSpecialSpec;
    private IstringEmptySpec _stringEmptySpec;

    /**
     * The value returned by <b>getstringSpecialSpec</b> may be <b>null</b>
     */
    public stringSpecialSpec getstringSpecialSpec() { return _stringSpecialSpec; }
    /**
     * The value returned by <b>getstringEmptySpec</b> may be <b>null</b>
     */
    public IstringEmptySpec getstringEmptySpec() { return _stringEmptySpec; }

    public stringCustomSpec(IToken leftIToken, IToken rightIToken,
                            stringSpecialSpec _stringSpecialSpec,
                            IstringEmptySpec _stringEmptySpec)
    {
        super(leftIToken, rightIToken);

        this._stringSpecialSpec = _stringSpecialSpec;
        if (_stringSpecialSpec != null) ((ASTNode) _stringSpecialSpec).setParent(this);
        this._stringEmptySpec = _stringEmptySpec;
        if (_stringEmptySpec != null) ((ASTNode) _stringEmptySpec).setParent(this);
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
        if (! (o instanceof stringCustomSpec)) return false;
        if (! super.equals(o)) return false;
        stringCustomSpec other = (stringCustomSpec) o;
        if (_stringSpecialSpec == null)
            if (other._stringSpecialSpec != null) return false;
            else; // continue
        else if (! _stringSpecialSpec.equals(other._stringSpecialSpec)) return false;
        if (_stringEmptySpec == null)
            if (other._stringEmptySpec != null) return false;
            else; // continue
        else if (! _stringEmptySpec.equals(other._stringEmptySpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_stringSpecialSpec == null ? 0 : _stringSpecialSpec.hashCode());
        hash = hash * 31 + (_stringEmptySpec == null ? 0 : _stringEmptySpec.hashCode());
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
            if (_stringSpecialSpec != null) _stringSpecialSpec.accept(v);
            if (_stringEmptySpec != null) _stringEmptySpec.accept(v);
        }
        v.endVisit(this);
    }
}


