
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

    public final static int NUM_STATES = 210;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 30;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 312;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 49;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 2;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 32;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 50;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 24;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 31;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 262;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 263;
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
            7,8,7,9,7,6,7,12,5,6,
            4,4,10,2,8,2,3,10,11,6,
            5,6,2,3,3,7,4,7,5,5,
            6,4,7,4,4,6,6,9,4
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
            1,1,1,1,1,1,1,1,1,1,
            1,52,72,100,15,62,23,36,108,55,
            37,98,109,27,112,24,19,38,48,113,
            99,116,119,121,117,123,125,126,127,51,
            132,50,131,65,129,128,140,68,71,143,
            144,73,145,146,147,149,151,79,154,46,
            153,81,162,156,82,165,167,169,171,172,
            176,157,177,178,180,184,185,186,188,189,
            192,193,194,198,195,203,205,209,208,211,
            90,214,85,215,89,217,97,220,221,222,
            223,227,231,228,234,236,240,237,245,247,
            248,251,252,243,253,260,255,256,263,261,
            266,267,265,273,269,274,277,278,282,285,
            281,289,290,293,294,292,296,301,302,305,
            297,306,307,312,310,314,316,318,324,322,
            328,327,331,334,335,337,342,343,336,338,
            344,347,351,356,353,357,349,359,364,362,
            367,368,370,371,372,373,375,378,376,384,
            387,389,391,390,392,399,396,402,393,407,
            405,409,410,413,415,416,417,418,420,422,
            426,429,423,430,431,436,437,442,444,438,
            445,446,450,454,456,452,460,461,462,464,
            466,263,263
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,0,15,2,17,0,19,
            20,21,0,0,2,2,0,5,5,6,
            4,9,14,7,8,0,0,0,1,4,
            18,15,5,6,8,0,10,0,3,0,
            0,15,5,8,0,18,2,10,8,14,
            11,0,12,9,0,16,19,0,1,8,
            0,0,0,1,4,3,9,7,0,15,
            0,0,4,3,0,5,2,6,0,0,
            1,10,3,15,0,24,0,0,0,0,
            12,5,5,15,20,6,10,0,0,2,
            2,0,0,2,16,0,0,5,0,4,
            0,3,0,3,0,0,0,0,0,5,
            0,0,10,17,4,9,5,9,13,0,
            13,2,0,0,0,0,0,3,0,7,
            0,8,0,0,8,0,0,5,10,9,
            7,0,17,2,0,1,0,1,0,1,
            0,0,16,3,19,0,0,0,7,0,
            1,6,6,0,0,0,1,0,0,1,
            3,0,0,0,0,1,4,0,14,22,
            7,4,0,12,0,3,23,0,0,2,
            0,7,4,0,0,2,0,0,4,0,
            0,0,0,13,3,6,0,0,6,13,
            0,1,12,0,7,0,0,4,12,0,
            1,5,0,8,0,1,0,0,2,2,
            0,0,0,3,0,0,14,6,6,0,
            0,2,0,1,0,0,0,12,0,3,
            6,17,0,0,6,2,0,0,18,14,
            0,0,5,11,0,5,10,6,0,0,
            6,0,0,0,1,0,0,2,9,8,
            0,0,14,11,0,0,0,7,7,0,
            6,0,16,0,3,0,11,0,5,4,
            11,0,16,0,1,8,0,0,7,2,
            0,1,6,0,0,0,0,0,4,3,
            7,0,0,0,3,3,0,4,0,14,
            0,1,0,16,8,0,0,2,0,7,
            4,0,4,0,1,17,0,0,2,0,
            0,0,0,3,0,0,4,0,11,5,
            11,20,11,0,7,10,0,4,0,0,
            0,0,0,4,6,0,10,7,0,8,
            5,0,4,2,0,13,0,1,0,0,
            6,3,0,1,0,0,0,0,9,0,
            5,0,0,9,3,0,9,2,0,0,
            0,2,13,5,18,0,0,0,8,3,
            18,0,1,0,0,0,3,2,4,0,
            1,0,17,0,1,0,1,6,21,0,
            0,0,3,0,0,0,5,0,0,0,
            7,0,12,8,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            263,66,70,62,56,60,63,57,61,58,
            55,68,67,69,263,65,72,59,263,64,
            54,53,263,263,77,92,263,76,90,91,
            86,75,93,87,88,263,263,263,97,78,
            74,287,95,96,296,263,82,263,136,263,
            263,83,99,134,263,94,81,98,111,135,
            115,263,112,80,263,114,100,263,120,73,
            26,263,263,125,290,124,119,121,263,297,
            263,263,131,138,263,139,166,141,263,263,
            163,142,162,132,263,262,263,263,263,263,
            169,172,84,168,165,71,171,263,263,79,
            85,263,263,89,102,263,263,101,263,103,
            263,104,263,105,263,263,263,263,263,108,
            263,263,107,106,298,110,113,116,109,263,
            117,118,263,263,263,263,263,126,263,122,
            263,123,263,263,128,263,263,137,129,130,
            133,263,127,140,263,143,263,308,263,307,
            263,263,146,144,312,263,263,263,305,263,
            300,145,147,263,263,263,151,263,263,153,
            152,263,263,263,263,284,155,263,150,148,
            156,285,263,154,263,157,149,263,263,160,
            263,158,159,263,263,164,263,263,167,263,
            263,263,263,161,174,173,263,263,175,170,
            263,177,266,263,178,263,263,179,176,263,
            303,302,263,180,263,181,263,263,182,183,
            263,263,263,294,263,263,185,184,186,263,
            263,187,263,282,263,263,263,189,263,192,
            193,188,263,263,195,196,263,263,190,191,
            263,263,198,194,263,270,197,199,263,263,
            200,263,263,263,203,263,263,205,268,204,
            263,263,201,202,263,263,263,310,309,263,
            206,263,304,263,295,263,207,263,209,210,
            293,263,208,263,212,211,263,263,283,213,
            263,279,214,263,263,263,263,263,216,217,
            215,263,263,263,218,219,263,221,263,273,
            263,223,263,220,222,263,263,224,263,225,
            226,263,301,263,299,306,263,263,228,263,
            263,263,263,231,263,263,278,263,229,232,
            230,227,280,263,276,233,263,274,263,263,
            263,263,263,236,235,263,234,269,263,265,
            237,263,264,238,263,239,263,288,263,263,
            240,241,263,275,263,263,263,263,242,263,
            244,263,263,243,247,263,311,248,263,263,
            263,250,246,249,245,263,263,263,251,253,
            277,263,291,263,263,263,286,256,255,263,
            267,263,252,263,292,263,257,258,254,263,
            263,263,259,263,263,263,260,263,263,263,
            271,263,281,272
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
