package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcDefaultValue implements Serializable {
  private short kind;
  private int valueIndex;
  
  public static AbcDefaultValue read(InputBitStream stream) throws IOException {
    AbcDefaultValue val = new AbcDefaultValue();
    val.valueIndex = stream.readU30();
    val.kind = stream.readUI8();
    return val;
  }
}
