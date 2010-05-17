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

import com.jswiff.constants.TagConstants.CapStyle;
import com.jswiff.constants.TagConstants.JointStyle;
import com.jswiff.constants.TagConstants.ScaleStrokeMethod;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.util.EnumSet;


/**
 * This class is used to define a line style. Contains line width and color.
 */
public final class LineStyle2 extends EnhancedStrokeStyle implements LineStyleTag {
  
  private static final long serialVersionUID = 1L;
  
  private int width;
  private RGBA color           = RGBA.BLACK;
  private FillStyle fillStyle;

  /**
   * Creates a new line style. Specify the width of the line in twips (1/20
   * px).
   *
   * @param width line width
   */
  public LineStyle2(int width) {
    this.width = width;
  }

  LineStyle2(InputBitStream stream) throws IOException {
    width           = stream.readUI16();
    setStartCapStyle(CapStyle.lookup((short)stream.readUnsignedBits(2)));
    setJointStyle(JointStyle.lookup((short) stream.readUnsignedBits(2)));
    boolean hasFill = stream.readBooleanBit();
    boolean noHScale = stream.readBooleanBit();
    boolean noVScale = stream.readBooleanBit();
    //Bitwise OR the two flags to get a number between 0 - 3 representing the stroke scaling method
    setScaleStroke(ScaleStrokeMethod.lookup( (short) ((noHScale ? 0 : 2) | (noVScale ? 0 : 1)) ));
    setPixelHinting(stream.readBooleanBit());
    stream.readUnsignedBits(5);
    setClose(!stream.readBooleanBit());
    setEndCapStyle(CapStyle.lookup((short) stream.readUnsignedBits(2)));
    if (JointStyle.MITER.equals(getJointStyle())) {
      setMiterLimit(stream.readFP16());
    }
    if (hasFill) {
      fillStyle   = new FillStyle(stream, true);
      color       = null;
    } else {
      color = new RGBA(stream);
    }
  }

  /**
   * Set the line colour
   * @param color the line colour
   */
  public void setColor(RGBA color) {
    this.color = color;
  }

  /**
   * Returns the line colour.
   * @return line colour
   */
  public RGBA getColor() {
    return color;
  }

  public void setFillStyle(FillStyle fillStyle) {
    this.fillStyle = fillStyle;
  }

  public FillStyle getFillStyle() {
    return fillStyle;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getWidth() {
    return width;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(width);
    stream.writeUnsignedBits(getStartCapStyle().getCode(), 2);
    stream.writeUnsignedBits(getJointStyle().getCode(), 2);
    boolean hasFill = fillStyle != null;
    stream.writeBooleanBit(hasFill);
    stream.writeBooleanBit(EnumSet.of(ScaleStrokeMethod.VERTICAL, ScaleStrokeMethod.NONE).contains(getScaleStroke()));
    stream.writeBooleanBit(EnumSet.of(ScaleStrokeMethod.HORIZONTAL, ScaleStrokeMethod.NONE).contains(getScaleStroke()));
    stream.writeBooleanBit(isPixelHinting());
    stream.writeUnsignedBits(0, 5);
    stream.writeBooleanBit(!isClose());
    stream.writeUnsignedBits(getEndCapStyle().getCode(), 2);
    if (JointStyle.MITER.equals(getJointStyle())) {
      stream.writeFP16(getMiterLimit());
    }
    if (hasFill) {
      fillStyle.write(stream);
    } else {
      color.write(stream);
    }
  }
}
