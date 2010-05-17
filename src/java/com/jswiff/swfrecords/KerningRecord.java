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
 * A kerning record is used to adjust the distance between two glyphs.
 */
public final class KerningRecord implements Serializable {
  private char left;
  private char right;
  private short adjustment;

  /**
   * Creates a new KerningRecord instance. Specify a character pair and an
   * adjustment to the advance value (i.e. the distance between glyph
   * reference points) of the left character.
   *
   * @param left left character
   * @param right right character
   * @param adjustment adjustment relative to advance value of left character
   *        (in EM square coords)
   */
  public KerningRecord(char left, char right, short adjustment) {
    this.left         = left;
    this.right        = right;
    this.adjustment   = adjustment;
  }

  /**
   * Creates a new KerningRecord instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   * @param wideCodes if <code>true</code>, 16 bits are used for character code
   *        representation (instead of 8)
   *
   * @throws IOException if an I/O error occured
   */
  public KerningRecord(InputBitStream stream, boolean wideCodes)
    throws IOException {
    if (wideCodes) {
      left    = (char) stream.readUI16();
      right   = (char) stream.readUI16();
    } else {
      left    = (char) stream.readUI8();
      right   = (char) stream.readUI8();
    }
    adjustment = stream.readSI16();
  }

  /**
   * Returns the adjustment to the advance value (i.e. the distance between
   * glyph reference points) of the character on the left.
   *
   * @return adjustment relative to advance value of left character (in EM
   *         square coords)
   */
  public short getAdjustment() {
    return adjustment;
  }

  /**
   * Returns the character on the left.
   *
   * @return left character
   */
  public char getLeft() {
    return left;
  }

  /**
   * Returns the character on the right.
   *
   * @return right character
   */
  public char getRight() {
    return right;
  }

  /**
   * Writes the instance to a bit stream.
   *
   * @param stream target bit stream
   * @param wideCodes if <code>true</code>, two bytes are used for
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream, boolean wideCodes)
    throws IOException {
    if (wideCodes) {
      stream.writeUI16(left);
      stream.writeUI16(right);
    } else {
      stream.writeUI8((short) left);
      stream.writeUI8((short) right);
    }
    stream.writeSI16(adjustment);
  }
}
