
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
 *<li>Rule 1:  prefSpecs ::= optPackageSpec optDetailsSpec topLevelItems
 *</b>
 */
public class prefSpecs extends ASTNode implements IprefSpecs
{
    private optPackageSpec _optPackageSpec;
    private optDetailsSpec _optDetailsSpec;
    private topLevelItemList _topLevelItems;

    /**
     * The value returned by <b>getoptPackageSpec</b> may be <b>null</b>
     */
    public optPackageSpec getoptPackageSpec() { return _optPackageSpec; }
    /**
     * The value returned by <b>getoptDetailsSpec</b> may be <b>null</b>
     */
    public optDetailsSpec getoptDetailsSpec() { return _optDetailsSpec; }
    public topLevelItemList gettopLevelItems() { return _topLevelItems; }

    public prefSpecs(IToken leftIToken, IToken rightIToken,
                     optPackageSpec _optPackageSpec,
                     optDetailsSpec _optDetailsSpec,
                     topLevelItemList _topLevelItems)
    {
        super(leftIToken, rightIToken);

        this._optPackageSpec = _optPackageSpec;
        if (_optPackageSpec != null) ((ASTNode) _optPackageSpec).setParent(this);
        this._optDetailsSpec = _optDetailsSpec;
        if (_optDetailsSpec != null) ((ASTNode) _optDetailsSpec).setParent(this);
        this._topLevelItems = _topLevelItems;
        ((ASTNode) _topLevelItems).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_optPackageSpec);
        list.add(_optDetailsSpec);
        list.add(_topLevelItems);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof prefSpecs)) return false;
        if (! super.equals(o)) return false;
        prefSpecs other = (prefSpecs) o;
        if (_optPackageSpec == null)
            if (other._optPackageSpec != null) return false;
            else; // continue
        else if (! _optPackageSpec.equals(other._optPackageSpec)) return false;
        if (_optDetailsSpec == null)
            if (other._optDetailsSpec != null) return false;
            else; // continue
        else if (! _optDetailsSpec.equals(other._optDetailsSpec)) return false;
        if (! _topLevelItems.equals(other._topLevelItems)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_optPackageSpec == null ? 0 : _optPackageSpec.hashCode());
        hash = hash * 31 + (_optDetailsSpec == null ? 0 : _optDetailsSpec.hashCode());
        hash = hash * 31 + (_topLevelItems.hashCode());
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
            if (_optPackageSpec != null) _optPackageSpec.accept(v);
            if (_optDetailsSpec != null) _optDetailsSpec.accept(v);
            _topLevelItems.accept(v);
        }
        v.endVisit(this);
    }
}


