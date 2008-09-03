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

/**
 * <p>
 * Tests whether a string is less than another.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop b</code><br>
 * <code>pop a</code><br>
 * <code>push [a &lt; b]</code> (1 (<code>true</code> in SWF 5 and higher) if
 * a&lt;b, otherwise 0 (<code>false</code>) )<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>lt</code> operator, <code>&lt;</code>
 * operator
 * </p>
 *
 * @since SWF 4
 */
public final class StringLess extends Action {
  /**
   * Creates a new StringLess action.
   */
  public StringLess() {
    code = ActionConstants.STRING_LESS;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"StringLess"</code>
   */
  public String toString() {
    return "StringLess";
  }
}
