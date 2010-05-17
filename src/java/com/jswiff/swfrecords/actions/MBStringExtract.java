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

import com.jswiff.constants.TagConstants.ActionType;


/**
 * <p>
 * Extracts a substring from a string, multibyte-aware. Deprecated as of SWF 5,
 * use String.substr() instead.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop count</code> (number of characters to extract)<br>
 * <code>pop index</code> (index of first character to extract)<br>
 * <code>pop string</code> (string to extract from)<br>
 * <code>push substr</code> (extracted substring)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>mbsubstring()</code>
 * </p>
 *
 * @since SWF 4
 */
public final class MBStringExtract extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new MBStringExtract action.
   */
  public MBStringExtract() {
    super(ActionType.M_B_STRING_EXTRACT);
  }
  
}
