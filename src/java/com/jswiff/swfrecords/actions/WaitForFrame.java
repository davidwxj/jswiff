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
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>ifFrameLoaded()</code>
 * </p>
 *
 * @since SWF 3
 */
public final class WaitForFrame extends Action {
  private int frame;
  private short skipCount;

  /**
   * Creates a new WaitForFrame action.
   *
   * @param frame frame number to be loaded
   * @param skipCount number of actions to be skipped if the frame isn't loaded
   *        yet
   */
  public WaitForFrame(int frame, short skipCount) {
    code             = ActionConstants.WAIT_FOR_FRAME;
    this.frame       = frame;
    this.skipCount   = skipCount;
  }

  /*
   * Reads a WaitForFrame action from a bit stream.
   */
  WaitForFrame(InputBitStream stream) throws IOException {
    code        = ActionConstants.WAIT_FOR_FRAME;
    frame       = stream.readUI16();
    skipCount   = stream.readUI8();
  }

  /**
   * Returns the number of the frame to be loaded.
   *
   * @return frame number
   */
  public int getFrame() {
    return frame;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    return 6;
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
   * Returns a short description of this action, the frame number to be loaded
   * and the number of actions to be skipped.
   *
   * @return <code>"WaitForFrame", frame, skipCount</code>
   */
  public String toString() {
    return "WaitForFrame frame: " + frame + " skipCount: " + skipCount;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeUI16(frame);
    dataStream.writeUI8(skipCount);
  }
}
