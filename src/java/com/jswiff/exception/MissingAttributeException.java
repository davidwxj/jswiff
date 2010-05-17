/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
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
