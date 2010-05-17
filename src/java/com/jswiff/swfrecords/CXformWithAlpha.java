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
 * This record defines a simple transform that can be applied to the color
 * space and the alpha channel of an object.
 */
public final class CXformWithAlpha implements Serializable {
  private int redMultTerm      = 256;
  private int greenMultTerm    = 256;
  private int blueMultTerm     = 256;
  private int alphaMultTerm    = 256;
  private int redAddTerm       = 0;
  private int greenAddTerm     = 0;
  private int blueAddTerm      = 0;
  private int alphaAddTerm     = 0;
  private boolean hasMultTerms;
  private boolean hasAddTerms;

  /**
   * Creates a new CXformWithAlpha instance. After creation, use setter methods
   * to set the values of the transform terms.
   */
  public CXformWithAlpha() {
    // nothing to do
  }

  /**
   * Reads a CXform instance from a bit stream.
   *
   * @param stream the source bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public CXformWithAlpha(InputBitStream stream) throws IOException {
    hasAddTerms    = stream.readBooleanBit();
    hasMultTerms   = stream.readBooleanBit();
    int nBits      = (int) stream.readUnsignedBits(4);
    if (hasMultTerms) {
      redMultTerm     = (int) stream.readSignedBits(nBits);
      greenMultTerm   = (int) stream.readSignedBits(nBits);
      blueMultTerm    = (int) stream.readSignedBits(nBits);
      alphaMultTerm   = (int) stream.readSignedBits(nBits);
    }
    if (hasAddTerms) {
      redAddTerm     = (int) stream.readSignedBits(nBits);
      greenAddTerm   = (int) stream.readSignedBits(nBits);
      blueAddTerm    = (int) stream.readSignedBits(nBits);
      alphaAddTerm   = (int) stream.readSignedBits(nBits);
    }
    stream.align();
  }

  /**
   * Sets the transform's additive terms. After addition, the result is
   * truncated at 255 if greater than 255 and at 0 if negative.
   *
   * @param redAddTerm red term
   * @param greenAddTerm green term
   * @param blueAddTerm blue term
   * @param alphaAddTerm alpha term
   */
  public void setAddTerms(
    int redAddTerm, int greenAddTerm, int blueAddTerm, int alphaAddTerm) {
    this.redAddTerm     = redAddTerm;
    this.greenAddTerm   = greenAddTerm;
    this.blueAddTerm    = blueAddTerm;
    this.alphaAddTerm   = alphaAddTerm;
    hasAddTerms         = true;
  }

  /**
   * Returns the additive term for the alpha component. Check with
   * <code>hasAddTerms()</code> first if additive terms have been specified.
   *
   * @return additive alpha term
   */
  public int getAlphaAddTerm() {
    return alphaAddTerm;
  }

  /**
   * Returns the multiplicative term for the alpha component. Check with
   * <code>hasMultTerms()</code> first if multiplicative terms have been
   * specified.
   *
   * @return multiplicative alpha term
   */
  public int getAlphaMultTerm() {
    return alphaMultTerm;
  }

  /**
   * Returns the additive term for the blue component. Check with
   * <code>hasAddTerms()</code> first if additive terms have been specified.
   *
   * @return additive blue term
   */
  public int getBlueAddTerm() {
    return blueAddTerm;
  }

  /**
   * Returns the multiplicative term for the blue component. Check with
   * <code>hasMultTerms()</code> first if multiplicative terms have been
   * specified.
   *
   * @return multiplicative blue term
   */
  public int getBlueMultTerm() {
    return blueMultTerm;
  }

  /**
   * Returns the additive term for the green component. Check with
   * <code>hasAddTerms()</code> first if additive terms have been specified.
   *
   * @return additive green term
   */
  public int getGreenAddTerm() {
    return greenAddTerm;
  }

  /**
   * Returns the multiplicative term for the green component. Check with
   * <code>hasMultTerms()</code> first if multiplicative terms have been
   * specified.
   *
   * @return multiplicative green term
   */
  public int getGreenMultTerm() {
    return greenMultTerm;
  }

  /**
   * Sets the transform's multiplicative terms. The terms are 8.8 fixed point
   * values (i.e. 256 corresponds to 1.0, after multiplication the result is
   * divided by 256).
   *
   * @param redMultTerm red term
   * @param greenMultTerm green term
   * @param blueMultTerm blue term
   * @param alphaMultTerm alpha term
   */
  public void setMultTerms(
    int redMultTerm, int greenMultTerm, int blueMultTerm, int alphaMultTerm) {
    this.redMultTerm     = redMultTerm;
    this.greenMultTerm   = greenMultTerm;
    this.blueMultTerm    = blueMultTerm;
    this.alphaMultTerm   = alphaMultTerm;
    hasMultTerms         = true;
  }

  /**
   * Returns the additive term for the red component. Check with
   * <code>hasAddTerms()</code> if additive terms have been specified.
   *
   * @return additive red term
   */
  public int getRedAddTerm() {
    return redAddTerm;
  }

  /**
   * Returns the multiplicative term for the red component. Check with
   * <code>hasMultTerms()</code> if multiplicative terms have been specified.
   *
   * @return multiplicative red term
   */
  public int getRedMultTerm() {
    return redMultTerm;
  }

  /**
   * Checks if the transform has additive terms.
   *
   * @return <code>true</code> if additive terms contained
   */
  public boolean hasAddTerms() {
    return hasAddTerms;
  }

  /**
   * Checks if the transform has multiplicative terms.
   *
   * @return <code>true</code> if multiplicative terms contained
   */
  public boolean hasMultTerms() {
    return hasMultTerms;
  }

  /**
   * Writes the transform to a bit stream.
   *
   * @param stream the target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeBooleanBit(hasAddTerms);
    stream.writeBooleanBit(hasMultTerms);
    int nBits = 0;
    if (hasAddTerms) {
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(redAddTerm));
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(greenAddTerm));
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(blueAddTerm));
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(alphaAddTerm));
    }
    if (hasMultTerms) {
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(redMultTerm));
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(greenMultTerm));
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(blueMultTerm));
      nBits   = Math.max(
          nBits, OutputBitStream.getSignedBitsLength(alphaMultTerm));
    }
    stream.writeUnsignedBits(nBits, 4);
    if (hasMultTerms) {
      stream.writeSignedBits(redMultTerm, nBits);
      stream.writeSignedBits(greenMultTerm, nBits);
      stream.writeSignedBits(blueMultTerm, nBits);
      stream.writeSignedBits(alphaMultTerm, nBits);
    }
    if (hasAddTerms) {
      stream.writeSignedBits(redAddTerm, nBits);
      stream.writeSignedBits(greenAddTerm, nBits);
      stream.writeSignedBits(blueAddTerm, nBits);
      stream.writeSignedBits(alphaAddTerm, nBits);
    }
    stream.align();
  }
}
