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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Implements an array of line styles. If used within
 * <code>DefineShape4</code>, the array contains only <code>LineStyle2</code>
 * instances. Otherwise it contains only <code>LineStyle</code> instances.
 * </p>
 * 
 * <p>
 * <b>WARNING:</b> array index starts with 1, not 0
 * </p>
 *
 * @see com.jswiff.swfrecords.tags.DefineShape4
 * @see com.jswiff.swfrecords.LineStyle
 * @see com.jswiff.swfrecords.LineStyle2
 */
public final class LineStyleArray implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private List<LineStyleTag> styles = new ArrayList<LineStyleTag>();

  /**
   * Creates a new LineStyleArray instance.
   */
  public LineStyleArray() {
    // empty
  }

  LineStyleArray(InputBitStream stream, boolean hasAlpha)
    throws IOException {
    int styleCount = stream.readUI8();
    if (styleCount == 0xFF) {
      styleCount = stream.readUI16();
    }
    for (int i = 0; i < styleCount; i++) {
      styles.add(new LineStyle(stream, hasAlpha));
    }
  }

  LineStyleArray(InputBitStream stream) throws IOException {
    int styleCount = stream.readUI8();
    if (styleCount == 0xFF) {
      styleCount = stream.readUI16();
    }
    for (int i = 0; i < styleCount; i++) {
      styles.add(new LineStyle2(stream));
    }
  }

  /**
   * Returns the size of the line style array.
   *
   * @return array size
   */
  public int getSize() {
    return styles.size();
  }

  /**
   * <p>
   * Returns the line style at the specified position in the array. Can be
   * either a <code>LineStyle</code> or a <code>LineStyle2</code> instance.
   * </p>
   * 
   * <p>
   * <b>WARNING:</b> indexes start at 1, not at 0!
   * </p>
   *
   * @param index index starting at 1
   *
   * @return line style located at the specified position
   *
   * @see com.jswiff.swfrecords.LineStyle
   * @see com.jswiff.swfrecords.LineStyle2
   */
  public LineStyleTag getStyle(int index) {
    return styles.get(index - 1);
  }

  /**
   * Returns all contained line styles as a list.
   *
   * @return all line styles
   */
  public List<LineStyleTag> getStyles() {
    return styles;
  }

  /**
   * Adds a line style at the end of the array. Use either
   * <code>LineStyle</code> or <code>LineStyle2</code> instances.
   *
   * @param lineStyle a line style
   *
   * @see com.jswiff.swfrecords.LineStyle
   * @see com.jswiff.swfrecords.LineStyle2
   */
  public void addStyle(LineStyleTag lineStyle) {
    styles.add(lineStyle);
  }

  void write(OutputBitStream stream) throws IOException {
    int styleCount = styles.size();
    if (styleCount >= 0xFF) {
      stream.writeUI8((short) 0xFF);
      stream.writeUI16(styleCount);
    } else {
      stream.writeUI8((short) styleCount);
    }
    for (LineStyleTag ls : this.styles) {
      ls.write(stream);
    }
  }
  
}
