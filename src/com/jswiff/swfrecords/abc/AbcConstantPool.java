package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcConstantPool implements Serializable {
  private List<Integer> ints = new ArrayList<Integer>();
  private List<Integer> uints = new ArrayList<Integer>();
  private List<Double> doubles = new ArrayList<Double>();
  private List<String> strings = new ArrayList<String>();
  private List<AbcNamespace> namespaces = new ArrayList<AbcNamespace>();
  private List<AbcNamespaceSet> namespaceSets = new ArrayList<AbcNamespaceSet>();
  private List<AbcMultiname> multinames = new ArrayList<AbcMultiname>();
  
  public AbcConstantPool() {
    ints.add(0);
    uints.add(0);
    doubles.add(Double.NaN);
    strings.add("");
    namespaces.add(new AbcNamespace(AbcConstants.NamespaceKinds.NAMESPACE, 0)); //TODO determine correct ns kind for this
    namespaceSets.add(null);
    multinames.add(null);
  }
  
  public static AbcConstantPool read(InputBitStream stream) throws IOException {
    AbcConstantPool pool = new AbcConstantPool();
    // read ints
    int count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.ints.add(stream.readAbcInt());
    }
    // read uints
    count =  stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.uints.add(stream.readAbcInt());
    }
    // read doubles
    count =  stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.doubles.add(stream.readDouble());
    }
    // read strings
    count =  stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      int length = stream.readAbcInt();
      pool.strings.add(new String(stream.readBytes(length), "UTF-8"));
    }
    // read namespaces
    count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.namespaces.add(AbcNamespace.read(stream));
    }
    // read namespace sets
    count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.namespaceSets.add(AbcNamespaceSet.read(stream));
    }
    // read multinames
    count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.multinames.add(AbcMultiname.read(stream));
    }
    return pool;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    int count = ints.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeAbcInt(ints.get(i));
      }
    }
    count = uints.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeAbcInt(uints.get(i));
      }
    }
    count = doubles.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeDouble(doubles.get(i));
      }
    }
    count = strings.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeString(strings.get(i));
      }
    }
    count = namespaces.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        namespaces.get(i).write(stream);
      }
    }
    count = namespaceSets.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        namespaceSets.get(i).write(stream);
      }
    }
    count = multinames.size() + 1;
    if (count < 3) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        multinames.get(i).write(stream);
      }
    }
  }
}
