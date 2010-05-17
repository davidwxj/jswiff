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
 * Concatenates two strings. Replaced by <code>Add2</code> as of SWF 5.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop str1<br>
 * pop str2<br>
 * push [str2 & str1]</code> (concatenation of str2 and str1)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>&</code> operator in SWF 4 (as of SWF 5, this
 * operator has a different meaning!)
 * </p>
 *
 * @since SWF 4
 */
public final class StringAdd extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new StringAdd action.
   */
  public StringAdd() {
    super(ActionType.STRING_ADD);
  }

}
