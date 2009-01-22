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

import com.jswiff.constants.TagConstants.ActionType;


/**
 * <p>
 * Performs a bitwise left shift (<code>&lt;&lt;</code>). The value argument is
 * interpreted as a 32-bit integer (signed integer for a negative number,
 * otherwise unsigned integer). The shift count is treated as an integer from
 * 0 to 31 (only lower 5 bits are considered). The result is interpreted as
 * signed 32-bit integer.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop b<br>
 * pop a<br>
 * push [a &lt;&lt; b]</code>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the <code>&lt;&lt;</code> operator
 * </p>
 *
 * @since SWF 5
 */
public final class BitLShift extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new BitLShift action.
   */
  public BitLShift() {
    super(ActionType.BIT_L_SHIFT);
  }
  
}
