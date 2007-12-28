package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcException implements Serializable {
  private int from;
  private int to;
  private int target;
  private int typeIndex;
  private int nameIndex;
  
  public static AbcException read(InputBitStream stream) throws IOException {
    AbcException e = new AbcException();
    e.from = stream.readU30();
    e.to = stream.readU30();
    e.target = stream.readU30();
    e.typeIndex = stream.readU30();
    e.nameIndex = stream.readU30();
    return e;
  }
}
