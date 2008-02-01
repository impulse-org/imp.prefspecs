%options package=org.eclipse.imp.prefspecs.parser
%options template=LexerTemplateF.gi
%options filter=PrefspecsKWLexer.gi

%Globals
    /.import java.util.*;
    import org.eclipse.imp.parser.ILexer;
    ./
%End

%Define
    $additional_interfaces /., ILexer./
    $kw_lexer_class /.$PrefspecsKWLexer./
%End

%Include
    LexerBasicMapF.gi
%End

%Export
    --
    -- List all the token types the lexer will directly process
    -- and export to the parser. If a keyword lexer is used as
    -- a filter for this lexer, it may export a set of keywords
    -- that will also be passed along to the parser.
    -- 
    -- For example:
    --
        SINGLE_LINE_COMMENT
        IDENTIFIER 
        NUMBER
        STRING_LITERAL
        --DoubleLiteral
        COMMA
        SEMICOLON
        PLUS
        MINUS
        LEFTPAREN
        RIGHTPAREN
        LEFTBRACE
        RIGHTBRACE
        DOTS
%End

%Terminals
    CtlCharNotWS

    LF   CR   HT   FF

    a    b    c    d    e    f    g    h    i    j    k    l    m
    n    o    p    q    r    s    t    u    v    w    x    y    z
    _

    A    B    C    D    E    F    G    H    I    J    K    L    M
    N    O    P    Q    R    S    T    U    V    W    X    Y    Z

    0    1    2    3    4    5    6    7    8    9

    AfterASCII   ::= '\u0080..\ufffe'
    Space        ::= ' '
    LF           ::= NewLine
    CR           ::= Return
    HT           ::= HorizontalTab
    FF           ::= FormFeed
    DoubleQuote  ::= '"'
    SingleQuote  ::= "'"
    Percent      ::= '%'
    VerticalBar  ::= '|'
    Exclamation  ::= '!'
    AtSign       ::= '@'
    BackQuote    ::= '`'
    Tilde        ::= '~'
    Sharp        ::= '#'
    DollarSign   ::= '$'
    Ampersand    ::= '&'
    Caret        ::= '^'
    Colon        ::= ':'
    SemiColon    ::= ';'
    BackSlash    ::= '\'
    LeftBrace    ::= '{'
    RightBrace   ::= '}'
    LeftBracket  ::= '['
    RightBracket ::= ']'
    QuestionMark ::= '?'
    Comma        ::= ','
    Dot          ::= '.'
    LessThan     ::= '<'
    GreaterThan  ::= '>'
    Plus         ::= '+'
    Minus        ::= '-'
    Slash        ::= '/'
    Star         ::= '*'
    LeftParen    ::= '('
    RightParen   ::= ')'
    Equal        ::= '='
%End

%Start
    Token
%End

%Notice
/.
// (C) Copyright IBM Corporation 2007
// 
// This file is part of the Eclipse IMP.
./
%End

%Rules
    Token ::= identifier
        /.$BeginJava
                    checkForKeyWord();
          $EndJava
        ./
    
    Token ::= number
        /.$BeginJava
                    makeToken($_NUMBER);
          $EndJava
        ./
    Token ::= stringliteral
        /.$BeginJava
               makeToken($_STRING_LITERAL);
          $EndJava
        ./
--    Token ::= DoubleLiteral
--        /.$BeginJava
--                    makeToken($_DoubleLiteral);
--          $EndJava
--        ./
    Token ::= white
        /.$BeginJava
                    skipToken();
          $EndJava
        ./
    Token ::= slc
        /.$BeginJava
                    makeComment($_SINGLE_LINE_COMMENT);
          $EndJava
        ./
    Token ::= ';'
        /.$BeginJava
                    makeToken($_SEMICOLON);
          $EndJava
        ./

    Token ::= ','
        /.$BeginJava
                    makeToken($_COMMA);
          $EndJava
        ./

    Token ::= '+'
        /.$BeginJava
                    makeToken($_PLUS);
          $EndJava
        ./

    Token ::= '-'
        /.$BeginJava
                    makeToken($_MINUS);
          $EndJava
        ./


    Token ::= '('
        /.$BeginJava
                    makeToken($_LEFTPAREN);
          $EndJava
        ./

    Token ::= ')'
        /.$BeginJava
                    makeToken($_RIGHTPAREN);
          $EndJava
        ./

    Token ::= '{'
        /.$BeginJava
                    makeToken($_LEFTBRACE);
          $EndJava
        ./

    Token ::= '}'
        /.$BeginJava
                    makeToken($_RIGHTBRACE);
          $EndJava
        ./	

    Token ::= '.' '.'
        /.$BeginJava
                    makeToken($_DOTS);
          $EndJava
        ./

    identifier -> letter
                | identifier letter
                | identifier digit

    number ::= digit
             | number digit


    stringliteral ::= singleQuoteString
    		|     doubleQuoteString
    
    doubleQuoteString ::= DoubleQuote doubleQuoteStringChars DoubleQuote
    
    doubleQuoteStringChars ::= %empty
    			| doubleQuoteStringChar
    			| doubleQuoteStringChars doubleQuoteStringChar
    
    doubleQuoteStringChar ::= letter | digit | Space | HT | specialNoDoubleQuote
    
    singleQuoteString ::= SingleQuote singleQuoteStringChars SingleQuote
    
    singleQuoteStringChars ::= %empty
    			| singleQuoteStringChar
    			| singleQuoteStringChars singleQuoteStringChar
    
    singleQuoteStringChar ::= letter | digit | Space | HT | specialNoSingleQuote

    white ::= whiteChar
            | white whiteChar

    slc ::= '/' '/'
          | slc notEOL

    digit ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9

    aA ::= a | A
    bB ::= b | B
    cC ::= c | C
    dD ::= d | D
    eE ::= e | E
    fF ::= f | F
    gG ::= g | G
    hH ::= h | H
    iI ::= i | I
    jJ ::= j | J
    kK ::= k | K
    lL ::= l | L
    mM ::= m | M
    nN ::= n | N
    oO ::= o | O
    pP ::= p | P
    qQ ::= q | Q
    rR ::= r | R
    sS ::= s | S
    tT ::= t | T
    uU ::= u | U
    vV ::= v | V
    wW ::= w | W
    xX ::= x | X
    yY ::= y | Y
    zZ ::= z | Z

    letter ::= aA | bB | cC | dD | eE | fF | gG | hH | iI | jJ | kK | lL | mM | nN | oO | pP | qQ | rR | sS | tT | uU | vV | wW | xX | yY | zZ

    -- any ::= letter | digit | special | white

    whiteChar ::= Space | LF | CR | HT | FF

    special ::= '+' | '-' | '(' | ')' | '"' | '!' | '@' | '`' | '~' | '.' |
                '%' | '&' | '^' | ':' | ';' | "'" | '\' | '|' | '{' | '}' |
                '[' | ']' | '?' | ',' | '<' | '>' | '=' | '#' | '*' | '_' |
                '/' | '$'
                
    specialNoDoubleQuote ::= '+' | '-' | '(' | ')' | '!' | '@' | '`' | '~' | '.' |
                '%' | '&' | '^' | ':' | ';' | "'" | '\' | '|' | '{' | '}' |
                '[' | ']' | '?' | ',' | '<' | '>' | '=' | '#' | '*' | '_' |
                '/' | '$'
                
    specialNoSingleQuote ::= '+' | '-' | '(' | ')' | '!' | '@' | '`' | '~' | '.' |
                '%' | '&' | '^' | ':' | ';' | '"' | '\' | '|' | '{' | '}' |
                '[' | ']' | '?' | ',' | '<' | '>' | '=' | '#' | '*' | '_' |
                '/' | '$'

    notEOL ::= letter | digit | special | Space | HT | FF
%End
