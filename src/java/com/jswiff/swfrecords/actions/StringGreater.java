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
 * Tests whether a string is greater than another.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop b</code><br>
 * <code>pop a</code><br>
 * <code>push [a &gt; b]</code> (1 (<code>true</code> in SWF 5 and higher) if
 * a&gt;b, otherwise 0 (<code>false</code>) )<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>&gt;</code> operator
 * </p>
 *
 * @since SWF 6
 */
public final class StringGreater extends Action {
  /**
   * Creates a new StringGreater action.
   */
  public StringGreater() {
    code = ActionConstants.STRING_GREATER;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"StringGreater"</code>
   */
  public String toString() {
    return "StringGreater";
  }
}
