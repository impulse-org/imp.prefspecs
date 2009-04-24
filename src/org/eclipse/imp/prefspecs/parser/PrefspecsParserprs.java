
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

    public final static int LA_STATE_OFFSET = 998;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 199;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 109;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 178;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 228;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 61;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 61;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 750;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 799;
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
            5,3,1,2,1,1,5,1,1,1,
            1,2,1,1,1,1,5,3,3,1,
            2,1,1,1,3,3,2,4,3,1,
            3,2,0,1,1,2,1,1,1,1,
            1,3,3,4,3,3,0,2,1,1,
            1,1,1,1,1,2,1,1,4,0,
            1,2,1,1,1,1,5,1,2,1,
            1,1,1,4,0,2,3,3,3,-30,
            -9,1,-2,-79,-22,2,3,4,5,6,
            -72,-34,9,10,11,12,13,7,8,-73,
            17,2,3,4,5,6,-7,-1,29,30,
            31,32,33,34,35,36,37,38,39,40,
            29,30,31,32,33,34,35,36,37,38,
            39,40,-77,-11,2,3,4,5,6,-20,
            41,42,43,-142,-31,2,3,4,5,6,
            -13,19,20,14,22,-81,72,2,3,4,
            5,6,-51,84,9,10,11,12,13,62,
            63,-85,17,2,3,4,5,6,46,47,
            9,10,11,12,13,-8,-14,-93,17,2,
            3,4,5,6,-74,96,9,10,11,12,
            13,7,8,-89,17,2,3,4,5,6,
            25,26,27,-3,-112,-59,2,3,4,5,
            6,59,60,79,80,81,82,83,-12,1,
            98,-4,1,19,20,-118,22,2,3,4,
            5,6,-27,104,9,10,11,12,13,-137,
            107,2,3,4,5,6,53,54,55,56,
            46,47,-123,-57,2,3,4,5,6,-15,
            1,9,10,11,12,13,-127,64,2,3,
            4,5,6,-94,-6,9,10,11,12,13,
            -136,-18,2,3,4,5,6,66,15,9,
            10,11,12,13,101,-130,91,2,3,4,
            5,6,-75,-171,2,3,4,5,6,-87,
            78,2,3,4,5,6,-103,-131,2,3,
            4,5,6,-110,-19,2,3,4,5,6,
            -128,-56,2,3,4,5,6,-21,21,-25,
            23,67,89,-91,-50,1,44,45,53,54,
            55,56,-10,-134,-83,-35,1,41,42,43,
            51,52,19,20,-33,22,-28,44,45,-17,
            -36,1,19,20,-138,22,2,3,4,5,
            6,51,52,-139,-124,2,3,4,5,6,
            -140,-5,2,3,4,5,6,-16,1,97,
            57,58,48,49,50,61,103,-152,1,100,
            57,58,73,74,-46,76,25,26,27,88,
            68,24,70,59,60,28,-24,1,-68,1,
            16,24,48,49,50,28,-37,1,69,-76,
            71,75,-53,77,-54,102,7,8,-38,1,
            24,-78,108,99,-80,-55,65,-82,7,8,
            -84,7,8,-86,7,8,-88,7,8,105,
            7,8,-90,7,8,-92,-101,-132,-133,7,
            8,-172,7,8,-39,1,-40,1,-58,61,
            -61,15,-41,1,-42,1,-43,1,21,21,
            23,23,21,-47,23,-44,1,-45,1,-48,
            -49,-52,1,-60,-62,-63,-95,-96,-97,16,
            -99,1,-100,1,-23,16,16,85,-102,1,
            86,15,15,15,-104,-105,-108,-109,-115,1,
            -117,1,87,-119,-120,-121,-129,-135,1,-157,
            -26,-29,18,18,18,18,-32,14,14,14,
            14,-64,-65,14,-66,-67,-69,-70,-71,-98,
            -106,-107,-111,-113,-114,-116,-122,-125,90,-126,
            -141,-143,106,93,-144,-145,-146,-147,-148,-149,
            -150,-151,-153,-154,-155,-156,-158,-159,-160,-161,
            -162,-163,-164,-165,-166,92,-167,-168,94,-169,
            95,-170,-173,-174,-175,-176,-177,-178,0,0,
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
            0,0,0,0,0,0,0
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
            48,49,50,100,100,51,51,52,103,103,
            103,101,101,53,53,53,53,54,55,56,
            102,102,57,57,57,58,20,20,22,19,
            28,28,24,104,104,17,17,9,9,9,
            9,9,12,13,13,10,11,7,7,8,
            8,1,18,18,14,21,21,23,23,76,
            105,105,105,60,60,60,60,59,106,106,
            107,107,107,107,77,108,108,108,61,61,
            12,279,271,130,97,284,161,90,91,92,
            93,222,1,155,157,158,159,160,55,659,
            150,356,102,90,91,92,93,137,178,42,
            44,45,46,47,48,49,50,51,52,53,
            54,43,44,45,46,47,48,49,50,51,
            52,53,54,82,13,115,90,91,92,93,
            283,98,100,101,119,185,89,90,91,92,
            93,91,112,113,154,705,97,470,161,90,
            91,92,93,189,212,155,157,158,159,160,
            203,334,97,383,161,90,91,92,93,110,
            114,155,157,158,159,160,245,139,97,397,
            161,90,91,92,93,222,447,155,157,158,
            159,160,56,659,127,411,136,90,91,92,
            93,11,12,13,275,2,325,115,90,91,
            92,93,182,642,29,30,31,32,33,279,
            5,335,279,4,112,113,17,705,161,90,
            91,92,93,15,152,156,157,158,159,160,
            227,189,89,90,91,92,93,131,133,134,
            135,111,114,52,318,161,90,91,92,93,
            279,412,156,157,158,159,160,62,522,161,
            90,91,92,93,27,170,156,157,158,159,
            160,72,203,161,90,91,92,93,227,370,
            156,157,158,159,160,426,107,591,136,90,
            91,92,93,190,161,108,90,91,92,93,
            212,265,126,90,91,92,93,140,85,102,
            90,91,92,93,160,265,108,90,91,92,
            93,169,315,126,90,91,92,93,285,731,
            286,732,254,585,246,300,698,105,107,132,
            133,134,135,11,221,171,279,573,99,100,
            101,123,125,143,144,180,705,293,106,107,
            253,279,575,143,144,236,705,89,90,91,
            92,93,124,125,244,261,89,90,91,92,
            93,252,92,89,90,91,92,93,279,260,
            454,140,142,117,119,120,697,745,279,260,
            461,141,142,20,497,3,23,10,12,13,
            582,307,150,390,181,642,455,279,260,267,
            698,692,150,118,119,120,738,279,586,468,
            222,205,21,303,25,308,484,57,659,279,
            462,151,222,559,515,222,314,306,222,58,
            659,222,59,659,222,60,659,222,61,659,
            283,62,659,222,63,659,222,27,85,85,
            64,659,85,65,659,279,384,279,619,319,
            700,329,264,279,336,279,644,279,621,733,
            734,732,732,746,3,732,279,645,279,646,
            3,3,279,699,326,330,333,27,27,27,
            693,279,199,279,198,289,695,696,211,279,
            168,315,505,514,521,266,266,266,266,279,
            722,279,723,570,357,357,357,357,279,735,
            357,292,295,712,715,718,719,296,724,725,
            726,730,336,337,740,341,342,345,94,347,
            350,304,348,352,353,354,356,272,359,588,
            360,361,367,711,597,369,371,373,376,378,
            380,381,385,387,389,391,394,396,365,398,
            400,401,402,403,405,408,594,411,274,603,
            413,606,415,416,417,419,422,424,425,799,
            0,486,41,0,501,41,0,567,41,0,
            579,41,0,615,41,0,617,41,0,623,
            41,0,625,41,0,627,41,0,636,41,
            0,638,41,0,985,180,0,984,180,0,
            983,180,0,982,180,0,970,195,0
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,0,0,3,3,4,5,6,7,8,
            0,0,0,1,0,14,0,16,17,3,
            4,5,6,7,8,9,0,11,12,29,
            30,31,32,33,34,35,36,37,38,39,
            29,30,31,32,33,34,35,36,37,38,
            39,0,50,51,3,4,5,6,7,8,
            9,0,11,12,3,4,5,6,7,8,
            9,0,11,12,3,4,5,6,7,8,
            9,0,11,12,0,4,5,6,7,8,
            0,0,2,0,10,14,0,16,17,0,
            4,5,6,7,8,9,0,11,12,3,
            4,5,6,7,8,9,0,26,0,28,
            0,15,4,5,6,7,0,43,44,0,
            4,5,6,7,8,9,0,1,0,0,
            2,15,3,4,5,6,7,8,9,0,
            57,58,0,4,5,6,7,8,9,0,
            0,25,3,4,5,6,7,8,0,0,
            0,3,4,5,6,7,8,0,8,0,
            62,63,64,65,0,15,0,3,0,0,
            61,3,0,4,5,6,7,8,19,20,
            21,22,0,19,20,21,22,19,20,21,
            22,0,52,53,54,4,5,6,7,8,
            0,0,45,3,55,56,0,25,8,3,
            4,5,6,7,14,0,16,17,3,4,
            5,6,7,0,0,0,3,4,5,6,
            7,0,0,8,3,4,5,6,7,14,
            0,16,17,3,0,0,0,3,8,3,
            26,0,28,0,0,15,3,13,0,27,
            59,60,0,0,0,0,13,3,0,23,
            2,0,0,2,0,0,2,2,46,0,
            18,23,0,0,2,40,41,0,0,2,
            27,40,41,0,0,2,2,0,0,2,
            2,18,23,49,0,0,2,2,0,0,
            2,2,0,48,2,0,0,2,2,47,
            0,0,2,2,0,1,0,0,2,0,
            1,0,0,0,1,0,0,2,0,0,
            0,10,10,3,0,18,0,1,0,1,
            0,1,0,1,18,0,1,0,1,0,
            0,1,24,24,0,1,0,1,0,1,
            0,1,13,0,1,0,1,0,1,0,
            0,0,0,1,0,1,42,0,1,10,
            0,10,0,1,0,0,0,1,0,1,
            10,0,1,0,0,1,0,0,13,0,
            0,0,0,10,0,0,0,0,24,0,
            0,0,42,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            799,799,799,839,870,668,667,702,701,671,
            17,41,799,806,28,704,799,669,706,872,
            668,667,702,701,674,676,88,707,675,486,
            501,567,579,615,617,623,625,627,636,638,
            751,754,757,760,763,766,769,772,775,778,
            781,799,837,838,874,668,667,702,701,674,
            676,799,707,675,878,668,667,702,701,674,
            676,799,707,675,886,668,667,702,701,674,
            676,799,707,675,799,668,667,702,701,671,
            799,799,528,799,974,704,799,669,706,799,
            668,667,702,701,674,676,799,707,675,882,
            668,667,702,701,609,608,799,483,188,201,
            799,448,668,667,702,701,799,976,977,799,
            668,667,702,701,609,608,799,802,799,799,
            500,448,866,668,667,702,701,666,665,799,
            653,651,799,668,667,702,701,666,665,799,
            799,349,868,668,667,702,701,703,799,799,
            799,880,668,667,702,701,677,2,710,180,
            989,990,991,992,799,708,799,826,799,799,
            750,978,799,668,667,702,701,703,793,790,
            787,784,16,641,640,634,545,982,983,984,
            985,799,928,929,927,668,667,702,701,677,
            799,167,352,884,807,808,799,817,678,836,
            668,667,702,701,704,799,669,706,835,668,
            667,702,701,799,1,799,834,668,667,702,
            701,799,19,678,833,668,667,702,701,704,
            799,669,706,876,799,799,799,813,710,993,
            483,799,201,799,6,708,947,557,799,26,
            968,969,153,799,799,22,557,814,799,970,
            363,799,24,200,799,799,495,475,655,195,
            973,970,66,799,220,971,972,68,799,433,
            681,680,727,70,72,253,204,74,76,276,
            485,716,796,405,78,80,292,440,82,84,
            324,474,86,682,308,799,799,404,647,687,
            799,799,648,649,799,995,799,799,607,799,
            996,799,799,799,946,799,799,538,799,799,
            799,720,721,986,799,717,799,902,799,903,
            799,896,799,895,973,799,894,799,893,799,
            799,948,728,729,799,945,799,915,799,964,
            799,965,737,799,961,799,962,799,921,799,
            799,799,799,937,799,938,742,799,944,434,
            799,975,799,963,799,799,799,920,799,926,
            743,799,936,799,799,908,799,799,747,799,
            799,799,799,748,799,799,799,799,744,799,
            799,799,612
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static char asb[] = {0,
            4,15,5,79,6,51,1,13,79,79,
            2,79,55,55,79,79,87,54,10,9,
            112,89,55,79,74,55,45,75,55,101,
            45,55,45,101,79,79,79,79,79,79,
            79,79,79,79,79,95,95,95,95,78,
            45,79,98,98,98,98,98,98,98,98,
            98,98,98,55,55,55,55,78,2,127,
            55,99,38,99,39,99,26,99,36,99,
            36,99,22,99,36,99,39,99,17,99,
            30,99,36,118,118,118,118,2,79,79,
            114,79,58,124,124,130,130,124,124,59,
            72,25,72,2,79,55,79,35,130,130,
            130,124,35,91,132,132,35,59,130,57,
            70,70,70,81,79,35,118,118,118,118,
            112,114,2,2,2,2,2,2,11,2,
            2,79,2,2,2,2,130,2,134,2,
            72,134,72,2,2,2,72,10,2,132,
            66,70,11,2,2,2,72,2
        };
    };
    public final static char asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            25,1,0,45,49,26,28,0,18,3,
            13,0,26,28,61,0,4,5,6,7,
            9,8,15,0,3,4,5,6,7,17,
            16,14,8,0,3,11,12,9,4,5,
            6,7,8,0,3,19,20,21,22,0,
            55,56,0,25,2,0,15,9,8,7,
            6,5,4,3,0,54,52,53,0,43,
            44,10,0,48,3,47,0,3,23,0,
            8,14,16,17,3,0,3,46,27,0,
            15,8,3,0,50,51,0,2,59,60,
            39,38,37,36,35,34,33,32,31,30,
            29,3,0,62,63,64,65,3,4,5,
            6,7,0,40,41,0,57,58,0,18,
            0,24,0,42,0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static byte nasb[] = {0,
            14,80,34,41,99,51,80,30,79,81,
            80,79,80,80,79,79,94,80,80,20,
            80,5,80,79,72,80,45,92,80,1,
            25,80,89,10,79,79,79,79,79,79,
            79,79,79,79,79,101,101,101,101,77,
            27,79,103,105,107,70,47,109,39,113,
            111,115,117,80,80,80,80,78,80,80,
            80,7,12,7,55,7,17,7,3,7,
            3,7,87,7,3,7,59,7,32,7,
            74,7,3,49,49,49,49,80,79,79,
            49,79,61,119,119,80,80,119,119,66,
            80,36,80,80,79,80,79,43,121,121,
            121,80,43,97,80,80,43,68,121,53,
            63,63,63,84,79,43,23,23,23,23,
            80,22,80,80,80,80,80,80,80,80,
            80,79,80,80,80,80,121,80,80,80,
            80,80,80,80,80,80,80,80,80,80,
            57,63,80,80,80,80,80,80
        };
    };
    public final static byte nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static char nasr[] = {0,
            84,0,17,0,72,0,8,7,0,29,
            0,96,0,63,62,0,22,98,0,104,
            0,107,2,0,79,0,60,59,0,25,
            0,101,0,64,0,22,46,0,91,0,
            66,0,9,0,78,0,89,0,15,0,
            67,0,53,0,97,0,103,0,100,0,
            41,0,23,21,0,44,0,51,0,88,
            0,74,0,22,102,0,108,61,1,0,
            70,68,0,22,57,0,99,0,60,105,
            0,75,0,71,69,0,48,0,65,0,
            16,0,85,0,86,0,87,0,90,0,
            93,0,92,0,94,0,95,0,18,0,
            14,0
        };
    };
    public final static char nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            3,8,9,41,42,44,47,38,40,62,
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
