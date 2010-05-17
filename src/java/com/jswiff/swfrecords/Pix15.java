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
 * This class is used for the representation of 15-bit pixel data.
 */
public final class Pix15 extends BitmapPixelData {
  private byte red;
  private byte green;
  private byte blue;

  /**
   * Creates a new Pix15 instance. Specify red, green and blue values.
   *
   * @param red red value (between 0 and 31)
   * @param green green value (between 0 and 31)
   * @param blue blue value (between 0 and 31)
   */
  public Pix15(byte red, byte green, byte blue) {
    this.red     = red;
    this.green   = green;
    this.blue    = blue;
  }

  Pix15(InputBitStream stream) throws IOException {
    stream.readUnsignedBits(1); // ignore MSB
    red     = (byte) stream.readUnsignedBits(5);
    green   = (byte) stream.readUnsignedBits(5);
    blue    = (byte) stream.readUnsignedBits(5);
  }

  /**
   * Returns the blue value.
   *
   * @return blue value (between 0 and 31)
   */
  public byte getBlue() {
    return blue;
  }

  /**
   * Returns the green value.
   *
   * @return green value (between 0 and 31)
   */
  public byte getGreen() {
    return green;
  }

  /**
   * Returns the red value.
   *
   * @return red value (between 0 and 31)
   */
  public byte getRed() {
    return red;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUnsignedBits(0, 1); // reserved bit
    stream.writeUnsignedBits(red, 5);
    stream.writeUnsignedBits(green, 5);
    stream.writeUnsignedBits(blue, 5);
  }
}
