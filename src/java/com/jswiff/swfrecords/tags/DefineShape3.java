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

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeWithStyle;

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

  void setData(byte[] data) throws IOException, InvalidCodeException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    shapeBounds = new Rect(inStream);
    shapes = new ShapeWithStyle(inStream, true); // alpha used
  }
}
