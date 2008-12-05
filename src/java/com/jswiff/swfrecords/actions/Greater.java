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
 * Tests whether a number is greater than another number, taking account of
 * data types (according to ECMA-262 spec).
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop b</code><br>
 * <code>pop a</code><br>
 * <code>push [a &gt; b]</code> (<code>true</code> if a&gt;b, otherwise
 * <code>false</code>)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>&gt;</code> operator
 * </p>
 *
 * @since SWF 6
 */
public final class Greater extends Action {
  /**
   * Creates a new Greater action.
   */
  public Greater() {
    code = ActionConstants.GREATER;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Greater"</code>
   */
  public String toString() {
    return "Greater";
  }
}
