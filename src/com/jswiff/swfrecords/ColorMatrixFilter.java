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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * TODO: Comments
 */
public final class ColorMatrixFilter extends Filter {
  private float[] matrix;

  /**
   * Creates a new ColorMatrixFilter instance.
   *
   * @param matrix TODO: Comments
   *
   * @throws IllegalArgumentException if matrix array length != 20
   */
  public ColorMatrixFilter(float[] matrix) {
    if (matrix.length != 20) {
      throw new IllegalArgumentException("matrix array length must be 20!");
    }
    this.matrix = matrix;
  }

  /**
   * Creates a new ColorMatrixFilter instance.
   *
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public ColorMatrixFilter(InputBitStream stream) throws IOException {
    matrix = new float[20];
    for (int i = 0; i < 20; i++) {
      matrix[i] = stream.readFloat();
    }
  }

  /**
   * TODO: Comments
   *
   * @param matrix TODO: Comments
   */
  public void setMatrix(float[] matrix) {
    this.matrix = matrix;
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
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public void write(OutputBitStream stream) throws IOException {
    for (int i = 0; i < matrix.length; i++) {
      stream.writeFloat(matrix[i]);
    }
  }
}
