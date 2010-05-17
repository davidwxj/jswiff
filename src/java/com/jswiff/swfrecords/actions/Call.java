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
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * Executes the script attached to a specified frame. The argument can be a
 * frame number or a frame label.<br>
 * This action is deprecated since SWF 5. Use <code>CallFunction</code> where
 * possible.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br><code>pop frame</code> (number or label)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: the <code>&&</code> operator
 * </p>
 *
 * @since SWF 4
 */
public final class Call extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new Call action.
   */
  public Call() {
    super(ActionType.CALL);
  }

  /**
   * <p>
   * Returns the size of this action record in bytes. Can be used to compute
   * the size of an action block (e.g. for implementing jumps, function
   * definitions etc.).
   * </p>
   *
   * @return size of action record (in bytes)
   */
  public int getSize() {
    return 3; // 1 (code) + 2 (size - always 0 ...)
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream) {
    // don't do anything
  }
}
