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

import com.jswiff.constants.AbcConstants;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbcConstantPool implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private List<Integer> ints = new ArrayList<Integer>();
  private List<Integer> uints = new ArrayList<Integer>();
  private List<Double> doubles = new ArrayList<Double>();
  private List<String> strings = new ArrayList<String>();
  private List<AbcNamespace> namespaces = new ArrayList<AbcNamespace>();
  private List<AbcNamespaceSet> namespaceSets = new ArrayList<AbcNamespaceSet>();
  private List<AbcMultiname> multinames = new ArrayList<AbcMultiname>();
  
  public AbcConstantPool() {
    ints.add(0);
    uints.add(0);
    doubles.add(Double.NaN);
    strings.add("");
    namespaces.add(new AbcNamespace(AbcConstants.NamespaceKind.NAMESPACE, 0)); //TODO determine correct ns kind for this
    namespaceSets.add(null);
    multinames.add(null);
  }
  
  public static AbcConstantPool read(InputBitStream stream) throws IOException {
    AbcConstantPool pool = new AbcConstantPool();
    // read ints
    int count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.ints.add(stream.readAbcInt());
    }
    // read uints
    count =  stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.uints.add(stream.readAbcInt());
    }
    // read doubles
    count =  stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.doubles.add(stream.readDouble());
    }
    // read strings
    count =  stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      int length = stream.readAbcInt();
      String string = new String(stream.readBytes(length), "UTF-8");
      pool.strings.add(string);
    }
    // read namespaces
    count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.namespaces.add(AbcNamespace.read(stream));
    }
    // read namespace sets
    count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.namespaceSets.add(AbcNamespaceSet.read(stream));
    }
    // read multinames
    count = stream.readAbcInt();
    for (int i = 1; i < count; i++) {
      pool.multinames.add(AbcMultiname.read(stream));
    }
    return pool;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    int count = ints.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeAbcInt(ints.get(i));
      }
    }
    count = uints.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeAbcInt(uints.get(i));
      }
    }
    count = doubles.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        stream.writeDouble(doubles.get(i));
      }
    }
    count = strings.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        byte[] buffer = strings.get(i).getBytes("UTF-8");
        stream.writeAbcInt(buffer.length);
        stream.writeBytes(buffer);
      }
    }
    count = namespaces.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        namespaces.get(i).write(stream);
      }
    }
    count = namespaceSets.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        namespaceSets.get(i).write(stream);
      }
    }
    count = multinames.size();
    if (count < 2) {
      stream.writeAbcInt(0);
    } else {
      stream.writeAbcInt(count);
      for (int i = 1; i < count; i++) {
        multinames.get(i).write(stream);
      }
    }
  }

  public List<Integer> getInts() {
    return ints;
  }

  public List<Integer> getUints() {
    return uints;
  }

  public List<Double> getDoubles() {
    return doubles;
  }

  public List<String> getStrings() {
    return strings;
  }

  public List<AbcNamespace> getNamespaces() {
    return namespaces;
  }

  public List<AbcNamespaceSet> getNamespaceSets() {
    return namespaceSets;
  }

  public List<AbcMultiname> getMultinames() {
    return multinames;
  }
}
