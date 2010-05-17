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

import java.io.IOException;

/**
 * <p>
 * This tag maps a glyph font (defined with <code>DefineFont</code>) to a device
 * font, providing a font name, style attributes (e.g. bold, italic) and a table
 * of characters matching the glyph shape table contained in the corresponding
 * <code>DefineFont</code> tag, thereby defining a one-to-one mapping between
 * glyphs and characters.
 * </p>
 * 
 * <p>
 * With this mapping available, you can choose to use the specified device font
 * if available and use the glyph font as fallback by passing the
 * <code>devicefont</code> parameter to the flash player (within the object tag:
 * <code>&lt;param name=&quot;devicefont&quot;
 * value=&quot;true&quot;&gt;</code>). With dynamic text, this parameter is not
 * needed anymore, as this option can be specified within the
 * <code>DefineEditText</code> tag.
 * </p>
 * 
 * <p>
 * Note: Consider using <code>DefineFont2</code> instead of the
 * <code>DefineFont</code> - <code>DefineFontInfo</code> tag pair, as it
 * incorporates the same functionality in a single tag.
 * </p>
 * 
 * <p>
 * Note: despite its name, this tag isn't a definition tag. It doesn't define a
 * new character, it specifies attributes for an existing character.
 * </p>
 * 
 * @see DefineFont
 * @see DefineFont2
 * @see DefineEditText
 * @since SWF 1
 */
public final class DefineFontInfo extends Tag {

  private static final long serialVersionUID = 1L;

  private int fontId;
  private String fontName;
  private boolean smallText;
  private boolean shiftJIS;
  private boolean ansi;
  private boolean italic;
  private boolean bold;
  private char[] codeTable;

  /**
   * Creates a new DefineFontInfo tag.
   * 
   * @param fontId
   *          character ID from <code>DefineFont</code>
   * @param fontName
   *          font name, direct (e.g. 'Times New Roman') or indirect (e.g.
   *          '_serif')
   * @param codeTable
   *          table of characters matching the glyph shape table of the font
   */
  public DefineFontInfo(int fontId, String fontName, char[] codeTable) {
    super(TagType.DEFINE_FONT_INFO);
    this.fontId = fontId;
    this.fontName = fontName;
    this.codeTable = codeTable;
  }
  
  DefineFontInfo() {
    super(TagType.DEFINE_FONT_INFO);
  }

  /**
   * Sets the value of the ANSI flag. If ANSI is set, the shiftJIS flag is
   * cleared. If neither ANSI nor shiftJIS are set, UCS-2 is used.
   * 
   * @param ansi
   *          <code>true</code> if flag set, else <code>false</code>
   */
  public void setANSI(boolean ansi) {
    this.ansi = ansi;
    if (ansi) {
      shiftJIS = false;
    }
  }

  /**
   * Checks if the ANSI flag is set. If neither ANSI nor shiftJIS are set, UCS-2
   * is used.
   * 
   * @return <code>true</code> if flag set, else <code>false</code>
   */
  public boolean isANSI() {
    return ansi;
  }

  /**
   * Sets/clears bold style.
   * 
   * @param bold
   *          <code>true</code> for bold, otherwise <code>false</code>
   */
  public void setBold(boolean bold) {
    this.bold = bold;
  }

  /**
   * Checks if the text is bold.
   * 
   * @return <code>true</code> if text is bold, otherwise <code>false</code>
   */
  public boolean isBold() {
    return bold;
  }

  /**
   * Sets the font's code table containing a character for each glyph.
   * 
   * @param codeTable
   *          code table of font
   */
  public void setCodeTable(char[] codeTable) {
    this.codeTable = codeTable;
  }

  /**
   * Returns the font's code table containing a character for each glyph.
   * 
   * @return code table of font
   */
  public char[] getCodeTable() {
    return codeTable;
  }

  /**
   * Sets the character ID of the font this tag specifies attributes for.
   * 
   * @param fontId
   *          character ID of font
   */
  public void setFontId(int fontId) {
    this.fontId = fontId;
  }

  /**
   * Returns the character ID of the font this tag specifies attributes for.
   * 
   * @return font's character ID
   */
  public int getFontId() {
    return fontId;
  }

  /**
   * Sets the name of the font. This can be either a direct (e.g. 'Times New
   * Roman') or an indirect font name (e.g. '_serif').
   * 
   * @param fontName
   *          font name as string
   */
  public void setFontName(String fontName) {
    this.fontName = fontName;
  }

  /**
   * Returns the name of the font. This can be either a direct (e.g. 'Times New
   * Roman') or an indirect font name (e.g. '_serif').
   * 
   * @return font name as string
   */
  public String getFontName() {
    return fontName;
  }

  /**
   * Sets/clears italic style.
   * 
   * @param italic
   *          <code>true</code> for italic, otherwise <code>false</code>
   */
  public void setItalic(boolean italic) {
    this.italic = italic;
  }

  /**
   * Checks if the text is italic.
   * 
   * @return <code>true</code> if text is italic, otherwise <code>false</code>
   */
  public boolean isItalic() {
    return italic;
  }

  /**
   * Sets the value of the shiftJIS flag. If shiftJIS is set, then ANSI is
   * cleared. If neither ANSI nor shiftJIS are set, UCS-2 is used.
   * 
   * @param shiftJIS
   *          <code>true</code> if flag set, else <code>false</code>
   */
  public void setShiftJIS(boolean shiftJIS) {
    this.shiftJIS = shiftJIS;
    if (shiftJIS) {
      ansi = false;
    }
  }

  /**
   * Checks if the shiftJIS flag is set. If neither ANSI nor shiftJIS are set,
   * UCS-2 is used.
   * 
   * @return <code>true</code> if flag set, else <code>false</code>
   */
  public boolean isShiftJIS() {
    return shiftJIS;
  }

  /**
   * Sets the value of the smallFont flag. When this flag is set, the font is
   * optimized for small text (anti-aliasing is disabled).
   * 
   * @param smallText
   *          <code>true</code> if flag set, else <code>false</code>
   */
  public void setSmallText(boolean smallText) {
    this.smallText = smallText;
  }

  /**
   * Checks the smallFont flag. When this flag is set, the font is optimized for
   * small text (anti-aliasing is disabled).
   * 
   * @return <code>true</code> if set, else <code>false</code>
   */
  public boolean isSmallText() {
    return smallText;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(fontId);
    byte[] fontNameBuffer = (fontName != null) ? fontName.getBytes("UTF-8") : new byte[0];
    outStream.writeUI8((short) (fontNameBuffer.length)); // font name length,
                                                         // not null terminated!
    outStream.writeBytes(fontNameBuffer);
    outStream.writeUnsignedBits(0, 2);
    outStream.writeBooleanBit(smallText);
    outStream.writeBooleanBit(shiftJIS);
    outStream.writeBooleanBit(ansi);
    outStream.writeBooleanBit(italic);
    outStream.writeBooleanBit(bold);
    boolean wideCodes = (!(shiftJIS || ansi)); // if shiftJIS or ansi set, then
                                               // false; else true
    outStream.writeBooleanBit(wideCodes);
    if (wideCodes) {
      for (int i = 0; i < codeTable.length; i++) {
        outStream.writeUI16(codeTable[i]);
      }
    } else {
      for (int i = 0; i < codeTable.length; i++) {
        outStream.writeUI8((short) codeTable[i]);
      }
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    fontId = inStream.readUI16();
    short fontNameLen = inStream.readUI8(); // not null-terminated!
    fontName = new String(inStream.readBytes(fontNameLen), "UTF-8");
    inStream.readUnsignedBits(2); // 2 reserved bits
    smallText = inStream.readBooleanBit();
    shiftJIS = inStream.readBooleanBit();
    ansi = inStream.readBooleanBit();
    italic = inStream.readBooleanBit();
    bold = inStream.readBooleanBit();
    boolean wideCodes = inStream.readBooleanBit();
    if (wideCodes) {
      int codeTableSize = (int) ((data.length - inStream.getOffset()) / 2);
      codeTable = new char[codeTableSize];
      for (int i = 0; i < codeTableSize; i++) {
        codeTable[i] = (char) inStream.readUI16();
      }
    } else {
      int codeTableSize = (int) (data.length - inStream.getOffset());
      codeTable = new char[codeTableSize];
      for (int i = 0; i < codeTableSize; i++) {
        codeTable[i] = (char) inStream.readUI8();
      }
    }
  }
}
