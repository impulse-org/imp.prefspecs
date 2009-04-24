
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
 *<li>Rule 187:  customRule ::= tab identifier {$ newPropertySpecs }$
 *</b>
 */
public class customRule extends ASTNode implements IcustomRule
{
    private Itab _tab;
    private identifier _identifier;
    private InewPropertySpecs _newPropertySpecs;

    public Itab gettab() { return _tab; }
    public identifier getidentifier() { return _identifier; }
    public InewPropertySpecs getnewPropertySpecs() { return _newPropertySpecs; }

    public customRule(IToken leftIToken, IToken rightIToken,
                      Itab _tab,
                      identifier _identifier,
                      InewPropertySpecs _newPropertySpecs)
    {
        super(leftIToken, rightIToken);

        this._tab = _tab;
        ((ASTNode) _tab).setParent(this);
        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._newPropertySpecs = _newPropertySpecs;
        ((ASTNode) _newPropertySpecs).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_tab);
        list.add(_identifier);
        list.add(_newPropertySpecs);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof customRule)) return false;
        if (! super.equals(o)) return false;
        customRule other = (customRule) o;
        if (! _tab.equals(other._tab)) return false;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _newPropertySpecs.equals(other._newPropertySpecs)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_tab.hashCode());
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_newPropertySpecs.hashCode());
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
            _tab.accept(v);
            _identifier.accept(v);
            _newPropertySpecs.accept(v);
        }
        v.endVisit(this);
    }
}


