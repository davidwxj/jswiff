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

public class AbcOpHasNext2 extends AbcOp {

  private static final long serialVersionUID = 1L;
  
  private int objectRegister;
  private int propertyIndexRegister;

  public AbcOpHasNext2(int objectRegister, int propertyIndexRegister) {
    super(OpCode.HAS_NEXT_2, OpCodeType.HAS_NEXT2);
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
    return super.toString() + ": objectRegister = " + objectRegister + 
    ", propertyIndexRegister = " + propertyIndexRegister;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opCode.getCode());
    stream.writeAbcInt(objectRegister);
    stream.writeAbcInt(propertyIndexRegister);
  }
  
}
