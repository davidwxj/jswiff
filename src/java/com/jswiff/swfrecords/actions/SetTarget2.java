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

/**
 * <p>
 * Instructs Flash Player to change the context of subsequent actions, so they
 * apply to an object with the specified name. This action can be used e.g. to
 * control the timeline of a sprite object. Unlike <code>SetTarget</code>,
 * <code>SetTarget2</code> pops the target off the stack.
 * </p>
 * 
 * <p>
 * Note: as of SWF 5, this action is deprecated. Use <code>With</code> instead.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br><code>pop target</code>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>tellTarget()</code>
 * </p>
 *
 * @since SWF 4
 */
public final class SetTarget2 extends Action {
  /**
   * Creates a new SetTarget2 action.
   */
  public SetTarget2() {
    code = ActionConstants.SET_TARGET_2;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"SetTarget2"</code>
   */
  public String toString() {
    return "SetTarget2";
  }
}
