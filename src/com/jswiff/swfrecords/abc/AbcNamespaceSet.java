package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcNamespaceSet implements Serializable {
  private List<Integer> namespaceIndices = new ArrayList<Integer>(); // points to string constant

  public static AbcNamespaceSet read(InputBitStream stream) throws IOException {
    AbcNamespaceSet set = new AbcNamespaceSet();
    int count = stream.readU30();
    for (int i = 0; i < count; i++) {
      set.namespaceIndices.add(stream.readU30());
    }
    return set;
  }
}
