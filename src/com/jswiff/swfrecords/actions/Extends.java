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
 * Extends creates an inheritance relationship between two classes (instead of
 * classes, interfaces can also be used, since inheritance between interfaces
 * is also possible).
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop superClass</code> (the class to be inherited)<br>
 * <code>pop subClassConstructor</code> (the constructor of the new class)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>extends</code> keyword
 * </p>
 *
 * @since SWF 7
 */
public final class Extends extends Action {
  /**
   * Creates a new Extends action.
   */
  public Extends() {
    code = ActionConstants.EXTENDS;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Extends"</code>
   */
  public String toString() {
    return "Extends";
  }
}
