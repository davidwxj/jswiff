package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public abstract class AbcTrait implements Serializable {
  protected static final int TYPE_SLOT = 0x00;
  protected static final int TYPE_METHOD = 0x01;
  protected static final int TYPE_GETTER = 0x02;
  protected static final int TYPE_SETTER = 0x03;
  protected static final int TYPE_CLASS = 0x04;
  protected static final int TYPE_FUNCTION = 0x05;
  protected static final int TYPE_CONST = 0x06;
  protected static final int FINAL_FLAG = 0x01;
  protected static final int OVERRIDE_FLAG = 0x02;
  protected static final int METADATA_FLAG = 0x04;
  protected int nameIndex;
  protected List<Integer> metadataIndices = new ArrayList<Integer>();
  
  protected AbcTrait(int nameIndex) {
    this.nameIndex = nameIndex;
  }
  
  public static AbcTrait read(InputBitStream stream) throws IOException {
    int nameIndex = stream.readAbcInt();
    short flagsAndKind = stream.readUI8();
    int flags = flagsAndKind >> 4;
    int kind = flagsAndKind & 0x0f;
    AbcTrait trait;
    switch (kind) {
      case TYPE_SLOT:
      case TYPE_CONST:
        int slotId = stream.readAbcInt();
        int typeIndex = stream.readAbcInt();
        int valueIndex = stream.readAbcInt();
        short valueKind = valueIndex == 0 ? 0 : stream.readUI8();
        AbcSlotTrait slotTrait = new AbcSlotTrait(nameIndex, slotId, typeIndex, valueIndex, valueKind, kind == TYPE_CONST);
        trait = slotTrait;
        break;
      case TYPE_METHOD:
      case TYPE_GETTER:
      case TYPE_SETTER:
        int dispId = stream.readAbcInt();
        int methodIndex = stream.readAbcInt();
        AbcMethodTrait methodTrait = new AbcMethodTrait(nameIndex, dispId, methodIndex,
            kind == TYPE_GETTER, kind == TYPE_SETTER, (flags & FINAL_FLAG) != 0, (flags & OVERRIDE_FLAG) != 0);
        trait = methodTrait;
        break;
      case TYPE_CLASS:
        slotId = stream.readAbcInt();
        int classIndex = stream.readAbcInt();
        AbcClassTrait classTrait = new AbcClassTrait(nameIndex, slotId, classIndex);
        trait = classTrait;
        break;
      case TYPE_FUNCTION:
        slotId = stream.readAbcInt();
        int functionIndex = stream.readAbcInt();
        AbcFunctionTrait functionTrait = new AbcFunctionTrait(nameIndex, slotId, functionIndex);
        trait = functionTrait;
        break;
      default:
        throw new IOException("Unknown trait type: " + kind);
    }
    if ((flags & METADATA_FLAG) != 0) {
      int metadataCount = stream.readAbcInt();
      for (int i = 0; i < metadataCount; i++) {
        trait.metadataIndices.add(stream.readAbcInt());
      }
    }return trait;
  }

  public abstract void write(OutputBitStream stream) throws IOException;

  public int getNameIndex() {
    return nameIndex;
  }

  public List<Integer> getMetadataIndices() {
    return metadataIndices;
  }
}
