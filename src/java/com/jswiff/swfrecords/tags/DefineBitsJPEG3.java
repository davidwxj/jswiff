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
 * Defines a bitmap character with JPEG compression. Unlike
 * <code>DefineBitsJPEG2</code>, this tag adds alpha channel (transparency)
 * support. As transparency is not a standard feature in JPEG images, the alpha
 * channel information is encoded separately from the JPEG data.
 * 
 * @since SWF 3
 */
public final class DefineBitsJPEG3 extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private byte[] jpegData;
  private byte[] bitmapAlphaData;

  /**
   * Creates a new DefineBitsJPEG3 tag.
   * 
   * @param characterId
   *          character ID of the bitmap
   * @param jpegData
   *          JPEG data (image and encoding)
   * @param bitmapAlphaData
   *          alpha channel data (ZLIB-compressed)
   */
  public DefineBitsJPEG3(int characterId, byte[] jpegData, byte[] bitmapAlphaData) {
    super(TagType.DEFINE_BITS_JPEG_3);
    this.characterId = characterId;
    this.jpegData = jpegData;
    this.bitmapAlphaData = bitmapAlphaData;
  }
  
  DefineBitsJPEG3() {
    super(TagType.DEFINE_BITS_JPEG_3);
  }

  /**
   * Sets the ZLIB-compressed alpha channel data.
   * 
   * @param bitmapAlphaData
   *          alpha channel data
   */
  public void setBitmapAlphaData(byte[] bitmapAlphaData) {
    this.bitmapAlphaData = bitmapAlphaData;
  }

  /**
   * Returns the ZLIB-compressed alpha channel data.
   * 
   * @return alpha channel data
   */
  public byte[] getBitmapAlphaData() {
    return bitmapAlphaData;
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
   * Returns the JPEG data contained in the tag.
   * 
   * @return JPEG data (image and encoding)
   */
  public byte[] getJpegData() {
    return jpegData;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    this.setForceLongHeader(true);
    outStream.writeUI16(characterId);
    outStream.writeUI32(jpegData.length); // alphaDataOffset
    outStream.writeBytes(jpegData);
    outStream.writeBytes(bitmapAlphaData);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    int alphaDataOffset = (int) inStream.readUI32();
    jpegData = new byte[alphaDataOffset];
    System.arraycopy(data, 6, jpegData, 0, alphaDataOffset);
    int alphaDataSize = data.length - 6 - alphaDataOffset;
    bitmapAlphaData = new byte[alphaDataSize];
    System.arraycopy(data, 6 + alphaDataOffset, bitmapAlphaData, 0, alphaDataSize);
  }
}
