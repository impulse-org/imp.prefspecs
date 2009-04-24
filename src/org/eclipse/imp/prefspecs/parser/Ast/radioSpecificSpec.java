
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
 *<li>Rule 146:  radioSpecificSpec ::= radioCustomSpec radioDefValueSpec
 *</b>
 */
public class radioSpecificSpec extends ASTNode implements IradioSpecificSpec
{
    private radioCustomSpec _radioCustomSpec;
    private radioDefValueSpec _radioDefValueSpec;

    public radioCustomSpec getradioCustomSpec() { return _radioCustomSpec; }
    /**
     * The value returned by <b>getradioDefValueSpec</b> may be <b>null</b>
     */
    public radioDefValueSpec getradioDefValueSpec() { return _radioDefValueSpec; }

    public radioSpecificSpec(IToken leftIToken, IToken rightIToken,
                             radioCustomSpec _radioCustomSpec,
                             radioDefValueSpec _radioDefValueSpec)
    {
        super(leftIToken, rightIToken);

        this._radioCustomSpec = _radioCustomSpec;
        ((ASTNode) _radioCustomSpec).setParent(this);
        this._radioDefValueSpec = _radioDefValueSpec;
        if (_radioDefValueSpec != null) ((ASTNode) _radioDefValueSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_radioCustomSpec);
        list.add(_radioDefValueSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof radioSpecificSpec)) return false;
        if (! super.equals(o)) return false;
        radioSpecificSpec other = (radioSpecificSpec) o;
        if (! _radioCustomSpec.equals(other._radioCustomSpec)) return false;
        if (_radioDefValueSpec == null)
            if (other._radioDefValueSpec != null) return false;
            else; // continue
        else if (! _radioDefValueSpec.equals(other._radioDefValueSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_radioCustomSpec.hashCode());
        hash = hash * 31 + (_radioDefValueSpec == null ? 0 : _radioDefValueSpec.hashCode());
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
            _radioCustomSpec.accept(v);
            if (_radioDefValueSpec != null) _radioDefValueSpec.accept(v);
        }
        v.endVisit(this);
    }
}


