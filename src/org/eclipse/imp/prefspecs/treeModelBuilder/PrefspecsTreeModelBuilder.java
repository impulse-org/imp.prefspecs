/*******************************************************************************
* Copyright (c) 2008 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Stan Sutton (suttons@us.ibm.com) - initial API and implementation

*******************************************************************************/

package org.eclipse.imp.prefspecs.treeModelBuilder;

import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalSpec0;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalSpec1;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.configurationTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customRule;
import org.eclipse.imp.prefspecs.parser.Ast.customSpec;
import org.eclipse.imp.prefspecs.parser.Ast.defaultTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.instanceTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.intFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.projectTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.base.TreeModelBuilderBase;

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
		@Override
		public void unimplementedVisitor(String s) { }

		public boolean visit(pageSpec n) {
			pushSubItem(n);
			return true;
		}

		@Override
		public void endVisit(pageSpec n) {
		    popSubItem();
		}

		// Tab specs
		
		public boolean visit(tabsSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(tabsSpec n) {
			popSubItem();
		}
	
		boolean inDefaultTabSpec = false;

		public boolean visit(defaultTabSpec n) {
			pushSubItem(n);
			inDefaultTabSpec = true;
			return true;
		}
		
		public void endVisit(defaultTabSpec n) {
			inDefaultTabSpec = false;
			popSubItem();
		}
		
		
		public boolean visit(configurationTabSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(configurationTabSpec n) {
			popSubItem();
		}
		
		public boolean visit(instanceTabSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(instanceTabSpec n) {
			popSubItem();
		}
		
		public boolean visit(projectTabSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(projectTabSpec n) {
			popSubItem();
		}
		
		// Field specs
		
		public boolean visit(fieldsSpec n) {
			pushSubItem(n);
			return true;
		}

		public void endVisit(fieldsSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(booleanFieldSpec n) {
			pushSubItem(n);
			return true;
		}

		public void endVisit(booleanFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(comboFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
		
		public void endVisit(comboFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(dirListFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(dirListFieldSpec n) {
			popSubItem();
		}
		
		
		public boolean visit(fileFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(fileFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(intFieldSpec n) {
			pushSubItem(n);
			return true;
		}
	
		public void endVisit(intFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(radioFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(radioFieldSpec n) {
			popSubItem();
		}

		
		public boolean visit(stringFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(stringFieldSpec n) {
			popSubItem();
		}

		
		// Custom
		
		public boolean visit(customSpec n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(customSpec n) {
			popSubItem();
		}
		
		public boolean visit(customRule n) {
			pushSubItem(n);
			return true;
		}
		
		public void endVisit(customRule n) {
			popSubItem();
		}

		
		// Conditionals
		
		public boolean visit(conditionalsSpec n) {
			pushSubItem(n);
			return true;
		}

		public void endVisit(conditionalsSpec n) {
			popSubItem();
		}
	
		
		public boolean visit(conditionalSpec0 n) {
			pushSubItem(n);
			return true;
		}
	
		public void endVisit(conditionalSpec0 n) {
			popSubItem();
		}

		
		public boolean visit(conditionalSpec1 n) {
			pushSubItem(n);
			return true;
		}

		public void endVisit(conditionalSpec1 n) {
			popSubItem();
		}
	}
}
