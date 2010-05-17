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
import com.jswiff.swfrecords.GlyphEntry;
import com.jswiff.swfrecords.Matrix;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.TextRecord;

import java.io.IOException;
import java.util.Vector;

/**
 * This tag defines a block of static text. The bounding box of the text as well
 * as a transform matrix can be specified. The actual text characters their
 * style and position is defined in an array of <code>TextRecord</code>
 * instances.
 * 
 * @see TextRecord
 * @see DefineText2
 * @since SWF 1
 */
public final class DefineText extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private Rect textBounds;
  private Matrix textMatrix;
  private TextRecord[] textRecords;

  /**
   * Creates a new DefineText tag. Supply text character ID, bounding box and
   * transform matrix. Specify text characters, their style and position in an
   * <code>TextRecord</code> array.
   * 
   * @param characterId
   *          text character ID
   * @param textBounds
   *          bounding box of text
   * @param textMatrix
   *          transform matrix for text
   * @param textRecords
   *          <code>TextRecord</code> array containing text characters
   */
  public DefineText(int characterId, Rect textBounds, Matrix textMatrix, TextRecord[] textRecords) {
    super(TagType.DEFINE_TEXT);
    this.characterId = characterId;
    this.textBounds = textBounds;
    this.textMatrix = textMatrix;
    this.textRecords = textRecords;
  }

  DefineText() {
    super(TagType.DEFINE_TEXT);
  }

  /**
   * Sets the bounding box of the text, i.e. the smallest rectangle enclosing
   * the text.
   * 
   * @param textBounds
   *          text bounds
   */
  public void setTextBounds(Rect textBounds) {
    this.textBounds = textBounds;
  }

  /**
   * Returns the bounding box of the text, i.e. the smallest rectangle enclosing
   * the text.
   * 
   * @return text bounds
   */
  public Rect getTextBounds() {
    return textBounds;
  }

  /**
   * Sets the transform matrix of the text.
   * 
   * @param textMatrix
   *          text transform matrix
   */
  public void setTextMatrix(Matrix textMatrix) {
    this.textMatrix = textMatrix;
  }

  /**
   * Returns the transform matrix of the text.
   * 
   * @return text transform matrix
   */
  public Matrix getTextMatrix() {
    return textMatrix;
  }

  /**
   * Sets the array of <code>TextRecords</code> containing the text characters
   * and their style and position.
   * 
   * @param textRecords
   *          text record array
   */
  public void setTextRecords(TextRecord[] textRecords) {
    this.textRecords = textRecords;
  }

  /**
   * Returns the array of <code>TextRecords</code> containing the text
   * characters and their style and position.
   * 
   * @return text record array
   */
  public TextRecord[] getTextRecords() {
    return textRecords;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    this.setForceLongHeader(true);
    outStream.writeUI16(characterId);
    textBounds.write(outStream);
    textMatrix.write(outStream);
    // compute glyphBits and advanceBits
    int glyphBits = 0;
    int advanceBits = 0;
    for (int i = 0; i < textRecords.length; i++) {
      GlyphEntry[] glyphs = textRecords[i].getGlyphEntries();
      for (int j = 0; j < glyphs.length; j++) {
        glyphBits = Math.max(glyphBits, OutputBitStream.getUnsignedBitsLength(glyphs[j].getGlyphIndex()));
        advanceBits = Math.max(advanceBits, OutputBitStream.getSignedBitsLength(glyphs[j].getGlyphAdvance()));
      }
    }
    outStream.writeUI8((short) glyphBits);
    outStream.writeUI8((short) advanceBits);
    for (int i = 0; i < textRecords.length; i++) {
      textRecords[i].write(outStream, (short) glyphBits, (short) advanceBits);
    }
    outStream.writeUI8((short) 0);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    textBounds = new Rect(inStream);
    textMatrix = new Matrix(inStream);
    short glyphBits = inStream.readUI8();
    short advanceBits = inStream.readUI8();
    Vector<TextRecord> records = new Vector<TextRecord>();
    do {
      if (data[(int) inStream.getOffset()] == 0) {
        break;
      }
      TextRecord record = new TextRecord(inStream, glyphBits, advanceBits, false);
      records.add(record);
    } while (true);
    textRecords = new TextRecord[records.size()];
    records.copyInto(textRecords);
  }
}
