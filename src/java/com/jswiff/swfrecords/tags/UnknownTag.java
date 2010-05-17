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
