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
import com.jswiff.swfrecords.Shape;


/**
 * This tag defines the name and license of a font.
 *
 * @since SWF 9.
 */
public final class DefineFontName extends Tag {
  private int fontId;
  private String fontName;
  private String fontLicense;

  public DefineFontName(int fontId, String fontName, String fontLicense) {
    this.fontId = fontId;
    this.fontName = fontName;
    this.fontLicense = fontLicense;
    code = TagConstants.DEFINE_FONT_NAME;
  }

  DefineFontName() {
    // empty
  }

  public int getFontId() {
    return fontId;
  }

  public String getFontName() {
    return fontName;
  }

  public String getFontLicense() {
    return fontLicense;
  }
  
  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    fontId = inStream.readUI16();
    fontName = inStream.readString();
    fontLicense = inStream.readString();
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(fontId);
    outStream.writeString(fontName);
    outStream.writeString(fontLicense);
  }
}
