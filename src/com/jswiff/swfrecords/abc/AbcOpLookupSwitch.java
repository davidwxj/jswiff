package com.jswiff.swfrecords.abc;

import java.util.ArrayList;
import java.util.List;

public class AbcOpLookupSwitch extends AbcOp {
  private int defaultOffset;
  private List<Integer> caseOffsets = new ArrayList<Integer>();

  public AbcOpLookupSwitch(int defaultOffset) {
    setOpcode(AbcConstants.Opcodes.OPCODE_lookupswitch);
    this.defaultOffset = defaultOffset;
  }

  public int getDefaultOffset() {
    return defaultOffset;
  }

  public List<Integer> getCaseOffsets() {
    return caseOffsets;
  }
  
  public void addCaseOffset(int caseOffset) {
    caseOffsets.add(caseOffset);
  }

  public String toString() {
    String result = "lookupswitch defaultOffset = " + defaultOffset + " caseOffsets = [ ";
    for (int i = 0; i < caseOffsets.size(); i++) {
      result += caseOffsets.get(i) + " ";
    }
    result += "]";
    return result;
  }
   
}
