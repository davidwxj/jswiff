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

import com.jswiff.constants.TagConstants;
import com.jswiff.io.InputBitStream;

import java.io.IOException;

/**
 * This class represents a SWF tag header.
 */
public final class TagHeader {

  private static final long serialVersionUID = 1L;

  private short code;
  private int length;

  /*
   * Creates a new TagHeader instance.
   */
  TagHeader() {
    // nothing to do
  }

  TagHeader(InputBitStream stream) throws IOException {
    read(stream);
  }

  /**
   * Returns the code of the tag which designates its type.
   * 
   * @return tag type code
   * 
   * @see TagConstants
   */
  public short getCode() {
    return code;
  }

  /**
   * Returns the length of the tag.
   * 
   * @return tag length
   */
  public int getLength() {
    return length;
  }

  private void read(InputBitStream stream) throws IOException {
    int codeAndLength = stream.readUI16();
    code = (short) (codeAndLength >> 6); // upper 10 bits
    length = codeAndLength & 0x3F; // 0x3F = 63 = 111111, lower 6 bits
    if (length == 0x3F) {
      length = (int) stream.readUI32();
    }
  }
}
