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
 * Deletes an object's property. Can be used to free memory. After deletion,
 * the property has the value <code>undefined</code>.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop ref</code> (reference to object to be altered)<br>
 * <code>pop prop</code> (property to be deleted)<br>
 * <code>push success</code> (<code>true</code> if the operation succeeded,
 * otherwise <code>false</code>)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the delete operator (e.g. <code>delete
 * ref.prop;</code>)
 * </p>
 *
 * @since SWF 5
 */
public final class Delete extends Action {
  /**
   * Creates a new Delete action.
   */
  public Delete() {
    code = ActionConstants.DELETE;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Delete"</code>
   */
  public String toString() {
    return "Delete";
  }
}
