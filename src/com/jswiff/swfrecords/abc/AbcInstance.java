package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcInstance implements Serializable {
  public static final short SEALED_FLAG = 0x01;
  public static final short FINAL_FLAG = 0x02;
  public static final short INTERFACE_FLAG = 0x04;
  public static final short PROTECTED_NS_FLAG = 0x08;
  
  private int nameIndex;
  private int supernameIndex;
  private short flags;
  private int protectedNsIndex;
  private int[] interfaceIndices;
  private int initializerIndex;
  private AbcTrait[] traits;
  
  public boolean isSetFlag(short flag) {
    return ((flags & flag) != 0);
  }
  
  public static AbcInstance read(InputBitStream stream) throws IOException {
    AbcInstance inst = new AbcInstance();
    inst.nameIndex = stream.readU30();
    inst.supernameIndex = stream.readU30();
    inst.flags = stream.readUI8();
    if (inst.isSetFlag(PROTECTED_NS_FLAG)) {
      inst.protectedNsIndex = stream.readU30();
    }
    int interfaceCount = stream.readU30();
    inst.interfaceIndices = new int[interfaceCount];
    for (int i = 0; i < interfaceCount; i++) {
      inst.interfaceIndices[i] = stream.readU30();
    }
    inst.initializerIndex = stream.readU30();
    int traitCount = stream.readU30();
    inst.traits = new AbcTrait[traitCount];
    for (int i = 0; i < traitCount; i++) {
      inst.traits[i] = AbcTrait.read(stream);
    }
    return inst;
  }
}
