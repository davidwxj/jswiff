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
 * Sends a debugging output string to the Output panel of the Macromedia Flash
 * authoring environment (in test mode).
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop message</code> (message to be displayed)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>trace()</code>
 * </p>
 *
 * @since SWF 5
 */
public final class Trace extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new Trace action.
   */
  public Trace() {
    super(ActionType.TRACE);
  }

}
