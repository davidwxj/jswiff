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

public class AbcOpHasNext2 extends AbcOp {

  private static final long serialVersionUID = 1L;
  
  private int objectRegister;
  private int propertyIndexRegister;

  public AbcOpHasNext2(int objectRegister, int propertyIndexRegister) {
    super(OpCode.HASNEXT2, OpCodeType.HAS_NEXT2);
    this.objectRegister = objectRegister;
    this.propertyIndexRegister = propertyIndexRegister;
  }

  public int getObjectRegister() {
    return objectRegister;
  }

  public int getPropertyIndexRegister() {
    return propertyIndexRegister;
  }
  
  public String toString() {
    return getOpcode().toString() + ": objectRegister = " + objectRegister + ", propertyIndexRegister = " + propertyIndexRegister;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(getOpcode().getCode());
    stream.writeAbcInt(objectRegister);
    stream.writeAbcInt(propertyIndexRegister);
  }
  
}
