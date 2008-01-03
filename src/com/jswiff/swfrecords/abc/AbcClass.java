package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcClass implements Serializable {
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
