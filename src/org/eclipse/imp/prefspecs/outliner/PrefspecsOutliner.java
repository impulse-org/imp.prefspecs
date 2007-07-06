	package prefspecs.safari.outliner;


import org.eclipse.uide.defaults.OutlinerBase;
import org.eclipse.uide.editor.IOutlineImage;

import prefspecs.safari.documentationProvider.PrefspecsNodeDocumentationProvider;
import prefspecs.safari.parser.Ast.ASTNode;
import prefspecs.safari.parser.Ast.AbstractVisitor;
import prefspecs.safari.parser.Ast.booleanFieldSpec;
import prefspecs.safari.parser.Ast.comboFieldSpec;
import prefspecs.safari.parser.Ast.conditionalSpec0;
import prefspecs.safari.parser.Ast.conditionalSpec1;
import prefspecs.safari.parser.Ast.conditionalsSpec;
import prefspecs.safari.parser.Ast.configurationTabSpec;
import prefspecs.safari.parser.Ast.customRule;
import prefspecs.safari.parser.Ast.customSpec;
import prefspecs.safari.parser.Ast.defaultTabSpec;
import prefspecs.safari.parser.Ast.dirListFieldSpec;
import prefspecs.safari.parser.Ast.fieldsSpec;
import prefspecs.safari.parser.Ast.fileFieldSpec;
import prefspecs.safari.parser.Ast.instanceTabSpec;
import prefspecs.safari.parser.Ast.intFieldSpec;
import prefspecs.safari.parser.Ast.pageSpec;
import prefspecs.safari.parser.Ast.projectTabSpec;
import prefspecs.safari.parser.Ast.radioFieldSpec;
import prefspecs.safari.parser.Ast.stringFieldSpec;
import prefspecs.safari.parser.Ast.tabsSpec;

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

	
	// Why not static?
	PrefspecsNodeDocumentationProvider docProvider = new PrefspecsNodeDocumentationProvider();
	
	
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
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			addSubItem(docProvider.getDocumentation(n, editor.getParseController()), n, 
					PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(tabsSpec n) {
			popSubItem();
		}
	
		 boolean inDefaultTabSpec = false;

		public boolean visit(defaultTabSpec n) {
			//addSubItem("Default tab", n);
			pushSubItem("Default tab", n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			inDefaultTabSpec = true;
			return true;
		}
		
		public void endVisit(defaultTabSpec n) {
			inDefaultTabSpec = false;
			popSubItem();
		}
		
		
		public boolean visit(configurationTabSpec n) {
			//addSubItem("Configuration tab", n);
			//pushSubItem(docProvider.getDocumentation(n, editor.getParseController()), n);
			pushSubItem("Configuration tab", n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(configurationTabSpec n) {
			popSubItem();
		}
		
		public boolean visit(instanceTabSpec n) {
			//addSubItem("Instance tab", n);
			//pushSubItem(docProvider.getDocumentation(n, editor.getParseController()), n);
			pushSubItem("Instance tab", n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(instanceTabSpec n) {
			popSubItem();
		}
		
		public boolean visit(projectTabSpec n) {
			//addSubItem("Project tab" , n);
			//pushSubItem(docProvider.getDocumentation(n, editor.getParseController()), n);
			pushSubItem("Project tab" , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(projectTabSpec n) {
			popSubItem();
		}
		
		// Field specs
		
		public boolean visit(fieldsSpec n) {
			pushSubItem("Fields", n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(fieldsSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(booleanFieldSpec n) {
			//addSubItem(((booleanFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((booleanFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(booleanFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(comboFieldSpec n) {
			//addSubItem(((comboFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((comboFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		
		public void endVisit(comboFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(dirListFieldSpec n) {
			//addSubItem(((dirListFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((dirListFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(dirListFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(fileFieldSpec n) {
			//addSubItem(((fileFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((fileFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(fileFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(intFieldSpec n) {
			//addSubItem(((intFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((intFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
	
		public void endVisit(intFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(radioFieldSpec n) {
			//addSubItem(((radioFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((radioFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(radioFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(stringFieldSpec n) {
			//addSubItem(((stringFieldSpec)n).getidentifier().toString() , n);
			pushSubItem(((stringFieldSpec)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(stringFieldSpec n) {
			popSubItem();
		}

		
		// Custom
		
		public boolean visit(customSpec n) {
			pushSubItem("Custom" , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(customSpec n) {
			popSubItem();
		}
		
		public boolean visit(customRule n) {
			//addSubItem(n.gettab().toString() + "." + n.getidentifier().toString(), n);
			pushSubItem(((customRule)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(customRule n) {
			popSubItem();
		}

		
		// Conditionals
		
		public boolean visit(conditionalsSpec n) {
			pushSubItem("Conditionals", n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(conditionalsSpec n) {
			popSubItem();
		}
	
		
		public boolean visit(conditionalSpec0 n) {
			//addSubItem(n.getidentifier().toString() + " with " + n.getidentifier3().toString(), n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
	
		public void endVisit(conditionalSpec0 n) {
			popSubItem();
		}

		
		public boolean visit(conditionalSpec1 n) {
			//addSubItem(n.getidentifier().toString() + " against " + n.getidentifier3().toString(), n);
			pushSubItem(((conditionalSpec1)n).getidentifier().toString() , n);
			//addSubItem(docProvider.getDocumentation(n, editor.getParseController()), null);
			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(conditionalSpec1 n) {
			popSubItem();
		}

		
		
    }


    protected void sendVisitorToAST(Object node) {
    	ASTNode root= (ASTNode) node;
    	root.accept(new OutlineVisitor());
    }
	
}
