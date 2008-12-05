/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.abc.opcode.AbcOp;

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
