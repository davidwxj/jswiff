package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.OutputBitStream;

public class AbcOpLookupSwitch extends AbcOp {
  private int defaultOffset;
  private List<Integer> caseOffsets = new ArrayList<Integer>();

  public AbcOpLookupSwitch(int defaultOffset) {
    super(AbcConstants.Opcodes.OPCODE_lookupswitch);
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

  public String getOpName() {
    return "lookupswitch";
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeSI24(defaultOffset);
    stream.writeAbcInt(caseOffsets.size() - 1);
    for (Iterator<Integer> it = caseOffsets.iterator(); it.hasNext(); ) {
      stream.writeSI24(it.next());
    }
  }
   
}
