
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
    public final static int ERROR_SYMBOL = 65;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = 0;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 1;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 23;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 164;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 65;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 893;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 178;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 99;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 164;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 262;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 61;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 61;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 711;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 715;
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
            0,1,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,1,1,0,0,0,0,
            1,1,0,1,1,0,1,0,0,0,
            0,0,1,1,1,1,1,1,1,1,
            1,1,1,0,0,0,0,0,0,0,
            0,1,1,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            8,62,63,64,65,66,43,96,92,93,
            94,95,81,27,29,30,31,32,33,34,
            35,36,37,38,39,40,41,57,73,74,
            75,88,67,86,90,97,5,10,11,12,
            13,28,68,69,70,71,72,76,77,78,
            79,80,83,84,85,87,89,99,2,3,
            4,6,7,9,14,15,16,17,18,19,
            20,21,22,23,24,25,26,42,44,45,
            46,47,48,49,50,51,52,53,54,55,
            56,58,59,60,61,82,91,98,1
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
            0,0,0,0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static short baseCheck[] = {0,
            4,0,3,1,3,0,3,1,1,1,
            2,1,1,5,5,2,0,3,0,3,
            1,0,1,0,4,0,2,1,1,1,
            1,4,4,4,4,1,1,4,1,2,
            1,1,1,1,1,1,1,1,1,1,
            1,1,4,4,4,4,4,4,4,4,
            4,4,4,5,0,3,0,3,0,3,
            0,3,0,3,0,3,0,3,0,3,
            0,3,0,3,0,3,1,1,1,1,
            3,3,3,3,1,2,1,1,3,1,
            2,1,1,7,1,2,1,1,1,1,
            3,1,2,1,1,1,5,3,1,2,
            1,1,5,1,1,1,1,2,1,1,
            1,5,3,1,2,1,1,1,1,3,
            2,4,2,1,3,1,3,2,0,1,
            1,2,1,1,1,1,3,4,3,3,
            0,2,1,1,1,1,1,1,1,2,
            1,1,4,0,2,3,3,3,-45,-79,
            -78,2,3,4,5,6,-12,7,8,-2,
            -5,-100,-157,15,16,17,18,19,20,21,
            22,23,24,25,26,27,15,16,17,18,
            19,20,21,22,23,24,25,26,27,-47,
            42,-3,43,44,38,39,40,37,-71,-46,
            1,-6,-132,42,15,16,17,18,19,20,
            21,22,23,24,25,26,27,15,16,17,
            18,19,20,21,22,23,24,25,26,27,
            -83,-1,2,3,4,5,6,-97,89,2,
            3,4,5,6,-85,-15,2,3,4,5,
            6,-14,61,9,10,11,12,58,96,29,
            30,31,32,-4,1,64,29,30,31,32,
            -11,1,28,-13,1,-23,-87,47,2,3,
            4,5,6,-21,86,9,10,11,12,14,
            59,60,-7,56,-60,1,-91,98,2,3,
            4,5,6,-73,28,9,10,11,12,-110,
            -29,2,3,4,5,6,65,72,67,-24,
            37,91,-9,13,28,-99,63,2,3,4,
            5,6,95,14,9,10,11,12,29,30,
            31,32,-130,-80,2,3,4,5,6,66,
            7,8,58,28,-63,-89,47,2,3,4,
            5,6,-95,-28,2,3,4,5,6,-18,
            1,29,30,31,32,-116,13,2,3,4,
            5,6,-32,-25,9,10,11,12,-48,1,
            -120,88,2,3,4,5,6,14,56,9,
            10,11,12,48,49,50,-81,97,2,3,
            4,5,6,-20,1,53,54,55,-124,-10,
            2,3,4,5,6,-26,-16,9,10,11,
            12,-131,78,2,3,4,5,6,-82,14,
            9,10,11,12,-103,7,8,92,35,-17,
            68,45,46,-8,41,-93,94,2,3,4,
            5,6,-121,-19,2,3,4,5,6,-127,
            57,2,3,4,5,6,-102,33,2,3,
            4,5,6,-108,-38,2,3,4,5,6,
            -22,38,39,40,-27,-125,90,2,3,4,
            5,6,-49,1,-50,1,51,52,-141,1,
            48,49,50,-51,1,62,-37,1,-84,43,
            44,-64,53,54,55,7,8,-86,45,46,
            -52,1,-53,1,7,8,73,74,75,76,
            77,-54,1,35,-65,-128,51,52,93,41,
            -88,35,-90,69,70,71,-92,7,8,7,
            8,-94,-129,7,8,57,-96,-158,7,8,
            -98,-55,1,7,8,-56,1,7,8,34,
            -66,36,-57,1,-58,1,-67,-68,-69,-70,
            -72,-76,1,-77,1,-106,34,-107,36,-117,
            79,34,-118,36,-101,1,-113,1,-115,1,
            -126,-30,13,-145,-31,13,-33,-34,-35,-36,
            -39,-40,-41,13,80,-42,13,-43,33,-44,
            33,-59,-61,-62,-74,-75,-104,-105,-109,-111,
            -112,-114,-119,-122,-123,-133,-134,-135,-136,-137,
            -138,-139,-140,-142,-143,-144,-146,-147,-148,-149,
            -150,81,-151,-152,-153,-154,-155,-156,82,-159,
            83,-160,84,-161,85,-162,-163,87,-164,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
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
            62,62,38,38,39,40,65,67,67,66,
            66,69,70,70,37,37,72,72,73,73,
            73,73,74,75,76,77,14,14,68,42,
            42,15,15,15,15,15,15,15,15,15,
            15,15,15,16,17,18,19,20,21,22,
            23,24,25,26,27,78,78,79,79,80,
            80,81,81,82,82,83,83,84,84,85,
            85,86,86,87,87,88,88,2,2,2,
            2,3,4,5,6,89,89,43,43,44,
            90,90,45,45,46,91,91,47,47,47,
            47,31,92,92,48,48,48,49,50,93,
            93,51,51,52,96,96,96,94,94,53,
            53,53,54,55,95,95,56,56,56,56,
            30,30,32,41,41,29,57,57,35,97,
            97,28,28,9,9,9,9,12,12,10,
            11,7,7,8,8,1,33,33,13,34,
            34,36,36,71,98,98,98,58,58,41,
            216,55,98,87,88,89,90,211,53,635,
            14,228,41,54,39,41,42,43,44,45,
            46,47,48,49,50,51,52,39,41,42,
            43,44,45,46,47,48,49,50,51,52,
            1,220,56,95,97,11,12,13,484,316,
            289,664,152,13,233,40,41,42,43,44,
            45,46,47,48,49,50,51,52,40,41,
            42,43,44,45,46,47,48,49,50,51,
            52,82,11,110,87,88,89,90,82,507,
            139,87,88,89,90,149,93,156,87,88,
            89,90,69,191,151,153,154,155,663,704,
            107,108,109,671,92,4,450,137,138,136,
            671,92,5,406,92,457,169,149,105,156,
            87,88,89,90,217,592,151,153,154,155,
            647,190,222,51,134,245,664,149,325,156,
            87,88,89,90,320,421,151,153,154,155,
            42,257,110,87,88,89,90,480,494,400,
            169,413,340,178,150,449,149,323,156,87,
            88,89,90,373,648,151,153,154,155,107,
            108,109,671,73,55,139,87,88,89,90,
            645,54,635,666,462,296,158,106,116,87,
            88,89,90,168,254,131,87,88,89,90,
            92,521,137,138,136,671,113,143,156,87,
            88,89,90,74,169,152,153,154,155,92,
            385,122,601,156,87,88,89,90,649,135,
            152,153,154,155,112,114,115,225,148,103,
            87,88,89,90,235,341,127,129,130,131,
            84,156,87,88,89,90,169,258,152,153,
            154,155,140,181,156,87,88,89,90,55,
            650,152,153,154,155,189,55,635,493,146,
            261,515,100,102,210,525,234,500,122,87,
            88,89,90,2,167,116,87,88,89,90,
            91,642,131,87,88,89,90,179,676,98,
            87,88,89,90,188,267,103,87,88,89,
            90,177,10,12,13,250,197,514,122,87,
            88,89,90,92,552,92,575,119,121,235,
            341,113,114,115,92,611,187,92,341,55,
            96,97,297,128,129,130,56,635,55,101,
            102,92,617,92,618,57,635,27,28,29,
            30,31,92,619,146,300,3,120,121,526,
            696,55,147,55,20,21,23,55,58,635,
            59,635,55,3,60,635,642,55,3,61,
            635,55,92,620,62,635,92,229,63,635,
            691,304,693,92,621,92,334,307,311,312,
            315,319,92,178,92,177,189,694,189,693,
            254,374,706,254,693,92,162,92,683,92,
            684,254,264,685,254,265,686,270,271,274,
            275,278,281,282,690,469,283,697,288,679,
            290,680,293,294,198,323,326,324,328,329,
            330,331,333,199,334,337,341,343,345,347,
            349,351,352,354,356,358,360,362,364,369,
            367,371,549,372,373,375,377,378,382,558,
            383,581,385,583,387,587,389,391,597,392,
            715,0,880,174,0
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,0,0,3,3,4,5,6,7,8,
            0,9,0,0,13,3,16,17,18,19,
            20,21,22,23,24,25,26,27,16,17,
            18,19,20,21,22,23,24,25,26,27,
            0,0,40,41,3,4,5,6,7,8,
            0,1,42,0,0,0,16,17,18,19,
            20,21,22,23,24,25,26,27,0,28,
            29,30,0,0,61,3,4,5,6,7,
            8,0,32,0,1,4,5,6,7,8,
            0,0,0,3,4,5,6,7,8,44,
            28,29,30,13,51,14,53,54,35,28,
            29,30,0,59,60,3,4,5,6,7,
            8,0,10,11,3,4,5,6,7,8,
            0,10,11,3,4,5,6,7,8,0,
            10,11,3,4,5,6,7,8,0,10,
            11,0,4,5,6,7,8,0,10,11,
            0,4,5,6,7,8,0,0,0,3,
            13,4,5,6,7,8,0,0,0,2,
            13,3,4,5,6,7,8,0,0,0,
            3,4,5,6,7,8,0,0,0,3,
            4,5,6,7,8,0,55,56,32,0,
            0,45,46,47,48,0,0,49,50,4,
            5,6,7,8,0,37,38,0,4,5,
            6,7,8,0,0,37,38,4,5,6,
            7,8,33,33,0,36,36,3,14,0,
            34,35,3,0,57,58,0,0,14,2,
            0,34,2,0,0,12,0,3,12,0,
            0,2,2,0,0,2,2,0,15,2,
            0,0,0,3,3,3,52,0,0,0,
            3,2,0,0,1,0,0,2,2,0,
            0,2,14,0,12,2,0,0,2,43,
            0,0,2,2,0,0,2,2,0,0,
            2,2,0,0,2,0,1,0,0,0,
            0,1,0,0,2,12,0,9,9,12,
            0,1,0,1,0,1,0,1,0,1,
            0,0,1,0,1,0,1,0,1,0,
            1,0,1,0,31,15,0,31,0,1,
            0,0,0,1,0,9,0,0,1,3,
            9,0,0,9,0,1,0,1,0,1,
            0,0,1,0,0,0,0,15,0,9,
            0,0,39,0,0,0,0,0,0,39,
            0,0,31,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            715,715,715,753,791,675,628,626,668,667,
            2,884,715,715,674,779,662,419,533,535,
            544,561,563,572,602,606,613,615,662,419,
            533,535,544,561,563,572,602,606,613,615,
            715,715,886,887,785,639,628,626,668,667,
            715,718,294,715,161,6,662,419,533,535,
            544,561,563,572,602,606,613,615,17,670,
            637,672,715,715,711,799,639,628,626,668,
            667,715,301,715,722,639,628,626,668,667,
            715,715,26,797,593,628,626,668,667,232,
            670,637,672,576,840,880,841,839,651,670,
            637,672,715,878,879,787,630,628,626,668,
            667,715,673,633,789,630,628,626,668,667,
            715,673,633,793,630,628,626,668,667,715,
            673,633,801,630,628,626,668,667,715,673,
            633,715,630,628,626,668,667,715,673,633,
            715,675,628,626,668,667,715,715,715,740,
            674,593,628,626,668,667,16,715,715,276,
            576,781,475,628,626,668,667,715,715,715,
            783,669,628,626,668,667,715,715,715,795,
            641,628,626,668,667,715,723,724,733,715,
            1,456,414,350,306,715,19,751,752,475,
            628,626,668,667,715,881,882,24,669,628,
            626,668,667,715,715,644,687,641,628,626,
            668,667,282,282,715,304,304,888,880,715,
            353,24,729,715,624,622,149,715,880,444,
            715,353,314,144,715,883,22,730,883,715,
            715,652,653,715,715,656,658,715,547,179,
            715,715,715,750,749,748,394,715,174,715,
            747,230,715,715,890,65,67,180,437,69,
            715,261,712,71,665,275,73,715,307,660,
            75,77,386,327,79,81,486,393,83,85,
            268,356,715,715,192,715,891,715,715,715,
            715,856,715,715,539,677,715,681,682,678,
            715,814,715,809,715,808,715,807,715,806,
            715,715,860,715,855,715,826,715,874,715,
            875,715,872,715,688,695,715,689,715,833,
            715,715,715,848,715,193,715,715,873,857,
            885,715,715,700,715,832,715,838,715,847,
            715,715,819,715,715,715,715,707,715,709,
            715,715,698,715,715,715,715,715,715,598,
            715,715,702
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }

    public interface Asb {
        public final static byte asb[] = {0,
            15,3,16,10,17,12,34,18,104,35,
            10,1,10,10,21,104,104,10,21,86,
            30,103,27,27,27,27,98,75,47,48,
            98,32,104,104,104,104,10,100,104,98,
            98,98,98,104,55,9,54,10,10,10,
            10,10,10,10,10,10,10,10,75,9,
            35,110,51,51,51,51,51,51,51,51,
            51,51,51,104,35,10,10,52,41,52,
            41,52,38,52,78,52,78,52,68,52,
            78,52,41,52,68,52,38,52,78,55,
            10,93,89,75,75,89,89,93,108,37,
            108,35,10,104,10,77,75,75,89,77,
            92,113,113,77,93,75,92,106,106,37,
            77,54,35,35,35,35,35,49,35,35,
            86,35,35,35,75,35,115,35,108,115,
            108,35,108,98,35,113,5,106,49,35,
            35,35,108,35
        };
    };
    public final static byte asb[] = Asb.asb;
    public final int asb(int index) { return asb[index]; }

    public interface Asr {
        public final static byte asr[] = {0,
            33,36,61,0,54,51,53,0,3,14,
            0,55,56,0,42,44,34,33,36,0,
            3,45,46,47,48,0,49,50,0,3,
            34,35,0,32,1,0,3,30,29,28,
            5,6,7,8,4,0,12,3,15,0,
            2,59,60,3,16,27,26,25,24,23,
            22,21,20,19,18,17,0,5,6,7,
            8,4,13,0,12,0,3,5,6,7,
            8,10,11,4,0,14,52,0,37,38,
            0,13,4,8,7,6,5,3,0,3,
            43,0,32,2,0,40,41,9,0,57,
            58,0,31,0,39,0
        };
    };
    public final static byte asr[] = Asr.asr;
    public final int asr(int index) { return asr[index]; }

    public interface Nasb {
        public final static byte nasb[] = {0,
            27,21,14,40,45,22,21,73,21,21,
            20,8,20,37,35,21,21,20,79,20,
            44,21,42,42,42,42,21,63,52,21,
            21,67,21,21,21,21,20,87,21,21,
            21,21,21,21,1,18,12,20,20,20,
            20,20,20,20,20,20,20,20,21,19,
            21,21,57,91,93,98,100,102,104,106,
            16,108,47,21,21,20,20,5,3,5,
            69,5,24,5,33,5,33,5,59,5,
            33,5,75,5,61,5,30,5,33,1,
            20,83,71,21,21,71,71,85,21,49,
            21,21,20,21,20,65,63,63,21,65,
            77,21,21,65,89,63,81,95,95,54,
            65,12,21,21,21,21,21,21,21,21,
            20,21,21,21,63,21,21,21,21,21,
            21,21,21,21,21,21,10,95,21,21,
            21,21,21,21
        };
    };
    public final static byte nasb[] = Nasb.nasb;
    public final int nasb(int index) { return nasb[index]; }

    public interface Nasr {
        public final static char nasr[] = {0,
            42,0,89,0,8,7,0,38,0,96,
            0,15,0,61,0,86,0,98,58,1,
            0,64,0,32,91,0,60,59,0,32,
            95,0,28,0,72,0,67,65,0,63,
            0,14,0,66,37,0,88,0,32,47,
            0,97,0,32,56,0,78,0,92,0,
            94,0,13,0,9,0,68,0,90,0,
            33,0,62,0,93,0,48,0,73,0,
            53,0,43,0,45,0,69,0,51,0,
            79,0,80,0,36,34,0,81,0,82,
            0,83,0,84,0,85,0,87,0
        };
    };
    public final static char nasr[] = Nasr.nasr;
    public final int nasr(int index) { return nasr[index]; }

    public interface TerminalIndex {
        public final static byte terminalIndex[] = {0,
            3,8,9,38,41,42,44,47,62,40,
            49,63,46,61,1,17,25,26,27,28,
            29,30,31,32,33,34,35,37,48,50,
            64,2,12,13,14,16,51,52,65,4,
            5,11,15,18,19,20,21,22,23,24,
            36,39,43,45,53,54,55,56,57,58,
            59,6,7,60,66
        };
    };
    public final static byte terminalIndex[] = TerminalIndex.terminalIndex;
    public final int terminalIndex(int index) { return terminalIndex[index]; }

    public interface NonterminalIndex {
        public final static byte nonterminalIndex[] = {0,
            70,0,0,0,0,0,0,103,102,0,
            0,0,94,77,79,0,0,0,0,0,
            0,0,0,0,0,0,0,83,0,0,
            0,99,88,97,101,104,0,72,0,0,
            73,78,89,0,90,0,91,92,0,0,
            93,0,96,0,0,98,100,105,67,0,
            0,68,69,71,74,0,0,75,0,0,
            0,0,76,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,80,81,
            82,84,85,86,87,95,0,0,0
        };
    };
    public final static byte nonterminalIndex[] = NonterminalIndex.nonterminalIndex;
    public final int nonterminalIndex(int index) { return nonterminalIndex[index]; }

    public interface ScopePrefix {
        public final static byte scopePrefix[] = {
            1
        };
    };
    public final static byte scopePrefix[] = ScopePrefix.scopePrefix;
    public final int scopePrefix(int index) { return scopePrefix[index]; }

    public interface ScopeSuffix {
        public final static byte scopeSuffix[] = {
            6
        };
    };
    public final static byte scopeSuffix[] = ScopeSuffix.scopeSuffix;
    public final int scopeSuffix(int index) { return scopeSuffix[index]; }

    public interface ScopeLhs {
        public final static char scopeLhs[] = {
            27
        };
    };
    public final static char scopeLhs[] = ScopeLhs.scopeLhs;
    public final int scopeLhs(int index) { return scopeLhs[index]; }

    public interface ScopeLa {
        public final static byte scopeLa[] = {
            3
        };
    };
    public final static byte scopeLa[] = ScopeLa.scopeLa;
    public final int scopeLa(int index) { return scopeLa[index]; }

    public interface ScopeStateSet {
        public final static byte scopeStateSet[] = {
            1
        };
    };
    public final static byte scopeStateSet[] = ScopeStateSet.scopeStateSet;
    public final int scopeStateSet(int index) { return scopeStateSet[index]; }

    public interface ScopeRhs {
        public final static char scopeRhs[] = {0,
            107,2,12,16,0,3,0
        };
    };
    public final static char scopeRhs[] = ScopeRhs.scopeRhs;
    public final int scopeRhs(int index) { return scopeRhs[index]; }

    public interface ScopeState {
        public final static char scopeState[] = {0,
            233,192,220,179,0
        };
    };
    public final static char scopeState[] = ScopeState.scopeState;
    public final int scopeState(int index) { return scopeState[index]; }

    public interface InSymb {
        public final static char inSymb[] = {0,
            0,124,125,42,126,44,128,102,34,129,
            32,127,36,33,2,66,130,132,137,2,
            2,66,48,47,46,45,106,52,66,122,
            131,102,79,79,79,79,15,133,35,2,
            2,2,2,43,2,2,107,17,18,19,
            20,21,22,23,24,25,26,27,16,163,
            123,66,66,66,66,66,66,66,66,66,
            66,66,66,12,123,58,57,143,2,144,
            2,145,2,146,2,147,2,148,2,149,
            2,150,2,151,2,152,2,153,2,2,
            73,154,4,8,7,6,5,155,4,156,
            28,97,29,30,4,93,4,11,10,93,
            157,13,4,93,158,4,159,13,4,160,
            93,107,98,12,12,98,98,9,9,66,
            2,66,78,78,37,38,31,31,78,99,
            101,99,15,106,78,39,9,39,9,31,
            161,99,15,9
        };
    };
    public final static char inSymb[] = InSymb.inSymb;
    public final int inSymb(int index) { return inSymb[index]; }

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
            "GROUP",
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
            "fieldSpecs",
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
