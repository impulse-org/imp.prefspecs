package org.eclipse.imp.prefspecs.foldingUpdater;

import java.util.HashMap;
import java.util.List;

import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.base.FolderBase;

/**
 * This file provides a skeletal implementation of the language-dependent aspects
 * of a source-text folder.  This implementation is generated from a template that
 * is parameterized with respect to the name of the language, the package containing
 * the language-specific types for AST nodes and AbstractVisitors, and the name of
 * the folder package and class.
 * 
 * @author suttons@us.ibm.com
 *
 */
public class PrefspecsFoldingUpdater extends FolderBase {

	/*
	 * A visitor for ASTs.  Its purpose is to create ProjectionAnnotations
	 * for regions of text corresponding to various types of AST node or to
	 * text ranges computed from AST nodes.  Projection annotations appear
	 * in the editor as the widgets that control folding.
	 */
	private class FoldingVisitor extends AbstractVisitor
	{
	    public void unimplementedVisitor(String s) { }
	    
		// START_HERE
		//
		// Include visit(..) functions for various types of AST nodes that are
		// associated with folding.  These functions should call one of the two
	    // versions of makeAnnotation(..) that are defined in FolderBase.  The
	    // usual case is to call the version of makeAnnotation that creates a
	    // folding annotation corresponding to the extent of a particular AST node.
	    // The other possibility is to create an annotation with an extent that
	    // is explicitly provided.  An example is shown below ...
	    
	    // Create annotations for the folding of blocks (for example)
	    	public boolean visit(pageSpec n) {
			makeAnnotation(n);
			return true;
		}
		
		public boolean visit(tabsSpec n) {
			makeAnnotation(n);
			return true;
		}
		
		public boolean visit(fieldsSpec n) {
			makeAnnotation(n);
			return true;
		}
		
		public boolean visit(customSpec n) {
			makeAnnotation(n);
			return true;
		}
	
		public boolean visit(conditionalsSpec n) {
			makeAnnotation(n);
			return true;
		}	
		
		public boolean visit(IfieldSpec n) {
			makeAnnotation(n);
			return true;
		}	
		
		
		
	};
   
	
	// When instantiated will provide a concrete implementation of an abstract method
	// defined in FolderBase
	public void sendVisitorToAST(HashMap newAnnotations, List annotations, Object ast) {
		ASTNode theAST= (ASTNode) ast;
		AbstractVisitor abstractVisitor = new FoldingVisitor();
		theAST.accept(abstractVisitor);
	}
}
