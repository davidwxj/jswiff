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
 * Indicates that an attempt to lookup the constant mapped to a given string failed.
 * @author bstock
 *
 */
public class InvalidNameException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;

  private final String name;
  
  public InvalidNameException(final String type, String name) {
    this(type, name, null);
  }
  
  public InvalidNameException(final String type, String name, Throwable cause) {
    super("Invalid '" + type + "' name: " + name, cause);
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }

}
