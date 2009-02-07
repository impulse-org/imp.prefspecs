
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
 *<em>
 *<li>Rule 41:  fieldSpecs ::= $Empty
 *<li>Rule 42:  fieldSpecs ::= fieldSpec
 *</em>
 *<p>
 *<b>
 *<li>Rule 43:  fieldSpecs ::= fieldSpecs fieldSpec
 *</b>
 */
public class fieldSpecs extends ASTNode implements IfieldSpecs
{
    private IfieldSpecs _fieldSpecs;
    private IfieldSpec _fieldSpec;

    /**
     * The value returned by <b>getfieldSpecs</b> may be <b>null</b>
     */
    public IfieldSpecs getfieldSpecs() { return _fieldSpecs; }
    public IfieldSpec getfieldSpec() { return _fieldSpec; }

    public fieldSpecs(IToken leftIToken, IToken rightIToken,
                      IfieldSpecs _fieldSpecs,
                      IfieldSpec _fieldSpec)
    {
        super(leftIToken, rightIToken);

        this._fieldSpecs = _fieldSpecs;
        if (_fieldSpecs != null) ((ASTNode) _fieldSpecs).setParent(this);
        this._fieldSpec = _fieldSpec;
        ((ASTNode) _fieldSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_fieldSpecs);
        list.add(_fieldSpec);
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
        if (! (o instanceof fieldSpecs)) return false;
        fieldSpecs other = (fieldSpecs) o;
        if (_fieldSpecs == null)
            if (other._fieldSpecs != null) return false;
            else; // continue
        else if (! _fieldSpecs.equals(other._fieldSpecs)) return false;
        if (! _fieldSpec.equals(other._fieldSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_fieldSpecs == null ? 0 : _fieldSpecs.hashCode());
        hash = hash * 31 + (_fieldSpec.hashCode());
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
            if (_fieldSpecs != null) _fieldSpecs.accept(v);
            _fieldSpec.accept(v);
        }
        v.endVisit(this);
    }
}


