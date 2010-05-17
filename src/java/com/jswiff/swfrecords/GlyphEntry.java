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
 * This class describes a single text character by referencing a glyph from the
 * text font's glyph table.
 */
public final class GlyphEntry implements Serializable {
  private int glyphIndex;
  private int glyphAdvance;

  /**
   * Creates a new GlyphEntry instance. Specify the index of the glyph in the
   * glyph table of the text font, and the advance value (i.e. the horizontal
   * distance between the reference points of current and subsequent glyph)
   *
   * @param glyphIndex index of glyph in glyph table
   * @param glyphAdvance advance in twips (1/20 px)
   */
  public GlyphEntry(int glyphIndex, int glyphAdvance) {
    this.glyphIndex     = glyphIndex;
    this.glyphAdvance   = glyphAdvance;
  }

  /**
   * Creates a new GlyphEntry instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   * @param glyphBits bit count used for glyph index representation
   * @param advanceBits bit count used for advance value representation
   *
   * @throws IOException if an I/O error occured
   */
  public GlyphEntry(InputBitStream stream, short glyphBits, short advanceBits)
    throws IOException {
    glyphIndex     = (int) stream.readUnsignedBits(glyphBits);
    glyphAdvance   = (int) stream.readSignedBits(advanceBits);
  }

  /**
   * Returns the glyph's advance value, i.e. the horizontal distance between
   * the reference points of current and subsequent glyph.
   *
   * @return glyph advance in twips (1/20 px)
   */
  public int getGlyphAdvance() {
    return glyphAdvance;
  }

  /**
   * Returns this glyph's index in the glyph table of the text font.
   *
   * @return index of glyph in glyph table
   */
  public int getGlyphIndex() {
    return glyphIndex;
  }

  /**
   * Writes the instance to a bit stream.
   *
   * @param stream target bit stream
   * @param glyphBits bit count used for glyph index representation
   * @param advanceBits bit count used for advance value representation
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream, short glyphBits, short advanceBits)
    throws IOException {
    stream.writeUnsignedBits(glyphIndex, glyphBits);
    stream.writeSignedBits(glyphAdvance, advanceBits);
  }
}
