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
 * Performs a bitwise AND (<code>&amp;</code>). The arguments are treated as
 * 32-bit integers (signed integers for negative numbers, otherwise unsigned
 * integers). The result is interpreted as a signed 32-bit integer.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop b<br>
 * pop a<br>
 * push [a &amp; b]</code>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the <code>&amp;</code> operator
 * </p>
 *
 * @since SWF 5
 */
public final class BitAnd extends Action {
  /**
   * Creates a new BitAnd action.
   */
  public BitAnd() {
    code = ActionConstants.BIT_AND;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"BitAnd"</code>
   */
  public String toString() {
    return "BitAnd";
  }
}
