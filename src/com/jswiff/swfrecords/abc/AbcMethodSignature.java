package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

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
  
  public void setFlag(short flag) {
    flags |= flag;
  }
  
  public void clearFlags() {
    flags = 0;
  }
  
  public static AbcMethodSignature read(InputBitStream stream) throws IOException {
    AbcMethodSignature sig = new AbcMethodSignature();
    int parameterCount = stream.readAbcInt();
    sig.returnTypeIndex = stream.readAbcInt();
    for (int i = 0; i < parameterCount; i++) {
      sig.parameterTypeIndices.add(stream.readAbcInt());
    }
    sig.nameIndex = stream.readAbcInt();
    sig.flags = stream.readUI8();
    if (sig.isSetFlag(HAS_OPTIONAL_FLAG)) {
      int count = stream.readAbcInt();
      for (int i = 0; i < count; i++) {
        sig.optionalParameters.add(AbcDefaultValue.read(stream));
      }
    }
    if (sig.isSetFlag(HAS_PARAM_NAMES_FLAG)) {
      for (int i = 0; i < parameterCount; i++) {
        sig.parameterNameIndices.add(stream.readAbcInt());
      }
    }
    return sig;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(parameterTypeIndices.size());
    stream.writeAbcInt(returnTypeIndex);
    for (Iterator<Integer> it = parameterTypeIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
    stream.writeAbcInt(nameIndex);
    stream.writeUI8(flags);
    stream.writeAbcInt(optionalParameters.size());
    for (Iterator<AbcDefaultValue> it = optionalParameters.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    for (Iterator<Integer> it = parameterNameIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
  }
}
