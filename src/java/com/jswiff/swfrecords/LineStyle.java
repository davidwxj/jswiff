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
 * This class is used to define a line style. Contains line width and color.
 */
public class LineStyle implements LineStyleTag, Serializable {

  private static final long serialVersionUID = 1L;
  
  private int width;
  private Color color;

  /**
   * Creates a new line style. Specify the width of the line in twips (1/20 px)
   * and the line color, which can be an <code>RGBA</code> or an
   * <code>RGB</code> instance depending on whether the tag the line style is
   * contained in supports transparency or not (<code>DefineShape3</code>
   * supports transparency).
   *
   * @param width line width
   * @param color line color
   *
   * @see com.jswiff.swfrecords.tags.DefineShape3
   */
  public LineStyle(int width, Color color) {
    this.width   = width;
    this.color   = color;
  }

  LineStyle(InputBitStream stream, boolean hasAlpha) throws IOException {
    width = stream.readUI16();
    if (hasAlpha) {
      color = new RGBA(stream);
    } else {
      color = new RGB(stream);
    }
  }

  /**
   * Returns the line color.
   *
   * @return line color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns the line width in twips (1/20 px).
   *
   * @return line width in twips
   */
  public int getWidth() {
    return width;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(width);
    color.write(stream);
  }
}
