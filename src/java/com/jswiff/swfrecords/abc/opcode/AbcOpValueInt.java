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

package com.jswiff.swfrecords.abc.opcode;

import java.io.IOException;

import com.jswiff.constants.AbcConstants.OpCode;
import com.jswiff.constants.AbcConstants.OpCodeType;
import com.jswiff.io.OutputBitStream;

public class AbcOpValueInt extends AbcOp {

  private static final long serialVersionUID = 1L;
  
  private int value;

  public AbcOpValueInt(OpCode opCode, int value) {
    super(opCode, OpCodeType.VALUE_INT);
    this.value = value;
  }

  public int getValue() {
    return value;
  }
  
  public String toString() {
    return getOpcode().toString() + ": value = " + value;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(getOpcode().getCode());
    stream.writeAbcInt(value);
  }
  
}
