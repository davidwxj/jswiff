package com.jswiff.swfrecords.abc;

public class AbcOpArgs extends AbcOp {
  private int argCount;

  public AbcOpArgs(short opcode, int argCount) {
    setOpcode(opcode);
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
}
