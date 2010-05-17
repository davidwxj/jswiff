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

package com.jswiff.swfrecords.actions;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Creates a new constant pool. The constants defined here can be referenced in
 * <code>Push</code> as <code>constant8</code> if there are less than 256
 * constants in the pool, otherwise as <code>constant16</code>.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: none
 * </p>
 *
 * @since SWF 5
 */
public final class ConstantPool extends Action {

  private static final long serialVersionUID = 1L;
  
  private List<String> constants = new ArrayList<String>();

  /**
   * Creates a new ConstantPool action.
   */
  public ConstantPool() {
    super(ActionType.CONSTANT_POOL);
  }

  /*
   * Creates a new ConstantPool action. Data is read from a bit stream.
   *
   * @param stream bit stream containing the action data
   *
   * @throws IOException if an I/O error has occured
   */
  ConstantPool(InputBitStream stream) throws IOException {
    super(ActionType.CONSTANT_POOL);
    int count = stream.readUI16();
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        constants.add(stream.readString());
      }
    }
  }

  /**
   * Returns the constants of the pool.
   *
   * @return list containing all constants as String instances
   */
  public List<String> getConstants() {
    return constants;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 5; // 1 (code) + 2 (data length) + 2 (# of constants)
    try {
      for (Iterator<String> i = constants.iterator(); i.hasNext();) {
        size += (i.next().getBytes("UTF-8").length + 1); // Unicode, null-terminated
      }
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available..
    }
    return size;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"ConstantPool"</code> and number of constants in pool
   */
  public String toString() {
    return super.toString() + " (" + constants.size() + " constants)";
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeUI16(constants.size());
    for (int i = 0; i < constants.size(); i++) {
      dataStream.writeString(constants.get(i));
    }
  }
}
