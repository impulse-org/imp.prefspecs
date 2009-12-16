
////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2007 IBM Corporation.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
//Contributors:
//    Stan Sutton (suttons@us.ibm.com) - initial API and implementation
//    Robert Fuhrer (rfuhrer@watson.ibm.com)
////////////////////////////////////////////////////////////////////////////////

package org.eclipse.imp.prefspecs.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import lpg.runtime.BacktrackingParser;
import lpg.runtime.BadParseException;
import lpg.runtime.BadParseSymFileException;
import lpg.runtime.DiagnoseParser;
import lpg.runtime.ErrorToken;
import lpg.runtime.IAst;
import lpg.runtime.ILexStream;
import lpg.runtime.IPrsStream;
import lpg.runtime.IToken;
import lpg.runtime.Monitor;
import lpg.runtime.NotBacktrackParseTableException;
import lpg.runtime.NullExportedSymbolsException;
import lpg.runtime.NullTerminalSymbolsException;
import lpg.runtime.ParseErrorCodes;
import lpg.runtime.ParseTable;
import lpg.runtime.PrsStream;
import lpg.runtime.RuleAction;
import lpg.runtime.UndefinedEofSymbolException;
import lpg.runtime.UnimplementedTerminalsException;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import org.eclipse.imp.prefspecs.parser.Ast.*;

public class PrefspecsParser implements RuleAction, IParser
{
    private PrsStream prsStream = null;
    
    private boolean unimplementedSymbolsWarning = false;

    private static ParseTable prsTable = new PrefspecsParserprs();
    public ParseTable getParseTable() { return prsTable; }

    private BacktrackingParser btParser = null;
    public BacktrackingParser getParser() { return btParser; }

    private void setResult(Object object) { btParser.setSym1(object); }
    public Object getRhsSym(int i) { return btParser.getSym(i); }

    public int getRhsTokenIndex(int i) { return btParser.getToken(i); }
    public IToken getRhsIToken(int i) { return prsStream.getIToken(getRhsTokenIndex(i)); }
    
    public int getRhsFirstTokenIndex(int i) { return btParser.getFirstToken(i); }
    public IToken getRhsFirstIToken(int i) { return prsStream.getIToken(getRhsFirstTokenIndex(i)); }

    public int getRhsLastTokenIndex(int i) { return btParser.getLastToken(i); }
    public IToken getRhsLastIToken(int i) { return prsStream.getIToken(getRhsLastTokenIndex(i)); }

    public int getLeftSpan() { return btParser.getFirstToken(); }
    public IToken getLeftIToken()  { return prsStream.getIToken(getLeftSpan()); }

    public int getRightSpan() { return btParser.getLastToken(); }
    public IToken getRightIToken() { return prsStream.getIToken(getRightSpan()); }

    public int getRhsErrorTokenIndex(int i)
    {
        int index = btParser.getToken(i);
        IToken err = prsStream.getIToken(index);
        return (err instanceof ErrorToken ? index : 0);
    }
    public ErrorToken getRhsErrorIToken(int i)
    {
        int index = btParser.getToken(i);
        IToken err = prsStream.getIToken(index);
        return (ErrorToken) (err instanceof ErrorToken ? err : null);
    }

    public void reset(ILexStream lexStream)
    {
        prsStream = new PrsStream(lexStream);
        btParser.reset(prsStream);

        try
        {
            prsStream.remapTerminalSymbols(orderedTerminalSymbols(), prsTable.getEoftSymbol());
        }
        catch(NullExportedSymbolsException e) {
        }
        catch(NullTerminalSymbolsException e) {
        }
        catch(UnimplementedTerminalsException e)
        {
            if (unimplementedSymbolsWarning) {
                java.util.ArrayList unimplemented_symbols = e.getSymbols();
                System.out.println("The Lexer will not scan the following token(s):");
                for (int i = 0; i < unimplemented_symbols.size(); i++)
                {
                    Integer id = (Integer) unimplemented_symbols.get(i);
                    System.out.println("    " + PrefspecsParsersym.orderedTerminalSymbols[id.intValue()]);               
                }
                System.out.println();
            }
        }
        catch(UndefinedEofSymbolException e)
        {
            throw new Error(new UndefinedEofSymbolException
                                ("The Lexer does not implement the Eof symbol " +
                                 PrefspecsParsersym.orderedTerminalSymbols[prsTable.getEoftSymbol()]));
        } 
    }
    
    public PrefspecsParser()
    {
        try
        {
            btParser = new BacktrackingParser(prsStream, prsTable, (RuleAction) this);
        }
        catch (NotBacktrackParseTableException e)
        {
            throw new Error(new NotBacktrackParseTableException
                                ("Regenerate PrefspecsParserprs.java with -BACKTRACK option"));
        }
        catch (BadParseSymFileException e)
        {
            throw new Error(new BadParseSymFileException("Bad Parser Symbol File -- PrefspecsParsersym.java"));
        }
    }
    
    public PrefspecsParser(ILexStream lexStream)
    {
        this();
        reset(lexStream);
    }
    
    public int numTokenKinds() { return PrefspecsParsersym.numTokenKinds; }
    public String[] orderedTerminalSymbols() { return PrefspecsParsersym.orderedTerminalSymbols; }
    public String getTokenKindName(int kind) { return PrefspecsParsersym.orderedTerminalSymbols[kind]; }
    public int getEOFTokenKind() { return prsTable.getEoftSymbol(); }
    public IPrsStream getIPrsStream() { return prsStream; }

    /**
     * @deprecated replaced by {@link #getIPrsStream()}
     *
     */
    public PrsStream getPrsStream() { return prsStream; }

    /**
     * @deprecated replaced by {@link #getIPrsStream()}
     *
     */
    public PrsStream getParseStream() { return prsStream; }

    public Object parser()
    {
        return parser(null, 0);
    }
    
    public Object parser(Monitor monitor)
    {
        return parser(monitor, 0);
    }
    
    public Object parser(int error_repair_count)
    {
        return parser(null, error_repair_count);
    }

    public Object parser(Monitor monitor, int error_repair_count)
    {
        btParser.setMonitor(monitor);
        
        try
        {
            return (Object) btParser.fuzzyParse(error_repair_count);
        }
        catch (BadParseException e)
        {
            prsStream.reset(e.error_token); // point to error token

            DiagnoseParser diagnoseParser = new DiagnoseParser(prsStream, prsTable);
            diagnoseParser.diagnose(e.error_token);
        }

        return null;
    }

    //
    // Additional entry points, if any
    //
    

    public final String DEFAULT_TAB = "default";
    public final String CONFIGURATION_TAB = "configuration";
    public final String INSTANCE_TAB = "instance";
    public final String PROJECT_TAB = "project";
    public final String BOOLEAN_TYPE = "boolean";
    public final String COMBO_TYPE = "combo";
    public final String DIRLIST_TYPE = "dirlist";
    public final String FILE_TYPE = "file";
    public final String INT_TYPE = "int";
    public final String RADIO_TYPE = "radio";
    public final String STRING_TYPE = "string";

    public static List<String> fieldNames = new ArrayList<String>();        
    public static List<String> booleanFields = new ArrayList<String>();
    public static HashMap<String,String> fieldTypes = new HashMap<String,String>();

    public static void reportError(String msg) {
        System.err.println(msg);
    }


    //////////////////////////////////////////////////////////////////
    //
    // Code here is from the original grammar template; it is used by
    // the parse controller template (although parts evidently can be
    // commented out)
    //

    Stack<SymbolTable<IAst>> symbolTableStack = null;
    SymbolTable<IAst> topLevelSymbolTable = null;
    public SymbolTable<IAst> getTopLevelSymbolTable() { return topLevelSymbolTable; }

    //
    // TODO: In the future, the user will be able to identify scope structures
    // (special non terminals such as block and functionDeclaration below) in
    // the grammar specification that carry symbol table information. The class
    // associated with such symbols will implement a special IScope interface and
    // will be required to specify an implementation of the method "getSymbolTable"
    // that is defined in IScope. Thus, the implementation of this funftion will
    // be simpler as it would only need to search for an instance of IScope.
    //
    public SymbolTable<IAst> getEnclosingSymbolTable(IAst n) {
//            for ( ; n != null; n = n.getParent())
    //                if (n instanceof block)
    //                     return ((block) n).getSymbolTable();
//                else if (n instanceof functionDeclaration)
//                     return ((functionDeclaration) n).getSymbolTable();
        return getTopLevelSymbolTable();
    }

    public void resolve(ASTNode root) {
        fieldNames = new ArrayList<String>();
        booleanFields = new ArrayList<String>();
        fieldTypes = new HashMap<String,String>();
        if (root != null) {
            // symbolTableStack = new Stack<SymbolTable<IAst>>();
            // topLevelSymbolTable = new SymbolTable<IAst>(null);
            // symbolTableStack.push(topLevelSymbolTable);
            root.accept(new SymbolTableVisitor());
        }
    }


    /*
     * A visitor for ASTs.  Its purpose is to build a symbol table
     * for declared symbols and resolved identifier in expressions.
     */
     
    private final class SymbolTableVisitor extends AbstractVisitor {
        public void unimplementedVisitor(String s) { /* Useful for debugging: System.out.println(s); */ }
        
        public void emitError(IToken id, String message) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getILexStream().getLocation(id.getStartOffset(), id.getEndOffset()),
                prsStream.getILexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
        }

        public void emitError(ASTNode node, String message) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getILexStream().getLocation(
                    node.getLeftIToken().getStartOffset(), node.getRightIToken().getEndOffset()),
                prsStream.getILexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
        }

       public void emitError(int startOffset, int endOffset, String message) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getILexStream().getLocation(startOffset, endOffset),
                prsStream.getILexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
        }

        //
        // Visitors for tab specs
        //
        
        protected boolean inDefaultTabSpec = false;
   
        public boolean visit(defaultTabSpec n) {
        inDefaultTabSpec = true;
        return true;
        }
    
        public void endVisit(defaultTabSpec n) {
            inDefaultTabSpec = false;
        }


        //
        // Visitors for properties
        //
        
        public boolean visit(isRemovableSpec n) {
            if (n.getbooleanValue() instanceof booleanValue__TRUE) {
                if (inDefaultTabSpec || inCustomSpecForDefaultTab) {
                    emitError(n, "Field values on default tab are not removable");
                }
            }
            return true;
        }
    
        public void endVisit(isRemovableSpec n) { }
          



        //
        // Visitors for field specs
        //
        public boolean visit(booleanFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            booleanFields.add(id);
            fieldTypes.put(id, BOOLEAN_TYPE);
            return true;
        }
    
        public void endVisit(booleanFieldSpec n) { }
        
        
        public boolean visit(comboFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, COMBO_TYPE);
            return true;
        }
    
        public void endVisit(comboFieldSpec n) { }
        
        
         public boolean visit(dirListFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, DIRLIST_TYPE);
            return true;
        }
    
        public void endVisit(dirListFieldSpec n) { }

        
        public boolean visit(fileFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, FILE_TYPE);
            return true;
        }
    
        public void endVisit(fileFieldSpec n) { }
        
        
        public boolean visit(intFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, INT_TYPE);
            return true;
        }
    
        public void endVisit(intFieldSpec n) { }
        
        
         public boolean visit(radioFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, RADIO_TYPE);
            return true;
        }
    
        public void endVisit(radioFieldSpec n) { }


           public boolean visit(stringFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, STRING_TYPE);
            return true;
        }
    
        public void endVisit(stringFieldSpec n) { }


         //
         // Visitors for custom rule and conditional specs
         //
         
         protected boolean inCustomSpecForDefaultTab = false;	
          
         public boolean visit(customRule n) {
            String id = n.getidentifier().toString();
            if (!fieldNames.contains(id)) {
                emitError(n.getidentifier().getIToken(), "Field identifier not decleared");
            }

            // Check whether properties are appropriate to field type
            // (Note:  Only inappropriate type-specific properties can be invalid for a typed field)
            String fieldType = fieldTypes.get(id);
            if (fieldType != null) {
                newPropertySpecs propertySpecs = (newPropertySpecs) n.getnewPropertySpecs();
                ItypeCustomSpecs typeCustomSpecs = propertySpecs.gettypeCustomSpecs();
                if (typeCustomSpecs != null) {
                        //if ((fieldType.equals(BOOLEAN_TYPE) && !(typeCustomSpecs instanceof booleanCustomSpec)) ||
                        if ((fieldType.equals(BOOLEAN_TYPE) && !(typeCustomSpecs instanceof booleanSpecialSpec)) ||
                            (fieldType.equals(COMBO_TYPE) && !(typeCustomSpecs instanceof IstringCustomSpec)) ||
                            (fieldType.equals(DIRLIST_TYPE) && !(typeCustomSpecs instanceof IstringCustomSpec)) ||
                            (fieldType.equals(FILE_TYPE) && !(typeCustomSpecs instanceof IstringCustomSpec)) ||
                            (fieldType.equals(INT_TYPE) && !(typeCustomSpecs instanceof IintCustomSpec)) ||
                            //(fieldType.equals(RADIO_TYPE) && !(typeCustomSpecs instanceof radioCustomSpec)) ||
                            (fieldType.equals(STRING_TYPE) && !(typeCustomSpecs instanceof IstringCustomSpec)))
                        {
                            String propertyMsg = "Property specification not consistent with field type";

                            emitError((ASTNode) typeCustomSpecs, propertyMsg);
                            //int startOffset = n.getidentifier().getIToken().getStartOffset();
                            //int endOffset = n.getRIGHTBRACE().getIToken().getEndOffset();
                            //emitError(startOffset, endOffset, "Property specification not consistent with field type");
                        }
                }
            }
            
            if (n.gettab() instanceof tab__DEFAULT) {
                // Have a the default tab
                inCustomSpecForDefaultTab = true;
            }
        
            return true;
        }


        public void endVisit(customRule n) { 
            inCustomSpecForDefaultTab = false;
        }
    
    
    
        public boolean visit(conditionalSpec__identifier_WITH_identifier n) {
            String id = n.getidentifier().toString();
            if (!fieldNames.contains(id)) {
                emitError(n.getidentifier().getIToken(), "Identifier does not represent a declared field");
            }
            id = n.getidentifier3().toString();
            if (!fieldNames.contains(id)) {
                emitError(n.getidentifier3().getIToken(), "Identifier does not represent a declared field");
            } else if (!booleanFields.contains(id)) {
                emitError(n.getidentifier3().getIToken(), "Condition not represented by a boolean field");
            }
            return true;
        }

        public void endVisit(conditionalSpec__identifier_WITH_identifier n) { }


         public boolean visit(conditionalSpec__identifier_AGAINST_identifier n) {
            String id = n.getidentifier().toString();
            if (!fieldNames.contains(id)) {
                emitError(n.getidentifier().getIToken(), "Identifier does not represent a declared field");
            }
            id = n.getidentifier3().toString();
            if (!fieldNames.contains(id)) {
                emitError(n.getidentifier3().getIToken(), "Identifier does not represent a declared field");
            } else if (!booleanFields.contains(id)) {
                emitError(n.getidentifier3().getIToken(), "Condition not represented by a boolean field");
            }
            return true;
        }

        public void endVisit(conditionalSpec__identifier_AGAINST_identifier n) { }


        


    } // End SymbolTableVisitor
    

    public void ruleAction(int ruleNumber)
    {
        switch (ruleNumber)
        {

            //
            // Rule 1:  prefSpecs ::= optPackageSpec optDetailsSpec topLevelItems
            //
            case 1: {
               //#line 55 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new prefSpecs(getLeftIToken(), getRightIToken(),
                                  (optPackageSpec)getRhsSym(1),
                                  (optDetailsSpec)getRhsSym(2),
                                  (topLevelItemList)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 2:  optPackageSpec ::= $Empty
            //
            case 2: {
               //#line 57 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 3:  optPackageSpec ::= PACKAGE$ packageName ;$
            //
            case 3: {
               //#line 57 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optPackageSpec(getLeftIToken(), getRightIToken(),
                                       (IpackageName)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 4:  packageName ::= identifier
            //
            case 4:
                break;
            //
            // Rule 5:  packageName ::= packageName .$ identifier
            //
            case 5: {
               //#line 60 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new packageName(getLeftIToken(), getRightIToken(),
                                    (IpackageName)getRhsSym(1),
                                    (identifier)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 6:  optDetailsSpec ::= $Empty
            //
            case 6: {
               //#line 62 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 7:  optDetailsSpec ::= DETAILS$ onOff ;$
            //
            case 7: {
               //#line 62 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optDetailsSpec(getLeftIToken(), getRightIToken(),
                                       (IonOff)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 8:  onOff ::= ON
            //
            case 8: {
               //#line 64 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new onOff__ON(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 9:  onOff ::= OFF
            //
            case 9: {
               //#line 64 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new onOff__OFF(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 10:  topLevelItems ::= topLevelItem
            //
            case 10: {
               //#line 66 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new topLevelItemList((ItopLevelItem)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 11:  topLevelItems ::= topLevelItems topLevelItem
            //
            case 11: {
               //#line 66 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((topLevelItemList)getRhsSym(1)).add((ItopLevelItem)getRhsSym(2));
                break;
            }
            //
            // Rule 12:  topLevelItem ::= typeSpec
            //
            case 12:
                break;
            //
            // Rule 13:  topLevelItem ::= pageSpec
            //
            case 13:
                break;
            //
            // Rule 14:  typeSpec ::= CHOICETYPE$ identifier {$ staticOrDynamicValues }$
            //
            case 14: {
               //#line 72 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeSpec(getLeftIToken(), getRightIToken(),
                                 (identifier)getRhsSym(2),
                                 (IstaticOrDynamicValues)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 15:  pageSpec ::= PAGE$ pageName {$ pageBody }$
            //
            case 15: {
               //#line 74 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new pageSpec(getLeftIToken(), getRightIToken(),
                                 (pageName)getRhsSym(2),
                                 (pageBody)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 16:  pageName ::= pagePath identifier$name
            //
            case 16: {
               //#line 76 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new pageName(getLeftIToken(), getRightIToken(),
                                 (pagePath)getRhsSym(1),
                                 (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 17:  pagePath ::= $Empty
            //
            case 17: {
               //#line 78 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 18:  pagePath ::= pagePath identifier .$
            //
            case 18: {
               //#line 79 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new pagePath(getLeftIToken(), getRightIToken(),
                                 (pagePath)getRhsSym(1),
                                 (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 19:  pageBody ::= $Empty
            //
            case 19: {
               //#line 81 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 20:  pageBody ::= tabsSpec fieldsSpec optionalSpecs
            //
            case 20: {
               //#line 82 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new pageBody(getLeftIToken(), getRightIToken(),
                                 (tabsSpec)getRhsSym(1),
                                 (fieldsSpec)getRhsSym(2),
                                 (optionalSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 21:  optionalSpecs ::= customSpecOption conditionalsSpecOption
            //
            case 21: {
               //#line 84 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optionalSpecs(getLeftIToken(), getRightIToken(),
                                      (customSpec)getRhsSym(1),
                                      (conditionalsSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 22:  customSpecOption ::= $Empty
            //
            case 22: {
               //#line 86 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 23:  customSpecOption ::= customSpec
            //
            case 23:
                break;
            //
            // Rule 24:  conditionalsSpecOption ::= $Empty
            //
            case 24: {
               //#line 89 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 25:  conditionalsSpecOption ::= conditionalsSpec
            //
            case 25:
                break;
            //
            // Rule 26:  tabsSpec ::= $Empty
            //
            case 26: {
               //#line 94 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 27:  tabsSpec ::= TABS$ {$ tabSpecs }$
            //
            case 27: {
               //#line 94 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tabsSpec(getLeftIToken(), getRightIToken(),
                                 (tabSpecList)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 28:  tabSpecs ::= $Empty
            //
            case 28: {
               //#line 96 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tabSpecList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 29:  tabSpecs ::= tabSpecs tabSpec
            //
            case 29: {
               //#line 96 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((tabSpecList)getRhsSym(1)).add((ItabSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 30:  tabSpec ::= defaultTabSpec
            //
            case 30:
                break;
            //
            // Rule 31:  tabSpec ::= configurationTabSpec
            //
            case 31:
                break;
            //
            // Rule 32:  tabSpec ::= instanceTabSpec
            //
            case 32:
                break;
            //
            // Rule 33:  tabSpec ::= projectTabSpec
            //
            case 33:
                break;
            //
            // Rule 34:  defaultTabSpec ::= DEFAULT$ inout {$ generalSpecs }$
            //
            case 34: {
               //#line 99 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new defaultTabSpec(getLeftIToken(), getRightIToken(),
                                       (Iinout)getRhsSym(2),
                                       (generalSpecList)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 35:  configurationTabSpec ::= CONFIGURATION$ inout {$ generalSpecs }$
            //
            case 35: {
               //#line 101 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new configurationTabSpec(getLeftIToken(), getRightIToken(),
                                             (Iinout)getRhsSym(2),
                                             (generalSpecList)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 36:  instanceTabSpec ::= INSTANCE$ inout {$ generalSpecs }$
            //
            case 36: {
               //#line 103 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new instanceTabSpec(getLeftIToken(), getRightIToken(),
                                        (Iinout)getRhsSym(2),
                                        (generalSpecList)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 37:  projectTabSpec ::= PROJECT$ inout {$ generalSpecs }$
            //
            case 37: {
               //#line 105 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new projectTabSpec(getLeftIToken(), getRightIToken(),
                                       (Iinout)getRhsSym(2),
                                       (generalSpecList)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 38:  inout ::= IN
            //
            case 38: {
               //#line 109 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new inout__IN(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 39:  inout ::= OUT
            //
            case 39: {
               //#line 109 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new inout__OUT(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 40:  fieldsSpec ::= FIELDS$ {$ fieldSpecs }$
            //
            case 40: {
               //#line 114 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fieldsSpec(getLeftIToken(), getRightIToken(),
                                   (IfieldSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 41:  fieldSpecs ::= $Empty
            //
            case 41: {
               //#line 116 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 42:  fieldSpecs ::= fieldSpec
            //
            case 42:
                break;
            //
            // Rule 43:  fieldSpecs ::= fieldSpecs fieldSpec
            //
            case 43: {
               //#line 118 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fieldSpecs(getLeftIToken(), getRightIToken(),
                                   (IfieldSpecs)getRhsSym(1),
                                   (IfieldSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 44:  fieldSpec ::= booleanFieldSpec
            //
            case 44:
                break;
            //
            // Rule 45:  fieldSpec ::= colorFieldSpec
            //
            case 45:
                break;
            //
            // Rule 46:  fieldSpec ::= comboFieldSpec
            //
            case 46:
                break;
            //
            // Rule 47:  fieldSpec ::= directoryFieldSpec
            //
            case 47:
                break;
            //
            // Rule 48:  fieldSpec ::= dirListFieldSpec
            //
            case 48:
                break;
            //
            // Rule 49:  fieldSpec ::= doubleFieldSpec
            //
            case 49:
                break;
            //
            // Rule 50:  fieldSpec ::= fileFieldSpec
            //
            case 50:
                break;
            //
            // Rule 51:  fieldSpec ::= fontFieldSpec
            //
            case 51:
                break;
            //
            // Rule 52:  fieldSpec ::= intFieldSpec
            //
            case 52:
                break;
            //
            // Rule 53:  fieldSpec ::= radioFieldSpec
            //
            case 53:
                break;
            //
            // Rule 54:  fieldSpec ::= stringFieldSpec
            //
            case 54:
                break;
            //
            // Rule 55:  booleanFieldSpec ::= BOOLEAN$ identifier booleanFieldPropertySpecs optConditionalSpec
            //
            case 55: {
               //#line 133 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2),
                                         (booleanFieldPropertySpecs)getRhsSym(3),
                                         (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 56:  colorFieldSpec ::= COLOR$ identifier colorFieldPropertySpecs optConditionalSpec
            //
            case 56: {
               //#line 134 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (colorFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 57:  comboFieldSpec ::= COMBO$ identifier comboFieldPropertySpecs optConditionalSpec
            //
            case 57: {
               //#line 135 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (comboFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 58:  directoryFieldSpec ::= DIRECTORY$ identifier directoryFieldPropertySpecs optConditionalSpec
            //
            case 58: {
               //#line 136 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new directoryFieldSpec(getLeftIToken(), getRightIToken(),
                                           (identifier)getRhsSym(2),
                                           (directoryFieldPropertySpecs)getRhsSym(3),
                                           (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 59:  dirListFieldSpec ::= DIRLIST$ identifier dirlistFieldPropertySpecs optConditionalSpec
            //
            case 59: {
               //#line 137 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new dirListFieldSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2),
                                         (dirlistFieldPropertySpecs)getRhsSym(3),
                                         (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 60:  doubleFieldSpec ::= DOUBLE$ identifier doubleFieldPropertySpecs optConditionalSpec
            //
            case 60: {
               //#line 138 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleFieldSpec(getLeftIToken(), getRightIToken(),
                                        (identifier)getRhsSym(2),
                                        (doubleFieldPropertySpecs)getRhsSym(3),
                                        (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 61:  fileFieldSpec ::= FILE$ identifier fileFieldPropertySpecs optConditionalSpec
            //
            case 61: {
               //#line 139 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fileFieldSpec(getLeftIToken(), getRightIToken(),
                                      (identifier)getRhsSym(2),
                                      (fileFieldPropertySpecs)getRhsSym(3),
                                      (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 62:  fontFieldSpec ::= FONT$ identifier fontFieldPropertySpecs optConditionalSpec
            //
            case 62: {
               //#line 140 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontFieldSpec(getLeftIToken(), getRightIToken(),
                                      (identifier)getRhsSym(2),
                                      (fontFieldPropertySpecs)getRhsSym(3),
                                      (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 63:  intFieldSpec ::= INT$ identifier intFieldPropertySpecs optConditionalSpec
            //
            case 63: {
               //#line 141 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intFieldSpec(getLeftIToken(), getRightIToken(),
                                     (identifier)getRhsSym(2),
                                     (intFieldPropertySpecs)getRhsSym(3),
                                     (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 64:  radioFieldSpec ::= RADIO$ identifier radioFieldPropertySpecs optConditionalSpec
            //
            case 64: {
               //#line 142 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (radioFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 65:  stringFieldSpec ::= STRING$ identifier stringFieldPropertySpecs optConditionalSpec
            //
            case 65: {
               //#line 143 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringFieldSpec(getLeftIToken(), getRightIToken(),
                                        (identifier)getRhsSym(2),
                                        (stringFieldPropertySpecs)getRhsSym(3),
                                        (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 66:  booleanFieldPropertySpecs ::= $Empty
            //
            case 66: {
               //#line 146 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 67:  booleanFieldPropertySpecs ::= {$ booleanSpecificSpecs }$
            //
            case 67: {
               //#line 146 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (booleanSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 68:  colorFieldPropertySpecs ::= $Empty
            //
            case 68: {
               //#line 147 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 69:  colorFieldPropertySpecs ::= {$ colorSpecificSpecs }$
            //
            case 69: {
               //#line 147 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (colorSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 70:  comboFieldPropertySpecs ::= $Empty
            //
            case 70: {
               //#line 148 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 71:  comboFieldPropertySpecs ::= {$ comboSpecificSpecs }$
            //
            case 71: {
               //#line 148 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (comboSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 72:  directoryFieldPropertySpecs ::= $Empty
            //
            case 72: {
               //#line 149 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 73:  directoryFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 73: {
               //#line 149 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new directoryFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                    (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 74:  dirlistFieldPropertySpecs ::= $Empty
            //
            case 74: {
               //#line 150 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 75:  dirlistFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 75: {
               //#line 150 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new dirlistFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 76:  doubleFieldPropertySpecs ::= $Empty
            //
            case 76: {
               //#line 151 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 77:  doubleFieldPropertySpecs ::= {$ doubleSpecificSpecs }$
            //
            case 77: {
               //#line 151 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (doubleSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 78:  fileFieldPropertySpecs ::= $Empty
            //
            case 78: {
               //#line 152 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 79:  fileFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 79: {
               //#line 152 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fileFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 80:  fontFieldPropertySpecs ::= $Empty
            //
            case 80: {
               //#line 153 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 81:  fontFieldPropertySpecs ::= {$ fontSpecificSpecs }$
            //
            case 81: {
               //#line 153 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (fontSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 82:  intFieldPropertySpecs ::= $Empty
            //
            case 82: {
               //#line 154 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 83:  intFieldPropertySpecs ::= {$ intSpecificSpecs }$
            //
            case 83: {
               //#line 154 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                              (intSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 84:  radioFieldPropertySpecs ::= $Empty
            //
            case 84: {
               //#line 155 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 85:  radioFieldPropertySpecs ::= {$ radioSpecificSpecs }$
            //
            case 85: {
               //#line 155 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (radioSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 86:  stringFieldPropertySpecs ::= $Empty
            //
            case 86: {
               //#line 156 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 87:  stringFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 87: {
               //#line 156 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 88:  generalSpecs ::= $Empty
            //
            case 88: {
               //#line 159 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new generalSpecList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 89:  generalSpecs ::= generalSpecs generalSpec
            //
            case 89: {
               //#line 159 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((generalSpecList)getRhsSym(1)).add((IgeneralSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 90:  generalSpec ::= isEditableSpec
            //
            case 90:
                break;
            //
            // Rule 91:  generalSpec ::= isRemovableSpec
            //
            case 91:
                break;
            //
            // Rule 92:  generalSpec ::= optLabelSpec
            //
            case 92:
                break;
            //
            // Rule 93:  generalSpec ::= optToolTipSpec
            //
            case 93:
                break;
            //
            // Rule 94:  isEditableSpec ::= ISEDITABLE$ booleanValue ;$
            //
            case 94: {
               //#line 162 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new isEditableSpec(getLeftIToken(), getRightIToken(),
                                       (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 95:  isRemovableSpec ::= ISREMOVABLE$ booleanValue ;$
            //
            case 95: {
               //#line 163 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new isRemovableSpec(getLeftIToken(), getRightIToken(),
                                        (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 96:  optLabelSpec ::= LABEL$ STRING_LITERAL ;$
            //
            case 96: {
               //#line 164 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optLabelSpec(getLeftIToken(), getRightIToken(),
                                     new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 97:  optToolTipSpec ::= TOOLTIP$ STRING_LITERAL ;$
            //
            case 97: {
               //#line 165 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optToolTipSpec(getLeftIToken(), getRightIToken(),
                                       new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 98:  booleanSpecificSpecs ::= booleanSpecificSpec
            //
            case 98: {
               //#line 168 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecificSpecList((IbooleanSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 99:  booleanSpecificSpecs ::= booleanSpecificSpecs booleanSpecificSpec
            //
            case 99: {
               //#line 168 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((booleanSpecificSpecList)getRhsSym(1)).add((IbooleanSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 100:  booleanSpecificSpec ::= booleanSpecialSpec
            //
            case 100:
                break;
            //
            // Rule 101:  booleanSpecificSpec ::= booleanDefValueSpec
            //
            case 101:
                break;
            //
            // Rule 102:  booleanSpecificSpec ::= generalSpec
            //
            case 102:
                break;
            //
            // Rule 103:  booleanSpecialSpec ::= HASSPECIAL$ booleanValue ;$
            //
            case 103: {
               //#line 170 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecialSpec(getLeftIToken(), getRightIToken(),
                                           (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 104:  booleanDefValueSpec ::= DEFVALUE$ booleanValue ;$
            //
            case 104: {
               //#line 171 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanDefValueSpec(getLeftIToken(), getRightIToken(),
                                            (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 105:  colorSpecificSpecs ::= colorSpecificSpec
            //
            case 105: {
               //#line 174 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorSpecificSpecList((IcolorSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 106:  colorSpecificSpecs ::= colorSpecificSpecs colorSpecificSpec
            //
            case 106: {
               //#line 174 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((colorSpecificSpecList)getRhsSym(1)).add((IcolorSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 107:  colorSpecificSpec ::= colorDefValueSpec
            //
            case 107:
                break;
            //
            // Rule 108:  colorSpecificSpec ::= generalSpec
            //
            case 108:
                break;
            //
            // Rule 109:  colorDefValueSpec ::= DEFVALUE$ INTEGER$red ,$ INTEGER$green ,$ INTEGER$blue ;$
            //
            case 109: {
               //#line 176 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorDefValueSpec(getLeftIToken(), getRightIToken(),
                                          new ASTNodeToken(getRhsIToken(2)),
                                          new ASTNodeToken(getRhsIToken(4)),
                                          new ASTNodeToken(getRhsIToken(6)))
                );
                break;
            }
            //
            // Rule 110:  comboSpecificSpecs ::= comboSpecificSpec
            //
            case 110: {
               //#line 179 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboSpecificSpecList((IcomboSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 111:  comboSpecificSpecs ::= comboSpecificSpecs comboSpecificSpec
            //
            case 111: {
               //#line 179 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((comboSpecificSpecList)getRhsSym(1)).add((IcomboSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 112:  comboSpecificSpec ::= columnsSpec
            //
            case 112:
                break;
            //
            // Rule 113:  comboSpecificSpec ::= typeOrValuesSpec
            //
            case 113:
                break;
            //
            // Rule 114:  comboSpecificSpec ::= enumDefValueSpec
            //
            case 114:
                break;
            //
            // Rule 115:  comboSpecificSpec ::= generalSpec
            //
            case 115:
                break;
            //
            // Rule 116:  enumDefValueSpec ::= DEFVALUE$ identifier ;$
            //
            case 116: {
               //#line 181 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new enumDefValueSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 117:  doubleSpecificSpecs ::= doubleSpecificSpec
            //
            case 117: {
               //#line 184 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleSpecificSpecList((IdoubleSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 118:  doubleSpecificSpecs ::= doubleSpecificSpecs doubleSpecificSpec
            //
            case 118: {
               //#line 184 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((doubleSpecificSpecList)getRhsSym(1)).add((IdoubleSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 119:  doubleSpecificSpec ::= doubleRangeSpec
            //
            case 119:
                break;
            //
            // Rule 120:  doubleSpecificSpec ::= doubleDefValueSpec
            //
            case 120:
                break;
            //
            // Rule 121:  doubleSpecificSpec ::= generalSpec
            //
            case 121:
                break;
            //
            // Rule 122:  doubleRangeSpec ::= RANGE$ DECIMAL$low DOTS$ DECIMAL$high ;$
            //
            case 122: {
               //#line 186 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleRangeSpec(getLeftIToken(), getRightIToken(),
                                        new ASTNodeToken(getRhsIToken(2)),
                                        new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 123:  doubleDefValueSpec ::= DEFVALUE$ DECIMAL ;$
            //
            case 123: {
               //#line 187 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleDefValueSpec(getLeftIToken(), getRightIToken(),
                                           new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 124:  fontSpecificSpecs ::= fontSpecificSpec
            //
            case 124: {
               //#line 190 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontSpecificSpecList((IfontSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 125:  fontSpecificSpecs ::= fontSpecificSpecs fontSpecificSpec
            //
            case 125: {
               //#line 190 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((fontSpecificSpecList)getRhsSym(1)).add((IfontSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 126:  fontSpecificSpec ::= fontDefValueSpec
            //
            case 126:
                break;
            //
            // Rule 127:  fontSpecificSpec ::= generalSpec
            //
            case 127:
                break;
            //
            // Rule 128:  fontDefValueSpec ::= DEFVALUE$ stringValue$name INTEGER$height fontStyle$style ;$
            //
            case 128: {
               //#line 193 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontDefValueSpec(getLeftIToken(), getRightIToken(),
                                         (stringValue)getRhsSym(2),
                                         new ASTNodeToken(getRhsIToken(3)),
                                         (IfontStyle)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 129:  fontStyle ::= NORMAL
            //
            case 129: {
               //#line 194 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__NORMAL(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 130:  fontStyle ::= BOLD
            //
            case 130: {
               //#line 194 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__BOLD(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 131:  fontStyle ::= ITALIC
            //
            case 131: {
               //#line 194 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__ITALIC(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 132:  intSpecificSpecs ::= intSpecificSpec
            //
            case 132: {
               //#line 197 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecificSpecList((IintSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 133:  intSpecificSpecs ::= intSpecificSpecs intSpecificSpec
            //
            case 133: {
               //#line 197 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((intSpecificSpecList)getRhsSym(1)).add((IintSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 134:  intSpecificSpec ::= intRangeSpec
            //
            case 134:
                break;
            //
            // Rule 135:  intSpecificSpec ::= intSpecialSpec
            //
            case 135:
                break;
            //
            // Rule 136:  intSpecificSpec ::= intDefValueSpec
            //
            case 136:
                break;
            //
            // Rule 137:  intSpecificSpec ::= generalSpec
            //
            case 137:
                break;
            //
            // Rule 138:  intRangeSpec ::= RANGE$ signedNumber$low DOTS$ signedNumber$high ;$
            //
            case 138: {
               //#line 199 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intRangeSpec(getLeftIToken(), getRightIToken(),
                                     (IsignedNumber)getRhsSym(2),
                                     (IsignedNumber)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 139:  intSpecialSpec ::= HASSPECIAL$ signedNumber ;$
            //
            case 139: {
               //#line 200 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecialSpec(getLeftIToken(), getRightIToken(),
                                       (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 140:  intDefValueSpec ::= DEFVALUE$ signedNumber ;$
            //
            case 140: {
               //#line 201 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intDefValueSpec(getLeftIToken(), getRightIToken(),
                                        (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 141:  radioSpecificSpecs ::= radioSpecificSpec
            //
            case 141: {
               //#line 204 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioSpecificSpecList((IradioSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 142:  radioSpecificSpecs ::= radioSpecificSpecs radioSpecificSpec
            //
            case 142: {
               //#line 204 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((radioSpecificSpecList)getRhsSym(1)).add((IradioSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 143:  radioSpecificSpec ::= enumDefValueSpec
            //
            case 143:
                break;
            //
            // Rule 144:  radioSpecificSpec ::= columnsSpec
            //
            case 144:
                break;
            //
            // Rule 145:  radioSpecificSpec ::= typeOrValuesSpec
            //
            case 145:
                break;
            //
            // Rule 146:  radioSpecificSpec ::= generalSpec
            //
            case 146:
                break;
            //
            // Rule 147:  typeOrValuesSpec ::= TYPE$ identifier ;$
            //
            case 147: {
               //#line 207 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec__TYPE_identifier_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                    (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 148:  typeOrValuesSpec ::= valuesSpec ;$
            //
            case 148: {
               //#line 207 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec__valuesSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                               (valuesSpec)getRhsSym(1))
                );
                break;
            }
            //
            // Rule 149:  valuesSpec ::= VALUES$ {$ staticOrDynamicValues }$
            //
            case 149: {
               //#line 208 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new valuesSpec(getLeftIToken(), getRightIToken(),
                                   (IstaticOrDynamicValues)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 150:  staticOrDynamicValues ::= DYNAMIC$ stringValue$qualClassName
            //
            case 150: {
               //#line 209 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new staticOrDynamicValues(getLeftIToken(), getRightIToken(),
                                              (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 151:  staticOrDynamicValues ::= labelledStringValueList
            //
            case 151:
                break;
            //
            // Rule 152:  columnsSpec ::= COLUMNS$ INTEGER ;$
            //
            case 152: {
               //#line 210 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new columnsSpec(getLeftIToken(), getRightIToken(),
                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 153:  labelledStringValueList ::= labelledStringValue
            //
            case 153: {
               //#line 212 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValueList((labelledStringValue)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 154:  labelledStringValueList ::= labelledStringValueList ,$ labelledStringValue
            //
            case 154: {
               //#line 213 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((labelledStringValueList)getRhsSym(1)).add((labelledStringValue)getRhsSym(3));
                break;
            }
            //
            // Rule 155:  labelledStringValue ::= identifier optLabel
            //
            case 155: {
               //#line 214 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValue(getLeftIToken(), getRightIToken(),
                                            (identifier)getRhsSym(1),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 156:  optLabel ::= $Empty
            //
            case 156: {
               //#line 215 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 157:  optLabel ::= stringValue
            //
            case 157:
                break;
            //
            // Rule 158:  stringSpecificSpecs ::= stringSpecificSpec
            //
            case 158: {
               //#line 218 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringSpecificSpecList((IstringSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 159:  stringSpecificSpecs ::= stringSpecificSpecs stringSpecificSpec
            //
            case 159: {
               //#line 218 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((stringSpecificSpecList)getRhsSym(1)).add((IstringSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 160:  stringSpecificSpec ::= stringDefValueSpec
            //
            case 160:
                break;
            //
            // Rule 161:  stringSpecificSpec ::= stringValidatorSpec
            //
            case 161:
                break;
            //
            // Rule 162:  stringSpecificSpec ::= stringSpecialSpec
            //
            case 162:
                break;
            //
            // Rule 163:  stringSpecificSpec ::= stringEmptySpec
            //
            case 163:
                break;
            //
            // Rule 164:  stringSpecificSpec ::= generalSpec
            //
            case 164:
                break;
            //
            // Rule 165:  stringSpecialSpec ::= HASSPECIAL$ stringValue ;$
            //
            case 165: {
               //#line 220 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringSpecialSpec(getLeftIToken(), getRightIToken(),
                                          (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 166:  stringEmptySpec ::= EMPTYALLOWED$ FALSE ;$
            //
            case 166: {
               //#line 221 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec__EMPTYALLOWED_FALSE_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                      new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 167:  stringEmptySpec ::= EMPTYALLOWED$ TRUE stringValue ;$
            //
            case 167: {
               //#line 222 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                 new ASTNodeToken(getRhsIToken(2)),
                                                                                 (stringValue)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 168:  stringDefValueSpec ::= DEFVALUE$ stringValue ;$
            //
            case 168: {
               //#line 223 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringDefValueSpec(getLeftIToken(), getRightIToken(),
                                           (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 169:  stringValidatorSpec ::= VALIDATOR$ stringValue$qualClassName ;$
            //
            case 169: {
               //#line 224 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValidatorSpec(getLeftIToken(), getRightIToken(),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 170:  optConditionalSpec ::= $Empty
            //
            case 170: {
               //#line 227 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 171:  optConditionalSpec ::= conditionType identifier
            //
            case 171: {
               //#line 227 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optConditionalSpec(getLeftIToken(), getRightIToken(),
                                           (IconditionType)getRhsSym(1),
                                           (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 172:  conditionType ::= IF
            //
            case 172: {
               //#line 229 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType__IF(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 173:  conditionType ::= UNLESS
            //
            case 173: {
               //#line 229 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType__UNLESS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 174:  identifier ::= IDENTIFIER
            //
            case 174: {
               //#line 233 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new identifier(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 175:  booleanValue ::= TRUE
            //
            case 175: {
               //#line 235 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue__TRUE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 176:  booleanValue ::= FALSE
            //
            case 176: {
               //#line 235 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue__FALSE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 177:  stringValue ::= STRING_LITERAL
            //
            case 177: {
               //#line 237 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValue(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 178:  signedNumber ::= INTEGER
            //
            case 178: {
               //#line 239 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber__INTEGER(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 179:  signedNumber ::= sign INTEGER
            //
            case 179: {
               //#line 239 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber__sign_INTEGER(getLeftIToken(), getRightIToken(),
                                                   (Isign)getRhsSym(1),
                                                   new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 180:  sign ::= PLUS
            //
            case 180: {
               //#line 241 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign__PLUS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 181:  sign ::= MINUS
            //
            case 181: {
               //#line 241 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign__MINUS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 182:  customSpec ::= CUSTOM$ {$ customRules }$
            //
            case 182: {
               //#line 245 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new customSpec(getLeftIToken(), getRightIToken(),
                                   (IcustomRules)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 183:  customRules ::= $Empty
            //
            case 183: {
               //#line 247 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 184:  customRules ::= customRule
            //
            case 184:
                break;
            //
            // Rule 185:  customRules ::= customRules customRule
            //
            case 185: {
               //#line 249 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new customRules(getLeftIToken(), getRightIToken(),
                                    (IcustomRules)getRhsSym(1),
                                    (customRule)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 186:  tab ::= DEFAULT
            //
            case 186: {
               //#line 251 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab__DEFAULT(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 187:  tab ::= CONFIGURATION
            //
            case 187: {
               //#line 251 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab__CONFIGURATION(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 188:  tab ::= INSTANCE
            //
            case 188: {
               //#line 251 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab__INSTANCE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 189:  tab ::= PROJECT
            //
            case 189: {
               //#line 251 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab__PROJECT(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 190:  customRule ::= tab identifier {$ newPropertySpecs }$
            //
            case 190: {
               //#line 253 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new customRule(getLeftIToken(), getRightIToken(),
                                   (Itab)getRhsSym(1),
                                   (identifier)getRhsSym(2),
                                   (InewPropertySpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 191:  newPropertySpecs ::= generalSpecs
            //
            case 191:
                break;
            //
            // Rule 192:  newPropertySpecs ::= generalSpecs typeCustomSpecs
            //
            case 192: {
               //#line 256 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new newPropertySpecs(getLeftIToken(), getRightIToken(),
                                         (generalSpecList)getRhsSym(1),
                                         (ItypeCustomSpecs)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 193:  typeCustomSpecs ::= booleanSpecialSpec
            //
            case 193:
                break;
            //
            // Rule 194:  typeCustomSpecs ::= intRangeSpec intSpecialSpec
            //
            case 194: {
               //#line 259 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeCustomSpecs__intRangeSpec_intSpecialSpec(getLeftIToken(), getRightIToken(),
                                                                     (intRangeSpec)getRhsSym(1),
                                                                     (intSpecialSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 195:  typeCustomSpecs ::= stringSpecialSpec stringEmptySpec
            //
            case 195: {
               //#line 260 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeCustomSpecs__stringSpecialSpec_stringEmptySpec(getLeftIToken(), getRightIToken(),
                                                                           (stringSpecialSpec)getRhsSym(1),
                                                                           (IstringEmptySpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 196:  conditionalsSpec ::= CONDITIONALS$ {$ conditionalSpecs }$
            //
            case 196: {
               //#line 265 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalsSpec(getLeftIToken(), getRightIToken(),
                                         (IconditionalSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 197:  conditionalSpecs ::= $Empty
            //
            case 197: {
               //#line 267 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 198:  conditionalSpecs ::= conditionalSpec ;
            //
            case 198: {
               //#line 268 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs__conditionalSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                    (IconditionalSpec)getRhsSym(1),
                                                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 199:  conditionalSpecs ::= conditionalSpecs conditionalSpec ;
            //
            case 199: {
               //#line 269 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs__conditionalSpecs_conditionalSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                     (IconditionalSpecs)getRhsSym(1),
                                                                                     (IconditionalSpec)getRhsSym(2),
                                                                                     new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
            //
            // Rule 200:  conditionalSpec ::= identifier WITH identifier
            //
            case 200: {
               //#line 271 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpec__identifier_WITH_identifier(getLeftIToken(), getRightIToken(),
                                                                    (identifier)getRhsSym(1),
                                                                    new ASTNodeToken(getRhsIToken(2)),
                                                                    (identifier)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 201:  conditionalSpec ::= identifier AGAINST identifier
            //
            case 201: {
               //#line 272 "/Users/rmfuhrer/eclipse/workspaces/imp-3.5-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpec__identifier_AGAINST_identifier(getLeftIToken(), getRightIToken(),
                                                                       (identifier)getRhsSym(1),
                                                                       new ASTNodeToken(getRhsIToken(2)),
                                                                       (identifier)getRhsSym(3))
                );
                break;
            }
    
            default:
                break;
        }
        return;
    }
}

