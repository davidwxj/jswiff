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
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

/**
 * Defines a bitmap character with JPEG compression. It contains only the JPEG
 * compressed image data. The JPEG encoding data is contained in a
 * <code>JPEGTables</code> tag.
 * 
 * @since SWF 1
 */
public final class DefineBits extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private byte[] jpegData;

  /**
   * Creates a new DefineBits tag.
   * 
   * @param characterId
   *          character ID of the bitmap
   * @param jpegData
   *          image data
   */
  public DefineBits(int characterId, byte[] jpegData) {
    super(TagType.DEFINE_BITS);
    this.characterId = characterId;
    this.jpegData = jpegData;
  }
  
  DefineBits() {
    super(TagType.DEFINE_BITS);
  }

  /**
   * Sets the byte array containing the image data.
   * 
   * @param jpegData
   *          image data
   */
  public void setJpegData(byte[] jpegData) {
    this.jpegData = jpegData;
  }

  /**
   * Returns the image data as byte array.
   * 
   * @return bitmap image data
   */
  public byte[] getJpegData() {
    return jpegData;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    outStream.writeBytes(jpegData);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    jpegData = new byte[data.length - 2];
    System.arraycopy(data, 2, jpegData, 0, jpegData.length);
  }
}
