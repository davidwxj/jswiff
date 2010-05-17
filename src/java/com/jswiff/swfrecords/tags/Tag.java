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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

  abstract void setData(byte[] data) throws IOException;

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
