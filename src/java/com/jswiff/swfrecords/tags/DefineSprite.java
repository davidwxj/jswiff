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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This tag defines a sprite character (i.e. a movie inside the main SWF movie).
 * It consists of a character ID, a frame count and several control tags.
 * Character instances referred to by these control tags in the sprite must have
 * been previously defined.
 * </p>
 * 
 * <p>
 * Once defined, the sprite can be displayed using the <code>PlaceObject2</code>
 * tag.
 * </p>
 * 
 * @see PlaceObject2
 * @since SWF 3
 */
public final class DefineSprite extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private List<Tag> controlTags = new ArrayList<Tag>();

  /**
   * Creates a new DefineSprite tag. Supply the character ID of the sprite.
   * After tag creation, use <code>addControlTag()</code> to add tags to the
   * sprite's tag list.
   * 
   * @param characterId
   *          sprite's character ID
   */
  public DefineSprite(int characterId) {
    super(TagType.DEFINE_SPRITE);
    this.characterId = characterId;
  }

  DefineSprite() {
    super(TagType.DEFINE_SPRITE);
  }

  /**
   * Returns the list of control tags contained in the sprite.
   * 
   * @return the sprite's control tags
   */
  public List<Tag> getControlTags() {
    return controlTags;
  }

  /**
   * Returns the number of frames contained in the sprite.
   * 
   * @return Returns the frameCount.
   */
  public int getFrameCount() {
    int count = 0;
    for (Tag tag : controlTags) {
      if (TagType.SHOW_FRAME.equals(tag.tagType())) {
        count++;
      }
    }
    return count;
  }

  /**
   * Adds a control tag to the sprite. Do not use definition tags (
   * <code>Define...</code>) here!
   * 
   * @param controlTag
   *          a control tag
   */
  public void addControlTag(Tag controlTag) {
    controlTags.add(controlTag);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    this.setForceLongHeader(true);
    outStream.writeUI16(characterId);
    outStream.writeUI16(getFrameCount());
    TagWriter.writeTags(outStream, controlTags, getSWFVersion());
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    inStream.readUI16(); // frameCount
    do {
      Tag tag = TagReader.readTag(inStream, getSWFVersion(), isJapanese());
      if (!TagType.END.equals(tag.tagType())) {
        controlTags.add(tag);
      } else {
        break;
      }
    } while (true);
  }
}
