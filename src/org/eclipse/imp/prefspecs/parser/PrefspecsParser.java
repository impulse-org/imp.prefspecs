
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
            // Rule 39:  fieldSpecs ::= fieldSpec
            //
            case 39:
                break;
            //
            // Rule 40:  fieldSpecs ::= fieldSpecs fieldSpec
            //
            case 40: {
               //#line 111 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fieldSpecs(getLeftIToken(), getRightIToken(),
                                   (IfieldSpecs)getRhsSym(1),
                                   (IfieldSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 41:  fieldSpec ::= booleanFieldSpec
            //
            case 41:
                break;
            //
            // Rule 42:  fieldSpec ::= colorFieldSpec
            //
            case 42:
                break;
            //
            // Rule 43:  fieldSpec ::= comboFieldSpec
            //
            case 43:
                break;
            //
            // Rule 44:  fieldSpec ::= directoryFieldSpec
            //
            case 44:
                break;
            //
            // Rule 45:  fieldSpec ::= dirListFieldSpec
            //
            case 45:
                break;
            //
            // Rule 46:  fieldSpec ::= doubleFieldSpec
            //
            case 46:
                break;
            //
            // Rule 47:  fieldSpec ::= fileFieldSpec
            //
            case 47:
                break;
            //
            // Rule 48:  fieldSpec ::= fontFieldSpec
            //
            case 48:
                break;
            //
            // Rule 49:  fieldSpec ::= intFieldSpec
            //
            case 49:
                break;
            //
            // Rule 50:  fieldSpec ::= radioFieldSpec
            //
            case 50:
                break;
            //
            // Rule 51:  fieldSpec ::= stringFieldSpec
            //
            case 51:
                break;
            //
            // Rule 52:  fieldSpec ::= groupSpec
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
            // Rule 64:  groupSpec ::= GROUP$ STRING_LITERAL {$ fieldSpecs }$
            //
            case 64: {
               //#line 139 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new groupSpec(getLeftIToken(), getRightIToken(),
                                  new ASTNodeToken(getRhsIToken(2)),
                                  (IfieldSpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 65:  booleanFieldPropertySpecs ::= $Empty
            //
            case 65: {
               //#line 141 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 66:  booleanFieldPropertySpecs ::= {$ booleanSpecificSpecs }$
            //
            case 66: {
               //#line 141 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (booleanSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 67:  colorFieldPropertySpecs ::= $Empty
            //
            case 67: {
               //#line 142 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 68:  colorFieldPropertySpecs ::= {$ colorSpecificSpecs }$
            //
            case 68: {
               //#line 142 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (colorSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 69:  comboFieldPropertySpecs ::= $Empty
            //
            case 69: {
               //#line 143 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 70:  comboFieldPropertySpecs ::= {$ comboSpecificSpecs }$
            //
            case 70: {
               //#line 143 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (comboSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 71:  directoryFieldPropertySpecs ::= $Empty
            //
            case 71: {
               //#line 144 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 72:  directoryFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 72: {
               //#line 144 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new directoryFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                    (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 73:  dirlistFieldPropertySpecs ::= $Empty
            //
            case 73: {
               //#line 145 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 74:  dirlistFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 74: {
               //#line 145 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new dirlistFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 75:  doubleFieldPropertySpecs ::= $Empty
            //
            case 75: {
               //#line 146 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 76:  doubleFieldPropertySpecs ::= {$ doubleSpecificSpecs }$
            //
            case 76: {
               //#line 146 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (doubleSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 77:  fileFieldPropertySpecs ::= $Empty
            //
            case 77: {
               //#line 147 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 78:  fileFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 78: {
               //#line 147 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fileFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 79:  fontFieldPropertySpecs ::= $Empty
            //
            case 79: {
               //#line 148 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 80:  fontFieldPropertySpecs ::= {$ fontSpecificSpecs }$
            //
            case 80: {
               //#line 148 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (fontSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 81:  intFieldPropertySpecs ::= $Empty
            //
            case 81: {
               //#line 149 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 82:  intFieldPropertySpecs ::= {$ intSpecificSpecs }$
            //
            case 82: {
               //#line 149 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                              (intSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 83:  radioFieldPropertySpecs ::= $Empty
            //
            case 83: {
               //#line 150 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 84:  radioFieldPropertySpecs ::= {$ radioSpecificSpecs }$
            //
            case 84: {
               //#line 150 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (radioSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 85:  stringFieldPropertySpecs ::= $Empty
            //
            case 85: {
               //#line 151 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 86:  stringFieldPropertySpecs ::= {$ stringSpecificSpecs }$
            //
            case 86: {
               //#line 151 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (stringSpecificSpecList)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 87:  generalSpec ::= isEditableSpec
            //
            case 87:
                break;
            //
            // Rule 88:  generalSpec ::= isRemovableSpec
            //
            case 88:
                break;
            //
            // Rule 89:  generalSpec ::= optLabelSpec
            //
            case 89:
                break;
            //
            // Rule 90:  generalSpec ::= optToolTipSpec
            //
            case 90:
                break;
            //
            // Rule 91:  isEditableSpec ::= ISEDITABLE$ booleanValue ;$
            //
            case 91: {
               //#line 156 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new isEditableSpec(getLeftIToken(), getRightIToken(),
                                       (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 92:  isRemovableSpec ::= ISREMOVABLE$ booleanValue ;$
            //
            case 92: {
               //#line 157 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new isRemovableSpec(getLeftIToken(), getRightIToken(),
                                        (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 93:  optLabelSpec ::= LABEL$ STRING_LITERAL ;$
            //
            case 93: {
               //#line 158 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new optLabelSpec(getLeftIToken(), getRightIToken(),
                                     new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 94:  optToolTipSpec ::= TOOLTIP$ STRING_LITERAL ;$
            //
            case 94: {
               //#line 159 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new optToolTipSpec(getLeftIToken(), getRightIToken(),
                                       new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 95:  booleanSpecificSpecs ::= booleanSpecificSpec
            //
            case 95: {
               //#line 162 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecificSpecList((IbooleanSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 96:  booleanSpecificSpecs ::= booleanSpecificSpecs booleanSpecificSpec
            //
            case 96: {
               //#line 162 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((booleanSpecificSpecList)getRhsSym(1)).add((IbooleanSpecificSpec)getRhsSym(2));
                break;
            }
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
            // Rule 99:  booleanDefValueSpec ::= DEFVALUE$ booleanValue ;$
            //
            case 99: {
               //#line 164 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanDefValueSpec(getLeftIToken(), getRightIToken(),
                                            (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 100:  colorSpecificSpecs ::= colorSpecificSpec
            //
            case 100: {
               //#line 167 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorSpecificSpecList((IcolorSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 101:  colorSpecificSpecs ::= colorSpecificSpecs colorSpecificSpec
            //
            case 101: {
               //#line 167 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((colorSpecificSpecList)getRhsSym(1)).add((IcolorSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 102:  colorSpecificSpec ::= colorDefValueSpec
            //
            case 102:
                break;
            //
            // Rule 103:  colorSpecificSpec ::= generalSpec
            //
            case 103:
                break;
            //
            // Rule 104:  colorDefValueSpec ::= DEFVALUE$ INTEGER$red ,$ INTEGER$green ,$ INTEGER$blue ;$
            //
            case 104: {
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
            // Rule 105:  comboSpecificSpecs ::= comboSpecificSpec
            //
            case 105: {
               //#line 172 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboSpecificSpecList((IcomboSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 106:  comboSpecificSpecs ::= comboSpecificSpecs comboSpecificSpec
            //
            case 106: {
               //#line 172 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((comboSpecificSpecList)getRhsSym(1)).add((IcomboSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 107:  comboSpecificSpec ::= columnsSpec
            //
            case 107:
                break;
            //
            // Rule 108:  comboSpecificSpec ::= typeOrValuesSpec
            //
            case 108:
                break;
            //
            // Rule 109:  comboSpecificSpec ::= enumDefValueSpec
            //
            case 109:
                break;
            //
            // Rule 110:  comboSpecificSpec ::= generalSpec
            //
            case 110:
                break;
            //
            // Rule 111:  enumDefValueSpec ::= DEFVALUE$ identifier ;$
            //
            case 111: {
               //#line 174 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new enumDefValueSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 112:  doubleSpecificSpecs ::= doubleSpecificSpec
            //
            case 112: {
               //#line 177 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleSpecificSpecList((IdoubleSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 113:  doubleSpecificSpecs ::= doubleSpecificSpecs doubleSpecificSpec
            //
            case 113: {
               //#line 177 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((doubleSpecificSpecList)getRhsSym(1)).add((IdoubleSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 114:  doubleSpecificSpec ::= doubleRangeSpec
            //
            case 114:
                break;
            //
            // Rule 115:  doubleSpecificSpec ::= doubleDefValueSpec
            //
            case 115:
                break;
            //
            // Rule 116:  doubleSpecificSpec ::= generalSpec
            //
            case 116:
                break;
            //
            // Rule 117:  doubleRangeSpec ::= RANGE$ DECIMAL$low DOTS$ DECIMAL$high ;$
            //
            case 117: {
               //#line 179 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleRangeSpec(getLeftIToken(), getRightIToken(),
                                        new ASTNodeToken(getRhsIToken(2)),
                                        new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 118:  doubleDefValueSpec ::= DEFVALUE$ DECIMAL ;$
            //
            case 118: {
               //#line 180 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleDefValueSpec(getLeftIToken(), getRightIToken(),
                                           new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 119:  fontSpecificSpecs ::= fontSpecificSpec
            //
            case 119: {
               //#line 183 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontSpecificSpecList((IfontSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 120:  fontSpecificSpecs ::= fontSpecificSpecs fontSpecificSpec
            //
            case 120: {
               //#line 183 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((fontSpecificSpecList)getRhsSym(1)).add((IfontSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 121:  fontSpecificSpec ::= fontDefValueSpec
            //
            case 121:
                break;
            //
            // Rule 122:  fontSpecificSpec ::= generalSpec
            //
            case 122:
                break;
            //
            // Rule 123:  fontDefValueSpec ::= DEFVALUE$ stringValue$name INTEGER$height fontStyle$style ;$
            //
            case 123: {
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
            // Rule 124:  fontStyle ::= NORMAL
            //
            case 124: {
               //#line 187 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__NORMAL(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 125:  fontStyle ::= BOLD
            //
            case 125: {
               //#line 187 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__BOLD(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 126:  fontStyle ::= ITALIC
            //
            case 126: {
               //#line 187 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle__ITALIC(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 127:  intSpecificSpecs ::= intSpecificSpec
            //
            case 127: {
               //#line 190 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecificSpecList((IintSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 128:  intSpecificSpecs ::= intSpecificSpecs intSpecificSpec
            //
            case 128: {
               //#line 190 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((intSpecificSpecList)getRhsSym(1)).add((IintSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 129:  intSpecificSpec ::= intRangeSpec
            //
            case 129:
                break;
            //
            // Rule 130:  intSpecificSpec ::= intDefValueSpec
            //
            case 130:
                break;
            //
            // Rule 131:  intSpecificSpec ::= generalSpec
            //
            case 131:
                break;
            //
            // Rule 132:  intRangeSpec ::= RANGE$ signedNumber$low DOTS$ signedNumber$high ;$
            //
            case 132: {
               //#line 192 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intRangeSpec(getLeftIToken(), getRightIToken(),
                                     (IsignedNumber)getRhsSym(2),
                                     (IsignedNumber)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 133:  intDefValueSpec ::= DEFVALUE$ signedNumber ;$
            //
            case 133: {
               //#line 193 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new intDefValueSpec(getLeftIToken(), getRightIToken(),
                                        (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 134:  radioSpecificSpecs ::= radioSpecificSpec
            //
            case 134: {
               //#line 196 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioSpecificSpecList((IradioSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 135:  radioSpecificSpecs ::= radioSpecificSpecs radioSpecificSpec
            //
            case 135: {
               //#line 196 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((radioSpecificSpecList)getRhsSym(1)).add((IradioSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 136:  radioSpecificSpec ::= enumDefValueSpec
            //
            case 136:
                break;
            //
            // Rule 137:  radioSpecificSpec ::= columnsSpec
            //
            case 137:
                break;
            //
            // Rule 138:  radioSpecificSpec ::= typeOrValuesSpec
            //
            case 138:
                break;
            //
            // Rule 139:  radioSpecificSpec ::= generalSpec
            //
            case 139:
                break;
            //
            // Rule 140:  typeOrValuesSpec ::= TYPE$ identifier ;$
            //
            case 140: {
               //#line 199 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec__TYPE_identifier_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                    (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 141:  typeOrValuesSpec ::= valuesSpec ;$
            //
            case 141: {
               //#line 199 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec__valuesSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                               (valuesSpec)getRhsSym(1))
                );
                break;
            }
            //
            // Rule 142:  valuesSpec ::= VALUES$ {$ staticOrDynamicValues }$
            //
            case 142: {
               //#line 200 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new valuesSpec(getLeftIToken(), getRightIToken(),
                                   (IstaticOrDynamicValues)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 143:  staticOrDynamicValues ::= DYNAMIC$ stringValue$qualClassName
            //
            case 143: {
               //#line 201 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new staticOrDynamicValues(getLeftIToken(), getRightIToken(),
                                              (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 144:  staticOrDynamicValues ::= labelledStringValueList
            //
            case 144:
                break;
            //
            // Rule 145:  columnsSpec ::= COLUMNS$ INTEGER ;$
            //
            case 145: {
               //#line 202 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new columnsSpec(getLeftIToken(), getRightIToken(),
                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 146:  labelledStringValueList ::= labelledStringValue
            //
            case 146: {
               //#line 204 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValueList((labelledStringValue)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 147:  labelledStringValueList ::= labelledStringValueList ,$ labelledStringValue
            //
            case 147: {
               //#line 205 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((labelledStringValueList)getRhsSym(1)).add((labelledStringValue)getRhsSym(3));
                break;
            }
            //
            // Rule 148:  labelledStringValue ::= identifier optLabel
            //
            case 148: {
               //#line 206 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValue(getLeftIToken(), getRightIToken(),
                                            (identifier)getRhsSym(1),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 149:  optLabel ::= $Empty
            //
            case 149: {
               //#line 207 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 150:  optLabel ::= stringValue
            //
            case 150:
                break;
            //
            // Rule 151:  stringSpecificSpecs ::= stringSpecificSpec
            //
            case 151: {
               //#line 210 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringSpecificSpecList((IstringSpecificSpec)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 152:  stringSpecificSpecs ::= stringSpecificSpecs stringSpecificSpec
            //
            case 152: {
               //#line 210 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                ((stringSpecificSpecList)getRhsSym(1)).add((IstringSpecificSpec)getRhsSym(2));
                break;
            }
            //
            // Rule 153:  stringSpecificSpec ::= stringDefValueSpec
            //
            case 153:
                break;
            //
            // Rule 154:  stringSpecificSpec ::= stringValidatorSpec
            //
            case 154:
                break;
            //
            // Rule 155:  stringSpecificSpec ::= stringEmptySpec
            //
            case 155:
                break;
            //
            // Rule 156:  stringSpecificSpec ::= generalSpec
            //
            case 156:
                break;
            //
            // Rule 157:  stringEmptySpec ::= EMPTYALLOWED$ FALSE ;$
            //
            case 157: {
               //#line 212 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec__EMPTYALLOWED_FALSE_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                      new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 158:  stringEmptySpec ::= EMPTYALLOWED$ TRUE stringValue ;$
            //
            case 158: {
               //#line 213 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec__EMPTYALLOWED_TRUE_stringValue_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                 new ASTNodeToken(getRhsIToken(2)),
                                                                                 (stringValue)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 159:  stringDefValueSpec ::= DEFVALUE$ stringValue ;$
            //
            case 159: {
               //#line 214 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringDefValueSpec(getLeftIToken(), getRightIToken(),
                                           (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 160:  stringValidatorSpec ::= VALIDATOR$ stringValue$qualClassName ;$
            //
            case 160: {
               //#line 215 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValidatorSpec(getLeftIToken(), getRightIToken(),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 161:  optConditionalSpec ::= $Empty
            //
            case 161: {
               //#line 218 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 162:  optConditionalSpec ::= conditionType identifier
            //
            case 162: {
               //#line 218 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new optConditionalSpec(getLeftIToken(), getRightIToken(),
                                           (IconditionType)getRhsSym(1),
                                           (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 163:  conditionType ::= IF
            //
            case 163: {
               //#line 220 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType__IF(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 164:  conditionType ::= UNLESS
            //
            case 164: {
               //#line 220 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType__UNLESS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 165:  identifier ::= IDENTIFIER
            //
            case 165: {
               //#line 224 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new identifier(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 166:  booleanValue ::= TRUE
            //
            case 166: {
               //#line 226 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue__TRUE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 167:  booleanValue ::= FALSE
            //
            case 167: {
               //#line 226 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue__FALSE(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 168:  stringValue ::= STRING_LITERAL
            //
            case 168: {
               //#line 228 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValue(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 169:  signedNumber ::= INTEGER
            //
            case 169: {
               //#line 230 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber__INTEGER(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 170:  signedNumber ::= sign INTEGER
            //
            case 170: {
               //#line 230 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber__sign_INTEGER(getLeftIToken(), getRightIToken(),
                                                   (Isign)getRhsSym(1),
                                                   new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 171:  sign ::= PLUS
            //
            case 171: {
               //#line 232 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign__PLUS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 172:  sign ::= MINUS
            //
            case 172: {
               //#line 232 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign__MINUS(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 173:  conditionalsSpec ::= CONDITIONALS$ {$ conditionalSpecs }$
            //
            case 173: {
               //#line 236 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalsSpec(getLeftIToken(), getRightIToken(),
                                         (IconditionalSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 174:  conditionalSpecs ::= $Empty
            //
            case 174: {
               //#line 238 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 175:  conditionalSpecs ::= conditionalSpec ;
            //
            case 175: {
               //#line 239 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs__conditionalSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                    (IconditionalSpec)getRhsSym(1),
                                                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 176:  conditionalSpecs ::= conditionalSpecs conditionalSpec ;
            //
            case 176: {
               //#line 240 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs__conditionalSpecs_conditionalSpec_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                     (IconditionalSpecs)getRhsSym(1),
                                                                                     (IconditionalSpec)getRhsSym(2),
                                                                                     new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
            //
            // Rule 177:  conditionalSpec ::= identifier WITH identifier
            //
            case 177: {
               //#line 242 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpec__identifier_WITH_identifier(getLeftIToken(), getRightIToken(),
                                                                    (identifier)getRhsSym(1),
                                                                    new ASTNodeToken(getRhsIToken(2)),
                                                                    (identifier)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 178:  conditionalSpec ::= identifier AGAINST identifier
            //
            case 178: {
               //#line 243 "/Developer/eclipse-3.5.2-Classic/plugins/lpg.generator_2.0.19/templates/java/btParserTemplateF.gi"
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

