package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcMethodBody implements Serializable {
  private int signatureIndex;
  private int maxStack;
  private AbcTrait[] traits;
  private int localCount;
  private int initScopeDepth;
  private int maxScopeDepth;
  private AbcOp code;
  private AbcException[] exceptions;
  
  public static AbcMethodBody read(InputBitStream stream) throws IOException {
    AbcMethodBody method = new AbcMethodBody();
    method.signatureIndex = stream.readU30();
    method.maxStack = stream.readU30();
    method.localCount = stream.readU30();
    method.initScopeDepth = stream.readU30();
    method.maxScopeDepth = stream.readU30();
    int codeLength = stream.readU30();
    byte[] byteCode = stream.readBytes(codeLength);
    InputBitStream byteCodeStream = new InputBitStream(byteCode);
    List operations = new ArrayList();
    while (byteCodeStream.available() > 0) {
      operations.add(AbcOp.read(byteCodeStream));
    }
    int exceptionCount = stream.readU30();
    method.exceptions = new AbcException[exceptionCount];
    for (int i = 0; i < exceptionCount; i++) {
      method.exceptions[i] = AbcException.read(stream);
    }
    int traitCount = stream.readU30();
    method.traits = new AbcTrait[traitCount];
    for (int i = 0; i < traitCount; i++) {
      method.traits[i] = AbcTrait.read(stream);
    }
    return method;
  }
}
