package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcMethodBody implements Serializable {
  private int signatureIndex;
  private int maxStack;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  private int localCount;
  private int initScopeDepth;
  private int maxScopeDepth;
  private AbcOp code;
  private List<AbcException> exceptions = new ArrayList<AbcException>();
  
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
    for (int i = 0; i < exceptionCount; i++) {
      method.exceptions.add(AbcException.read(stream));
    }
    int traitCount = stream.readU30();
    for (int i = 0; i < traitCount; i++) {
      method.traits.add(AbcTrait.read(stream));
    }
    return method;
  }
}
