
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
 *<li>Rule 161:  stringSpecificSpec ::= stringCustomSpec stringDefValueSpec stringValidatorSpec
 *</b>
 */
public class stringSpecificSpec extends ASTNode implements IstringSpecificSpec
{
    private stringCustomSpec _stringCustomSpec;
    private stringDefValueSpec _stringDefValueSpec;
    private stringValidatorSpec _stringValidatorSpec;

    public stringCustomSpec getstringCustomSpec() { return _stringCustomSpec; }
    /**
     * The value returned by <b>getstringDefValueSpec</b> may be <b>null</b>
     */
    public stringDefValueSpec getstringDefValueSpec() { return _stringDefValueSpec; }
    /**
     * The value returned by <b>getstringValidatorSpec</b> may be <b>null</b>
     */
    public stringValidatorSpec getstringValidatorSpec() { return _stringValidatorSpec; }

    public stringSpecificSpec(IToken leftIToken, IToken rightIToken,
                              stringCustomSpec _stringCustomSpec,
                              stringDefValueSpec _stringDefValueSpec,
                              stringValidatorSpec _stringValidatorSpec)
    {
        super(leftIToken, rightIToken);

        this._stringCustomSpec = _stringCustomSpec;
        ((ASTNode) _stringCustomSpec).setParent(this);
        this._stringDefValueSpec = _stringDefValueSpec;
        if (_stringDefValueSpec != null) ((ASTNode) _stringDefValueSpec).setParent(this);
        this._stringValidatorSpec = _stringValidatorSpec;
        if (_stringValidatorSpec != null) ((ASTNode) _stringValidatorSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_stringCustomSpec);
        list.add(_stringDefValueSpec);
        list.add(_stringValidatorSpec);
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
        if (! (o instanceof stringSpecificSpec)) return false;
        stringSpecificSpec other = (stringSpecificSpec) o;
        if (! _stringCustomSpec.equals(other._stringCustomSpec)) return false;
        if (_stringDefValueSpec == null)
            if (other._stringDefValueSpec != null) return false;
            else; // continue
        else if (! _stringDefValueSpec.equals(other._stringDefValueSpec)) return false;
        if (_stringValidatorSpec == null)
            if (other._stringValidatorSpec != null) return false;
            else; // continue
        else if (! _stringValidatorSpec.equals(other._stringValidatorSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = 7;
        hash = hash * 31 + (_stringCustomSpec.hashCode());
        hash = hash * 31 + (_stringDefValueSpec == null ? 0 : _stringDefValueSpec.hashCode());
        hash = hash * 31 + (_stringValidatorSpec == null ? 0 : _stringValidatorSpec.hashCode());
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
            _stringCustomSpec.accept(v);
            if (_stringDefValueSpec != null) _stringDefValueSpec.accept(v);
            if (_stringValidatorSpec != null) _stringValidatorSpec.accept(v);
        }
        v.endVisit(this);
    }
}


