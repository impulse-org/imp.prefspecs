package prefspecs.safari.contentProposer;

import java.util.ArrayList;
import java.util.List;

import lpg.runtime.IToken;
import lpg.runtime.LexStream;
import lpg.runtime.PrsStream;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.uide.editor.IContentProposer;
import org.eclipse.uide.editor.SourceProposal;
import org.eclipse.uide.parser.IParseController;

import prefspecs.safari.parser.PrefspecsASTNodeLocator;
import prefspecs.safari.parser.PrefspecsLexer;
import prefspecs.safari.parser.Ast.ASTNode;
import prefspecs.safari.parser.Ast.Itab;
import prefspecs.safari.parser.Ast.identifier;

import prefspecs.safari.parser.PrefspecsLexer.*;

public class PrefspecsContentProposer implements IContentProposer
{
    private IToken getToken(IParseController controller, int offset) {
        PrsStream stream = (PrsStream) controller.getParser();
        int index = stream.getTokenIndexAtCharacter(offset),
            token_index = (index < 0 ? -(index - 1) : index),
            previous_index = stream.getPrevious(token_index);
        return stream.getIToken(((stream.getKind(previous_index) == PrefspecsLexer.TK_IDENTIFIER ||
                                  controller.isKeyword(stream.getKind(previous_index))) &&
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
    	ArrayList list = new ArrayList(); // a list of proposals.

    	ArrayList fieldIDs = getFieldIdentifiers(controller);	
    	
        IToken token = getToken(controller, offset);
        int kind = -1;
        if (token != null) {
        	kind = token.getKind();
        }
        if (token == null || !kindCanBeCompleted(controller, kind)) {
        	list.add(new SourceProposal("No completion exists for that prefix", "", offset));
        	return (ICompletionProposal[]) list.toArray(new ICompletionProposal[list.size()]);	
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
        return (ICompletionProposal[]) list.toArray(new ICompletionProposal[list.size()]);
    }

    
    public ArrayList getFieldIdentifiers(IParseController controller)
    {
    	ArrayList result = new ArrayList();
    	String fieldID = null;
    	boolean started = false;

    	PrsStream prsStream = controller.getParser().getParseStream();
    	for (int i = 0; i < prsStream.getStreamLength(); i++) {
    		IToken token = prsStream.getIToken(i);
    		int kind = token.getKind();
    		if (!started) {
    			if (kind == PrefspecsLexer.TK_FIELDS) {
    				started = true;
    			}
    			continue;
    		}
    		if ((kind == PrefspecsLexer.TK_CUSTOM) || (kind	 == PrefspecsLexer.TK_CONDITIONALS))
    			break;
    		
    		switch (token.getKind()) {
    		case PrefspecsLexer.TK_IDENTIFIER:
    			fieldID = token.toString().substring(0, token.getEndOffset() - token.getStartOffset() + 1);
    			result.add(fieldID);
    			break;
    		}
    	}
  	
    	return result;
    }
    
    
    private ArrayList tabNames = null;
    
    public ArrayList getTabNames() {
    	if (tabNames == null) {
	    	tabNames = new ArrayList();
	    	tabNames.add("default");
	    	tabNames.add("configuration");
	    	tabNames.add("instance");
	    	tabNames.add("project");
    	}
    	return tabNames;
    }
    

    private ArrayList attributeKeywords = null;
    
    public ArrayList getAttributeKeywords() {
    	if (attributeKeywords == null) {
	    	attributeKeywords = new ArrayList();
	    	attributeKeywords.add("emptyallowed");
	    	attributeKeywords.add("hasspecial");
	    	attributeKeywords.add("iseditable");
	    	attributeKeywords.add("isremovable");
	    	attributeKeywords.add("range");
    	}
    	return attributeKeywords;
    }
    
    
    private ArrayList fieldTypeNames = null;
    
    public ArrayList getFieldTypeNames() {
    	if (fieldTypeNames == null) {
    		fieldTypeNames = new ArrayList();
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
    	
    	if (controller.isKeyword(kind))
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
    	PrsStream prsStream = controller.getParser().getParseStream();
    	for (int i = 0; i < prsStream.getStreamLength(); i++) {
    		IToken token = prsStream.getIToken(i);
    		int kind = token.getKind();
    		switch(kind) {
    		case PrefspecsLexer.TK_TABS:
    			startTabs = findFirstLeftBrace(prsStream, token);
    			endTabs = findConcludingRightBrace(prsStream, token);
    			break;
    		case PrefspecsLexer.TK_FIELDS:
    			startFields = findFirstLeftBrace(prsStream, token);
    			endFields = findConcludingRightBrace(prsStream, token);
    			break;
    		case PrefspecsLexer.TK_CUSTOM:
    			startCustom = findFirstLeftBrace(prsStream, token);
    			endCustom = findConcludingRightBrace(prsStream, token);
    			break;
    		case PrefspecsLexer.TK_CONDITIONALS:
    			startConditionals = findFirstLeftBrace(prsStream, token);
    			endConditionals = findConcludingRightBrace(prsStream, token);
    			break;	
    		}
    	}	
    }
    
    
    private int findFirstLeftBrace(PrsStream prsStream, IToken token)
    {
    	for (int i = token.getTokenIndex(); i < prsStream.getSize(); i++) {
    		token = prsStream.getIToken(i);
    		if (token.getKind() == PrefspecsLexer.TK_LEFTBRACE) {
    			return token.getEndOffset();
    		}
    	}
    	return -1;
    }
    
    private int findConcludingRightBrace(PrsStream prsStream, IToken token)
    {
    	int count = 0;
    	for (int i = token.getTokenIndex(); i < prsStream.getSize(); i++) {
    		token = prsStream.getIToken(i);
    		if (token.getKind() == PrefspecsLexer.TK_LEFTBRACE) {
    			count++;
    			continue;
    		}
    		if (token.getKind() == PrefspecsLexer.TK_RIGHTBRACE) {
    			count--;
    			if (count > 0) continue;
    			return token.getStartOffset();
    		}
    	}
    	return -1;
    }
 
    
}
