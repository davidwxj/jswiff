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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbcInstance implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final short SEALED_FLAG = 0x01;
  public static final short FINAL_FLAG = 0x02;
  public static final short INTERFACE_FLAG = 0x04;
  public static final short PROTECTED_NS_FLAG = 0x08;

  private int nameIndex;
  private int supernameIndex;
  private short flags;
  private int protectedNsIndex;
  private List<Integer> interfaceIndices = new ArrayList<Integer>();
  private int initializerIndex;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();

  private AbcInstance() { } // empty

  public AbcInstance(int nameIndex, int supernameIndex, int initializerIndex) {
    this.nameIndex = nameIndex;
    this.supernameIndex = supernameIndex;
    this.initializerIndex = initializerIndex;
  }

  public boolean isSetFlag(short flag) {
    return ((flags & flag) != 0);
  }

  public void setFlag(short flag) {
    flags |= flag;
  }

  public void clearFlags() {
    flags = 0;
  }

  public static AbcInstance read(InputBitStream stream) throws IOException {
    AbcInstance inst = new AbcInstance();
    inst.nameIndex = stream.readAbcInt();
    inst.supernameIndex = stream.readAbcInt();
    inst.flags = stream.readUI8();
    if (inst.isSetFlag(PROTECTED_NS_FLAG)) {
      inst.protectedNsIndex = stream.readAbcInt();
    }
    int interfaceCount = stream.readAbcInt();
    for (int i = 0; i < interfaceCount; i++) {
      inst.interfaceIndices.add(stream.readAbcInt());
    }
    inst.initializerIndex = stream.readAbcInt();
    int traitCount = stream.readAbcInt();
    for (int i = 0; i < traitCount; i++) {
      AbcTrait trait = AbcTrait.read(stream);
      inst.traits.add(trait);
    }
    return inst;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(nameIndex);
    stream.writeAbcInt(supernameIndex);
    stream.writeUI8(flags);
    if (isSetFlag(PROTECTED_NS_FLAG)) {
      stream.writeAbcInt(protectedNsIndex);
    }
    stream.writeAbcInt(interfaceIndices.size());
    for (Iterator<Integer> it = interfaceIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
    stream.writeAbcInt(initializerIndex);
    stream.writeAbcInt(traits.size());
    for (AbcTrait trait : traits) {
      trait.write(stream);
    }
  }

  public int getProtectedNsIndex() {
    return protectedNsIndex;
  }

  public void setProtectedNsIndex(int protectedNsIndex) {
    this.protectedNsIndex = protectedNsIndex;
    setFlag(PROTECTED_NS_FLAG);
  }

  public int getNameIndex() {
    return nameIndex;
  }

  public int getSupernameIndex() {
    return supernameIndex;
  }

  public List<Integer> getInterfaceIndices() {
    return interfaceIndices;
  }

  public int getInitializerIndex() {
    return initializerIndex;
  }

  public List<AbcTrait> getTraits() {
    return traits;
  }
}
