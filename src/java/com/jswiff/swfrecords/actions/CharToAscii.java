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
 * Converts a character to its ASCII code. Deprecated, use String.charCodeAt()
 * where possible.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop char<br>
 * push code</code> (ASCII code of <code>char</code>)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>ord()</code>
 * </p>
 *
 * @since SWF 4
 */
public final class CharToAscii extends Action {
  /**
   * Creates a new CharToAscii action.
   */
  public CharToAscii() {
    code = ActionConstants.CHAR_TO_ASCII;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"CharToAscii"</code>
   */
  public String toString() {
    return "CharToAscii";
  }
}