/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
 */

package com.jswiff.swfrecords.abc;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.abc.opcode.AbcOp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbcMethodBody implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private int signatureIndex;
  private int maxStack;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  private int localCount;
  private int initScopeDepth;
  private int maxScopeDepth;
  private List<AbcOp> abcCode = new ArrayList<AbcOp>();
  private List<AbcException> exceptions = new ArrayList<AbcException>();
  
  private AbcMethodBody() { } // empty
  
  public AbcMethodBody(int signatureIndex, int maxStack, int localCount,
      int initScopeDepth, int maxScopeDepth) {
    this.signatureIndex = signatureIndex;
    this.maxStack = maxStack;
    this.localCount = localCount;
    this.initScopeDepth = initScopeDepth;
    this.maxScopeDepth = maxScopeDepth;
  }

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
      it.next().write(byteCodeStream);
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

  public int getSignatureIndex() {
    return signatureIndex;
  }

  public int getMaxStack() {
    return maxStack;
  }

  public List<AbcTrait> getTraits() {
    return traits;
  }

  public int getLocalCount() {
    return localCount;
  }

  public int getInitScopeDepth() {
    return initScopeDepth;
  }

  public int getMaxScopeDepth() {
    return maxScopeDepth;
  }

  public List<AbcOp> getAbcCode() {
    return abcCode;
  }

  public List<AbcException> getExceptions() {
    return exceptions;
  }
}
