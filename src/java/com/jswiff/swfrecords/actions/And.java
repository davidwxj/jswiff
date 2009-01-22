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
 * Performs a boolean AND (<code>&&</code>) operation.
 * </p>
 * 
 * <p>
 * Note: Before SWF 5, 1 was used instead of true and 0 instead of false.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop b<br>
 * pop a<br>
 * push [a && b]</code> (<code>true</code> (with SWF 4: 1) if both
 * <code>a</code> and <code>b</code> are <code>true</code> (with SWF 4: 1))
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the <code>&&</code> operator
 * </p>
 *
 * @since SWF 4
 */
public final class And extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new And action.
   */
  public And() {
    super(ActionType.AND);
  }

}
