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
 * This class is used to define line styles used in morph sequences. Line
 * styles are defined in pairs: for start and for end shapes.
 *
 * @see MorphLineStyles
 * @see com.jswiff.swfrecords.tags.DefineMorphShape
 * @since SWF 8
 */
public final class MorphLineStyle2 extends EnhancedStrokeStyle implements MorphLineStyleTag {
  
  private static final long serialVersionUID = 1L;
  
  private int startWidth;
  private int endWidth;
  private RGBA startColor;
  private RGBA endColor;
  private MorphFillStyle fillStyle;
  
  /**
   * Creates a new MorphLineStyle2 instance. Supply width and RGBA color for
   * lines in start and end shapes.
   *
   * @param startWidth width of line in start shape (in twips = 1/20 px)
   * @param startColor color of line in start shape
   * @param endWidth width of line in start shape (in twips = 1/20 px)
   * @param endColor color of line in end shape
   */
  public MorphLineStyle2(int startWidth, RGBA startColor, int endWidth, RGBA endColor) {
    this.startWidth   = startWidth;
    this.startColor   = startColor;
    this.endWidth     = endWidth;
    this.endColor     = endColor;
  }

  /**
   * Creates a new MorphLineStyle2 instance. Supply start and end width and
   * fill style.
   *
   * @param startWidth width of line in start shape (in twips = 1/20 px)
   * @param endWidth width of line in start shape (in twips = 1/20 px)
   * @param fillStyle color of line in start shape
   */
  public MorphLineStyle2(int startWidth, int endWidth, MorphFillStyle fillStyle) {
    this.startWidth   = startWidth;
    this.endWidth     = endWidth;
    this.fillStyle    = fillStyle;
  }

  MorphLineStyle2(InputBitStream stream) throws IOException {
    startWidth      = stream.readUI16();
    endWidth        = stream.readUI16();
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
      fillStyle = new MorphFillStyle(stream);
    } else {
      startColor   = new RGBA(stream);
      endColor     = new RGBA(stream);
    }
  }

  public void setEndColor(RGBA endColor) {
    this.endColor = endColor;
    if (endColor != null) {
      fillStyle = null;
    }
  }

  /**
   * Returns the color of lines used in the end shape of the morph sequence.
   *
   * @return RGBA color of lines in end shape
   */
  public RGBA getEndColor() {
    return endColor;
  }

  public void setEndWidth(int endWidth) {
    this.endWidth = endWidth;
  }

  /**
   * Returns the width of lines used in the end shape of the morph sequence.
   *
   * @return width of lines in end shape in twips (1/20 px)
   */
  public int getEndWidth() {
    return endWidth;
  }

  public void setFillStyle(MorphFillStyle fillStyle) {
    this.fillStyle = fillStyle;
    if (fillStyle != null) {
      startColor   = null;
      endColor     = null;
    }
  }

  public MorphFillStyle getFillStyle() {
    return fillStyle;
  }

  public void setStartColor(RGBA startColor) {
    this.startColor = startColor;
    if (startColor != null) {
      fillStyle = null;
    }
  }

  /**
   * Returns the color of lines used in the start shape of the morph sequence.
   *
   * @return RGBA color of lines in start shape
   */
  public RGBA getStartColor() {
    return startColor;
  }

  public void setStartWidth(int startWidth) {
    this.startWidth = startWidth;
  }

  /**
   * Returns the width of lines used in the start shape of the morph sequence.
   *
   * @return width of lines in start shape in twips (1/20 px)
   */
  public int getStartWidth() {
    return startWidth;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(startWidth);
    stream.writeUI16(endWidth);
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
      startColor.write(stream);
      endColor.write(stream);
    }
  }
  
}
