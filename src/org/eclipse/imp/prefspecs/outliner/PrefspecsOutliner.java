package prefspecs.safari.outliner;


import org.eclipse.uide.defaults.*;	
import org.eclipse.uide.parser.IParseController;

import prefspecs.safari.parser.Ast.*;

/**
 * This file provides a skeletal implementation of the language-dependent aspects
 * of a source-program outliner.  This implementation is generated from a template
 * that is parameterized with respect to the name of the language, the package
 * containing the language-specific types for AST nodes and AbstractVisitors, and
 * the name of the outliner package and class.
 * 
 * @author suttons@us.ibm.com
 *
 */
public class PrefspecsOutliner extends OutlinerBase
{
	/*
	 * A visitor for ASTs.  Its purpose is to create outline-tree items
	 * for AST node types that are to appear in the outline view.
	 */
    private final class OutlineVisitor extends AbstractVisitor {

		public OutlineVisitor() {
			super();
			// TODO:  Replace the language name here with the name of your
			// own language (provided that your language isn't called "Leg"
			// and you want a heading like this at the top of your outline)
			//pushTopItem("prefspecs program", null);
		}


		public void unimplementedVisitor(String s) {
			// Sometimes useful for debugging
			//System.out.println(s);
		}
	
		// START_HERE
		// Include visit(..) and endVisit(..) methods for each AST node
		// type that is to appear in the outline tree.  Typically, the
		// visit(..) method is used to create an outline item and to
		// push it onto a stack that contains the current ancestor items
		// (the top member of which will be the parent of the current item).
		// The  endVisit(..) method is used to pop items from the stack.
		// To aid in the implementation of these methods, methods to push
		// and pop top-level and sub-level outline items are provided.  The
		// method addSubItem(..) allows you to add an outline item at the
		// current level of indentation without changing the level.  Some
		// examples follow ...
		
		public boolean visit(pageSpec n) {
			pushTopItem("Page " + n.getidentifier().toString(), n);
			return true;
		}
		
		// Tab specs
		
		
		public boolean visit(tabsSpec n) {
			pushSubItem("Tabs", n);
			return true;
		}
		
		public void endVisit(tabsSpec n) {
			popSubItem();
		}
	
		
		public boolean visit(defaultTabSpec n) {
			addSubItem("Default tab", n);
			return true;
		}
		
		
		public boolean visit(configurationTabSpec n) {
			addSubItem("Configuration tab", n);
			return true;
		}
		
		
		public boolean visit(instanceTabSpec n) {
			addSubItem("Instance tab", n);
			return true;
		}
		
		
		public boolean visit(projectTabSpec n) {
			addSubItem("Project tab" , n);
			return true;
		}
		
		
		// Field specs
		
		public boolean visit(fieldsSpec n) {
			pushSubItem("Fields", n);
			return true;
		}

		public void endVisit(fieldsSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(booleanFieldSpec n) {
			addSubItem(((booleanFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		public boolean visit(comboFieldSpec n) {
			addSubItem(((comboFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		public boolean visit(dirListFieldSpec n) {
			addSubItem(((dirListFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		public boolean visit(fileFieldSpec n) {
			addSubItem(((fileFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		public boolean visit(intFieldSpec n) {
			addSubItem(((intFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		public boolean visit(radioFieldSpec n) {
			addSubItem(((radioFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		public boolean visit(stringFieldSpec n) {
			addSubItem(((stringFieldSpec)n).getidentifier().toString() , n);
			return true;
		}
		
		
		// Custom
		
		public boolean visit(customSpec n) {
			pushSubItem("Custom" , n);
			return true;
		}
		
		public void endVisit(customSpec n) {
			popSubItem();
		}
		
		public boolean visit(customRule n) {
			addSubItem(n.gettab().toString() + "." + n.getidentifier().toString(), n);
			return true;
		}
		
		
		// Conditionals
		
		public boolean visit(conditionalsSpec n) {
			pushSubItem("Conditionals", n);
			return true;
		}

		public void endVisit(conditionalsSpec n) {
			popSubItem();
		}
		
		public boolean visit(conditionalSpec0 n) {
			addSubItem(n.getidentifier().toString() + " with " + n.getidentifier3().toString(), n);
			return true;
		}
		
		public boolean visit(conditionalSpec1 n) {
			addSubItem(n.getidentifier().toString() + " against " + n.getidentifier3().toString(), n);
			return true;
		}
		
    }


    protected void sendVisitorToAST(Object node) {
    	ASTNode root= (ASTNode) node;
    	root.accept(new OutlineVisitor());
    }
	
}
