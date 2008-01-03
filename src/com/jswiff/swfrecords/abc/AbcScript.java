package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcScript implements Serializable {
  private int initializerIndex;
  private AbcTrait[] traits;
  
  public static AbcScript read(InputBitStream stream) throws IOException {
    AbcScript script = new AbcScript();
    script.initializerIndex = stream.readU30();
    int traitCount = stream.readU30();
    script.traits = new AbcTrait[traitCount];
    for (int i = 0; i < traitCount; i++) {
      script.traits[i] = AbcTrait.read(stream);
    }
    return script;
  }
}
