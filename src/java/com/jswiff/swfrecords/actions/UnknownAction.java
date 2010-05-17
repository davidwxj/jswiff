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
