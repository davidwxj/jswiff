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

import com.jswiff.constants.TagConstants.LangCode;
import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

/**
 * <p>
 * Like <code>DefineFontInfo</code>, this tag also maps a glyph font (defined
 * with <code>DefineFont</code>) to a device font, providing a font name, style
 * attributes (e.g. bold, italic) and a table of characters matching the glyph
 * shape table contained in the corresponding <code>DefineFont</code> tag,
 * thereby defining a one-to-one mapping between glyphs and characters.
 * </p>
 * 
 * <p>
 * Unlike <code>DefineFontInfo</code>, <code>DefineFontInfo2</code> contains a
 * field for a language code, making text behavior independent on the locale in
 * which Flash Player is running. This field is considered e.g. when determining
 * line breaking rules. Also, the ANSI and ShiftJIS encodings are not available
 * anymore, as Unicode encoding is used.
 * </p>
 * 
 * <p>
 * Note: Consider using <code>DefineFont2</code> instead of the
 * <code>DefineFont</code> - <code>DefineFontInfo2</code> tag pair, as it
 * incorporates the same functionality in a single tag.
 * </p>
 * 
 * <p>
 * Note: despite its name, this tag isn't a definition tag. It doesn't define a
 * new character, it specifies attributes for an existing character.
 * </p>
 * 
 * @see DefineFontInfo
 * @see DefineFont
 * @see DefineFont2
 * @since SWF 6
 */
public final class DefineFontInfo2 extends Tag {

  private static final long serialVersionUID = 1L;

  private int fontId;
  private String fontName;
  private boolean smallText; // glyphs aligned on pixel boundaries
  private boolean italic;
  private boolean bold;
  private LangCode langCode;
  private char[] codeTable;

  /**
   * Creates a new DefineFontInfo2 tag.
   * 
   * @param fontId
   *          character ID from <code>DefineFont</code>
   * @param fontName
   *          font name, direct (e.g. 'Times New Roman') or indirect (e.g.
   *          '_serif')
   * @param codeTable
   *          table of characters matching the glyph shape table of the font
   * @param langCode
   *          font language code
   */
  public DefineFontInfo2(int fontId, String fontName, char[] codeTable, LangCode langCode) {
    super(TagType.DEFINE_FONT_INFO_2);
    this.fontId = fontId;
    this.fontName = fontName;
    this.codeTable = codeTable;
    this.langCode = langCode;
  }
  
  DefineFontInfo2() {
    super(TagType.DEFINE_FONT_INFO_2);
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
   * Returns the character ID of the font.
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
   * Sets the language code of the font.
   * 
   * @param langCode
   *          font language code
   */
  public void setLangCode(LangCode langCode) {
    this.langCode = langCode;
  }

  /**
   * Returns the language code of the font.
   * 
   * @return font language code
   */
  public LangCode getLangCode() {
    return langCode;
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
    outStream.writeUnsignedBits(0, 2); // 2 reserved bits
    outStream.writeBooleanBit(smallText);
    outStream.writeUnsignedBits(0, 2); // shiftJIS and ansi not set
    outStream.writeBooleanBit(italic);
    outStream.writeBooleanBit(bold);
    outStream.writeBooleanBit(true); // wideCodes always set
    outStream.writeUI8(langCode.getCode());
    for (int i = 0; i < codeTable.length; i++) {
      outStream.writeUI16(codeTable[i]);
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    fontId = inStream.readUI16();
    short fontNameLen = inStream.readUI8(); // not null-terminated!
    fontName = new String(inStream.readBytes(fontNameLen), "UTF-8");
    inStream.readUnsignedBits(2); // 2 reserved bits
    smallText = inStream.readBooleanBit();
    inStream.readBooleanBit(); // shiftJIS, always 0
    inStream.readBooleanBit(); // ANSI, always 0
    italic = inStream.readBooleanBit();
    bold = inStream.readBooleanBit();
    // dataStream.align(); // wideCodes always true - but align not needed
    // before readUI8()
    langCode = LangCode.lookup(inStream.readUI8());
    int codeTableSize = (int) ((data.length - inStream.getOffset()) / 2);
    codeTable = new char[codeTableSize];
    for (int i = 0; i < codeTableSize; i++) {
      codeTable[i] = (char) inStream.readUI16();
    }
  }
}
