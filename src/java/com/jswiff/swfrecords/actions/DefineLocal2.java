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
 * Defines a local variable without setting its value. The initial value is
 * <code>undefined</code>. If the variable already exists, nothing happens.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code> pop varName</code> (the new variable's name)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: variable declaration without initialization (e.g.
 * <code>var a;)</code>
 * </p>
 *
 * @since SWF 5
 */
public final class DefineLocal2 extends Action {
  /**
   * Creates a new DefineLocal2 action.
   */
  public DefineLocal2() {
    code = ActionConstants.DEFINE_LOCAL_2;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"DefineLocal2"</code>
   */
  public String toString() {
    return "DefineLocal2";
  }
}