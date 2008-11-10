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

package com.jswiff.swfrecords;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * Implements an array of fill styles used in a morphing sequence.
 * </p>
 * 
 * <p>
 * <b>WARNING:</b> array index starts at 1, not 0!
 * </p>
 */
public final class MorphFillStyles implements Serializable {
  private List<MorphFillStyle> styles = new ArrayList<MorphFillStyle>();

  /**
   * Creates a new MorphFillStyles instance.
   */
  public MorphFillStyles() {
    // empty
  }

  /**
   * Reads a new instance from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public MorphFillStyles(InputBitStream stream) throws IOException {
    int styleCount = stream.readUI8();
    if (styleCount == 0xFF) {
      styleCount = stream.readUI16();
    }
    for (int i = 0; i < styleCount; i++) {
      styles.add(new MorphFillStyle(stream));
    }
  }

  /**
   * Returns the size of the morph fill style array.
   *
   * @return array size
   */
  public int getSize() {
    return styles.size();
  }

  /**
   * <p>
   * Returns the morph fill style at the specified position in the array.
   * </p>
   * 
   * <p>
   * <b>WARNING:</b> indexes start at 1, not at 0!
   * </p>
   *
   * @param index index starting at 1
   *
   * @return morph fill style located at the specified position
   */
  public MorphFillStyle getStyle(int index) {
    return styles.get(index - 1);
  }

  /**
   * Adds a morph fill style at the end of the array.
   *
   * @param fillStyle a morph fill style
   */
  public void addStyle(MorphFillStyle fillStyle) {
    styles.add(fillStyle);
  }

  /**
   * Writes this instance to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream) throws IOException {
    int styleCount = styles.size();
    if (styleCount >= 0xFF) {
      stream.writeUI8((short) 0xFF);
      stream.writeUI16(styleCount);
    } else {
      stream.writeUI8((short) styleCount);
    }
    for (int i = 0; i < styles.size(); i++) {
      styles.get(i).write(stream);
    }
  }
}
