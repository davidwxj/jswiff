package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcFunctionTrait extends AbcTrait {
  private int slotId;
  private int functionIndex;

  public AbcFunctionTrait(int nameIndex, int slotId, int functionIndex) {
    super(nameIndex);
    this.slotId = slotId;
    this.functionIndex = functionIndex;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(nameIndex);
    int metadataCount = metadataIndices.size();
    int flagsAndKind = metadataCount != 0 ? METADATA_FLAG << 4 : 0;
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(slotId);
    stream.writeAbcInt(functionIndex);  
  }
  
}
