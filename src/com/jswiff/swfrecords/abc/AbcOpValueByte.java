package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpValueByte extends AbcOp {
  private byte value;

  public AbcOpValueByte(short opcode, byte value) {
    super(opcode);
    checkOpcode(opcode);
    this.value = value;
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_pushbyte:
      case AbcConstants.Opcodes.OPCODE_getscopeobject:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  AbcOpValueByte(byte value) {
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

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeSI8(value);
  }
}
