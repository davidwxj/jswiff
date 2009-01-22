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
 * This action determines if an object is an instance of a specified class (or
 * interface as of SWF 7).
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop class</code><br>
 * <code>pop ref</code> (reference to the object to be checked)<br>
 * <code>push result</code> (<code>true</code> or <code>false</code>)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>instanceof</code> operator
 * </p>
 *
 * @since SWF 6
 */
public final class InstanceOf extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new InstanceOf action.
   */
  public InstanceOf() {
    super(ActionType.INSTANCE_OF);
  }
  
}
