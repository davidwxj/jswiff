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
 * <p>
 * Contains data of a binary object (e.g. a nested SWF).
 * </p>
 * 
 * @since SWF 9
 */
public final class DefineBinaryData extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private byte[] binaryData;

  /**
   * Creates a new DefineBinaryData tag.
   * 
   * @param characterId
   *          character ID of the bitmap
   * @param binaryData
   *          binary data storing product information
   */
  public DefineBinaryData(int characterId, byte[] binaryData) {
    super(TagType.DEFINE_BINARY_DATA);
    this.characterId = characterId;
    this.binaryData = binaryData;
  }
  
  DefineBinaryData() {
    super(TagType.DEFINE_BINARY_DATA);
  }

  /**
   * Returns the data contained in the tag.
   * 
   * @return tag data
   */
  public byte[] getBinaryData() {
    return binaryData;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    outStream.writeUI32(0);
    outStream.writeBytes(binaryData);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    inStream.move(4);
    binaryData = new byte[data.length - 6];
    System.arraycopy(data, 6, binaryData, 0, binaryData.length);
  }
}
