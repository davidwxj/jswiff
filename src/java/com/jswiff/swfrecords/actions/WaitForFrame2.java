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

import java.io.IOException;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * Checks whether the specified frame is loaded. If not, the specified number
 * of actions is skipped. As of SWF 5, this action is deprecated. Macromedia
 * recommends to use <code>MovieClip._framesLoaded</code> instead.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop frame</code> (frame number or label)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>ifFrameLoaded()</code>
 * </p>
 *
 * @since SWF 4
 */
public final class WaitForFrame2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private short skipCount;

  /**
   * Creates a new WaitForFrame2 action.
   *
   * @param skipCount number of actions to be skipped if the frame isn't loaded
   *        yet
   */
  public WaitForFrame2(short skipCount) {
    super(ActionType.WAIT_FOR_FRAME_2);
    this.skipCount   = skipCount;
  }

  WaitForFrame2(InputBitStream stream) throws IOException {
    super(ActionType.WAIT_FOR_FRAME_2);
    skipCount   = stream.readUI8();
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    return 4;
  }

  /**
   * Returns the number of actions which are skipped in case the frame is not
   * loaded yet.
   *
   * @return number of actions to skip
   */
  public short getSkipCount() {
    return skipCount;
  }

  /**
   * Returns a short description of this action and the number of actions to be
   * skipped.
   *
   * @return <code>"WaitForFrame", skipCount</code>
   */
  public String toString() {
    return super.toString() + " skipCount: " + skipCount;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeUI8(skipCount);
  }
}
