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

/**
 * This tag is used as container for malformed tag data which could not be
 * interpreted. The exception thrown while parsing the malformed tag is also
 * contained herein and can be used for error tracing.
 */
public final class MalformedTag extends Tag {

  private static final long serialVersionUID = 1L;

  private byte[] data;
  private TagHeader tagHeader;
  private Exception exception;

  /**
   * Creates a new MalformedTag instance. It makes no sense to add MalformedTag
   * instances to a SWF document, as SWF writers don't write their contents.
   * 
   * @param tagHeader
   *          tag header
   * @param data
   *          raw tag data
   * @param exception
   *          exception thrown while parsing the tag
   */
  public MalformedTag(TagHeader tagHeader, byte[] data, Exception exception) {
    super(TagType.MALFORMED);
    this.tagHeader = tagHeader;
    this.data = data;
    this.exception = exception;
  }

  /**
   * Returns the raw data of the tag.
   * 
   * @return tag data
   */
  public byte[] getData() {
    return data;
  }

  /**
   * Returns the exception which occured at parsing time.
   * 
   * @return exception thrown while parsing tag
   */
  public Exception getException() {
    return exception;
  }

  /**
   * Returns the header of the tag.
   * 
   * @return tag header
   */
  public TagHeader getTagHeader() {
    return tagHeader;
  }

  protected void writeData(OutputBitStream outStream) {
    // do nothing
  }

  void setData(byte[] data) {
    this.data = data;
  }

  void write(OutputBitStream stream) {
    // don't write malformed tags...
  }
}
