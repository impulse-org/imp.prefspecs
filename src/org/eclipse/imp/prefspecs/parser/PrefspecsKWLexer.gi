--
-- The Java KeyWord Lexer
--
%options package=org.eclipse.imp.prefspecs.parser
%options template=KeywordTemplate.gi

%Include
    KWLexerLowerCaseMap.gi
%End

%Export

    -- List all the keywords the kwlexer will export to the lexer and parser

    -- For parts of specs
    PACKAGE
    PAGE
    TABS
    FIELDS
    CONDITIONALS
    CUSTOM

    -- For tab types
    DEFAULT
    CONFIGURATION
    INSTANCE
    PROJECT
    
    -- For tab inclusion
    IN
    OUT
    
    -- For field types
    -- commented types not available for pages
    BOOLEAN
    -- bytes -- byte array
    COMBO
    -- double
    DIRLIST
    FILE
    -- float
    INT
    RADIO
    STRING
    
    -- For field specifications
    --SPECIAL
    
    -- For attributes
    LABEL
    DEFVALUE
    EMPTYALLOWED
    HASSPECIAL
    ISEDITABLE
    ISREMOVABLE
    RANGE
    
    -- For boolean properties of fields (or anything else)
    TRUE
    FALSE
    
    -- For conditionals
    WITH
    AGAINST
    IF
    UNLESS
%End

%Terminals
    a    b    c    d    e    f    g    h    i    j    k    l    m
    n    o    p    q    r    s    t    u    v    w    x    y    z
%End

%Start
    Keyword
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
//    Philippe Charles (pcharles@us.ibm.com) - initial API and implementation

////////////////////////////////////////////////////////////////////////////////
./
%End

%Rules

    -- The Goal for the parser is a single Keyword

    Keyword ::= a g a i n s t
        /.$BeginAction
            $setResult($_AGAINST);
          $EndAction
        ./

    Keyword ::= b o o l e a n
        /.$BeginAction
            $setResult($_BOOLEAN);
          $EndAction
        ./

    Keyword ::= c o m b o
        /.$BeginAction
            $setResult($_COMBO);
          $EndAction
        ./
        
    Keyword ::= c o n d i t i o n a l s
        /.$BeginAction
            $setResult($_CONDITIONALS);
          $EndAction
        ./

    Keyword ::= c o n f i g u r a t i o n
        /.$BeginAction
            $setResult($_CONFIGURATION);
          $EndAction
        ./

    Keyword ::= c u s t o m
        /.$BeginAction
            $setResult($_CUSTOM);
          $EndAction
        ./

    Keyword ::= d e f a u l t
        /.$BeginAction
            $setResult($_DEFAULT);
          $EndAction
        ./

    Keyword ::= d e f v a l u e
        /.$BeginAction
            $setResult($_DEFVALUE);
          $EndAction
        ./

    Keyword ::= d i r l i s t
        /.$BeginAction
            $setResult($_DIRLIST);
          $EndAction
        ./

    Keyword ::= e m p t y a l l o w e d
        /.$BeginAction
            $setResult($_EMPTYALLOWED);
          $EndAction
        ./

    Keyword ::= f a l s e
        /.$BeginAction
            $setResult($_FALSE);
          $EndAction
        ./

    Keyword ::= f i e l d s
        /.$BeginAction
            $setResult($_FIELDS);
          $EndAction
        ./

    Keyword ::= f i l e
        /.$BeginAction
            $setResult($_FILE);
          $EndAction
        ./

    Keyword ::= h a s s p e c i a l
        /.$BeginAction
            $setResult($_HASSPECIAL);
          $EndAction
        ./

    Keyword ::= i f
        /.$BeginAction
            $setResult($_IF);
          $EndAction
        ./

    Keyword ::= i n s t a n c e
        /.$BeginAction
            $setResult($_INSTANCE);
          $EndAction
        ./

    Keyword ::= i n
        /.$BeginAction
            $setResult($_IN);
          $EndAction
        ./

    Keyword ::= i n t
        /.$BeginAction
            $setResult($_INT);
          $EndAction
        ./
        
    Keyword ::= i s e d i t a b l e
        /.$BeginAction
            $setResult($_ISEDITABLE);
          $EndAction
        ./    
        
    Keyword ::= i s r e m o v a b l e
        /.$BeginAction
            $setResult($_ISREMOVABLE);
          $EndAction
        ./

    Keyword ::= l a b e l
        /.$BeginAction
            $setResult($_LABEL);
          $EndAction
        ./

    Keyword ::= o u t
        /.$BeginAction
            $setResult($_OUT);
          $EndAction
        ./

    Keyword ::= p a c k a g e
        /.$BeginAction
            $setResult($_PACKAGE);
          $EndAction
        ./

    Keyword ::= p a g e
        /.$BeginAction
            $setResult($_PAGE);
          $EndAction
        ./

    Keyword ::= p r o j e c t
        /.$BeginAction
            $setResult($_PROJECT);
          $EndAction
        ./
        
    Keyword ::= r a d i o
        /.$BeginAction
            $setResult($_RADIO);
          $EndAction
        ./
        
    Keyword ::= r a n g e
        /.$BeginAction
            $setResult($_RANGE);
          $EndAction
        ./

    --Keyword ::= s p e c i a l
    --    /.$BeginAction
    --        $setResult($_SPECIAL);
    --      $EndAction
    --    ./

    Keyword ::= s t r i n g
        /.$BeginAction
            $setResult($_STRING);
          $EndAction
        ./    

    Keyword ::= t a b s
        /.$BeginAction
            $setResult($_TABS);
          $EndAction
        ./

    Keyword ::= t r u e
        /.$BeginAction
            $setResult($_TRUE);
          $EndAction
        ./

    Keyword ::= u n l e s s
        /.$BeginAction
            $setResult($_UNLESS);
          $EndAction
        ./

    Keyword ::= w i t h
        /.$BeginAction
            $setResult($_WITH);
          $EndAction
        ./

%End
