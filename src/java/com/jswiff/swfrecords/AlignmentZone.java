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


/**
 * TODO: Comments
 */
public final class AlignmentZone implements Serializable {
  private float left;
  private float width;
  private float baseline;
  private float height;
  private boolean hasX;
  private boolean hasY;

  /**
   * Creates a new AlignmentZone instance.
   */
  public AlignmentZone() {
    // empty
  }

  /**
   * Creates a new AlignmentZone instance.
   *
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public AlignmentZone(InputBitStream stream) throws IOException {
    stream.readUI8(); // always 2
    left       = stream.readFloat16();
    width      = stream.readFloat16();
    baseline   = stream.readFloat16();
    height     = stream.readFloat16();
    stream.readUnsignedBits(6);
    hasX   = stream.readBooleanBit();
    hasY   = stream.readBooleanBit();
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float getBaseline() {
    return baseline;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float getHeight() {
    return height;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float getLeft() {
    return left;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float getWidth() {
    return width;
  }

  /**
   * TODO: Comments
   *
   * @param left TODO: Comments
   * @param width TODO: Comments
   */
  public void setX(float left, float width) {
    this.left    = left;
    this.width   = width;
    hasX         = true;
  }

  /**
   * TODO: Comments
   *
   * @param baseline TODO: Comments
   * @param height TODO: Comments
   */
  public void setY(float baseline, float height) {
    this.baseline   = baseline;
    this.height     = height;
    hasY            = true;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean hasX() {
    return hasX;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean hasY() {
    return hasY;
  }

  /**
   * TODO: Comments
   *
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8((short) 2);
    stream.writeFloat16(left);
    stream.writeFloat16(width);
    stream.writeFloat16(baseline);
    stream.writeFloat16(height);
    stream.writeUnsignedBits(0, 6);
    stream.writeBooleanBit(hasX);
    stream.writeBooleanBit(hasY);
  }
}
