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
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.util.List;

public class AbcSlotTrait extends AbcTrait {

  private static final long serialVersionUID = 1L;

  private boolean isConst;
  private int slotId;
  private int typeIndex;
  private int valueIndex;
  private short valueKind;
  
  public AbcSlotTrait(int nameIndex, int slotId, int typeIndex, int valueIndex, short valueKind, boolean isConst) {
    super(nameIndex, TraitKind.SLOT);
    this.slotId = slotId;
    this.typeIndex = typeIndex;
    this.valueIndex = valueIndex;
    this.valueKind = valueKind;
    this.isConst = isConst;
  }
  
  public boolean isConst() {
    return isConst;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(getNameIndex());
    List<Integer> metadataIndices = getMetadataIndices();
    int metadataCount = metadataIndices.size();
    short kind = isConst ? TraitKind.CONST.getCode() : TraitKind.SLOT.getCode();
    int flagsAndKind = (metadataCount != 0 ? METADATA_FLAG << 4 : 0) | kind;
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(slotId);
    stream.writeAbcInt(typeIndex);
    stream.writeAbcInt(valueIndex);
    if (valueIndex != 0 ) {
      stream.writeUI8(valueKind);
    }
    if (metadataCount != 0) {
      stream.writeAbcInt(metadataCount);
      for (int index : metadataIndices) {
        stream.writeAbcInt(index);
      }
    }
  }

  public int getSlotId() {
    return slotId;
  }

  public int getTypeIndex() {
    return typeIndex;
  }

  public int getValueIndex() {
    return valueIndex;
  }

  public short getValueKind() {
    return valueKind;
  }
  
}
