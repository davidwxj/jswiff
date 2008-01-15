package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpDebug extends AbcOp {
  private short type;
  private int registerNameIndex;
  private short register;
  private int extra;

  public AbcOpDebug(short type, int registerNameIndex, short register, int extra) {
    super(AbcConstants.Opcodes.OPCODE_debug);
    this.type = type;
    this.registerNameIndex = registerNameIndex;
    this.register = register;
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

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeUI8(type);
    stream.writeAbcInt(registerNameIndex);
    stream.writeUI8(register);
    stream.writeAbcInt(extra);
  }
  
}
