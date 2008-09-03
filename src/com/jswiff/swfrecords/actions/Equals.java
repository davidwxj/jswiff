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
 * Tests two numbers for equality.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop num1</code> (first number)<br>
 * <code>pop num2</code> (second number)<br>
 * <code>push [num2 == num1]</code> (1 if equal, else 0; as of SWF 5,
 * <code>true</code> instead of 1 and <code>false</code> instead of 0)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>==</code> operator
 * </p>
 *
 * @since SWF 4
 */
public final class Equals extends Action {
  /**
   * Creates a new Equals action.
   */
  public Equals() {
    code = ActionConstants.EQUALS;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Equals"</code>
   */
  public String toString() {
    return "Equals";
  }
}
