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
