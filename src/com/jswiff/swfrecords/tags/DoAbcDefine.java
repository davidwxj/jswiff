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
import com.jswiff.util.HexUtils;


/**
 * 
 *
 * @since SWF 9
 */
public final class DoAbcDefine extends Tag {
  private String abcName;
  private AbcFile abcFile;
  
  /**
   * Creates a new DoABCDefine instance.
   */
  public DoAbcDefine() {
    code = TagConstants.DO_ABC_DEFINE;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    forceLongHeader = true;
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    inStream.readBytes(4);
    abcName = inStream.readString();
    abcFile = AbcFile.read(inStream);
  }
}
