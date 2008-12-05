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
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcClass implements Serializable {

  private static final long serialVersionUID = 1L;

  private int initializerIndex;
  private List<AbcTrait> traits = new ArrayList<AbcTrait>();
  
  private AbcClass() { } // empty
  
  public AbcClass(int initializerIndex) {
    this.initializerIndex = initializerIndex;
  }
  
  public static AbcClass read(InputBitStream stream) throws IOException {
    AbcClass cls = new AbcClass();
    cls.initializerIndex = stream.readAbcInt();
    int traitCount = stream.readAbcInt();
    for (int i = 0; i < traitCount; i++) {
      AbcTrait trait = AbcTrait.read(stream);
      cls.traits.add(trait);
    }
    return cls;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(initializerIndex);
    stream.writeAbcInt(traits.size());
    for (AbcTrait trait : traits) {
      trait.write(stream);
    }
  }

  public int getInitializerIndex() {
    return initializerIndex;
  }

  public List<AbcTrait> getTraits() {
    return traits;
  }
}
