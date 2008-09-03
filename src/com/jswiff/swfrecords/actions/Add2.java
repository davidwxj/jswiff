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
 * This action is used to implement the addition operator according to the
 * ECMAScript specification, i.e. it performs either string concatenation or
 * numeric addition, depending on the data types of the operands.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br><code>pop b<br> pop a<br> push [a + b]</code>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the <code>+</code> operator
 * </p>
 *
 * @since SWF 5
 */
public final class Add2 extends Action {
  /**
   * Creates a new Add2 action.
   */
  public Add2() {
    code = ActionConstants.ADD_2;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Add2"</code>
   */
  public String toString() {
    return "Add2";
  }
}
