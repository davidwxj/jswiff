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

import com.jswiff.constants.AbcConstants.NamespaceKind;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcNamespace implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private NamespaceKind kind;
  private int nameIndex; // points to string constant

  public AbcNamespace(NamespaceKind kind, int nameIndex) {
    this.kind = kind;
    this.nameIndex = nameIndex;
  }

  public static AbcNamespace read(InputBitStream stream) throws IOException, InvalidCodeException {
    short code = stream.readUI8();
    NamespaceKind kind = NamespaceKind.lookup( code );
    AbcNamespace ns = new AbcNamespace(kind, stream.readAbcInt());
    return ns;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(kind.getCode());
    stream.writeAbcInt(nameIndex);
  }

  public NamespaceKind getKind() {
    return kind;
  }

  public int getNameIndex() {
    return nameIndex;
  }
  
  @Override
  public String toString() {
    return getKind().toString() + ": nameIndex = " + this.nameIndex;
  }
  
}
