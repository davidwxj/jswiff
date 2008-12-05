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
import java.util.List;

import com.jswiff.constants.AbcConstants.TraitKind;
import com.jswiff.io.OutputBitStream;

public class AbcClassTrait extends AbcTrait {

  private static final long serialVersionUID = 1L;
  
  private int slotId;
  private int classIndex; 
  
  public AbcClassTrait(int nameIndex, int slotId, int classIndex) {
    super(nameIndex, TraitKind.CLASS);
    this.slotId = slotId;
    this.classIndex = classIndex;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(getNameIndex());
    List<Integer> metadataIndices = getMetadataIndices();
    int metadataCount = metadataIndices.size();
    int flagsAndKind = (metadataCount != 0 ? METADATA_FLAG << 4 : 0) | TraitKind.CLASS.getCode();
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(slotId);
    stream.writeAbcInt(classIndex);
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

  public int getClassIndex() {
    return classIndex;
  }
}
