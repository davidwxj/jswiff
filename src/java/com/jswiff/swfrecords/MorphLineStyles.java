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

import com.jswiff.exception.InvalidCodeException;
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
  
  private static final long serialVersionUID = 1L;
  
  private List<MorphLineStyleTag> styles = new ArrayList<MorphLineStyleTag>();

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
   * @throws InvalidCodeException if the tag header contains an invalid code.
   * This normally means invalid or corrupted data.
   */
  public MorphLineStyles(InputBitStream stream, boolean useNewMorphLineStyle)
    throws IOException, InvalidCodeException {
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
  public MorphLineStyleTag getStyle(int index) {
    return styles.get(index - 1);
  }

  /**
   * Returns all contained morph line styles as a list.
   *
   * @return all morph line styles
   */
  public List<MorphLineStyleTag> getStyles() {
    return styles;
  }

  /**
   * Adds a morph line style at the end of the array. Use either
   * <code>MorphLineStyle</code> or <code>MorphLineStyle2</code> instances.
   *
   * @param lineStyle a morph line style
   */
  public void addStyle(MorphLineStyleTag lineStyle) {
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
    for (MorphLineStyleTag mls : this.styles) {
      mls.write(stream);
    }
  }
  
}
