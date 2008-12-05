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
 * Instructs Flash Player to go back to the previous frame in the current
 * movie.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>prevFrame()</code>
 * </p>
 *
 * @since SWF 3
 */
public final class PreviousFrame extends Action {
  /**
   * Creates a new PreviousFrame action.
   */
  public PreviousFrame() {
    code = ActionConstants.PREVIOUS_FRAME;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"PreviousFrame"</code>
   */
  public String toString() {
    return "PreviousFrame";
  }
}
