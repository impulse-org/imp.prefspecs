
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
 *<li>onOff0
 *<li>onOff1
 *<li>inout0
 *<li>inout1
 *<li>fontStyle0
 *<li>fontStyle1
 *<li>fontStyle2
 *<li>conditionType0
 *<li>conditionType1
 *<li>booleanValue0
 *<li>booleanValue1
 *<li>signedNumber0
 *<li>signedNumber1
 *<li>sign0
 *<li>sign1
 *<li>tab0
 *<li>tab1
 *<li>tab2
 *<li>tab3
 *</ul>
 *</b>
 */
public interface IASTNodeToken
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


