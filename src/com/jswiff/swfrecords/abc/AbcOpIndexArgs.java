package com.jswiff.swfrecords.abc;

public class AbcOpIndexArgs extends AbcOp {
  private int index;
  private int argCount;

  public AbcOpIndexArgs(short opcode, int index, int argCount) {
    setOpcode(opcode);
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
}
