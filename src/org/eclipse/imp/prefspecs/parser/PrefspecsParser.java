
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

    public static void reportError(String msg) {
        System.err.println(msg);
    }


    //////////////////////////////////////////////////////////////////
    //
    // Code here is from the original grammar template; it is used by
    // the parse controller template (although parts evidently can be
    // commented out)
    //

    public class SymbolTable extends Hashtable {
        SymbolTable parent;
        SymbolTable(SymbolTable parent) { this.parent = parent; }
        public IAst findDeclaration(String name) {
            IAst decl = (IAst) get(name);
            return (decl != null
                          ? decl
                          : parent != null ? parent.findDeclaration(name) : null);
        }
        public SymbolTable getParent() { return parent; }
    }

    Stack symbolTableStack = null;
    SymbolTable topLevelSymbolTable = null;
    public SymbolTable getTopLevelSymbolTable() { return topLevelSymbolTable; }

    //
    // TODO: In the future, the user will be able to identify scope structures
    // (special non terminals such as block and functionDeclaration below) in
    // the grammar specification that carry symbol table information. The class
    // associated with such symbols will implement a special IScope interface and
    // will be required to specify an implementation of the method "getSymbolTable"
    // that is defined in IScope. Thus, the implementation of this funftion will
    // be simpler as it would only need to search for an instance of IScope.
    //
    public SymbolTable getEnclosingSymbolTable(IAst n) {
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
            // symbolTableStack = new Stack();
            // topLevelSymbolTable = new SymbolTable(null);
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
                prsStream.getLexStream().getLocation(id.getStartOffset(), id.getEndOffset()),
                prsStream.getLexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
        }

        public void emitError(ASTNode node, String message) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getLexStream().getLocation(
                    node.getLeftIToken().getStartOffset(), node.getRightIToken().getEndOffset()),
                prsStream.getLexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
        }

       public void emitError(int startOffset, int endOffset, String message) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getLexStream().getLocation(startOffset, endOffset),
                prsStream.getLexStream().getLocation(0, 0),
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
            if (n.getbooleanValue() instanceof booleanValue0) {
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
                            (fieldType.equals(COMBO_TYPE) && !(typeCustomSpecs instanceof stringCustomSpec)) ||
                            (fieldType.equals(DIRLIST_TYPE) && !(typeCustomSpecs instanceof stringCustomSpec)) ||
                            (fieldType.equals(FILE_TYPE) && !(typeCustomSpecs instanceof stringCustomSpec)) ||
                            (fieldType.equals(INT_TYPE) && !(typeCustomSpecs instanceof intCustomSpec)) ||
                            //(fieldType.equals(RADIO_TYPE) && !(typeCustomSpecs instanceof radioCustomSpec)) ||
                            (fieldType.equals(STRING_TYPE) && !(typeCustomSpecs instanceof stringCustomSpec)))
                        {
                            //emitError(n.getidentifier().getIToken(), ""); //"Field type not consistent with property specification");
                            
                            String propertyMsg = "Property specification not consistent with field type";
                            //if (typeCustomSpecs instanceof booleanCustomSpec) {
                            //    emitError((booleanCustomSpec)typeCustomSpecs, propertyMsg);
                            if (typeCustomSpecs instanceof booleanSpecialSpec) {
                                emitError((booleanSpecialSpec)typeCustomSpecs, propertyMsg);
                            } else if (typeCustomSpecs instanceof intCustomSpec) {
                                emitError((intCustomSpec)typeCustomSpecs, propertyMsg);
                            //} else if (typeCustomSpecs instanceof radioCustomSpec) {
                            //    emitError((radioCustomSpec)typeCustomSpecs, propertyMsg);
                            } else if (typeCustomSpecs instanceof stringCustomSpec) {
                                emitError((stringCustomSpec)typeCustomSpecs, propertyMsg);
                            }
                            //int startOffset = n.getidentifier().getIToken().getStartOffset();
                            //int endOffset = n.getRIGHTBRACE().getIToken().getEndOffset();
                            //emitError(startOffset, endOffset, "Property specification not consistent with field type");
                        }
                }
            }
            
            if (n.gettab() instanceof tab0) {
                // Have a the default tab
                inCustomSpecForDefaultTab = true;
            }
        
            return true;
        }


        public void endVisit(customRule n) { 
            inCustomSpecForDefaultTab = false;
        }
    
    
    
        public boolean visit(conditionalSpec0 n) {
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

        public void endVisit(conditionalSpec0 n) { }


         public boolean visit(conditionalSpec1 n) {
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

        public void endVisit(conditionalSpec1 n) { }


        


    } // End SymbolTableVisitor
    

    public void ruleAction(int ruleNumber)
    {
        switch (ruleNumber)
        {

            //
            // Rule 1:  prefSpecs ::= optPackageSpec optDetailsSpec topLevelItems
            //
            case 1: {
               //#line 54 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 56 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 3:  optPackageSpec ::= PACKAGE$ packageName ;$
            //
            case 3: {
               //#line 56 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 59 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 61 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 7:  optDetailsSpec ::= DETAILS$ onOff ;$
            //
            case 7: {
               //#line 61 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 63 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new onOff0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 9:  onOff ::= OFF
            //
            case 9: {
               //#line 63 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new onOff1(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 10:  topLevelItems ::= topLevelItem
            //
            case 10: {
               //#line 65 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new topLevelItemList((ItopLevelItem)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 11:  topLevelItems ::= topLevelItems topLevelItem
            //
            case 11: {
               //#line 65 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
            // Rule 14:  typeSpec ::= CHOICETYPE$ identifier {$ labelledStringValueList }$
            //
            case 14: {
               //#line 71 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeSpec(getLeftIToken(), getRightIToken(),
                                 (identifier)getRhsSym(2),
                                 (labelledStringValueList)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 15:  pageSpec ::= PAGE$ pageName {$ pageBody }$
            //
            case 15: {
               //#line 73 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 75 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 77 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 18:  pagePath ::= pagePath identifier .$
            //
            case 18: {
               //#line 78 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 80 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 20:  pageBody ::= tabsSpec fieldsSpec optionalSpecs
            //
            case 20: {
               //#line 81 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 83 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 85 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 88 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 94 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 27:  tabsSpec ::= TABS$ {$ tabSpecs }$
            //
            case 27: {
               //#line 94 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tabsSpec(getLeftIToken(), getRightIToken(),
                                 (tabSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 28:  tab ::= DEFAULT
            //
            case 28: {
               //#line 96 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 29:  tab ::= CONFIGURATION
            //
            case 29: {
               //#line 96 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab1(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 30:  tab ::= INSTANCE
            //
            case 30: {
               //#line 96 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab2(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 31:  tab ::= PROJECT
            //
            case 31: {
               //#line 96 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tab3(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 32:  tabSpecs ::= $Empty
            //
            case 32: {
               //#line 98 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 33:  tabSpecs ::= defaultTabSpec configurationTabSpec instanceTabSpec projectTabSpec
            //
            case 33: {
               //#line 99 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new tabSpecs(getLeftIToken(), getRightIToken(),
                                 (defaultTabSpec)getRhsSym(1),
                                 (configurationTabSpec)getRhsSym(2),
                                 (instanceTabSpec)getRhsSym(3),
                                 (projectTabSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 34:  defaultTabSpec ::= DEFAULT$ inout {$ generalSpecs }$
            //
            case 34: {
               //#line 101 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new defaultTabSpec(getLeftIToken(), getRightIToken(),
                                       (Iinout)getRhsSym(2),
                                       (generalSpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 35:  configurationTabSpec ::= CONFIGURATION$ inout {$ generalSpecs }$
            //
            case 35: {
               //#line 103 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new configurationTabSpec(getLeftIToken(), getRightIToken(),
                                             (Iinout)getRhsSym(2),
                                             (generalSpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 36:  instanceTabSpec ::= INSTANCE$ inout {$ generalSpecs }$
            //
            case 36: {
               //#line 105 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new instanceTabSpec(getLeftIToken(), getRightIToken(),
                                        (Iinout)getRhsSym(2),
                                        (generalSpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 37:  projectTabSpec ::= PROJECT$ inout {$ generalSpecs }$
            //
            case 37: {
               //#line 107 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new projectTabSpec(getLeftIToken(), getRightIToken(),
                                       (Iinout)getRhsSym(2),
                                       (generalSpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 38:  inout ::= IN
            //
            case 38: {
               //#line 111 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new inout0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 39:  inout ::= OUT
            //
            case 39: {
               //#line 111 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new inout1(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 40:  fieldsSpec ::= FIELDS$ {$ fieldSpecs }$
            //
            case 40: {
               //#line 116 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 118 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 120 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
            // Rule 47:  fieldSpec ::= dirListFieldSpec
            //
            case 47:
                break;
            //
            // Rule 48:  fieldSpec ::= doubleFieldSpec
            //
            case 48:
                break;
            //
            // Rule 49:  fieldSpec ::= fileFieldSpec
            //
            case 49:
                break;
            //
            // Rule 50:  fieldSpec ::= fontFieldSpec
            //
            case 50:
                break;
            //
            // Rule 51:  fieldSpec ::= intFieldSpec
            //
            case 51:
                break;
            //
            // Rule 52:  fieldSpec ::= radioFieldSpec
            //
            case 52:
                break;
            //
            // Rule 53:  fieldSpec ::= stringFieldSpec
            //
            case 53:
                break;
            //
            // Rule 54:  booleanFieldSpec ::= BOOLEAN$ identifier booleanFieldPropertySpecs optConditionalSpec
            //
            case 54: {
               //#line 134 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldSpec(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(2),
                                         (booleanFieldPropertySpecs)getRhsSym(3),
                                         (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 55:  colorFieldSpec ::= COLOR$ identifier colorFieldPropertySpecs optConditionalSpec
            //
            case 55: {
               //#line 136 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (colorFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 56:  comboFieldSpec ::= COMBO$ identifier comboFieldPropertySpecs optConditionalSpec
            //
            case 56: {
               //#line 138 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldSpec(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(2),
                                       (comboFieldPropertySpecs)getRhsSym(3),
                                       (optConditionalSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 57:  dirListFieldSpec ::= DIRLIST$ identifier dirlistFieldPropertySpecs optConditionalSpec
            //
            case 57: {
               //#line 140 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 142 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 144 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 146 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 148 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 150 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 152 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
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
               //#line 155 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 65:  booleanFieldPropertySpecs ::= {$ generalSpecs booleanSpecificSpec }$
            //
            case 65: {
               //#line 155 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (generalSpecs)getRhsSym(2),
                                                  (booleanSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 66:  colorFieldPropertySpecs ::= $Empty
            //
            case 66: {
               //#line 157 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 67:  colorFieldPropertySpecs ::= {$ generalSpecs colorSpecificSpec }$
            //
            case 67: {
               //#line 157 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (generalSpecs)getRhsSym(2),
                                                (colorDefValueSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 68:  comboFieldPropertySpecs ::= $Empty
            //
            case 68: {
               //#line 159 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 69:  comboFieldPropertySpecs ::= {$ generalSpecs comboSpecificSpec }$
            //
            case 69: {
               //#line 159 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (generalSpecs)getRhsSym(2),
                                                (comboSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 70:  dirlistFieldPropertySpecs ::= $Empty
            //
            case 70: {
               //#line 161 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 71:  dirlistFieldPropertySpecs ::= {$ generalSpecs stringSpecificSpec }$
            //
            case 71: {
               //#line 161 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new dirlistFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                  (generalSpecs)getRhsSym(2),
                                                  (stringSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 72:  doubleFieldPropertySpecs ::= $Empty
            //
            case 72: {
               //#line 163 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 73:  doubleFieldPropertySpecs ::= {$ generalSpecs doubleSpecificSpec }$
            //
            case 73: {
               //#line 163 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (generalSpecs)getRhsSym(2),
                                                 (doubleSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 74:  fileFieldPropertySpecs ::= $Empty
            //
            case 74: {
               //#line 165 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 75:  fileFieldPropertySpecs ::= {$ generalSpecs stringSpecificSpec }$
            //
            case 75: {
               //#line 165 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fileFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (generalSpecs)getRhsSym(2),
                                               (stringSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 76:  fontFieldPropertySpecs ::= $Empty
            //
            case 76: {
               //#line 167 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 77:  fontFieldPropertySpecs ::= {$ generalSpecs fontSpecificSpec }$
            //
            case 77: {
               //#line 167 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                               (generalSpecs)getRhsSym(2),
                                               (fontDefValueSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 78:  intFieldPropertySpecs ::= $Empty
            //
            case 78: {
               //#line 169 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 79:  intFieldPropertySpecs ::= {$ generalSpecs intSpecificSpec }$
            //
            case 79: {
               //#line 169 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                              (generalSpecs)getRhsSym(2),
                                              (intSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 80:  radioFieldPropertySpecs ::= $Empty
            //
            case 80: {
               //#line 171 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 81:  radioFieldPropertySpecs ::= {$ generalSpecs radioSpecificSpec }$
            //
            case 81: {
               //#line 171 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                (generalSpecs)getRhsSym(2),
                                                (radioSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 82:  stringFieldPropertySpecs ::= $Empty
            //
            case 82: {
               //#line 173 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 83:  stringFieldPropertySpecs ::= {$ generalSpecs stringSpecificSpec }$
            //
            case 83: {
               //#line 173 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringFieldPropertySpecs(getLeftIToken(), getRightIToken(),
                                                 (generalSpecs)getRhsSym(2),
                                                 (stringSpecificSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 84:  optConditionalSpec ::= $Empty
            //
            case 84: {
               //#line 176 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 85:  optConditionalSpec ::= conditionType identifier
            //
            case 85: {
               //#line 176 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optConditionalSpec(getLeftIToken(), getRightIToken(),
                                           (IconditionType)getRhsSym(1),
                                           (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 86:  conditionType ::= IF
            //
            case 86: {
               //#line 178 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 87:  conditionType ::= UNLESS
            //
            case 87: {
               //#line 178 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionType1(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 88:  customSpec ::= CUSTOM$ {$ customRules }$
            //
            case 88: {
               //#line 182 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new customSpec(getLeftIToken(), getRightIToken(),
                                   (IcustomRules)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 89:  customRules ::= $Empty
            //
            case 89: {
               //#line 184 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 90:  customRules ::= customRule
            //
            case 90:
                break;
            //
            // Rule 91:  customRules ::= customRules customRule
            //
            case 91: {
               //#line 186 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new customRules(getLeftIToken(), getRightIToken(),
                                    (IcustomRules)getRhsSym(1),
                                    (customRule)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 92:  customRule ::= tab identifier {$ newPropertySpecs }$
            //
            case 92: {
               //#line 188 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new customRule(getLeftIToken(), getRightIToken(),
                                   (Itab)getRhsSym(1),
                                   (identifier)getRhsSym(2),
                                   (InewPropertySpecs)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 93:  newPropertySpecs ::= generalSpecs
            //
            case 93:
                break;
            //
            // Rule 94:  newPropertySpecs ::= generalSpecs typeCustomSpecs
            //
            case 94: {
               //#line 191 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new newPropertySpecs(getLeftIToken(), getRightIToken(),
                                         (generalSpecs)getRhsSym(1),
                                         (ItypeCustomSpecs)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 95:  typeCustomSpecs ::= booleanCustomSpec
            //
            case 95:
                break;
            //
            // Rule 96:  typeCustomSpecs ::= intCustomSpec
            //
            case 96:
                break;
            //
            // Rule 97:  typeCustomSpecs ::= radioCustomSpec
            //
            case 97:
                break;
            //
            // Rule 98:  typeCustomSpecs ::= stringCustomSpec
            //
            case 98:
                break;
            //
            // Rule 99:  conditionalsSpec ::= CONDITIONALS$ {$ conditionalSpecs }$
            //
            case 99: {
               //#line 201 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalsSpec(getLeftIToken(), getRightIToken(),
                                         (IconditionalSpecs)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 100:  conditionalSpecs ::= $Empty
            //
            case 100: {
               //#line 203 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 101:  conditionalSpecs ::= conditionalSpec ;
            //
            case 101: {
               //#line 204 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs0(getLeftIToken(), getRightIToken(),
                                          (IconditionalSpec)getRhsSym(1),
                                          new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 102:  conditionalSpecs ::= conditionalSpecs conditionalSpec ;
            //
            case 102: {
               //#line 205 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpecs1(getLeftIToken(), getRightIToken(),
                                          (IconditionalSpecs)getRhsSym(1),
                                          (IconditionalSpec)getRhsSym(2),
                                          new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
            //
            // Rule 103:  conditionalSpec ::= identifier WITH identifier
            //
            case 103: {
               //#line 207 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpec0(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(1),
                                         new ASTNodeToken(getRhsIToken(2)),
                                         (identifier)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 104:  conditionalSpec ::= identifier AGAINST identifier
            //
            case 104: {
               //#line 208 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new conditionalSpec1(getLeftIToken(), getRightIToken(),
                                         (identifier)getRhsSym(1),
                                         new ASTNodeToken(getRhsIToken(2)),
                                         (identifier)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 105:  generalSpecs ::= isEditableSpec isRemovableSpec optLabelSpec optToolTipSpec
            //
            case 105: {
               //#line 213 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new generalSpecs(getLeftIToken(), getRightIToken(),
                                     (isEditableSpec)getRhsSym(1),
                                     (isRemovableSpec)getRhsSym(2),
                                     (optLabelSpec)getRhsSym(3),
                                     (optToolTipSpec)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 106:  isEditableSpec ::= $Empty
            //
            case 106: {
               //#line 215 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 107:  isEditableSpec ::= ISEDITABLE$ booleanValue ;$
            //
            case 107: {
               //#line 215 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new isEditableSpec(getLeftIToken(), getRightIToken(),
                                       (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 108:  isRemovableSpec ::= $Empty
            //
            case 108: {
               //#line 216 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 109:  isRemovableSpec ::= ISREMOVABLE$ booleanValue ;$
            //
            case 109: {
               //#line 216 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new isRemovableSpec(getLeftIToken(), getRightIToken(),
                                        (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 110:  optLabelSpec ::= $Empty
            //
            case 110: {
               //#line 217 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 111:  optLabelSpec ::= LABEL$ STRING_LITERAL ;$
            //
            case 111: {
               //#line 217 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optLabelSpec(getLeftIToken(), getRightIToken(),
                                     new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 112:  optToolTipSpec ::= $Empty
            //
            case 112: {
               //#line 218 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 113:  optToolTipSpec ::= TOOLTIP$ STRING_LITERAL ;$
            //
            case 113: {
               //#line 218 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new optToolTipSpec(getLeftIToken(), getRightIToken(),
                                       new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 114:  booleanSpecificSpec ::= booleanCustomSpec booleanDefValueSpec
            //
            case 114: {
               //#line 221 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecificSpec(getLeftIToken(), getRightIToken(),
                                            (booleanSpecialSpec)getRhsSym(1),
                                            (booleanDefValueSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 115:  booleanCustomSpec ::= booleanSpecialSpec
            //
            case 115:
                break;
            //
            // Rule 116:  booleanSpecialSpec ::= $Empty
            //
            case 116: {
               //#line 223 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 117:  booleanSpecialSpec ::= HASSPECIAL$ booleanValue ;$
            //
            case 117: {
               //#line 223 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanSpecialSpec(getLeftIToken(), getRightIToken(),
                                           (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 118:  booleanDefValueSpec ::= $Empty
            //
            case 118: {
               //#line 224 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 119:  booleanDefValueSpec ::= DEFVALUE$ booleanValue ;$
            //
            case 119: {
               //#line 224 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanDefValueSpec(getLeftIToken(), getRightIToken(),
                                            (IbooleanValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 120:  comboSpecificSpec ::= comboCustomSpec comboDefValueSpec
            //
            case 120: {
               //#line 227 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboSpecificSpec(getLeftIToken(), getRightIToken(),
                                          (comboCustomSpec)getRhsSym(1),
                                          (comboDefValueSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 121:  comboCustomSpec ::= columnsSpec typeOrValuesSpec
            //
            case 121: {
               //#line 228 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboCustomSpec(getLeftIToken(), getRightIToken(),
                                        (columnsSpec)getRhsSym(1),
                                        (ItypeOrValuesSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 122:  comboDefValueSpec ::= $Empty
            //
            case 122: {
               //#line 229 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 123:  comboDefValueSpec ::= DEFVALUE$ stringValue ;$
            //
            case 123: {
               //#line 229 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new comboDefValueSpec(getLeftIToken(), getRightIToken(),
                                          (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 124:  radioSpecificSpec ::= radioCustomSpec radioDefValueSpec
            //
            case 124: {
               //#line 231 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioSpecificSpec(getLeftIToken(), getRightIToken(),
                                          (radioCustomSpec)getRhsSym(1),
                                          (radioDefValueSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 125:  radioCustomSpec ::= columnsSpec typeOrValuesSpec
            //
            case 125: {
               //#line 232 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioCustomSpec(getLeftIToken(), getRightIToken(),
                                        (columnsSpec)getRhsSym(1),
                                        (ItypeOrValuesSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 126:  radioDefValueSpec ::= $Empty
            //
            case 126: {
               //#line 233 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 127:  radioDefValueSpec ::= DEFVALUE$ identifier ;$
            //
            case 127: {
               //#line 233 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new radioDefValueSpec(getLeftIToken(), getRightIToken(),
                                          (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 128:  typeOrValuesSpec ::= TYPE$ identifier ;$
            //
            case 128: {
               //#line 235 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec0(getLeftIToken(), getRightIToken(),
                                          (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 129:  typeOrValuesSpec ::= valuesSpec ;$
            //
            case 129: {
               //#line 235 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new typeOrValuesSpec1(getLeftIToken(), getRightIToken(),
                                          (valuesSpec)getRhsSym(1))
                );
                break;
            }
            //
            // Rule 130:  valuesSpec ::= VALUES$ {$ labelledStringValueList }$
            //
            case 130: {
               //#line 236 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new valuesSpec(getLeftIToken(), getRightIToken(),
                                   (labelledStringValueList)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 131:  columnsSpec ::= $Empty
            //
            case 131: {
               //#line 237 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 132:  columnsSpec ::= COLUMNS$ INTEGER ;$
            //
            case 132: {
               //#line 237 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new columnsSpec(getLeftIToken(), getRightIToken(),
                                    new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 133:  labelledStringValueList ::= labelledStringValue
            //
            case 133: {
               //#line 239 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValueList((labelledStringValue)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 134:  labelledStringValueList ::= labelledStringValueList ,$ labelledStringValue
            //
            case 134: {
               //#line 240 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                ((labelledStringValueList)getRhsSym(1)).add((labelledStringValue)getRhsSym(3));
                break;
            }
            //
            // Rule 135:  labelledStringValue ::= identifier optLabel
            //
            case 135: {
               //#line 241 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new labelledStringValue(getLeftIToken(), getRightIToken(),
                                            (identifier)getRhsSym(1),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 136:  optLabel ::= $Empty
            //
            case 136: {
               //#line 242 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 137:  optLabel ::= stringValue
            //
            case 137:
                break;
            //
            // Rule 138:  colorSpecificSpec ::= colorDefValueSpec
            //
            case 138:
                break;
            //
            // Rule 139:  colorDefValueSpec ::= $Empty
            //
            case 139: {
               //#line 246 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 140:  colorDefValueSpec ::= DEFVALUE$ INTEGER$red ,$ INTEGER$green ,$ INTEGER$blue ;$
            //
            case 140: {
               //#line 246 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new colorDefValueSpec(getLeftIToken(), getRightIToken(),
                                          new ASTNodeToken(getRhsIToken(2)),
                                          new ASTNodeToken(getRhsIToken(4)),
                                          new ASTNodeToken(getRhsIToken(6)))
                );
                break;
            }
            //
            // Rule 141:  fontSpecificSpec ::= fontDefValueSpec
            //
            case 141:
                break;
            //
            // Rule 142:  fontDefValueSpec ::= $Empty
            //
            case 142: {
               //#line 251 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 143:  fontDefValueSpec ::= DEFVALUE$ stringValue$name INTEGER$height fontStyle$style ;$
            //
            case 143: {
               //#line 251 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontDefValueSpec(getLeftIToken(), getRightIToken(),
                                         (stringValue)getRhsSym(2),
                                         new ASTNodeToken(getRhsIToken(3)),
                                         (IfontStyle)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 144:  fontStyle ::= NORMAL
            //
            case 144: {
               //#line 252 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 145:  fontStyle ::= BOLD
            //
            case 145: {
               //#line 252 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle1(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 146:  fontStyle ::= ITALIC
            //
            case 146: {
               //#line 252 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new fontStyle2(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 147:  intSpecificSpec ::= intCustomSpec intDefValueSpec
            //
            case 147: {
               //#line 255 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecificSpec(getLeftIToken(), getRightIToken(),
                                        (intCustomSpec)getRhsSym(1),
                                        (intDefValueSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 148:  intCustomSpec ::= intRangeSpec intSpecialSpec
            //
            case 148: {
               //#line 256 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intCustomSpec(getLeftIToken(), getRightIToken(),
                                      (intRangeSpec)getRhsSym(1),
                                      (intSpecialSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 149:  intRangeSpec ::= $Empty
            //
            case 149: {
               //#line 257 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 150:  intRangeSpec ::= RANGE$ signedNumber$low DOTS$ signedNumber$high ;$
            //
            case 150: {
               //#line 257 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intRangeSpec(getLeftIToken(), getRightIToken(),
                                     (IsignedNumber)getRhsSym(2),
                                     (IsignedNumber)getRhsSym(4))
                );
                break;
            }
            //
            // Rule 151:  intSpecialSpec ::= $Empty
            //
            case 151: {
               //#line 258 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 152:  intSpecialSpec ::= HASSPECIAL$ signedNumber ;$
            //
            case 152: {
               //#line 258 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intSpecialSpec(getLeftIToken(), getRightIToken(),
                                       (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 153:  intDefValueSpec ::= $Empty
            //
            case 153: {
               //#line 259 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 154:  intDefValueSpec ::= DEFVALUE$ signedNumber ;$
            //
            case 154: {
               //#line 259 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new intDefValueSpec(getLeftIToken(), getRightIToken(),
                                        (IsignedNumber)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 155:  doubleSpecificSpec ::= doubleCustomSpec doubleDefValueSpec
            //
            case 155: {
               //#line 261 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleSpecificSpec(getLeftIToken(), getRightIToken(),
                                           (doubleRangeSpec)getRhsSym(1),
                                           (doubleDefValueSpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 156:  doubleCustomSpec ::= doubleRangeSpec
            //
            case 156:
                break;
            //
            // Rule 157:  doubleRangeSpec ::= $Empty
            //
            case 157: {
               //#line 263 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 158:  doubleRangeSpec ::= RANGE$ DECIMAL$low DOTS$ DECIMAL$high ;$
            //
            case 158: {
               //#line 263 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleRangeSpec(getLeftIToken(), getRightIToken(),
                                        new ASTNodeToken(getRhsIToken(2)),
                                        new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 159:  doubleDefValueSpec ::= $Empty
            //
            case 159: {
               //#line 264 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 160:  doubleDefValueSpec ::= DEFVALUE$ DECIMAL ;$
            //
            case 160: {
               //#line 264 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new doubleDefValueSpec(getLeftIToken(), getRightIToken(),
                                           new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 161:  stringSpecificSpec ::= stringCustomSpec stringDefValueSpec stringValidatorSpec
            //
            case 161: {
               //#line 266 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringSpecificSpec(getLeftIToken(), getRightIToken(),
                                           (stringCustomSpec)getRhsSym(1),
                                           (stringDefValueSpec)getRhsSym(2),
                                           (stringValidatorSpec)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 162:  stringCustomSpec ::= stringSpecialSpec stringEmptySpec
            //
            case 162: {
               //#line 267 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringCustomSpec(getLeftIToken(), getRightIToken(),
                                         (stringSpecialSpec)getRhsSym(1),
                                         (IstringEmptySpec)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 163:  stringSpecialSpec ::= $Empty
            //
            case 163: {
               //#line 268 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 164:  stringSpecialSpec ::= HASSPECIAL$ stringValue ;$
            //
            case 164: {
               //#line 268 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringSpecialSpec(getLeftIToken(), getRightIToken(),
                                          (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 165:  stringEmptySpec ::= $Empty
            //
            case 165: {
               //#line 269 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 166:  stringEmptySpec ::= EMPTYALLOWED$ FALSE ;$
            //
            case 166: {
               //#line 270 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec0(getLeftIToken(), getRightIToken(),
                                         new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 167:  stringEmptySpec ::= EMPTYALLOWED$ TRUE stringValue ;$
            //
            case 167: {
               //#line 271 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringEmptySpec1(getLeftIToken(), getRightIToken(),
                                         new ASTNodeToken(getRhsIToken(2)),
                                         (stringValue)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 168:  stringDefValueSpec ::= $Empty
            //
            case 168: {
               //#line 272 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 169:  stringDefValueSpec ::= DEFVALUE$ stringValue ;$
            //
            case 169: {
               //#line 272 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringDefValueSpec(getLeftIToken(), getRightIToken(),
                                           (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 170:  stringValidatorSpec ::= $Empty
            //
            case 170: {
               //#line 273 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(null);
                break;
            }
            //
            // Rule 171:  stringValidatorSpec ::= VALIDATOR$ stringValue$qualClassName ;$
            //
            case 171: {
               //#line 273 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValidatorSpec(getLeftIToken(), getRightIToken(),
                                            (stringValue)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 172:  identifier ::= IDENTIFIER
            //
            case 172: {
               //#line 277 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new identifier(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 173:  booleanValue ::= TRUE
            //
            case 173: {
               //#line 279 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 174:  booleanValue ::= FALSE
            //
            case 174: {
               //#line 279 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new booleanValue1(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 175:  stringValue ::= STRING_LITERAL
            //
            case 175: {
               //#line 281 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new stringValue(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 176:  signedNumber ::= INTEGER
            //
            case 176: {
               //#line 283 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 177:  signedNumber ::= sign INTEGER
            //
            case 177: {
               //#line 283 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new signedNumber1(getLeftIToken(), getRightIToken(),
                                      (Isign)getRhsSym(1),
                                      new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 178:  sign ::= PLUS
            //
            case 178: {
               //#line 285 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign0(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 179:  sign ::= MINUS
            //
            case 179: {
               //#line 285 "/Users/rmfuhrer/eclipse/workspaces/imp-3.3-release/lpg.generator/templates/java/btParserTemplateF.gi"
                setResult(
                    new sign1(getRhsIToken(1))
                );
                break;
            }
    
            default:
                break;
        }
        return;
    }
}

