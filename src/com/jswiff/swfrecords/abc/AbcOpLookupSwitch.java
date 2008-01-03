package com.jswiff.swfrecords.abc;

public class AbcOpLookupSwitch extends AbcOp {
  private int defaultOffset;
  private int[] caseOffsets;

  public AbcOpLookupSwitch(int defaultOffset, int[] caseOffsets) {
    setOpcode(AbcConstants.Opcodes.OPCODE_lookupswitch);
    this.defaultOffset = defaultOffset;
    this.caseOffsets = caseOffsets;
  }

  public int getDefaultOffset() {
    return defaultOffset;
  }

  public int[] getCaseOffsets() {
    return caseOffsets;
  }

  public String toString() {
    String result = "lookupswitch defaultOffset = " + defaultOffset + " caseOffsets = [ ";
    for (int i = 0; i < caseOffsets.length; i++) {
      result += caseOffsets[i] + " ";
    }
    return result;
  }
   
}
