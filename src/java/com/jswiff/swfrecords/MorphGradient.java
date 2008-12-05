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

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * This class is used for defining gradients used in morph sequences (within
 * <code>MorphFillStyle</code> instances).  Gradients are used for
 * interpolation between at least two colors defined by control points. In a
 * morph sequence, different gradients can be used for filling start and end
 * shapes. However, these gradients must have the same number of control
 * points, therefore these are defined pairwise in
 * <code>MorphGradRecord</code> instances.
 * </p>
 *
 * @see Gradient
 * @see MorphGradRecord
 * @see MorphFillStyle
 */
public class MorphGradient implements Serializable {
  /** TODO: Comments */
  public static final byte SPREAD_PAD               = 0;
  /** TODO: Comments */
  public static final byte SPREAD_REFLECT           = 1;
  /** TODO: Comments */
  public static final byte SPREAD_REPEAT            = 2;
  /** TODO: Comments */
  public static final byte INTERPOLATION_RGB        = 0;
  /** TODO: Comments */
  public static final byte INTERPOLATION_LINEAR_RGB = 1;
  private MorphGradRecord[] gradientRecords;
  private byte spreadMethod;
  private byte interpolationMethod;

  /**
   * Creates a new MorphGradient instance. Specify an array of at least two
   * morph gradient records containing pairwise definitions of gradient
   * control points for filling a morph's start and end shapes.
   *
   * @param gradientRecords morph gradient records
   */
  public MorphGradient(MorphGradRecord[] gradientRecords) {
    this.gradientRecords = gradientRecords;
  }

  MorphGradient(InputBitStream stream) throws IOException {
    spreadMethod          = (byte) stream.readUnsignedBits(2);
    interpolationMethod   = (byte) stream.readUnsignedBits(2);
    short count           = (short) stream.readUnsignedBits(4);
    gradientRecords = new MorphGradRecord[count];
    for (int i = 0; i < count; i++) {
      gradientRecords[i] = new MorphGradRecord(stream);
    }
  }

  /**
   * Returns the morph gradient records containing pairwise definitions of
   * control points for gradients applied to the start and end shapes of a
   * morph sequence.
   *
   * @return morph gradient records
   */
  public MorphGradRecord[] getGradientRecords() {
    return gradientRecords;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUnsignedBits(spreadMethod, 2);
    stream.writeUnsignedBits(interpolationMethod, 2);
    int count = gradientRecords.length;
    stream.writeUnsignedBits(count, 4);
    for (int i = 0; i < count; i++) {
      gradientRecords[i].write(stream);
    }
  }

  public byte getInterpolationMethod() {
    return interpolationMethod;
  }

  public void setInterpolationMethod(byte interpolationMethod) {
    this.interpolationMethod = interpolationMethod;
  }

  public byte getSpreadMethod() {
    return spreadMethod;
  }

  public void setSpreadMethod(byte spreadMethod) {
    this.spreadMethod = spreadMethod;
  }

  public void setGradientRecords(MorphGradRecord[] gradientRecords) {
    this.gradientRecords = gradientRecords;
  }
}