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
 * This action specifies the interfaces a class implements.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop constructor</code> (the constructor of the new class)<br>
 * <code>pop n</code> (number of interfaces this class implements)<br>
 * <code>pop i1</code> (1st interface)<br>
 * <code>pop i2</code> (2nd interface)<br>
 * <code>...<br>
 * pop in</code> (n-th interface)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>implements</code> keyword
 * </p>
 *
 * @since SWF 7
 */
public final class ImplementsOp extends Action {
  /**
   * Creates a new ImplementsOp action.
   */
  public ImplementsOp() {
    code = ActionConstants.IMPLEMENTS_OP;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"ImplementsOp"</code>
   */
  public String toString() {
    return "ImplementsOp";
  }
}
