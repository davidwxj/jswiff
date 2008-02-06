/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.abc.AbcFile;


/**
 * Defines ActionScript 3 code in ABC format.
 *
 * @since SWF 9
 */
public final class DoAbcDefine extends Tag {
  private String abcName;
  private AbcFile abcFile;
  
  /**
   * Creates a new DoABCDefine instance.
   */
  public DoAbcDefine(String abcName) {
    code = TagConstants.DO_ABC_DEFINE;
    this.abcName = abcName;
    abcFile = new AbcFile();
  }
  
  DoAbcDefine() {
    // empty
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    forceLongHeader = true;
    outStream.writeBytes(new byte[] {1, 0, 0, 0});
    outStream.writeString(abcName);
    abcFile.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    inStream.readBytes(4);
    abcName = inStream.readString();
    abcFile = AbcFile.read(inStream);
  }

  public String getAbcName() {
    return abcName;
  }

  public AbcFile getAbcFile() {
    return abcFile;
  }
}
