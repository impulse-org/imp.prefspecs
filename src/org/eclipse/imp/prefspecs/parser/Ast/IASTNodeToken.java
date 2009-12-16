
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
 * is always implemented by <b>ASTNodeToken</b>. It is also implemented by:
 *<b>
 *<ul>
 *<li>identifier
 *<li>stringValue
 *<li>onOff__ON
 *<li>onOff__OFF
 *<li>inout__IN
 *<li>inout__OUT
 *<li>fontStyle__NORMAL
 *<li>fontStyle__BOLD
 *<li>fontStyle__ITALIC
 *<li>conditionType__IF
 *<li>conditionType__UNLESS
 *<li>booleanValue__TRUE
 *<li>booleanValue__FALSE
 *<li>signedNumber__INTEGER
 *<li>signedNumber__sign_INTEGER
 *<li>sign__PLUS
 *<li>sign__MINUS
 *<li>tab__DEFAULT
 *<li>tab__CONFIGURATION
 *<li>tab__INSTANCE
 *<li>tab__PROJECT
 *</ul>
 *</b>
 */
public interface IASTNodeToken
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


