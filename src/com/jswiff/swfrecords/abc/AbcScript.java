package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcScript implements Serializable {
  private int initializerIndex;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  
  public static AbcScript read(InputBitStream stream) throws IOException {
    AbcScript script = new AbcScript();
    script.initializerIndex = stream.readAbcInt();
    int traitCount = stream.readAbcInt();
    for (int i = 0; i < traitCount; i++) {
      script.traits.add(AbcTrait.read(stream));
    }
    return script;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(initializerIndex);
    stream.writeAbcInt(traits.size());
    for (Iterator<AbcTrait> it = traits.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
  }
}
