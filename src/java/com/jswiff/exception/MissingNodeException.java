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

package com.jswiff.exception;

/**
 * Indicates that a mandatory node is missing.
 */
public class MissingNodeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new MissingNodeException instance.
   *
   * @param message detail message (returned by <code>getMessage()</code>).
   */
  public MissingNodeException(String message) {
    super(message);
  }
}
