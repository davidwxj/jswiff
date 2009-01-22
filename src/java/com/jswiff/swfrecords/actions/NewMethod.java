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
 * Creates a new object, invoking a constructor. You will likely use
 * <code>NewObject</code> instead.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop constrName</code> (name of the constructor function)<br>
 * <code>pop class</code> (class to be instantiated)<br>
 * <code>pop n</code> (number of parameters passed to constructor)<br>
 * <code>pop param1</code> (1st parameter)<br>
 * <code>pop param2</code> (2nd parameter)<br>
 * <code>...</code><br>
 * <code>pop paramn</code> (n-th parameter)<br>
 * <code>push obj</code> (the newly constructed object)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalents: unknown
 * </p>
 *
 * @since SWF 5
 */
public final class NewMethod extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new NewMethod action.
   */
  public NewMethod() {
    super(ActionType.NEW_METHOD);
  }

}
