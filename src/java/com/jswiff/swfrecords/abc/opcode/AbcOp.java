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

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.constants.AbcConstants.OpCode;
import com.jswiff.constants.AbcConstants.OpCodeType;
import com.jswiff.exception.UnknownCodeException;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public abstract class AbcOp implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private final OpCode opCode;

  public AbcOp(OpCode opCode, OpCodeType opCodeType) {
    if (!opCode.getType().equals(opCodeType)) {
      throw new IllegalArgumentException("The provided opCode must be of type '" + opCodeType + "', but opCode '" + opCode + "' with type '" + opCode.getType() + "' was given instead");
    }
    this.opCode = opCode;
  }

  public static AbcOp read(InputBitStream stream) throws IOException {
    short code = stream.readUI8();
    OpCode opCode = OpCode.lookupOpCode(code);
    if (opCode == null) throw new UnknownCodeException("Unknown abc byte code operation, opcode = " + code, code);
    AbcOp op;
    switch(opCode.getType()) {
    case INDEX:
      op = new AbcOpIndex(opCode, stream.readAbcInt());
      break;
    case VALUE_INT:
      op = new AbcOpValueInt(opCode, stream.readAbcInt());
      break;
    case VALUE_BYTE:
      op = new AbcOpValueByte(opCode, stream.readSI8());
      break;
    case INDEX_ARGS:
      op = new AbcOpIndexArgs(opCode, stream.readAbcInt(), stream.readAbcInt());
      break;
    case LOOKUP_SWITCH:
      int defaultOffset = stream.readSI24();
      int caseCount = stream.readAbcInt() + 1;
      AbcOpLookupSwitch switchOp = new AbcOpLookupSwitch(defaultOffset);
      for (int i = 0; i < caseCount; i++) {
        switchOp.addCaseOffset(stream.readSI24());
      }
      op = switchOp;
      break;
    case BRANCH:
      op = new AbcOpBranch(opCode, stream.readSI24());
      break;
    case DEBUG:
      op = new AbcOpDebug(stream.readUI8(), stream.readAbcInt(), stream.readUI8(), stream.readAbcInt());
      break;
    case ARGS:
      op = new AbcOpArgs(opCode, stream.readAbcInt());
      break;
    case HAS_NEXT2:
      op = new AbcOpHasNext2(stream.readAbcInt(), stream.readAbcInt());
      break;
    case SIMPLE:
      op = new AbcOpSimple(opCode);
      break;
    default:
      throw new AssertionError("Unknown opcode type '" + opCode.getType() + "'");
    }
    return op;
  }

  public OpCode getOpcode() {
    return this.opCode;
  }

  public abstract void write(OutputBitStream stream) throws IOException;

}
