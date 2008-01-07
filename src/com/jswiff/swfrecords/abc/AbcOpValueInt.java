package com.jswiff.swfrecords.abc;

public class AbcOpValueInt extends AbcOp {
  private int value;

  public AbcOpValueInt(short opcode, int value) {
    setOpcode(opcode);
    this.value = value;
  }

  public int getValue() {
    return value;
  }
  
  public String toString() {
    return getOpName() + " value = " + value;
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_debugline:
        opName = "debugline";
        break;
      case AbcConstants.Opcodes.OPCODE_pushshort:
        opName = "pushshort";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }
}
