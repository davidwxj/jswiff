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

import com.jswiff.constants.TagConstants;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;


/**
 * This class is used to define fill styles used in a morph sequence (as array
 * within a <code>DefineMorphShape</code> tag. Three basic types of shape
 * fills are available:
 * 
 * <ul>
 * <li>
 * solid fill (with / without transparency)
 * </li>
 * <li>
 * gradient fill (linear / radial)
 * </li>
 * <li>
 * bitmap fill (clipped / tiled, smoothed / non-smoothed)
 * </li>
 * </ul>
 * 
 *
 * @see MorphFillStyles
 * @see com.jswiff.swfrecords.tags.DefineMorphShape
 * @see FillStyle
 */
public final class MorphFillStyle implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private TagConstants.FillType type;
  private RGBA startColor;
  private RGBA endColor;
  private Matrix startGradientMatrix;
  private Matrix endGradientMatrix;
  private MorphGradient gradient;
  private int bitmapId;
  private Matrix startBitmapMatrix;
  private Matrix endBitmapMatrix;

  /**
   * Creates a new solid morph fill style. Specify fill colors with
   * transparency (RGBA) for start and end state.
   *
   * @param startColor start color
   * @param endColor end color
   */
  public MorphFillStyle(RGBA startColor, RGBA endColor) {
    this.startColor   = startColor;
    this.endColor     = endColor;
    type              = TagConstants.FillType.SOLID;
  }

  /**
   * <p>
   * Creates a new gradient morph fill style.
   * </p>
   * 
   * <p>
   * Shapes can be filled with different gradients in the morph's start and end
   * state. A gradient contains several control points; the fill color is
   * interpolated between these point's colors. Start and end gradients must
   * have the same number of control points. Control points of start and end
   * gradients as well as their colors are defined in a
   * <code>MorphGradient</code> instance.
   * </p>
   * 
   * <p>
   * Gradients are defined in the <i>gradient square</i>: (-16384, -16384,
   * 16384, 16384). The gradient matrix is used to map the gradient from the
   * gradient square to the display surface. Supply gradient matrices for
   * start and end state.
   * </p>
   * 
   * <p>
   * Linear and circular gradients are supported. Use either
   * <code>LINEAR_GRADIENT</code>, <code>RADIAL_GRADIENT</code>, or 
   * FOCAL_RADIAL_GRADIENT as gradient type.
   * </p>
   *
   * @param gradient a morph gradient
   * @param startGradientMatrix start gradient matrix
   * @param endGradientMatrix end gradient matrix
   * @param type gradient type
   *
   * @throws IllegalArgumentException if specified gradient type is not
   *         supported
   */
  public MorphFillStyle(MorphGradient gradient, Matrix startGradientMatrix, 
      Matrix endGradientMatrix, TagConstants.FillType type) {
    if (!TagConstants.FillTypeGroup.GRADIENT.equals(type.getGroup())) {
      throw new IllegalArgumentException("Illegal gradient type!");
    }
    this.gradient              = gradient;
    this.startGradientMatrix   = startGradientMatrix;
    this.endGradientMatrix     = endGradientMatrix;
    this.type                  = type;
  }

  /**
   * Creates a new bitmap morph fill style. You have to specify the character
   * ID of a previously defined bitmap, two transform matrices used for
   * mapping the bitmap to the filled (start and end) shapes, and the bitmap
   * type (one of the constants <code>TILED_BITMAP</code>,
   * <code>CLIPPED_BITMAP</code>,
   * <code>NONSMOOTHED_TILED_BITMAP</code> or
   * <code>NONSMOOTHED_CLIPPED_BITMAP</code>).
   *
   * @param bitmapId character ID of the bitmap
   * @param startBitmapMatrix transform matrix for start state
   * @param endBitmapMatrix transform matrix for end state
   * @param type bitmap type
   *
   * @throws IllegalArgumentException if an illegal bitmap type has been
   *         specified
   */
  public MorphFillStyle(int bitmapId, Matrix startBitmapMatrix, 
      Matrix endBitmapMatrix, TagConstants.FillType type) {
    if (!TagConstants.FillTypeGroup.BITMAP.equals(type.getGroup())) {
      throw new IllegalArgumentException("Illegal bitmap type");
    }
    this.bitmapId            = bitmapId;
    this.startBitmapMatrix   = startBitmapMatrix;
    this.endBitmapMatrix     = endBitmapMatrix;
    this.type                = type;
  }

  MorphFillStyle(InputBitStream stream) throws IOException {
    short code = stream.readUI8();
    type = TagConstants.FillType.lookup(code);
    switch (type.getGroup()) {
      case SOLID:
        startColor = new RGBA(stream);
        endColor = new RGBA(stream);
        break;
      case GRADIENT:
        startGradientMatrix = new Matrix(stream);
        endGradientMatrix = new Matrix(stream);
        gradient = type.equals(TagConstants.FillType.FOCAL_RADIAL_GRADIENT) ? 
            new FocalMorphGradient(stream) : new MorphGradient(stream);
        break;
      case BITMAP:
        bitmapId = stream.readUI16();
        startBitmapMatrix = new Matrix(stream);
        endBitmapMatrix = new Matrix(stream);
        break;
      default:
        throw new AssertionError("Unhandled fill type : " + type);
    }
  }

  /**
   * Returns the character ID of the bitmap used for filling. Supported only
   * for bitmap fills.
   *
   * @return bitmap character ID
   */
  public int getBitmapId() {
    return bitmapId;
  }

  /**
   * Returns the bitmap matrix used to map the bitmap to the filled shape in
   * the end state of the morph sequence. Supported only for bitmap fills.
   *
   * @return end bitmap matrix
   */
  public Matrix getEndBitmapMatrix() {
    return endBitmapMatrix;
  }

  /**
   * Returns the fill color of the shape in the end state of the morph
   * sequence. Supported only for solid fills.
   *
   * @return end color
   */
  public RGBA getEndColor() {
    return endColor;
  }

  /**
   * Returns the transform matrix used to map the gradient from the gradient
   * square to the display surface in the end state of the morph sequence.
   * Supported only for gradient fills.
   *
   * @return end gradient matrix
   */
  public Matrix getEndGradientMatrix() {
    return endGradientMatrix;
  }

  /**
   * Returns the morph gradient used for filling. Contains gradient control
   * points as well as their colors, both for start and end morph state.
   * Supported only for gradient fills.
   *
   * @return morph gradient
   */
  public MorphGradient getGradient() {
    return gradient;
  }

  /**
   * Returns the bitmap matrix used to map the bitmap to the filled shape in
   * the start state of the morph sequence. Supported only for bitmap fills.
   *
   * @return start bitmap matrix
   */
  public Matrix getStartBitmapMatrix() {
    return startBitmapMatrix;
  }

  /**
   * Returns the fill color of the shape in the start state of the morph
   * sequence. Supported only for solid fills.
   *
   * @return start color
   */
  public RGBA getStartColor() {
    return startColor;
  }

  /**
   * Returns the transform matrix used to map the gradient from the gradient
   * square to the display surface in the start state of the morph sequence.
   * Supported only for gradient fills.
   *
   * @return start gradient matrix
   */
  public Matrix getStartGradientMatrix() {
    return startGradientMatrix;
  }

  /**
   * Returns the type of the morph fill style (one of the <code>TYPE_...</code>
   * constants).
   *
   * @return fill type
   */
  public TagConstants.FillType getType() {
    return type;
  }

  void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(type.getCode());
    switch (type.getGroup()) {
      case SOLID:
        startColor.write(stream);
        endColor.write(stream);
        break;
      case GRADIENT:
        startGradientMatrix.write(stream);
        endGradientMatrix.write(stream);
        gradient.write(stream);
        break;
      case BITMAP:
        stream.writeUI16(bitmapId);
        startBitmapMatrix.write(stream);
        endBitmapMatrix.write(stream);
    }
  }
}
