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
 * Gets a variable's value.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop name</code> (the variable's name)<br>
 * <code>push value</code> (the value of the variable)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: conventional variable access
 * </p>
 *
 * @since SWF 4
 */
public final class GetVariable extends Action {
  /**
   * Creates a new GetVariable action.
   */
  public GetVariable() {
    code = ActionConstants.GET_VARIABLE;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"GetVariable"</code>
   */
  public String toString() {
    return "GetVariable";
  }
}
