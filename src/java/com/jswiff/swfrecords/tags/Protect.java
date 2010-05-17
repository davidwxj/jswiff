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

package com.jswiff.swfrecords.tags;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

/**
 * <p>
 * This tag is used to advise SWF editors to restrict edition capabilities for
 * the current SWF ducument. The behavior depends on the editor's
 * implementation.
 * </p>
 * 
 * <p>
 * Upon encountering a <code>Protect</code> tag, the Macromedia Flash authoring
 * environment checks if a password is contained in this tag. If the password is
 * missing, the file import is disallowed. Otherwise, file import is allowed if
 * the correct password is specified.
 * </p>
 * 
 * <p>
 * <b>Warning:</b> This tag is advisory only. Since editors might choose to
 * ignore it, this is not an appropriate way of protecting sensitive data.
 * </p>
 * 
 * <p>
 * <b>Warning:</b> the main purpose of this tag is to mark the file as being
 * copyrighted in a way or another. Importing the file regardless of this tag
 * may be considered by the file's author as reverse engineering and copyright
 * violation.
 * </p>
 * 
 * @since SWF 2
 */
public final class Protect extends Tag {

  private static final long serialVersionUID = 1L;

  private String password;

  /**
   * Creates a new Protect tag. Supply a password encrypted with the MD5
   * algorithm.
   * 
   * @param password
   *          MD5-encrypted password
   */
  public Protect(String password) {
    super(TagType.PROTECT);
    this.password = password;
  }

  Protect() {
    super(TagType.PROTECT);
  }

  /**
   * Sets the MD5 encrypted password the user needs to supply in order to be
   * able to import this SWF file.
   * 
   * @param password
   *          MD5-encrypted password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the MD5 encrypted password the user needs to supply in order to be
   * able to import this SWF file.
   * 
   * @return MD5-encrypted password
   */
  public String getPassword() {
    return password;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    if (password != null) {
      outStream.writeUI16(0);
      outStream.writeString(password);
    }
  }

  void setData(byte[] data) throws IOException {
    if ((data.length > 3)) {
      // reserved UINT16=0, then passwd as MD5
      password = new String(data, 2, data.length - 3, "UTF-8");
    }
  }
}
