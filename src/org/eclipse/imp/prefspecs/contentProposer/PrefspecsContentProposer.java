/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.contentProposer;

import java.util.ArrayList;

import lpg.runtime.IToken;
import lpg.runtime.PrsStream;

import org.eclipse.imp.editor.SourceProposal;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsLexer;
import org.eclipse.imp.prefspecs.parser.PrefspecsParseController;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.IbooleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IcomboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IdirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.IfileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IintFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IradioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fieldSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.intFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optionalSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.pageBody;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.tabSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.services.IContentProposer;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class PrefspecsContentProposer implements IContentProposer
{
    private IToken getToken(IParseController controller, int offset) {
        PrsStream stream = (PrsStream) controller.getParser();
        PrefspecsParseController psPC= (PrefspecsParseController) controller;
        int index = stream.getTokenIndexAtCharacter(offset),
            token_index = (index < 0 ? -(index - 1) : index),
            previous_index = stream.getPrevious(token_index);
        return stream.getIToken(((stream.getKind(previous_index) == PrefspecsLexer.TK_IDENTIFIER ||
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
    	ArrayList<ICompletionProposal> list = new ArrayList<ICompletionProposal>();

    	ArrayList fieldIDs = getFieldIdentifiers(controller);	
    	
        IToken token = getToken(controller, offset);
        int kind = -1;
        if (token != null) {
        	kind = token.getKind();
        }
        if (token == null || !kindCanBeCompleted(controller, kind)) {
        	list.add(new SourceProposal("No completion exists for that prefix", "", offset));
        	return list.toArray(new ICompletionProposal[list.size()]);	
        }
        
        // Delimit ranges where completion is supported
        setSectionLimits(controller);
        
        String prefix = getPrefix(token, offset);
        int tokenPosition = token.getStartOffset();
        
        // Add tab-name candidates, if appropriate
        if (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) {
        	switch (kind) {
        	case PrefspecsLexer.TK_PROJECT:
        	case PrefspecsLexer.TK_INSTANCE:
        	case PrefspecsLexer.TK_CONFIGURATION:
        	case PrefspecsLexer.TK_DEFAULT:
              if ("default".startsWith(prefix))
            	  list.add(new SourceProposal("default", prefix, offset));
              else if ("configuration".startsWith(prefix))
            	  list.add(new SourceProposal("configuration", prefix, offset));
              else if ("instance".startsWith(prefix))
            	  list.add(new SourceProposal("instance", prefix, offset));
              else if ("project".startsWith(prefix))
            	  list.add(new SourceProposal("project", prefix, offset));
            }
        }
        
        
        // Add field-type-name candidates, if appropriate
        if (tokenPosition > startFields && tokenPosition < endFields)
        {
    		ArrayList ftn = getFieldTypeNames();
        	for (int i = 0; i < ftn.size(); i++) {
        		String candidate = (String)ftn.get(i);
        		if (candidate.startsWith(prefix)) {
        			list.add(new SourceProposal(candidate, prefix, offset));
        		}
        	}
        }
        
            
        // Add identifier candidates, if appropriate
        if ((startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) ||
        	(startConditionals > 0 && tokenPosition > startConditionals && tokenPosition < endConditionals))
        {
            for (int i = 0; i < fieldIDs.size(); i++) {
            	String candidate = (String)fieldIDs.get(i);
            	if (candidate.startsWith(prefix))
            		list.add(new SourceProposal(candidate, prefix, offset));
            }
        }   
                        
        // Add attribute keyword candidates, if appropriate
        if ((tokenPosition > startTabs && tokenPosition < endTabs) ||
            (tokenPosition > startFields && tokenPosition < endFields) ||
            (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom))
        {
    		ArrayList kw = getAttributeKeywords();
        	for (int i = 0; i < kw.size(); i++) {
        		String candidate = (String)kw.get(i);
        		if (candidate.startsWith(prefix)) {
        			list.add(new SourceProposal(candidate, prefix, offset));
        		}
        	}
        }
        
        if (!((tokenPosition > startTabs && tokenPosition < endTabs) ||
            (tokenPosition > startFields && tokenPosition < endFields) ||
            (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) ||
            (startConditionals > 0 && tokenPosition > startConditionals && tokenPosition < endConditionals)))
        {
        	list.add(new SourceProposal("No completions available at this position", "", offset));
        }
        return list.toArray(new ICompletionProposal[list.size()]);
    }

    
    public ArrayList getFieldIdentifiers(IParseController controller)
    {
    	ArrayList<String> result = new ArrayList<String>();
    	pageSpec ps= (pageSpec) controller.getCurrentAst();
        IfieldSpecs specs= ps.getpageBody().getfieldsSpec().getfieldSpecs();

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
    
    
    private ArrayList<String> tabNames = null;
    
    public ArrayList getTabNames() {
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
    
    public ArrayList getAttributeKeywords() {
    	if (attributeKeywords == null) {
	    	attributeKeywords = new ArrayList<String>();
	    	attributeKeywords.add("emptyallowed");
	    	attributeKeywords.add("hasspecial");
	    	attributeKeywords.add("iseditable");
	    	attributeKeywords.add("isremovable");
	    	attributeKeywords.add("range");
    	}
    	return attributeKeywords;
    }
    
    
    private ArrayList<String> fieldTypeNames = null;
    
    public ArrayList getFieldTypeNames() {
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
    	case PrefspecsLexer.TK_PROJECT:
    	case PrefspecsLexer.TK_INSTANCE:
    	case PrefspecsLexer.TK_CONFIGURATION:
    	case PrefspecsLexer.TK_DEFAULT:
    	case PrefspecsLexer.TK_IDENTIFIER:
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
    
    
    private void setSectionLimits(IParseController controller)
    {
        pageSpec ps= (pageSpec) controller.getCurrentAst();
        pageBody pb= ps.getpageBody();
        final optionalSpecs optSpecs= pb.getoptionalSpecs();

        tabsSpec ts= pb.gettabsSpec();
        startTabs= ts.getLeftIToken().getStartOffset();
        endTabs= ts.getRightIToken().getEndOffset();

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
