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
