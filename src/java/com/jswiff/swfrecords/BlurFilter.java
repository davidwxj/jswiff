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

import com.jswiff.constants.TagConstants.FilterType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

/**
 * TODO: Comments
 */
public final class BlurFilter extends Filter {
  private double x;
  private double y;
  private int quality;

  /**
   * Creates a new BlurFilter instance.
   *
   * @param x TODO: Comments
   * @param y TODO: Comments
   */
  public BlurFilter(double x, double y) {
    super(FilterType.BLUR);
    this.x   = x;
    this.y   = y;
    quality = 1;
  }

  public BlurFilter(InputBitStream stream) throws IOException {
    super(FilterType.BLUR);
    x = stream.readFP32();
    y = stream.readFP32();
    quality = (int) stream.readUnsignedBits(5);
    stream.align();
  }
  
  public void write(OutputBitStream stream) throws IOException {
    stream.writeFP32(x);
    stream.writeFP32(y);
    stream.writeUnsignedBits(quality, 5);
    stream.writeUnsignedBits(0, 3);
  }
  
  /**
   * TODO: Comments
   *
   * @param quality TODO: Comments
   */
  public void setQuality(int quality) {
    if ((quality < 0) || (quality > 15)) {
      throw new IllegalArgumentException("quality must be between 0 and 15");
    }
    this.quality = quality;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public int getQuality() {
    return quality;
  }

  /**
   * TODO: Comments
   *
   * @param x TODO: Comments
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getX() {
    return x;
  }

  /**
   * TODO: Comments
   *
   * @param y TODO: Comments
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getY() {
    return y;
  }
}
