/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
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
