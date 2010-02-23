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

/**
 * This tag removes a character with a given ID, thereby freeing memory from
 * Flash Player.
 */
public final class FreeCharacter extends Tag {

  private static final long serialVersionUID = 1L;

  private int characterId;

  /**
   * Creates a new FreeCharacter tag. Supply the ID of the character to be
   * removed.
   * 
   * @param characterId
   *          character ID to be removed
   */
  public FreeCharacter(int characterId) {
    super(TagType.FREE_CHARACTER);
    this.characterId = characterId;
  }

  FreeCharacter() {
    super(TagType.FREE_CHARACTER);
  }

  /**
   * Sets the ID of the character to be removed
   * 
   * @param characterId
   *          character ID to be removed
   */
  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  /**
   * Returns the ID of the character to be removed from memory.
   * 
   * @return character ID to be removed
   */
  public int getCharacterId() {
    return characterId;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
  }
}
