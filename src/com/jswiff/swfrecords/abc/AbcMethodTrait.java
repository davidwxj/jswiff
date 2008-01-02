package com.jswiff.swfrecords.abc;

public class AbcMethodTrait extends AbcTrait {
  private int dispId;
  private int methodIndex;
  private boolean isSetter;
  private boolean isGetter;
  private boolean isFinal;
  private boolean isOverride;
  
  public AbcMethodTrait(int nameIndex, int dispId, int methodIndex, boolean isGetter, boolean isSetter, boolean isFinal, boolean isOverride) {
    super(nameIndex);
    this.dispId = dispId;
    this.methodIndex = methodIndex;
    this.isGetter = isGetter;
    this.isSetter = isSetter;
    if (isSetter && isGetter) {
      throw new IllegalArgumentException("Method trait cannot be both getter and setter");
    }
    this.isFinal = isFinal;
    this.isOverride = isOverride;
  }
}
