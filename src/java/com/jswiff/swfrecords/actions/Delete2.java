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
 * Destroys an object reference. Can be used for freeing memory. After deleting
 * the reference, an internal reference counter is decremented. When the
 * counter of an object has reached zero, Flash Player will mark that object
 * for garbage collection.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop ref</code> (reference to be deleted)<br>
 * <code>push success</code> (<code>true</code> if the operation succeeded,
 * otherwise <code>false</code>)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the delete operator (e.g. <code>delete ref;</code>)
 * </p>
 *
 * @since SWF 5
 */
public final class Delete2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new Delete2 action.
   */
  public Delete2() {
    super(ActionType.DELETE_2);
  }

}
