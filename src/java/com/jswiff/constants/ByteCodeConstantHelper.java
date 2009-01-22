package com.jswiff.constants;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.jswiff.exception.InvalidCodeException;
import com.jswiff.exception.InvalidNameException;

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
  
  E codeLookup(short code) throws InvalidCodeException {
    E val = codeLookupMap.get(code);
    if (val == null) throw new InvalidCodeException(this.typeName, code);
    return val;
  }
  
  E nameLookup(String name) throws InvalidNameException {
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
