package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpHasNext2 extends AbcOp {
  private int objectRegister;
  private int propertyIndexRegister;

  public AbcOpHasNext2(int objectRegister, int propertyIndexRegister) {
    super(AbcConstants.Opcodes.OPCODE_hasnext2);
    this.objectRegister = objectRegister;
    this.propertyIndexRegister = propertyIndexRegister;
  }

  public int getObjectRegister() {
    return objectRegister;
  }

  public int getPropertyIndexRegister() {
    return propertyIndexRegister;
  }
  
  public String toString() {
    return "hasnext2 objectRegister = " + objectRegister + " propertyIndexRegister = " + propertyIndexRegister;
  }

  public String getOpName() {
    return "hasnext2";
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeAbcInt(objectRegister);
    stream.writeAbcInt(propertyIndexRegister);
  }
}
