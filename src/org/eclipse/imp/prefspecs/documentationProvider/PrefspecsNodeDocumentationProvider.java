/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.documentationProvider;


import java.util.ArrayList;

import org.eclipse.imp.language.ILanguageService;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.IconditionalSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
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
import org.eclipse.imp.prefspecs.parser.Ast.projectTabSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.IDocumentationProvider;


public class PrefspecsNodeDocumentationProvider implements IDocumentationProvider, ILanguageService {

    public String getDocumentation(Object target, IParseController parseController) {
    	StringBuffer buff= new StringBuffer();
    	Object node = target;
	
        if (node == null || !(node instanceof ASTNode))
            return null;	

        if (node instanceof tabsSpec) {
        	return "In this section list each of the four tabs:  " +
        			"'default', 'configuration', 'instance', and 'project',	" +
        			"and, for each, indicate whether it is 'in' or 'out' of the preference page and assign tab-wide attributes, " +
        			"'iseditable' and/or 'isremovable', as appropriate";
        } if (node instanceof defaultTabSpec || node instanceof configurationTabSpec ||
			node instanceof instanceTabSpec || node instanceof projectTabSpec) {
			return TABSPECS_DOC;
		}  else if (node instanceof fieldsSpec) {
        	return "In this section list the fields that will appear on the generated preferences page (each field will appear" +
        			"on each included tab).  To list a field, give its type and name, and optionally set any properties specific " +
					"to that field (applicable properties depend on field type).  The name of each field should be unique.";
        } else if (node instanceof booleanFieldSpec) {
        	return "Specify attributes for a boolean field:  three optional, semicolon-separated attributes, " +
        			"in this order:  'iseditable' (boolean), 'isremovable' (boolean), 'hasspecial' (boolean)";
        } else if (node instanceof comboFieldSpec) {
        	return "Specify attributes for a combo-box field:  four optional, semicolon-separated attributes, " +
					"in this order:  'iseditable' (boolean), 'isremovable' (boolean), 'hasspecial' (string), " +
					"and 'emptyallowed' (boolean--plus a string if 'true')";
        } else if (node instanceof dirListFieldSpec) {
    		return "Specify attributes for a directory-list field:   four optional, semicolon-separated attributes, " +
    				"in this order:  'iseditable' (boolean), 'isremovable' (boolean), 'hasspecial' (string), " +
    				"and 'emptyallowed' (boolean--plus a string if 'true')";
       } else if (node instanceof fileFieldSpec) {
	   		return "Specify attributes for a file-name field:   four optional, semicolon-separated attributes, " +
					"in this order:  'iseditable' (boolean), 'isremovable' (boolean), 'hasspecial' (string), " +
					"and 'emptyallowed' (boolean--plus a string if 'true')";
        } else if (node instanceof intFieldSpec) {
	    	return "Specify attributes for an integer field:   four optional, semicolon-separated attributes, " +
	    			"in this order:  'iseditable' (boolean), 'isremovable' (boolean), 'range' (lowval .. highval), " +
	    			"and 'hasspecial' (int)";
        } else if (node instanceof radioFieldSpec) {
    		return "Specify attributes for a radio-button field:   three optional, semicolon-separated attributes, " +
					"in this order:  'iseditable' (boolean), 'isremovable' (boolean), and 'hasspecial' (int)";
        } else if (node instanceof stringFieldSpec) {
	   		return "Specify attributes for a string field:   four optional, semicolon-separated attributes, " +
	   				"in this order:  'iseditable' (boolean), 'isremovable' (boolean), 'hasspecial' (string), " +
	   				"and 'emptyallowed' (boolean--plus a string if 'true')";
        } else if (node instanceof customSpec) {
    		return "In this section provide property values that apply to specific fields on specific tabs.  " +
    				"Field names should have been introduced in the 'fields' section.  " +
    				"Specific tab-field combinations may appear multiple times. ";
        } else if (node instanceof customRule) {
        	return "Designate a field by 'tab-name' 'field-name'; list properties within '{' ... '}' according to type of field.";  	
        } else if (node instanceof conditionalsSpec) {
    		return "In this section list fields of any type whose enabled state depends on the state " +
    				"of another field of boolean type";
        } else if (node instanceof IconditionalSpec) {
        	return "Specify 'dependentField with booleanField' or 'dependentField against booleanField'";  
        }
		else if (node instanceof IfieldSpec) {
			// This is probably never going to be useful
			return "This is a field specification";
		}
        return "Not a token:  No help for:  '" + node.toString() + "'";
    }


    public static String TABSPECS_DOC = 
    	"Specify tab properties within braces; these properties by default apply to " +
		"all fields in the tab.  There are two optional properties:  " +
		"'iseditable' (boolean) indicates whether fields in the tab are editable; " +
		"'isremovable' (boolean) indicates whether values stored in fields on the tab " +
		"can be removed (triggering inheritance).  When both are used 'iseditable' " +
		"must appear first.  Each should be followed by a ';'.";
   
	protected ASTNode getFieldSpecNode(ASTNode node)
	{
   		ASTNode grandParentNode = (ASTNode) node.getParent().getParent();
   		int nodeOffset = node.getLeftIToken().getStartOffset();
   		int specOffset = 0;
   		ArrayList parents = grandParentNode.getChildren();
   		ASTNode spec = null;
   		
   		for (int i = 0; i < parents.size(); i++) {
   			ASTNode parent = (ASTNode) parents.get(i);
   			if (parent instanceof IfieldSpec) {
   				int parentOffset = parent.getLeftIToken().getStartOffset();
   				if (parentOffset < nodeOffset && parentOffset > specOffset) {
   					specOffset = parentOffset;
   					spec = parent;
   				}
   			}
   		}
   		return spec;
	}


    public static String getSubstring(IParseController parseController, int start, int end) {
        return new String(parseController.getLexer().getLexStream().getInputChars(), start, end-start+1);
    }
}
