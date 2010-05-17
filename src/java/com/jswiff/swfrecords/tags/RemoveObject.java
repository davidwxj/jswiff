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
 * The RemoveObject tag removes the instance of a particular character at the
 * specified depth from the display list.
 * 
 * @since SWF 1
 */
public final class RemoveObject extends Tag {

  private static final long serialVersionUID = 1L;

  private int characterId;
  private int depth;

  /**
   * Creates a new RemoveObject tag. Specify character ID and depth of the
   * instance to be removed.
   * 
   * @param characterId
   *          character ID of instance to remove
   * @param depth
   *          depth of instance to remove
   */
  public RemoveObject(int characterId, int depth) {
    super(TagType.REMOVE_OBJECT);
    this.characterId = characterId;
    this.depth = depth;
  }

  RemoveObject() {
    super(TagType.REMOVE_OBJECT);
  }

  /**
   * Sets the character ID of the instance which is supposed to be removed.
   * 
   * @param characterId
   *          character ID of instance to be removed
   */
  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  /**
   * Returns the character ID of the instance which is supposed to be removed.
   * 
   * @return character ID of instance to be removed
   */
  public int getCharacterId() {
    return characterId;
  }

  /**
   * Sets the depth of the character instance which is supposed to be removed.
   * 
   * @param depth
   *          depth of instance to be removed
   */
  public void setDepth(int depth) {
    this.depth = depth;
  }

  /**
   * Returns the depth of the character instance which is supposed to be
   * removed.
   * 
   * @return depth of instance to be removed
   */
  public int getDepth() {
    return depth;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    outStream.writeUI16(depth);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    depth = inStream.readUI16();
  }
}
