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
import org.eclipse.imp.prefspecs.parser.Ast.colorFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalSpec__identifier_AGAINST_identifier;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalSpec__identifier_WITH_identifier;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.configurationTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customRule;
import org.eclipse.imp.prefspecs.parser.Ast.customSpec;
import org.eclipse.imp.prefspecs.parser.Ast.defaultTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.directoryFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.doubleFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fontFieldSpec;
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

        @Override
		public boolean visit(pageSpec n) {
			pushSubItem(n);
			return true;
		}

		@Override
		public void endVisit(pageSpec n) {
		    popSubItem();
		}

		// Tab specs
		
        @Override
		public boolean visit(tabsSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(tabsSpec n) {
			popSubItem();
		}
	
		boolean inDefaultTabSpec = false;

        @Override
		public boolean visit(defaultTabSpec n) {
			pushSubItem(n);
			inDefaultTabSpec = true;
			return true;
		}
		
        @Override
		public void endVisit(defaultTabSpec n) {
			inDefaultTabSpec = false;
			popSubItem();
		}
		
		
        @Override
		public boolean visit(configurationTabSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(configurationTabSpec n) {
			popSubItem();
		}
		
        @Override
		public boolean visit(instanceTabSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(instanceTabSpec n) {
			popSubItem();
		}
		
        @Override
		public boolean visit(projectTabSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(projectTabSpec n) {
			popSubItem();
		}
		
		// Field specs
		
        @Override
		public boolean visit(fieldsSpec n) {
			pushSubItem(n);
			return true;
		}

        @Override
		public void endVisit(fieldsSpec n) {
			popSubItem();
		}

        @Override
		public boolean visit(booleanFieldSpec n) {
			pushSubItem(n);
			return true;
		}

        @Override
		public void endVisit(booleanFieldSpec n) {
			popSubItem();
		}

		@Override
		public boolean visit(colorFieldSpec n) {
		    pushSubItem(n);
		    return true;
		}

		@Override
		public void endVisit(colorFieldSpec n) {
		    popSubItem();
		}

        @Override
		public boolean visit(comboFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(comboFieldSpec n) {
			popSubItem();
		}

		@Override
		public boolean visit(doubleFieldSpec n) {
		    pushSubItem(n);
		    return true;
		}

		@Override
		public void endVisit(doubleFieldSpec n) {
		    popSubItem();
		}

		@Override
		public boolean visit(directoryFieldSpec n) {
		    pushSubItem(n);
		    return true;
		}

		@Override
		public void endVisit(directoryFieldSpec n) {
		    popSubItem();
		}

        @Override
		public boolean visit(dirListFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(dirListFieldSpec n) {
			popSubItem();
		}

        @Override
		public boolean visit(fileFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(fileFieldSpec n) {
			popSubItem();
		}

		@Override
		public boolean visit(fontFieldSpec n) {
		    pushSubItem(n);
		    return true;
		}

		@Override
		public void endVisit(fontFieldSpec n) {
		    popSubItem();
		}

        @Override
		public boolean visit(intFieldSpec n) {
			pushSubItem(n);
			return true;
		}
	
        @Override
		public void endVisit(intFieldSpec n) {
			popSubItem();
		}

		
        @Override
		public boolean visit(radioFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(radioFieldSpec n) {
			popSubItem();
		}

		
        @Override
		public boolean visit(stringFieldSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(stringFieldSpec n) {
			popSubItem();
		}

		
		// Custom
		
        @Override
		public boolean visit(customSpec n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(customSpec n) {
			popSubItem();
		}
		
        @Override
		public boolean visit(customRule n) {
			pushSubItem(n);
			return true;
		}
		
        @Override
		public void endVisit(customRule n) {
			popSubItem();
		}

		
		// Conditionals
		
        @Override
		public boolean visit(conditionalsSpec n) {
			pushSubItem(n);
			return true;
		}

        @Override
		public void endVisit(conditionalsSpec n) {
			popSubItem();
		}

        @Override
		public boolean visit(conditionalSpec__identifier_WITH_identifier n) {
			pushSubItem(n);
			return true;
		}
	
        @Override
		public void endVisit(conditionalSpec__identifier_WITH_identifier n) {
			popSubItem();
		}

		
        @Override
		public boolean visit(conditionalSpec__identifier_AGAINST_identifier n) {
			pushSubItem(n);
			return true;
		}

        @Override
		public void endVisit(conditionalSpec__identifier_AGAINST_identifier n) {
			popSubItem();
		}
	}
}
