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

package com.jswiff.swfrecords.abc.opcode;

import com.jswiff.constants.AbcConstants.OpCode;
import com.jswiff.constants.AbcConstants.OpCodeType;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

public class AbcOpIndexArgs extends AbcOp {

  private static final long serialVersionUID = 1L;
  
  private int index;
  private int argCount;

  public AbcOpIndexArgs(OpCode opCode, int index, int argCount) {
    super(opCode, OpCodeType.INDEX_ARGS);
    this.index = index;
    this.argCount = argCount;
  }

  public int getIndex() {
    return index;
  }

  public int getArgCount() {
    return argCount;
  }

  public String toString() {
    return super.toString() + ": index = " + index + ", argCount = " + argCount;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opCode.getCode());
    stream.writeAbcInt(index);
    stream.writeAbcInt(argCount);
  }
  
}
