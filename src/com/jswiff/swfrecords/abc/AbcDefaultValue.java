package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcDefaultValue implements Serializable {
  private short kind;
  private int valueIndex;
  
  public static AbcDefaultValue read(InputBitStream stream) throws IOException {
    AbcDefaultValue val = new AbcDefaultValue();
    val.valueIndex = stream.readAbcInt();
    val.kind = stream.readUI8();
    return val;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(valueIndex);
    stream.writeUI8(kind);
  }
}
