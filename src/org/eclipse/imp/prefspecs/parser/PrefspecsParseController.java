/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lpg.runtime.Monitor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.language.ILanguageSyntaxProperties;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.ILexer;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;

public class PrefspecsParseController extends SimpleLPGParseController implements IParseController
{
    private PrefspecsParser parser;
    private PrefspecsLexer lexer;

    public PrefspecsParseController() { }

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
    
    public IParser getParser() { return parser; }
    public ILexer getLexer() { return lexer; }

    public ISourcePositionLocator getNodeLocator() { return new PrefspecsASTNodeLocator(); }	//return new AstLocator(); }

    private void createLexerAndParser(String filePath) {
        try {
            lexer = new PrefspecsLexer(filePath); // Create the lexer
            parser = new PrefspecsParser(lexer.getLexStream() /*, project*/);  // Create the parser
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
    public Object parse(String contents, boolean scanOnly, IProgressMonitor monitor) {
    	PMMonitor my_monitor = new PMMonitor(monitor);
    	char[] contentsArray = contents.toCharArray();

        lexer.initialize(contentsArray, fFilePath.toPortableString());
        parser.getParseStream().resetTokenStream();
        
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
        
        lexer.lexer(my_monitor, parser.getParseStream()); // Lex the stream to produce the token stream
        if (my_monitor.isCancelled())
        	return fCurrentAst; // TODO currentAst might (probably will) be inconsistent wrt the lex stream now

        fCurrentAst = parser.parser(my_monitor, 0);
        parser.resolve((ASTNode) fCurrentAst);

        cacheKeywordsOnce(); // better place/time to do this?

        return fCurrentAst;
    }
}
