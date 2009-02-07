
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

package org.eclipse.imp.prefspecs.parser;

import lpg.runtime.*;
import java.util.*;
import org.eclipse.imp.parser.ILexer;

public class PrefspecsLexer implements RuleAction, ILexer
{
    private PrefspecsLexerLpgLexStream lexStream;
    
    private static ParseTable prs = new PrefspecsLexerprs();
    public ParseTable getParseTable() { return prs; }

    private LexParser lexParser = new LexParser();
    public LexParser getParser() { return lexParser; }

    public int getToken(int i) { return lexParser.getToken(i); }
    public int getRhsFirstTokenIndex(int i) { return lexParser.getFirstToken(i); }
    public int getRhsLastTokenIndex(int i) { return lexParser.getLastToken(i); }

    public int getLeftSpan() { return lexParser.getToken(1); }
    public int getRightSpan() { return lexParser.getLastToken(); }

    public void resetKeywordLexer()
    {
        if (kwLexer == null)
              this.kwLexer = new PrefspecsKWLexer(lexStream.getInputChars(), PrefspecsParsersym.TK_IDENTIFIER);
        else this.kwLexer.setInputChars(lexStream.getInputChars());
    }

    public void reset(String filename, int tab) throws java.io.IOException
    {
        lexStream = new PrefspecsLexerLpgLexStream(filename, tab);
        lexParser.reset((ILexStream) lexStream, prs, (RuleAction) this);
        resetKeywordLexer();
    }

    public void reset(char[] input_chars, String filename)
    {
        reset(input_chars, filename, 1);
    }
    
    public void reset(char[] input_chars, String filename, int tab)
    {
        lexStream = new PrefspecsLexerLpgLexStream(input_chars, filename, tab);
        lexParser.reset((ILexStream) lexStream, prs, (RuleAction) this);
        resetKeywordLexer();
    }
    
    public PrefspecsLexer(String filename, int tab) throws java.io.IOException 
    {
        reset(filename, tab);
    }

    public PrefspecsLexer(char[] input_chars, String filename, int tab)
    {
        reset(input_chars, filename, tab);
    }

    public PrefspecsLexer(char[] input_chars, String filename)
    {
        reset(input_chars, filename, 1);
    }

    public PrefspecsLexer() {}

    public ILexStream getILexStream() { return lexStream; }

    /**
     * @deprecated replaced by {@link #getILexStream()}
     */
    public ILexStream getLexStream() { return lexStream; }

    private void initializeLexer(IPrsStream prsStream, int start_offset, int end_offset)
    {
        if (lexStream.getInputChars() == null)
            throw new NullPointerException("LexStream was not initialized");
        lexStream.setPrsStream(prsStream);
        prsStream.makeToken(start_offset, end_offset, 0); // Token list must start with a bad token
    }

    private void addEOF(IPrsStream prsStream, int end_offset)
    {
        prsStream.makeToken(end_offset, end_offset, PrefspecsParsersym.TK_EOF_TOKEN); // and end with the end of file token
        prsStream.setStreamLength(prsStream.getSize());
    }

    public void lexer(IPrsStream prsStream)
    {
        lexer(null, prsStream);
    }
    
    public void lexer(Monitor monitor, IPrsStream prsStream)
    {
        initializeLexer(prsStream, 0, -1);
        lexParser.parseCharacters(monitor);  // Lex the input characters
        addEOF(prsStream, lexStream.getStreamIndex());
    }

    public void lexer(IPrsStream prsStream, int start_offset, int end_offset)
    {
        lexer(null, prsStream, start_offset, end_offset);
    }
    
    public void lexer(Monitor monitor, IPrsStream prsStream, int start_offset, int end_offset)
    {
        if (start_offset <= 1)
             initializeLexer(prsStream, 0, -1);
        else initializeLexer(prsStream, start_offset - 1, start_offset - 1);

        lexParser.parseCharacters(monitor, start_offset, end_offset);

        addEOF(prsStream, (end_offset >= lexStream.getStreamIndex() ? lexStream.getStreamIndex() : end_offset + 1));
    }

    /**
     * If a parse stream was not passed to this Lexical analyser then we
     * simply report a lexical error. Otherwise, we produce a bad token.
     */
    public void reportLexicalError(int startLoc, int endLoc) {
        IPrsStream prs_stream = lexStream.getPrsStream();
        if (prs_stream == null)
            lexStream.reportLexicalError(startLoc, endLoc);
        else {
            //
            // Remove any token that may have been processed that fall in the
            // range of the lexical error... then add one error token that spans
            // the error range.
            //
            for (int i = prs_stream.getSize() - 1; i > 0; i--) {
                if (prs_stream.getStartOffset(i) >= startLoc)
                     prs_stream.removeLastToken();
                else break;
            }
            prs_stream.makeToken(startLoc, endLoc, 0); // add an error token to the prsStream
        }        
    }

    //
    // The Lexer contains an array of characters as the input stream to be parsed.
    // There are methods to retrieve and classify characters.
    // The lexparser "token" is implemented simply as the index of the next character in the array.
    // The Lexer extends the abstract class LpgLexStream with an implementation of the abstract
    // method getKind.  The template defines the Lexer class and the lexer() method.
    // A driver creates the action class, "Lexer", passing an Option object to the constructor.
    //
    PrefspecsKWLexer kwLexer;
    boolean printTokens;
    private final static int ECLIPSE_TAB_VALUE = 4;

    public int [] getKeywordKinds() { return kwLexer.getKeywordKinds(); }

    public PrefspecsLexer(String filename) throws java.io.IOException
    {
        this(filename, ECLIPSE_TAB_VALUE);
        this.kwLexer = new PrefspecsKWLexer(lexStream.getInputChars(), PrefspecsParsersym.TK_IDENTIFIER);
    }

    /**
     * @deprecated function replaced by {@link #reset(char [] content, String filename)}
     */
    public void initialize(char [] content, String filename)
    {
        reset(content, filename);
    }
    
    final void makeToken(int left_token, int right_token, int kind)
    {
        lexStream.makeToken(left_token, right_token, kind);
    }
    
    final void makeToken(int kind)
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan();
        lexStream.makeToken(startOffset, endOffset, kind);
        if (printTokens) printValue(startOffset, endOffset);
    }

    final void makeComment(int kind)
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan();
        lexStream.getIPrsStream().makeAdjunct(startOffset, endOffset, kind);
    }

    final void skipToken()
    {
        if (printTokens) printValue(getLeftSpan(), getRightSpan());
    }
    
    final void checkForKeyWord()
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan(),
            kwKind = kwLexer.lexer(startOffset, endOffset);
        lexStream.makeToken(startOffset, endOffset, kwKind);
        if (printTokens) printValue(startOffset, endOffset);
    }
    
    //
    // This flavor of checkForKeyWord is necessary when the default kind
    // (which is returned when the keyword filter doesn't match) is something
    // other than _IDENTIFIER.
    //
    final void checkForKeyWord(int defaultKind)
    {
        int startOffset = getLeftSpan(),
            endOffset = getRightSpan(),
            kwKind = kwLexer.lexer(startOffset, endOffset);
        if (kwKind == PrefspecsParsersym.TK_IDENTIFIER)
            kwKind = defaultKind;
        lexStream.makeToken(startOffset, endOffset, kwKind);
        if (printTokens) printValue(startOffset, endOffset);
    }
    
    final void printValue(int startOffset, int endOffset)
    {
        String s = new String(lexStream.getInputChars(), startOffset, endOffset - startOffset + 1);
        System.out.print(s);
    }

    //
    //
    //
    static class PrefspecsLexerLpgLexStream extends LpgLexStream
    {
    public final static int tokenKind[] =
    {
        PrefspecsLexersym.Char_CtlCharNotWS,    // 000    0x00
        PrefspecsLexersym.Char_CtlCharNotWS,    // 001    0x01
        PrefspecsLexersym.Char_CtlCharNotWS,    // 002    0x02
        PrefspecsLexersym.Char_CtlCharNotWS,    // 003    0x03
        PrefspecsLexersym.Char_CtlCharNotWS,    // 004    0x04
        PrefspecsLexersym.Char_CtlCharNotWS,    // 005    0x05
        PrefspecsLexersym.Char_CtlCharNotWS,    // 006    0x06
        PrefspecsLexersym.Char_CtlCharNotWS,    // 007    0x07
        PrefspecsLexersym.Char_CtlCharNotWS,    // 008    0x08
        PrefspecsLexersym.Char_HT,              // 009    0x09
        PrefspecsLexersym.Char_LF,              // 010    0x0A
        PrefspecsLexersym.Char_CtlCharNotWS,    // 011    0x0B
        PrefspecsLexersym.Char_FF,              // 012    0x0C
        PrefspecsLexersym.Char_CR,              // 013    0x0D
        PrefspecsLexersym.Char_CtlCharNotWS,    // 014    0x0E
        PrefspecsLexersym.Char_CtlCharNotWS,    // 015    0x0F
        PrefspecsLexersym.Char_CtlCharNotWS,    // 016    0x10
        PrefspecsLexersym.Char_CtlCharNotWS,    // 017    0x11
        PrefspecsLexersym.Char_CtlCharNotWS,    // 018    0x12
        PrefspecsLexersym.Char_CtlCharNotWS,    // 019    0x13
        PrefspecsLexersym.Char_CtlCharNotWS,    // 020    0x14
        PrefspecsLexersym.Char_CtlCharNotWS,    // 021    0x15
        PrefspecsLexersym.Char_CtlCharNotWS,    // 022    0x16
        PrefspecsLexersym.Char_CtlCharNotWS,    // 023    0x17
        PrefspecsLexersym.Char_CtlCharNotWS,    // 024    0x18
        PrefspecsLexersym.Char_CtlCharNotWS,    // 025    0x19
        PrefspecsLexersym.Char_CtlCharNotWS,    // 026    0x1A
        PrefspecsLexersym.Char_CtlCharNotWS,    // 027    0x1B
        PrefspecsLexersym.Char_CtlCharNotWS,    // 028    0x1C
        PrefspecsLexersym.Char_CtlCharNotWS,    // 029    0x1D
        PrefspecsLexersym.Char_CtlCharNotWS,    // 030    0x1E
        PrefspecsLexersym.Char_CtlCharNotWS,    // 031    0x1F
        PrefspecsLexersym.Char_Space,           // 032    0x20
        PrefspecsLexersym.Char_Exclamation,     // 033    0x21
        PrefspecsLexersym.Char_DoubleQuote,     // 034    0x22
        PrefspecsLexersym.Char_Sharp,           // 035    0x23
        PrefspecsLexersym.Char_DollarSign,      // 036    0x24
        PrefspecsLexersym.Char_Percent,         // 037    0x25
        PrefspecsLexersym.Char_Ampersand,       // 038    0x26
        PrefspecsLexersym.Char_SingleQuote,     // 039    0x27
        PrefspecsLexersym.Char_LeftParen,       // 040    0x28
        PrefspecsLexersym.Char_RightParen,      // 041    0x29
        PrefspecsLexersym.Char_Star,            // 042    0x2A
        PrefspecsLexersym.Char_Plus,            // 043    0x2B
        PrefspecsLexersym.Char_Comma,           // 044    0x2C
        PrefspecsLexersym.Char_Minus,           // 045    0x2D
        PrefspecsLexersym.Char_Dot,             // 046    0x2E
        PrefspecsLexersym.Char_Slash,           // 047    0x2F
        PrefspecsLexersym.Char_0,               // 048    0x30
        PrefspecsLexersym.Char_1,               // 049    0x31
        PrefspecsLexersym.Char_2,               // 050    0x32
        PrefspecsLexersym.Char_3,               // 051    0x33
        PrefspecsLexersym.Char_4,               // 052    0x34
        PrefspecsLexersym.Char_5,               // 053    0x35
        PrefspecsLexersym.Char_6,               // 054    0x36
        PrefspecsLexersym.Char_7,               // 055    0x37
        PrefspecsLexersym.Char_8,               // 056    0x38
        PrefspecsLexersym.Char_9,               // 057    0x39
        PrefspecsLexersym.Char_Colon,           // 058    0x3A
        PrefspecsLexersym.Char_SemiColon,       // 059    0x3B
        PrefspecsLexersym.Char_LessThan,        // 060    0x3C
        PrefspecsLexersym.Char_Equal,           // 061    0x3D
        PrefspecsLexersym.Char_GreaterThan,     // 062    0x3E
        PrefspecsLexersym.Char_QuestionMark,    // 063    0x3F
        PrefspecsLexersym.Char_AtSign,          // 064    0x40
        PrefspecsLexersym.Char_A,               // 065    0x41
        PrefspecsLexersym.Char_B,               // 066    0x42
        PrefspecsLexersym.Char_C,               // 067    0x43
        PrefspecsLexersym.Char_D,               // 068    0x44
        PrefspecsLexersym.Char_E,               // 069    0x45
        PrefspecsLexersym.Char_F,               // 070    0x46
        PrefspecsLexersym.Char_G,               // 071    0x47
        PrefspecsLexersym.Char_H,               // 072    0x48
        PrefspecsLexersym.Char_I,               // 073    0x49
        PrefspecsLexersym.Char_J,               // 074    0x4A
        PrefspecsLexersym.Char_K,               // 075    0x4B
        PrefspecsLexersym.Char_L,               // 076    0x4C
        PrefspecsLexersym.Char_M,               // 077    0x4D
        PrefspecsLexersym.Char_N,               // 078    0x4E
        PrefspecsLexersym.Char_O,               // 079    0x4F
        PrefspecsLexersym.Char_P,               // 080    0x50
        PrefspecsLexersym.Char_Q,               // 081    0x51
        PrefspecsLexersym.Char_R,               // 082    0x52
        PrefspecsLexersym.Char_S,               // 083    0x53
        PrefspecsLexersym.Char_T,               // 084    0x54
        PrefspecsLexersym.Char_U,               // 085    0x55
        PrefspecsLexersym.Char_V,               // 086    0x56
        PrefspecsLexersym.Char_W,               // 087    0x57
        PrefspecsLexersym.Char_X,               // 088    0x58
        PrefspecsLexersym.Char_Y,               // 089    0x59
        PrefspecsLexersym.Char_Z,               // 090    0x5A
        PrefspecsLexersym.Char_LeftBracket,     // 091    0x5B
        PrefspecsLexersym.Char_BackSlash,       // 092    0x5C
        PrefspecsLexersym.Char_RightBracket,    // 093    0x5D
        PrefspecsLexersym.Char_Caret,           // 094    0x5E
        PrefspecsLexersym.Char__,               // 095    0x5F
        PrefspecsLexersym.Char_BackQuote,       // 096    0x60
        PrefspecsLexersym.Char_a,               // 097    0x61
        PrefspecsLexersym.Char_b,               // 098    0x62
        PrefspecsLexersym.Char_c,               // 099    0x63
        PrefspecsLexersym.Char_d,               // 100    0x64
        PrefspecsLexersym.Char_e,               // 101    0x65
        PrefspecsLexersym.Char_f,               // 102    0x66
        PrefspecsLexersym.Char_g,               // 103    0x67
        PrefspecsLexersym.Char_h,               // 104    0x68
        PrefspecsLexersym.Char_i,               // 105    0x69
        PrefspecsLexersym.Char_j,               // 106    0x6A
        PrefspecsLexersym.Char_k,               // 107    0x6B
        PrefspecsLexersym.Char_l,               // 108    0x6C
        PrefspecsLexersym.Char_m,               // 109    0x6D
        PrefspecsLexersym.Char_n,               // 110    0x6E
        PrefspecsLexersym.Char_o,               // 111    0x6F
        PrefspecsLexersym.Char_p,               // 112    0x70
        PrefspecsLexersym.Char_q,               // 113    0x71
        PrefspecsLexersym.Char_r,               // 114    0x72
        PrefspecsLexersym.Char_s,               // 115    0x73
        PrefspecsLexersym.Char_t,               // 116    0x74
        PrefspecsLexersym.Char_u,               // 117    0x75
        PrefspecsLexersym.Char_v,               // 118    0x76
        PrefspecsLexersym.Char_w,               // 119    0x77
        PrefspecsLexersym.Char_x,               // 120    0x78
        PrefspecsLexersym.Char_y,               // 121    0x79
        PrefspecsLexersym.Char_z,               // 122    0x7A
        PrefspecsLexersym.Char_LeftBrace,       // 123    0x7B
        PrefspecsLexersym.Char_VerticalBar,     // 124    0x7C
        PrefspecsLexersym.Char_RightBrace,      // 125    0x7D
        PrefspecsLexersym.Char_Tilde,           // 126    0x7E

        PrefspecsLexersym.Char_AfterASCII,      // for all chars in range 128..65534
        PrefspecsLexersym.Char_EOF              // for '\uffff' or 65535 
    };
            
    public final int getKind(int i)  // Classify character at ith location
    {
        int c = (i >= getStreamLength() ? '\uffff' : getCharValue(i));
        return (c < 128 // ASCII Character
                  ? tokenKind[c]
                  : c == '\uffff'
                       ? PrefspecsLexersym.Char_EOF
                       : PrefspecsLexersym.Char_AfterASCII);
    }

    public String[] orderedExportedSymbols() { return PrefspecsParsersym.orderedTerminalSymbols; }

    public PrefspecsLexerLpgLexStream(String filename, int tab) throws java.io.IOException
    {
        super(filename, tab);
    }

    public PrefspecsLexerLpgLexStream(char[] input_chars, String filename, int tab)
    {
        super(input_chars, filename, tab);
    }

    public PrefspecsLexerLpgLexStream(char[] input_chars, String filename)
    {
        super(input_chars, filename, 1);
    }
    }

    public void ruleAction(int ruleNumber)
    {
        switch(ruleNumber)
        {

            //
            // Rule 1:  Token ::= identifier
            //
            case 1: { 
            
                checkForKeyWord();
                  break;
            }
    
            //
            // Rule 2:  Token ::= integer
            //
            case 2: { 
            
                makeToken(PrefspecsParsersym.TK_INTEGER);
                  break;
            }
    
            //
            // Rule 3:  Token ::= decimal
            //
            case 3: { 
            
                makeToken(PrefspecsParsersym.TK_DECIMAL);
                  break;
            }
    
            //
            // Rule 4:  Token ::= stringliteral
            //
            case 4: { 
            
           makeToken(PrefspecsParsersym.TK_STRING_LITERAL);
                  break;
            }
    
            //
            // Rule 5:  Token ::= white
            //
            case 5: { 
            
                skipToken();
                  break;
            }
    
            //
            // Rule 6:  Token ::= slc
            //
            case 6: { 
            
                makeComment(PrefspecsParsersym.TK_SINGLE_LINE_COMMENT);
                  break;
            }
    
            //
            // Rule 7:  Token ::= ;
            //
            case 7: { 
            
                makeToken(PrefspecsParsersym.TK_SEMICOLON);
                  break;
            }
    
            //
            // Rule 8:  Token ::= ,
            //
            case 8: { 
            
                makeToken(PrefspecsParsersym.TK_COMMA);
                  break;
            }
    
            //
            // Rule 9:  Token ::= +
            //
            case 9: { 
            
                makeToken(PrefspecsParsersym.TK_PLUS);
                  break;
            }
    
            //
            // Rule 10:  Token ::= -
            //
            case 10: { 
            
                makeToken(PrefspecsParsersym.TK_MINUS);
                  break;
            }
    
            //
            // Rule 11:  Token ::= (
            //
            case 11: { 
            
                makeToken(PrefspecsParsersym.TK_LEFTPAREN);
                  break;
            }
    
            //
            // Rule 12:  Token ::= )
            //
            case 12: { 
            
                makeToken(PrefspecsParsersym.TK_RIGHTPAREN);
                  break;
            }
    
            //
            // Rule 13:  Token ::= {
            //
            case 13: { 
            
                makeToken(PrefspecsParsersym.TK_LEFTBRACE);
                  break;
            }
    
            //
            // Rule 14:  Token ::= }
            //
            case 14: { 
            
                makeToken(PrefspecsParsersym.TK_RIGHTBRACE);
                  break;
            }
    
            //
            // Rule 15:  Token ::= .
            //
            case 15: { 
            
                makeToken(PrefspecsParsersym.TK_DOT);
                  break;
            }
    
            //
            // Rule 16:  Token ::= . .
            //
            case 16: { 
            
                makeToken(PrefspecsParsersym.TK_DOTS);
                  break;
            }
    
    
            default:
                break;
        }
        return;
    }
}

