/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lpg.runtime.IMessageHandler;
import lpg.runtime.IToken;
import lpg.runtime.Monitor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.IASTNodeLocator;
import org.eclipse.imp.parser.ILexer;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.ParseError;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;

public class PrefspecsParseController implements IParseController
{
    private ISourceProject project;
    private IPath filePath;
    private PrefspecsParser parser;
    private PrefspecsLexer lexer;
    private ASTNode currentAst;

    private char keywords[][];
    private boolean isKeyword[];


    // SMS 5 May 2006:
    // Version of initialize method corresponding to change in IParseController
    /**
     * @param filePath		Project-relative path of file
     * @param project		Project that contains the file
     * @param handler		A message handler to receive error messages (or any others)
     * 						from the parser
     */
    public void initialize(IPath filePath, ISourceProject project, IMessageHandler handler) {
    	this.filePath= filePath;
    	this.project= project;
    	String fullFilePath = project.getRawProject().getLocation().toString() + "/" + filePath;
        createLexerAndParser(fullFilePath);
    	
    	parser.setMessageHandler(handler);
    }
    
    // SMS 5 May 2006:
    // To make this available to users of the controller
    public ISourceProject getProject() { return project; }
    
    
    // SMS 6 Jul 2007
    public IPath getPath() {
    	return filePath;
    }

    
    public IParser getParser() { return parser; }
    public ILexer getLexer() { return lexer; }
    public Object getCurrentAst() { return currentAst; }
    public char [][] getKeywords() { return keywords; }
    public boolean isKeyword(int kind) { return isKeyword[kind]; }
    public int getTokenIndexAtCharacter(int offset)
    {
        int index = parser.getParseStream().getTokenIndexAtCharacter(offset);
        return (index < 0 ? -index : index);
    }
    public IToken getTokenAtCharacter(int offset) {
    	return parser.getParseStream().getTokenAtCharacter(offset);
    }											// SMS 3 Oct 2006:  trying new locator
    public IASTNodeLocator getNodeLocator() { return new PrefspecsASTNodeLocator(); }	//return new AstLocator(); }

    public boolean hasErrors() { return currentAst == null; }
    public List getErrors() { return Collections.singletonList(new ParseError("parse error", null)); }
    
    public PrefspecsParseController()
    {
    }

    private void createLexerAndParser(String filePath) {
        try {
            lexer = new PrefspecsLexer(filePath); // Create the lexer
            parser = new PrefspecsParser(lexer.getLexStream() /*, project*/);  // Create the parser
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    class MyMonitor implements Monitor
    {
        IProgressMonitor monitor;
        boolean wasCancelled= false;
        MyMonitor(IProgressMonitor monitor)
        {
            this.monitor = monitor;
        }
        public boolean isCancelled() {
        	if (!wasCancelled)
        		wasCancelled = monitor.isCanceled();
        	return wasCancelled;
        }
    }
    
    /**
     * setFilePath() should be called before calling this method.
     */
    public Object parse(String contents, boolean scanOnly, IProgressMonitor monitor)
    {
    	MyMonitor my_monitor = new MyMonitor(monitor);
    	char[] contentsArray = contents.toCharArray();

        lexer.initialize(contentsArray, filePath.toPortableString());
        parser.getParseStream().resetTokenStream();
        
        // SMS 5 May 2006:
        // Clear the problem markers on the file
        // It should be okay to do this here because ...
        // Whoever is doing the parsing should have passed in whatever
        // listener they were interested in having receive messages
        // and presumably in creating whatever annotations or markers
        // those messages require (and is that a good reason?)
        IResource file = project.getRawProject().getFile(filePath);
   	    try {
        	file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
        } catch(CoreException e) {
        	System.err.println("prefspecsParseController.parse:  caught CoreException while deleting problem markers; continuing to parse regardless");
        }
        
        lexer.lexer(my_monitor, parser.getParseStream()); // Lex the stream to produce the token stream
        if (my_monitor.isCancelled())
        	return currentAst; // TODO currentAst might (probably will) be inconsistent wrt the lex stream now

        currentAst = (ASTNode) parser.parser(my_monitor, 0);
        parser.resolve(currentAst);

        cacheKeywordsOnce(); // better place/time to do this?

        return currentAst;
    }

    public String getSingleLineCommentPrefix() { return "//"; }
    
    private void cacheKeywordsOnce() {
        if (keywords == null) {
            String tokenKindNames[] = parser.getParseStream().orderedTerminalSymbols();
            this.isKeyword = new boolean[tokenKindNames.length];
            this.keywords = new char[tokenKindNames.length][];

            int [] keywordKinds = lexer.getKeywordKinds();
            for (int i = 1; i < keywordKinds.length; i++)
            {
                int index = parser.getParseStream().mapKind(keywordKinds[i]);

                isKeyword[index] = true;
                keywords[index] = parser.getParseStream().orderedTerminalSymbols()[index].toCharArray();
            }
        }
    }

    
    /*
     * For the management of associated problem-marker types
     */
    
    private static List problemMarkerTypes = new ArrayList();
    
    public List getProblemMarkerTypes() {
    	return problemMarkerTypes;
    }
    
    public void addProblemMarkerType(String problemMarkerType) {
    	problemMarkerTypes.add(problemMarkerType);
    }
    
	public void removeProblemMarkerType(String problemMarkerType) {
		problemMarkerTypes.remove(problemMarkerType);
	}
    
}
