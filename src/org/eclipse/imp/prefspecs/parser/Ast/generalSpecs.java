
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
 *<li>Rule 105:  generalSpecs ::= isEditableSpec isRemovableSpec optLabelSpec optToolTipSpec
 *</b>
 */
public class generalSpecs extends ASTNode implements IgeneralSpecs
{
    private isEditableSpec _isEditableSpec;
    private isRemovableSpec _isRemovableSpec;
    private optLabelSpec _optLabelSpec;
    private optToolTipSpec _optToolTipSpec;

    /**
     * The value returned by <b>getisEditableSpec</b> may be <b>null</b>
     */
    public isEditableSpec getisEditableSpec() { return _isEditableSpec; }
    /**
     * The value returned by <b>getisRemovableSpec</b> may be <b>null</b>
     */
    public isRemovableSpec getisRemovableSpec() { return _isRemovableSpec; }
    /**
     * The value returned by <b>getoptLabelSpec</b> may be <b>null</b>
     */
    public optLabelSpec getoptLabelSpec() { return _optLabelSpec; }
    /**
     * The value returned by <b>getoptToolTipSpec</b> may be <b>null</b>
     */
    public optToolTipSpec getoptToolTipSpec() { return _optToolTipSpec; }

    public generalSpecs(IToken leftIToken, IToken rightIToken,
                        isEditableSpec _isEditableSpec,
                        isRemovableSpec _isRemovableSpec,
                        optLabelSpec _optLabelSpec,
                        optToolTipSpec _optToolTipSpec)
    {
        super(leftIToken, rightIToken);

        this._isEditableSpec = _isEditableSpec;
        if (_isEditableSpec != null) ((ASTNode) _isEditableSpec).setParent(this);
        this._isRemovableSpec = _isRemovableSpec;
        if (_isRemovableSpec != null) ((ASTNode) _isRemovableSpec).setParent(this);
        this._optLabelSpec = _optLabelSpec;
        if (_optLabelSpec != null) ((ASTNode) _optLabelSpec).setParent(this);
        this._optToolTipSpec = _optToolTipSpec;
        if (_optToolTipSpec != null) ((ASTNode) _optToolTipSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_isEditableSpec);
        list.add(_isRemovableSpec);
        list.add(_optLabelSpec);
        list.add(_optToolTipSpec);
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
        if (! (o instanceof generalSpecs)) return false;
        generalSpecs other = (generalSpecs) o;
        if (_isEditableSpec == null)
            if (other._isEditableSpec != null) return false;
            else; // continue
        else if (! _isEditableSpec.equals(other._isEditableSpec)) return false;
        if (_isRemovableSpec == null)
            if (other._isRemovableSpec != null) return false;
            else; // continue
        else if (! _isRemovableSpec.equals(other._isRemovableSpec)) return false;
        if (_optLabelSpec == null)
            if (other._optLabelSpec != null) return false;
            else; // continue
        else if (! _optLabelSpec.equals(other._optLabelSpec)) return false;
        if (_optToolTipSpec == null)
            if (other._optToolTipSpec != null) return false;
            else; // continue
        else if (! _optToolTipSpec.equals(other._optToolTipSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_isEditableSpec == null ? 0 : _isEditableSpec.hashCode());
        hash = hash * 31 + (_isRemovableSpec == null ? 0 : _isRemovableSpec.hashCode());
        hash = hash * 31 + (_optLabelSpec == null ? 0 : _optLabelSpec.hashCode());
        hash = hash * 31 + (_optToolTipSpec == null ? 0 : _optToolTipSpec.hashCode());
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
            if (_isEditableSpec != null) _isEditableSpec.accept(v);
            if (_isRemovableSpec != null) _isRemovableSpec.accept(v);
            if (_optLabelSpec != null) _optLabelSpec.accept(v);
            if (_optToolTipSpec != null) _optToolTipSpec.accept(v);
        }
        v.endVisit(this);
    }
}


