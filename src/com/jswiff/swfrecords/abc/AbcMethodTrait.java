package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.util.Iterator;

import com.jswiff.io.OutputBitStream;

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

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(nameIndex);
    int metadataCount = metadataIndices.size();
    int flagsAndKind = metadataCount != 0 ? METADATA_FLAG : 0;
    if (isFinal) {
      flagsAndKind |= FINAL_FLAG;
    }
    if (isOverride) {
      flagsAndKind |= OVERRIDE_FLAG;
    }
    flagsAndKind <<= 4;
    if (isGetter) {
      flagsAndKind |= TYPE_GETTER;
    } else if (isSetter) {
      flagsAndKind |= TYPE_SETTER;
    } else {
      flagsAndKind |= TYPE_METHOD;
    }
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(dispId);
    stream.writeAbcInt(methodIndex);
    if (metadataCount != 0) {
      stream.writeAbcInt(metadataCount);
      for (Iterator<Integer> it = metadataIndices.iterator(); it.hasNext(); ) {
        stream.writeAbcInt(it.next());
      }
    }
  }
}
