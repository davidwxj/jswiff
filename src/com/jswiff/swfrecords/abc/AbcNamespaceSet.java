package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcNamespaceSet implements Serializable {
  private int namespaceIndices[]; // points to string constant

  public static AbcNamespaceSet read(InputBitStream stream) throws IOException {
    AbcNamespaceSet set = new AbcNamespaceSet();
    int count = stream.readU30();
    set.namespaceIndices = new int[count];
    for (int i = 0; i < count; i++) {
      set.namespaceIndices[i] = stream.readU30();
    }
    return set;
  }
}
