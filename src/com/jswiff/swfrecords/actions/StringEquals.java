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
 * Tests two strings for equality. Replaced by <code>Equals2</code> as of SWF
 * 5.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop str1</code> (first string)<br>
 * <code>pop str2</code> (second number)<br>
 * <code>push [str2 == str1]</code> (1 if equal, else 0)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>eq</code> operator
 * </p>
 *
 * @since SWF 4
 */
public final class StringEquals extends Action {
  /**
   * Creates a new StringEquals action.
   */
  public StringEquals() {
    code = ActionConstants.STRING_EQUALS;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"StringEquals"</code>
   */
  public String toString() {
    return "StringEquals";
  }
}
