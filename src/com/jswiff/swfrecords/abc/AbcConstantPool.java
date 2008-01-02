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
    System.out.println("int count: " + count);
    pool.ints = new int[count == 0 ? 1 : count];
    pool.ints[0] = 0;
    for (int i = 1; i < count; i++) {
      pool.ints[i] = stream.readS32();
      System.out.println(pool.ints[i]);
    }
    // read uints
    count =  stream.readU30();
    System.out.println("uint count: " + count);
    pool.uints = new int[count == 0 ? 1 : count];
    pool.uints[0] = 0;
    for (int i = 1; i < count; i++) {
      pool.uints[i] = stream.readU30();
      System.out.println(pool.uints[i]);
    }
    // read doubles
    count =  stream.readU30();
    System.out.println("double count: " + count);
    pool.doubles = new double[count == 0 ? 1 : count];
    pool.doubles[0] = Double.NaN;
    for (int i = 1; i < count; i++) {
      pool.doubles[i] = stream.readDouble();
      System.out.println(pool.doubles[i]);
    }
    // read strings
    count =  stream.readU30();
    System.out.println("string count: " + count);
    pool.strings = new String[count == 0 ? 1 : count];
    pool.strings[0] = "";
    for (int i = 1; i < count; i++) {
      int length = stream.readU30();
      pool.strings[i] = new String(stream.readBytes(length), "UTF-8");
      System.out.println(i + " : " + pool.strings[i]);
    }
    // read namespaces
    count = stream.readU30();
    System.out.println("namespace count: " + count);
    pool.namespaces = new AbcNamespace[count == 0 ? 1 : count];
    pool.namespaces[0] = new AbcNamespace(AbcConstantKinds.Namespace.NAMESPACE, 0); //TODO determine correct ns kind for this
    for (int i = 1; i < count; i++) {
      pool.namespaces[i] = AbcNamespace.read(stream);
    }
    // read namespace sets
    count = stream.readU30();
    System.out.println("namespace set count: " + count);
    pool.namespaceSets = new AbcNamespaceSet[count == 0 ? 1 : count];
    for (int i = 1; i < count; i++) {
      pool.namespaceSets[i] = AbcNamespaceSet.read(stream);
    }
    // read multinames
    count = stream.readU30();
    System.out.println("multiname count: " + count);
    pool.multinames = new AbcMultiname[count == 0 ? 1 : count];
    for (int i = 1; i < count; i++) {
      pool.multinames[i] = AbcMultiname.read(stream);
    }
    return pool;
  }
}
