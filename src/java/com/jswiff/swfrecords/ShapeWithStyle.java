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


/**
 * This class extends the <code>Shape</code> class by including fill and line
 * styles. Like the <code>Shape</code> class, <code>ShapeWithStyle</code> also
 * contains one or more <code>ShapeRecord</code> instances which define style
 * changes and primitives as lines and curves. Used within
 * <code>DefineShape</code>, <code>DefineShape2</code> and
 * <code>DefineShape3</code>.
 *
 * @see com.jswiff.swfrecords.tags.DefineShape
 * @see com.jswiff.swfrecords.tags.DefineShape2
 * @see com.jswiff.swfrecords.tags.DefineShape3
 */
public final class ShapeWithStyle extends Shape {
  private FillStyleArray fillStyles;
  private LineStyleArray lineStyles;

  /**
   * Creates a new ShapeWithStyle instance. Supply a fill and line style array
   * and an array of shape records. The style arrays must contain less than
   * 256 styles when used within a <code>DefineShape</code> tag.
   *
   * @param fillStyles fill style array
   * @param lineStyles line style array
   * @param shapeRecords shape record array
   */
  public ShapeWithStyle(
    FillStyleArray fillStyles, LineStyleArray lineStyles,
    ShapeRecord[] shapeRecords) {
    super(shapeRecords);
    this.fillStyles   = fillStyles;
    this.lineStyles   = lineStyles;
  }

  /**
   * Creates a new ShapeWithStyle instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   * @param hasAlpha whether transparency is supported
   *
   * @throws IOException if an I/O error occured
   */
  public ShapeWithStyle(InputBitStream stream, boolean hasAlpha)
    throws IOException {
    fillStyles   = new FillStyleArray(stream, hasAlpha);
    lineStyles   = new LineStyleArray(stream, hasAlpha);
    read(stream, false, hasAlpha);
  }
  
  public ShapeWithStyle(InputBitStream stream)
  throws IOException {
  fillStyles   = new FillStyleArray(stream, true);
  lineStyles   = new LineStyleArray(stream);
  read(stream, true, true);
}

  /**
   * Returns the fill style array.
   *
   * @return fill styles
   */
  public FillStyleArray getFillStyles() {
    return fillStyles;
  }

  /**
   * Returns the line style array.
   *
   * @return line styles
   */
  public LineStyleArray getLineStyles() {
    return lineStyles;
  }

  /**
   * Writes this instance to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream) throws IOException {
    fillStyles.write(stream);
    lineStyles.write(stream);
    super.write(stream);
  }
}
