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
 * Indicates that a mandatory attribute is missing within a specific parent
 * element.
 */
public class MissingAttributeException extends MissingNodeException {

  private static final long serialVersionUID = 1L;
  
  private String missingAttributeName;
  private String parentElementPath;

  /**
   * Creates a new MissingAttributeException instance. Pass the name of the
   * missing attribute and the path of the parent element (in XPath notation).
   *
   * @param missingAttributeName name of the missing element
   * @param parentElementPath TODO: Comments
   */
  public MissingAttributeException(
    String missingAttributeName, String parentElementPath) {
    super(
      "Mandatory attribute '" + missingAttributeName + "' missing from " +
      parentElementPath);
    this.missingAttributeName   = missingAttributeName;
    this.parentElementPath      = parentElementPath;
  }

  /**
   * Returns the name of the missing attribute.
   *
   * @return missing attribute name
   */
  public String getMissingAttributeName() {
    return missingAttributeName;
  }

  /**
   * Returns the path of the parent element (in XPath notation).
   *
   * @return parent element path
   */
  public String getParentElementPath() {
    return parentElementPath;
  }
}
