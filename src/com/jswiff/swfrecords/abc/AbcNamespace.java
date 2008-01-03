package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcNamespace implements Serializable {
  private short type; // one of the constants in AbcTypes.Namespace
  private int nameIndex; // points to string constant

  public AbcNamespace(short type, int nameIndex) {
    this.type = type;
    this.nameIndex = nameIndex;
  }

  public static AbcNamespace read(InputBitStream stream) throws IOException {
    AbcNamespace ns = new AbcNamespace(stream.readUI8(), stream.readU30());
    return ns;
  }
}
