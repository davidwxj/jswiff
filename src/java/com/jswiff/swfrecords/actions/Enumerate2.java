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
 * Iterates over all properties of an object and pushes their names to the
 * stack. The difference to <code>Enumerate</code> is that
 * <code>Enumerate2</code> uses a stack argument of object type rather than
 * the object's name as string.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop obj</code> (object instance)<br>
 * <code>push null</code> (indicates the end of the property list)<br>
 * <code>push prop1</code> (1st property name)<br>
 * <code>push prop2</code> (2nd property name)<br>
 * <code>...</code><br>
 * <code>push propn</code> (n-th property name)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>for..in</code> loop
 * </p>
 *
 * @since SWF 6
 */
public final class Enumerate2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new Enumerate2 action.
   */
  public Enumerate2() {
    super(ActionType.ENUMERATE_2);
  }

}
