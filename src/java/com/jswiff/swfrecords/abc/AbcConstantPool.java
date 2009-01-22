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

import com.jswiff.constants.AbcConstants;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

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
  
  public static AbcConstantPool read(InputBitStream stream) throws IOException, InvalidCodeException {
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
