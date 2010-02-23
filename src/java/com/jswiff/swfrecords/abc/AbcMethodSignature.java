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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbcMethodSignature implements Serializable {

  private static final long serialVersionUID = 1L;
  
  public static final short NEED_ARGUMENTS_FLAG = 0x01;
  public static final short NEED_ACTIVATION_FLAG = 0x02;
  public static final short NEED_REST_FLAG = 0x04;
  public static final short HAS_OPTIONAL_FLAG = 0x08;
  public static final short SET_DXNS_FLAG = 0x40;
  public static final short HAS_PARAM_NAMES_FLAG = 0x80;
  
  private int returnTypeIndex;
  private List<Integer> parameterTypeIndices = new ArrayList<Integer>();
  private int nameIndex;
  private short flags;
  private List<AbcDefaultValue> optionalParameters = new ArrayList<AbcDefaultValue>();
  private List<Integer> parameterNameIndices = new ArrayList<Integer>();

  private AbcMethodSignature() { } // empty
  
  public AbcMethodSignature(int nameIndex, int returnTypeIndex) {
    this.nameIndex = nameIndex;
    this.returnTypeIndex = returnTypeIndex;
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
  
  public static AbcMethodSignature read(InputBitStream stream) throws IOException {
    AbcMethodSignature sig = new AbcMethodSignature();
    int parameterCount = stream.readAbcInt();
    sig.returnTypeIndex = stream.readAbcInt();
    for (int i = 0; i < parameterCount; i++) {
      sig.parameterTypeIndices.add(stream.readAbcInt());
    }
    sig.nameIndex = stream.readAbcInt();
    sig.flags = stream.readUI8();
    if (sig.isSetFlag(HAS_OPTIONAL_FLAG)) {
      int count = stream.readAbcInt();
      for (int i = 0; i < count; i++) {
        sig.optionalParameters.add(AbcDefaultValue.read(stream));
      }
    }
    if (sig.isSetFlag(HAS_PARAM_NAMES_FLAG)) {
      for (int i = 0; i < parameterCount; i++) {
        sig.parameterNameIndices.add(stream.readAbcInt());
      }
    }
    return sig;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(parameterTypeIndices.size());
    stream.writeAbcInt(returnTypeIndex);
    for (Iterator<Integer> it = parameterTypeIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
    stream.writeAbcInt(nameIndex);
    stream.writeUI8(flags);
    int optionalParamCount = optionalParameters.size();
    if (optionalParamCount > 0) {
      stream.writeAbcInt(optionalParamCount);
      for (Iterator<AbcDefaultValue> it = optionalParameters.iterator(); it.hasNext(); ) {
        it.next().write(stream);
      }
    }
    for (Iterator<Integer> it = parameterNameIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
  }

  public int getReturnTypeIndex() {
    return returnTypeIndex;
  }

  public List<Integer> getParameterTypeIndices() {
    return parameterTypeIndices;
  }

  public int getNameIndex() {
    return nameIndex;
  }

  public List<AbcDefaultValue> getOptionalParameters() {
    return optionalParameters;
  }

  public List<Integer> getParameterNameIndices() {
    return parameterNameIndices;
  }
}
