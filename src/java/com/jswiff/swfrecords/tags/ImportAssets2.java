/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

/**
 * TODO: Comments
 */
public class ImportAssets2 extends ImportAssets {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new ImportAssets2 instance.
   * 
   * @param url
   *          TODO: Comments
   * @param importMappings
   *          TODO: Comments
   */
  public ImportAssets2(String url, ImportMapping[] importMappings) {
    super(url, importMappings, TagType.IMPORT_ASSETS_2);
  }

  ImportAssets2() {
    super(TagType.IMPORT_ASSETS_2);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeString(url);
    outStream.writeUI8((short) 1);
    outStream.writeUI8((short) 0);
    int count = importMappings.length;
    outStream.writeUI16(count);
    for (int i = 0; i < count; i++) {
      outStream.writeUI16(importMappings[i].getCharacterId());
      outStream.writeString(importMappings[i].getName());
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    url = inStream.readString();
    inStream.readUI16();
    int count = inStream.readUI16();
    importMappings = new ImportMapping[count];
    for (int i = 0; i < count; i++) {
      int characterId = inStream.readUI16();
      String name = inStream.readString();
      importMappings[i] = new ImportMapping(name, characterId);
    }
  }
}
