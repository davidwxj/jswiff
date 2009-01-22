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
 * Creates an object and initializes it with values from the stack.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code> pop n</code> (property number)<br>
 * <code> pop value_1</code> (1st property value)<br>
 * <code> pop name_1</code> (1st property name)<br>
 * <code> pop value_2</code> (2nd property value)<br>
 * <code> pop name_2</code> (2nd property name)<br>
 * <code> ...<br>
 * pop value_n</code> (n-th property value)<br>
 * <code> pop name_n</code> (n-th property name)<br>
 * <code> push obj</code> (the new object)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: object literal (e.g. <code>{width: 150, height:
 * 100}</code>)
 * </p>
 *
 * @since SWF 5
 */
public final class InitObject extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new InitObject action.
   */
  public InitObject() {
    super(ActionType.INIT_OBJECT);
  }

}
