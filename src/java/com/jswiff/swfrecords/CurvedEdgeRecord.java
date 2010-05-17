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
 * This class is used for defining quadratic Bezier curves. A Bezier curve is
 * defined by three points: two on-curve anchor points and one off-curve
 * control point. The current drawing position is considered to be the first
 * anchor point, i.e. for a curve definition it is sufficient to specify one
 * control and one (single) anchor point.
 */
public final class CurvedEdgeRecord extends EdgeRecord {
  private int controlDeltaX;
  private int controlDeltaY;
  private int anchorDeltaX;
  private int anchorDeltaY;

  /**
   * Creates a new CurvedEdgeRecord instance. Supply the control point
   * (relative to the current drawing position) and the anchor point (relative
   * to the specified control point).
   *
   * @param controlDeltaX x coordinate of control point (relative to current
   *        position, in twips)
   * @param controlDeltaY y coordinate of control point (relative to current
   *        position, in twips)
   * @param anchorDeltaX x coordinate of anchor point (relative to control
   *        point, in twips)
   * @param anchorDeltaY y coordinate of anchor point (relative to control
   *        point, in twips)
   */
  public CurvedEdgeRecord(
    int controlDeltaX, int controlDeltaY, int anchorDeltaX, int anchorDeltaY) {
    this.controlDeltaX   = controlDeltaX;
    this.controlDeltaY   = controlDeltaY;
    this.anchorDeltaX    = anchorDeltaX;
    this.anchorDeltaY    = anchorDeltaY;
  }

  /**
   * Creates a new CurvedEdgeRecord instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public CurvedEdgeRecord(InputBitStream stream) throws IOException {
    int numBits = (int) stream.readUnsignedBits(4) + 2;
    controlDeltaX   = (int) stream.readSignedBits(numBits);
    controlDeltaY   = (int) stream.readSignedBits(numBits);
    anchorDeltaX    = (int) stream.readSignedBits(numBits);
    anchorDeltaY    = (int) stream.readSignedBits(numBits);
  }

  /**
   * Returns the x coordinate of the anchor point, relative to the control
   * point.
   *
   * @return x delta of anchor point in twips (1/20 px)
   */
  public int getAnchorDeltaX() {
    return anchorDeltaX;
  }

  /**
   * Returns the y coordinate of the anchor point, relative to the control
   * point.
   *
   * @return y delta of anchor point in twips (1/20 px)
   */
  public int getAnchorDeltaY() {
    return anchorDeltaY;
  }

  /**
   * Returns the x coordinate of the control point, relative to the current
   * drawing position.
   *
   * @return x delta of control point in twips (1/20 px)
   */
  public int getControlDeltaX() {
    return controlDeltaX;
  }

  /**
   * Returns the y coordinate of the control point, relative to the current
   * drawing position.
   *
   * @return y delta of control point in twips (1/20 px)
   */
  public int getControlDeltaY() {
    return controlDeltaY;
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
    stream.writeUnsignedBits(0, 1); // curved edge
    int numBits = 2;
    numBits   = Math.max(
        numBits, OutputBitStream.getSignedBitsLength(controlDeltaX));
    numBits   = Math.max(
        numBits, OutputBitStream.getSignedBitsLength(controlDeltaY));
    numBits   = Math.max(
        numBits, OutputBitStream.getSignedBitsLength(anchorDeltaX));
    numBits   = Math.max(
        numBits, OutputBitStream.getSignedBitsLength(anchorDeltaY));
    stream.writeUnsignedBits(numBits - 2, 4);
    stream.writeSignedBits(controlDeltaX, numBits);
    stream.writeSignedBits(controlDeltaY, numBits);
    stream.writeSignedBits(anchorDeltaX, numBits);
    stream.writeSignedBits(anchorDeltaY, numBits);
  }
}
