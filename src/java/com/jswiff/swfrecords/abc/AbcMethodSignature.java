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
