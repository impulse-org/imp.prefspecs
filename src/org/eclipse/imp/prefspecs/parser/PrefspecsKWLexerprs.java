
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

public class PrefspecsKWLexerprs implements lpg.runtime.ParseTable, PrefspecsKWLexersym {
    public final static int ERROR_SYMBOL = 0;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = 0;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 0;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 0;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 200;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 30;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 298;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 47;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 2;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 32;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 48;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 24;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 31;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 250;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 251;
    public final int getErrorAction() { return ERROR_ACTION; }

    public final static boolean BACKTRACK = false;
    public final boolean getBacktrack() { return BACKTRACK; }

    public final int getStartSymbol() { return lhs(0); }
    public final boolean isValidForParser() { return PrefspecsKWLexersym.isValidForParser; }


    public interface IsNullable {
        public final static byte isNullable[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            2,1
        };
    };
    public final static byte prosthesesIndex[] = ProsthesesIndex.prosthesesIndex;
    public final int prosthesesIndex(int index) { return prosthesesIndex[index]; }

    public interface IsKeyword {
        public final static byte isKeyword[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static byte baseCheck[] = {0,
            7,7,4,10,5,7,5,12,13,6,
            7,8,7,7,6,12,5,6,4,4,
            10,2,8,2,3,10,11,6,5,6,
            2,3,3,7,4,7,5,5,6,4,
            7,4,4,6,6,9,4
        };
    };
    public final static byte baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static byte rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,50,
            83,88,85,89,21,98,101,27,25,95,
            104,24,108,40,105,43,47,109,111,112,
            34,115,113,117,119,120,122,51,123,54,
            125,133,134,136,135,55,68,138,141,60,
            139,140,144,146,70,151,50,156,73,150,
            153,71,16,159,162,164,165,168,166,169,
            170,176,173,178,179,182,183,186,187,188,
            189,198,200,201,202,204,207,209,80,213,
            79,212,86,214,215,218,219,222,227,223,
            231,229,238,236,242,232,244,245,247,240,
            249,250,251,254,257,259,260,261,264,269,
            262,273,274,275,277,276,284,283,285,287,
            288,291,294,297,190,289,299,304,300,305,
            308,309,315,311,319,322,320,325,326,330,
            324,331,332,334,337,339,343,344,335,345,
            353,347,355,351,356,358,359,360,362,370,
            365,371,373,378,368,379,381,385,384,388,
            391,390,394,392,398,399,400,402,404,406,
            405,411,412,415,418,416,422,423,424,427,
            430,433,435,437,438,439,440,442,446,251,
            251
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,0,1,17,18,19,
            0,21,2,0,0,5,0,4,2,9,
            7,8,8,0,10,9,3,14,14,0,
            20,2,0,1,5,6,0,5,6,0,
            0,5,3,0,0,1,10,8,8,0,
            1,11,3,9,18,16,13,0,15,0,
            0,4,0,4,7,3,6,5,0,0,
            10,2,0,14,0,0,2,0,0,11,
            5,0,14,6,0,10,8,0,19,5,
            0,4,2,0,0,2,24,0,0,2,
            0,0,0,5,0,4,0,3,0,0,
            16,0,0,5,0,15,10,5,4,17,
            9,12,0,0,0,0,2,0,0,0,
            0,3,9,0,7,0,14,12,8,0,
            0,2,0,10,9,0,17,7,0,1,
            5,0,1,0,0,0,3,0,0,0,
            18,7,0,6,6,0,1,0,0,1,
            15,0,0,1,3,0,0,0,0,0,
            4,22,4,16,7,23,11,0,1,0,
            0,0,3,0,15,4,0,7,0,3,
            2,0,0,0,0,12,4,0,0,6,
            3,0,0,12,6,11,0,1,0,7,
            0,0,11,2,4,0,8,0,1,0,
            5,0,1,0,0,2,0,3,0,0,
            0,2,6,0,6,16,0,1,0,0,
            0,0,3,0,11,0,6,17,0,6,
            2,10,0,0,0,0,0,5,20,6,
            6,5,0,0,0,1,0,0,0,2,
            0,16,9,0,8,13,0,7,0,0,
            7,13,6,0,0,0,3,0,0,5,
            0,4,13,15,0,1,8,7,0,0,
            2,0,1,0,0,0,7,3,3,0,
            0,0,3,0,0,4,0,1,0,16,
            2,8,0,0,0,15,0,4,4,7,
            0,17,0,1,0,0,2,0,0,0,
            3,0,4,13,0,19,7,0,13,0,
            0,10,0,4,10,8,6,0,0,7,
            0,4,4,0,0,5,2,0,1,0,
            0,0,3,0,1,12,6,0,0,0,
            9,0,5,0,0,0,3,2,9,5,
            0,0,2,12,0,0,1,0,20,8,
            3,0,0,0,3,0,0,4,2,0,
            1,17,0,1,0,1,0,0,0,0,
            3,0,6,21,5,0,0,0,7,11,
            0,0,0,8,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            251,64,68,60,54,58,61,55,59,56,
            53,65,67,66,63,251,139,57,62,52,
            251,51,75,251,251,74,251,84,79,73,
            85,86,282,251,80,78,101,273,81,251,
            72,90,251,94,88,89,251,92,93,251,
            251,96,132,251,251,117,95,130,108,251,
            122,109,121,116,97,131,112,24,111,251,
            251,276,251,127,118,134,137,135,251,251,
            138,160,251,128,251,251,70,251,251,163,
            166,251,162,69,251,165,71,251,159,82,
            251,76,77,251,251,83,250,251,251,87,
            251,251,251,98,251,100,251,102,251,251,
            91,251,251,105,251,99,104,110,284,103,
            107,106,251,251,251,251,115,251,251,251,
            251,123,113,251,119,251,283,114,120,251,
            251,136,251,125,126,251,124,129,251,294,
            133,251,293,251,251,251,140,251,251,251,
            298,291,251,141,143,251,286,251,251,147,
            142,251,251,149,148,251,251,251,251,251,
            151,144,271,146,152,145,150,251,270,251,
            251,251,153,251,290,155,251,154,251,157,
            158,251,251,251,251,156,161,251,251,167,
            168,251,251,164,169,254,251,171,251,172,
            251,251,170,176,173,251,174,251,289,251,
            288,251,175,251,251,177,251,280,251,251,
            251,181,178,251,180,179,251,268,251,251,
            251,251,185,251,183,251,186,182,251,187,
            188,189,251,251,251,251,251,190,184,191,
            192,258,251,251,251,195,251,251,251,197,
            251,193,256,251,196,194,251,296,251,251,
            295,199,198,251,251,251,281,251,251,201,
            251,202,279,200,251,204,203,269,251,251,
            205,251,266,251,251,251,206,207,208,251,
            251,251,209,251,251,211,251,213,251,261,
            214,212,251,251,251,210,251,216,287,215,
            251,292,251,285,251,251,218,251,251,251,
            221,251,265,219,251,217,264,251,220,251,
            251,222,251,262,223,253,224,251,251,257,
            251,225,252,251,251,226,227,251,274,251,
            251,251,230,251,263,228,229,251,251,251,
            231,251,232,251,251,251,235,236,297,237,
            251,251,238,234,251,251,277,251,233,239,
            241,251,251,251,272,251,251,243,244,251,
            255,240,251,278,251,245,251,251,251,251,
            247,251,246,242,248,251,251,251,259,267,
            251,251,251,260
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }
    public final int asb(int index) { return 0; }
    public final int asr(int index) { return 0; }
    public final int nasb(int index) { return 0; }
    public final int nasr(int index) { return 0; }
    public final int terminalIndex(int index) { return 0; }
    public final int nonterminalIndex(int index) { return 0; }
    public final int scopePrefix(int index) { return 0;}
    public final int scopeSuffix(int index) { return 0;}
    public final int scopeLhs(int index) { return 0;}
    public final int scopeLa(int index) { return 0;}
    public final int scopeStateSet(int index) { return 0;}
    public final int scopeRhs(int index) { return 0;}
    public final int scopeState(int index) { return 0;}
    public final int inSymb(int index) { return 0;}
    public final String name(int index) { return null; }
    public final int originalState(int state) { return 0; }
    public final int asi(int state) { return 0; }
    public final int nasi(int state) { return 0; }
    public final int inSymbol(int state) { return 0; }

    /**
     * assert(! goto_default);
     */
    public final int ntAction(int state, int sym) {
        return baseAction[state + sym];
    }

    /**
     * assert(! shift_default);
     */
    public final int tAction(int state, int sym) {
        int i = baseAction[state],
            k = i + sym;
        return termAction[termCheck[k] == sym ? k : i];
    }
    public final int lookAhead(int la_state, int sym) {
        int k = la_state + sym;
        return termAction[termCheck[k] == sym ? k : la_state];
    }
}
