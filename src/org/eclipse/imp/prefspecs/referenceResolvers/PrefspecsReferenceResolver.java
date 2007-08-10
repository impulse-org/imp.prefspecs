package org.eclipse.imp.prefspecs.referenceResolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.imp.language.ILanguageService;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.AbstractVisitor;
import org.eclipse.imp.prefspecs.parser.Ast.Itab;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.configurationTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customSpec;
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
import org.eclipse.imp.prefspecs.parser.Ast.tab0;
import org.eclipse.imp.prefspecs.parser.Ast.tab1;
import org.eclipse.imp.prefspecs.parser.Ast.tab2;
import org.eclipse.imp.prefspecs.parser.Ast.tab3;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.IReferenceResolver;


public class PrefspecsReferenceResolver implements IReferenceResolver, ILanguageService {

    public PrefspecsReferenceResolver () {
    }

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
    public Object getLinkTarget(Object node, IParseController controller)
    {
    	if (controller.getCurrentAst() != null) {
    		buildScopeAndDeclStructures(controller);
    	}
			
        if (node instanceof identifier) {
    	    ASTNode bindingTarget = findDeclForIdentifier((identifier)node);
    	    return bindingTarget;
        }
        if (node instanceof Itab) {
            ASTNode bindingTarget = findDeclForTab((Itab)node);
            return bindingTarget;
        }
        
        return null;
    }
    
    
    protected ASTNode findDeclForTab(Itab node)
    {
    	if (node instanceof tab0) {
    		return (ASTNode) tabDecls.get(((tab0)node).getDEFAULT().toString());
    	}
    	if (node instanceof tab1) {
    		return (ASTNode) tabDecls.get(((tab1)node).getCONFIGURATION().toString());
    	}
    	if (node instanceof tab2) {
    		return (ASTNode) tabDecls.get(((tab2)node).getINSTANCE().toString());
    	}
    	if (node instanceof tab3) {
    		ASTNode res = (ASTNode) tabDecls.get(((tab3)node).getPROJECT().toString());
    		return res;
    	}
    	System.err.println("PrefspecsReferenceResolver.findDeclForTab:  got tab of unknown type, returning null");
    	return null;
    }
    
    
    protected ASTNode findDeclForIdentifier(identifier node)
    {
    	return (ASTNode) fieldDecls.get(node.getIDENTIFIER().toString());
    }

    
    
	private HashMap tabDecls = null;
	private HashMap fieldDecls = null;
	private List customTabs = null;
	private List customFields = null;
    
    protected void buildScopeAndDeclStructures(IParseController controller)
    {
    	tabDecls = new HashMap();
    	fieldDecls = new HashMap();
    	customTabs = new ArrayList();
    	customFields = new ArrayList();
    	
		PageVisitor visitor = new PageVisitor();
		ASTNode ast = (ASTNode) controller.getCurrentAst();
		ast.accept(visitor);
    
    }
    
    
	/**
	 * A visitor for ASTs that records information about scopes,
	 * declarations and identifiers.
	 */
	public class PageVisitor extends AbstractVisitor
	{
  	
       	PageVisitor() { }
   			
       	
       	
       	//private String logging message(String methodName, String stackName, String action)
       	
  
       	// Visit methods
       	
       	public void unimplementedVisitor(String s) {
       	    //System.out.println("ScopeAndDeclVisitor:  Unimplemented visitor:  " + s);
       	}
       	
       	
       	boolean inTabsSpec = false;
       	boolean inFieldsSpec = false;
       	boolean inCustomSpec = false;
       	boolean inConditionalsSpec = false;
       	
        
       	// Spec visits
       	
       	public boolean visit(tabsSpec node) {
    		inTabsSpec = true;	
       		return true;
       	}
       	
       	
       	public void endVisit(tabsSpec node) {
       		inTabsSpec = false;
       	}
 
       	
       	public boolean visit(fieldsSpec node) {
       		inFieldsSpec = true;
       		return true;
       	}
       	
       	public void endVisit(fieldsSpec node) {
       		inFieldsSpec = false;
       	}
       	
       	
       	public boolean visit(customSpec node) {
       		inCustomSpec = true;
       		return true;
       	}
       	
       	public void endVisit(customSpec node) {
       		inCustomSpec = false;
       	}
       	
       	
       	public boolean visit(conditionalsSpec node) {
       		inConditionalsSpec = true;
       		return true;
       	}
       	
       	public void endVisit(conditionalsSpec node) {
       		inConditionalsSpec = false;
       	}

       	
       	// Tab spec visits
       	
       	public boolean visit(defaultTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getDEFAULT().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(configurationTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getCONFIGURATION().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(instanceTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getINSTANCE().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(projectTabSpec node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getPROJECT().toString(), node);
       		}
       		return true;	
       	}
       	
       	// Field spec visits
       	
       	public boolean visit(booleanFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(comboFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(dirListFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(fileFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(intFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(radioFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	public boolean visit(stringFieldSpec node) {
       		if (inFieldsSpec) {
       			fieldDecls.put(node.getidentifier().getIDENTIFIER().toString(), node);
       		}
       		return true;	
       	}
       	
       	
       	// Tab visits
       	
       	public boolean visit(tab0 node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getDEFAULT().toString(), node);
       		}
       		return false;	
       	}

       	public boolean visit(tab1 node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getCONFIGURATION().toString(), node);
       		}
       		return false;	
       	}
       	
       	public boolean visit(tab2 node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getINSTANCE().toString(), node);
       		}
       		return false;	
       	}
       	
       	public boolean visit(tab3 node) {
       		if (inTabsSpec) {
       			tabDecls.put(node.getPROJECT().toString(), node);
       		}
       		return false;	
       	}
     	
	}		// End PageVisitor

    
    
}






























