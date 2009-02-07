
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
 *<li>Rule 88:  customSpec ::= CUSTOM$ {$ customRules }$
 *</b>
 */
public class customSpec extends ASTNode implements IcustomSpec
{
    private IcustomRules _customRules;

    /**
     * The value returned by <b>getcustomRules</b> may be <b>null</b>
     */
    public IcustomRules getcustomRules() { return _customRules; }

    public customSpec(IToken leftIToken, IToken rightIToken,
                      IcustomRules _customRules)
    {
        super(leftIToken, rightIToken);

        this._customRules = _customRules;
        if (_customRules != null) ((ASTNode) _customRules).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_customRules);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        //
        // The super call test is not required for now because an Ast node
        // can only extend the root Ast, AstToken and AstList and none of
        // these nodes contain additional children.
        //
        // if (! super.equals(o)) return false;
        //
        if (! (o instanceof customSpec)) return false;
        customSpec other = (customSpec) o;
        if (_customRules == null)
            if (other._customRules != null) return false;
            else; // continue
        else if (! _customRules.equals(other._customRules)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_customRules == null ? 0 : _customRules.hashCode());
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
            if (_customRules != null) _customRules.accept(v);
        v.endVisit(this);
    }
}


