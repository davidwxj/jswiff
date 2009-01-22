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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * This class is used to define line styles used in morph sequences. Line
 * styles are defined in pairs: for start and for end shapes.
 *
 * @see MorphLineStyles
 * @see com.jswiff.swfrecords.tags.DefineMorphShape
 */
public final class MorphLineStyle implements MorphLineStyleTag, Serializable {
  
  private static final long serialVersionUID = 1L;

  private int startWidth;
  private int endWidth;
  private RGBA startColor;
  private RGBA endColor;

  /**
   * Creates a new MorphLineStyle instance. Supply width and RGBA color for
   * lines in start and end shapes.
   *
   * @param startWidth width of line in start shape (in twips = 1/20 px)
   * @param startColor color of line in start shape
   * @param endWidth width of line in start shape (in twips = 1/20 px)
   * @param endColor color of line in end shape
   */
  public MorphLineStyle(
    int startWidth, RGBA startColor, int endWidth, RGBA endColor) {
    this.startWidth   = startWidth;
    this.startColor   = startColor;
    this.endWidth     = endWidth;
    this.endColor     = endColor;
  }

  MorphLineStyle(InputBitStream stream) throws IOException {
    startWidth   = stream.readUI16();
    endWidth     = stream.readUI16();
    startColor   = new RGBA(stream);
    endColor     = new RGBA(stream);
  }

  /**
   * Returns the color of lines used in the end shape of the morph sequence.
   *
   * @return RGBA color of lines in end shape
   */
  public RGBA getEndColor() {
    return endColor;
  }

  /**
   * Returns the width of lines used in the end shape of the morph sequence.
   *
   * @return width of lines in end shape in twips (1/20 px)
   */
  public int getEndWidth() {
    return endWidth;
  }

  /**
   * Returns the color of lines used in the start shape of the morph sequence.
   *
   * @return RGBA color of lines in start shape
   */
  public RGBA getStartColor() {
    return startColor;
  }

  /**
   * Returns the width of lines used in the start shape of the morph sequence.
   *
   * @return width of lines in start shape in twips (1/20 px)
   */
  public int getStartWidth() {
    return startWidth;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(startWidth);
    stream.writeUI16(endWidth);
    startColor.write(stream);
    endColor.write(stream);
  }
  
}
