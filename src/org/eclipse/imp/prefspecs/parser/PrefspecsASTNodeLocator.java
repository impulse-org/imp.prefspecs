/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.parser;

import lpg.runtime.IAst;
import lpg.runtime.IToken;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.imp.model.ICompilationUnit;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;

public class PrefspecsASTNodeLocator implements ISourcePositionLocator
{
    private final ASTNode[] fNode= new ASTNode[1];

    private int fStartOffset;
    private int fEndOffset;

    public PrefspecsASTNodeLocator( ) {
    }

    private final class NodeVisitor extends AbstractVisitor {
        public void unimplementedVisitor(String s) {
            // System.out.println("NodeVisitor.unimplementedVisitor:  Unimplemented");
        }

        public boolean preVisit(ASTNode element)
        {
            int nodeStartOffset = element.getLeftIToken().getStartOffset();
            int nodeEndOffset = element.getRightIToken().getEndOffset();
            //System.out.println("prefspecsNodeLocator.NodeVisitor.preVisit(ASTNode):  Examining " + element.getClass().getName() +
            //    " @ [" + nodeStartOffset + "->" + nodeEndOffset + ']');

            // If this node contains the span of interest then record it
            if (nodeStartOffset <= fStartOffset && nodeEndOffset >= fEndOffset) {
                //System.out.println("prefspecsNodeLocator.NodeVisitor.preVisit(ASTNode) SELECTED for offsets [" + fStartOffset + ".." + fEndOffset + "]");
                fNode[0]= element;
                return true; // to continue visiting here?
            }
            return false; // to stop visiting here?
        }
    }

    private NodeVisitor fVisitor= new NodeVisitor();

    public Object findNode(Object ast, int offset) {
        return findNode(ast, offset, offset);
    }

    public Object findNode(Object ast, int startOffset, int endOffset) {
        // System.out.println("Looking for node spanning offsets " + startOffset + " => " + endOffset);
        fStartOffset = startOffset;
        fEndOffset = endOffset;
        // The following could be treated as an IASTNodeToken, but ASTNode
        // is required for the visit/preVisit method, and there's no reason
        // to use both of those types
        ((ASTNode) ast).accept(fVisitor);
        if (fNode[0] == null) {
            //System.out.println("Selected node:  null");
        } else {
            //System.out.println("Selected node: " + fNode[0] + " [" +
            //   fNode[0].getLeftIToken().getStartOffset() + ".." + fNode[0].getLeftIToken().getEndOffset() + "]");
        }
        return fNode[0];
    }

    public int getStartOffset(Object entity) {
        if (entity instanceof IAst) {
            IAst n = (ASTNode) entity;
            return n.getLeftIToken().getStartOffset();
        } else if (entity instanceof ICompilationUnit) {
            ICompilationUnit icu= (ICompilationUnit) entity;
            return 0;
        } else if (entity instanceof IToken) {
            IToken t= (IToken) entity;
            return t.getStartOffset();
        }
        return 0;
    }

    public int getEndOffset(Object entity) {
        if (entity instanceof IAst) {
            IAst n = (IAst) entity;
            return n.getRightIToken().getEndOffset();
        } else if (entity instanceof ICompilationUnit) {
            return 0;
        } else if (entity instanceof IToken) {
            IToken t= (IToken) entity;
            return t.getEndOffset();
        }
        return 0;
    }

    public int getLength(Object entity) {
        return getEndOffset(entity) - getStartOffset(entity);
    }

    public IPath getPath(Object node) {
        // TODO Determine path of compilation unit containing this node
        return new Path("");
    }
}
