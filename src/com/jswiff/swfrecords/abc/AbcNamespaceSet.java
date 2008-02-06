package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcNamespaceSet implements Serializable {
  private List<Integer> namespaceIndices = new ArrayList<Integer>(); // points to string constant

  public static AbcNamespaceSet read(InputBitStream stream) throws IOException {
    AbcNamespaceSet set = new AbcNamespaceSet();
    int count = stream.readAbcInt();
    for (int i = 0; i < count; i++) {
      set.namespaceIndices.add(stream.readAbcInt());
    }
    return set;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(namespaceIndices.size());
    for (Iterator<Integer> it = namespaceIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
  }

  public List<Integer> getNamespaceIndices() {
    return namespaceIndices;
  }
}
