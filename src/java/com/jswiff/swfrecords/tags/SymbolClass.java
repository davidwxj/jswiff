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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This tag contains a list of symbol references, which is used to instantiate
 * objects (symbols) defined with <code>DoABCDefine</code>.
 * </p>
 * 
 * @see DoAbcDefine
 * @since SWF 9
 */
public final class SymbolClass extends Tag {

  private static final long serialVersionUID = 1L;

  private List<SymbolReference> references = new ArrayList<SymbolReference>();

  /**
   * Creates a new SymbolClass instance. Supply an array of export mappings (for
   * each exported character one).
   */
  public SymbolClass() {
    super(TagType.SYMBOL_CLASS);
  }

  /**
   * Returns the symbol references defined in this tag.
   * 
   * @return character symbol references
   */
  public List<SymbolReference> getReferences() {
    return references;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    int count = references.size();
    outStream.writeUI16(count);
    for (int i = 0; i < count; i++) {
      SymbolReference symbolReference = references.get(i);
      outStream.writeUI16(symbolReference.getCharacterId());
      outStream.writeString(symbolReference.getName());
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    int count = inStream.readUI16();
    for (int i = 0; i < count; i++) {
      references.add(new SymbolReference(inStream.readUI16(), inStream.readString()));
    }
  }

  /**
   * Defines an (immutable) symbol reference.
   */
  public static class SymbolReference implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int characterId;
    private String name;

    /**
     * Creates a new symbol reference. Supply character ID and symbol name
     * 
     * @param characterId
     *          character ID
     * @param name
     *          symbol name
     */
    public SymbolReference(int characterId, String name) {
      this.characterId = characterId;
      this.name = name;
    }

    /**
     * Returns the character ID of the symbol reference
     * 
     * @return character ID
     */
    public int getCharacterId() {
      return characterId;
    }

    /**
     * Returns the name of the symbol to be referenced
     * 
     * @return export name
     */
    public String getName() {
      return name;
    }
  }
}
