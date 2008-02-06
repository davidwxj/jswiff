package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.util.Iterator;

import com.jswiff.io.OutputBitStream;

public class AbcClassTrait extends AbcTrait {
  private int slotId;
  private int classIndex; 
  
  public AbcClassTrait(int nameIndex, int slotId, int classIndex) {
    super(nameIndex);
    this.slotId = slotId;
    this.classIndex = classIndex;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(nameIndex);
    int metadataCount = metadataIndices.size();
    int flagsAndKind = (metadataCount != 0 ? METADATA_FLAG << 4 : 0) | TYPE_CLASS;
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(slotId);
    stream.writeAbcInt(classIndex);
    if (metadataCount != 0) {
      stream.writeAbcInt(metadataCount);
      for (Iterator<Integer> it = metadataIndices.iterator(); it.hasNext(); ) {
        stream.writeAbcInt(it.next());
      }
    }
  }

  public int getSlotId() {
    return slotId;
  }

  public int getClassIndex() {
    return classIndex;
  }
}
