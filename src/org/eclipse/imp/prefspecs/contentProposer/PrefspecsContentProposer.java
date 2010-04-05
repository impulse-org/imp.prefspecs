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

import lpg.runtime.IPrsStream;
import lpg.runtime.IToken;

import org.eclipse.imp.editor.SourceProposal;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParsersym;
import org.eclipse.imp.prefspecs.parser.Ast.ASTNode;
import org.eclipse.imp.prefspecs.parser.Ast.IbooleanSpecificSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.IcolorSpecificSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.IcomboSpecificSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.IfieldSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.ItopLevelItem;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.booleanFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.colorFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.comboFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.conditionalsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.customSpec;
import org.eclipse.imp.prefspecs.parser.Ast.dirListFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.directoryFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.dirlistFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.doubleFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.fieldSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.fieldsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fileFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.fontFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.intFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.intFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.optionalSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.pageBody;
import org.eclipse.imp.prefspecs.parser.Ast.pageSpec;
import org.eclipse.imp.prefspecs.parser.Ast.prefSpecs;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.radioFieldSpec;
import org.eclipse.imp.prefspecs.parser.Ast.stringFieldPropertySpecs;
import org.eclipse.imp.prefspecs.parser.Ast.tabsSpec;
import org.eclipse.imp.prefspecs.parser.Ast.topLevelItemList;
import org.eclipse.imp.services.IContentProposer;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;

public class PrefspecsContentProposer implements IContentProposer {
    private IToken getToken(IParseController controller, int offset) {
        SimpleLPGParseController pc= (SimpleLPGParseController) controller;
        IPrsStream stream = pc.getParser().getIPrsStream();
        int index = stream.getTokenIndexAtCharacter(offset),
            token_index = (index < 0 ? -(index - 1) : index), // either the token surrounding the offset, or the one just after it
            previous_index = stream.getPrevious(token_index);
        return stream.getIToken(((stream.getKind(previous_index) == PrefspecsParsersym.TK_IDENTIFIER ||
                                  pc.isKeyword(stream.getKind(previous_index))) &&
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

    private static final String CONTEXT_ID= "prefspecsSource";

    private TemplateContextType fContextType= new TemplateContextType(CONTEXT_ID, "Coding Templates");

    private final Template fPackageTemplate= new Template("package", "", CONTEXT_ID, "package ${qualifiedPackageName};", false);

    private final Template fBooleanFieldTemplate= new Template("boolean field", "a boolean-typed preference field", CONTEXT_ID, "boolean ${key} { defvalue ${trueFalse}; tooltip \"${tooltip}\"; }", false);
    private final Template fColorFieldTemplate= new Template("color field", "a color-typed preference field", CONTEXT_ID, "color ${key} { defvalue ${intRed}, ${intGreen}, ${intBlue}; tooltip \"${tooltip}\"; }", false);
    private final Template fComboFieldTemplate= new Template("combo field", "an enum preference displayed as a combo-box field", CONTEXT_ID, "combo ${key} { values { ${id1} \"${label1}\", ${id2} \"${label2}\" }; defvalue ${defid}; tooltip \"${tooltip}\"; }", false);
    private final Template fDirectoryFieldTemplate= new Template("directory field", "a directory path preference field", CONTEXT_ID, "directory ${key} { defvalue \"${path}\"; tooltip \"${tooltip}\"; }", false);
    private final Template fDirListFieldTemplate= new Template("dirlist field", "a directory path list preference field", CONTEXT_ID, "dirlist ${key} { defvalue \"${pathList}\"; tooltip \"${tooltip}\"; }", false);
    private final Template fDoubleFieldTemplate= new Template("double field", "a double-typed preference field", CONTEXT_ID, "double ${key} { defvalue ${double}; tooltip \"${tooltip}\"; }", false);
    private final Template fFileFieldTemplate= new Template("file field", "a file path preference field", CONTEXT_ID, "file ${key} { defvalue \"${path}\"; tooltip \"${tooltip}\"; }", false);
    private final Template fFontFieldTemplate= new Template("font field", "a font-typed preference field", CONTEXT_ID, "font ${key} { defvalue \"${familyName}\" ${intHeight} ${normalBoldItalic}; tooltip \"${tooltip}\"; }", false);
    private final Template fIntFieldTemplate= new Template("int field", "an integer-typed preference field", CONTEXT_ID, "int ${key} { defvalue ${int}; tooltip \"${tooltip}\"; }", false);
    private final Template fStringFieldTemplate= new Template("string field", "a string-typed preference field", CONTEXT_ID, "string ${key} { defvalue \"${string}\"; tooltip \"${tooltip}\"; }", false);

    private final Template[] fAllFieldTemplates = new Template[] {
            fBooleanFieldTemplate, fColorFieldTemplate, fComboFieldTemplate, fDirectoryFieldTemplate, fDirListFieldTemplate,
            fDoubleFieldTemplate, fFileFieldTemplate, fFontFieldTemplate, fIntFieldTemplate, fStringFieldTemplate
    };

    private final Template fDefValueFieldAttrTemplate= new Template("defvalue", "the default value for this preference", CONTEXT_ID, "defvalue ${value};", false);
    private final Template fTooltipFieldAttrTemplate= new Template("tooltip", "the tooltip for this preference field", CONTEXT_ID, "tooltip \"${description}\";", false);
    private final Template fLabelFieldAttrTemplate= new Template("label", "the label of this preference field", CONTEXT_ID, "label ${label};", false);

    private final Template[] fAllFieldAttributeTemplates = new Template[] {
            fDefValueFieldAttrTemplate, fTooltipFieldAttrTemplate, fLabelFieldAttrTemplate
    };

    private final Template fPageTemplate= new Template("page", "", CONTEXT_ID, "page ${dotSeparatedPageNames} {\n    fields {\n        ${cursor}\n    }\n}", false);

    public ICompletionProposal[] getContentProposals(IParseController controller, int offset, ITextViewer viewer) {
    	ArrayList<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
    	ArrayList<String> fieldIDs = getFieldIdentifiers(controller, offset);

        IToken token = getToken(controller, offset);
        final prefSpecs prefSpecs= (prefSpecs) controller.getCurrentAst();
        ISourcePositionLocator srcLocator= controller.getSourcePositionLocator();
        int kind = -1;

        if (token != null) {
        	kind = token.getKind();
        }

        if (offset == 0 && prefSpecs.getoptPackageSpec() == null) {
            // suggest a package template if at the beginning of the document
            TemplateContext tc= new DocumentTemplateContext(fContextType, viewer.getDocument(), offset, 0);

            return new ICompletionProposal[] { new TemplateProposal(fPackageTemplate, tc, new Region(offset, 0), null) };
        }

        if (kind == PrefspecsParsersym.TK_EOF_TOKEN) {
            // suggest a page template if at the end of the document
            TemplateContext tc= new DocumentTemplateContext(fContextType, viewer.getDocument(), offset, 0);

            return new ICompletionProposal[] { new TemplateProposal(fPageTemplate, tc, new Region(offset, 0), null) };
        }

        if (token.getStartOffset() > offset || token.getEndOffset() < offset) {
            // Offset is outside any token, and 'token' is presumably the first token after the offset
            // (see the docs for IPrsStream.getTokenIndexAtCharacter()).
            if (kind == PrefspecsParsersym.TK_RIGHTBRACE) {
                // We seem to be inside a block. Find out what kind.
                ASTNode node= (ASTNode) srcLocator.findNode(prefSpecs, token.getStartOffset(), token.getEndOffset());
                if (node instanceof fieldsSpec) {
                    TemplateContext tc= new DocumentTemplateContext(fContextType, viewer.getDocument(), offset, 0);
                    Region r= new Region(offset, 0);
                    for(Template ft: fAllFieldTemplates) {
                        result.add(new TemplateProposal(ft, tc, r, null));
                    }
                    return result.toArray(new ICompletionProposal[result.size()]);
                }
            }
        }
        if (token == null || !kindCanBeCompleted(controller, kind)) {
        	result.add(new SourceProposal("No completion exists at this position", "", offset));
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

        TemplateContext tc= new DocumentTemplateContext(fContextType, viewer.getDocument(), offset - prefix.length(), prefix.length());
        Region r= new Region(offset, prefix.length());

        if (prefSpecs != null && getPageAtOffset(prefSpecs, offset) == null && (prefSpecs.getoptPackageSpec() == null || offset > prefSpecs.getoptPackageSpec().getRightIToken().getEndOffset())) {
            result.add(new TemplateProposal(fPageTemplate, tc, r, null));
        }

        // Add field-type-name candidates, if appropriate
        if (tokenPosition > startFields && tokenPosition < endFields && !insideFieldSpec(prefSpecs, token, srcLocator)) {
    		ArrayList<String> ftn = getFieldTypeNames();
        	for(String candidate: ftn) {
        		if (candidate.startsWith(prefix)) {
        			result.add(new SourceProposal(candidate, prefix, offset));
        		}
        	}
        	for(Template ft: fAllFieldTemplates) {
        	    if (prefix == null || prefix.length() == 0 || ft.getName().startsWith(prefix)) {
        	        result.add(new TemplateProposal(ft, tc, r, null));
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
        if (insideFieldSpec(prefSpecs, token, srcLocator)) {
//            (tokenPosition > startTabs && tokenPosition < endTabs) ||
//            (tokenPosition > startFields && tokenPosition < endFields) ||
//            (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom))
    		ArrayList<String> kw = getAttributeKeywords();
        	for (String candidate: kw) {
        		if (candidate.startsWith(prefix)) {
        			result.add(new SourceProposal(candidate, prefix, offset));
        		}
        	}
        	for(Template at: fAllFieldAttributeTemplates) {
        	    if (prefix == null || prefix.length() == 0 || at.getName().startsWith(prefix)) {
        	        result.add(new TemplateProposal(at, tc, r, null));
        	    }
        	}
        }
        
//        if (!((tokenPosition > startTabs && tokenPosition < endTabs) ||
//              (tokenPosition > startFields && tokenPosition < endFields) ||
//              (startCustom > 0 && tokenPosition > startCustom && tokenPosition < endCustom) ||
//              (startConditionals > 0 && tokenPosition > startConditionals && tokenPosition < endConditionals)))
        if (result.size() == 0) {
        	result.add(new SourceProposal("No completions available at this position", "", offset));
        }
        return result.toArray(new ICompletionProposal[result.size()]);
    }

    private boolean insideFieldSpec(prefSpecs ps, IToken token, ISourcePositionLocator srcLocator) {
        ASTNode node= (ASTNode) srcLocator.findNode(ps, token.getStartOffset(), token.getEndOffset());

        while (node != null) {
            if (node instanceof booleanFieldPropertySpecs || node instanceof colorFieldPropertySpecs || node instanceof comboFieldPropertySpecs ||
                node instanceof directoryFieldPropertySpecs || node instanceof dirlistFieldPropertySpecs || node instanceof doubleFieldPropertySpecs ||
                node instanceof fontFieldPropertySpecs || node instanceof intFieldPropertySpecs || node instanceof radioFieldPropertySpecs ||
                node instanceof stringFieldPropertySpecs)
                return true;
            node = (ASTNode) node.getParent();
        }
        return false;
    }

    public ArrayList<String> getFieldIdentifiers(IParseController controller, int offset) {
    	ArrayList<String> result = new ArrayList<String>();
    	final prefSpecs prefSpecs= (prefSpecs) controller.getCurrentAst();
    	if (prefSpecs == null) { // prefSpecs may be null if there are errors
    	    return result;
    	}
        pageSpec ps= getPageAtOffset(prefSpecs, offset);
        IfieldSpecs specs = null;
        if (ps == null) {
            return result;
        }
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
//	    	attributeKeywords.add("hasspecial");
//	    	attributeKeywords.add("iseditable");
//	    	attributeKeywords.add("isremovable");
            attributeKeywords.add("label");
            attributeKeywords.add("range");
            attributeKeywords.add("tooltip");
    	}
    	return attributeKeywords;
    }

    private ArrayList<String> fieldTypeNames = null;
    
    public ArrayList<String> getFieldTypeNames() {
    	if (fieldTypeNames == null) {
    		fieldTypeNames = new ArrayList<String>();
	    	fieldTypeNames.add("boolean");
            fieldTypeNames.add("color");
	    	fieldTypeNames.add("combo");
            fieldTypeNames.add("directory");
	    	fieldTypeNames.add("dirlist");
            fieldTypeNames.add("double");
	    	fieldTypeNames.add("file");
	    	fieldTypeNames.add("font");
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

    private void setSectionLimits(IParseController controller, int offset) {
        prefSpecs prefSpecs= (prefSpecs) controller.getCurrentAst();
        if (prefSpecs == null) // current AST may be null if there are errors
            return;
        pageSpec ps= getPageAtOffset(prefSpecs, offset);
        if (ps != null) {
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
}
