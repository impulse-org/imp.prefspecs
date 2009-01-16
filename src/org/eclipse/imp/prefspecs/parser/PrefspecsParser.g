%options package=org.eclipse.imp.prefspecs.parser
%options template=btParserTemplateF.gi
%options import_terminals=PrefspecsLexer.gi
%options parent_saved,automatic_ast=toplevel,visitor=preorder,ast_directory=./Ast,ast_type=ASTNode

%Globals
    /.import org.eclipse.imp.parser.IParser;
    import java.util.Hashtable;
    import java.util.Stack;
    // SMS 2 Apr 2007
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
         IDENTIFIER
         NUMBER
         STRING_LITERAL
         DoubleLiteral
         COMMA ::= ','
         DOT   ::= '.'
         SEMICOLON ::= ';'
         PLUS ::= '+'
         MINUS ::= '-'
         LEFTPAREN ::= '('
         RIGHTPAREN ::= ')'
         LEFTBRACE ::= '{'
         RIGHTBRACE ::= '}'
    -- Note that the terminals that do not have aliases are declared
    -- above only for documentation purposes.
%End

%Start
    prefSpecs
%End

%Recover
   MissingExpression
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
    prefSpecs ::= optPackageSpec optDetailsSpec pageSpecList

    optPackageSpec ::= %empty | PACKAGE$ packageName ';'$

    packageName ::= identifier
                  | packageName '.'$ identifier

    optDetailsSpec ::= %empty | DETAILS$ onOff ';'$

    onOff ::= ON | OFF

    pageSpecList$$pageSpec ::= pageSpec | pageSpecList pageSpec

    -- Rules for the major parts:  pages and their sections
    
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
    
    tab ::= DEFAULT | CONFIGURATION | INSTANCE | PROJECT
    
    tabSpecs ::= %empty
               | defaultTabSpec configurationTabSpec instanceTabSpec projectTabSpec

    defaultTabSpec       ::= DEFAULT$ inout '{'$ generalSpecs '}'$

    configurationTabSpec ::= CONFIGURATION$ inout '{'$ generalSpecs '}'$

    instanceTabSpec      ::= INSTANCE$ inout '{'$ generalSpecs '}'$

    projectTabSpec       ::= PROJECT$ inout '{'$ generalSpecs '}'$

    --tabPropertySpecs ::= isEditableSpec isRemovableSpec

    inout ::= IN | OUT


    -- Rules for the "fields" section
    
    fieldsSpec ::= FIELDS$ '{'$ fieldSpecs '}'$
    
    fieldSpecs ::= %empty
                 | fieldSpec
                 | fieldSpecs fieldSpec
                    
    fieldSpec ::= booleanFieldSpec
                | comboFieldSpec
                | dirListFieldSpec
                | fileFieldSpec
                | fontFieldSpec
                | intFieldSpec
                | radioFieldSpec
                | stringFieldSpec


    booleanFieldSpec ::= BOOLEAN$ identifier booleanFieldPropertySpecs optConditionalSpec

    comboFieldSpec   ::= COMBO$   identifier comboFieldPropertySpecs   optConditionalSpec

    dirListFieldSpec ::= DIRLIST$ identifier dirlistFieldPropertySpecs optConditionalSpec

    fileFieldSpec    ::= FILE$    identifier fileFieldPropertySpecs    optConditionalSpec

    fontFieldSpec    ::= FONT$    identifier fontFieldPropertySpecs    optConditionalSpec

    intFieldSpec     ::= INT$     identifier intFieldPropertySpecs     optConditionalSpec

    radioFieldSpec   ::= RADIO$   identifier radioFieldPropertySpecs   optConditionalSpec

    stringFieldSpec  ::= STRING$  identifier stringFieldPropertySpecs  optConditionalSpec


    booleanFieldPropertySpecs ::= %empty | '{'$ generalSpecs booleanSpecificSpec '}'$

    comboFieldPropertySpecs   ::= %empty | '{'$ generalSpecs comboSpecificSpec   '}'$

    dirlistFieldPropertySpecs ::= %empty | '{'$ generalSpecs stringSpecificSpec  '}'$

    fileFieldPropertySpecs    ::= %empty | '{'$ generalSpecs stringSpecificSpec  '}'$

    fontFieldPropertySpecs    ::= %empty | '{'$ generalSpecs fontSpecificSpec    '}'$

    intFieldPropertySpecs     ::= %empty | '{'$ generalSpecs intSpecificSpec     '}'$

    radioFieldPropertySpecs   ::= %empty | '{'$ generalSpecs radioSpecificSpec   '}'$

    stringFieldPropertySpecs  ::= %empty | '{'$ generalSpecs stringSpecificSpec  '}'$

    optConditionalSpec ::= %empty | conditionType identifier

    conditionType ::= IF | UNLESS

    -- Rules for the "custom" section
     
    customSpec ::= CUSTOM$ '{'$ customRules '}'$

    customRules ::= %empty
                       |  customRule
                       |  customRules customRule

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


    -- Rules for specifications used in various parts

    generalSpecs ::= isEditableSpec isRemovableSpec optLabelSpec optToolTipSpec

    isEditableSpec  ::= %empty | ISEDITABLE$ booleanValue ';'$
    isRemovableSpec ::= %empty | ISREMOVABLE$ booleanValue ';'$
    optLabelSpec    ::= %empty | LABEL$ STRING_LITERAL ';'$
    optToolTipSpec  ::= %empty | TOOLTIP$ STRING_LITERAL ';'$

    booleanSpecificSpec ::= booleanCustomSpec booleanDefValueSpec
    booleanCustomSpec   ::= booleanSpecialSpec
    booleanSpecialSpec  ::= %empty | HASSPECIAL$ booleanValue ';'$
    booleanDefValueSpec ::= %empty | DEFVALUE$ booleanValue ';'$


    comboSpecificSpec ::= comboCustomSpec comboDefValueSpec
    comboCustomSpec   ::= columnsSpec comboValuesSpec
    comboValuesSpec   ::= VALUES$ '{'$ labelledStringValueList '}'$
    comboDefValueSpec ::= %empty | DEFVALUE$ stringValue ';'$

    radioSpecificSpec ::= radioCustomSpec radioDefValueSpec
    radioCustomSpec   ::= columnsSpec radioValuesSpec
    radioDefValueSpec ::= %empty | DEFVALUE$ stringValue ';'$
    radioValuesSpec   ::= VALUES$ '{'$ labelledStringValueList '}'$

    columnsSpec       ::= %empty | COLUMNS$ NUMBER ';'$

    labelledStringValueList$$labelledStringValue ::=
        labelledStringValue | labelledStringValueList ','$ labelledStringValue
    labelledStringValue ::= identifier optLabelSpec


    fontSpecificSpec ::= fontDefValueSpec
    -- The following represents the information needed to construct a FontData object
    fontDefValueSpec ::= %empty | DEFVALUE$ stringValue$name NUMBER$height fontStyle$style ';'$
    fontStyle        ::= NORMAL | BOLD | ITALIC


    intSpecificSpec ::= intCustomSpec intDefValueSpec
    intCustomSpec   ::= intRangeSpec intSpecialSpec
    intRangeSpec    ::= %empty | RANGE$ signedNumber$low DOTS$ signedNumber$high ';'$
    intSpecialSpec  ::= %empty | HASSPECIAL$ signedNumber ';'$
    intDefValueSpec ::= %empty | DEFVALUE$ signedNumber ';'$


    stringSpecificSpec ::= stringCustomSpec stringDefValueSpec stringValidatorSpec
    stringCustomSpec   ::= stringSpecialSpec stringEmptySpec
    stringSpecialSpec  ::= %empty | HASSPECIAL$ stringValue ';'$
    stringEmptySpec    ::= %empty
                         | EMPTYALLOWED$ FALSE ';'$
                         | EMPTYALLOWED$ TRUE stringValue ';'$
    stringDefValueSpec ::= %empty | DEFVALUE$ stringValue ';'$
    stringValidatorSpec ::= %empty | VALIDATOR$ stringValue$qualClassName ';'$

    -- Rules for values and identifiers

    identifier   ::= IDENTIFIER

    booleanValue ::= TRUE | FALSE

    stringValue  ::= STRING_LITERAL

    signedNumber ::= NUMBER |  sign NUMBER

    sign ::= PLUS | MINUS
%End

%Headers
    /.

            // SMS 2 Apr 2007 custom code
            
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

        public void resolve($ast_type root) {
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
                                (fieldType.equals(RADIO_TYPE) && !(typeCustomSpecs instanceof radioSpecialSpec)) ||
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
                                 } else if (typeCustomSpecs instanceof radioSpecialSpec) {
                                    emitError((radioSpecialSpec)typeCustomSpecs, propertyMsg);
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
        
    ./
%End
