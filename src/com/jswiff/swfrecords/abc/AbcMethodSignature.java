package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcMethodSignature implements Serializable {
  public static final short NEED_ARGUMENTS_FLAG = 0x01;
  public static final short NEED_ACTIVATION_FLAG = 0x02;
  public static final short NEED_REST_FLAG = 0x04;
  public static final short HAS_OPTIONAL_FLAG = 0x08;
  public static final short SET_DXNS_FLAG = 0x40;
  public static final short HAS_PARAM_NAMES_FLAG = 0x80;
  
  private int returnTypeIndex;
  private int[] parameterTypeIndices;
  private int nameIndex;
  private short flags;
  private AbcDefaultValue[] optionalParameters;
  private int[] parameterNameIndices;

  public boolean isSetFlag(short flag) {
    return ((flags & flag) != 0);
  }
  
  public static AbcMethodSignature read(InputBitStream stream) throws IOException {
    AbcMethodSignature sig = new AbcMethodSignature();
    int parameterCount = stream.readU30();
    sig.returnTypeIndex = stream.readU30();
    sig.parameterTypeIndices = new int[parameterCount];
    for (int i = 0; i < parameterCount; i++) {
      sig.parameterTypeIndices[i] = stream.readU30();
    }
    sig.nameIndex = stream.readU30();
    sig.flags = stream.readUI8();
    if (sig.isSetFlag(HAS_OPTIONAL_FLAG)) {
      int count = stream.readU30();
      sig.optionalParameters = new AbcDefaultValue[count];
      for (int i = 0; i < count; i++) {
        sig.optionalParameters[i] = AbcDefaultValue.read(stream);
      }
    }
    if (sig.isSetFlag(HAS_PARAM_NAMES_FLAG)) {
      sig.parameterNameIndices = new int[parameterCount];
      for (int i = 0; i < parameterCount; i++) {
        sig.parameterNameIndices[i] = stream.readU30();
      }
    }
    return sig;
  }
}
