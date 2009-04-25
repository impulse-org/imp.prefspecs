
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
    public final static int ERROR_SYMBOL = 69;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = -1;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 0;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 23;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 178;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 69;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 1022;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 201;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 109;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 178;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 230;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 61;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 61;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 772;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 821;
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
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,1,0,0,0,0,
            0,0,0,1,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,1,1,0,0,0,0,1,1,1,
            0,1,1,1,0,0,1,0,0,0,
            0,0,1,1,1,1,1,1,1,1,
            1,1,1,1,0,0,0,0,0,0,
            0,0,1,1,1,0,1,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            7,64,65,66,67,68,45,101,96,97,
            98,99,100,84,30,29,59,69,76,77,
            90,93,102,94,9,10,11,12,32,33,
            34,35,36,37,38,39,40,41,42,43,
            70,71,72,73,74,75,78,79,80,81,
            82,83,86,87,88,89,91,92,104,105,
            109,2,3,4,5,6,8,13,14,15,
            16,17,18,19,20,21,22,23,24,25,
            26,27,28,31,44,46,47,48,49,50,
            51,52,53,54,55,56,57,58,60,61,
            62,63,85,95,103,106,107,108,1
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
            0,0,0,0,0,0,0,0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static short baseCheck[] = {0,
            3,0,3,1,3,0,3,1,1,1,
            2,1,1,5,5,2,0,3,0,3,
            2,0,1,0,1,0,4,0,2,1,
            1,1,1,5,5,5,5,1,1,4,
            0,1,2,1,1,1,1,1,1,1,
            1,1,1,1,4,4,4,4,4,4,
            4,4,4,4,4,0,3,0,3,0,
            3,0,3,0,3,0,3,0,3,0,
            3,0,3,0,3,0,3,0,2,1,
            1,1,1,3,3,3,3,1,2,1,
            1,1,3,3,1,2,1,1,7,1,
            2,1,1,1,1,3,1,2,1,1,
            1,5,3,1,2,1,1,5,1,1,
            1,1,2,1,1,1,1,5,3,3,
            1,2,1,1,1,1,3,3,2,4,
            3,1,3,2,0,1,1,2,1,1,
            1,1,1,3,3,4,3,3,0,2,
            1,1,1,1,1,1,1,2,1,1,
            4,0,1,2,1,1,1,1,5,1,
            2,1,1,1,1,4,0,2,3,3,
            3,-30,-33,-68,1,-79,-3,2,3,4,
            5,6,-72,-34,9,10,11,12,13,7,
            8,-137,17,2,3,4,5,6,-61,-1,
            29,30,31,32,33,34,35,36,37,38,
            39,40,29,30,31,32,33,34,35,36,
            37,38,39,40,-77,-22,2,3,4,5,
            6,59,60,-89,61,2,3,4,5,6,
            64,-55,-25,19,20,-2,22,-81,-51,2,
            3,4,5,6,-7,84,9,10,11,12,
            13,62,63,-85,17,2,3,4,5,6,
            46,47,9,10,11,12,13,105,-11,-91,
            17,2,3,4,5,6,53,54,55,56,
            -103,93,2,3,4,5,6,72,19,20,
            -93,22,2,3,4,5,6,59,60,9,
            10,11,12,13,-46,73,74,17,76,-9,
            1,-112,98,2,3,4,5,6,87,-6,
            16,41,42,43,101,-20,57,58,-4,1,
            19,20,-118,22,2,3,4,5,6,14,
            -53,9,10,11,12,13,-12,1,-24,1,
            -123,-31,2,3,4,5,6,46,47,9,
            10,11,12,13,-127,-119,2,3,4,5,
            6,102,24,9,10,11,12,13,-134,14,
            2,3,4,5,6,-73,67,2,3,4,
            5,6,-15,1,66,-5,-10,19,20,-136,
            22,2,3,4,5,6,-35,1,9,10,
            11,12,13,-83,-59,2,3,4,5,6,
            25,26,27,-36,1,85,41,42,43,104,
            79,80,81,82,83,57,58,-130,-120,2,
            3,4,5,6,-75,-57,2,3,4,5,
            6,-87,14,2,3,4,5,6,-50,1,
            65,48,49,50,68,-124,70,2,3,4,
            5,6,-110,-58,2,3,4,5,6,-17,
            -128,96,2,3,4,5,6,-28,44,45,
            53,54,55,56,-142,-47,2,3,4,5,
            6,-8,51,52,-138,91,2,3,4,5,
            6,16,99,48,49,50,44,45,-139,61,
            2,3,4,5,6,-101,25,26,27,-16,
            1,51,52,-140,89,2,3,4,5,6,
            15,97,-48,-152,1,-27,-56,-74,69,-54,
            71,100,-76,24,7,8,-60,28,16,7,
            8,-78,75,90,77,-80,108,24,7,8,
            -82,28,7,8,-84,-49,-86,7,8,-88,
            -62,7,8,7,8,-90,7,8,-92,-131,
            -132,16,7,8,-133,7,8,-172,-37,1,
            -63,107,-38,1,-39,1,-40,1,-41,1,
            21,21,23,23,-171,21,-13,23,21,-14,
            23,-42,1,78,-43,1,-44,1,-45,1,
            -94,106,-52,1,88,86,-95,-96,-97,-99,
            1,-100,1,-102,1,15,-104,-105,92,-108,
            -109,15,15,15,-115,1,-117,1,-121,-129,
            -135,1,-157,-18,18,18,-19,18,18,-21,
            -23,-26,14,14,94,-29,14,-32,-64,-65,
            -66,-67,-69,-70,-71,-98,-106,-107,-111,-113,
            -114,-116,-122,-125,-126,95,-141,-143,-144,-145,
            -146,-147,-148,-149,-150,-151,-153,-154,-155,-156,
            -158,-159,-160,-161,-162,-163,-164,103,-165,-166,
            -167,-168,-169,-170,-173,-174,-175,-176,-177,-178,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0
        };
    };
    public final static short baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static short rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            62,62,63,63,66,66,64,64,67,67,
            65,65,25,25,26,27,68,70,70,69,
            69,73,74,74,75,75,71,71,78,78,
            79,79,79,79,80,81,82,83,16,16,
            72,84,84,84,29,29,29,29,29,29,
            29,29,29,29,29,30,31,32,33,34,
            35,36,37,38,39,40,85,85,86,86,
            87,87,88,88,89,89,90,90,91,91,
            92,92,93,93,94,94,95,95,15,15,
            2,2,2,2,3,4,5,6,96,96,
            41,41,41,42,43,97,97,44,44,45,
            98,98,46,46,46,46,47,99,99,48,
            48,48,49,50,100,100,51,51,52,103,
            103,103,101,101,53,53,53,53,54,55,
            56,102,102,57,57,57,57,58,20,20,
            22,19,28,28,24,104,104,17,17,9,
            9,9,9,9,12,13,13,10,11,7,
            7,8,8,1,18,18,14,21,21,23,
            23,76,105,105,105,60,60,60,60,59,
            106,106,107,107,107,107,77,108,108,108,
            61,61,12,303,305,724,140,17,163,90,
            91,92,93,247,1,157,159,160,161,162,
            55,684,279,373,89,90,91,92,93,346,
            13,42,44,45,46,47,48,49,50,51,
            52,53,54,43,44,45,46,47,48,49,
            50,51,52,53,54,122,88,115,90,91,
            92,93,183,673,195,726,137,90,91,92,
            93,436,334,137,112,113,62,731,140,256,
            163,90,91,92,93,181,214,157,159,160,
            161,162,276,207,140,391,163,90,91,92,
            93,110,114,157,159,160,161,162,279,14,
            153,405,146,90,91,92,93,132,134,135,
            136,20,620,102,90,91,92,93,273,144,
            145,140,731,163,90,91,92,93,184,673,
            157,159,160,161,162,258,20,528,440,23,
            3,657,2,352,115,90,91,92,93,593,
            128,719,99,100,101,478,53,141,143,3,
            4,112,113,52,731,163,90,91,92,93,
            156,331,158,159,160,161,162,3,5,3,
            366,65,252,163,90,91,92,93,111,114,
            158,159,160,161,162,78,375,163,90,91,
            92,93,419,153,158,159,160,161,162,91,
            748,146,90,91,92,93,218,309,102,90,
            91,92,93,3,704,285,75,84,144,145,
            109,731,163,90,91,92,93,3,381,158,
            159,160,161,162,228,343,121,90,91,92,
            93,10,12,13,3,590,213,98,100,101,
            154,29,30,31,32,33,142,143,171,375,
            137,90,91,92,93,160,341,108,90,91,
            92,93,262,749,127,90,91,92,93,79,
            724,542,117,119,120,660,209,433,121,90,
            91,92,93,237,342,108,90,91,92,93,
            150,246,321,127,90,91,92,93,311,105,
            107,133,134,135,136,129,258,89,90,91,
            92,93,229,124,126,285,615,89,90,91,
            92,93,720,506,118,119,120,106,107,291,
            723,89,90,91,92,93,100,11,12,13,
            3,366,125,126,297,606,89,90,91,92,
            93,535,513,258,3,366,92,339,247,710,
            332,256,521,247,152,56,684,344,707,721,
            57,684,247,21,611,25,247,204,152,58,
            684,247,762,59,684,247,258,247,60,684,
            247,351,61,684,62,684,247,63,684,247,
            225,225,722,64,684,225,65,684,225,3,
            272,353,191,3,587,3,486,3,514,3,
            455,755,757,756,756,135,759,201,756,768,
            202,756,3,597,392,3,229,3,621,3,
            641,100,737,3,725,602,588,100,100,100,
            3,201,3,200,3,170,222,106,106,617,
            106,106,545,559,574,3,746,3,747,375,
            375,3,760,375,190,738,739,97,742,743,
            319,310,327,750,754,626,329,763,330,354,
            355,356,363,313,260,364,365,367,368,11,
            371,372,374,280,376,377,629,382,378,383,
            387,390,394,396,141,398,402,404,406,408,
            410,412,219,414,416,392,417,418,767,420,
            422,425,159,428,424,430,431,436,438,440,
            441,821,0,447,41,0,464,41,0,639,
            41,0,643,41,0,645,41,0,647,41,
            0,649,41,0,662,41,0,665,41,0,
            667,41,0,669,41,0,1009,182,0,1008,
            182,0,1007,182,0,1006,182,0,994,197,
            0
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,0,0,2,3,4,5,7,7,8,
            0,0,0,0,1,14,0,16,17,0,
            10,2,3,4,5,23,7,8,9,29,
            30,31,32,33,34,35,36,37,38,39,
            29,30,31,32,33,34,35,36,37,38,
            39,0,0,2,3,4,5,45,7,8,
            9,0,11,12,0,49,2,3,4,5,
            18,7,8,9,0,11,12,0,0,2,
            3,4,5,0,7,8,9,0,11,12,
            0,0,2,3,4,5,0,7,8,0,
            26,23,28,7,14,0,16,17,0,13,
            2,3,4,5,27,7,8,9,0,11,
            12,0,61,2,3,4,5,0,0,8,
            2,3,4,5,0,14,0,16,17,0,
            0,2,3,4,5,40,41,8,9,0,
            11,12,0,13,2,3,4,5,0,0,
            8,2,3,4,5,7,14,8,16,17,
            0,13,2,3,4,5,27,7,8,9,
            0,1,55,56,48,15,52,53,54,0,
            62,63,64,65,0,46,2,3,4,5,
            0,0,8,9,0,25,6,6,0,15,
            2,3,4,5,25,7,8,0,0,2,
            3,4,5,15,0,8,9,0,0,2,
            3,4,5,0,10,8,0,0,2,3,
            4,5,15,7,8,0,0,2,3,4,
            5,0,7,8,26,0,28,0,7,0,
            42,0,7,2,3,4,5,43,44,8,
            19,20,21,22,19,20,21,22,0,0,
            2,3,4,5,0,7,2,3,4,5,
            0,7,2,3,4,5,0,7,2,3,
            4,5,0,7,0,59,60,50,51,0,
            0,7,0,1,0,6,57,58,0,40,
            41,19,20,21,22,7,0,23,0,0,
            0,0,6,0,6,6,6,6,0,6,
            0,0,0,0,6,0,6,6,6,6,
            0,6,0,0,0,0,6,47,6,6,
            6,6,0,0,0,1,0,0,6,6,
            0,0,1,0,0,0,0,0,1,6,
            10,0,0,1,18,18,0,1,7,0,
            1,0,18,0,1,0,1,0,1,24,
            24,0,1,0,1,0,1,0,1,0,
            1,0,1,0,1,0,0,0,1,0,
            1,0,1,0,0,10,10,0,1,0,
            0,1,0,42,10,0,1,0,1,0,
            0,1,13,0,0,0,0,24,0,10,
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
            821,821,821,691,690,728,727,861,892,697,
            821,41,2,821,828,730,6,695,732,821,
            744,691,690,728,727,994,888,688,687,447,
            464,639,643,645,647,649,662,665,667,669,
            773,776,779,782,785,788,791,794,797,800,
            803,821,155,691,690,728,727,369,894,406,
            699,821,733,479,821,360,691,690,728,727,
            997,896,406,699,821,733,479,821,197,691,
            690,728,727,17,900,406,699,821,733,479,
            821,28,691,690,728,727,821,906,701,88,
            437,818,350,835,730,821,695,732,821,389,
            691,690,728,727,712,908,406,699,821,733,
            479,821,772,691,690,728,727,821,190,697,
            691,690,728,727,821,730,22,695,732,821,
            821,691,690,728,727,995,996,406,699,19,
            733,479,821,761,691,690,728,727,821,821,
            701,691,690,728,727,971,730,729,695,732,
            821,389,691,690,728,727,26,904,635,631,
            821,824,829,830,716,630,951,952,950,16,
            1013,1014,1015,1016,821,711,691,690,728,727,
            821,821,635,631,821,387,570,520,821,630,
            691,690,728,727,839,898,735,821,821,691,
            690,728,727,734,821,688,687,821,1,691,
            690,728,727,821,998,735,821,821,691,690,
            728,727,734,890,729,821,169,691,690,728,
            727,821,902,700,437,821,350,821,848,821,
            764,821,1002,691,690,728,727,1000,1001,700,
            616,583,536,345,1006,1007,1008,1009,821,821,
            691,690,728,727,821,858,691,690,728,727,
            821,857,691,690,728,727,821,856,691,690,
            728,727,182,855,821,992,993,859,860,821,
            24,1017,821,1019,821,586,682,680,821,703,
            751,815,812,809,806,836,821,994,821,821,
            66,68,202,70,203,499,426,485,72,255,
            74,76,78,80,206,82,278,454,294,492,
            84,264,86,821,821,821,310,718,331,671,
            677,678,821,821,821,1020,821,821,679,566,
            821,821,970,821,821,821,821,821,924,584,
            745,821,821,925,740,741,821,918,1010,821,
            917,821,997,821,916,821,915,821,972,752,
            753,821,969,821,937,821,988,821,989,821,
            985,821,986,821,944,821,821,821,960,821,
            961,821,968,821,821,655,999,821,987,821,
            821,943,821,638,765,821,949,821,959,821,
            821,930,769,821,821,821,821,766,821,770
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static char asb[] = {0,
            1,93,2,85,3,42,18,91,85,85,
            19,85,123,123,85,85,87,122,46,45,
            73,89,123,85,14,123,105,15,123,62,
            105,123,105,62,85,85,85,85,85,85,
            85,85,85,85,85,29,29,29,29,84,
            105,85,59,59,59,59,59,59,59,59,
            59,59,59,123,123,123,123,84,19,125,
            123,60,52,60,53,60,33,60,50,60,
            50,60,7,60,50,60,53,60,6,60,
            33,60,50,99,99,99,99,19,85,85,
            95,85,21,119,119,128,128,119,119,77,
            113,32,113,19,85,123,85,49,128,128,
            128,119,49,76,130,130,49,77,128,75,
            111,111,111,32,85,49,99,99,99,99,
            73,95,19,19,19,19,19,19,47,19,
            19,85,19,19,19,19,128,19,132,19,
            113,132,113,19,19,19,113,46,19,130,
            115,111,47,19,19,19,113,19
        };
    };
    public final static char asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            45,49,26,28,0,9,2,3,4,5,
            8,15,0,48,7,47,0,25,1,0,
            9,8,5,4,3,2,7,0,50,51,
            0,7,2,3,4,5,8,17,16,14,
            0,55,56,0,18,7,13,0,7,11,
            12,9,2,3,4,5,8,0,6,59,
            60,39,38,37,36,35,34,33,32,31,
            30,29,7,0,9,15,8,5,4,3,
            2,7,0,7,23,0,7,46,27,0,
            26,28,61,0,62,63,64,65,7,2,
            3,4,5,0,7,19,20,21,22,0,
            43,44,10,0,54,52,53,0,40,41,
            0,25,6,0,57,58,0,18,0,24,
            0,42,0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static byte nasb[] = {0,
            17,83,8,48,63,44,83,100,82,65,
            83,82,83,83,82,82,90,83,83,46,
            83,23,83,82,29,83,102,95,83,1,
            54,83,3,13,82,82,82,82,82,82,
            82,82,82,82,82,39,39,39,39,80,
            31,82,52,106,27,104,76,88,70,108,
            15,110,115,83,83,83,83,81,83,83,
            83,10,61,10,74,10,20,10,6,10,
            6,10,68,10,6,10,78,10,25,10,
            34,10,6,119,119,119,119,83,82,82,
            119,82,37,121,121,83,83,121,121,86,
            83,41,83,83,82,83,82,50,56,56,
            56,83,50,84,83,83,50,93,56,72,
            112,112,112,58,82,50,98,98,98,98,
            83,97,83,83,83,83,83,83,83,83,
            83,82,83,83,83,83,56,83,83,83,
            83,83,83,83,83,83,83,83,83,83,
            117,112,83,83,83,83,83,83
        };
    };
    public final static byte nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static char nasr[] = {0,
            84,0,60,105,0,17,0,64,0,8,
            7,0,29,0,93,0,63,62,0,22,
            98,0,72,0,101,0,87,0,74,0,
            60,59,0,22,102,0,41,0,16,0,
            22,46,0,67,0,104,0,66,0,9,
            0,85,0,79,0,14,0,22,57,0,
            96,0,65,0,70,68,0,99,0,91,
            0,53,0,97,0,89,0,100,0,108,
            61,1,0,48,0,44,0,90,0,71,
            69,0,51,0,75,0,107,2,0,25,
            0,78,0,88,0,86,0,92,0,94,
            0,23,21,0,95,0,103,0,15,0,
            18,0
        };
    };
    public final static char nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            3,41,42,44,47,8,9,38,40,62,
            39,49,1,37,46,48,50,63,19,20,
            21,22,61,64,2,12,14,16,25,26,
            27,28,29,30,31,32,33,34,35,51,
            52,65,4,5,11,13,15,17,18,23,
            24,36,43,45,53,54,55,56,57,58,
            59,66,67,68,69,6,7,60,70
        };
    };
    public final static byte terminalIndex[] = TerminalIndex.terminalIndex;
    public final int terminalIndex(int index) { return terminalIndex[index]; }

    public interface NonterminalIndex {
        public final static byte nonterminalIndex[] = {0,
            74,91,0,0,0,0,0,106,105,0,
            0,0,0,98,0,81,86,92,0,0,
            101,103,107,104,76,0,0,77,82,0,
            0,0,0,0,0,0,0,0,0,0,
            93,0,0,94,0,95,0,96,0,0,
            97,0,100,0,0,0,102,0,108,109,
            111,71,0,0,72,73,75,78,0,0,
            0,79,0,0,0,0,0,0,80,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,83,84,85,87,88,
            89,90,99,0,0,0,110,0,0
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
            "DIRECTORY",
            "DIRLIST",
            "DOUBLE",
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
            "booleanCustomSpec",
            "intCustomSpec",
            "radioCustomSpec",
            "stringCustomSpec",
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
            "tabSpec",
            "inout",
            "fieldSpec",
            "booleanSpecificSpecs",
            "colorSpecificSpecs",
            "comboSpecificSpecs",
            "stringSpecificSpecs",
            "doubleSpecificSpecs",
            "fontSpecificSpecs",
            "intSpecificSpecs",
            "radioSpecificSpecs",
            "generalSpec",
            "booleanValue",
            "booleanSpecificSpec",
            "colorSpecificSpec",
            "comboSpecificSpec",
            "doubleSpecificSpec",
            "fontSpecificSpec",
            "stringValue",
            "fontStyle",
            "intSpecificSpec",
            "signedNumber",
            "radioSpecificSpec",
            "valuesSpec",
            "labelledStringValue",
            "stringSpecificSpec",
            "conditionType",
            "sign",
            "customRule",
            "tab",
            "typeCustomSpecs",
            "conditionalSpec"
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
