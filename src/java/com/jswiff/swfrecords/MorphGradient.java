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

  private static final long serialVersionUID = 1L;
  
  private MorphGradRecord[] gradientRecords;
  private SpreadMethod spreadMethod;
  private InterpolationMethod interpolationMethod;

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
    spreadMethod          = SpreadMethod.lookup((short) stream.readUnsignedBits(2));
    interpolationMethod   = InterpolationMethod.lookup((short) stream.readUnsignedBits(2));
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
    stream.writeUnsignedBits(spreadMethod.getCode(), 2);
    stream.writeUnsignedBits(interpolationMethod.getCode(), 2);
    int count = gradientRecords.length;
    stream.writeUnsignedBits(count, 4);
    for (int i = 0; i < count; i++) {
      gradientRecords[i].write(stream);
    }
  }

  public InterpolationMethod getInterpolationMethod() {
    return interpolationMethod;
  }

  public void setInterpolationMethod(InterpolationMethod interpolationMethod) {
    this.interpolationMethod = interpolationMethod;
  }

  public SpreadMethod getSpreadMethod() {
    return spreadMethod;
  }

  public void setSpreadMethod(SpreadMethod spreadMethod) {
    this.spreadMethod = spreadMethod;
  }

  public void setGradientRecords(MorphGradRecord[] gradientRecords) {
    this.gradientRecords = gradientRecords;
  }
}
