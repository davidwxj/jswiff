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


/**
 * This class is used for defining a line between two points. The current
 * drawing position is considered to be the first point. The second point is
 * specified as coordinates relative to the current drawing position.
 */
public final class StraightEdgeRecord extends EdgeRecord {
  private int deltaX;
  private int deltaY;

  /**
   * Creates a new StraightEdgeRecord instance. Specify the point the line is
   * supposed to be drawn to by supplying its coordinates relative to the
   * current drawing position.
   *
   * @param deltaX x coordinate relative to current position (in twips)
   * @param deltaY y coordinate relative to current position (in twips)
   */
  public StraightEdgeRecord(int deltaX, int deltaY) {
    this.deltaX   = deltaX;
    this.deltaY   = deltaY;
  }

  /**
   * Creates a new StraightEdgeRecord instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public StraightEdgeRecord(InputBitStream stream) throws IOException {
    byte numBits            = (byte) stream.readUnsignedBits(4);
    boolean generalLineFlag = stream.readBooleanBit();
    if (generalLineFlag) {
      // read deltas
      deltaX   = (int) stream.readSignedBits(numBits + 2);
      deltaY   = (int) stream.readSignedBits(numBits + 2);
    } else {
      boolean vertLineFlag = stream.readBooleanBit();
      if (vertLineFlag) {
        // vertical line; read deltaY
        deltaY = (int) stream.readSignedBits(numBits + 2);
      } else {
        // horizontal line; read deltaX
        deltaX = (int) stream.readSignedBits(numBits + 2);
      }
    }
  }

  /**
   * Returns the x coordinate of the point the line is supposed to be drawn to,
   * relative to the current drawing position.
   *
   * @return x delta in twips (1/20 px)
   */
  public int getDeltaX() {
    return deltaX;
  }

  /**
   * Returns the y coordinate of the point the line is supposed to be drawn to,
   * relative to the current drawing position.
   *
   * @return y delta in twips (1/20 px)
   */
  public int getDeltaY() {
    return deltaY;
  }

  /**
   * Writes this instance to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUnsignedBits(1, 1); // edge record
    stream.writeUnsignedBits(1, 1); // straight edge
    int numBits = 2;
    numBits   = Math.max(numBits, OutputBitStream.getSignedBitsLength(deltaX));
    numBits   = Math.max(numBits, OutputBitStream.getSignedBitsLength(deltaY));
    stream.writeUnsignedBits(numBits - 2, 4);
    boolean generalLineFlag = (deltaX != 0) && (deltaY != 0);
    stream.writeBooleanBit(generalLineFlag);
    if (generalLineFlag) {
      stream.writeSignedBits(deltaX, numBits);
      stream.writeSignedBits(deltaY, numBits);
    } else {
      boolean vertLineFlag = (deltaX == 0);
      stream.writeBooleanBit(vertLineFlag);
      if (vertLineFlag) {
        stream.writeSignedBits(deltaY, numBits);
      } else {
        stream.writeSignedBits(deltaX, numBits);
      }
    }
  }
}
