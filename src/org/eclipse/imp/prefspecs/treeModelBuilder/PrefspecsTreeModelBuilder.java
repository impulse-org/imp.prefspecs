package org.eclipse.imp.prefspecs.treeModelBuilder;

import org.eclipse.imp.services.base.TreeModelBuilderBase;

import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.imp.prefspecs.documentationProvider.PrefspecsNodeDocumentationProvider;
import org.eclipse.imp.prefspecs.outliner.PrefspecsDocOutlineImage;
import org.eclipse.imp.prefspecs.parser.Ast.*;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class PrefspecsTreeModelBuilder extends TreeModelBuilderBase {
	@Override
	public void visitTree(Object root) {
		if (root == null)
			return;
		ASTNode rootNode = (ASTNode) root;
		PrefspecsModelVisitor visitor = new PrefspecsModelVisitor();

		rootNode.accept(visitor);
	}

	private class PrefspecsModelVisitor extends AbstractVisitor {
		private StringBuffer fRHSLabel;

		@Override
		public void unimplementedVisitor(String s) {
		}

		
//		PrefspecsNodeDocumentationProvider docProvider = new PrefspecsNodeDocumentationProvider();
//		
//		IEditorPart activeEditor= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		UniversalEditor editor = (UniversalEditor) activeEditor;

		
		public boolean visit(pageSpec n) {
			pushSubItem(n);
			return true;
		}
		
		// Tab specs
		
		public boolean visit(tabsSpec n) {
			pushSubItem(n);
//			addSubItem(docProvider.getDocumentation(n, editor.getParseController()), n, 
//					PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage(), SWT.LEAD);
			return true;
		}
		
		public void endVisit(tabsSpec n) {
			popSubItem();
		}
	
		boolean inDefaultTabSpec = false;

		public boolean visit(defaultTabSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage(), SWT.LEAD);
			inDefaultTabSpec = true;
			return true;
		}
		
		public void endVisit(defaultTabSpec n) {
			inDefaultTabSpec = false;
			popSubItem();
		}
		
		
		public boolean visit(configurationTabSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(configurationTabSpec n) {
			popSubItem();
		}
		
		public boolean visit(instanceTabSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(instanceTabSpec n) {
			popSubItem();
		}
		
		public boolean visit(projectTabSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(projectTabSpec n) {
			popSubItem();
		}
		
		// Field specs
		
		public boolean visit(fieldsSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(fieldsSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(booleanFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(booleanFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(comboFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		
		public void endVisit(comboFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(dirListFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(dirListFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(fileFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(fileFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(intFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
	
		public void endVisit(intFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(radioFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(radioFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(stringFieldSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(stringFieldSpec n) {
			popSubItem();
		}

		
		// Custom
		
		public boolean visit(customSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(customSpec n) {
			popSubItem();
		}
		
		public boolean visit(customRule n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
		
		public void endVisit(customRule n) {
			popSubItem();
		}

		
		// Conditionals
		
		public boolean visit(conditionalsSpec n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(conditionalsSpec n) {
			popSubItem();
		}
	
		
		public boolean visit(conditionalSpec0 n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}
	
		public void endVisit(conditionalSpec0 n) {
			popSubItem();
		}

		
		public boolean visit(conditionalSpec1 n) {
			pushSubItem(n);
//			createSubItem(docProvider.getDocumentation(n, editor.getParseController()), null, 
//							PrefspecsDocOutlineImage.getPrefspecsDocOutlineImage());
			return true;
		}

		public void endVisit(conditionalSpec1 n) {
			popSubItem();
		}

		
		
		
		
	}
}
