package com.jswiff.swfrecords.abc;

public class AbcOpHasNext2 extends AbcOp {
  private int objectRegister;
  private int propertyIndexRegister;

  public AbcOpHasNext2(int objectRegister, int propertyIndexRegister) {
    setOpcode(AbcConstants.Opcodes.OPCODE_hasnext2);
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
}
