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

import com.jswiff.constants.TagConstants.InterpolationMethod;
import com.jswiff.constants.TagConstants.SpreadMethod;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;


/**
 * Gradients are used for interpolation between at least two colors defined by
 * control points. This structure contains the control points of the gradient
 * (see <code>GradRecord</code> for more details on control points).
 *
 * @see GradRecord
 */
public class Gradient implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private GradRecord[] gradientRecords;
  private SpreadMethod spreadMethod;
  private InterpolationMethod interpolationMethod;

  /**
   * Creates a new Gradient instance. Supply at least two gradient control
   * points as gradient record array.
   *
   * @param gradientRecords gradient control points
   */
  public Gradient(GradRecord[] gradientRecords) {
    this.gradientRecords = gradientRecords;
  }

  Gradient(InputBitStream stream, boolean hasAlpha) throws IOException {
    spreadMethod          = SpreadMethod.lookup((short) stream.readUnsignedBits(2));
    interpolationMethod   = InterpolationMethod.lookup((short) stream.readUnsignedBits(2));
    short count           = (short) stream.readUnsignedBits(4);
    gradientRecords       = new GradRecord[count];
    for (int i = 0; i < count; i++) {
      gradientRecords[i] = new GradRecord(stream, hasAlpha);
    }
  }

  /**
   * TODO: Comments
   *
   * @param gradientRecords TODO: Comments
   */
  public void setGradientRecords(GradRecord[] gradientRecords) {
    this.gradientRecords = gradientRecords;
  }

  /**
   * Returns the gradient's control points.
   *
   * @return gradient control points (as <code>GradRecord</code> array)
   */
  public GradRecord[] getGradientRecords() {
    return gradientRecords;
  }

  /**
   * TODO: Comments
   *
   * @param interpolationMethod TODO: Comments
   */
  public void setInterpolationMethod(InterpolationMethod interpolationMethod) {
    if (interpolationMethod == null) throw new IllegalArgumentException(
        "interpolationMethod can not be null");
    this.interpolationMethod = interpolationMethod;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public InterpolationMethod getInterpolationMethod() {
    return interpolationMethod;
  }

  /**
   * TODO: Comments
   *
   * @param spreadMethod TODO: Comments
   */
  public void setSpreadMethod(SpreadMethod spreadMethod) {
    if (spreadMethod == null) throw new IllegalArgumentException(
        "spreadMethod can not be null");
    this.spreadMethod = spreadMethod;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public SpreadMethod getSpreadMethod() {
    return spreadMethod;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUnsignedBits(spreadMethod.getCode(), 2);
    stream.writeUnsignedBits(interpolationMethod.getCode(), 2);
    int count = gradientRecords.length;
    stream.writeUnsignedBits(count, 4);
    for (int i = 0; i < count; i++) {
      gradientRecords[i].write(stream);
    }
  }
}
