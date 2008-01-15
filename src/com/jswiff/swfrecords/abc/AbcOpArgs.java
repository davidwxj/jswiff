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

  AbcOpArgs(int argCount) {
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
