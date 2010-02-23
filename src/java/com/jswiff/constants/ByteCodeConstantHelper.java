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

package com.jswiff.constants;

import com.jswiff.exception.InvalidCodeException;
import com.jswiff.exception.InvalidNameException;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

interface ByteCodeConstant {
  /**
   * Get the bytecode identifier
   * @return the bytecode identifier
   */
  public short getCode();
}

class ByteCodeConstantHelper<E extends Enum<E> & ByteCodeConstant> {
  
  private final Map<Short, E> codeLookupMap;
  private final Map<String, E> nameLookupMap;
  private final String typeName;
  private Map<E, String> niceNames = null;
  
  ByteCodeConstantHelper(E[] stuff, String typeName) {
    codeLookupMap = new HashMap<Short, E>(stuff.length);
    nameLookupMap = new HashMap<String, E>(stuff.length);
    this.typeName = typeName;
    for (E val : stuff) {
      codeLookupMap.put(val.getCode(), val);
      nameLookupMap.put(val.toString(), val);
    }
  };
  
  E codeLookup(short code) {
    E val = codeLookupMap.get(code);
    if (val == null) throw new InvalidCodeException(this.typeName, code);
    return val;
  }
  
  E nameLookup(String name) {
    E val = nameLookupMap.get(name);
    if (val == null) throw new InvalidNameException(this.typeName, name);
    return val;
  }
  
  String getNiceName(E val) {
    if (niceNames == null) {
      niceNames = new EnumMap<E, String>(val.getDeclaringClass());
    }
    String niceName = niceNames.get(val);
    if (niceName == null) {
      String[] bits = val.name().split("_");
      niceName = "";
      for (String bit : bits) {
        niceName += bit.substring(0, 1).concat(bit.substring(1).toLowerCase());
      }
      niceNames.put(val, niceName);
    }
    return niceName;
  }

}
