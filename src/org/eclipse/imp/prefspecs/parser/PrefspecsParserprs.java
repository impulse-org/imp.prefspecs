
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

package org.eclipse.imp.prefspecs.parser;

public class PrefspecsParserprs implements lpg.runtime.ParseTable, PrefspecsParsersym {
    public final static int ERROR_SYMBOL = 64;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = -1;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 0;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 23;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 201;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 64;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 846;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 179;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 103;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 167;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 253;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 60;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 60;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 613;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 667;
    public final int getErrorAction() { return ERROR_ACTION; }

    public final static boolean BACKTRACK = true;
    public final boolean getBacktrack() { return BACKTRACK; }

    public final int getStartSymbol() { return lhs(0); }
    public final boolean isValidForParser() { return PrefspecsParsersym.isValidForParser; }


    public interface IsNullable {
        public final static byte isNullable[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,1,1,1,0,0,
            0,0,1,0,1,0,1,1,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,1,1,0,0,
            1,0,0,1,0,1,1,0,0,0,
            0,1,1,1,0,1,1,1,0,0,
            1,0,0,0,0,1,1,1,1,1,
            1,1,1,1,1,1,1,1,0,1,
            1,1,0,1,1,1,1,1,1,1,
            1,0,1,1,1,1,1,0,1,1,
            1,1,1,1,1,1,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            7,30,73,44,62,84,77,29,70,95,
            101,103,57,82,87,9,10,11,12,24,
            32,33,34,35,36,37,38,39,40,41,
            42,64,67,68,69,72,79,83,86,93,
            2,3,4,5,6,8,13,14,15,16,
            17,18,19,20,21,22,23,25,26,27,
            28,31,43,45,46,47,48,49,50,51,
            52,53,54,55,56,58,59,60,61,63,
            65,66,71,74,75,76,78,80,81,85,
            88,89,90,91,92,94,96,97,98,99,
            100,102,1
        };
    };
    public final static byte prosthesesIndex[] = ProsthesesIndex.prosthesesIndex;
    public final int prosthesesIndex(int index) { return prosthesesIndex[index]; }

    public interface IsKeyword {
        public final static byte isKeyword[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static short baseCheck[] = {0,
            3,0,3,1,3,0,3,1,1,1,
            2,1,1,5,5,2,0,3,0,3,
            2,0,1,0,1,0,4,1,1,1,
            1,0,4,5,5,5,5,1,1,4,
            0,1,2,1,1,1,1,1,1,1,
            1,1,1,4,4,4,4,4,4,4,
            4,4,4,0,4,0,4,0,4,0,
            4,0,4,0,4,0,4,0,4,0,
            4,0,4,0,2,1,1,4,0,1,
            2,5,1,2,1,1,1,1,4,0,
            2,3,3,3,4,0,3,0,3,0,
            3,0,3,2,1,0,3,0,3,2,
            2,0,3,2,2,0,3,3,2,4,
            0,3,1,3,2,0,1,1,0,7,
            1,0,5,1,1,1,2,2,0,5,
            0,3,0,3,2,1,0,5,0,3,
            3,2,0,3,0,3,4,0,3,0,
            3,1,1,1,1,1,2,1,1,-30,
            -5,-33,-4,1,-2,-25,-7,-50,1,8,
            -36,-100,-102,2,3,-59,16,17,18,-106,
            21,22,23,24,25,26,27,28,29,30,
            31,21,22,23,24,25,26,27,28,29,
            30,31,-119,36,44,33,-8,45,-35,37,
            -17,9,-104,11,-16,1,14,52,53,-10,
            55,62,16,17,18,-20,14,-110,20,15,
            -109,6,-1,19,-27,33,34,35,-105,37,
            32,14,40,-28,69,73,-11,9,-107,11,
            83,13,81,-47,-111,76,-32,9,48,11,
            50,13,35,9,34,11,47,13,49,-51,
            40,-180,1,41,42,-48,97,-58,99,-24,
            1,-67,1,8,82,-103,15,75,80,20,
            19,57,58,-66,15,2,3,54,-71,56,
            88,32,4,5,-108,-152,79,-72,78,2,
            3,6,7,60,-73,59,91,36,4,5,
            -74,-75,2,3,-96,4,5,-76,-77,2,
            3,7,4,5,-78,-79,2,3,-13,4,
            5,-80,-81,2,3,68,4,5,-82,-83,
            2,3,-128,4,5,-84,-85,2,3,74,
            4,5,-86,-87,2,3,-145,4,5,-88,
            -89,2,3,-3,4,5,-90,92,2,3,
            -93,77,2,3,-113,-6,2,3,-141,-168,
            38,39,-147,-22,2,3,-171,93,10,10,
            12,12,-189,-53,38,39,10,-54,12,-9,
            1,-55,10,-56,12,-57,43,-12,1,-15,
            1,-37,1,-38,1,-39,1,-40,1,-41,
            1,46,-42,1,-43,1,-44,1,-45,1,
            -46,1,-52,1,51,-60,-61,-62,-63,-64,
            -91,-95,-98,1,-99,1,-115,8,8,-101,
            1,-116,-121,-122,-126,-130,63,-131,7,-132,
            7,64,-134,6,-138,-149,65,-140,-142,66,
            6,-153,67,-144,-156,-159,1,-161,7,-162,
            6,-172,1,-181,-183,6,-187,-14,-18,6,
            6,-19,-21,-23,-26,-29,-31,-34,-49,61,
            -65,-68,-69,-70,-92,70,-94,71,-97,72,
            -112,-114,-117,-118,-120,-123,-124,-125,-127,-129,
            -133,-135,-136,-137,-139,84,-143,-146,-148,-150,
            -151,85,-154,-155,-157,-158,-160,-163,-164,87,
            -165,-166,-167,89,-169,-170,-173,-174,-175,-176,
            -177,86,-178,-179,-182,100,-184,-185,-186,-188,
            98,102,95,90,96,-190,-191,-192,-193,-194,
            -195,-196,-197,-198,-199,-200,-201,0,101,0,
            94,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0
        };
    };
    public final static short baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static short rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            41,41,42,42,45,45,43,43,46,46,
            44,44,16,16,17,18,47,49,49,48,
            48,52,53,53,54,54,50,50,20,20,
            20,20,57,57,58,59,60,61,8,8,
            51,62,62,62,21,21,21,21,21,21,
            21,21,21,21,22,23,24,25,26,27,
            28,29,30,31,63,63,64,64,65,65,
            66,66,67,67,68,68,69,69,70,70,
            71,71,72,72,4,4,5,5,55,80,
            80,80,32,81,81,82,82,82,82,56,
            83,83,83,36,36,2,3,3,84,84,
            85,85,86,86,73,33,37,37,87,87,
            75,88,89,89,79,35,90,90,38,38,
            39,14,14,19,19,15,91,91,74,92,
            92,77,93,93,94,94,94,78,34,40,
            40,96,96,95,95,76,97,99,99,98,
            98,13,9,11,11,102,102,102,100,100,
            101,101,1,7,7,6,10,10,12,12,
            11,47,48,92,4,77,94,69,138,533,
            529,1,141,173,223,472,157,10,12,13,
            184,42,44,45,46,47,48,49,50,51,
            52,53,43,44,45,46,47,48,49,50,
            51,52,53,43,532,227,483,53,187,8,
            115,58,98,177,490,92,246,387,20,264,
            2,23,191,11,12,13,109,373,177,463,
            133,188,137,62,522,118,95,96,97,182,
            115,90,387,499,105,370,545,21,486,182,
            490,302,550,544,131,182,551,130,486,523,
            490,414,553,504,486,498,490,518,558,440,
            4,499,92,246,185,394,48,493,155,156,
            92,246,65,533,531,94,183,133,548,290,
            463,600,527,277,141,134,537,472,21,55,
            25,485,91,54,480,185,46,557,141,555,
            193,472,568,563,469,55,274,135,539,55,
            480,141,55,306,472,71,56,480,141,55,
            233,472,543,57,480,141,55,259,472,100,
            58,480,141,55,200,472,363,59,480,141,
            55,269,472,73,60,480,141,55,325,472,
            546,61,480,141,55,251,472,73,62,480,
            141,55,248,472,88,63,480,141,138,275,
            472,141,554,542,472,141,9,559,472,44,
            44,121,566,141,102,577,472,44,141,575,
            590,576,576,44,143,125,566,596,145,576,
            92,359,147,605,149,576,153,181,92,5,
            92,519,92,424,92,428,92,432,92,434,
            92,436,267,92,298,92,196,92,466,92,
            467,92,468,92,534,186,159,161,163,139,
            48,48,167,92,104,92,103,192,535,541,
            92,85,71,204,71,215,221,319,223,561,
            224,563,335,227,568,223,196,342,237,242,
            349,573,71,356,244,223,92,584,239,580,
            223,583,92,597,223,223,585,10,101,93,
            601,602,5,110,116,117,125,121,132,133,
            33,165,169,50,171,172,377,176,384,179,
            391,191,193,198,200,203,210,206,214,217,
            218,226,230,232,233,236,477,243,249,252,
            253,255,496,260,258,262,266,268,269,83,
            114,264,272,273,120,274,275,277,276,283,
            285,287,105,288,293,295,508,297,299,290,
            301,155,162,147,124,148,303,309,311,313,
            82,314,316,318,321,323,325,326,667,161,
            667,604,667,0,442,41,0,444,41,0,
            446,41,0,448,41,0,450,41,0,453,
            41,0,455,41,0,457,41,0,459,41,
            0,461,41,0,698,89,0,697,89,0,
            696,89,0,695,89,0,839,100,0,326,
            149,0,116,163,149,93,0
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,0,2,0,0,2,2,0,0,0,
            0,7,9,10,11,12,9,10,11,12,
            0,1,22,23,24,25,26,27,28,29,
            30,31,22,23,24,25,26,27,28,29,
            30,31,0,0,2,0,0,0,5,0,
            8,6,0,44,0,13,47,0,49,14,
            15,0,54,55,0,19,2,21,0,1,
            0,19,0,21,32,33,0,20,35,36,
            16,0,0,2,14,15,18,0,7,42,
            43,0,0,0,0,38,14,15,37,0,
            0,0,3,3,0,56,57,16,0,0,
            18,2,58,59,6,0,0,0,3,3,
            0,20,2,51,0,53,9,3,41,0,
            0,0,0,40,3,3,60,0,0,10,
            0,11,0,39,0,3,0,3,0,3,
            12,3,0,16,0,3,0,3,0,3,
            0,3,0,3,0,3,0,3,0,1,
            0,0,0,3,3,0,0,2,0,1,
            8,0,0,0,0,45,4,0,4,8,
            0,0,0,3,2,0,13,0,1,0,
            13,2,0,0,2,0,0,4,32,0,
            5,2,46,0,0,2,0,0,4,2,
            0,5,0,0,4,0,0,2,6,0,
            4,0,0,2,2,0,0,2,0,48,
            4,0,0,0,2,50,17,4,0,8,
            2,0,0,2,0,1,33,0,6,0,
            1,0,1,0,7,0,1,0,0,1,
            3,0,0,0,0,0,0,5,2,5,
            17,6,0,1,0,1,0,0,1,0,
            52,5,0,1,0,1,0,1,0,1,
            0,1,0,1,0,34,17,34,0,1,
            0,1,0,0,1,0,1,0,1,7,
            0,1,0,1,0,0,1,0,0,5,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            667,17,707,667,667,755,681,89,667,667,
            41,300,695,696,697,698,653,650,647,644,
            667,674,442,444,446,450,448,453,455,457,
            459,461,614,617,620,626,623,629,632,635,
            638,641,131,667,662,667,667,667,843,667,
            659,842,1,812,84,409,813,19,811,840,
            841,2,675,676,667,240,766,430,667,670,
            667,240,667,430,549,163,667,26,845,846,
            839,667,667,797,840,841,438,6,300,705,
            706,667,16,22,667,524,515,587,183,667,
            667,667,235,231,24,475,473,839,136,667,
            685,682,753,754,842,667,667,32,255,180,
            667,525,694,506,667,567,182,229,406,667,
            667,667,667,526,188,314,613,100,667,296,
            106,470,64,528,66,328,68,341,70,348,
            471,355,72,656,74,362,76,369,78,376,
            80,383,82,390,667,397,108,401,667,768,
            667,667,116,192,405,667,131,701,667,769,
            484,163,139,157,142,345,547,149,495,488,
            667,110,667,413,702,112,552,667,774,667,
            409,759,667,118,732,667,667,502,549,667,
            564,734,482,667,122,736,667,667,505,738,
            168,565,667,165,510,667,159,740,842,667,
            571,667,667,742,744,667,153,746,170,560,
            410,151,667,126,748,578,572,512,667,417,
            750,667,667,703,667,776,569,667,579,667,
            784,667,799,667,581,667,796,667,667,831,
            292,667,667,667,667,667,667,517,704,844,
            588,598,667,778,667,786,667,667,790,667,
            514,599,667,795,667,836,667,833,667,827,
            667,821,667,819,667,589,603,423,667,794,
            667,780,667,667,838,667,834,667,825,606,
            667,810,667,817,667,667,807,667,667,607
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static char asb[] = {0,
            49,28,50,12,51,87,8,26,12,12,
            9,12,131,131,12,12,36,130,41,40,
            107,38,131,12,4,131,33,5,131,97,
            107,62,1,131,30,97,12,12,12,12,
            12,12,12,12,12,12,60,1,131,11,
            30,12,94,94,94,94,94,94,94,94,
            94,94,109,1,131,72,11,9,133,131,
            95,70,95,71,95,18,95,68,95,79,
            95,68,95,71,95,78,95,18,95,68,
            1,131,72,107,111,65,9,12,12,14,
            12,45,84,120,54,83,54,84,44,120,
            54,131,72,107,112,65,9,107,116,107,
            84,65,107,92,107,84,92,121,107,56,
            124,55,107,84,136,107,107,124,107,84,
            90,45,107,84,121,107,72,107,113,124,
            9,64,65,9,42,124,9,9,12,131,
            57,124,9,65,136,138,92,90,138,92,
            90,12,107,124,9,9,92,9,9,12,
            124,9,124,9,9,136,126,9,90,9,
            9,9,42,41,9,9,9,9,9,92,
            9
        };
    };
    public final static char asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            42,43,0,40,2,39,0,18,1,0,
            2,16,0,2,33,8,13,32,46,48,
            50,51,53,45,0,19,21,60,0,10,
            11,12,2,9,0,2,38,20,0,6,
            2,7,0,13,2,4,8,0,37,41,
            19,21,0,8,33,4,2,52,0,11,
            0,10,0,6,14,15,0,33,52,8,
            4,2,46,48,50,45,0,8,46,48,
            50,45,13,2,4,0,54,55,0,35,
            36,5,0,3,58,59,31,30,29,28,
            27,25,26,24,23,22,2,0,12,0,
            46,48,4,52,50,33,13,2,8,32,
            53,51,0,6,0,49,44,47,0,18,
            3,0,56,57,0,17,0,34,0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static char nasb[] = {0,
            46,26,82,7,3,84,26,27,13,38,
            26,13,26,26,13,13,32,26,26,41,
            26,89,26,13,9,26,49,54,26,1,
            26,58,5,26,29,15,13,13,13,13,
            13,13,13,13,13,13,56,5,26,11,
            60,13,91,93,95,97,99,63,19,101,
            103,105,107,5,26,67,12,26,26,26,
            69,67,69,67,69,67,69,67,69,67,
            69,67,69,67,69,67,69,67,69,67,
            5,26,67,26,109,77,26,13,13,67,
            13,17,65,35,52,21,52,72,25,43,
            52,26,67,26,111,77,26,26,23,26,
            113,77,26,26,26,115,26,79,26,117,
            75,119,26,121,26,26,26,75,26,125,
            86,127,26,129,79,26,67,26,123,26,
            26,74,77,26,26,75,26,26,13,26,
            131,75,26,26,26,26,26,86,26,26,
            86,13,26,26,26,26,26,26,26,13,
            75,26,75,26,26,26,133,26,86,26,
            26,26,26,26,26,26,26,26,26,26,
            26
        };
    };
    public final static char nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static char nasr[] = {0,
            62,0,44,0,8,0,45,0,53,0,
            83,36,1,0,21,0,33,0,69,0,
            97,0,14,11,40,0,16,0,20,80,
            0,50,48,0,14,88,0,49,47,0,
            91,0,14,35,0,42,41,0,58,57,
            0,11,0,54,0,60,0,59,0,20,
            32,0,68,0,74,0,3,0,5,4,
            0,77,0,7,6,0,7,0,39,38,
            0,43,0,46,0,12,10,0,51,0,
            63,0,64,0,65,0,66,0,67,0,
            70,0,71,0,72,0,61,0,84,0,
            85,0,87,0,89,0,100,0,102,0,
            98,0,86,0,95,0,96,0,90,0,
            101,0,94,0
        };
    };
    public final static char nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            3,9,8,37,61,62,1,39,19,20,
            21,22,45,50,51,60,63,2,12,14,
            16,25,26,27,28,29,30,31,32,33,
            34,36,38,64,4,5,11,13,15,17,
            18,23,24,35,40,41,42,43,44,46,
            47,48,49,52,53,54,55,56,57,58,
            6,7,59,65
        };
    };
    public final static byte terminalIndex[] = TerminalIndex.terminalIndex;
    public final int terminalIndex(int index) { return terminalIndex[index]; }

    public interface NonterminalIndex {
        public final static byte nonterminalIndex[] = {0,
            69,0,0,0,84,91,88,80,0,95,
            0,96,0,0,93,71,0,0,72,75,
            81,0,0,0,0,0,0,0,0,0,
            0,85,0,0,86,87,0,90,92,0,
            66,0,0,67,68,70,73,0,0,0,
            74,0,0,0,0,0,0,76,77,78,
            79,0,0,0,0,0,0,0,0,0,
            0,0,0,0,82,0,0,0,83,0,
            0,0,0,0,0,0,0,89,0,0,
            0,0,0,94,0,0,0,0,0,0,
            0,0,0
        };
    };
    public final static byte nonterminalIndex[] = NonterminalIndex.nonterminalIndex;
    public final int nonterminalIndex(int index) { return nonterminalIndex[index]; }
    public final static int scopePrefix[] = null;
    public final int scopePrefix(int index) { return 0;}

    public final static int scopeSuffix[] = null;
    public final int scopeSuffix(int index) { return 0;}

    public final static int scopeLhs[] = null;
    public final int scopeLhs(int index) { return 0;}

    public final static int scopeLa[] = null;
    public final int scopeLa(int index) { return 0;}

    public final static int scopeStateSet[] = null;
    public final int scopeStateSet(int index) { return 0;}

    public final static int scopeRhs[] = null;
    public final int scopeRhs(int index) { return 0;}

    public final static int scopeState[] = null;
    public final int scopeState(int index) { return 0;}

    public final static int inSymb[] = null;
    public final int inSymb(int index) { return 0;}


    public interface Name {
        public final static String name[] = {
            "",
            ",",
            ".",
            ";",
            "+",
            "-",
            "(",
            ")",
            "{",
            "}",
            "$empty",
            "PACKAGE",
            "PAGE",
            "TABS",
            "FIELDS",
            "CONDITIONALS",
            "CHOICETYPE",
            "CUSTOM",
            "DETAILS",
            "DEFAULT",
            "CONFIGURATION",
            "INSTANCE",
            "PROJECT",
            "IN",
            "OUT",
            "BOOLEAN",
            "COLOR",
            "COMBO",
            "DOUBLE",
            "DIRLIST",
            "FILE",
            "FONT",
            "INT",
            "RADIO",
            "STRING",
            "BOLD",
            "COLUMNS",
            "DEFVALUE",
            "EMPTYALLOWED",
            "HASSPECIAL",
            "ISEDITABLE",
            "ISREMOVABLE",
            "ITALIC",
            "LABEL",
            "NORMAL",
            "RANGE",
            "TOOLTIP",
            "TYPE",
            "VALIDATOR",
            "VALUES",
            "TRUE",
            "FALSE",
            "ON",
            "OFF",
            "WITH",
            "AGAINST",
            "IF",
            "UNLESS",
            "EOF_TOKEN",
            "SINGLE_LINE_COMMENT",
            "IDENTIFIER",
            "INTEGER",
            "STRING_LITERAL",
            "DECIMAL",
            "DOTS",
            "ERROR_TOKEN",
            "prefSpecs",
            "topLevelItems",
            "packageName",
            "identifier",
            "onOff",
            "topLevelItem",
            "labelledStringValueList",
            "pageName",
            "fieldsSpec",
            "tab",
            "defaultTabSpec",
            "configurationTabSpec",
            "instanceTabSpec",
            "projectTabSpec",
            "inout",
            "fieldSpec",
            "comboSpecificSpec",
            "radioSpecificSpec",
            "conditionType",
            "customRule",
            "radioCustomSpec",
            "conditionalSpec",
            "booleanValue",
            "comboCustomSpec",
            "typeOrValuesSpec",
            "stringValue",
            "valuesSpec",
            "labelledStringValue",
            "fontStyle",
            "signedNumber",
            "sign"
        };
    };
    public final static String name[] = Name.name;
    public final String name(int index) { return name[index]; }

    public final int originalState(int state) {
        return -baseCheck[state];
    }
    public final int asi(int state) {
        return asb[originalState(state)];
    }
    public final int nasi(int state) {
        return nasb[originalState(state)];
    }
    public final int inSymbol(int state) {
        return inSymb[originalState(state)];
    }

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
