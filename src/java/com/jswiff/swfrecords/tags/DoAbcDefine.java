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

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.abc.AbcFile;

import java.io.IOException;

/**
 * Defines ActionScript 3 code in ABC (ActionScript byte code) format.
 * 
 * @since SWF 9
 */
public final class DoAbcDefine extends Tag {

  private static final long serialVersionUID = 1L;

  private String abcName;
  private AbcFile abcFile;

  /**
   * Creates a new DoABCDefine instance.
   */
  public DoAbcDefine(String abcName) {
    super(TagType.DO_ABC_DEFINE);
    this.abcName = abcName;
  }

  DoAbcDefine() {
    super(TagType.DO_ABC_DEFINE);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    if (abcName == null) {
      throw new IllegalStateException("abcName not set");
    }
    if (abcFile == null) {
      throw new IllegalStateException("abcFile not set");
    }
    
    this.setForceLongHeader(true);
    outStream.writeBytes(new byte[] { 1, 0, 0, 0 });
    outStream.writeString(abcName);
    abcFile.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    inStream.readBytes(4);
    abcName = inStream.readString();
    abcFile = new AbcFile();
    abcFile.read(inStream);
  }

  public String getAbcName() {
    return abcName;
  }
  
  public void setAbcName(String abcName) {
    this.abcName = abcName;
  }

  public AbcFile getAbcFile() {
    return abcFile;
  }
  
  public void setAbcFile(AbcFile abcFile) {
    this.abcFile = abcFile;
  }
}
