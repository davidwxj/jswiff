package com.jswiff.swfrecords.abc;

public class AbcOpValueByte extends AbcOp {
  private byte value;

  public AbcOpValueByte(short opcode, byte value) {
    setOpcode(opcode);
    this.value = value;
  }

  public byte getValue() {
    return value;
  }
  
  public String toString() {
    return getOpName() + " value = " + value;
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_pushbyte:
        opName = "pushbyte";
        break;
      case AbcConstants.Opcodes.OPCODE_getscopeobject:
        opName = "getscopeobject";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }
}
