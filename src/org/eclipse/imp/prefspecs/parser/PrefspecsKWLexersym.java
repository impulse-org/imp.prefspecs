
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

public interface PrefspecsKWLexersym {
    public final static int
      Char_DollarSign = 25,
      Char_Percent = 26,
      Char__ = 27,
      Char_a = 2,
      Char_b = 13,
      Char_c = 11,
      Char_d = 12,
      Char_e = 1,
      Char_f = 14,
      Char_g = 15,
      Char_h = 19,
      Char_i = 6,
      Char_j = 22,
      Char_k = 23,
      Char_l = 3,
      Char_m = 16,
      Char_n = 8,
      Char_o = 5,
      Char_p = 17,
      Char_q = 28,
      Char_r = 9,
      Char_s = 7,
      Char_t = 4,
      Char_u = 10,
      Char_v = 20,
      Char_w = 21,
      Char_x = 29,
      Char_y = 18,
      Char_z = 30,
      Char_EOF = 24;

    public final static String orderedTerminalSymbols[] = {
                 "",
                 "e",
                 "a",
                 "l",
                 "t",
                 "o",
                 "i",
                 "s",
                 "n",
                 "r",
                 "u",
                 "c",
                 "d",
                 "b",
                 "f",
                 "g",
                 "m",
                 "p",
                 "y",
                 "h",
                 "v",
                 "w",
                 "j",
                 "k",
                 "EOF",
                 "DollarSign",
                 "Percent",
                 "_",
                 "q",
                 "x",
                 "z"
             };

    public final static int numTokenKinds = orderedTerminalSymbols.length;
    public final static boolean isValidForParser = true;
}
