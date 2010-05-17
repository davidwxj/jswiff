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
