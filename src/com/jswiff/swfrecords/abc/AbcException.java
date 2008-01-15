package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcException implements Serializable {
  private int from;
  private int to;
  private int target;
  private int typeIndex;
  private int nameIndex;
  
  public static AbcException read(InputBitStream stream) throws IOException {
    AbcException e = new AbcException();
    e.from = stream.readAbcInt();
    e.to = stream.readAbcInt();
    e.target = stream.readAbcInt();
    e.typeIndex = stream.readAbcInt();
    e.nameIndex = stream.readAbcInt();
    return e;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(from);
    stream.writeAbcInt(to);
    stream.writeAbcInt(target);
    stream.writeAbcInt(typeIndex);
    stream.writeAbcInt(nameIndex);
  }
}
