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
