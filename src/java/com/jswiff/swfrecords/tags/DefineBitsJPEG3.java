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
