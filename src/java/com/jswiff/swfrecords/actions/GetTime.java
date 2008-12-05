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
 * Reports the milliseconds since the SWF started playing.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>push time</code> (ms since the Player started)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>getTimer()</code>
 * </p>
 *
 * @since SWF 5
 */
public final class GetTime extends Action {
  /**
   * Creates a new GetTime action.
   */
  public GetTime() {
    code = ActionConstants.GET_TIME;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"GetTime"</code>
   */
  public String toString() {
    return "GetTime";
  }
}
