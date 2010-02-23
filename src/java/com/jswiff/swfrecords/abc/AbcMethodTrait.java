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
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.util.List;

public class AbcMethodTrait extends AbcTrait {

  private static final long serialVersionUID = 1L;
  
  private int dispId;
  private int methodIndex;
  private boolean isSetter;
  private boolean isGetter;
  private boolean isFinal;
  private boolean isOverride;
  
  public AbcMethodTrait(int nameIndex, int dispId, int methodIndex, boolean isGetter, boolean isSetter, boolean isFinal, boolean isOverride) {
    super(nameIndex, TraitKind.METHOD);
    this.dispId = dispId;
    this.methodIndex = methodIndex;
    this.isGetter = isGetter;
    this.isSetter = isSetter;
    if (isSetter && isGetter) {
      throw new IllegalArgumentException("Method trait cannot be both getter and setter");
    }
    this.isFinal = isFinal;
    this.isOverride = isOverride;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(getNameIndex());
    List<Integer> metadataIndices = getMetadataIndices();
    int metadataCount = metadataIndices.size();
    int flagsAndKind = metadataCount != 0 ? METADATA_FLAG : 0;
    if (isFinal) {
      flagsAndKind |= FINAL_FLAG;
    }
    if (isOverride) {
      flagsAndKind |= OVERRIDE_FLAG;
    }
    flagsAndKind <<= 4;
    if (isGetter) {
      flagsAndKind |= TraitKind.GETTER.getCode();
    } else if (isSetter) {
      flagsAndKind |= TraitKind.SETTER.getCode();
    } else {
      flagsAndKind |= TraitKind.METHOD.getCode();
    }
    stream.writeUI8((short) flagsAndKind);
    stream.writeAbcInt(dispId);
    stream.writeAbcInt(methodIndex);
    if (metadataCount != 0) {
      stream.writeAbcInt(metadataCount);
      for (int index : metadataIndices) {
        stream.writeAbcInt(index);
      }
    }
  }

  public int getDispId() {
    return dispId;
  }

  public int getMethodIndex() {
    return methodIndex;
  }

  public boolean isSetter() {
    return isSetter;
  }

  public boolean isGetter() {
    return isGetter;
  }

  public boolean isFinal() {
    return isFinal;
  }

  public boolean isOverride() {
    return isOverride;
  }
}
