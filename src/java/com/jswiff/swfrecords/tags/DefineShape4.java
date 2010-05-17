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

package com.jswiff.swfrecords.tags;

import com.jswiff.constants.TagConstants.ScaleStrokeMethod;
import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.LineStyle2;
import com.jswiff.swfrecords.LineStyleArray;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeWithStyle;

import java.io.IOException;

/**
 * Used to define shapes, similar to <code>DefineShape</code>,
 * <code>DefineShape2</code> and <code>DefineShape3</code>. Unlike older shape
 * definition tags, <code>DefineShape4</code> can contain
 * <code>LineStyle2</code> structures. Additionally, it allows to define edge
 * bounds and to specify flags for stroke hinting.
 * 
 * @see com.jswiff.swfrecords.tags.DefineShape
 * @see com.jswiff.swfrecords.tags.DefineShape2
 * @see com.jswiff.swfrecords.tags.DefineShape3
 * @see com.jswiff.swfrecords.LineStyle2
 * @since SWF 8
 */
public final class DefineShape4 extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private Rect shapeBounds;
  private Rect edgeBounds;
  private ShapeWithStyle shapes;
  private boolean hasScalingStrokes;
  private boolean hasNonscalingStrokes;

  /**
   * Creates a new DefineShape4 tag. Supply the character ID of the shape, its
   * shape and edge bounding box and its primitives and styles.
   * 
   * @param characterId
   *          character ID of shape
   * @param shapeBounds
   *          bounding box of shape
   * @param edgeBounds
   *          edge bounding box
   * @param shapes
   *          shape's primitives and styles
   */
  public DefineShape4(int characterId, Rect shapeBounds, Rect edgeBounds, ShapeWithStyle shapes) {
    super(TagType.DEFINE_SHAPE_4);
    this.characterId = characterId;
    this.shapeBounds = shapeBounds;
    this.edgeBounds = edgeBounds;
    this.shapes = shapes;
  }

  DefineShape4() {
    super(TagType.DEFINE_SHAPE_4);
  }

  /**
   * TODO: Comments
   * 
   * @param edgeBounds
   *          TODO: Comments
   */
  public void setEdgeBounds(Rect edgeBounds) {
    this.edgeBounds = edgeBounds;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public Rect getEdgeBounds() {
    return edgeBounds;
  }

  /**
   * Sets the bounding box of the shape, i.e. the rectangle that completely
   * encloses it.
   * 
   * @param shapeBounds
   *          shape's bounds
   */
  public void setShapeBounds(Rect shapeBounds) {
    this.shapeBounds = shapeBounds;
  }

  /**
   * Returns the bounding box of the shape, i.e. the rectangle that completely
   * encloses it.
   * 
   * @return shape's bounds
   */
  public Rect getShapeBounds() {
    return shapeBounds;
  }

  /**
   * Sets the shape's primitives and styles (i.e. lines and curves) in a
   * <code>ShapeWithStyle</code> instance.
   * 
   * @param shapes
   *          shape's primitives and styles
   */
  public void setShapes(ShapeWithStyle shapes) {
    this.shapes = shapes;
  }

  /**
   * Returns the shape's primitives and styles (i.e. lines and curves) in a
   * <code>ShapeWithStyle</code> instance.
   * 
   * @return shape's primitives and styles
   */
  public ShapeWithStyle getShapes() {
    return shapes;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    shapeBounds.write(outStream);
    edgeBounds.write(outStream);
    outStream.writeUnsignedBits(0, 6);
    checkStrokeScaling();
    outStream.writeBooleanBit(hasNonscalingStrokes);
    outStream.writeBooleanBit(hasScalingStrokes);
    shapes.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    shapeBounds = new Rect(inStream);
    edgeBounds = new Rect(inStream);
    inStream.readUI8(); // 6 reserved bits and 2 flags we can ignore
    shapes = new ShapeWithStyle(inStream);
  }

  private void checkStrokeScaling() {
    if (shapes == null) {
      return;
    }
    hasNonscalingStrokes = false;
    hasScalingStrokes = false;
    LineStyleArray lineStyles = shapes.getLineStyles();
    for (int i = 1; i <= lineStyles.getSize(); i++) {
      LineStyle2 style = (LineStyle2) lineStyles.getStyle(i);
      if (ScaleStrokeMethod.NONE.equals(style.getScaleStroke())) {
        hasNonscalingStrokes = true;
      } else {
        hasScalingStrokes = true;
      }
      if (hasNonscalingStrokes && hasScalingStrokes) {
        break;
      }
    }
  }
}
