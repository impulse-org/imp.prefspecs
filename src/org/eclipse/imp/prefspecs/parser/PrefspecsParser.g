%options package=prefspecs.safari.parser
%options template=UIDE/dtParserTemplate.gi
%options import_terminals=PrefspecsLexer.gi
%options parent_saved,automatic_ast=toplevel,visitor=preorder,ast_directory=./Ast,ast_type=ASTNode
--
-- This is just a sample grammar and not a real grammar for prefspecs
--

$Globals
    /.import org.eclipse.uide.parser.IParser;
    import java.util.Hashtable;
    import java.util.Stack;
    ./
$End


$Define
    $ast_class /.Object./
    $additional_interfaces /., IParser./
$End	

$Terminals
    --            
    -- Here, you may list terminals needed by this grammar.
    -- Furthermore, a terminal may be mapped into an alias
    -- that can also be used in a grammar rule. In addition,
    -- when an alias is specified here it instructs the
    -- generated parser to use the alias in question when
    -- referring to the symbol to which it is aliased. For
    -- example, consider the following definitions:
    --
         IDENTIFIER
         NUMBER
         STRING_LITERAL
         DoubleLiteral
         COMMA ::= ','
         SEMICOLON ::= ';'
         PLUS ::= '+'
         MINUS ::= '-'
         LEFTPAREN ::= '('
         RIGHTPAREN ::= ')'
         LEFTBRACE ::= '{'
         RIGHTBRACE ::= '}'
    --
    -- Here the terminals int, float, identifier and NUMBER are
    -- defined without an alias; SEMICOLON is aliased to ';';
    -- PLUS is aliased to '+'... etc...
    --
    -- Note that the terminals that do not have aliases are declared
    -- above only for documentation purposes.
    --
$End

$Start
    pageSpec	
$End

$Recover
   MissingExpression
$End

$Rules
    -- In this section you list all the rules for your grammar.
    -- When reduced, each rule will produce one Ast node.
    -- 
    --  Here are some example rules:
    -- 
    identifier ::= IDENTIFIER
    
    pageSpec ::= $empty
		|  PAGE identifier '{' pageBody '}'
		
    pageBody ::= $empty
    		|  tabsSpec fieldsSpec optionalSpecs
    
    optionalSpecs ::= customSpecOption conditionalsSpecOption
    
    customSpecOption ::= $empty
    			| customSpec
	    			
	    			
    conditionalsSpecOption ::= $empty
	    		| conditionalsSpec
    		
    tabsSpec ::=  TABS '{' tabSpecs '}'
    
    tab ::= DEFAULT | CONFIGURATION | INSTANCE | PROJECT
    
    tabSpecs ::= $empty
    		|  defaultTabSpec configurationTabSpec instanceTabSpec projectTabSpec

    defaultTabSpec ::= DEFAULT inout '{' tabPropertySpecs '}'
    
    configurationTabSpec ::= CONFIGURATION inout '{' tabPropertySpecs '}'
    
    instanceTabSpec ::= INSTANCE inout '{' tabPropertySpecs '}'
    
    projectTabSpec ::= PROJECT inout '{' tabPropertySpecs '}'
    
    

    tabPropertySpecs ::= isEditableSpec isRemovableSpec
    
    isEditableSpec ::= $empty
    		    | ISEDITABLE booleanValue ';'    

    inout ::= IN | OUT
    
    fieldsSpec ::= FIELDS '{' fieldSpecs '}'
    
    fieldSpecs ::= $empty
    		| fieldSpec
    		| fieldSpecs fieldSpec
    		
    fieldSpec ::= booleanFieldSpec
    		| comboFieldSpec
    		| dirListFieldSpec
    		| fileFieldSpec
    		| intFieldSpec
    		| radioFieldSpec
    		| stringFieldSpec
    		

    booleanFieldSpec ::= BOOLEAN identifier '{' booleanFieldPropertySpecs '}'

    booleanSpecialSpec ::= $empty
    			|  HASSPECIAL booleanValue ';'
    			
    isRemovableSpec ::= $empty
    			|  ISREMOVABLE booleanValue ';'

    comboFieldSpec ::= COMBO identifier '{' comboFieldPropertySpecs '}'

    comboSpecialSpec ::= $empty
    			| HASSPECIAL stringValue ';'
      
    dirListFieldSpec ::= DIRLIST identifier '{' dirlistFieldPropertySpecs '}'
    
    dirlistSpecialSpec ::= $empty
    			| HASSPECIAL stringValue ';'
    			
    stringEmptySpec ::= $empty
    			| EMPTYALLOWED FALSE ';'
    			| EMPTYALLOWED TRUE stringValue ';'
    
    fileFieldSpec ::= FILE identifier '{' fileFieldPropertySpecs '}'

    
    fileSpecialSpec ::= $empty
    			| HASSPECIAL stringValue ';'
    
    intFieldSpec ::= INT identifier '{' intFieldPropertySpecs '}'
    		  
    intRangeSpec ::= $empty
    		|    RANGE signedNumber DOTS signedNumber ';'
    		
    intSpecialSpec ::= $empty
    			| HASSPECIAL signedNumber ';'
    
    signedNumber ::= NUMBER
    		  |  sign NUMBER
    
    sign ::= PLUS | MINUS
    		  
    radioFieldSpec ::= RADIO identifier '{' radioFieldPropertySpecs '}'
    
    radioSpecialSpec ::= $empty
    			| HASSPECIAL NUMBER ';'
    
    stringFieldSpec ::= STRING identifier '{' stringFieldPropertySpecs '}'
    
    stringSpecialSpec ::= $empty
    			| HASSPECIAL stringValue ';'
     
    customSpec ::= CUSTOM '{' customRules '}'

    customRules ::= $empty
    		   |  customRule
    		   |  customRules customRule
    
    customRule ::= tab identifier '{' fieldPropertySpecs '}'

    
    fieldPropertySpecs ::= booleanFieldPropertySpecs
    			|  comboFieldPropertySpecs
    			|  dirlistFieldPropertySpecs
    			|  fileFieldPropertySpecs
    			|  intFieldPropertySpecs
    			|  radioFieldPropertySpecs
    			|  stringFieldPropertySpecs

    booleanFieldPropertySpecs ::= isEditableSpec booleanSpecialSpec isRemovableSpec

    comboFieldPropertySpecs ::= isEditableSpec comboSpecialSpec stringEmptySpec isRemovableSpec
    
    dirlistFieldPropertySpecs ::= isEditableSpec dirlistSpecialSpec stringEmptySpec isRemovableSpec
    
    fileFieldPropertySpecs ::= isEditableSpec fileSpecialSpec stringEmptySpec isRemovableSpec
    
    intFieldPropertySpecs ::= isEditableSpec intRangeSpec intSpecialSpec isRemovableSpec
    
    radioFieldPropertySpecs ::= isEditableSpec radioSpecialSpec isRemovableSpec
    
    stringFieldPropertySpecs ::= isEditableSpec stringSpecialSpec stringEmptySpec isRemovableSpec
       
    booleanValue ::= TRUE | FALSE
    
    stringValue ::= STRING_LITERAL

    conditionalsSpec ::= CONDITIONALS '{' conditionalSpecs '}'
    
    conditionalSpecs ::= $empty
    			| conditionalSpec ;
    			| conditionalSpecs conditionalSpec ;
    			
    conditionalSpec ::= identifier WITH identifier
    			| identifier AGAINST identifier


$End

$Headers
    /.
    
  
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
            if (root != null) {
                symbolTableStack = new Stack();
                topLevelSymbolTable = new SymbolTable(null);
                symbolTableStack.push(topLevelSymbolTable);
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
                getMessageHandler().handleMessage(ParseErrorCodes.NO_MESSAGE_CODE,
                                                  getLexStream().getLocation(id.getStartOffset(), id.getEndOffset()),
                                                  getLexStream().getLocation(0, 0),
                                                  getFileName(),
                                                  new String [] { message });
            }
                        
 /*
            public boolean visit(block n) {
                n.setSymbolTable((SymbolTable) symbolTableStack.push(new SymbolTable((SymbolTable) symbolTableStack.peek())));
                return true;
            }

            public void endVisit(block n) { symbolTableStack.pop(); }

            public boolean visit(functionDeclaration n) {
                IToken id = n.getIdentifier().getIToken();
                SymbolTable symbol_table = (SymbolTable) symbolTableStack.peek();
                if (symbol_table.get(id.toString()) == null)
                     symbol_table.put(id.toString(), n);
                else emitError(id, "Illegal redeclaration of " + id.toString());

                //
                // Add a symbol table for the parameters
                //
                n.setSymbolTable((SymbolTable) symbolTableStack.push(new SymbolTable((SymbolTable) symbolTableStack.peek())));

                return true;
            }
            
            public void endVisit(functionDeclaration n) { symbolTableStack.pop(); }

            public boolean visit(declaration n) {
                IToken id = n.getIdentifier().getIToken();
                SymbolTable symbol_table = (SymbolTable) symbolTableStack.peek();
                if (symbol_table.get(id.toString()) == null)
                     symbol_table.put(id.toString(), n);
                else emitError(id, "Illegal redeclaration of " + id.toString());
                return true;
            }

            public boolean visit(identifier n) {
                IToken id = n.getIdentifier();
                IAst decl = ((SymbolTable) symbolTableStack.peek()).findDeclaration(id.toString());
                if (decl == null)
                     emitError(id, "Undeclared variable " + id.toString());
                else n.setDeclaration(decl);
                return true;
            }
*/
        } // End SymbolTableVisitor
    ./
$End
