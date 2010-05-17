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
 * Represents a 24-bit RGB color.
 */
public final class RGB extends Color {
  /** Black */
  public static final RGB BLACK = new RGB(0, 0, 0);
  /** White */
  public static final RGB WHITE = new RGB(255, 255, 255);
  private short red;
  private short green;
  private short blue;

  /**
   * Creates a new RGB instance.
   *
   * @param red red value (between 0 and 255)
   * @param green green value (between 0 and 255)
   * @param blue blue value (between 0 and 255)
   */
  public RGB(short red, short green, short blue) {
    this.red     = red;
    this.green   = green;
    this.blue    = blue;
  }

  /**
   * Creates a new RGB instance. Convenience constructor which can be used to
   * avoid annoying type casts with literals like<br>
   * new RGB((short) 0, (short) 0, (short) 0);
   *
   * @param red red value (between 0 and 255)
   * @param green green value (between 0 and 255)
   * @param blue blue value (between 0 and 255)
   */
  public RGB(int red, int green, int blue) {
    this.red     = (short) red;
    this.green   = (short) green;
    this.blue    = (short) blue;
  }

  /**
   * Reads an instance from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public RGB(InputBitStream stream) throws IOException {
    red     = stream.readUI8();
    green   = stream.readUI8();
    blue    = stream.readUI8();
  }

  /**
   * Returns the blue value.
   *
   * @return blue value (between 0 and 255)
   */
  public short getBlue() {
    return blue;
  }

  /**
   * Returns the green value.
   *
   * @return green value (between 0 and 255)
   */
  public short getGreen() {
    return green;
  }

  /**
   * Returns the red value.
   *
   * @return red value (between 0 and 255)
   */
  public short getRed() {
    return red;
  }

  /**
   * Returns the string representation of the instance.
   *
   * @return string representation of color
   */
  public String toString() {
    return "RGB (" + red + "; " + green + "; " + blue + ")";
  }

  /**
   * Writes the instance to a bit stream.
   *
   * @param stream the target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(red);
    stream.writeUI8(green);
    stream.writeUI8(blue);
  }
}
