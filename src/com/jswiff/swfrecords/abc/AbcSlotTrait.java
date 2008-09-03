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
import java.util.Iterator;

import com.jswiff.io.OutputBitStream;

public class AbcSlotTrait extends AbcTrait {
  private boolean isConst;
  private int slotId;
  private int typeIndex;
  private int valueIndex;
  private short valueKind;
  
  public AbcSlotTrait(int nameIndex, int slotId, int typeIndex, int valueIndex, short valueKind, boolean isConst) {
    super(nameIndex);
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
    stream.writeAbcInt(nameIndex);
    int metadataCount = metadataIndices.size();
    int flagsAndKind = (metadataCount != 0 ? METADATA_FLAG << 4 : 0) | (isConst ? TYPE_CONST : TYPE_SLOT);
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(slotId);
    stream.writeAbcInt(typeIndex);
    stream.writeAbcInt(valueIndex);
    if (valueIndex != 0 ) {
      stream.writeUI8(valueKind);
    }
    if (metadataCount != 0) {
      stream.writeAbcInt(metadataCount);
      for (Iterator<Integer> it = metadataIndices.iterator(); it.hasNext(); ) {
        stream.writeAbcInt(it.next());
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
