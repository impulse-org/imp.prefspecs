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

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.MessageHandlerAdapter;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.prefspecs.PrefspecsPlugin;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.services.ILanguageSyntaxProperties;

public class PrefspecsParseController extends SimpleLPGParseController implements IParseController {
    public PrefspecsParseController() {
        super(PrefspecsPlugin.kLanguageID);
    }

    // SMS 5 May 2006:
    // Version of initialize method corresponding to change in IParseController
    /**
     * @param filePath		Project-relative path of file
     * @param project		Project that contains the file
     * @param handler		A message handler to receive error messages (or any others)
     * 						from the parser
     */
    public void initialize(IPath filePath, ISourceProject project, IMessageHandler handler) {
        super.initialize(filePath, project, handler);
    	String fullFilePath = project.getRawProject().getLocation().toString() + "/" + filePath;
        createLexerAndParser(fullFilePath);
    }

    private void createLexerAndParser(String filePath) {
        try {
            fLexer = new PrefspecsLexer(filePath);
            fParser = new PrefspecsParser(fLexer.getILexStream() /*, project*/);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return new PrefspecsSyntaxProperties();
    }

    /**
     * setFilePath() should be called before calling this method.
     */
    public Object parse(String contents, IProgressMonitor monitor) {
    	PMMonitor my_monitor = new PMMonitor(monitor);
    	char[] contentsArray = contents.toCharArray();

		if (fLexer == null) {
		    fLexer = new PrefspecsLexer();
		}
		fLexer.reset(contentsArray, fFilePath.toPortableString());
        
		if (fParser == null) {
			fParser = new PrefspecsParser(fLexer.getILexStream());
		}
		fParser.reset(fLexer.getILexStream());
		fParser.getIPrsStream().setMessageHandler(new MessageHandlerAdapter(handler));

        // SMS 5 May 2006:
        // Clear the problem markers on the file
        // It should be okay to do this here because ...
        // Whoever is doing the parsing should have passed in whatever
        // listener they were interested in having receive messages
        // and presumably in creating whatever annotations or markers
        // those messages require (and is that a good reason?)
        IResource file = fProject.getRawProject().getFile(fFilePath);
   	    try {
        	file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
        } catch(CoreException e) {
        	System.err.println("prefspecsParseController.parse:  caught CoreException while deleting problem markers; continuing to parse regardless");
        }
        
        fLexer.lexer(my_monitor, fParser.getIPrsStream()); // Lex the stream to produce the token stream
        if (my_monitor.isCancelled())
        	return fCurrentAst; // TODO currentAst might (probably will) be inconsistent wrt the lex stream now

        fCurrentAst = fParser.parser(my_monitor, 0);
        ((PrefspecsParser) fParser).resolve((ASTNode) fCurrentAst);

        cacheKeywordsOnce(); // better place/time to do this?

        return fCurrentAst;
    }
}
