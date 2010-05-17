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
 * Tests two items for equality. Unlike <code>Equals</code>,
 * <code>Equals2</code> takes account of data types.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop item1</code> (first item)<br>
 * <code>pop item2</code> (second item)<br>
 * <code>push [item2 == item1]</code> (<code>true</code> if equal, else
 * <code>false</code>)
 * </p>
 * 
 * <p>
 * ActionScript equivalents: <code>==</code> operator
 * </p>
 *
 * @since SWF 5
 */
public final class Equals2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new Equals2 actions.
   */
  public Equals2() {
    super(ActionType.EQUALS_2);
  }

}
