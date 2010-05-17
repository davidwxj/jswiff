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
 * Returns the value of a movie property.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop property</code> (property index - see <code>MovieProperties</code>)<br>
 * <code>pop movie</code> (reference to the clip)<br>
 * <code>push value</code> (property value)
 * </p>
 * 
 * <p>
 * ActionScript equivalents: <code>getProperty()</code>
 * </p>
 *
 * @see MovieProperties
 * @since SWF 4
 */
public final class GetProperty extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new GetProperty action.
   */
  public GetProperty() {
    super(ActionType.GET_PROPERTY);
  }

}
