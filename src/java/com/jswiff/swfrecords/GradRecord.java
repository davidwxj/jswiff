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
 * This class implements a gradient control point. A control point is defined
 * by a ratio (i.e. the position of the control point in the gradient) and a
 * color value. Depending on whether the tag this record is used in supports
 * transparency or not, the color is either an <code>RGBA</code> or an
 * <code>RGB</code> value (e.g. <code>DefineShape3</code> supports
 * transparency).
 *
 * @see Gradient
 * @see com.jswiff.swfrecords.tags.DefineShape3
 */
public final class GradRecord implements Serializable {
  private short ratio;
  private Color color;

  /**
   * <p>
   * Creates a new GradRecord (i.e. a gradient control point) instance. You
   * have to specify the ratio and the color value of the control point.
   * </p>
   * 
   * <p>
   * The ratio is a value between 0 and 255. 0 maps to the left edge of the
   * gradient square for a linear gradient, 255 to the right edge. For radial
   * gradients, 0 maps to the center of the square and 255 to the largest
   * circle fitting inside the square.
   * </p>
   * 
   * <p>
   * The color is either an <code>RGB</code> or an <code>RGBA</code> instance,
   * depending on whether the tag this record is used in supports transparency
   * or not (e.g. <code>DefineShape3</code> does).
   * </p>
   *
   * @param ratio control point ratio (from [0; 255])
   * @param color the color value of the gradient control point
   *
   * @see com.jswiff.swfrecords.tags.DefineShape3
   */
  public GradRecord(short ratio, Color color) {
    this.ratio   = ratio;
    this.color   = color;
  }

  GradRecord(InputBitStream stream, boolean hasAlpha) throws IOException {
    ratio = stream.readUI8();
    if (hasAlpha) {
      color = new RGBA(stream);
    } else {
      color = new RGB(stream);
    }
  }

  /**
   * Returns the color value of the gradient control point.
   *
   * @return color value
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns the ratio of the gradient control point (a value between 0 and
   * 255).
   *
   * @return control point ratio
   */
  public short getRatio() {
    return ratio;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(ratio);
    color.write(stream);
  }
}
