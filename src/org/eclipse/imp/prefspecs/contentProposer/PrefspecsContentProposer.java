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

package org.eclipse.imp.prefspecs.contentProposer;

import java.util.ArrayList;

import lpg.runtime.IToken;
import lpg.runtime.PrsStream;

import org.eclipse.imp.editor.SourceProposal;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParsersym;
import org.eclipse.imp.prefspecs.parser.Ast.*;
import org.eclipse.imp.services.IContentProposer;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class PrefspecsContentProposer implements IContentProposer
{
    private IToken getToken(IParseController controller, int offset) {
        PrsStream stream = ((SimpleLPGParseController) controller).getParser().getParseStream();
        PrefspecsParseController psPC= (PrefspecsParseController) controller;
        int index = stream.getTokenIndexAtCharacter(offset),
            token_index = (index < 0 ? -(index - 1) : index),
            previous_index = stream.getPrevious(token_index);
        return stream.getIToken(((stream.getKind(previous_index) == PrefspecsParsersym.TK_IDENTIFIER ||
                                  psPC.isKeyword(stream.getKind(previous_index))) &&
                                 offset == stream.getEndOffset(previous_index) + 1)
                                         ? previous_index
                                         : token_index);
    }

    private String getPrefix(IToken token, int offset) {
        //if (token.getKind() == PrefspecsLexer.TK_IDENTIFIER)
            if (offset >= token.getStartOffset() && offset <= token.getEndOffset() + 1)
                return token.toString().substring(0, offset - token.getStartOffset());
        return "";
    }


    /**
     * Returns an array of content proposals applicable relative to the AST of the given
     * parse controller at the given position.
     * 
     * (The provided ITextViewer is not used in the default implementation provided here
     * but but is stipulated by the IContentProposer interface for purposes such as accessing
     * the IDocument for which content proposals are sought.)
     * 
     * @param controller	A parse controller from which the AST of the document being edited
     * 						can be obtained
     * @param int			The offset for which content proposals are sought
     * @param viewer		The viewer in which the document represented by the AST in the given
     * 						parse controller is being displayed (may be null for some implementations)
     * @return				An array of completion proposals applicable relative to the AST of the given
     * 						parse controller at the given position
     */
    public ICompletionProposal[] getContentProposals(IParseController controller, int offset, ITextViewer viewer)
    {
        // START_HERE           
    	ArrayList<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
    	ArrayList<String> fieldIDs = getFieldIdentifiers(controller, offset);

        IToken token = getToken(controller, offset);
        int kind = -1;
        if (token != null) {
        	kind = token.getKind();
        }
        if (token == null || !kindCanBeCompleted(controller, kind)) {
        	result.add(new SourceProposal("No completion exists for that prefix", "", offset));
        	return result.toArray(new ICompletionProposal[result.size()]);	
        }

        // Delimit ranges where completion is supported
        setSectionLimits(controller, offset);

        String prefix = getPrefix(token, offset);
        int tokenPosition = token.getStartOffset();

        // Add tab-name candidates, if appropriate
        if (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) {
        	switch (kind) {
        	case PrefspecsParsersym.TK_PROJECT:
        	case PrefspecsParsersym.TK_INSTANCE:
        	case PrefspecsParsersym.TK_CONFIGURATION:
        	case PrefspecsParsersym.TK_DEFAULT:
              if ("default".startsWith(prefix))
            	  result.add(new SourceProposal("default", prefix, offset));
              else if ("configuration".startsWith(prefix))
            	  result.add(new SourceProposal("configuration", prefix, offset));
              else if ("instance".startsWith(prefix))
            	  result.add(new SourceProposal("instance", prefix, offset));
              else if ("project".startsWith(prefix))
            	  result.add(new SourceProposal("project", prefix, offset));
            }
        }


        // Add field-type-name candidates, if appropriate
        if (tokenPosition > startFields && tokenPosition < endFields)
        {
    		ArrayList<String> ftn = getFieldTypeNames();
        	for(String candidate: ftn) {
        		if (candidate.startsWith(prefix)) {
        			result.add(new SourceProposal(candidate, prefix, offset));
        		}
        	}
        }
        
            
        // Add identifier candidates, if appropriate
        if ((startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) ||
        	(startConditionals > 0 && tokenPosition > startConditionals && tokenPosition < endConditionals) ||
        	(startFields > 0 && tokenPosition > startFields && tokenPosition < endFields))
        {
            for (String candidate: fieldIDs) {
            	if (candidate.startsWith(prefix))
            		result.add(new SourceProposal(candidate, prefix, offset));
            }
        }   

        // Add attribute keyword candidates, if appropriate
        if ((tokenPosition > startTabs && tokenPosition < endTabs) ||
            (tokenPosition > startFields && tokenPosition < endFields) ||
            (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom))
        {
    		ArrayList<String> kw = getAttributeKeywords();
        	for (String candidate: kw) {
        		if (candidate.startsWith(prefix)) {
        			result.add(new SourceProposal(candidate, prefix, offset));
        		}
        	}
        }
        
        if (!((tokenPosition > startTabs && tokenPosition < endTabs) ||
            (tokenPosition > startFields && tokenPosition < endFields) ||
            (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) ||
            (startConditionals > 0 && tokenPosition > startConditionals && tokenPosition < endConditionals)))
        {
        	result.add(new SourceProposal("No completions available at this position", "", offset));
        }
        return result.toArray(new ICompletionProposal[result.size()]);
    }

    
    public ArrayList<String> getFieldIdentifiers(IParseController controller, int offset)
    {
    	ArrayList<String> result = new ArrayList<String>();
    	final prefSpecs prefSpecs= (prefSpecs) controller.getCurrentAst();
    	if (prefSpecs == null) { // prefSpecs may be null if there are errors
    	    return result;
    	}
        pageSpec ps= getPageAtOffset(prefSpecs, offset);
        IfieldSpecs specs = null;
        pageBody pb = ps.getpageBody();
        if (pb != null) // The value returned by getpageBody() may be null
        	specs = pb.getfieldsSpec().getfieldSpecs();
        else
        	return result;
        
        do {
            IfieldSpec fs;
            if (specs instanceof fieldSpecs) {
                fs= ((fieldSpecs) specs).getfieldSpec();
                specs= ((fieldSpecs) specs).getfieldSpecs();
            } else  {
                fs= (IfieldSpec) specs;
                specs= null; // hit the end of the line
            }
            if (fs instanceof booleanFieldSpec) {
                result.add(((booleanFieldSpec) fs).getidentifier().toString());
            } else if (fs instanceof comboFieldSpec) {
                result.add(((comboFieldSpec) fs).getidentifier().toString());
            } else if (fs instanceof dirListFieldSpec) {
                result.add(((dirListFieldSpec) fs).getidentifier().toString());
            } else if (fs instanceof fileFieldSpec) {
                result.add(((fileFieldSpec) fs).getidentifier().toString());
            } else if (fs instanceof intFieldSpec) {
                result.add(((intFieldSpec) fs).getidentifier().toString());
            } else if (fs instanceof radioFieldSpec) {
                result.add(((radioFieldSpec) fs).getidentifier().toString());
            }
        } while (specs != null);
    	return result;
    }
    
    
    private pageSpec getPageAtOffset(prefSpecs prefSpecs, int offset) {
        topLevelItemList itemList= prefSpecs.gettopLevelItems();

        for(int i=0; i < itemList.size(); i++) {
            ItopLevelItem item= itemList.gettopLevelItemAt(i);
            if (item instanceof pageSpec) {
                pageSpec page = (pageSpec) item;
                if (page.getLeftIToken().getStartOffset() <= offset &&
                    page.getRightIToken().getEndOffset() >= offset) {
                    return page;
                }
            }
        }
        return null;
    }


    private ArrayList<String> tabNames = null;
    
    public ArrayList<String> getTabNames() {
    	if (tabNames == null) {
	    	tabNames = new ArrayList<String>();
	    	tabNames.add("default");
	    	tabNames.add("configuration");
	    	tabNames.add("instance");
	    	tabNames.add("project");
    	}
    	return tabNames;
    }
    

    private ArrayList<String> attributeKeywords = null;
    
    public ArrayList<String> getAttributeKeywords() {
    	if (attributeKeywords == null) {
	    	attributeKeywords = new ArrayList<String>();
            attributeKeywords.add("defvalue");
            attributeKeywords.add("emptyallowed");
	    	attributeKeywords.add("hasspecial");
	    	attributeKeywords.add("iseditable");
	    	attributeKeywords.add("isremovable");
	    	attributeKeywords.add("range");
    	}
    	return attributeKeywords;
    }
    
    
    private ArrayList<String> fieldTypeNames = null;
    
    public ArrayList<String> getFieldTypeNames() {
    	if (fieldTypeNames == null) {
    		fieldTypeNames = new ArrayList<String>();
	    	fieldTypeNames.add("boolean");
	    	fieldTypeNames.add("combo");
	    	fieldTypeNames.add("dirlist");
	    	fieldTypeNames.add("file");
	    	fieldTypeNames.add("int");
	    	fieldTypeNames.add("radio");
	    	fieldTypeNames.add("string");
    	}
    	return fieldTypeNames;
    }
    
    
    public boolean kindCanBeCompleted(IParseController controller, int kind) {
    	// TODO:  revise this appropriately for new sets of kinds
    	switch (kind) {
    	case PrefspecsParsersym.TK_PROJECT:
    	case PrefspecsParsersym.TK_INSTANCE:
    	case PrefspecsParsersym.TK_CONFIGURATION:
    	case PrefspecsParsersym.TK_DEFAULT:
    	case PrefspecsParsersym.TK_IDENTIFIER:
    		return true;
    	}
        PrefspecsParseController psPC= (PrefspecsParseController) controller;
    	
    	if (psPC.isKeyword(kind))
    		return true;
    	
    	return false;
    }
    
    
    // To delimit regions governing types of
    // completion that are available
    private int startTabs = 0;
    private int endTabs = 0;
    private int startFields = 0;
    private int endFields = 0;
    private int startCustom = 0;
    private int endCustom = 0;
    private int startConditionals = 0;
    private int endConditionals = 0;
    
    
    private void setSectionLimits(IParseController controller, int offset)
    {
        prefSpecs prefSpecs= (prefSpecs) controller.getCurrentAst();
        if (prefSpecs == null) // current AST may be null if there are errors
            return;
        pageSpec ps= getPageAtOffset(prefSpecs, offset);
        pageBody pb= ps.getpageBody();
        final optionalSpecs optSpecs= pb.getoptionalSpecs();

        tabsSpec ts= pb.gettabsSpec();
        startTabs= (ts != null) ? ts.getLeftIToken().getStartOffset() : 0;
        endTabs= (ts != null) ? ts.getRightIToken().getEndOffset() : 0;

        fieldsSpec fs= pb.getfieldsSpec();
        startFields= fs.getLeftIToken().getStartOffset();
        endFields= fs.getRightIToken().getEndOffset();

        customSpec cso= optSpecs.getcustomSpecOption();
        startCustom= (cso != null ? cso.getLeftIToken().getStartOffset() : 0);
        endCustom= (cso != null ? cso.getRightIToken().getEndOffset() : 0);

        conditionalsSpec condsSpec= optSpecs.getconditionalsSpecOption();
        startConditionals= (condsSpec != null ? condsSpec.getLeftIToken().getStartOffset() : 0);
        endConditionals= (condsSpec != null ? condsSpec.getRightIToken().getEndOffset() : 0);
    }
}
