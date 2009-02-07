
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
 *<li>Rule 32:  tabSpecs ::= $Empty
 *</em>
 *<p>
 *<b>
 *<li>Rule 33:  tabSpecs ::= defaultTabSpec configurationTabSpec instanceTabSpec projectTabSpec
 *</b>
 */
public class tabSpecs extends ASTNode implements ItabSpecs
{
    private defaultTabSpec _defaultTabSpec;
    private configurationTabSpec _configurationTabSpec;
    private instanceTabSpec _instanceTabSpec;
    private projectTabSpec _projectTabSpec;

    public defaultTabSpec getdefaultTabSpec() { return _defaultTabSpec; }
    public configurationTabSpec getconfigurationTabSpec() { return _configurationTabSpec; }
    public instanceTabSpec getinstanceTabSpec() { return _instanceTabSpec; }
    public projectTabSpec getprojectTabSpec() { return _projectTabSpec; }

    public tabSpecs(IToken leftIToken, IToken rightIToken,
                    defaultTabSpec _defaultTabSpec,
                    configurationTabSpec _configurationTabSpec,
                    instanceTabSpec _instanceTabSpec,
                    projectTabSpec _projectTabSpec)
    {
        super(leftIToken, rightIToken);

        this._defaultTabSpec = _defaultTabSpec;
        ((ASTNode) _defaultTabSpec).setParent(this);
        this._configurationTabSpec = _configurationTabSpec;
        ((ASTNode) _configurationTabSpec).setParent(this);
        this._instanceTabSpec = _instanceTabSpec;
        ((ASTNode) _instanceTabSpec).setParent(this);
        this._projectTabSpec = _projectTabSpec;
        ((ASTNode) _projectTabSpec).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_defaultTabSpec);
        list.add(_configurationTabSpec);
        list.add(_instanceTabSpec);
        list.add(_projectTabSpec);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof tabSpecs)) return false;
        if (! super.equals(o)) return false;
        tabSpecs other = (tabSpecs) o;
        if (! _defaultTabSpec.equals(other._defaultTabSpec)) return false;
        if (! _configurationTabSpec.equals(other._configurationTabSpec)) return false;
        if (! _instanceTabSpec.equals(other._instanceTabSpec)) return false;
        if (! _projectTabSpec.equals(other._projectTabSpec)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_defaultTabSpec.hashCode());
        hash = hash * 31 + (_configurationTabSpec.hashCode());
        hash = hash * 31 + (_instanceTabSpec.hashCode());
        hash = hash * 31 + (_projectTabSpec.hashCode());
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
            _defaultTabSpec.accept(v);
            _configurationTabSpec.accept(v);
            _instanceTabSpec.accept(v);
            _projectTabSpec.accept(v);
        }
        v.endVisit(this);
    }
}


