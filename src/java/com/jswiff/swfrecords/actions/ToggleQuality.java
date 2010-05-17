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
 * Toggles the display between high and low quality. Since SWF 5, this action
 * is deprecated. Use the global property <code>_quality</code> instead.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>toggleHighQuality()</code>
 * </p>
 *
 * @since SWF 3
 */
public final class ToggleQuality extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new ToggleQuality action.
   */
  public ToggleQuality() {
    super(ActionType.TOGGLE_QUALITY);
  }

}
