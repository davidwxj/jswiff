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
 * Sets a variable.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop value</code> (the new value)<br>
 * <code>pop name</code> (the variable's name)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: conventional variable assignment (e.g. <code>x =
 * 2;</code>)
 * </p>
 *
 * @since SWF 4
 */
public final class SetVariable extends Action {
  /**
   * Creates a new SetVariable action.
   */
  public SetVariable() {
    code = ActionConstants.SET_VARIABLE;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"SetVariable"</code>
   */
  public String toString() {
    return "SetVariable";
  }
}
