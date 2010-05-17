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

package com.jswiff.swfrecords.abc.opcode;

import com.jswiff.constants.AbcConstants.OpCode;
import com.jswiff.constants.AbcConstants.OpCodeType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;

public abstract class AbcOp implements Serializable {

  private static final long serialVersionUID = 1L;
  
  public final OpCode opCode;

  public AbcOp(OpCode opCode, OpCodeType opCodeType) {
    if (!opCode.getType().equals(opCodeType)) {
      throw new IllegalArgumentException("The provided opCode must be of type '" + opCodeType + "', but opCode '" + opCode + "' with type '" + opCode.getType() + "' was given instead");
    }
    this.opCode = opCode;
  }

  public static AbcOp read(InputBitStream stream) throws IOException {
    short code = stream.readUI8();
    OpCode opCode = OpCode.lookup(code);
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
      throw new AssertionError("Opcode type '" + opCode.getType() + "' not handled!");
    }
    return op;
  }
  
  @Override
  public String toString() {
    String[] bits = this.opCode.name().split("_");
    String niceName = "";
    for (String bit : bits) {
      niceName += bit.substring(0, 1).concat(bit.substring(1).toLowerCase());
    }
    return niceName;
  }

  public abstract void write(OutputBitStream stream) throws IOException;

}
