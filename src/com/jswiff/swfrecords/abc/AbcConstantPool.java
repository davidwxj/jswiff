package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcConstantPool implements Serializable {
  private int[] ints;
  private int[] uints;
  private double[] doubles;
  String[] strings;
  private AbcNamespace[] namespaces;
  private AbcNamespaceSet[] namespaceSets;
  private AbcMultiname[] multinames;
  
  public static AbcConstantPool read(InputBitStream stream) throws IOException {
    AbcConstantPool pool = new AbcConstantPool();
    // read ints
    int count = stream.readU30();
    pool.ints = new int[count == 0 ? 1 : count];
    pool.ints[0] = 0;
    for (int i = 1; i < count; i++) {
      pool.ints[i] = stream.readS32();
    }
    // read uints
    count =  stream.readU30();
    pool.uints = new int[count == 0 ? 1 : count];
    pool.uints[0] = 0;
    for (int i = 1; i < count; i++) {
      pool.uints[i] = stream.readU30();
    }
    // read doubles
    count =  stream.readU30();
    pool.doubles = new double[count == 0 ? 1 : count];
    pool.doubles[0] = Double.NaN;
    for (int i = 1; i < count; i++) {
      pool.doubles[i] = stream.readDouble();
    }
    // read strings
    count =  stream.readU30();
    pool.strings = new String[count == 0 ? 1 : count];
    pool.strings[0] = "";
    for (int i = 1; i < count; i++) {
      int length = stream.readU30();
      pool.strings[i] = new String(stream.readBytes(length), "UTF-8");
    }
    // read namespaces
    count = stream.readU30();
    pool.namespaces = new AbcNamespace[count == 0 ? 1 : count];
    pool.namespaces[0] = new AbcNamespace(AbcConstants.NamespaceKinds.NAMESPACE, 0); //TODO determine correct ns kind for this
    for (int i = 1; i < count; i++) {
      pool.namespaces[i] = AbcNamespace.read(stream);
    }
    // read namespace sets
    count = stream.readU30();
    pool.namespaceSets = new AbcNamespaceSet[count == 0 ? 1 : count];
    for (int i = 1; i < count; i++) {
      pool.namespaceSets[i] = AbcNamespaceSet.read(stream);
    }
    // read multinames
    count = stream.readU30();
    pool.multinames = new AbcMultiname[count == 0 ? 1 : count];
    for (int i = 1; i < count; i++) {
      pool.multinames[i] = AbcMultiname.read(stream);
    }
    return pool;
  }
}
