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


/**
 * <p>
 * Instructs Flash Player to change the context of subsequent actions, so they
 * apply to an object with the specified name. This action can be used e.g. to
 * control the timeline of a sprite object.
 * </p>
 * 
 * <p>
 * Note: as of SWF 5, this action is deprecated. Use <code>With</code> instead.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>tellTarget()</code>
 * </p>
 *
 * @since SWF 3
 */
public final class SetTarget extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private String name;

  /**
   * Creates a new SetTarget action. The target object's name is passed as a
   * string.
   *
   * @param name target object name
   */
  public SetTarget(String name) {
    super(ActionType.SET_TARGET);
    this.name   = name;
  }

  /*
   * Reads a SetTarget action from a bit stream.
   */
  SetTarget(InputBitStream stream) throws IOException {
    super(ActionType.SET_TARGET);
    name   = stream.readString();
  }

  /**
   * Returns the name of the target object.
   *
   * @return target object's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 4;
    try {
      size += name.getBytes("UTF-8").length;
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available
    }
    return size;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"SetTarget"</code>
   */
  public String toString() {
    return super.toString() + " " + name;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(name);
  }
}
