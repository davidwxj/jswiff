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
 * Retrieves a member value from an object. Can be either a property or a
 * method.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop name</code> (the member's name)<br>
 * <code>pop ref</code> (reference to the object to be accessed)<br>
 * <code>push value</code> (the value of the member)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalents:<br>
 * 
 * <ul>
 * <li>
 * member access (with or without dot operator), e.g. <code>speed</code> or
 * <code>car.speed</code>
 * </li>
 * <li>
 * internally used by the AS compiler to implement various constructs
 * </li>
 * </ul>
 * </p>
 *
 * @since SWF 5
 */
public final class GetMember extends Action {
  /**
   * Creates a new GetMember action.
   */
  public GetMember() {
    code = ActionConstants.GET_MEMBER;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"GetMember"</code>
   */
  public String toString() {
    return "GetMember";
  }
}
