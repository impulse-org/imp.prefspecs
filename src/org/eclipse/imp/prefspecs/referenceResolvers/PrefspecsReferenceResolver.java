/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.prefspecs.referenceResolvers;

import java.util.HashMap;

import org.eclipse.imp.language.ILanguageService;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.configurationTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.defaultTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.identifier;
import org.eclipse.imp.prefspecs.parser.Ast.instanceTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.intFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.projectTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.IReferenceResolver;

public class PrefspecsReferenceResolver implements IReferenceResolver, ILanguageService {

    public PrefspecsReferenceResolver () { }

    /**
      * Get the text associated with a given node for use in a link
      * from (or to) that node
      */
    public String getLinkText(Object node) {
        // TODO:  Replace the call to super.getLinkText(..) with an implementation
        // suitable to you language and link types
        return node.toString();
    }
        
    /**
      * Get the target for a given source node in the AST represented by a
      * given Parse Controller.
      */
    public Object getLinkTarget(Object node, IParseController controller) {
    	if (controller.getCurrentAst() != null) {
    		buildScopeAndDeclStructures(controller);
    	}
			
        if (node instanceof identifier) {
    	    ASTNode bindingTarget = findDeclForIdentifier((identifier)node);
    	    return bindingTarget;
        }
        
        return null;
    }

    protected ASTNode findDeclForIdentifier(identifier node) {
    	return fieldDecls.get(node.getIDENTIFIER().toString());
    }


	private HashMap<String,ASTNode> tabDecls = null;
	private HashMap<String,ASTNode> fieldDecls = null;


    protected void buildScopeAndDeclStructures(IParseController controller) {
    	tabDecls = new HashMap<String,ASTNode>();
    	fieldDecls = new HashMap<String,ASTNode>();
    	
		PageVisitor visitor = new PageVisitor();
		ASTNode ast = (ASTNode) controller.getCurrentAst();
		ast.accept(visitor);
    }
    
    
	/**
	 * A visitor for ASTs that records information about scopes,
	 * declarations and identifiers.
	 */
	public class PageVisitor extends AbstractVisitor {
       	PageVisitor() { }

       	public void unimplementedVisitor(String s) {
       	    //System.out.println("ScopeAndDeclVisitor:  Unimplemented visitor:  " + s);
       	}
       	
       	
       	boolean inTabsSpec = false;
       	boolean inFieldsSpec = false;
       	boolean inConditionalsSpec = false;
       	
        
       	// Spec visits
       	@Override
       	public boolean visit(tabsSpec node) {
    		inTabsSpec = true;	
       		return true;
       	}
       	
        @Override
       	public void endVisit(tabsSpec node) {
       		inTabsSpec = false;
       	}
 
        @Override
       	public boolean visit(fieldsSpec node) {
       		inFieldsSpec = true;
       		return true;
       	}

        @Override
       	public void endVisit(fieldsSpec node) {
       		inFieldsSpec = false;
       	}
       	
        @Override
       	public boolean visit(conditionalsSpec node) {
       		inConditionalsSpec = true;
       		return true;
       	}
       	
        @Override
       	public void endVisit(conditionalsSpec node) {
       		inConditionalsSpec = false;
       	}

       	
       	// Tab spec visits
        @Override
       	public boolean visit(defaultTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put("DEFAULT", node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(configurationTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put("CONFIGURATION", node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(instanceTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put("INSTANCE", node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(projectTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put("PROJECT", node);
       		}
       		return true;	
       	}
       	
       	// Field spec visits
        @Override
       	public boolean visit(booleanFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(comboFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(dirListFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(fileFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(intFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(radioFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
        @Override
       	public boolean visit(stringFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
	}		// End PageVisitor
}
