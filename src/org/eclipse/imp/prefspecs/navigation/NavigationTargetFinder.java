/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
 *******************************************************************************/

package org.eclipse.imp.prefspecs.navigation;

import java.util.Arrays;
import java.util.Collection;

import lpg.runtime.IAst;

import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.ItabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optPackageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.INavigationTargetFinder;

public class NavigationTargetFinder implements INavigationTargetFinder {
    public NavigationTargetFinder() { }

    private static Collection<Class<?>> RELEVANT_CLASSES= Arrays.asList(optPackageSpec.class, pageSpec.class, fieldsSpec.class, IfieldSpec.class, tabsSpec.class, ItabSpec.class);

    private boolean nodeIsRelevant(IAst node) {
        for(Class<?> clazz: RELEVANT_CLASSES) {
            if (clazz.isAssignableFrom(node.getClass())) {
                return true;
            }
        }
        return false;
    }

    public Object getNextTarget(Object cur, Object root) {
        IAst curNode= (IAst) cur;
        IAst rootNode= (IAst) root;
        final IAst[] result= new IAst[1];
        final int curNodeOffset= curNode.getLeftIToken().getStartOffset();

        rootNode.accept(new AbstractVisitor() {
            @Override
            public boolean preVisit(IAst element) {
                int thisOffset= element.getLeftIToken().getStartOffset();

                // This relies on the fact that sibling AST nodes are visited in the order
                // that they appear in the source code, so that the first node encountered
                // that satisfies the condition below is the immediately following node of
                // one of the appropriate types.
                if (result[0] == null && nodeIsRelevant(element) && thisOffset > curNodeOffset) {
                    result[0]= element;
                }
                return true;
            }

            @Override
            public void postVisit(IAst element) { }

            @Override
            public void unimplementedVisitor(String s) { }
        });
        return result[0];
    }

    public Object getPreviousTarget(Object cur, Object root) {
        IAst curNode= (IAst) cur;
        IAst rootNode= (IAst) root;
        final IAst[] result= new IAst[1];
        final int curNodeOffset= curNode.getLeftIToken().getStartOffset();

        rootNode.accept(new AbstractVisitor() {
            @Override
            public boolean preVisit(IAst element) {
                int thisOffset= element.getLeftIToken().getStartOffset();

                // This relies on the fact that sibling AST nodes are visited in the order
                // that they appear in the source code, so that the last node encountered
                // that satisfies the condition below is the immediately preceding node of
                // one of the appropriate types.
                if (nodeIsRelevant(element) && thisOffset < curNodeOffset) {
                    result[0]= element;
                }
                return true;
            }

            @Override
            public void postVisit(IAst element) { }

            @Override
            public void unimplementedVisitor(String s) { }
        });
        return result[0];
    }

    public Object getEnclosingConstruct(Object cur, Object root) {
        IAst curNode= (IAst) cur;
        int curNodeOffset= curNode.getLeftIToken().getStartOffset();
        int curNodeEnd= curNode.getRightIToken().getEndOffset();
        IAst parent= curNode.getParent();

        while (parent != null && parent.getLeftIToken().getStartOffset() == curNodeOffset && parent.getRightIToken().getEndOffset() == curNodeEnd) {
            parent= parent.getParent();
        }
        return parent;
    }
}
