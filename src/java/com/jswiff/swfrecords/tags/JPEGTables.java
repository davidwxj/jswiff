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
 * This tag contains the JPEG encoding table (the Tables/Misc segment) for all
 * JPEG images defined in the SWF file with the <code>DefineBits</code> tag.
 * 
 * @since SWF 1
 */
public final class JPEGTables extends Tag {

  private static final long serialVersionUID = 1L;

  private byte[] jpegData;

  /**
   * Creates a new JPEGTables instance.
   * 
   * @param jpegData
   *          JPEG encoding data
   */
  public JPEGTables(byte[] jpegData) {
    super(TagType.JPEG_TABLES);
    this.jpegData = jpegData;
  }

  JPEGTables() {
    super(TagType.JPEG_TABLES);
  }

  /**
   * Sets the contained JPEG encoding data.
   * 
   * @param jpegData
   *          encoding data
   */
  public void setJpegData(byte[] jpegData) {
    this.jpegData = jpegData;
  }

  /**
   * Returns the contained JPEG encoding data.
   * 
   * @return JPEG encoding data
   */
  public byte[] getJpegData() {
    return jpegData;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeBytes(jpegData);
  }

  void setData(byte[] data) {
    jpegData = data;
  }
}
