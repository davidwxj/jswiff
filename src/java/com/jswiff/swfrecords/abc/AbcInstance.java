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
  
  public void clearFlags(short flag) {
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
    for (Iterator<AbcTrait> it = traits.iterator(); it.hasNext(); ) {
      AbcTrait trait = it.next();
      if (trait instanceof AbcMethodTrait) {
      ((AbcMethodTrait)trait).write(stream);
      } else {
        trait.write(stream);
      }
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
