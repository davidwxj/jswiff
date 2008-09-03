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

package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpArgs extends AbcOp {
  private int argCount;

  public AbcOpArgs(short opcode, int argCount) {
    super(opcode);
    checkOpcode(opcode);
    this.argCount = argCount;
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_newobject:
      case AbcConstants.Opcodes.OPCODE_newarray:
      case AbcConstants.Opcodes.OPCODE_call:
      case AbcConstants.Opcodes.OPCODE_construct:
      case AbcConstants.Opcodes.OPCODE_constructsuper:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  public AbcOpArgs(int argCount) {
    this.argCount = argCount;
  }

  public int getArgCount() {
    return argCount;
  }

  public String toString() {
    return getOpName() + " argCount = " + argCount;
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_newobject:
        opName = "newobject";
        break;
      case AbcConstants.Opcodes.OPCODE_newarray:
        opName = "newarray";
        break;
      case AbcConstants.Opcodes.OPCODE_call:
        opName = "call";
        break;
      case AbcConstants.Opcodes.OPCODE_construct:
        opName = "construct";
        break;
      case AbcConstants.Opcodes.OPCODE_constructsuper:
        opName = "constructsuper";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeAbcInt(argCount);
  }
}
