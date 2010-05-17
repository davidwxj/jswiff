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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Implements an array of fill styles used in a morphing sequence.
 * </p>
 * 
 * <p>
 * <b>WARNING:</b> array index starts at 1, not 0!
 * </p>
 */
public final class MorphFillStyles implements Serializable {
  private List<MorphFillStyle> styles = new ArrayList<MorphFillStyle>();

  /**
   * Creates a new MorphFillStyles instance.
   */
  public MorphFillStyles() {
    // empty
  }

  /**
   * Reads a new instance from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public MorphFillStyles(InputBitStream stream) throws IOException {
    int styleCount = stream.readUI8();
    if (styleCount == 0xFF) {
      styleCount = stream.readUI16();
    }
    for (int i = 0; i < styleCount; i++) {
      styles.add(new MorphFillStyle(stream));
    }
  }

  /**
   * Returns the size of the morph fill style array.
   *
   * @return array size
   */
  public int getSize() {
    return styles.size();
  }

  /**
   * <p>
   * Returns the morph fill style at the specified position in the array.
   * </p>
   * 
   * <p>
   * <b>WARNING:</b> indexes start at 1, not at 0!
   * </p>
   *
   * @param index index starting at 1
   *
   * @return morph fill style located at the specified position
   */
  public MorphFillStyle getStyle(int index) {
    return styles.get(index - 1);
  }

  /**
   * Adds a morph fill style at the end of the array.
   *
   * @param fillStyle a morph fill style
   */
  public void addStyle(MorphFillStyle fillStyle) {
    styles.add(fillStyle);
  }

  /**
   * Writes this instance to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream) throws IOException {
    int styleCount = styles.size();
    if (styleCount >= 0xFF) {
      stream.writeUI8((short) 0xFF);
      stream.writeUI16(styleCount);
    } else {
      stream.writeUI8((short) styleCount);
    }
    for (int i = 0; i < styles.size(); i++) {
      styles.get(i).write(stream);
    }
  }
}
