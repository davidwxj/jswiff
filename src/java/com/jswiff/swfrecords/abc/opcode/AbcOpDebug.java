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

import com.jswiff.constants.AbcConstants.OpCode;
import com.jswiff.constants.AbcConstants.OpCodeType;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

public class AbcOpDebug extends AbcOp {

  private static final long serialVersionUID = 1L;
  
  private short type;
  private int registerNameIndex;
  private short register;
  private int extra;

  public AbcOpDebug(short type, int registerNameIndex, short register, int extra) {
    super(OpCode.DEBUG, OpCodeType.DEBUG);
    this.type = type;
    this.registerNameIndex = registerNameIndex;
    this.register = register;
    this.extra = extra;
  }

  public short getType() {
    return type;
  }

  public int getRegisterNameIndex() {
    return registerNameIndex;
  }

  public short getRegister() {
    return register;
  }

  public int getExtra() {
    return extra;
  }
  
  public String toString() {
    return super.toString() + ": registerNameIndex = " + registerNameIndex
    + ", register = " + register + ", extra = " + extra;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opCode.getCode());
    stream.writeUI8(type);
    stream.writeAbcInt(registerNameIndex);
    stream.writeUI8(register);
    stream.writeAbcInt(extra);
  }
  
}
