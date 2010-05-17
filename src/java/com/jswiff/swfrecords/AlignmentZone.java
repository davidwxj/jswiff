/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
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
