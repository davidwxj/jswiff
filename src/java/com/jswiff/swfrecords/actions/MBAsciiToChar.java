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

package com.jswiff.swfrecords.actions;

import com.jswiff.constants.ActionConstants;

/**
 * <p>
 * Converts an ASCII character code to a multibyte character. Deprecated as of
 * SWF 5, use String.fromCharCode() where possible.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop number<br>
 * push char</code> (Character with an ASCII code of <code>number</code>)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>mbchr()</code>
 * </p>
 *
 * @since SWF 4
 */
public final class MBAsciiToChar extends Action {
  /**
   * Creates a new MBAsciiToChar action.
   */
  public MBAsciiToChar() {
    code = ActionConstants.M_B_ASCII_TO_CHAR;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"MBAsciiToChar"</code>
   */
  public String toString() {
    return "MBAsciiToChar";
  }
}