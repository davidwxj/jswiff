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
