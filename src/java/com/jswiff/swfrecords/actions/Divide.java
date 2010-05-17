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
 * Divides two numbers.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br><code>pop b<br> pop a<br> push [a / b]</code>
 * </p>
 * 
 * <p>
 * Note: in SWF 5 and later, if <code>a</code> or <code>b</code> are not (or
 * cannot be converted to) floating point numbers, the result is
 * <code>NaN</code> (or <code>Double.NaN</code>); if <code>b</code> is 0, the
 * result is <code>Infinity</code> or <code>-Infinity</code>
 * (<code>Double.POSITIVE_INFINITY</code> or
 * <code>Double.NEGATIVE_INFINITY</code>), depending on <code>a</code>'s sign.
 * Before SWF 5, these results were not IEEE-754 compliant.
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the <code>/</code> operator
 * </p>
 *
 * @since SWF 4
 */
public final class Divide extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new Divide action.
   */
  public Divide() {
    super(ActionType.DIVIDE);
  }

}
