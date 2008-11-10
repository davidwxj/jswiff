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

import java.io.IOException;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.Rect;


/**
 * @since SWF 8
 */
public class Scale9Grid extends Tag {
  private int characterId;
  private Rect grid;

  /**
   * Creates a new Scale9Grid instance.
   *
   * @param characterId TODO: Comments
   * @param grid TODO: Comments
   */
  public Scale9Grid(int characterId, Rect grid) {
    code               = TagConstants.SCALE_9_GRID;
    this.characterId   = characterId;
    this.grid          = grid;
  }

  Scale9Grid() {
    // empty
  }

  /**
   * TODO: Comments
   *
   * @param characterId TODO: Comments
   */
  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public int getCharacterId() {
    return characterId;
  }

  /**
   * TODO: Comments
   *
   * @param grid TODO: Comments
   */
  public void setGrid(Rect grid) {
    this.grid = grid;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public Rect getGrid() {
    return grid;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    grid.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    grid = new Rect(inStream);
  }
}
