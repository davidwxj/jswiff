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

import com.jswiff.constants.TagConstants;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.RGB;


/**
 * This tag contains the background color of the SWF. Do NOT add this tag to
 * your <code>SWFDocument</code>, use its <code>setBackgroundColor</code>
 * method instead!
 *
 * @see com.jswiff.SWFDocument#setBackgroundColor(RGB)
 * @since SWF 1
 */
public final class SetBackgroundColor extends Tag {
  private RGB color;

  /**
   * Creates a new SetBackgroundColor tag.
   *
   * @param color background color
   */
  public SetBackgroundColor(RGB color) {
    code         = TagConstants.SET_BACKGROUND_COLOR;
    this.color   = color;
  }

  SetBackgroundColor() {
    // empty
  }

  /**
   * Returns the background color.
   *
   * @return Returns the color.
   */
  public RGB getColor() {
    return color;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    color.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    color = new RGB(inStream);
  }
}
