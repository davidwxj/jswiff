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
 * This class is used for the representation of a rectangle. A rectangle is
 * defined by a minimum x- and y-coordinate and a maximum x- and y-coordinate
 * position.
 */
public final class Rect implements Serializable {
  private long xMin;
  private long xMax;
  private long yMin;
  private long yMax;

  /**
   * Creates a new Rect instance. Four coordinates must be specified.
   *
   * @param xMin minimum x in twips (1/20 px)
   * @param xMax maximum x in twips
   * @param yMin minimum y in twips
   * @param yMax maximum y in twips
   */
  public Rect(long xMin, long xMax, long yMin, long yMax) {
    this.xMin   = xMin;
    this.xMax   = xMax;
    this.yMin   = yMin;
    this.yMax   = yMax;
  }

  /*
   *
   */
  public Rect(InputBitStream stream) throws IOException {
    int nBits = (int) (stream.readUnsignedBits(5));
    xMin   = stream.readSignedBits(nBits);
    xMax   = stream.readSignedBits(nBits);
    yMin   = stream.readSignedBits(nBits);
    yMax   = stream.readSignedBits(nBits);
    stream.align();
  }

  /**
   * Returns the maximum x coordinate.
   *
   * @return max x
   */
  public long getXMax() {
    return xMax;
  }

  /**
   * Returns the minimum x coordinate.
   *
   * @return min x
   */
  public long getXMin() {
    return xMin;
  }

  /**
   * Returns the maximum y coordinate.
   *
   * @return max y
   */
  public long getYMax() {
    return yMax;
  }

  /**
   * Returns the minimum y coordinate.
   *
   * @return min y
   */
  public long getYMin() {
    return yMin;
  }

  /**
   * Returns the string representation of the rectangle.
   *
   * @return string representation
   */
  public String toString() {
    return "Rect (" + xMin + ", " + xMax + ", " + yMin + ", " + yMax + ")";
  }

  /**
   * Writes the rectangle to a bit stream.
   *
   * @param stream the target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    int nBits = OutputBitStream.getSignedBitsLength(xMin);
    nBits   = Math.max(nBits, OutputBitStream.getSignedBitsLength(xMax));
    nBits   = Math.max(nBits, OutputBitStream.getSignedBitsLength(yMin));
    nBits   = Math.max(nBits, OutputBitStream.getSignedBitsLength(yMax));
    stream.writeUnsignedBits(nBits, 5);
    stream.writeSignedBits(xMin, nBits);
    stream.writeSignedBits(xMax, nBits);
    stream.writeSignedBits(yMin, nBits);
    stream.writeSignedBits(yMax, nBits);
    stream.align();
  }
}
