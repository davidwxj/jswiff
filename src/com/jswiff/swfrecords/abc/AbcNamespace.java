package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcNamespace implements Serializable {
  public static final short PRIVATE_NAMESPACE = 0x05;
  public static final short NAMESPACE = 0x08;
  public static final short PACKAGE_NAMESPACE = 0x16;
  public static final short PACKAGE_INTERNAL_NAMESPACE = 0x17;
  public static final short PROTECTED_NAMESPACE = 0x18;
  public static final short EXPLICIT_NAMESPACE = 0x19;
  public static final short STATIC_PROTECTED_NAMESPACE = 0x1A;

  private short kind; // one of the constants above
  private int nameIndex; // points to string constant

  public AbcNamespace(short kind, int nameIndex) {
    this.kind = kind;
    this.nameIndex = nameIndex;
  }

  public static AbcNamespace read(InputBitStream stream) throws IOException {
    AbcNamespace ns = new AbcNamespace(stream.readUI8(), stream.readU30());
    System.out.println("kind: " + ns.kind + " name: " + ns.nameIndex);
    return ns;
  }
}
