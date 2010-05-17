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


/**
 * This class implements a placeholder for unknown action records.
 */
public final class UnknownAction extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private final short unknownActionCode;
  private InputBitStream inStream;
  private byte[] actionData;

  /**
   * Creates a new UnknownAction instance.
   *
   * @param code action code indicationg the action type
   * @param data data contained in the action record
   */
  public UnknownAction(short code, byte[] data) {
    super(ActionType.UNKNOWN_ACTION);
    this.unknownActionCode = code;
    actionData = data;
  }

  UnknownAction(InputBitStream stream, short code) throws IOException {
    super(ActionType.UNKNOWN_ACTION);
    inStream = stream;
    this.unknownActionCode = code;
    if (inStream != null) {
      actionData = inStream.readBytes(inStream.available());
    } else {
      actionData = new byte[0];
    }
  }

  /**
   * Returns the record data as a byte array.
   *
   * @return record data
   */
  public byte[] getData() {
    return actionData;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Unknown action", code, data length</code>
   */
  public String toString() {
    return super.toString() + " (code: " + this.unknownActionCode + ", length: " +
    actionData.length + ")";
  }

  /*
   * Copies data from the input bit stream to the output bit stream. This can
   * be used to copy actions with unknown structure.
   */
  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeBytes(actionData);
  }
  
  @Override
  public short actionCode() {
    return this.unknownActionCode;
  }
  
}
