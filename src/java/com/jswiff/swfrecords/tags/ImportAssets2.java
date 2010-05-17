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
