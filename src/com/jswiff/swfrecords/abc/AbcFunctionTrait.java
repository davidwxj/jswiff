package com.jswiff.swfrecords.abc;


public class AbcFunctionTrait extends AbcTrait {
  private int slotId;
  private int functionIndex;

  public AbcFunctionTrait(int nameIndex, int slotId, int functionIndex) {
    super(nameIndex);
    this.slotId = slotId;
    this.functionIndex = functionIndex;
  }
  
}
