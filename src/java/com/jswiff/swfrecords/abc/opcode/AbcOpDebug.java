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
