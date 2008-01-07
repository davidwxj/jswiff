package com.jswiff.swfrecords.abc;

public class AbcOpDebug extends AbcOp {
  private short type;
  private int registerNameIndex;
  private short register;
  private int extra;

  public AbcOpDebug(short type, int regNameIndex, short regIndex, int extra) {
    setOpcode(AbcConstants.Opcodes.OPCODE_debug);
    this.type = type;
    this.registerNameIndex = regNameIndex;
    this.register = regIndex;
    this.extra = extra;
  }

  public short getType() {
    return type;
  }

  public int getRegisterNameIndex() {
    return registerNameIndex;
  }

  public short getRegister() {
    return register;
  }

  public int getExtra() {
    return extra;
  }
  
  public String toString() {
    return "debug registerNameIndex = " + registerNameIndex + " register = " + register + " extra = " + extra;
  }

  public String getOpName() {
    return "debug";
  }
  
}
