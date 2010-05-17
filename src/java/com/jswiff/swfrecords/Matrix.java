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
 * This class is used to represent a standard 2D transform matrix (used for
 * affine transforms).
 */
public final class Matrix implements Serializable {
  private double scaleX         = 1.0;
  private double scaleY         = 1.0;
  private double rotateSkew0    = 0.0;
  private double rotateSkew1    = 0.0;
  private int translateX        = 0;
  private int translateY        = 0;
  private boolean hasScale;
  private boolean hasRotateSkew;

  /**
   * Creates a new Matrix instance. Specify the translate values. If the matrix
   * also has scale or rotate/skew values, use the appropriate setters for
   * setting these values.
   *
   * @param translateX x translate value in twips
   * @param translateY y translate value in twips
   */
  public Matrix(int translateX, int translateY) {
    this.translateX   = translateX;
    this.translateY   = translateY;
  }

  /**
   * Reads a Matrix instance from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public Matrix(InputBitStream stream) throws IOException {
    hasScale = stream.readBooleanBit();
    if (hasScale) {
      int nScaleBits = (int) stream.readUnsignedBits(5);
      scaleX   = stream.readFPBits(nScaleBits);
      scaleY   = stream.readFPBits(nScaleBits);
    }
    hasRotateSkew = stream.readBooleanBit();
    if (hasRotateSkew) {
      int nRotateBits = (int) stream.readUnsignedBits(5);
      rotateSkew0   = stream.readFPBits(nRotateBits);
      rotateSkew1   = stream.readFPBits(nRotateBits);
    }
    int nTranslateBits = (int) stream.readUnsignedBits(5);
    translateX   = (int) stream.readSignedBits(nTranslateBits);
    translateY   = (int) stream.readSignedBits(nTranslateBits);
    stream.align();
  }

  /**
   * Sets the rotate and skew values
   *
   * @param rotateSkew0 rotate/skew value 0
   * @param rotateSkew1 rotate/skew value 1
   */
  public void setRotateSkew(double rotateSkew0, double rotateSkew1) {
    this.rotateSkew0   = rotateSkew0;
    this.rotateSkew1   = rotateSkew1;
    hasRotateSkew      = true;
  }

  /**
   * Returns the rotate/skew 0 value. Check with <code>hasRotateSkew()</code>
   * first if value is set.
   *
   * @return rotate/skew 0 value
   */
  public double getRotateSkew0() {
    return rotateSkew0;
  }

  /**
   * Returns the rotate/skew 1 value. Check with <code>hasRotateSkew()</code>
   * first if value is set.
   *
   * @return rotate/skew 1 value
   */
  public double getRotateSkew1() {
    return rotateSkew1;
  }

  /**
   * Sets the scale values.
   *
   * @param scaleX x scale value
   * @param scaleY y scale value
   */
  public void setScale(double scaleX, double scaleY) {
    this.scaleX   = scaleX;
    this.scaleY   = scaleY;
    hasScale      = true;
  }

  /**
   * Returns the x scale value. Check with <code>hasScale()</code> first if
   * value is set.
   *
   * @return x scale
   */
  public double getScaleX() {
    return scaleX;
  }

  /**
   * Returns the y scale value. Check with <code>hasScale()</code> first if
   * value is set.
   *
   * @return y scale
   */
  public double getScaleY() {
    return scaleY;
  }

  /**
   * Returns the x translation value in twips (1/20 px).
   *
   * @return x translate value in twips
   */
  public int getTranslateX() {
    return translateX;
  }

  /**
   * Returns the y translation value in twips (1/20 px).
   *
   * @return y translate value in twips
   */
  public int getTranslateY() {
    return translateY;
  }

  /**
   * Checks if the rotate/skew values have been set.
   *
   * @return <code>true</code> if rotate/skew values set, else
   *         <code>false</code>
   */
  public boolean hasRotateSkew() {
    return hasRotateSkew;
  }

  /**
   * Checks if the scale values have been set.
   *
   * @return <code>true</code> if scale values set, else <code>false</code>
   */
  public boolean hasScale() {
    return hasScale;
  }

  /**
   * Returns the string representation of the matrix.
   *
   * @return string representation of matrix
   */
  public String toString() {
    return "Matrix (" + "scaleX=" + scaleX + " scaleY=" + scaleY +
    " rotateSkew0=" + rotateSkew0 + " rotateSkew1=" + rotateSkew1 +
    " translateX=" + translateX + " translateY=" + translateY + ")";
  }

  /**
   * Writes the matrix to a bit stream.
   *
   * @param stream the target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeBooleanBit(hasScale);
    if (hasScale) {
      int nScaleBits = OutputBitStream.getFPBitsLength(scaleX);
      nScaleBits = Math.max(
          nScaleBits, OutputBitStream.getFPBitsLength(scaleY));
      stream.writeUnsignedBits(nScaleBits, 5);
      stream.writeFPBits(scaleX, nScaleBits);
      stream.writeFPBits(scaleY, nScaleBits);
    }
    stream.writeBooleanBit(hasRotateSkew);
    if (hasRotateSkew) {
      int nRotateBits = OutputBitStream.getFPBitsLength(rotateSkew0);
      nRotateBits = Math.max(
          nRotateBits, OutputBitStream.getFPBitsLength(rotateSkew1));
      stream.writeUnsignedBits(nRotateBits, 5);
      stream.writeFPBits(rotateSkew0, nRotateBits);
      stream.writeFPBits(rotateSkew1, nRotateBits);
    }
    int nTranslateBits = OutputBitStream.getSignedBitsLength(translateX);
    nTranslateBits = Math.max(
        nTranslateBits, OutputBitStream.getSignedBitsLength(translateY));
    stream.writeUnsignedBits(nTranslateBits, 5);
    stream.writeSignedBits(translateX, nTranslateBits);
    stream.writeSignedBits(translateY, nTranslateBits);
    stream.align();
  }
}
