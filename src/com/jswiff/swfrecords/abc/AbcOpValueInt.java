package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpValueInt extends AbcOp {
  private int value;

  public AbcOpValueInt(short opcode, int value) {
    super(opcode);
    checkOpcode(opcode);
    this.value = value;
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_debugline:
      case AbcConstants.Opcodes.OPCODE_pushshort:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  AbcOpValueInt(int value) {
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

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeAbcInt(value);
  }
}
