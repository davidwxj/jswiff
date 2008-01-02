package com.jswiff.swfrecords.abc;


public class AbcClassTrait extends AbcTrait {
  private boolean isConst;
  private int slotId;
  private int classIndex; 
  
  public AbcClassTrait(int nameIndex, int slotId, int classIndex) {
    super(nameIndex);
    this.slotId = slotId;
    this.classIndex = classIndex;
  }
  
}
