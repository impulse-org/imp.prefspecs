
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

public class PrefspecsKWLexer extends PrefspecsKWLexerprs
{
    private char[] inputChars;
    private final int keywordKind[] = new int[48 + 1];

    public int[] getKeywordKinds() { return keywordKind; }

    public int lexer(int curtok, int lasttok)
    {
        int current_kind = getKind(inputChars[curtok]),
            act;

        for (act = tAction(START_STATE, current_kind);
             act > NUM_RULES && act < ACCEPT_ACTION;
             act = tAction(act, current_kind))
        {
            curtok++;
            current_kind = (curtok > lasttok
                                   ? PrefspecsKWLexersym.Char_EOF
                                   : getKind(inputChars[curtok]));
        }

        if (act > ERROR_ACTION)
        {
            curtok++;
            act -= ERROR_ACTION;
        }

        return keywordKind[act == ERROR_ACTION  || curtok <= lasttok ? 0 : act];
    }

    public void setInputChars(char[] inputChars) { this.inputChars = inputChars; }


    final static int tokenKind[] = new int[128];
    static
    {
        tokenKind['$'] = PrefspecsKWLexersym.Char_DollarSign;
        tokenKind['%'] = PrefspecsKWLexersym.Char_Percent;
        tokenKind['_'] = PrefspecsKWLexersym.Char__;
        
        tokenKind['a'] = PrefspecsKWLexersym.Char_a;
        tokenKind['b'] = PrefspecsKWLexersym.Char_b;
        tokenKind['c'] = PrefspecsKWLexersym.Char_c;
        tokenKind['d'] = PrefspecsKWLexersym.Char_d;
        tokenKind['e'] = PrefspecsKWLexersym.Char_e;
        tokenKind['f'] = PrefspecsKWLexersym.Char_f;
        tokenKind['g'] = PrefspecsKWLexersym.Char_g;
        tokenKind['h'] = PrefspecsKWLexersym.Char_h;
        tokenKind['i'] = PrefspecsKWLexersym.Char_i;
        tokenKind['j'] = PrefspecsKWLexersym.Char_j;
        tokenKind['k'] = PrefspecsKWLexersym.Char_k;
        tokenKind['l'] = PrefspecsKWLexersym.Char_l;
        tokenKind['m'] = PrefspecsKWLexersym.Char_m;
        tokenKind['n'] = PrefspecsKWLexersym.Char_n;
        tokenKind['o'] = PrefspecsKWLexersym.Char_o;
        tokenKind['p'] = PrefspecsKWLexersym.Char_p;
        tokenKind['q'] = PrefspecsKWLexersym.Char_q;
        tokenKind['r'] = PrefspecsKWLexersym.Char_r;
        tokenKind['s'] = PrefspecsKWLexersym.Char_s;
        tokenKind['t'] = PrefspecsKWLexersym.Char_t;
        tokenKind['u'] = PrefspecsKWLexersym.Char_u;
        tokenKind['v'] = PrefspecsKWLexersym.Char_v;
        tokenKind['w'] = PrefspecsKWLexersym.Char_w;
        tokenKind['x'] = PrefspecsKWLexersym.Char_x;
        tokenKind['y'] = PrefspecsKWLexersym.Char_y;
        tokenKind['z'] = PrefspecsKWLexersym.Char_z;
    };

    final int getKind(int c)
    {
        return ((c & 0xFFFFFF80) == 0 /* 0 <= c < 128? */ ? tokenKind[c] : 0);
    }


    public PrefspecsKWLexer(char[] inputChars, int identifierKind)
    {
        this.inputChars = inputChars;
        keywordKind[0] = identifierKind;

        //
        // Rule 1:  Keyword ::= a g a i n s t
        //
        
        keywordKind[1] = (PrefspecsParsersym.TK_AGAINST);
      
    
        //
        // Rule 2:  Keyword ::= b o o l e a n
        //
        
        keywordKind[2] = (PrefspecsParsersym.TK_BOOLEAN);
      
    
        //
        // Rule 3:  Keyword ::= b o l d
        //
        
        keywordKind[3] = (PrefspecsParsersym.TK_BOLD);
      
    
        //
        // Rule 4:  Keyword ::= c h o i c e t y p e
        //
        
        keywordKind[4] = (PrefspecsParsersym.TK_CHOICETYPE);
      
    
        //
        // Rule 5:  Keyword ::= c o l o r
        //
        
        keywordKind[5] = (PrefspecsParsersym.TK_COLOR);
      
    
        //
        // Rule 6:  Keyword ::= c o l u m n s
        //
        
        keywordKind[6] = (PrefspecsParsersym.TK_COLUMNS);
      
    
        //
        // Rule 7:  Keyword ::= c o m b o
        //
        
        keywordKind[7] = (PrefspecsParsersym.TK_COMBO);
      
    
        //
        // Rule 8:  Keyword ::= c o n d i t i o n a l s
        //
        
        keywordKind[8] = (PrefspecsParsersym.TK_CONDITIONALS);
      
    
        //
        // Rule 9:  Keyword ::= c o n f i g u r a t i o n
        //
        
        keywordKind[9] = (PrefspecsParsersym.TK_CONFIGURATION);
      
    
        //
        // Rule 10:  Keyword ::= d e f a u l t
        //
        
        keywordKind[10] = (PrefspecsParsersym.TK_DEFAULT);
      
    
        //
        // Rule 11:  Keyword ::= d e f v a l u e
        //
        
        keywordKind[11] = (PrefspecsParsersym.TK_DEFVALUE);
      
    
        //
        // Rule 12:  Keyword ::= d e t a i l s
        //
        
        keywordKind[12] = (PrefspecsParsersym.TK_DETAILS);
      
    
        //
        // Rule 13:  Keyword ::= d i r e c t o r y
        //
        
        keywordKind[13] = (PrefspecsParsersym.TK_DIRECTORY);
      
    
        //
        // Rule 14:  Keyword ::= d i r l i s t
        //
        
        keywordKind[14] = (PrefspecsParsersym.TK_DIRLIST);
      
    
        //
        // Rule 15:  Keyword ::= d o u b l e
        //
        
        keywordKind[15] = (PrefspecsParsersym.TK_DOUBLE);
      
    
        //
        // Rule 16:  Keyword ::= d y n a m i c
        //
        
        keywordKind[16] = (PrefspecsParsersym.TK_DYNAMIC);
      
    
        //
        // Rule 17:  Keyword ::= e m p t y a l l o w e d
        //
        
        keywordKind[17] = (PrefspecsParsersym.TK_EMPTYALLOWED);
      
    
        //
        // Rule 18:  Keyword ::= f a l s e
        //
        
        keywordKind[18] = (PrefspecsParsersym.TK_FALSE);
      
    
        //
        // Rule 19:  Keyword ::= f i e l d s
        //
        
        keywordKind[19] = (PrefspecsParsersym.TK_FIELDS);
      
    
        //
        // Rule 20:  Keyword ::= f i l e
        //
        
        keywordKind[20] = (PrefspecsParsersym.TK_FILE);
      
    
        //
        // Rule 21:  Keyword ::= f o n t
        //
        
        keywordKind[21] = (PrefspecsParsersym.TK_FONT);
      
    
        //
        // Rule 22:  Keyword ::= g r o u p
        //
        
        keywordKind[22] = (PrefspecsParsersym.TK_GROUP);
      
    
        //
        // Rule 23:  Keyword ::= i f
        //
        
        keywordKind[23] = (PrefspecsParsersym.TK_IF);
      
    
        //
        // Rule 24:  Keyword ::= i n s t a n c e
        //
        
        keywordKind[24] = (PrefspecsParsersym.TK_INSTANCE);
      
    
        //
        // Rule 25:  Keyword ::= i n
        //
        
        keywordKind[25] = (PrefspecsParsersym.TK_IN);
      
    
        //
        // Rule 26:  Keyword ::= i n t
        //
        
        keywordKind[26] = (PrefspecsParsersym.TK_INT);
      
    
        //
        // Rule 27:  Keyword ::= i s e d i t a b l e
        //
        
        keywordKind[27] = (PrefspecsParsersym.TK_ISEDITABLE);
      
    
        //
        // Rule 28:  Keyword ::= i s r e m o v a b l e
        //
        
        keywordKind[28] = (PrefspecsParsersym.TK_ISREMOVABLE);
      
    
        //
        // Rule 29:  Keyword ::= i t a l i c
        //
        
        keywordKind[29] = (PrefspecsParsersym.TK_ITALIC);
      
    
        //
        // Rule 30:  Keyword ::= l a b e l
        //
        
        keywordKind[30] = (PrefspecsParsersym.TK_LABEL);
      
    
        //
        // Rule 31:  Keyword ::= n o r m a l
        //
        
        keywordKind[31] = (PrefspecsParsersym.TK_NORMAL);
      
    
        //
        // Rule 32:  Keyword ::= o n
        //
        
        keywordKind[32] = (PrefspecsParsersym.TK_ON);
      
    
        //
        // Rule 33:  Keyword ::= o f f
        //
        
        keywordKind[33] = (PrefspecsParsersym.TK_OFF);
      
    
        //
        // Rule 34:  Keyword ::= o u t
        //
        
        keywordKind[34] = (PrefspecsParsersym.TK_OUT);
      
    
        //
        // Rule 35:  Keyword ::= p a c k a g e
        //
        
        keywordKind[35] = (PrefspecsParsersym.TK_PACKAGE);
      
    
        //
        // Rule 36:  Keyword ::= p a g e
        //
        
        keywordKind[36] = (PrefspecsParsersym.TK_PAGE);
      
    
        //
        // Rule 37:  Keyword ::= p r o j e c t
        //
        
        keywordKind[37] = (PrefspecsParsersym.TK_PROJECT);
      
    
        //
        // Rule 38:  Keyword ::= r a d i o
        //
        
        keywordKind[38] = (PrefspecsParsersym.TK_RADIO);
      
    
        //
        // Rule 39:  Keyword ::= r a n g e
        //
        
        keywordKind[39] = (PrefspecsParsersym.TK_RANGE);
      
    
        //
        // Rule 40:  Keyword ::= s t r i n g
        //
        
        keywordKind[40] = (PrefspecsParsersym.TK_STRING);
      
    
        //
        // Rule 41:  Keyword ::= t a b s
        //
        
        keywordKind[41] = (PrefspecsParsersym.TK_TABS);
      
    
        //
        // Rule 42:  Keyword ::= t o o l t i p
        //
        
        keywordKind[42] = (PrefspecsParsersym.TK_TOOLTIP);
      
    
        //
        // Rule 43:  Keyword ::= t r u e
        //
        
        keywordKind[43] = (PrefspecsParsersym.TK_TRUE);
      
    
        //
        // Rule 44:  Keyword ::= t y p e
        //
        
        keywordKind[44] = (PrefspecsParsersym.TK_TYPE);
      
    
        //
        // Rule 45:  Keyword ::= u n l e s s
        //
        
        keywordKind[45] = (PrefspecsParsersym.TK_UNLESS);
      
    
        //
        // Rule 46:  Keyword ::= v a l u e s
        //
        
        keywordKind[46] = (PrefspecsParsersym.TK_VALUES);
      
    
        //
        // Rule 47:  Keyword ::= v a l i d a t o r
        //
        
        keywordKind[47] = (PrefspecsParsersym.TK_VALIDATOR);
      
    
        //
        // Rule 48:  Keyword ::= w i t h
        //
        
        keywordKind[48] = (PrefspecsParsersym.TK_WITH);
      
    
        for (int i = 0; i < keywordKind.length; i++)
        {
            if (keywordKind[i] == 0)
                keywordKind[i] = identifierKind;
        }
    }
}

