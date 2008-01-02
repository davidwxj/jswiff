package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcClass implements Serializable {
  public static final short SEALED_FLAG = 0x01;
  public static final short FINAL_FLAG = 0x02;
  public static final short INTERFACE_FLAG = 0x04;
  public static final short PROTECTED_NS_FLAG = 0x08;
  
  private int initializerIndex;
  private AbcTrait[] traits;
  
  public static AbcClass read(InputBitStream stream) throws IOException {
    AbcClass cls = new AbcClass();
    cls.initializerIndex = stream.readU30();
    int traitCount = stream.readU30();
    cls.traits = new AbcTrait[traitCount];
    for (int i = 0; i < traitCount; i++) {
      cls.traits[i] = AbcTrait.read(stream);
    }
    return cls;
  }
}
