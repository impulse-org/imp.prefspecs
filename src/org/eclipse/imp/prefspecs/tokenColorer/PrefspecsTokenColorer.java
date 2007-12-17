/*
 * (C) Copyright IBM Corporation 2007
 * 
 * This file is part of the Eclipse IMP.
 */
package org.eclipse.imp.prefspecs.tokenColorer;

import lpg.runtime.IToken;

import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.prefspecs.parser.PrefspecsParsersym;
import org.eclipse.imp.services.ITokenColorer;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class PrefspecsTokenColorer implements PrefspecsParsersym, ITokenColorer {

    TextAttribute commentAttribute, keywordAttribute, stringAttribute, numberAttribute, doubleAttribute, identifierAttribute,
    				partsKeywordAttribute, valueKeywordAttribute, tabKeywordAttribute, fieldTypeKeywordAttribute,
    				modifierKeywordAttribute;

    public TextAttribute getColoring(IParseController controller, Object o) {
        IToken token= (IToken) o;
        switch (token.getKind()) {
            // START_HERE
            case TK_IDENTIFIER:
                 return identifierAttribute;
            case TK_NUMBER:
                return numberAttribute;
            case TK_DoubleLiteral:
                return doubleAttribute;
//          case TK_StringLiteral:
//               return stringAttribute;
            case TK_PAGE: case TK_TABS: case TK_FIELDS:
            case TK_CUSTOM: case TK_CONDITIONALS:
            	return partsKeywordAttribute;
            case TK_DEFAULT: case TK_CONFIGURATION:
            case TK_INSTANCE: case TK_PROJECT:
            	return tabKeywordAttribute;
            case TK_IN: case TK_OUT:
            case TK_TRUE: case TK_FALSE:
            	return valueKeywordAttribute;
            case TK_BOOLEAN: case TK_COMBO: case TK_DIRLIST: case TK_FILE:
            case TK_INT: case TK_RADIO: case TK_STRING:
            	return fieldTypeKeywordAttribute;
            case TK_HASSPECIAL: case TK_RANGE:
                return keywordAttribute;
            case TK_SINGLE_LINE_COMMENT:
            	return commentAttribute;
            default: {
                SimpleLPGParseController lpgPC= (SimpleLPGParseController) controller;
                if (lpgPC.isKeyword(token.getKind()))
                     return keywordAttribute;
               else return null;
            }
        }
    }

    public PrefspecsTokenColorer() {
        super();
        // TODO:  Define text attributes for the various
        // token types that will have their text colored
        Display display = Display.getDefault();
        commentAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_GREEN), null, SWT.ITALIC);
        stringAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_BLUE), null, SWT.BOLD);
        identifierAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_BLACK), null, SWT.NORMAL);
        doubleAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_BLACK), null, SWT.BOLD);
        numberAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_BLACK), null, SWT.BOLD);
        partsKeywordAttribute  = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_BLUE), null, SWT.BOLD);
        valueKeywordAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_MAGENTA), null, SWT.ITALIC);
        tabKeywordAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_RED), null, SWT.BOLD);
        fieldTypeKeywordAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_GRAY), null, SWT.BOLD);
        modifierKeywordAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_MAGENTA), null, SWT.ITALIC);
 
        keywordAttribute = new TextAttribute(display.getSystemColor(SWT.COLOR_DARK_MAGENTA), null, SWT.BOLD);
    }

    public void setLanguage(String language) {
    }

    public IRegion calculateDamageExtent(IRegion seed) {
        return seed;
    }
}
