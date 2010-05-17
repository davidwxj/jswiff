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
 * Populates an object's member (either a property or a method) with a given
 * value. If the member does not exist, it is created. Otherwise, it's value
 * is overwritten.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop value</code> (the new value of the member)<br>
 * <code>pop name</code> (the member's name)<br>
 * <code>pop ref</code> (reference to the object to be accessed)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalents:<br>
 * 
 * <ul>
 * <li>
 * member assignment (with or without dot operator), e.g.
 * <code>speed=100;</code> or <code>car.speed=100;</code>
 * </li>
 * <li>
 * internally used by the AS compiler to implement various constructs
 * </li>
 * </ul>
 * </p>
 *
 * @since SWF 5
 */
public final class SetMember extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new SetMember action.
   */
  public SetMember() {
    super(ActionType.SET_MEMBER);
  }

}
