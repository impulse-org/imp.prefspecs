/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.services.ILanguageSyntaxProperties;

public class PrefspecsParseController extends SimpleLPGParseController implements IParseController {
    public PrefspecsParseController() {
        super(PrefspecsPlugin.kLanguageID);
        fLexer= new PrefspecsLexer();
        fParser= new PrefspecsParser();
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return new PrefspecsSyntaxProperties();
    }

    public Object parse(String contents, IProgressMonitor monitor) {
        super.parse(contents, monitor);

        ((PrefspecsParser) fParser).resolve((ASTNode) fCurrentAst);

        return fCurrentAst;
    }
}
