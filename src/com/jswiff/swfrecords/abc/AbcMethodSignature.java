package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcMethodSignature implements Serializable {
  public static final short NEED_ARGUMENTS_FLAG = 0x01;
  public static final short NEED_ACTIVATION_FLAG = 0x02;
  public static final short NEED_REST_FLAG = 0x04;
  public static final short HAS_OPTIONAL_FLAG = 0x08;
  public static final short SET_DXNS_FLAG = 0x40;
  public static final short HAS_PARAM_NAMES_FLAG = 0x80;
  
  private int returnTypeIndex;
  private List<Integer> parameterTypeIndices = new ArrayList<Integer>();
  private int nameIndex;
  private short flags;
  private List<AbcDefaultValue> optionalParameters = new ArrayList<AbcDefaultValue>();
  private List<Integer> parameterNameIndices = new ArrayList<Integer>();

  public boolean isSetFlag(short flag) {
    return ((flags & flag) != 0);
  }
  
  public static AbcMethodSignature read(InputBitStream stream) throws IOException {
    AbcMethodSignature sig = new AbcMethodSignature();
    int parameterCount = stream.readU30();
    sig.returnTypeIndex = stream.readU30();
    for (int i = 0; i < parameterCount; i++) {
      sig.parameterTypeIndices.add(stream.readU30());
    }
    sig.nameIndex = stream.readU30();
    sig.flags = stream.readUI8();
    if (sig.isSetFlag(HAS_OPTIONAL_FLAG)) {
      int count = stream.readU30();
      for (int i = 0; i < count; i++) {
        sig.optionalParameters.add(AbcDefaultValue.read(stream));
      }
    }
    if (sig.isSetFlag(HAS_PARAM_NAMES_FLAG)) {
      for (int i = 0; i < parameterCount; i++) {
        sig.parameterNameIndices.add(stream.readU30());
      }
    }
    return sig;
  }
}
