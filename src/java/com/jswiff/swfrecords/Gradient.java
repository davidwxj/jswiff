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
