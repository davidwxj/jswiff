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
 * Indicates that an attempt to lookup the constant mapped to a given code failed.
 * @author bstock
 *
 */
public class InvalidCodeException extends Exception {
  
  private static final long serialVersionUID = 1L;

  private final short code;
  
  public InvalidCodeException(final String type, short code) {
    this(type, code, null);
  }
  
  public InvalidCodeException(final String type, short code, Throwable cause) {
    super("A SWF Tag/flag of type '" + type + "' read with invalid code '" + code + "'.\n" +
        "This probably means whatever is being read has invalid/corrupt data, " +
        "but if you believe this to instead be a bug in the library then please contact the author.", cause);
    this.code = code;
  }
  
  public short getCode() {
    return this.code;
  }

}
