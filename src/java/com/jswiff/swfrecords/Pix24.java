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
