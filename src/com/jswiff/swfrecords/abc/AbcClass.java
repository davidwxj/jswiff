package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcClass implements Serializable {
  private int initializerIndex;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  
  public static AbcClass read(InputBitStream stream) throws IOException {
    AbcClass cls = new AbcClass();
    cls.initializerIndex = stream.readAbcInt();
    int traitCount = stream.readAbcInt();
    for (int i = 0; i < traitCount; i++) {
      cls.traits.add(AbcTrait.read(stream));
    }
    return cls;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    
  }
}
