package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public abstract class AbcTrait implements Serializable {
  private static final int TYPE_SLOT = 0x00;
  private static final int TYPE_METHOD = 0x01;
  private static final int TYPE_GETTER = 0x02;
  private static final int TYPE_SETTER = 0x03;
  private static final int TYPE_CLASS = 0x04;
  private static final int TYPE_FUNCTION = 0x05;
  private static final int TYPE_CONST = 0x06;
  private static final int FINAL_FLAG = 0x01;
  private static final int OVERRIDE_FLAG = 0x02;
  private static final int METADATA_FLAG = 0x04;
  private int nameIndex;
  private List<Integer> metadataIndices = new ArrayList<Integer>();
  
  public AbcTrait(int nameIndex) {
    this.nameIndex = nameIndex;
  }
  
  public static AbcTrait read(InputBitStream stream) throws IOException {
    int nameIndex = stream.readU30();
    short flagsAndKind = stream.readUI8();
    int flags = flagsAndKind >> 4;
    int kind = flagsAndKind & 0x0f;
    AbcTrait trait;
    switch (kind) {
      case TYPE_SLOT:
      case TYPE_CONST:
        int slotId = stream.readU30();
        int typeIndex = stream.readU30();
        int valueIndex = stream.readU30();
        short valueKind = valueIndex == 0 ? 0 : stream.readUI8();
        AbcSlotTrait slotTrait = new AbcSlotTrait(nameIndex, slotId, typeIndex, valueIndex, valueKind, kind == TYPE_CONST);
        trait = slotTrait;
        break;
      case TYPE_METHOD:
      case TYPE_GETTER:
      case TYPE_SETTER:
        int dispId = stream.readU30();
        int methodIndex = stream.readU30();
        AbcMethodTrait methodTrait = new AbcMethodTrait(nameIndex, dispId, methodIndex,
            kind == TYPE_GETTER, kind == TYPE_SETTER, (flags & FINAL_FLAG) != 0, (flags & OVERRIDE_FLAG) != 0);
        trait = methodTrait;
        break;
      case TYPE_CLASS:
        slotId = stream.readU30();
        int classIndex = stream.readU30();
        AbcClassTrait classTrait = new AbcClassTrait(nameIndex, slotId, classIndex);
        trait = classTrait;
        break;
      case TYPE_FUNCTION:
        slotId = stream.readU30();
        int functionIndex = stream.readU30();
        AbcFunctionTrait functionTrait = new AbcFunctionTrait(nameIndex, slotId, functionIndex);
        trait = functionTrait;
        break;
      default:
        throw new IOException("Unknown trait type: " + kind);
    }
    if ((flags & METADATA_FLAG) != 0) {
      int metadataCount = stream.readU30();
      for (int i = 0; i < metadataCount; i++) {
        trait.metadataIndices.add(stream.readU30());
      }
    }
    return trait;
  }
}
