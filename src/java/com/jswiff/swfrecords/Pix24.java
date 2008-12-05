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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * This class is used for the representation of 24-bit pixel data.
 */
public final class Pix24 extends BitmapPixelData {
  private short red;
  private short green;
  private short blue;

  /**
   * Creates a new Pix24 instance. Specify red, green and blue values.
   *
   * @param red red value (between 0 and 255)
   * @param green green value (between 0 and 255)
   * @param blue blue value (between 0 and 255)
   */
  public Pix24(short red, short green, short blue) {
    this.red     = red;
    this.green   = green;
    this.blue    = blue;
  }

  Pix24(InputBitStream stream) throws IOException {
    stream.readUI8();
    red     = stream.readUI8();
    green   = stream.readUI8();
    blue    = stream.readUI8();
  }

  /**
   * Returns the blue value
   *
   * @return blue value (between 0 and 255)
   */
  public short getBlue() {
    return blue;
  }

  /**
   * Returns the green value
   *
   * @return green value (between 0 and 255)
   */
  public short getGreen() {
    return green;
  }

  /**
   * Returns the red value
   *
   * @return red value (between 0 and 255)
   */
  public short getRed() {
    return red;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUI8((short) 255);
    stream.writeUI8(red);
    stream.writeUI8(green);
    stream.writeUI8(blue);
  }
}