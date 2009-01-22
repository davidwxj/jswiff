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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.OutputBitStream;

/**
 * Base class for SWF tags.
 */
public abstract class Tag implements Serializable {

  private static final long serialVersionUID = 1L;

  private final TagType tagType;
  private boolean forceLongHeader; // override this with =true to write long headers
  private int length;
  private byte[] outData;
  private short swfVersion;
  private boolean shiftJIS;

  public Tag(TagType tagType) {
    this.tagType = tagType;
  }
  
  /**
   * Get the type of the tag
   * @return the type of tag
   */
  public TagType tagType() {
    return this.tagType;
  }
  
  /**
   * In most cases this is the same as getTagType.getCode(), but UnknownTag for example
   * will return something different.
   * @return the tag code
   */
  public short tagCode() {
    return this.tagType.getCode();
  }

  protected boolean isForceLongHeader() {
    return forceLongHeader;
  }

  protected void setForceLongHeader(boolean forceLongHeader) {
    this.forceLongHeader = forceLongHeader;
  }

  protected int getLength() {
    return length;
  }

  protected void setLength(int length) {
    this.length = length;
  }

  /**
   * Creates a deep copy of this tag. Useful if you want to clone a part of a
   * SWF document.
   * 
   * @return a copy of the tag
   */
  public Tag copy() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(this);
      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      ObjectInputStream ois = new ObjectInputStream(bais);
      return (Tag) ois.readObject();
    } catch (Exception e) {
      // actually, this should never happen (everything serializable??)
      // this will eventually be removed
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * This method writes the tag data to a bit stream. Descendants of the
   * <code>Tag</code> class must implement this class.
   * 
   * @param outStream
   *          target bit stream
   * 
   * @throws IOException
   *           if an I/O error has occured
   */
  abstract protected void writeData(OutputBitStream outStream) throws IOException;

  abstract void setData(byte[] data) throws IOException, InvalidCodeException;

  void setSWFVersion(short swfVersion) {
    this.swfVersion = swfVersion;
  }

  short getSWFVersion() {
    return swfVersion;
  }

  void setJapanese(boolean shiftJIS) {
    this.shiftJIS = shiftJIS;
  }

  boolean isJapanese() {
    return shiftJIS;
  }

  void write(OutputBitStream stream) throws IOException {
    initData(stream);
    stream.writeBytes(getHeaderData());
    stream.writeBytes(outData);
  }

  private byte[] getHeaderData() throws IOException {
    OutputBitStream headerStream = new OutputBitStream();
    int typeAndLength = this.tagCode() << 6;
    length = outData.length;
    // for the length to be correct, initData() must be called before this
    if (forceLongHeader || (length >= 0x3F)) {
      // long header
      typeAndLength |= 0x3F;
      headerStream.writeUI16(typeAndLength);
      headerStream.writeUI32(length);
    } else {
      // short header
      typeAndLength |= length;
      headerStream.writeUI16(typeAndLength);
    }
    return headerStream.getData();
  }

  private void initData(OutputBitStream parentStream) throws IOException {
    OutputBitStream outStream = new OutputBitStream();
    outStream.setANSI(parentStream.isANSI());
    outStream.setShiftJIS(parentStream.isShiftJIS());
    writeData(outStream);
    outData = outStream.getData();
  }
  
  @Override
  public String toString() {
    return tagType().getNiceName();
  }
  
}
