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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.abc.AbcFile;


/**
 * Defines ActionScript 3 code in ABC (ActionScript byte code) format.
 *
 * @since SWF 9
 */
public final class DoAbc extends Tag {
  private AbcFile abcFile;
  
  /**
   * Creates a new DoABC instance.
   */
  public DoAbc() {
    code = TagConstants.DO_ABC;
    abcFile = new AbcFile();
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    forceLongHeader = true;
    abcFile.write(outStream);
    //FIXME: Do we need this?
    //OutputBitStream s = new OutputBitStream();
    //abcFile.write(s);
    //byte[] buf = s.getData();
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    abcFile = AbcFile.read(inStream);
  }

  public AbcFile getAbcFile() {
    return abcFile;
  }
}
