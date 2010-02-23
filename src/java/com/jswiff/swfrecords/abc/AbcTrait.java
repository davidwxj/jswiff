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

import com.jswiff.constants.AbcConstants.TraitKind;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbcTrait implements Serializable {
  
  protected static final int FINAL_FLAG = 0x01;
  protected static final int OVERRIDE_FLAG = 0x02;
  protected static final int METADATA_FLAG = 0x04;
  
  private static final long serialVersionUID = 1L;

  private final TraitKind type;
  private final int nameIndex;
  private final List<Integer> metadataIndices = new ArrayList<Integer>();
  
  public AbcTrait(int nameIndex, TraitKind type) {
    this.nameIndex = nameIndex;
    this.type = type;
  }
  
  public static AbcTrait read(InputBitStream stream) throws IOException {
    int nameIndex = stream.readAbcInt();
    short flagsAndKind = stream.readUI8();
    int flags = flagsAndKind >> 4;
    int kind = flagsAndKind & 0x0f;
    AbcTrait trait;
    TraitKind traitType = TraitKind.lookup((short)kind);
    switch (traitType) {
      case SLOT:
      case CONST:
        int slotId = stream.readAbcInt();
        int typeIndex = stream.readAbcInt();
        int valueIndex = stream.readAbcInt();
        short valueKind = valueIndex == 0 ? 0 : stream.readUI8();
        AbcSlotTrait slotTrait = new AbcSlotTrait(nameIndex, slotId, typeIndex, valueIndex,
            valueKind, traitType.equals(TraitKind.CONST));
        trait = slotTrait;
        break;
      case METHOD:
      case GETTER:
      case SETTER:
        int dispId = stream.readAbcInt();
        int methodIndex = stream.readAbcInt();
        AbcMethodTrait methodTrait = new AbcMethodTrait(nameIndex, dispId, methodIndex,
            traitType.equals(TraitKind.GETTER), traitType.equals(TraitKind.SETTER),
            (flags & FINAL_FLAG) != 0, (flags & OVERRIDE_FLAG) != 0);
        trait = methodTrait;
        break;
      case CLASS:
        slotId = stream.readAbcInt();
        int classIndex = stream.readAbcInt();
        AbcClassTrait classTrait = new AbcClassTrait(nameIndex, slotId, classIndex);
        trait = classTrait;
        break;
      case FUNCTION:
        slotId = stream.readAbcInt();
        int functionIndex = stream.readAbcInt();
        AbcFunctionTrait functionTrait = new AbcFunctionTrait(nameIndex, slotId, functionIndex);
        trait = functionTrait;
        break;
      default:
        throw new AssertionError("Unhandled trait type: '" + traitType.name() + "'");
    }
    if ((flags & METADATA_FLAG) != 0) {
      int metadataCount = stream.readAbcInt();
      for (int i = 0; i < metadataCount; i++) {
        trait.metadataIndices.add(stream.readAbcInt());
      }
    }return trait;
  }

  public abstract void write(OutputBitStream stream) throws IOException;

  public int getNameIndex() {
    return nameIndex;
  }
  
  public TraitKind getType() {
    return this.type;
  }

  public List<Integer> getMetadataIndices() {
    return metadataIndices;
  }
}
