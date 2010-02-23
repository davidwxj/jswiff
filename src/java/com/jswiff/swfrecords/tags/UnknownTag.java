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
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * This class implements a container for tag data which cannot be interpreted
 * because the tag type is unknown (e.g. for new Flash versions).
 */
public class UnknownTag extends Tag {

  private static final long serialVersionUID = 1L;
  
  private final short unknownTagCode;
  private byte[] inData;

  /**
   * Creates a new UnknownTag instance.
   *
   * @param code tag code (indicating the tag type)
   */
  public UnknownTag(short code) {
    super(TagType.UNKNOWN_TAG);
    this.unknownTagCode = code;
  }
  
  /**
   * Creates a new UnknownTag instance.
   *
   * @param code tag code (indicating the tag type)
   * @param data tag data
   */
  public UnknownTag(short code, byte[] data) {
    this(code);
    this.inData = data;
  }

  /**
   * Returns the data contained in the tag.
   *
   * @return tag data
   */
  public byte[] getData() {
    return inData;
  }

  /**
   * Returns the string representation of the tag, containing tag code and data
   * size.
   *
   * @return string representation
   */
  public String toString() {
    return super.toString() + " (tag code: " + this.unknownTagCode + "; data size: " + getData().length +
    " bytes)";
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeBytes(inData);
  }

  void setData(byte[] data) {
    inData = data;
  }
  
  @Override
  public short tagCode() {
    return this.unknownTagCode;
  }
  
}
