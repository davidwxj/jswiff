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
import com.jswiff.swfrecords.AlignmentZone;

import java.io.IOException;

/**
 * @since SWF 8
 */
public class DefineFontAlignment extends Tag {

  private static final long serialVersionUID = 1L;
  
  /** TODO: Comments */
  public static final byte THIN = 0;
  /** TODO: Comments */
  public static final byte MEDIUM = 1;
  /** TODO: Comments */
  public static final byte THICK = 2;
  private int fontId;
  private byte thickness;
  private AlignmentZone[] alignmentZones;

  /**
   * Creates a new DefineFontAlignment instance.
   * 
   * @param fontId
   *          TODO: Comments
   * @param thickness
   *          TODO: Comments
   * @param alignmentZones
   *          TODO: Comments
   */
  public DefineFontAlignment(int fontId, byte thickness, AlignmentZone[] alignmentZones) {
    super(TagType.DEFINE_FONT_ALIGNMENT);
    this.fontId = fontId;
    this.thickness = thickness;
    this.alignmentZones = alignmentZones;
  }
  
  DefineFontAlignment() {
    super(TagType.DEFINE_FONT_ALIGNMENT);
  }

  /**
   * TODO: Comments
   * 
   * @param alignmentZones
   *          TODO: Comments
   */
  public void setAlignmentZones(AlignmentZone[] alignmentZones) {
    this.alignmentZones = alignmentZones;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public AlignmentZone[] getAlignmentZones() {
    return alignmentZones;
  }

  /**
   * TODO: Comments
   * 
   * @param fontId
   *          TODO: Comments
   */
  public void setFontId(int fontId) {
    this.fontId = fontId;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public int getFontId() {
    return fontId;
  }

  /**
   * TODO: Comments
   * 
   * @param thickness
   *          THIN, MEDIUM or THICK
   */
  public void setThickness(byte thickness) {
    this.thickness = thickness;
  }

  /**
   * TODO: Comments
   * 
   * @return THIN, MEDIUM or THICK
   */
  public byte getThickness() {
    return thickness;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(fontId);
    outStream.writeUnsignedBits(thickness, 2);
    outStream.writeUnsignedBits(0, 6);
    if (alignmentZones != null) {
      for (int i = 0; i < alignmentZones.length; i++) {
        alignmentZones[i].write(outStream);
      }
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    fontId = inStream.readUI16();
    thickness = (byte) inStream.readUnsignedBits(2);
    inStream.readUnsignedBits(6);
    int glyphCount = inStream.available() / 10;
    alignmentZones = new AlignmentZone[glyphCount];
    for (int i = 0; i < glyphCount; i++) {
      alignmentZones[i] = new AlignmentZone(inStream);
    }
  }
}
