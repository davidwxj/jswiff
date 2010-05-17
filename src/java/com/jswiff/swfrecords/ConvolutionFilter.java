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
public final class ConvolutionFilter extends Filter {
  private int matrixRows;
  private float[] matrix;
  private RGBA color;
  private float divisor;
  private float bias;
  private boolean clamp;
  private boolean preserveAlpha;

  /**
   * Creates a new ConvolutionFilter instance.
   *
   * @param matrix TODO: Comments
   * @param matrixRows TODO: Comments
   */
  public ConvolutionFilter(float[] matrix, int matrixRows) {
    super(FilterType.CONVOLUTION);
    setMatrix(matrix, matrixRows);
    initDefaults();
  }
  
  public ConvolutionFilter(InputBitStream stream) throws IOException {
    super(FilterType.CONVOLUTION);
    int matrixColumns = stream.readUI8();
    matrixRows = stream.readUI8();
    divisor = stream.readFloat();
    bias = stream.readFloat();
    int matrixSize = matrixColumns * matrixRows;
    matrix = new float[matrixSize];
    for (int i=0; i<matrixSize; i++) {
      matrix[i] = stream.readFloat();
    }
    color = new RGBA(stream);
    stream.readUnsignedBits(6);
    clamp = stream.readBooleanBit();
    preserveAlpha = stream.readBooleanBit();
  }

  public void write(OutputBitStream stream) throws IOException {
    int matrixSize = matrix.length;
    int matrixColumns = matrixSize / matrixRows;
    stream.writeUI8((short) matrixColumns);
    stream.writeUI8((short) matrixRows);
    stream.writeFloat(divisor);
    stream.writeFloat(bias);
    for (int i=0; i<matrixSize; i++) {
      stream.writeFloat(matrix[i]);
    }
    color.write(stream);
    stream.writeUnsignedBits(0, 6);
    stream.writeBooleanBit(clamp);
    stream.writeBooleanBit(preserveAlpha);
  }
  
  /**
   * TODO: Comments
   *
   * @param bias TODO: Comments
   */
  public void setBias(float bias) {
    this.bias = bias;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float getBias() {
    return bias;
  }

  /**
   * TODO: Comments
   *
   * @param clamp TODO: Comments
   */
  public void setClamp(boolean clamp) {
    this.clamp = clamp;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean isClamp() {
    return clamp;
  }

  /**
   * TODO: Comments
   *
   * @param color TODO: Comments
   */
  public void setColor(RGBA color) {
    this.color = color;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public RGBA getColor() {
    return color;
  }

  /**
   * TODO: Comments
   *
   * @param divisor TODO: Comments
   */
  public void setDivisor(float divisor) {
    this.divisor = divisor;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float getDivisor() {
    return divisor;
  }

  /**
   * TODO: Comments
   *
   * @param matrix TODO: Comments
   * @param matrixRows TODO: Comments
   *
   * @throws IllegalArgumentException TODO: Comments
   */
  public void setMatrix(float[] matrix, int matrixRows) {
    if ((matrix.length % matrixRows) != 0) {
      throw new IllegalArgumentException(
        "matrix array length must be a multiple of the matrix width!");
    }
    this.matrix       = matrix;
    this.matrixRows   = matrixRows;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public float[] getMatrix() {
    return matrix;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public int getMatrixRows() {
    return matrixRows;
  }

  /**
   * TODO: Comments
   *
   * @param preserveAlpha TODO: Comments
   */
  public void setPreserveAlpha(boolean preserveAlpha) {
    this.preserveAlpha = preserveAlpha;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean isPreserveAlpha() {
    return preserveAlpha;
  }

  private void initDefaults() {
    color           = RGBA.BLACK;
    divisor         = 1;
    preserveAlpha   = true;
    clamp           = true;
  }
}
