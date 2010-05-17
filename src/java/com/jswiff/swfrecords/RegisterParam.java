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

package com.jswiff.swfrecords;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.actions.Action;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;


/**
 * This class is used by <code>DefineFunction2</code> to specify the function's
 * parameters. These can be either variables or registers.
 */
public final class RegisterParam implements Serializable {
  private short register;
  private String paramName;

  /**
   * Creates a new RegisterParam instance. If you use 0 as register number, the
   * parameter can be referenced as a variable within the function (this
   * variable's name is contained in <code>paramName</code>). If the register
   * number is greater than 0, the parameter is copied into the corresponding
   * register.
   *
   * @param register register number
   * @param paramName variable name
   */
  public RegisterParam(short register, String paramName) {
    this.register    = register;
    this.paramName   = paramName;
  }

  /**
   * Reads an instance from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public RegisterParam(InputBitStream stream) throws IOException {
    register    = stream.readUI8();
    paramName   = stream.readString();
  }

  /**
   * Returns the parameter (i.e. the variable's) name
   *
   * @return variable name
   */
  public String getParamName() {
    return paramName;
  }

  /**
   * Returns the register number.
   *
   * @return register number
   */
  public short getRegister() {
    return register;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    // null-terminated unicode + 1 byte
    int size = 2;
    try {
      size += paramName.getBytes("UTF-8").length;
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should always be available
    }
    return size;
  }

  /**
   * Writes the instance to a bit stream.
   *
   * @param stream the target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(register);
    stream.writeString(paramName);
  }
}
