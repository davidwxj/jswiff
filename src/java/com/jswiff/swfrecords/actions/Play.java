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
 * Instructs Flash Player to start playing at the current frame.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>gotoAndPlay()</code> (no 1:1 equivalent
 * available)
 * </p>
 *
 * @since SWF 3
 */
public final class Play extends Action {
  /**
   * Creates a new Play action.
   */
  public Play() {
    code = ActionConstants.PLAY;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Play"</code>
   */
  public String toString() {
    return "Play";
  }
}
