/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
 */

package com.jswiff.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Contains some methods for string manipulation.
 */
public class StringUtilities {
  /** Default rounding precision (digits after decimal point) */
  public static final int DEFAULT_ROUND_PRECISION = 16;
  private static DecimalFormat df;
  private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

  static {
    Locale.setDefault(Locale.US);
    df = new DecimalFormat();
    df.setGroupingUsed(false);
  }

  /**
   * Checks if a string contains illegal characters with respect to the XML
   * specification.
   *
   * @param text the text to be checked
   *
   * @return true if illegal chars contained, otherwise false
   */
  public static boolean containsIllegalChars(String text) {
    int size        = text.length();
    boolean illegal = false;
    for (int i = 0; i < size; i++) {
      char c = text.charAt(i);
      if (isIllegal(c)) {
        illegal = true;
        break;
      }
    }
    return illegal;
  }

  /**
   * Converts a double to a string. You can specify the rounding precision,
   * i.e. the number of digits after the decimal point.
   *
   * @param d double to be converted
   * @param precision rounding precision
   *
   * @return a string representing the passed double value
   */
  public static String doubleToString(double d, int precision) {
    synchronized (df) {
      df.setMaximumFractionDigits(precision);
      return df.format(d);
    }
  }

  /**
   * Converts a double to a string. The default rounding precision is used.
   *
   * @param d double to be converted
   *
   * @return a string representing the passed double value
   */
  public static String doubleToString(double d) {
    return doubleToString(d, DEFAULT_ROUND_PRECISION);
  }
  
  public static String dateToString(Date date) {
    return dateFormat.format(date);
  }
  
  public static Date parseDate(String str) throws ParseException {
    return dateFormat.parse(str);
  }

  /**
   * Checks if the character is illegal with respect to the XML
   * specification.
   *
   * @param c the character to be checked
   *
   * @return true if character is illegal
   */
  public static boolean isIllegal(char c) {
    return ((c < 32) && (c != '\t') && (c != '\n') && (c != '\r'));
  }
}
