
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

    public final static int NUM_STATES = 164;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 66;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 942;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 181;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 100;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 166;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 184;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 61;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 61;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 724;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 761;
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
            0,0,0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,1,1,0,0,0,
            0,1,1,0,1,1,0,1,0,0,
            0,0,0,1,1,1,1,1,1,1,
            1,1,1,1,1,0,0,0,0,0,
            0,0,0,1,1,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            8,61,62,63,64,65,42,97,93,94,
            95,96,81,27,56,66,73,74,75,87,
            89,98,91,5,10,11,12,13,29,30,
            31,32,33,34,35,36,37,38,39,40,
            67,68,69,70,71,72,76,77,78,79,
            80,83,84,85,86,88,90,100,2,3,
            4,6,7,9,14,15,16,17,18,19,
            20,21,22,23,24,25,26,28,41,43,
            44,45,46,47,48,49,50,51,52,53,
            54,55,57,58,59,60,82,92,99,1
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
            4,0,3,1,3,0,3,1,1,1,
            2,1,1,5,5,2,0,3,0,3,
            1,0,1,0,4,0,2,1,1,1,
            1,4,4,4,4,1,1,4,0,1,
            2,1,1,1,1,1,1,1,1,1,
            1,1,4,4,4,4,4,4,4,4,
            4,4,4,0,3,0,3,0,3,0,
            3,0,3,0,3,0,3,0,3,0,
            3,0,3,0,3,1,1,1,1,3,
            3,3,3,1,2,1,1,1,3,3,
            1,2,1,1,7,1,2,1,1,1,
            1,3,1,2,1,1,1,5,3,1,
            2,1,1,5,1,1,1,1,2,1,
            1,1,1,5,3,3,1,2,1,1,
            1,1,3,2,4,2,1,3,1,3,
            2,0,1,1,2,1,1,1,1,3,
            4,3,3,0,2,1,1,1,1,1,
            1,1,2,1,1,4,0,2,3,3,
            3,-45,-8,-1,-3,-95,-6,2,3,4,
            5,6,-29,-47,-93,-2,2,3,4,5,
            6,-19,17,18,19,13,21,25,26,27,
            29,30,31,32,33,34,35,36,37,38,
            39,40,29,30,31,32,33,34,35,36,
            37,38,39,40,-81,-66,2,3,4,5,
            6,56,59,60,62,61,52,53,54,55,
            64,17,18,19,-7,21,-37,1,-83,78,
            2,3,4,5,6,-4,1,9,10,11,
            12,-5,-9,15,73,74,75,76,77,23,
            46,96,-85,-62,2,3,4,5,6,95,
            98,9,10,11,12,24,-89,15,2,3,
            4,5,6,-11,1,9,10,11,12,-23,
            -97,15,2,3,4,5,6,-38,83,9,
            10,11,12,14,-14,15,92,-108,63,2,
            3,4,5,6,-129,-15,2,3,4,5,
            6,-59,1,-12,17,18,19,-10,21,-13,
            1,17,18,19,-77,21,2,3,4,5,
            6,-87,79,2,3,4,5,6,25,26,
            27,-16,-114,46,2,3,4,5,6,-18,
            1,9,10,11,12,-76,69,70,71,65,
            56,67,7,8,-17,41,42,43,-125,58,
            2,3,4,5,6,-24,-22,72,47,48,
            49,-118,-28,2,3,4,5,6,-27,14,
            9,10,11,12,-122,13,2,3,4,5,
            6,-25,-21,9,10,11,12,-130,-30,2,
            3,4,5,6,90,14,9,10,11,12,
            52,53,54,55,93,-79,24,2,3,4,
            5,6,-91,-78,2,3,4,5,6,-31,
            7,8,-33,-99,-26,2,3,4,5,6,
            -119,-32,2,3,4,5,6,-106,14,2,
            3,4,5,6,-48,1,-20,1,66,44,
            45,-123,-64,2,3,4,5,6,-46,1,
            -140,1,50,51,41,42,43,-126,-80,23,
            -49,1,-50,1,28,7,8,47,48,49,
            -63,44,45,23,-82,-65,-84,20,28,22,
            -86,7,8,7,8,-67,91,7,8,68,
            -88,50,51,57,-90,-68,94,7,8,-127,
            -128,7,8,-92,-158,-94,58,57,-96,-69,
            7,8,7,8,-70,7,8,-51,1,20,
            20,22,22,81,20,-71,22,-52,1,-53,
            1,-54,1,-55,1,-56,1,-57,1,-58,
            1,-72,-74,1,-75,1,-100,99,-98,1,
            80,-101,-104,-115,-105,-111,1,82,-113,1,
            -116,-124,16,-144,-157,-34,13,16,16,84,
            16,-35,-36,13,13,-39,13,-40,-41,-42,
            85,-43,-44,-60,-61,-73,-102,-103,-107,-109,
            -110,-112,-117,-120,-121,86,-131,-132,-133,-134,
            -135,87,-136,-137,-138,-139,-141,-142,-143,-145,
            -146,-147,-148,88,-149,-150,-151,-152,-153,-154,
            -155,-156,-159,-160,-161,-162,-163,-164,0,0,
            89,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,97,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0
        };
    };
    public final static short baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static short rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            59,59,60,60,63,63,61,61,64,64,
            62,62,25,25,26,27,65,67,67,66,
            66,69,70,70,24,24,72,72,73,73,
            73,73,74,75,76,77,14,14,68,78,
            78,78,29,29,29,29,29,29,29,29,
            29,29,29,30,31,32,33,34,35,36,
            37,38,39,40,79,79,80,80,81,81,
            82,82,83,83,84,84,85,85,86,86,
            87,87,88,88,89,89,2,2,2,2,
            3,4,5,6,90,90,41,41,41,42,
            43,91,91,44,44,45,92,92,46,46,
            46,46,19,93,93,47,47,47,48,49,
            94,94,50,50,51,97,97,97,95,95,
            52,52,52,52,53,54,55,96,96,56,
            56,56,56,18,18,21,28,28,17,57,
            57,23,98,98,15,15,9,9,9,9,
            12,12,10,11,7,7,8,8,1,16,
            16,13,20,20,22,22,71,99,99,99,
            58,58,12,203,79,78,65,174,142,86,
            87,88,89,248,1,141,190,133,86,87,
            88,89,13,140,141,139,153,651,10,12,
            13,40,42,43,44,45,46,47,48,49,
            50,51,52,41,42,43,44,45,46,47,
            48,49,50,51,52,65,300,111,86,87,
            88,89,137,196,185,344,272,128,130,131,
            132,348,108,109,110,150,651,231,193,132,
            194,159,86,87,88,89,231,4,154,156,
            157,158,221,161,373,27,28,29,30,31,
            150,106,335,132,292,159,86,87,88,89,
            399,151,154,156,157,158,183,132,412,159,
            86,87,88,89,231,5,154,156,157,158,
            14,132,425,159,86,87,88,89,242,537,
            154,156,157,158,473,3,438,328,2,255,
            111,86,87,88,89,49,15,142,86,87,
            88,89,232,645,211,108,109,110,80,651,
            231,372,140,141,139,172,651,98,86,87,
            88,89,181,386,117,86,87,88,89,11,
            12,13,237,85,107,159,86,87,88,89,
            231,407,155,156,157,158,17,20,21,23,
            395,138,380,53,609,252,94,96,97,112,
            646,133,86,87,88,89,14,238,202,113,
            115,116,94,247,159,86,87,88,89,255,
            626,155,156,157,158,103,146,159,86,87,
            88,89,14,51,155,156,157,158,123,253,
            159,86,87,88,89,474,632,155,156,157,
            158,129,130,131,132,481,18,482,104,86,
            87,88,89,210,17,123,86,87,88,89,
            261,54,609,265,152,14,98,86,87,88,
            89,162,62,117,86,87,88,89,192,633,
            104,86,87,88,89,231,284,220,193,470,
            101,103,201,296,123,86,87,88,89,289,
            645,220,193,120,122,95,96,97,182,17,
            149,231,531,231,503,419,55,609,114,115,
            116,293,102,103,149,17,297,17,675,680,
            676,17,56,609,57,609,304,488,58,609,
            318,17,121,122,439,17,305,502,59,609,
            182,182,60,609,17,182,17,644,439,17,
            308,61,609,62,609,309,63,609,231,536,
            677,678,676,676,519,686,312,676,231,236,
            231,546,231,556,231,570,231,575,231,586,
            231,602,313,231,181,231,180,212,342,231,
            165,464,212,212,247,212,231,666,535,231,
            667,247,247,657,247,173,266,668,658,661,
            541,663,271,274,669,673,275,681,278,279,
            280,551,284,286,290,183,316,318,319,135,
            193,320,322,219,323,325,555,326,328,330,
            335,337,564,339,332,341,346,349,351,353,
            355,11,357,359,566,344,360,361,363,365,
            367,371,366,373,375,377,379,381,384,761,
            761,569,761,761,761,761,761,761,761,761,
            761,761,761,761,761,761,761,761,761,761,
            761,761,761,761,761,761,761,761,761,761,
            761,761,685,761,0,495,39,0,521,39,
            0,523,39,0,578,39,0,588,39,0,
            590,39,0,592,39,0,594,39,0,596,
            39,0,598,39,0,600,39,0,929,177,
            0
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,0,0,3,3,4,5,6,7,8,
            0,0,0,0,0,3,0,0,17,18,
            19,4,5,6,7,8,26,27,28,29,
            30,31,32,33,34,35,36,26,27,28,
            29,30,31,32,33,34,35,36,0,39,
            0,3,4,5,6,7,8,45,46,47,
            48,0,49,50,0,17,18,19,4,5,
            6,7,8,23,24,59,60,0,0,0,
            1,17,18,19,0,24,0,3,4,5,
            6,7,8,0,10,11,3,4,5,6,
            7,8,0,10,11,3,4,5,6,7,
            8,0,10,11,3,4,5,6,7,8,
            42,44,0,12,13,3,4,5,6,7,
            8,0,10,11,0,4,5,6,7,8,
            0,10,11,9,4,5,6,7,8,0,
            1,0,12,13,3,4,5,6,7,8,
            0,0,2,12,3,4,5,6,7,8,
            21,0,0,0,13,4,5,6,7,8,
            0,0,0,12,4,5,6,7,8,0,
            9,0,0,13,3,4,5,6,7,8,
            0,9,0,3,4,5,6,7,8,0,
            0,0,0,4,5,6,7,8,0,0,
            0,40,41,51,22,53,54,25,55,56,
            0,0,22,14,3,25,0,0,2,57,
            58,0,0,23,14,14,0,0,37,38,
            61,0,0,2,0,37,38,3,21,0,
            0,15,15,3,0,0,2,2,16,0,
            0,52,2,0,0,2,2,0,0,0,
            3,3,3,0,43,0,3,2,0,0,
            1,0,0,2,2,0,0,2,2,0,
            0,2,14,0,0,2,2,0,0,2,
            2,0,0,2,2,0,1,0,0,0,
            1,0,0,2,0,0,1,0,1,0,
            1,0,15,15,0,1,0,1,0,1,
            0,1,20,0,20,0,1,16,0,1,
            0,1,0,1,0,1,0,1,0,0,
            0,1,0,1,0,0,0,9,9,3,
            0,1,0,9,0,1,0,1,0,1,
            0,0,39,0,1,20,0,0,16,9,
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
            761,761,17,799,830,619,615,613,648,647,
            761,39,761,761,26,786,164,761,650,616,
            652,649,615,613,648,647,495,521,523,578,
            588,590,592,594,596,598,600,725,728,731,
            734,737,740,743,746,749,752,755,761,682,
            19,844,619,615,613,648,647,475,432,406,
            310,761,797,798,761,650,616,652,619,615,
            613,648,647,273,24,927,928,6,2,761,
            768,650,616,652,761,636,761,832,614,615,
            613,648,647,761,653,621,834,614,615,613,
            648,647,761,653,621,838,614,615,613,648,
            647,761,653,621,842,561,615,613,648,647,
            266,187,761,560,518,846,614,615,613,648,
            647,761,653,621,761,614,615,613,648,647,
            761,653,621,664,561,615,613,648,647,761,
            764,761,560,518,826,612,615,613,648,647,
            761,761,336,607,836,655,615,613,648,647,
            304,761,761,761,654,612,615,613,648,647,
            761,761,761,607,655,615,613,648,647,761,
            933,761,761,654,828,649,615,613,648,647,
            761,665,761,840,622,615,613,648,647,761,
            1,761,761,622,615,613,648,647,761,761,
            24,935,936,887,325,888,886,350,769,770,
            761,761,325,929,937,350,761,16,497,605,
            603,22,761,273,929,929,761,152,930,931,
            724,761,147,433,761,624,670,775,779,761,
            761,932,932,776,761,761,638,639,257,761,
            761,413,640,761,761,642,182,761,761,761,
            796,795,794,761,643,761,793,509,177,761,
            939,64,66,355,456,68,70,235,259,72,
            761,283,758,74,76,362,297,78,80,463,
            195,82,84,186,311,761,940,761,761,761,
            905,761,761,511,761,761,860,761,861,761,
            854,761,659,660,761,853,761,852,761,851,
            761,909,671,761,672,761,904,679,761,873,
            761,923,761,924,761,921,761,880,761,761,
            761,896,761,897,761,761,761,625,934,906,
            761,922,761,683,761,879,761,885,761,895,
            761,761,565,761,866,684,761,761,687,688
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static byte asb[] = {0,
            1,65,2,106,3,7,22,4,86,23,
            106,63,106,106,57,86,86,106,57,102,
            90,85,41,41,41,41,39,88,10,11,
            39,92,86,86,86,86,106,44,86,39,
            39,39,39,86,28,105,28,106,106,106,
            106,106,106,106,106,106,106,106,105,23,
            119,25,25,25,25,25,25,25,25,25,
            25,25,23,106,106,26,95,26,51,26,
            48,26,68,26,68,26,15,26,68,26,
            51,26,14,26,48,26,68,106,94,112,
            112,88,88,112,112,78,110,47,110,23,
            106,86,106,67,88,88,112,67,77,122,
            122,67,78,88,76,108,108,108,47,67,
            23,23,23,23,23,23,12,23,23,102,
            23,23,23,88,23,124,23,110,124,110,
            23,23,110,39,23,122,115,108,12,23,
            23,23,110,23
        };
    };
    public final static byte asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            42,44,23,22,25,0,55,56,0,15,
            3,16,0,12,5,6,7,8,4,13,
            0,21,1,0,2,59,60,36,35,34,
            33,32,31,30,29,28,27,26,3,0,
            49,50,0,3,43,0,3,19,18,17,
            5,6,7,8,4,0,3,45,46,47,
            48,0,22,25,61,0,3,5,6,7,
            8,10,11,4,0,12,13,4,8,7,
            6,5,3,0,21,2,0,15,0,3,
            23,24,0,3,5,6,7,8,4,12,
            0,14,52,0,3,14,0,40,41,9,
            0,37,38,0,54,51,53,0,57,58,
            0,20,0,39,0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static byte nasb[] = {0,
            5,86,8,30,65,13,86,3,86,86,
            85,49,85,38,47,86,86,85,21,85,
            64,86,34,34,34,34,86,62,15,86,
            86,75,86,86,86,86,85,36,86,86,
            86,86,86,86,1,83,17,85,85,85,
            85,85,85,85,85,85,85,85,84,86,
            86,32,90,81,92,26,94,96,98,100,
            102,104,86,85,85,57,51,57,67,57,
            23,57,28,57,28,57,53,57,28,57,
            69,57,19,57,10,57,28,85,71,106,
            106,86,86,106,106,77,86,41,86,86,
            85,86,85,55,62,62,86,55,73,86,
            86,55,79,62,60,87,87,87,44,55,
            86,86,86,86,86,86,86,86,86,85,
            86,86,86,62,86,86,86,86,86,86,
            86,86,86,86,86,86,108,87,86,86,
            86,86,86,86
        };
    };
    public final static byte nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static char nasr[] = {0,
            78,0,62,0,60,59,0,61,0,21,
            96,0,64,0,98,0,29,0,95,0,
            73,0,21,92,0,83,0,15,0,63,
            0,79,0,14,0,69,0,67,65,0,
            21,46,0,21,56,0,72,0,25,0,
            90,0,93,0,9,0,8,7,0,52,
            0,13,0,66,24,0,91,0,94,0,
            41,0,47,0,68,0,44,0,50,0,
            81,0,99,58,1,0,22,20,0,80,
            0,82,0,84,0,85,0,86,0,87,
            0,88,0,89,0,16,0,97,0
        };
    };
    public final static char nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            3,8,9,38,42,43,45,48,63,40,
            50,41,47,62,64,1,37,49,51,65,
            2,12,13,14,16,25,26,27,28,29,
            30,31,32,33,34,35,52,53,66,4,
            5,11,15,18,19,20,21,22,23,24,
            36,39,44,46,54,55,56,57,58,59,
            60,6,7,17,61,67
        };
    };
    public final static byte terminalIndex[] = TerminalIndex.terminalIndex;
    public final int terminalIndex(int index) { return terminalIndex[index]; }

    public interface NonterminalIndex {
        public final static byte nonterminalIndex[] = {0,
            71,0,0,0,0,0,0,103,102,0,
            0,0,94,78,83,88,0,0,0,97,
            99,104,101,0,73,0,0,74,79,0,
            0,0,0,0,0,0,0,0,0,0,
            89,0,0,90,0,91,92,0,0,93,
            0,96,0,0,0,98,100,105,68,0,
            0,69,70,72,75,0,0,76,0,0,
            0,0,77,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,80,
            81,82,84,85,86,87,95,0,0,0
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
            "labelledStringValueList",
            "labelledStringValue",
            "stringSpecificSpec",
            "conditionType",
            "sign",
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
