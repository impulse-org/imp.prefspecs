%options package=org.eclipse.imp.prefspecs.parser
%options template=btParserTemplateF.gi
%options import_terminals=PrefspecsLexer.gi
%options parent_saved,automatic_ast=toplevel,visitor=preorder,ast_directory=./Ast,ast_type=ASTNode

%Globals
    /.import org.eclipse.imp.parser.IParser;
    import org.eclipse.imp.parser.SymbolTable;
    import java.util.Hashtable;
    import java.util.Stack;
    import java.util.List;
    import java.util.ArrayList;
    import java.util.HashMap;
    ./
%End

%Define
    $ast_class /.Object./
    $additional_interfaces /., IParser./
%End        

%Terminals
         COMMA ::= ','
         DOT   ::= '.'
         SEMICOLON ::= ';'
         PLUS ::= '+'
         MINUS ::= '-'
         LEFTPAREN ::= '('
         RIGHTPAREN ::= ')'
         LEFTBRACE ::= '{'
         RIGHTBRACE ::= '}'
%End

%Start
    prefSpecs
%End

%Notice
/.
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
./
%End

%Rules
    prefSpecs ::= optPackageSpec optDetailsSpec topLevelItems

    optPackageSpec ::= %empty | PACKAGE$ packageName ';'$

    packageName ::= identifier
                  | packageName '.'$ identifier

    optDetailsSpec ::= %empty | DETAILS$ onOff ';'$

    onOff ::= ON | OFF

    topLevelItems$$topLevelItem ::= topLevelItem | topLevelItems topLevelItem

    topLevelItem ::= typeSpec | pageSpec

    -- Rules for the major parts:  types, pages and their sections
    
    typeSpec ::= CHOICETYPE$ identifier '{'$ labelledStringValueList '}'$

    pageSpec ::= PAGE$ pageName '{'$ pageBody '}'$

    pageName ::= pagePath identifier$name

    pagePath ::= %empty
               | pagePath identifier '.'$

    pageBody ::= %empty
               | tabsSpec fieldsSpec optionalSpecs

    optionalSpecs ::= customSpecOption conditionalsSpecOption

    customSpecOption ::= %empty
                       | customSpec

    conditionalsSpecOption ::= %empty
                             | conditionalsSpec

    -- Rules for the "tabs" section
                    
    tabsSpec ::=  %empty | TABS$ '{'$ tabSpecs '}'$
    
    tabSpecs$$tabSpec ::= %empty | tabSpecs tabSpec
    tabSpec ::= defaultTabSpec | configurationTabSpec | instanceTabSpec | projectTabSpec

    defaultTabSpec       ::= DEFAULT$       inout '{'$ generalSpecs '}'$

    configurationTabSpec ::= CONFIGURATION$ inout '{'$ generalSpecs '}'$

    instanceTabSpec      ::= INSTANCE$      inout '{'$ generalSpecs '}'$

    projectTabSpec       ::= PROJECT$       inout '{'$ generalSpecs '}'$

    --tabPropertySpecs ::= isEditableSpec isRemovableSpec

    inout ::= IN | OUT


    -- Rules for the "fields" section
    
    fieldsSpec ::= FIELDS$ '{'$ fieldSpecs '}'$

    fieldSpecs ::= %empty
                 | fieldSpec
                 | fieldSpecs fieldSpec
                    
    fieldSpec ::= booleanFieldSpec
                | colorFieldSpec
                | comboFieldSpec
                | directoryFieldSpec
                | dirListFieldSpec
                | doubleFieldSpec
                | fileFieldSpec
                | fontFieldSpec
                | intFieldSpec
                | radioFieldSpec
                | stringFieldSpec


    booleanFieldSpec   ::= BOOLEAN$   identifier booleanFieldPropertySpecs   optConditionalSpec
    colorFieldSpec     ::= COLOR$     identifier colorFieldPropertySpecs     optConditionalSpec
    comboFieldSpec     ::= COMBO$     identifier comboFieldPropertySpecs     optConditionalSpec
    directoryFieldSpec ::= DIRECTORY$ identifier directoryFieldPropertySpecs optConditionalSpec
    dirListFieldSpec   ::= DIRLIST$   identifier dirlistFieldPropertySpecs   optConditionalSpec
    doubleFieldSpec    ::= DOUBLE$    identifier doubleFieldPropertySpecs    optConditionalSpec
    fileFieldSpec      ::= FILE$      identifier fileFieldPropertySpecs      optConditionalSpec
    fontFieldSpec      ::= FONT$      identifier fontFieldPropertySpecs      optConditionalSpec
    intFieldSpec       ::= INT$       identifier intFieldPropertySpecs       optConditionalSpec
    radioFieldSpec     ::= RADIO$     identifier radioFieldPropertySpecs     optConditionalSpec
    stringFieldSpec    ::= STRING$    identifier stringFieldPropertySpecs    optConditionalSpec


    booleanFieldPropertySpecs   ::= %empty | '{'$ booleanSpecificSpecs '}'$
    colorFieldPropertySpecs     ::= %empty | '{'$ colorSpecificSpecs   '}'$
    comboFieldPropertySpecs     ::= %empty | '{'$ comboSpecificSpecs   '}'$
    directoryFieldPropertySpecs ::= %empty | '{'$ stringSpecificSpecs  '}'$
    dirlistFieldPropertySpecs   ::= %empty | '{'$ stringSpecificSpecs  '}'$
    doubleFieldPropertySpecs    ::= %empty | '{'$ doubleSpecificSpecs  '}'$
    fileFieldPropertySpecs      ::= %empty | '{'$ stringSpecificSpecs  '}'$
    fontFieldPropertySpecs      ::= %empty | '{'$ fontSpecificSpecs    '}'$
    intFieldPropertySpecs       ::= %empty | '{'$ intSpecificSpecs     '}'$
    radioFieldPropertySpecs     ::= %empty | '{'$ radioSpecificSpecs   '}'$
    stringFieldPropertySpecs    ::= %empty | '{'$ stringSpecificSpecs  '}'$

    -- Rules for specifications used in various parts
    generalSpecs$$generalSpec ::= %empty | generalSpecs generalSpec
    generalSpec ::= isEditableSpec | isRemovableSpec | optLabelSpec | optToolTipSpec

    isEditableSpec  ::= ISEDITABLE$ booleanValue ';'$
    isRemovableSpec ::= ISREMOVABLE$ booleanValue ';'$
    optLabelSpec    ::= LABEL$ STRING_LITERAL ';'$
    optToolTipSpec  ::= TOOLTIP$ STRING_LITERAL ';'$


    booleanSpecificSpecs$$booleanSpecificSpec ::= booleanSpecificSpec | booleanSpecificSpecs booleanSpecificSpec
    booleanSpecificSpec ::= booleanSpecialSpec | booleanDefValueSpec | generalSpec
    booleanSpecialSpec  ::= HASSPECIAL$ booleanValue ';'$
    booleanDefValueSpec ::= DEFVALUE$ booleanValue ';'$


    colorSpecificSpecs$$colorSpecificSpec ::= colorSpecificSpec | colorSpecificSpecs colorSpecificSpec
    colorSpecificSpec ::= colorDefValueSpec | generalSpec
    colorDefValueSpec ::= DEFVALUE$ INTEGER$red ','$ INTEGER$green ','$ INTEGER$blue ';'$


    comboSpecificSpecs$$comboSpecificSpec ::= comboSpecificSpec | comboSpecificSpecs comboSpecificSpec
    comboSpecificSpec ::= columnsSpec | typeOrValuesSpec | comboDefValueSpec | generalSpec
    comboDefValueSpec ::= DEFVALUE$ identifier ';'$


    doubleSpecificSpecs$$doubleSpecificSpec ::= doubleSpecificSpec | doubleSpecificSpecs doubleSpecificSpec
    doubleSpecificSpec ::= doubleRangeSpec | doubleDefValueSpec
    doubleRangeSpec    ::= RANGE$ DECIMAL$low DOTS$ DECIMAL$high ';'$
    doubleDefValueSpec ::= DEFVALUE$ DECIMAL ';'$


    fontSpecificSpecs$$fontSpecificSpec ::= fontSpecificSpec | fontSpecificSpecs fontSpecificSpec
    fontSpecificSpec ::= fontDefValueSpec | generalSpec
    -- The following represents the information needed to construct a FontData object
    fontDefValueSpec ::= DEFVALUE$ stringValue$name INTEGER$height fontStyle$style ';'$
    fontStyle        ::= NORMAL | BOLD | ITALIC


    intSpecificSpecs$$intSpecificSpec ::= intSpecificSpec | intSpecificSpecs intSpecificSpec
    intSpecificSpec ::= intRangeSpec | intSpecialSpec | intDefValueSpec | generalSpec
    intRangeSpec    ::= RANGE$ signedNumber$low DOTS$ signedNumber$high ';'$
    intSpecialSpec  ::= HASSPECIAL$ signedNumber ';'$
    intDefValueSpec ::= DEFVALUE$ signedNumber ';'$


    radioSpecificSpecs$$radioSpecificSpec ::= radioSpecificSpec | radioSpecificSpecs radioSpecificSpec
    radioSpecificSpec ::= radioDefValueSpec | columnsSpec | typeOrValuesSpec
    radioDefValueSpec ::= DEFVALUE$ identifier ';'$

    typeOrValuesSpec  ::= TYPE$ identifier ';'$ | valuesSpec ';'$
    valuesSpec        ::= VALUES$ '{'$ labelledStringValueList '}'$
    columnsSpec       ::= COLUMNS$ INTEGER ';'$

    labelledStringValueList$$labelledStringValue ::=
        labelledStringValue | labelledStringValueList ','$ labelledStringValue
    labelledStringValue ::= identifier optLabel
    optLabel            ::= %empty | stringValue


    stringSpecificSpecs$$stringSpecificSpec ::= stringSpecificSpec | stringSpecificSpecs stringSpecificSpec
    stringSpecificSpec  ::= stringDefValueSpec | stringValidatorSpec | stringSpecialSpec | stringEmptySpec | generalSpec
    stringSpecialSpec   ::= HASSPECIAL$ stringValue ';'$
    stringEmptySpec     ::= EMPTYALLOWED$ FALSE ';'$
                          | EMPTYALLOWED$ TRUE stringValue ';'$
    stringDefValueSpec  ::= DEFVALUE$ stringValue ';'$
    stringValidatorSpec ::= VALIDATOR$ stringValue$qualClassName ';'$


    optConditionalSpec ::= %empty | conditionType identifier

    conditionType ::= IF | UNLESS

    -- Rules for values and identifiers

    identifier   ::= IDENTIFIER

    booleanValue ::= TRUE | FALSE

    stringValue  ::= STRING_LITERAL

    signedNumber ::= INTEGER | sign INTEGER

    sign ::= PLUS | MINUS

    -- Rules for the "custom" section
     
    customSpec ::= CUSTOM$ '{'$ customRules '}'$

    customRules ::= %empty
                       |  customRule
                       |  customRules customRule

    tab ::= DEFAULT | CONFIGURATION | INSTANCE | PROJECT
    
    customRule ::= tab identifier '{'$ newPropertySpecs '}'$

    newPropertySpecs ::= generalSpecs 
                       | generalSpecs typeCustomSpecs

    typeCustomSpecs ::=  booleanCustomSpec
                     |   intCustomSpec
                     |   radioCustomSpec
                     |   stringCustomSpec


    -- Rules for the "conditionals" section

    conditionalsSpec ::= CONDITIONALS$ '{'$ conditionalSpecs '}'$

    conditionalSpecs ::= %empty
                       | conditionalSpec ;
                       | conditionalSpecs conditionalSpec ;

    conditionalSpec ::= identifier WITH identifier
                      | identifier AGAINST identifier
%End

%Headers
    /.
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

        public void resolve($ast_type root) {
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
        
    ./
%End
