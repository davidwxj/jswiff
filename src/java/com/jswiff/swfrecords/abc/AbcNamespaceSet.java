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

public class AbcNamespaceSet implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private List<Integer> namespaceIndices = new ArrayList<Integer>(); // points to string constant

  public static AbcNamespaceSet read(InputBitStream stream) throws IOException {
    AbcNamespaceSet set = new AbcNamespaceSet();
    int count = stream.readAbcInt();
    for (int i = 0; i < count; i++) {
      set.namespaceIndices.add(stream.readAbcInt());
    }
    return set;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(namespaceIndices.size());
    for (Iterator<Integer> it = namespaceIndices.iterator(); it.hasNext(); ) {
      stream.writeAbcInt(it.next());
    }
  }

  public List<Integer> getNamespaceIndices() {
    return namespaceIndices;
  }
}
