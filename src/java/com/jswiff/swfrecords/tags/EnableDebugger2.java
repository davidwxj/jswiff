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
 * This tag enables debugging within the Macromedia Flash authoring tool. A
 * password encrypted with the MD5 algorithm has to be supplied.
 * </p>
 * 
 * <p>
 * Note: the format of the debugging information required in the ActionScript
 * debugger has changed with version 6. In SWF 6 or later, this tag is used
 * instead of <code>EnableDebugger</code>.
 * </p>
 * 
 * @since SWF 6
 */
public final class EnableDebugger2 extends Tag {

  private static final long serialVersionUID = 1L;

  private String password;

  /**
   * Creates a new EnableDebugger instance. Supply a password encrypted with the
   * MD5 algorithm.
   * 
   * @param password
   *          MD5 encrypted password
   */
  public EnableDebugger2(String password) {
    super(TagType.ENABLE_DEBUGGER_2);
    this.password = password;
  }

  EnableDebugger2() {
    super(TagType.ENABLE_DEBUGGER_2);
  }

  /**
   * Sets the (MD5-encrypted) password.
   * 
   * @param password
   *          encrypted password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the MD5-encrypted password.
   * 
   * @return encrypted password
   */
  public String getPassword() {
    return password;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(0);
    if (password != null) {
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
