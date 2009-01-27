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

package org.eclipse.imp.prefspecs.documentationProvider;

import org.eclipse.imp.language.ILanguageService;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParsersym;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNodeToken;
import org.eclipse.imp.prefspecs.parser.Ast.IASTNodeToken;
import org.eclipse.imp.services.IDocumentationProvider;

public class PrefspecsDocumentationProvider implements IDocumentationProvider, ILanguageService {

    public String getDocumentation(Object target, IParseController parseController) {

    	Object node = target;
	
        if (node == null)
            return null;
        
        if (node instanceof IASTNodeToken	
        		|| node instanceof ASTNodeToken)
        {      
        	int tokenKind = ((IASTNodeToken) node).getLeftIToken().getKind();
        	
        	switch (tokenKind) {
        	
        	case PrefspecsParsersym.TK_TABS:
        		return "Introduces required section for specifying participation of tabs on the preference page " +
        			"and optionally specifying certain properties of fields on those tabs";
        	case PrefspecsParsersym.TK_FIELDS:
        		return "Introduces required section for specifying fields for tabs on the preference page " +
						"and optionally specifying certain properties of those fields";
        	case PrefspecsParsersym.TK_CUSTOM:
        		return "Introduces optional section for specifying fiel-property values that apply to specific fields on specific tabs.";
        	case PrefspecsParsersym.TK_CONDITIONALS:
        		return "Introduces optional section for specifying fields that are enabled depending on the states other fields.";
 
        	case PrefspecsParsersym.TK_DEFAULT:
        		return "Designates the 'default' level preferences tab";
        	case PrefspecsParsersym.TK_CONFIGURATION:
        		return "Designates the 'configuration' level preferences tab";
        	case PrefspecsParsersym.TK_INSTANCE:
        		return "Designates the 'instance' level preferences tab";
        	case PrefspecsParsersym.TK_PROJECT:
        		return "Designates the 'project' level preferences tab";

        	case PrefspecsParsersym.TK_BOOLEAN:
        		return "Designates the 'boolean' (checkbox) field type";
        	case PrefspecsParsersym.TK_COMBO:
        		return "Designates the 'combo' (combo box) field type";
        	case PrefspecsParsersym.TK_DIRLIST:
        		return "Designates the 'dirlist' (directory list) field type";
        	case PrefspecsParsersym.TK_FILE:
        		return "Designates the 'file' (file name) field type";
        	case PrefspecsParsersym.TK_INT:
        		return "Designates the 'int' field type";
        	case PrefspecsParsersym.TK_RADIO:
        		return "Designates the 'radio' (radio buttons) field type";
        	case PrefspecsParsersym.TK_STRING:
        		return "Designates the 'string' field type";

        	case PrefspecsParsersym.TK_DEFVALUE:
        		return "'defvalue' indicates a default value for the field, which is used to set " +
        				"the value of the field on the 'default' preferences level; takes a value " +
        				"of the type of the field";
        	case PrefspecsParsersym.TK_EMPTYALLOWED:
        		return "'emptyallowed' indicates whether the field has, and is allowed to take on, " +
        				"an 'empty' value; this attribute takes a boolean value; if 'true' then a value " +
           				"of the field type must be provided (to serve as the 'empty' value); " +
        				"if 'false', then no other argument is needed";
        	case PrefspecsParsersym.TK_HASSPECIAL:
        		return "'hasspecial' requires a value of the type of the field that " +
        				"will serve as a distinguished value; omit if no such value";
        	case PrefspecsParsersym.TK_ISEDITABLE:
        		return "'iseditable' takes a boolean value:  'true' indicates that the field " +
        				"can be edited (normal case); false indicates that it cannot (field is 'read only')";
        	case PrefspecsParsersym.TK_ISREMOVABLE:
        		return "'isremovable' takes a boolean value:  'true' indicates that the value " +
        				"can be removed from this field an inherited from a higher level; " +
        				"'false' means that the field must always have a local value (not inherited";
        	case PrefspecsParsersym.TK_RANGE:
        		return "'range' sets a range for numeric field types in the form 'lowVal .. highVal'";


        	case PrefspecsParsersym.TK_AGAINST:
        		return "'against' means that the preceding field (of any type) is enabled if and only if " +
        				"the following field (of boolean type) is set to 'false'";
        	case PrefspecsParsersym.TK_WITH:
        		return "'with' means that the preceding field (of any type) is enabled if and only if " +
        				"the following field (of boolean type) is set to 'true'";
        		
        	case PrefspecsParsersym.TK_STRING_LITERAL:
        		return "String literal";
        		
        	case PrefspecsParsersym.TK_IDENTIFIER:
        		int tokenNumber = ((IASTNodeToken) node).getLeftIToken().getTokenIndex();
        		if (tokenNumber == 2)
        			return "Preference-page identifier";
        		else
        			return "Preference-field identifier";

        	case PrefspecsParsersym.TK_SINGLE_LINE_COMMENT:
        		// Comment tokens may not appear as such, and I don't want
        		// to go digging around in the adjuncts of "real" tokens,
        		// so don't expect much here
        		return "Comment (no effect on page generation)";
        	
           	case PrefspecsParsersym.TK_IN:
        		return "'in' means that the associated tab will be included in the generated preferences page";
        	case PrefspecsParsersym.TK_OUT:
           		return "'out' means that the associated tab will not be included in the generated preferences page";
 
           	case PrefspecsParsersym.TK_TRUE:
        		return "The opposite of false.";
        	case PrefspecsParsersym.TK_FALSE:
           		return "The opposite of true.";
       		
        	case PrefspecsParsersym.TK_PAGE:
        		return "This designates the beginning of a preference-page specification";
      		
        	default:
        		return null;
        	}
        }
        
        return null;
    }

  
//	protected ASTNode getFieldSpecNode(ASTNode node)
//	{
//   		ASTNode grandParentNode = (ASTNode) node.getParent().getParent();
//   		int nodeOffset = node.getLeftIToken().getStartOffset();
//   		int specOffset = 0;
//   		ArrayList parents = grandParentNode.getChildren();
//   		ASTNode spec = null;
//   		
//   		for (int i = 0; i < parents.size(); i++) {
//   			ASTNode parent = (ASTNode) parents.get(i);
//   			if (parent instanceof IfieldSpec) {
//   				int parentOffset = parent.getLeftIToken().getStartOffset();
//   				if (parentOffset < nodeOffset && parentOffset > specOffset) {
//   					specOffset = parentOffset;
//   					spec = parent;
//   				}
//   			}
//   		}
//   		return spec;
//	}
//
//
//    public static String getSubstring(IParseController parseController, int start, int end) {
//        return new String(((SimpleLPGParseController) parseController).getLexer().getLexStream().getInputChars(), start, end-start+1);
//    }
}
