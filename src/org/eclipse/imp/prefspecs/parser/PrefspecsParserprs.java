
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
    public final static int ERROR_SYMBOL = 66;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = -1;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 0;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 23;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 181;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 66;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 1034;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 201;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 109;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 175;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 283;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 62;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 62;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 784;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 833;
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
            0,0,1,0,0,0,0,0,0,0,
            1,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,1,1,
            0,0,0,0,1,1,1,0,1,1,
            1,0,0,1,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,0,1,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            64,65,66,67,68,7,45,101,99,100,
            84,96,97,98,30,69,29,59,76,77,
            78,90,92,102,71,87,88,94,9,10,
            11,12,32,33,34,35,36,37,38,39,
            40,41,42,43,70,72,73,74,75,79,
            80,81,82,83,86,89,91,93,104,105,
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
            0,0,0,0,0,0
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
            1,2,1,1,1,1,3,2,4,2,
            1,3,1,3,2,0,1,1,2,1,
            1,1,1,1,3,3,4,3,3,0,
            2,1,1,1,1,1,1,1,2,1,
            1,4,0,1,2,1,1,1,1,5,
            1,2,1,2,2,4,0,2,3,3,
            3,-32,-81,1,2,3,4,5,-53,-63,
            -74,9,10,-36,12,13,14,7,8,-3,
            18,-138,1,2,3,4,5,-2,-77,1,
            2,3,4,5,33,34,35,36,37,38,
            39,40,41,42,43,44,33,34,35,36,
            37,38,39,40,41,42,43,44,-79,1,
            2,3,4,5,-106,-59,-6,59,60,-75,
            1,2,3,4,5,47,48,19,20,21,
            16,23,-1,64,-48,84,-83,1,2,3,
            4,5,-60,-35,25,9,10,-9,12,13,
            14,17,93,6,18,-8,-7,49,-87,1,
            2,3,4,5,45,46,-76,9,10,-24,
            12,13,14,7,8,97,18,-93,1,2,
            3,4,5,67,29,30,31,-91,1,2,
            3,4,5,-5,62,63,19,20,21,-4,
            23,-49,59,60,89,6,98,-95,1,2,
            3,4,5,26,27,96,9,10,17,12,
            13,14,29,30,31,18,-114,1,2,3,
            4,5,90,-20,57,-85,1,2,3,4,
            5,72,55,56,11,19,20,21,105,23,
            -120,1,2,3,4,5,-11,-29,65,9,
            10,-12,12,13,14,66,-56,6,-125,1,
            2,3,4,5,-62,49,-13,9,10,102,
            12,13,14,-10,-169,50,51,52,101,-129,
            1,2,3,4,5,11,-17,-14,9,10,
            16,12,13,14,-136,1,2,3,4,5,
            -89,1,2,3,4,5,-33,-132,1,2,
            3,4,5,19,20,21,-167,23,-137,1,
            2,3,4,5,99,78,10,9,10,-18,
            12,13,14,26,27,-143,1,2,3,4,
            5,68,86,70,9,-105,1,2,3,4,
            5,57,-52,53,54,69,92,71,6,-27,
            25,26,55,56,-126,1,2,3,4,5,
            25,-112,1,2,3,4,5,-130,1,2,
            3,4,5,-57,-30,79,80,81,82,83,
            45,46,-139,1,2,3,4,5,-58,-19,
            100,-140,1,2,3,4,5,-141,1,2,
            3,4,5,61,50,51,52,-16,47,48,
            -153,-70,-26,6,-50,-21,6,6,6,-103,
            53,54,73,74,-133,76,11,-15,-64,-78,
            -61,17,107,6,15,28,7,8,28,32,
            28,-51,32,-55,-65,-80,22,-82,24,75,
            108,77,7,8,7,8,-84,-22,17,-86,
            87,-174,-88,7,8,58,7,8,58,7,
            8,-90,61,-92,-134,-94,88,-135,7,8,
            7,8,7,8,-175,-37,-38,-39,-40,-41,
            -23,6,6,6,6,6,22,-42,24,22,
            -25,24,-96,6,-43,-44,22,-45,24,-46,
            6,6,-47,6,-54,6,-97,15,6,104,
            6,91,94,-98,-101,106,-99,-102,85,-104,
            6,15,-107,6,-110,6,-111,-117,15,95,
            -119,15,-121,6,-122,-123,6,-131,16,-158,
            16,-168,16,11,-28,11,11,-31,11,-34,
            11,-66,-67,-68,103,-69,-71,-72,-73,-100,
            -108,-109,-113,-115,-116,-118,-124,-127,27,-128,
            -142,-144,-145,-146,-147,-148,-149,-150,-151,-152,
            -154,-155,-156,-157,-159,-160,-161,-162,-163,-164,
            -165,-166,-170,-171,-172,-173,-176,-177,-178,-179,
            -180,-181,0,0,0,0,0,0,0,0,
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
            0
        };
    };
    public final static short baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static short rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            62,62,63,63,66,66,64,64,67,67,
            65,65,29,29,30,31,68,70,70,69,
            69,73,74,74,75,75,71,71,78,78,
            79,79,79,79,80,81,82,83,17,17,
            72,84,84,84,33,33,33,33,33,33,
            33,33,33,33,33,34,35,36,37,38,
            39,40,41,42,43,44,85,85,86,86,
            87,87,88,88,89,89,90,90,91,91,
            92,92,93,93,94,94,95,95,15,15,
            1,1,1,1,2,3,4,5,96,96,
            45,45,45,25,46,97,97,47,47,48,
            98,98,49,49,49,49,21,99,99,50,
            50,50,51,52,100,100,53,53,54,103,
            103,103,101,101,55,55,55,55,26,27,
            56,102,102,57,57,57,57,20,20,23,
            32,32,19,58,58,28,104,104,18,18,
            12,12,12,12,12,9,10,10,13,14,
            7,7,8,8,6,16,16,11,22,22,
            24,24,76,105,105,105,60,60,60,60,
            59,106,106,107,107,107,77,108,108,108,
            61,61,12,144,164,90,91,92,93,207,
            342,238,162,163,1,158,160,161,55,700,
            246,401,237,89,90,91,92,93,64,208,
            108,90,91,92,93,42,44,45,46,47,
            48,49,50,51,52,53,54,43,44,45,
            46,47,48,49,50,51,52,53,54,124,
            115,90,91,92,93,276,334,231,185,685,
            22,102,90,91,92,93,105,107,112,113,
            114,752,745,55,344,167,214,144,164,90,
            91,92,93,337,254,100,162,163,154,158,
            160,161,732,642,427,419,262,115,110,144,
            164,90,91,92,93,98,101,238,162,163,
            286,158,160,161,56,700,532,440,124,146,
            90,91,92,93,407,11,12,13,166,137,
            90,91,92,93,80,228,220,144,145,143,
            154,745,167,184,685,618,4,377,144,164,
            90,91,92,93,134,135,506,162,163,733,
            158,160,161,10,12,13,479,2,115,90,
            91,92,93,627,297,141,185,121,90,91,
            92,93,520,132,136,150,112,113,114,209,
            745,54,164,90,91,92,93,293,88,306,
            162,163,154,159,160,161,307,329,5,66,
            164,90,91,92,93,341,111,209,162,163,
            455,159,160,161,56,255,117,119,120,468,
            78,164,90,91,92,93,764,158,289,162,
            163,752,159,160,161,90,146,90,91,92,
            93,228,127,90,91,92,93,202,156,137,
            90,91,92,93,144,145,143,415,745,114,
            164,90,91,92,93,525,467,195,162,163,
            283,159,160,161,134,135,194,89,90,91,
            92,93,448,317,598,477,11,102,90,91,
            92,93,142,313,124,126,661,633,320,738,
            304,193,722,133,136,176,121,90,91,92,
            93,100,99,108,90,91,92,93,133,127,
            90,91,92,93,330,21,29,30,31,32,
            33,99,101,245,89,90,91,92,93,333,
            302,538,253,89,90,91,92,93,261,89,
            90,91,92,93,737,118,119,120,270,106,
            107,270,281,154,586,167,303,586,738,586,
            92,125,126,20,545,186,23,157,154,345,
            238,338,734,192,490,496,153,57,700,153,
            560,154,167,774,325,346,238,769,238,770,
            21,582,25,58,700,59,700,238,247,736,
            238,600,223,238,60,700,628,61,700,628,
            62,700,238,740,238,186,238,616,186,63,
            700,64,700,65,700,186,154,154,154,154,
            154,309,614,417,544,559,266,771,154,770,
            772,314,770,92,293,154,154,780,154,770,
            154,601,425,154,210,154,599,92,222,615,
            155,739,630,644,92,154,751,92,154,211,
            154,201,553,276,200,276,171,276,154,562,
            646,154,568,297,760,297,297,761,297,753,
            297,756,419,757,762,318,763,764,319,768,
            322,775,349,353,354,779,357,360,76,362,
            365,358,363,3,368,369,371,282,350,194,
            367,379,374,380,383,385,387,389,391,394,
            396,398,400,402,404,408,101,410,406,392,
            412,413,417,420,421,431,423,425,435,437,
            439,433,444,833,0,656,41,0,657,41,
            0,658,41,0,659,41,0,660,41,0,
            668,41,0,675,41,0,676,41,0,678,
            41,0,680,41,0,683,41,0,1022,183,
            0,1021,183,0,1020,183,0,1019,183,0,
            1007,197,0
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,0,0,3,3,4,5,6,7,8,
            0,0,10,3,4,5,6,7,8,9,
            0,0,21,22,23,4,5,6,7,8,
            9,31,32,33,34,35,36,37,38,39,
            40,41,31,32,33,34,35,36,37,38,
            39,40,41,0,0,0,3,4,5,6,
            7,8,9,0,11,0,13,47,3,4,
            5,6,7,8,9,0,11,0,13,0,
            3,4,5,6,7,8,9,0,11,0,
            13,0,3,4,5,6,7,8,0,45,
            0,3,4,5,6,7,8,28,0,30,
            21,22,23,0,0,1,3,4,5,6,
            7,8,9,0,11,62,13,4,5,6,
            7,8,0,58,59,3,4,5,6,7,
            8,27,42,0,21,22,23,4,5,6,
            7,8,9,0,11,0,13,0,3,4,
            5,6,7,8,9,0,0,12,15,4,
            5,6,7,8,9,0,0,12,3,4,
            5,6,7,8,0,0,29,12,4,5,
            6,7,8,0,0,10,12,4,5,6,
            7,0,9,46,3,12,0,0,0,3,
            2,4,5,6,7,8,50,51,17,18,
            19,20,0,17,18,19,20,0,43,44,
            0,4,5,6,7,8,0,0,0,3,
            4,5,6,7,0,0,0,3,4,5,
            6,7,0,0,0,3,4,5,6,7,
            0,0,16,3,4,5,6,7,14,0,
            17,18,19,20,52,0,54,55,24,25,
            0,0,0,3,15,0,56,57,0,28,
            2,30,0,1,49,15,0,60,61,24,
            25,0,0,0,3,24,25,0,0,27,
            14,3,0,0,29,2,14,0,0,2,
            2,0,53,2,0,0,2,15,0,0,
            2,2,0,0,2,2,0,0,2,2,
            0,0,2,2,0,0,2,2,0,0,
            2,48,0,0,2,2,0,0,2,0,
            1,0,0,2,0,1,0,0,0,1,
            0,14,2,0,1,26,14,10,0,0,
            1,3,0,1,0,1,0,1,0,1,
            0,0,26,0,1,0,1,0,1,0,
            1,0,1,0,1,0,16,0,1,0,
            1,0,0,1,0,10,0,1,0,0,
            0,10,0,3,0,11,0,9,0,10,
            0,1,0,42,0,1,0,1,0,1,
            16,0,10,0,1,0,0,0,26,0,
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
            833,833,833,873,904,707,705,742,741,711,
            833,41,758,900,707,705,742,741,703,265,
            24,833,744,708,746,707,705,742,741,703,
            265,656,657,658,659,660,668,675,676,678,
            680,683,785,788,791,794,797,800,803,806,
            809,812,815,833,2,17,906,707,705,742,
            741,713,716,833,747,833,715,730,908,707,
            705,742,741,713,716,833,747,833,715,833,
            912,707,705,742,741,713,716,28,747,833,
            715,88,918,707,705,742,741,711,833,350,
            833,902,707,705,742,741,743,434,833,298,
            744,708,746,833,833,836,920,707,705,742,
            741,713,716,833,747,784,715,707,705,742,
            741,711,833,698,695,914,707,705,742,741,
            718,412,776,833,744,708,746,707,705,742,
            741,713,716,833,747,833,715,19,916,707,
            705,742,741,648,645,833,833,595,1007,707,
            705,742,741,648,645,833,833,595,910,707,
            705,742,741,750,833,833,26,748,707,705,
            742,741,750,191,833,1011,748,707,705,742,
            741,833,435,671,860,595,833,833,833,1015,
            578,707,705,742,741,743,871,872,612,585,
            352,285,833,1019,1020,1021,1022,833,1013,1014,
            833,707,705,742,741,718,833,170,833,870,
            707,705,742,741,833,6,151,869,707,705,
            742,741,833,183,833,868,707,705,742,741,
            833,1,583,867,707,705,742,741,1010,833,
            827,824,821,818,963,833,964,962,1008,1009,
            833,833,16,1029,1007,833,841,842,833,434,
            447,298,833,840,267,1007,833,1005,1006,1008,
            1009,833,156,22,847,720,765,833,833,851,
            1010,848,197,833,725,408,1010,833,833,202,
            294,833,384,513,66,833,270,830,68,70,
            229,259,72,74,203,287,76,78,386,309,
            80,82,461,338,84,86,328,358,833,833,
            673,728,833,833,687,694,833,833,697,833,
            1031,833,833,590,833,1032,833,833,833,981,
            833,754,581,833,936,766,755,759,833,833,
            937,1023,833,930,833,929,833,928,833,927,
            833,833,767,833,985,833,980,833,949,833,
            1001,833,1002,833,998,833,773,833,999,833,
            956,833,833,972,833,632,833,973,833,833,
            833,1012,833,982,833,747,833,645,833,777,
            833,1000,833,655,833,955,833,961,833,971,
            781,833,782,833,942,833,833,833,778
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static char asb[] = {0,
            4,14,5,86,6,1,16,12,86,86,
            17,86,75,75,86,92,59,74,125,37,
            95,96,125,61,75,86,88,75,49,89,
            75,114,49,75,49,114,86,86,86,86,
            86,86,86,86,86,86,86,9,9,9,
            9,85,49,86,111,111,111,111,111,111,
            111,111,111,111,111,75,75,75,75,85,
            17,131,75,112,42,112,43,112,28,112,
            40,112,40,112,20,112,40,112,43,112,
            19,112,28,112,40,66,66,66,66,17,
            86,86,99,86,77,56,56,37,37,56,
            56,65,109,27,109,17,86,75,86,39,
            37,37,37,56,39,64,134,134,39,65,
            37,63,107,107,107,27,39,66,66,66,
            66,125,99,17,17,17,17,17,17,97,
            17,17,92,17,17,17,17,37,17,136,
            17,109,136,109,17,17,72,83,55,109,
            125,17,134,127,107,97,17,17,17,109,
            17
        };
    };
    public final static char asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            56,57,0,45,49,28,30,0,50,51,
            0,28,30,62,0,27,1,0,9,4,
            5,6,7,8,12,0,3,4,5,6,
            7,8,23,22,21,0,14,0,3,11,
            13,9,4,5,6,7,8,0,3,17,
            18,19,20,0,14,24,25,0,3,46,
            29,0,9,12,8,3,4,5,6,7,
            0,11,0,27,2,0,8,7,6,5,
            4,3,9,0,3,15,0,48,3,47,
            0,15,53,0,14,3,16,0,3,9,
            4,5,6,12,7,0,43,44,10,0,
            2,60,61,41,40,39,38,37,36,35,
            34,33,32,31,3,0,55,52,54,0,
            58,59,0,26,0,42,0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static char nasb[] = {0,
            28,90,15,49,47,24,90,38,95,66,
            90,95,90,90,95,95,72,90,90,54,
            111,90,90,40,90,95,97,90,60,107,
            90,1,80,90,35,13,95,95,95,95,
            95,95,95,95,95,95,95,31,31,31,
            31,93,5,95,120,62,105,109,22,33,
            118,64,8,116,122,90,90,90,90,94,
            90,90,90,10,26,10,17,10,19,10,
            3,10,3,10,56,10,3,10,78,10,
            45,10,42,10,3,126,126,126,126,90,
            95,95,126,95,91,70,70,90,90,70,
            70,101,90,51,90,90,95,90,95,58,
            54,54,54,90,58,99,90,90,58,103,
            54,82,113,113,113,75,58,89,89,89,
            89,90,86,90,90,90,90,90,90,90,
            90,90,95,90,90,90,90,54,90,90,
            90,90,90,90,90,90,84,128,69,90,
            90,90,90,124,113,90,90,90,90,90,
            90
        };
    };
    public final static char nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static char nasr[] = {0,
            84,0,18,0,60,59,0,93,0,8,
            7,0,33,0,64,0,97,0,23,98,
            0,89,0,67,0,96,0,63,62,0,
            17,0,90,0,60,105,0,29,0,72,
            0,23,102,0,101,0,65,0,66,0,
            23,49,0,11,0,99,0,12,0,78,
            0,86,0,92,0,70,68,0,11,16,
            0,71,69,0,23,57,0,100,0,79,
            0,55,0,10,0,26,9,107,1,0,
            45,0,108,61,6,0,74,0,50,0,
            47,0,53,0,87,0,75,0,88,0,
            104,0,24,22,0,94,0,91,0,85,
            0,95,0,103,0,15,0,27,0
        };
    };
    public final static char nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            3,8,9,42,43,45,48,38,41,63,
            40,47,50,64,62,1,19,20,21,22,
            37,49,51,52,53,65,2,12,14,16,
            25,26,27,28,29,30,31,32,33,34,
            35,66,4,5,11,13,15,17,18,23,
            24,36,39,44,46,54,55,56,57,58,
            59,60,6,7,61,67
        };
    };
    public final static byte terminalIndex[] = TerminalIndex.terminalIndex;
    public final int terminalIndex(int index) { return terminalIndex[index]; }

    public interface NonterminalIndex {
        public final static byte nonterminalIndex[] = {0,
            88,0,0,0,0,71,0,108,106,107,
            95,105,0,0,0,89,78,83,0,0,
            0,100,102,109,0,98,99,104,73,0,
            0,74,79,0,0,0,0,0,0,0,
            0,0,0,0,90,0,91,0,92,93,
            0,0,94,0,97,0,101,103,110,111,
            113,68,0,0,69,70,72,75,0,0,
            0,76,0,0,0,0,0,0,77,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,80,81,82,84,85,
            86,87,96,0,0,0,112,0,0
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
            "DYNAMIC",
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
            "staticOrDynamicValues",
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
            "intRangeSpec",
            "intSpecialSpec",
            "signedNumber",
            "radioSpecificSpec",
            "valuesSpec",
            "labelledStringValueList",
            "labelledStringValue",
            "stringSpecificSpec",
            "stringSpecialSpec",
            "stringEmptySpec",
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
