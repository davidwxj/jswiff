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

/**
 * <p>
 * This tag imports one or more characters from an SWF file. The imported
 * characters must have been previously exported with an
 * <code>ExportAssets</code> tag.
 * </p>
 * 
 * <p>
 * The character IDs of the imported characters presumably differ from the IDs
 * in the exporting file, therefore the characters chosen for export are
 * identified by (unique) export names. The character IDs are mapped to names
 * within a <code>ExportAssets</code> tag (using <code>ExportMapping</code>
 * instances). After import, these names are mapped back to (different)
 * character IDs within <code>ImportAssets</code> (using
 * <code>ImportMapping</code> instances).
 * </p>
 * 
 * @see ExportAssets
 * @since SWF 5
 */
public class ImportAssets extends Tag {

  private static final long serialVersionUID = 1L;
  
  protected String url;
  protected ImportMapping[] importMappings;

  /**
   * Creates a new ImportAssets tag. Supply the URL of the exporting SWF and an
   * array of import mappings (for each imported character one).
   * 
   * @param url
   *          URL of the source SWF
   * @param importMappings
   *          character import mappings
   */
  public ImportAssets(String url, ImportMapping[] importMappings) {
    this(url, importMappings, TagType.IMPORT_ASSETS);
  }
  
  ImportAssets(String url, ImportMapping[] importMappings, TagType t) {
    super(t);
    this.url = url;
    this.importMappings = importMappings;
  }

  ImportAssets() {
    this(TagType.IMPORT_ASSETS);
  }
  
  ImportAssets(TagType t) {
    super(t);
  }

  /**
   * Sets the import mappings defined in this tag.
   * 
   * @param importMappings
   *          character import mappings
   */
  public void setImportMappings(ImportMapping[] importMappings) {
    this.importMappings = importMappings;
  }

  /**
   * Returns the import mappings defined in this tag.
   * 
   * @return character import mappings
   */
  public ImportMapping[] getImportMappings() {
    return importMappings;
  }

  /**
   * Sets the URL of the SWF file exporting the characters.
   * 
   * @param url
   *          URL of import source
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Returns the URL of the SWF file exporting the characters.
   * 
   * @return URL of import source
   */
  public String getUrl() {
    return url;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeString(url);
    int count = importMappings.length;
    outStream.writeUI16(count);
    for (int i = 0; i < count; i++) {
      outStream.writeUI16(importMappings[i].getCharacterId());
      outStream.writeString(importMappings[i].getName());
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    if (getSWFVersion() < 6) {
      if (isJapanese()) {
        inStream.setShiftJIS(true);
      } else {
        inStream.setANSI(true);
      }
    }
    url = inStream.readString();
    int count = inStream.readUI16();
    importMappings = new ImportMapping[count];
    for (int i = 0; i < count; i++) {
      int characterId = inStream.readUI16();
      String name = inStream.readString();
      importMappings[i] = new ImportMapping(name, characterId);
    }
  }

  /**
   * Defines an (immutable) import mapping for a character, containing its
   * export name and the ID the character instance gets after import.
   */
  public static class ImportMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int characterId;
    private String name;

    /**
     * Creates a new import mapping. Supply export name of character and ID of
     * imported instance.
     * 
     * @param name
     *          export name of imported character
     * @param characterId
     *          imported instance ID
     */
    public ImportMapping(String name, int characterId) {
      this.name = name;
      this.characterId = characterId;
    }

    /**
     * Returns the imported character instance's ID.
     * 
     * @return character ID
     */
    public int getCharacterId() {
      return characterId;
    }

    /**
     * Returns the export name of the imported character.
     * 
     * @return export name
     */
    public String getName() {
      return name;
    }
  }
}
