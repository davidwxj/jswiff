package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

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
    int count = stream.readU30();
    for (int i = 1; i < count; i++) {
      pool.ints.add(stream.readS32());
    }
    // read uints
    count =  stream.readU30();
    for (int i = 1; i < count; i++) {
      pool.uints.add(stream.readU30());
    }
    // read doubles
    count =  stream.readU30();
    for (int i = 1; i < count; i++) {
      pool.doubles.add(stream.readDouble());
    }
    // read strings
    count =  stream.readU30();
    for (int i = 1; i < count; i++) {
      int length = stream.readU30();
      pool.strings.add(new String(stream.readBytes(length), "UTF-8"));
    }
    // read namespaces
    count = stream.readU30();
    for (int i = 1; i < count; i++) {
      pool.namespaces.add(AbcNamespace.read(stream));
    }
    // read namespace sets
    count = stream.readU30();
    for (int i = 1; i < count; i++) {
      pool.namespaceSets.add(AbcNamespaceSet.read(stream));
    }
    // read multinames
    count = stream.readU30();
    for (int i = 1; i < count; i++) {
      pool.multinames.add(AbcMultiname.read(stream));
    }
    return pool;
  }
}
