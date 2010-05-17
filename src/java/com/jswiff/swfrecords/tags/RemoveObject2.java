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
 * The RemoveObject2 tag removes the character instance at the specified depth
 * from the display list.
 * 
 * @since SWF 3
 */
public final class RemoveObject2 extends Tag {

  private static final long serialVersionUID = 1L;

  private int depth;

  /**
   * Creates a new RemoveObject2 tag. Supply the depth of the character instance
   * to be removed.
   * 
   * @param depth
   *          depth of instance to be removed
   */
  public RemoveObject2(int depth) {
    super(TagType.REMOVE_OBJECT_2);
    this.depth = depth;
  }

  RemoveObject2() {
    super(TagType.REMOVE_OBJECT_2);
  }

  /**
   * Sets the depth of the character instance to be removed.
   * 
   * @param depth
   *          depth of instance to be removed
   */
  public void setDepth(int depth) {
    this.depth = depth;
  }

  /**
   * Returns the depth of the character instance to be removed.
   * 
   * @return depth of instance to be removed
   */
  public int getDepth() {
    return depth;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(depth);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    depth = inStream.readUI16();
  }
}
