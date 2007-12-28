package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcDefaultValue implements Serializable {
  private short type;
  private int valueIndex;
  
  public static AbcDefaultValue read(InputBitStream stream) throws IOException {
    AbcDefaultValue val = new AbcDefaultValue();
    val.valueIndex = stream.readU30();
    val.type = stream.readUI8();
    return val;
  }
}
