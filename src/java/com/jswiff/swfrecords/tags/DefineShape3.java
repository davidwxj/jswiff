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

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeWithStyle;

import java.io.IOException;

/**
 * This tag is used to define shapes, just like <code>DefineShape</code> and
 * <code>DefineShape2</code>. Unlike older shape definition tags,
 * <code>DefineShape3</code> adds transparency support, i.e. colors within this
 * tag are <code>RGBA</code> (not <code>RGB</code>) instances.
 * 
 * @see com.jswiff.swfrecords.tags.DefineShape
 * @see com.jswiff.swfrecords.tags.DefineShape2
 * @see com.jswiff.swfrecords.RGBA
 * @since SWF 3
 */
public final class DefineShape3 extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private Rect shapeBounds;
  private ShapeWithStyle shapes;

  /**
   * Creates a new DefineShape3 tag. Supply the character ID of the shape, its
   * bounding box and its primitives and styles.
   * 
   * @param characterId
   *          character ID of shape
   * @param shapeBounds
   *          bounding box of shape
   * @param shapes
   *          shape's primitives and styles
   */
  public DefineShape3(int characterId, Rect shapeBounds, ShapeWithStyle shapes) {
    super(TagType.DEFINE_SHAPE_3);
    this.characterId = characterId;
    this.shapeBounds = shapeBounds;
    this.shapes = shapes;
  }

  DefineShape3() {
    super(TagType.DEFINE_SHAPE_3);
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
    shapes.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    shapeBounds = new Rect(inStream);
    shapes = new ShapeWithStyle(inStream, true); // alpha used
  }
}
