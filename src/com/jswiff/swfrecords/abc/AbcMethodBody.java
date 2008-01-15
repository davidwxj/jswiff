package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcMethodBody implements Serializable {
  private int signatureIndex;
  private int maxStack;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  private int localCount;
  private int initScopeDepth;
  private int maxScopeDepth;
  private List<AbcOp> abcCode = new ArrayList<AbcOp>();
  private List<AbcException> exceptions = new ArrayList<AbcException>();
  
  public static AbcMethodBody read(InputBitStream stream) throws IOException {
    AbcMethodBody method = new AbcMethodBody();
    method.signatureIndex = stream.readAbcInt();
    method.maxStack = stream.readAbcInt();
    method.localCount = stream.readAbcInt();
    method.initScopeDepth = stream.readAbcInt();
    method.maxScopeDepth = stream.readAbcInt();
    int codeLength = stream.readAbcInt();
    byte[] byteCode = stream.readBytes(codeLength);
    InputBitStream byteCodeStream = new InputBitStream(byteCode);
    while (byteCodeStream.available() > 0) {
      method.abcCode.add(AbcOp.read(byteCodeStream));
    }
    int exceptionCount = stream.readAbcInt();
    for (int i = 0; i < exceptionCount; i++) {
      method.exceptions.add(AbcException.read(stream));
    }
    int traitCount = stream.readAbcInt();
    for (int i = 0; i < traitCount; i++) {
      method.traits.add(AbcTrait.read(stream));
    }
    return method;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(signatureIndex);
    stream.writeAbcInt(maxStack);
    stream.writeAbcInt(localCount);
    stream.writeAbcInt(initScopeDepth);
    stream.writeAbcInt(maxScopeDepth);
    OutputBitStream byteCodeStream = new OutputBitStream();
    for (Iterator<AbcOp> it = abcCode.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    byte[] byteCode = byteCodeStream.getData();
    stream.writeAbcInt(byteCode.length);
    stream.writeBytes(byteCode);
    stream.writeAbcInt(exceptions.size());
    for (Iterator<AbcException> it = exceptions.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(traits.size());
    for (Iterator<AbcTrait> it = traits.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
  }
}
