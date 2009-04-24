
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
 *<li>Rule 180:  customRules ::= $Empty
 *<li>Rule 181:  customRules ::= customRule
 *</em>
 *<p>
 *<b>
 *<li>Rule 182:  customRules ::= customRules customRule
 *</b>
 */
public class customRules extends ASTNode implements IcustomRules
{
    private IcustomRules _customRules;
    private customRule _customRule;

    /**
     * The value returned by <b>getcustomRules</b> may be <b>null</b>
     */
    public IcustomRules getcustomRules() { return _customRules; }
    public customRule getcustomRule() { return _customRule; }

    public customRules(IToken leftIToken, IToken rightIToken,
                       IcustomRules _customRules,
                       customRule _customRule)
    {
        super(leftIToken, rightIToken);

        this._customRules = _customRules;
        if (_customRules != null) ((ASTNode) _customRules).setParent(this);
        this._customRule = _customRule;
        ((ASTNode) _customRule).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_customRules);
        list.add(_customRule);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof customRules)) return false;
        if (! super.equals(o)) return false;
        customRules other = (customRules) o;
        if (_customRules == null)
            if (other._customRules != null) return false;
            else; // continue
        else if (! _customRules.equals(other._customRules)) return false;
        if (! _customRule.equals(other._customRule)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_customRules == null ? 0 : _customRules.hashCode());
        hash = hash * 31 + (_customRule.hashCode());
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
            if (_customRules != null) _customRules.accept(v);
            _customRule.accept(v);
        }
        v.endVisit(this);
    }
}


