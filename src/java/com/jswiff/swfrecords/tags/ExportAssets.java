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
 * This tag makes one or more defined characters available for import by other
 * SWF files. Exported characters can be imported with the
 * <code>ImportAssets</code> tag.
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
 * @see ImportAssets
 * @since SWF 5
 */
public final class ExportAssets extends Tag {

  private static final long serialVersionUID = 1L;

  private ExportMapping[] exportMappings;

  /**
   * Creates a new ExportAssets instance. Supply an array of export mappings
   * (for each exported character one).
   * 
   * @param exportMappings
   *          character export mappings
   */
  public ExportAssets(ExportMapping[] exportMappings) {
    super(TagType.EXPORT_ASSETS);
    this.exportMappings = exportMappings;
  }

  ExportAssets() {
    super(TagType.EXPORT_ASSETS);
  }

  /**
   * Sets the export mappings defined in this tag.
   * 
   * @param exportMappings
   *          character export mappings
   */
  public void setExportMappings(ExportMapping[] exportMappings) {
    this.exportMappings = exportMappings;
  }

  /**
   * Returns the export mappings defined in this tag.
   * 
   * @return character export mappings
   */
  public ExportMapping[] getExportMappings() {
    return exportMappings;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    int count = exportMappings.length;
    outStream.writeUI16(count);
    for (int i = 0; i < count; i++) {
      outStream.writeUI16(exportMappings[i].getCharacterId());
      outStream.writeString(exportMappings[i].getName());
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
    int count = inStream.readUI16();
    exportMappings = new ExportMapping[count];
    for (int i = 0; i < count; i++) {
      exportMappings[i] = new ExportMapping(inStream.readUI16(), inStream.readString());
    }
  }

  /**
   * Defines an (immutable) export mapping for a character to be exported,
   * containing its ID and its export name.
   */
  public static class ExportMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int characterId;
    private String name;

    /**
     * Creates a new export mapping. Supply ID of exported character and export
     * name.
     * 
     * @param characterId
     *          character ID
     * @param name
     *          export name
     */
    public ExportMapping(int characterId, String name) {
      this.characterId = characterId;
      this.name = name;
    }

    /**
     * Returns the ID of the exported character.
     * 
     * @return character ID
     */
    public int getCharacterId() {
      return characterId;
    }

    /**
     * Returns the export name of the character.
     * 
     * @return export name
     */
    public String getName() {
      return name;
    }
  }
}
