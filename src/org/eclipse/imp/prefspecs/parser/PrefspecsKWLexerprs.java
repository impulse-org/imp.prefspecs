
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

    public final static int NUM_STATES = 201;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 30;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 301;
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

    public final static int ACCEPT_ACTION = 252;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 253;
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
            7,7,4,10,5,7,5,12,13,7,
            8,7,9,7,6,7,12,5,6,4,
            4,5,2,8,2,3,10,11,6,5,
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
            51,84,87,62,95,17,91,99,52,40,
            100,103,23,106,42,107,33,49,109,110,
            111,114,118,112,120,122,119,125,56,127,
            59,128,135,136,138,137,57,67,140,141,
            24,143,142,147,152,149,71,29,153,74,
            161,155,79,164,166,168,170,171,172,160,
            176,177,180,183,184,185,187,188,191,192,
            194,193,195,202,201,207,208,211,80,213,
            41,82,214,88,216,217,218,219,220,230,
            226,223,233,235,237,238,242,243,246,247,
            248,250,252,251,255,257,259,260,262,263,
            261,273,278,271,282,283,266,274,275,285,
            286,291,292,293,295,299,297,302,300,304,
            306,308,310,309,318,319,322,323,325,327,
            331,335,336,329,337,340,344,346,341,350,
            351,352,353,356,358,359,361,362,366,363,
            369,320,374,372,378,380,376,383,386,385,
            392,387,395,398,393,403,397,405,399,407,
            408,410,411,412,418,419,414,422,423,427,
            428,432,435,437,439,441,442,443,446,444,
            449,253,253
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,15,0,17,2,19,
            4,21,0,0,1,9,3,5,0,7,
            8,3,0,1,18,7,4,15,6,0,
            0,0,2,2,16,4,7,6,0,10,
            18,0,4,2,15,0,0,1,0,19,
            9,0,7,2,0,9,0,12,20,11,
            0,5,14,0,8,5,3,4,0,0,
            1,0,3,0,6,15,0,0,10,0,
            0,4,6,12,0,5,15,10,0,0,
            2,7,0,4,2,0,0,24,0,0,
            0,0,4,0,9,5,3,0,0,0,
            3,0,16,14,0,4,0,0,17,10,
            4,13,5,9,0,0,0,0,2,0,
            0,0,0,4,9,3,0,7,0,15,
            13,0,0,7,0,0,4,9,17,0,
            0,10,2,0,1,0,1,0,1,0,
            0,0,3,14,20,0,0,6,8,0,
            1,6,0,0,0,1,0,0,1,3,
            0,0,0,0,0,1,5,5,22,16,
            0,0,12,10,3,23,0,0,8,2,
            0,5,0,0,2,0,0,0,0,0,
            3,6,0,13,6,0,13,5,12,0,
            1,12,0,8,0,1,0,0,1,7,
            4,0,0,2,2,0,0,0,3,0,
            0,0,6,2,0,6,0,1,0,0,
            0,0,0,16,3,0,12,17,6,4,
            0,11,0,0,0,16,18,0,6,2,
            10,0,0,9,0,0,1,6,6,16,
            0,0,0,2,0,11,0,7,0,0,
            8,0,8,0,6,0,3,0,0,0,
            14,4,11,14,5,7,11,0,0,0,
            2,0,0,1,0,8,0,6,0,10,
            0,5,8,3,0,0,0,3,3,0,
            0,5,14,0,1,0,7,2,8,0,
            0,0,0,1,5,0,5,0,0,2,
            0,0,0,3,0,0,4,17,0,11,
            5,0,11,0,19,0,8,0,5,0,
            5,10,0,6,0,0,0,8,4,7,
            5,0,0,2,0,1,0,0,0,13,
            3,9,0,1,0,9,0,0,4,0,
            0,0,3,0,4,9,18,0,0,2,
            13,0,0,1,3,7,0,0,0,18,
            17,0,5,2,0,1,0,1,0,1,
            0,0,0,0,3,0,6,21,0,4,
            0,8,0,0,12,7,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            253,65,69,61,59,55,62,60,56,57,
            54,67,66,68,63,64,253,58,76,53,
            75,52,253,253,123,74,122,85,253,87,
            86,133,253,96,73,131,94,276,95,253,
            253,253,163,91,132,89,285,90,253,81,
            93,253,97,80,82,253,253,118,253,162,
            79,253,109,71,253,117,25,110,98,113,
            253,279,112,253,119,129,135,136,253,253,
            160,253,159,253,138,130,253,253,139,253,
            253,168,70,165,253,77,164,167,253,253,
            78,72,253,83,84,253,253,252,253,253,
            253,253,99,253,88,101,102,253,253,253,
            103,253,92,100,253,106,253,253,104,105,
            111,107,287,108,253,253,253,253,116,253,
            253,253,253,120,114,124,253,121,253,286,
            115,253,253,126,253,253,134,128,125,253,
            253,127,137,253,140,253,297,253,296,253,
            253,253,141,143,301,253,253,142,294,253,
            289,144,253,253,253,148,253,253,150,149,
            253,253,253,253,253,273,152,274,145,147,
            253,253,151,153,154,146,253,253,155,157,
            253,156,253,253,161,253,253,253,253,253,
            170,169,253,158,171,253,166,175,256,253,
            173,172,253,174,253,292,253,253,177,176,
            291,253,253,178,179,253,253,253,283,253,
            253,253,180,183,253,182,253,271,253,253,
            253,253,253,181,187,253,184,275,188,260,
            253,189,253,253,253,186,185,253,190,191,
            192,253,253,258,253,253,197,193,194,195,
            253,253,253,199,253,196,253,198,253,253,
            299,253,298,253,200,253,284,253,253,253,
            293,203,201,202,204,205,282,253,253,253,
            206,253,253,268,253,272,253,207,253,225,
            253,209,208,210,253,253,253,211,212,253,
            253,214,213,253,216,253,215,217,218,253,
            253,253,253,288,219,253,290,253,253,221,
            253,253,253,223,253,253,224,295,253,222,
            267,253,269,253,220,253,265,253,263,253,
            228,226,253,227,253,253,253,259,229,255,
            254,253,253,230,253,277,253,253,253,231,
            232,233,253,264,253,234,253,253,235,253,
            253,253,238,253,239,300,236,253,253,240,
            237,253,253,280,243,241,253,253,253,266,
            242,253,245,246,253,257,253,281,253,247,
            253,253,253,253,249,253,248,244,253,250,
            253,261,253,253,270,262
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
