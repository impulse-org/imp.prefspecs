
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

    public final static int NUM_STATES = 205;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 30;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 305;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 48;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 2;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 32;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 49;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 24;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 31;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 256;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 257;
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
            7,8,7,9,7,6,12,5,6,4,
            4,10,2,8,2,3,10,11,6,5,
            6,2,3,3,7,4,7,5,5,6,
            4,7,4,4,6,6,9,4
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
            51,73,95,90,16,23,102,91,55,29,
            103,105,26,109,42,104,45,49,110,112,
            113,116,118,114,60,123,122,124,19,125,
            52,132,126,129,134,137,65,71,141,142,
            35,143,144,145,149,58,152,53,151,76,
            160,153,77,163,165,167,154,169,171,170,
            173,174,180,175,178,182,184,188,190,191,
            186,197,199,203,204,205,207,85,208,80,
            212,84,213,89,215,217,219,218,223,227,
            225,230,231,236,233,240,242,243,246,247,
            248,250,252,251,255,257,259,260,265,261,
            269,274,270,273,279,281,283,266,284,286,
            289,291,292,295,296,298,300,301,305,306,
            307,308,310,311,316,321,322,325,323,327,
            329,333,334,335,338,339,340,341,344,345,
            350,347,355,356,349,358,361,362,363,367,
            365,368,374,376,377,379,381,378,383,386,
            389,393,392,396,398,399,400,406,401,403,
            411,412,413,414,416,415,419,422,423,425,
            427,432,435,428,436,439,443,445,447,450,
            451,452,453,455,457,257,257
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,0,0,17,0,19,
            20,21,0,8,2,0,8,5,0,4,
            12,9,7,8,0,1,8,3,10,14,
            18,0,14,2,0,1,5,6,0,5,
            6,0,0,5,0,3,2,0,10,0,
            8,4,11,9,0,1,15,19,16,10,
            0,14,0,9,4,0,0,7,3,0,
            5,2,6,0,0,1,10,3,0,0,
            0,2,2,5,0,12,24,14,10,20,
            6,0,0,0,0,4,2,5,0,0,
            2,0,0,0,5,0,4,0,3,16,
            3,0,0,0,0,0,15,5,0,5,
            17,0,9,0,13,4,0,9,2,14,
            0,0,0,0,0,3,13,7,0,8,
            0,0,0,0,10,5,3,9,7,0,
            17,2,0,1,0,1,0,1,0,0,
            0,19,0,0,0,7,6,0,6,0,
            1,0,1,0,15,0,3,0,1,0,
            0,0,7,16,4,22,0,23,0,1,
            4,12,0,0,0,3,0,0,4,2,
            7,0,0,0,0,4,0,0,0,13,
            6,3,0,6,0,13,0,1,12,0,
            0,7,0,4,12,0,1,5,8,0,
            1,0,0,2,2,0,0,0,3,0,
            0,0,6,2,0,6,0,1,0,0,
            0,0,3,16,0,0,12,17,0,0,
            6,11,0,0,6,2,18,5,0,10,
            0,16,0,0,6,0,6,5,0,1,
            0,0,9,2,0,0,11,0,8,0,
            0,7,7,6,0,0,0,0,3,0,
            0,11,5,4,15,0,1,11,8,15,
            0,0,0,2,0,1,0,7,0,7,
            4,3,0,0,0,3,3,0,0,0,
            0,1,4,0,0,2,0,8,0,0,
            16,7,15,4,0,0,1,0,4,2,
            0,0,0,17,0,3,0,0,20,5,
            4,11,11,0,7,0,0,0,0,4,
            0,4,0,10,6,0,10,7,0,4,
            8,0,0,5,2,0,1,0,0,0,
            0,3,0,6,13,0,1,5,9,9,
            0,0,0,0,0,0,3,2,0,5,
            9,0,0,2,0,13,0,0,18,3,
            8,0,1,0,0,0,18,3,0,4,
            2,17,0,1,0,1,0,1,21,0,
            0,0,0,3,0,6,0,5,0,0,
            0,7,0,12,8,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            257,65,69,61,55,59,62,56,60,57,
            54,67,66,68,64,257,257,58,257,63,
            53,52,257,72,76,257,109,75,257,85,
            110,74,86,87,257,123,289,122,81,280,
            73,257,82,91,257,95,89,90,257,93,
            94,257,257,97,257,133,80,257,96,257,
            131,128,113,79,257,118,112,98,132,105,
            25,129,257,117,283,257,257,119,135,257,
            136,162,138,257,257,159,139,158,257,257,
            257,71,78,168,257,165,256,164,167,161,
            70,257,257,257,257,77,84,83,257,257,
            88,257,257,257,99,257,101,257,102,92,
            103,257,257,257,257,257,100,106,257,111,
            104,257,108,257,107,291,257,114,116,290,
            257,257,257,257,257,124,115,120,257,121,
            257,257,257,257,126,134,141,127,130,257,
            125,137,257,140,257,301,257,300,257,257,
            257,305,257,257,257,298,142,257,144,257,
            293,257,148,257,143,257,149,257,150,257,
            257,257,153,147,152,145,257,146,257,277,
            278,151,257,257,257,154,257,257,156,160,
            155,257,257,257,257,163,257,257,257,157,
            169,170,257,171,257,166,257,173,260,257,
            257,174,257,175,172,257,296,295,176,257,
            177,257,257,178,179,257,257,257,287,257,
            257,257,180,183,257,182,257,275,257,257,
            257,257,187,181,257,257,185,184,257,257,
            188,189,257,257,190,191,186,193,257,192,
            257,196,257,257,194,257,195,264,257,198,
            257,257,262,200,257,257,197,257,199,257,
            257,303,302,201,257,257,257,257,288,257,
            257,202,204,205,297,257,207,286,206,203,
            257,257,257,208,257,273,257,276,257,209,
            210,211,257,257,257,212,213,257,257,257,
            257,217,215,257,257,218,257,216,257,257,
            267,219,214,220,257,257,292,257,294,222,
            257,257,257,299,257,225,257,257,221,226,
            272,223,224,257,270,257,257,257,257,268,
            257,230,257,227,229,257,228,263,257,258,
            259,257,257,231,232,257,281,257,257,257,
            257,235,257,234,233,257,269,238,236,237,
            257,257,257,257,257,257,241,242,257,243,
            304,257,257,244,257,240,257,257,239,247,
            245,257,284,257,257,257,271,279,257,249,
            250,246,257,261,257,285,257,251,248,257,
            257,257,257,253,257,252,257,254,257,257,
            257,265,257,274,266
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
