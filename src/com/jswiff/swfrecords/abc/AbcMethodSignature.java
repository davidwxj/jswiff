package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcMethodSignature implements Serializable {
  private int returnTypeIndex;
  private short[] parameterTypes;
  private int nameIndex;

  public static AbcMethodSignature read(InputBitStream stream) throws IOException {
    AbcMethodSignature sig = new AbcMethodSignature();
    int paramCount = stream.readU30();
    sig.returnTypeIndex = stream.readU30();
    sig.parameterTypes = new short[paramCount];
    sig.nameIndex = stream.readU30();
    short flags = stream.readUI8();
    return sig;
  }
}
