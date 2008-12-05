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
 * Implements an array of line styles used in a morph sequence. Line styles are
 * defined pairwise in <code>MorphLineStyle</code> or <code>MorphLineStyle2</code> instances.
 * </p>
 * 
 * <p>
 * <b>WARNING:</b> array index starts at 1, not 0!
 * </p>
 *
 * @see com.jswiff.swfrecords.MorphLineStyle
 * @see com.jswiff.swfrecords.MorphLineStyle2
 * @see com.jswiff.swfrecords.tags.DefineMorphShape
 * @see com.jswiff.swfrecords.tags.DefineMorphShape2
 */
public final class MorphLineStyles implements Serializable {
  
  //FIXME: Give MorphLineStyle and MorphLineStyle2 a common lineage
  //and tighten generic type for styles array, etc.
  private List<Object> styles = new ArrayList<Object>();

  /**
   * Creates a new MorphLineStyles instance.
   */
  public MorphLineStyles() {
    // empty
  }

  /**
   * Reads a new instance from a bit stream.
   *
   * @param stream source bit stream
   * @param useNewMorphLineStyle TODO: Comments
   *
   * @throws IOException if an I/O error occured
   */
  public MorphLineStyles(InputBitStream stream, boolean useNewMorphLineStyle)
    throws IOException {
    int styleCount = stream.readUI8();
    if (styleCount == 0xFF) {
      styleCount = stream.readUI16();
    }
    for (int i = 0; i < styleCount; i++) {
      if (useNewMorphLineStyle) {
        styles.add(new MorphLineStyle2(stream));
      } else {
        styles.add(new MorphLineStyle(stream));
      }
    }
  }

  /**
   * Returns the size of the morph line style array.
   *
   * @return array size
   */
  public int getSize() {
    return styles.size();
  }

  /**
   * <p>
   * Returns the morph line style at the specified position in the array. Can
   * be a <code>MorphLineStyle</code> or a <code>MorphLineStyle2</code>
   * instance.
   * </p>
   * 
   * <p>
   * <b>WARNING:</b> indexes start at 1, not at 0!
   * </p>
   *
   * @param index index starting at 1
   *
   * @return morph line style located at the specified position
   */
  public Object getStyle(int index) {
    return styles.get(index - 1);
  }

  /**
   * Returns all contained morph line styles as a list.
   *
   * @return all morph line styles
   */
  public List<Object> getStyles() {
    return styles;
  }

  /**
   * Adds a morph line style at the end of the array. Use either
   * <code>MorphLineStyle</code> or <code>MorphLineStyle2</code> instances.
   *
   * @param lineStyle a morph line style
   */
  public void addStyle(Object lineStyle) {
    if ( !(lineStyle instanceof MorphLineStyle) 
      && !(lineStyle instanceof MorphLineStyle2) ) {
        throw new IllegalArgumentException(
            "Parameter has type '" + lineStyle.getClass().getName()
            + "', must be of type MorphLineStyle or MorphLineStyle2");
      }
    styles.add(lineStyle);
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
      Object lineStyle = styles.get(i);
      if (lineStyle instanceof MorphLineStyle) {
        ((MorphLineStyle) lineStyle).write(stream);
      } else {
        ((MorphLineStyle2) lineStyle).write(stream);
      }
    }
  }
}