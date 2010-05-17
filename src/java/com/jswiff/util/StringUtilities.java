/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
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
