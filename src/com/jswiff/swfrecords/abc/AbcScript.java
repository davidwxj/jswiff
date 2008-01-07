package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcScript implements Serializable {
  private int initializerIndex;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  
  public static AbcScript read(InputBitStream stream) throws IOException {
    AbcScript script = new AbcScript();
    script.initializerIndex = stream.readU30();
    int traitCount = stream.readU30();
    for (int i = 0; i < traitCount; i++) {
      script.traits.add(AbcTrait.read(stream));
    }
    return script;
  }
}
