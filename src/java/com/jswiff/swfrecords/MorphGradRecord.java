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
 * This class is used for defining a pair of control points for morph gradients
 * (see <code>MorphGradient</code> for details). Start and end gradients in a
 * morph must have the same number of control points, therefore these are
 * defined pairwise. A control point definition consists of a ratio (i.e. the
 * position of the control point in the gradient) and a color value.
 *
 * @see MorphGradient
 * @see MorphFillStyle
 * @see com.jswiff.swfrecords.tags.DefineMorphShape
 */
public final class MorphGradRecord implements Serializable {
  private short startRatio;
  private RGBA startColor;
  private short endRatio;
  private RGBA endColor;

  /**
   * <p>
   * Creates a new MorphGradRecord instance. Specify ratio and (RGBA) color for
   * each control point.
   * </p>
   * 
   * <p>
   * The ratio is a value between 0 and 255. 0 maps to the left edge of the
   * gradient square for a linear gradient, 255 to the right edge. For radial
   * gradients, 0 maps to the center of the square and 255 to the largest
   * circle fitting inside the square.
   * </p>
   *
   * @param startRatio ratio of control point for start gradient
   * @param startColor color of control point for start gradient
   * @param endRatio ratio of control point for end gradient
   * @param endColor color of control point for end gradient
   */
  public MorphGradRecord(
    short startRatio, RGBA startColor, short endRatio, RGBA endColor) {
    this.startRatio   = startRatio;
    this.startColor   = startColor;
    this.endRatio     = endRatio;
    this.endColor     = endColor;
  }

  MorphGradRecord(InputBitStream stream) throws IOException {
    startRatio   = stream.readUI8();
    startColor   = new RGBA(stream);
    endRatio     = stream.readUI8();
    endColor     = new RGBA(stream);
  }

  /**
   * Returns the color of the control point for the gradient used for filling
   * the morph's end shapes.
   *
   * @return end control point color
   */
  public RGBA getEndColor() {
    return endColor;
  }

  /**
   * Returns the ratio of the control point for the gradient used for filling
   * the morph's end shapes.
   *
   * @return end control point ratio
   */
  public short getEndRatio() {
    return endRatio;
  }

  /**
   * Returns the color of the control point for the gradient used for filling
   * the morph's start shapes.
   *
   * @return start control point color
   */
  public RGBA getStartColor() {
    return startColor;
  }

  /**
   * Returns the ratio of the control point for the gradient used for filling
   * the morph's start shapes.
   *
   * @return start control point ratio
   */
  public short getStartRatio() {
    return startRatio;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(startRatio);
    startColor.write(stream);
    stream.writeUI8(endRatio);
    endColor.write(stream);
  }
}
