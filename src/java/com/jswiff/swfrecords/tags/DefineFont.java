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
import com.jswiff.swfrecords.Shape;

import java.io.IOException;

/**
 * <p>
 * This tag defines the shape outlines of each glyph used in a particular font.
 * Only glyphs used by <code>DefineText</code> tags need to be defined.
 * </p>
 * 
 * <p>
 * Warning: for dynamic text, you have to use the <code>DefineFont2</code> tag.
 * </p>
 * 
 * @see Shape
 * @since SWF 1
 */
public final class DefineFont extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private Shape[] glyphShapeTable;

  /**
   * Creates a new DefineFont tag.
   * 
   * @param characterId
   *          the character ID of the font
   * @param glyphShapeTable
   *          array of <code>Shape</code> instances
   */
  public DefineFont(int characterId, Shape[] glyphShapeTable) {
    super(TagType.DEFINE_FONT);
    this.characterId = characterId;
    this.glyphShapeTable = glyphShapeTable;
  }
  
  DefineFont() {
    super(TagType.DEFINE_FONT);
  }

  /**
   * Sets an array of <code>Shape</code> instances used to define character
   * glyphs.
   * 
   * @param glyphShapeTable
   *          array of <code>Shape</code> instances
   */
  public void setGlyphShapeTable(Shape[] glyphShapeTable) {
    this.glyphShapeTable = glyphShapeTable;
  }

  /**
   * Returns an array of <code>Shape</code> instances used to define character
   * glyphs.
   * 
   * @return array of <code>Shape</code> instances
   */
  public Shape[] getGlyphShapeTable() {
    return glyphShapeTable;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    int shapeTableOffset = glyphShapeTable.length * 2; // 2 bytes * table length
    outStream.writeUI16(shapeTableOffset); // first entry of offsetTable
    OutputBitStream glyphShapeTableStream = new OutputBitStream();
    glyphShapeTable[0].write(glyphShapeTableStream); // write first shape
    for (int i = 1; i < glyphShapeTable.length; i++) {
      outStream.writeUI16((int) (shapeTableOffset + glyphShapeTableStream.getOffset()));
      glyphShapeTable[i].write(glyphShapeTableStream);
    }
    outStream.writeBytes(glyphShapeTableStream.getData());
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    int shapeTableOffset = inStream.readUI16();
    int tableSize = shapeTableOffset / 2;
    inStream.readBytes(shapeTableOffset - 2); // ignore rest of the offsetTable
    glyphShapeTable = new Shape[tableSize];
    for (int i = 0; i < tableSize; i++) {
      glyphShapeTable[i] = new Shape(inStream);
    }
  }
}
