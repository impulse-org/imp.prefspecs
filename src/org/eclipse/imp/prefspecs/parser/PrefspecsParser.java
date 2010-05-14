
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

import org.eclipse.imp.prefspecs.parser.Ast.*;
import lpg.runtime.*;
import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Hashtable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

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
   
        @Override
        public boolean visit(defaultTabSpec n) {
        inDefaultTabSpec = true;
        return true;
        }
    
        @Override
        public void endVisit(defaultTabSpec n) {
            inDefaultTabSpec = false;
        }


        //
        // Visitors for properties
        //
        @Override
        public boolean visit(isRemovableSpec n) {
            if (n.getbooleanValue() instanceof booleanValue__TRUE) {
                if (inDefaultTabSpec) {
                    emitError(n, "Field values on default tab are not removable");
                }
            }
            return true;
        }

        //
        // Visitors for field specs
        //
        @Override
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
    
        @Override
        public boolean visit(comboFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, COMBO_TYPE);
            return true;
        }
    
        @Override
        public boolean visit(dirListFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, DIRLIST_TYPE);
            return true;
        }
        
        @Override
        public boolean visit(fileFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, FILE_TYPE);
            return true;
        }
    
        @Override
        public boolean visit(intFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, INT_TYPE);
            return true;
        }
    
        @Override
        public boolean visit(radioFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, RADIO_TYPE);
            return true;
        }
    
        @Override
        public boolean visit(stringFieldSpec n) {
            String id = n.getidentifier().toString();
            if (fieldNames.contains(id)) {
                    emitError(n.getidentifier().getIToken(), "Duplicate identifier (not allowed)");
            }
            fieldNames.add(id);
            fieldTypes.put(id, STRING_TYPE);
            return true;
        }

        //
        // Visitors for conditional specs
        //

        @Override
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

        @Override
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
    } // End SymbolTableVisitor

    public void ruleAction(int ruleNumber)
    {
        switch (ruleNumber)
        {

            //
            // Rule 1:  prefSpecs ::= optPackageSpec optDetailsSpec tabsSpec topLevelItems
            //
            case 1: {
               //#line 55 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new prefSpecs(getLeftIToken(), getRightIToken(),
                                  (optPackageSpec)getRhsSym(1),
                                  (optDetailsSpec)getRhsSym(2),
                                  (tabsSpec)getRhsSym(3),
                                  (topLevelItemList)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 2:  optPackageSpec ::= $Empty
            //
            case 2: {
               //#line 57 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 3:  optPackageSpec ::= PACKAGE$ packageName ;$
            //
            case 3: {
               //#line 57 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 60 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 62 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 7:  optDetailsSpec ::= DETAILS$ onOff ;$
            //
            case 7: {
               //#line 62 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 64 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new onOff__ON(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 9:  onOff ::= OFF
            //
            case 9: {
               //#line 64 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new onOff__OFF(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 10:  topLevelItems ::= topLevelItem
            //
            case 10: {
               //#line 66 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new topLevelItemList((ItopLevelItem)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 11:  topLevelItems ::= topLevelItems topLevelItem
            //
            case 11: {
               //#line 66 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 72 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 74 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 76 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 78 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 18:  pagePath ::= pagePath identifier .$
            //
            case 18: {
               //#line 79 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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
               //#line 81 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 20:  pageBody ::= tabsSpec fieldsSpec optionalSpecs
            //
            case 20: {
               //#line 82 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new pageBody(getLeftIToken(), getRightIToken(),
                                 (tabsSpec)getRhsSym(1),
                                 (fieldsSpec)getRhsSym(2),
                                 (conditionalsSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 21:  optionalSpecs ::= conditionalsSpecOption
            //
            case 21:
                break;
            //
            // Rule 22:  conditionalsSpecOption ::= $Empty
            //
            case 22: {
               //#line 86 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 23:  conditionalsSpecOption ::= conditionalsSpec
            //
            case 23:
                break;
            //
            // Rule 24:  tabsSpec ::= $Empty
            //
            case 24: {
               //#line 91 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 25:  tabsSpec ::= TABS$ {$ tabSpecs }$
            //
            case 25: {
               //#line 91 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new tabsSpec(getLeftIToken(), getRightIToken(),
                                 (tabSpecList)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 26:  tabSpecs ::= $Empty
            //
            case 26: {
               //#line 93 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new tabSpecList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 27:  tabSpecs ::= tabSpecs tabSpec
            //
            case 27: {
               //#line 93 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((tabSpecList)getRhsSym(1)).add((ItabSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 28:  tabSpec ::= defaultTabSpec
            //
            case 28:
                break;
            //
            // Rule 29:  tabSpec ::= configurationTabSpec
            //
            case 29:
                break;
            //
            // Rule 30:  tabSpec ::= instanceTabSpec
            //
            case 30:
                break;
            //
            // Rule 31:  tabSpec ::= projectTabSpec
            //
            case 31:
                break;
            //
            // Rule 32:  defaultTabSpec ::= DEFAULT$ inout {$ }$
            //
            case 32: {
               //#line 96 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new defaultTabSpec(getLeftIToken(), getRightIToken(),
                                       (Iinout)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 33:  configurationTabSpec ::= CONFIGURATION$ inout {$ }$
            //
            case 33: {
               //#line 98 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new configurationTabSpec(getLeftIToken(), getRightIToken(),
                                             (Iinout)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 34:  instanceTabSpec ::= INSTANCE$ inout {$ }$
            //
            case 34: {
               //#line 100 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new instanceTabSpec(getLeftIToken(), getRightIToken(),
                                        (Iinout)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 35:  projectTabSpec ::= PROJECT$ inout {$ }$
            //
            case 35: {
               //#line 102 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new projectTabSpec(getLeftIToken(), getRightIToken(),
                                       (Iinout)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 36:  inout ::= IN
            //
            case 36: {
               //#line 104 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new inout__IN(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 37:  inout ::= OUT
            //
            case 37: {
               //#line 104 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new inout__OUT(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 38:  fieldsSpec ::= FIELDS$ {$ fieldSpecs }$
            //
            case 38: {
               //#line 108 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fieldsSpec(getLeftIToken(), getRightIToken(),
                                   (IfieldSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 39:  fieldSpecs ::= $Empty
            //
            case 39: {
               //#line 110 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 40:  fieldSpecs ::= fieldSpec
            //
            case 40:
                break;
            //
            // Rule 41:  fieldSpecs ::= fieldSpecs fieldSpec
            //
            case 41: {
               //#line 112 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fieldSpecs(getLeftIToken(), getRightIToken(),
                                   (IfieldSpecs)getRhsSym(1),
                                   (IfieldSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 42:  fieldSpec ::= booleanFieldSpec
            //
            case 42:
                break;
            //
            // Rule 43:  fieldSpec ::= colorFieldSpec
            //
            case 43:
                break;
            //
            // Rule 44:  fieldSpec ::= comboFieldSpec
            //
            case 44:
                break;
            //
            // Rule 45:  fieldSpec ::= directoryFieldSpec
            //
            case 45:
                break;
            //
            // Rule 46:  fieldSpec ::= dirListFieldSpec
            //
            case 46:
                break;
            //
            // Rule 47:  fieldSpec ::= doubleFieldSpec
            //
            case 47:
                break;
            //
            // Rule 48:  fieldSpec ::= fileFieldSpec
            //
            case 48:
                break;
            //
            // Rule 49:  fieldSpec ::= fontFieldSpec
            //
            case 49:
                break;
            //
            // Rule 50:  fieldSpec ::= intFieldSpec
            //
            case 50:
                break;
            //
            // Rule 51:  fieldSpec ::= radioFieldSpec
            //
            case 51:
                break;
            //
            // Rule 52:  fieldSpec ::= stringFieldSpec
            //
            case 52:
                break;
            //
            // Rule 53:  booleanFieldSpec ::= BOOLEAN$ identifier booleanFieldPropertySpecs optConditionalSpec
            //
            case 53: {
               //#line 127 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2),
                                         (booleanFieldPropertySpecs)getRhsSym(3),
                                         (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 54:  colorFieldSpec ::= COLOR$ identifier colorFieldPropertySpecs optConditionalSpec
            //
            case 54: {
               //#line 128 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (colorFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 55:  comboFieldSpec ::= COMBO$ identifier comboFieldPropertySpecs optConditionalSpec
            //
            case 55: {
               //#line 129 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (comboFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 56:  directoryFieldSpec ::= DIRECTORY$ identifier directoryFieldPropertySpecs optConditionalSpec
            //
            case 56: {
               //#line 130 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new directoryFieldSpec(getLeftIToken(), getRightIToken(),
                                           (identifier)getRhsSym(2),
                                           (directoryFieldPropertySpecs)getRhsSym(3),
                                           (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 57:  dirListFieldSpec ::= DIRLIST$ identifier dirlistFieldPropertySpecs optConditionalSpec
            //
            case 57: {
               //#line 131 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new dirListFieldSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2),
                                         (dirlistFieldPropertySpecs)getRhsSym(3),
                                         (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 58:  doubleFieldSpec ::= DOUBLE$ identifier doubleFieldPropertySpecs optConditionalSpec
            //
            case 58: {
               //#line 132 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleFieldSpec(getLeftIToken(), getRightIToken(),
                                        (identifier)getRhsSym(2),
                                        (doubleFieldPropertySpecs)getRhsSym(3),
                                        (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 59:  fileFieldSpec ::= FILE$ identifier fileFieldPropertySpecs optConditionalSpec
            //
            case 59: {
               //#line 133 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fileFieldSpec(getLeftIToken(), getRightIToken(),
                                      (identifier)getRhsSym(2),
                                      (fileFieldPropertySpecs)getRhsSym(3),
                                      (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 60:  fontFieldSpec ::= FONT$ identifier fontFieldPropertySpecs optConditionalSpec
            //
            case 60: {
               //#line 134 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontFieldSpec(getLeftIToken(), getRightIToken(),
                                      (identifier)getRhsSym(2),
                                      (fontFieldPropertySpecs)getRhsSym(3),
                                      (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 61:  intFieldSpec ::= INT$ identifier intFieldPropertySpecs optConditionalSpec
            //
            case 61: {
               //#line 135 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intFieldSpec(getLeftIToken(), getRightIToken(),
                                     (identifier)getRhsSym(2),
                                     (intFieldPropertySpecs)getRhsSym(3),
                                     (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 62:  radioFieldSpec ::= RADIO$ identifier radioFieldPropertySpecs optConditionalSpec
            //
            case 62: {
               //#line 136 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (radioFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 63:  stringFieldSpec ::= STRING$ identifier stringFieldPropertySpecs optConditionalSpec
            //
            case 63: {
               //#line 137 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringFieldSpec(getLeftIToken(), getRightIToken(),
                                        (identifier)getRhsSym(2),
                                        (stringFieldPropertySpecs)getRhsSym(3),
                                        (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 64:  booleanFieldPropertySpecs ::= $Empty
            //
            case 64: {
               //#line 140 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 65:  booleanFieldPropertySpecs ::= {$ booleanSpecificSpecs }$
            //
            case 65: {
               //#line 140 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (booleanSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 66:  colorFieldPropertySpecs ::= $Empty
            //
            case 66: {
               //#line 141 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 67:  colorFieldPropertySpecs ::= {$ colorSpecificSpecs }$
            //
            case 67: {
               //#line 141 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (colorSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 68:  comboFieldPropertySpecs ::= $Empty
            //
            case 68: {
               //#line 142 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 69:  comboFieldPropertySpecs ::= {$ comboSpecificSpecs }$
            //
            case 69: {
               //#line 142 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (comboSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 70:  directoryFieldPropertySpecs ::= $Empty
            //
            case 70: {
               //#line 143 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 71:  directoryFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 71: {
               //#line 143 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new directoryFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                    (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 72:  dirlistFieldPropertySpecs ::= $Empty
            //
            case 72: {
               //#line 144 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 73:  dirlistFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 73: {
               //#line 144 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new dirlistFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 74:  doubleFieldPropertySpecs ::= $Empty
            //
            case 74: {
               //#line 145 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 75:  doubleFieldPropertySpecs ::= {$ doubleSpecificSpecs }$
            //
            case 75: {
               //#line 145 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (doubleSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 76:  fileFieldPropertySpecs ::= $Empty
            //
            case 76: {
               //#line 146 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 77:  fileFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 77: {
               //#line 146 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fileFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 78:  fontFieldPropertySpecs ::= $Empty
            //
            case 78: {
               //#line 147 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 79:  fontFieldPropertySpecs ::= {$ fontSpecificSpecs }$
            //
            case 79: {
               //#line 147 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (fontSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 80:  intFieldPropertySpecs ::= $Empty
            //
            case 80: {
               //#line 148 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 81:  intFieldPropertySpecs ::= {$ intSpecificSpecs }$
            //
            case 81: {
               //#line 148 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                              (intSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 82:  radioFieldPropertySpecs ::= $Empty
            //
            case 82: {
               //#line 149 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 83:  radioFieldPropertySpecs ::= {$ radioSpecificSpecs }$
            //
            case 83: {
               //#line 149 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (radioSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 84:  stringFieldPropertySpecs ::= $Empty
            //
            case 84: {
               //#line 150 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 85:  stringFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 85: {
               //#line 150 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 86:  generalSpec ::= isEditableSpec
            //
            case 86:
                break;
            //
            // Rule 87:  generalSpec ::= isRemovableSpec
            //
            case 87:
                break;
            //
            // Rule 88:  generalSpec ::= optLabelSpec
            //
            case 88:
                break;
            //
            // Rule 89:  generalSpec ::= optToolTipSpec
            //
            case 89:
                break;
            //
            // Rule 90:  isEditableSpec ::= ISEDITABLE$ booleanValue ;$
            //
            case 90: {
               //#line 155 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new isEditableSpec(getLeftIToken(), getRightIToken(),
                                       (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 91:  isRemovableSpec ::= ISREMOVABLE$ booleanValue ;$
            //
            case 91: {
               //#line 156 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new isRemovableSpec(getLeftIToken(), getRightIToken(),
                                        (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 92:  optLabelSpec ::= LABEL$ STRING_LITERAL ;$
            //
            case 92: {
               //#line 157 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new optLabelSpec(getLeftIToken(), getRightIToken(),
                                     new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 93:  optToolTipSpec ::= TOOLTIP$ STRING_LITERAL ;$
            //
            case 93: {
               //#line 158 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new optToolTipSpec(getLeftIToken(), getRightIToken(),
                                       new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 94:  booleanSpecificSpecs ::= booleanSpecificSpec
            //
            case 94: {
               //#line 161 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecificSpecList((IbooleanSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 95:  booleanSpecificSpecs ::= booleanSpecificSpecs booleanSpecificSpec
            //
            case 95: {
               //#line 161 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((booleanSpecificSpecList)getRhsSym(1)).add((IbooleanSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 96:  booleanSpecificSpec ::= booleanSpecialSpec
            //
            case 96:
                break;
            //
            // Rule 97:  booleanSpecificSpec ::= booleanDefValueSpec
            //
            case 97:
                break;
            //
            // Rule 98:  booleanSpecificSpec ::= generalSpec
            //
            case 98:
                break;
            //
            // Rule 99:  booleanSpecialSpec ::= HASSPECIAL$ booleanValue ;$
            //
            case 99: {
               //#line 163 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecialSpec(getLeftIToken(), getRightIToken(),
                                           (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 100:  booleanDefValueSpec ::= DEFVALUE$ booleanValue ;$
            //
            case 100: {
               //#line 164 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanDefValueSpec(getLeftIToken(), getRightIToken(),
                                            (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 101:  colorSpecificSpecs ::= colorSpecificSpec
            //
            case 101: {
               //#line 167 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorSpecificSpecList((IcolorSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 102:  colorSpecificSpecs ::= colorSpecificSpecs colorSpecificSpec
            //
            case 102: {
               //#line 167 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((colorSpecificSpecList)getRhsSym(1)).add((IcolorSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 103:  colorSpecificSpec ::= colorDefValueSpec
            //
            case 103:
                break;
            //
            // Rule 104:  colorSpecificSpec ::= generalSpec
            //
            case 104:
                break;
            //
            // Rule 105:  colorDefValueSpec ::= DEFVALUE$ INTEGER$red ,$ INTEGER$green ,$ INTEGER$blue ;$
            //
            case 105: {
               //#line 169 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorDefValueSpec(getLeftIToken(), getRightIToken(),
                                          new ASTNodeToken(getRhsIToken(2)),
                                          new ASTNodeToken(getRhsIToken(4)),
                                          new ASTNodeToken(getRhsIToken(6)))
                );
                break;
            }
            //
            // Rule 106:  comboSpecificSpecs ::= comboSpecificSpec
            //
            case 106: {
               //#line 172 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboSpecificSpecList((IcomboSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 107:  comboSpecificSpecs ::= comboSpecificSpecs comboSpecificSpec
            //
            case 107: {
               //#line 172 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((comboSpecificSpecList)getRhsSym(1)).add((IcomboSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 108:  comboSpecificSpec ::= columnsSpec
            //
            case 108:
                break;
            //
            // Rule 109:  comboSpecificSpec ::= typeOrValuesSpec
            //
            case 109:
                break;
            //
            // Rule 110:  comboSpecificSpec ::= enumDefValueSpec
            //
            case 110:
                break;
            //
            // Rule 111:  comboSpecificSpec ::= generalSpec
            //
            case 111:
                break;
            //
            // Rule 112:  enumDefValueSpec ::= DEFVALUE$ identifier ;$
            //
            case 112: {
               //#line 174 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new enumDefValueSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 113:  doubleSpecificSpecs ::= doubleSpecificSpec
            //
            case 113: {
               //#line 177 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleSpecificSpecList((IdoubleSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 114:  doubleSpecificSpecs ::= doubleSpecificSpecs doubleSpecificSpec
            //
            case 114: {
               //#line 177 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((doubleSpecificSpecList)getRhsSym(1)).add((IdoubleSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 115:  doubleSpecificSpec ::= doubleRangeSpec
            //
            case 115:
                break;
            //
            // Rule 116:  doubleSpecificSpec ::= doubleDefValueSpec
            //
            case 116:
                break;
            //
            // Rule 117:  doubleSpecificSpec ::= generalSpec
            //
            case 117:
                break;
            //
            // Rule 118:  doubleRangeSpec ::= RANGE$ DECIMAL$low DOTS$ DECIMAL$high ;$
            //
            case 118: {
               //#line 179 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleRangeSpec(getLeftIToken(), getRightIToken(),
                                        new ASTNodeToken(getRhsIToken(2)),
                                        new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 119:  doubleDefValueSpec ::= DEFVALUE$ DECIMAL ;$
            //
            case 119: {
               //#line 180 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleDefValueSpec(getLeftIToken(), getRightIToken(),
                                           new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 120:  fontSpecificSpecs ::= fontSpecificSpec
            //
            case 120: {
               //#line 183 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontSpecificSpecList((IfontSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 121:  fontSpecificSpecs ::= fontSpecificSpecs fontSpecificSpec
            //
            case 121: {
               //#line 183 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((fontSpecificSpecList)getRhsSym(1)).add((IfontSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 122:  fontSpecificSpec ::= fontDefValueSpec
            //
            case 122:
                break;
            //
            // Rule 123:  fontSpecificSpec ::= generalSpec
            //
            case 123:
                break;
            //
            // Rule 124:  fontDefValueSpec ::= DEFVALUE$ stringValue$name INTEGER$height fontStyle$style ;$
            //
            case 124: {
               //#line 186 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontDefValueSpec(getLeftIToken(), getRightIToken(),
                                         (stringValue)getRhsSym(2),
                                         new ASTNodeToken(getRhsIToken(3)),
                                         (IfontStyle)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 125:  fontStyle ::= NORMAL
            //
            case 125: {
               //#line 187 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__NORMAL(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 126:  fontStyle ::= BOLD
            //
            case 126: {
               //#line 187 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__BOLD(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 127:  fontStyle ::= ITALIC
            //
            case 127: {
               //#line 187 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__ITALIC(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 128:  intSpecificSpecs ::= intSpecificSpec
            //
            case 128: {
               //#line 190 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecificSpecList((IintSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 129:  intSpecificSpecs ::= intSpecificSpecs intSpecificSpec
            //
            case 129: {
               //#line 190 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((intSpecificSpecList)getRhsSym(1)).add((IintSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 130:  intSpecificSpec ::= intRangeSpec
            //
            case 130:
                break;
            //
            // Rule 131:  intSpecificSpec ::= intSpecialSpec
            //
            case 131:
                break;
            //
            // Rule 132:  intSpecificSpec ::= intDefValueSpec
            //
            case 132:
                break;
            //
            // Rule 133:  intSpecificSpec ::= generalSpec
            //
            case 133:
                break;
            //
            // Rule 134:  intRangeSpec ::= RANGE$ signedNumber$low DOTS$ signedNumber$high ;$
            //
            case 134: {
               //#line 192 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intRangeSpec(getLeftIToken(), getRightIToken(),
                                     (IsignedNumber)getRhsSym(2),
                                     (IsignedNumber)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 135:  intSpecialSpec ::= HASSPECIAL$ signedNumber ;$
            //
            case 135: {
               //#line 193 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecialSpec(getLeftIToken(), getRightIToken(),
                                       (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 136:  intDefValueSpec ::= DEFVALUE$ signedNumber ;$
            //
            case 136: {
               //#line 194 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intDefValueSpec(getLeftIToken(), getRightIToken(),
                                        (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 137:  radioSpecificSpecs ::= radioSpecificSpec
            //
            case 137: {
               //#line 197 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioSpecificSpecList((IradioSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 138:  radioSpecificSpecs ::= radioSpecificSpecs radioSpecificSpec
            //
            case 138: {
               //#line 197 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((radioSpecificSpecList)getRhsSym(1)).add((IradioSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 139:  radioSpecificSpec ::= enumDefValueSpec
            //
            case 139:
                break;
            //
            // Rule 140:  radioSpecificSpec ::= columnsSpec
            //
            case 140:
                break;
            //
            // Rule 141:  radioSpecificSpec ::= typeOrValuesSpec
            //
            case 141:
                break;
            //
            // Rule 142:  radioSpecificSpec ::= generalSpec
            //
            case 142:
                break;
            //
            // Rule 143:  typeOrValuesSpec ::= TYPE$ identifier ;$
            //
            case 143: {
               //#line 200 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec__TYPE_identifier_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                    (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 144:  typeOrValuesSpec ::= valuesSpec ;$
            //
            case 144: {
               //#line 200 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec__valuesSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                               (valuesSpec)getRhsSym(1))
                );
                break;
            }
            //
            // Rule 145:  valuesSpec ::= VALUES$ {$ staticOrDynamicValues }$
            //
            case 145: {
               //#line 201 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new valuesSpec(getLeftIToken(), getRightIToken(),
                                   (IstaticOrDynamicValues)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 146:  staticOrDynamicValues ::= DYNAMIC$ stringValue$qualClassName
            //
            case 146: {
               //#line 202 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new staticOrDynamicValues(getLeftIToken(), getRightIToken(),
                                              (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 147:  staticOrDynamicValues ::= labelledStringValueList
            //
            case 147:
                break;
            //
            // Rule 148:  columnsSpec ::= COLUMNS$ INTEGER ;$
            //
            case 148: {
               //#line 203 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new columnsSpec(getLeftIToken(), getRightIToken(),
                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 149:  labelledStringValueList ::= labelledStringValue
            //
            case 149: {
               //#line 205 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValueList((labelledStringValue)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 150:  labelledStringValueList ::= labelledStringValueList ,$ labelledStringValue
            //
            case 150: {
               //#line 206 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((labelledStringValueList)getRhsSym(1)).add((labelledStringValue)getRhsSym(3));
                break;
            }
            //
            // Rule 151:  labelledStringValue ::= identifier optLabel
            //
            case 151: {
               //#line 207 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValue(getLeftIToken(), getRightIToken(),
                                            (identifier)getRhsSym(1),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 152:  optLabel ::= $Empty
            //
            case 152: {
               //#line 208 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 153:  optLabel ::= stringValue
            //
            case 153:
                break;
            //
            // Rule 154:  stringSpecificSpecs ::= stringSpecificSpec
            //
            case 154: {
               //#line 211 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringSpecificSpecList((IstringSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 155:  stringSpecificSpecs ::= stringSpecificSpecs stringSpecificSpec
            //
            case 155: {
               //#line 211 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((stringSpecificSpecList)getRhsSym(1)).add((IstringSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 156:  stringSpecificSpec ::= stringDefValueSpec
            //
            case 156:
                break;
            //
            // Rule 157:  stringSpecificSpec ::= stringValidatorSpec
            //
            case 157:
                break;
            //
            // Rule 158:  stringSpecificSpec ::= stringEmptySpec
            //
            case 158:
                break;
            //
            // Rule 159:  stringSpecificSpec ::= generalSpec
            //
            case 159:
                break;
            //
            // Rule 160:  stringEmptySpec ::= EMPTYALLOWED$ FALSE ;$
            //
            case 160: {
               //#line 213 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec__EMPTYALLOWED_FALSE_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                      new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 161:  stringEmptySpec ::= EMPTYALLOWED$ TRUE stringValue ;$
            //
            case 161: {
               //#line 214 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                 new ASTNodeToken(getRhsIToken(2)),
                                                                                 (stringValue)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 162:  stringDefValueSpec ::= DEFVALUE$ stringValue ;$
            //
            case 162: {
               //#line 215 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringDefValueSpec(getLeftIToken(), getRightIToken(),
                                           (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 163:  stringValidatorSpec ::= VALIDATOR$ stringValue$qualClassName ;$
            //
            case 163: {
               //#line 216 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValidatorSpec(getLeftIToken(), getRightIToken(),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 164:  optConditionalSpec ::= $Empty
            //
            case 164: {
               //#line 219 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 165:  optConditionalSpec ::= conditionType identifier
            //
            case 165: {
               //#line 219 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new optConditionalSpec(getLeftIToken(), getRightIToken(),
                                           (IconditionType)getRhsSym(1),
                                           (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 166:  conditionType ::= IF
            //
            case 166: {
               //#line 221 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType__IF(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 167:  conditionType ::= UNLESS
            //
            case 167: {
               //#line 221 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType__UNLESS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 168:  identifier ::= IDENTIFIER
            //
            case 168: {
               //#line 225 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new identifier(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 169:  booleanValue ::= TRUE
            //
            case 169: {
               //#line 227 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue__TRUE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 170:  booleanValue ::= FALSE
            //
            case 170: {
               //#line 227 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue__FALSE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 171:  stringValue ::= STRING_LITERAL
            //
            case 171: {
               //#line 229 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValue(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 172:  signedNumber ::= INTEGER
            //
            case 172: {
               //#line 231 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber__INTEGER(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 173:  signedNumber ::= sign INTEGER
            //
            case 173: {
               //#line 231 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber__sign_INTEGER(getLeftIToken(), getRightIToken(),
                                                   (Isign)getRhsSym(1),
                                                   new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 174:  sign ::= PLUS
            //
            case 174: {
               //#line 233 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign__PLUS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 175:  sign ::= MINUS
            //
            case 175: {
               //#line 233 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign__MINUS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 176:  conditionalsSpec ::= CONDITIONALS$ {$ conditionalSpecs }$
            //
            case 176: {
               //#line 237 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalsSpec(getLeftIToken(), getRightIToken(),
                                         (IconditionalSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 177:  conditionalSpecs ::= $Empty
            //
            case 177: {
               //#line 239 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 178:  conditionalSpecs ::= conditionalSpec ;
            //
            case 178: {
               //#line 240 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs__conditionalSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                    (IconditionalSpec)getRhsSym(1),
                                                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 179:  conditionalSpecs ::= conditionalSpecs conditionalSpec ;
            //
            case 179: {
               //#line 241 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs__conditionalSpecs_conditionalSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                     (IconditionalSpecs)getRhsSym(1),
                                                                                     (IconditionalSpec)getRhsSym(2),
                                                                                     new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
            //
            // Rule 180:  conditionalSpec ::= identifier WITH identifier
            //
            case 180: {
               //#line 243 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpec__identifier_WITH_identifier(getLeftIToken(), getRightIToken(),
                                                                    (identifier)getRhsSym(1),
                                                                    new ASTNodeToken(getRhsIToken(2)),
                                                                    (identifier)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 181:  conditionalSpec ::= identifier AGAINST identifier
            //
            case 181: {
               //#line 244 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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

