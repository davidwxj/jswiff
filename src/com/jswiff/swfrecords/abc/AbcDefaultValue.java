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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcDefaultValue implements Serializable {
  private short kind;
  private int valueIndex;
  
  private AbcDefaultValue() {
    // empty
  }
  
  public AbcDefaultValue(short kind, int valueIndex) {
    this.kind = kind;
    this.valueIndex = valueIndex;
  }

  public static AbcDefaultValue read(InputBitStream stream) throws IOException {
    AbcDefaultValue val = new AbcDefaultValue();
    val.valueIndex = stream.readAbcInt();
    val.kind = stream.readUI8();
    return val;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(valueIndex);
    stream.writeUI8(kind);
  }

  public short getKind() {
    return kind;
  }

  public int getValueIndex() {
    return valueIndex;
  }
}
