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

import com.jswiff.constants.ActionConstants;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * Instructs Flash Player to go to the specified frame in the current movie.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>gotoAndPlay(), gotoAndStop()</code>
 * </p>
 *
 * @since SWF 3
 */
public final class GoToFrame extends Action {
  private int frame;

  /**
   * Creates a new GoToFrame action. The target frame number is passed as a
   * integer.
   *
   * @param frame a frame number
   */
  public GoToFrame(int frame) {
    code         = ActionConstants.GO_TO_FRAME;
    this.frame   = frame;
  }

  /*
   * Reads a GoToFrame action from a bit stream.
   */
  GoToFrame(InputBitStream stream) throws IOException {
    code    = ActionConstants.GO_TO_FRAME;
    frame   = stream.readUI16();
  }

  /**
   * Returns the frame number Flash Player is supposed to jump to.
   *
   * @return a frame number
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
    return 5;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"GoToFrame"</code>
   */
  public String toString() {
    return "GoToFrame " + frame;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeUI16(frame);
  }
}
