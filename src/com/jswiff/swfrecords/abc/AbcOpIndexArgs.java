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

public class AbcOpIndexArgs extends AbcOp {
  private int index;
  private int argCount;

  public AbcOpIndexArgs(short opcode, int index, int argCount) {
    super(opcode);
    checkOpcode(opcode);
    this.index = index;
    this.argCount = argCount;
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_constructprop:
      case AbcConstants.Opcodes.OPCODE_callproperty:
      case AbcConstants.Opcodes.OPCODE_callproplex:
      case AbcConstants.Opcodes.OPCODE_callsuper:
      case AbcConstants.Opcodes.OPCODE_callsupervoid:
      case AbcConstants.Opcodes.OPCODE_callpropvoid:
      case AbcConstants.Opcodes.OPCODE_callstatic:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  public AbcOpIndexArgs(int index, int argCount) {
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
    return getOpName() + " index = " + index + " argCount = " + argCount;
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_constructprop:
        opName = "constructprop";
        break;
      case AbcConstants.Opcodes.OPCODE_callproperty:
        opName = "callproperty";
        break;
      case AbcConstants.Opcodes.OPCODE_callproplex:
        opName = "callproplex";
        break;
      case AbcConstants.Opcodes.OPCODE_callsuper:
        opName = "callsuper";
        break;
      case AbcConstants.Opcodes.OPCODE_callsupervoid:
        opName = "callsupervoid";
        break;
      case AbcConstants.Opcodes.OPCODE_callpropvoid:
        opName = "callpropvoid";
        break;
      case AbcConstants.Opcodes.OPCODE_callstatic:
        opName = "callstatic";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeAbcInt(index);
    stream.writeAbcInt(argCount);
  }
}
