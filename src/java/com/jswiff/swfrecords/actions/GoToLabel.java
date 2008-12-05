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
import java.io.UnsupportedEncodingException;

import com.jswiff.constants.ActionConstants;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * Instructs Flash Player to go to a frame associated with the specified label.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>gotoAndPlay(), gotoAndStop()</code> (no 1:1
 * equivalent available)
 * </p>
 *
 * @see com.jswiff.swfrecords.tags.FrameLabel
 * @since SWF 3
 */
public final class GoToLabel extends Action {
  private String frameLabel;

  /**
   * Creates a new GoToLabel action.
   *
   * @param frameLabel the label of the target frame
   */
  public GoToLabel(String frameLabel) {
    code         = ActionConstants.GO_TO_LABEL;
    this.frameLabel   = frameLabel;
  }

  /*
   * Reads a GoToLabel action from a bit stream.
   */
  GoToLabel(InputBitStream stream) throws IOException {
    code    = ActionConstants.GO_TO_LABEL;
    frameLabel   = stream.readString();
  }

  /**
   * Returns the label of the target frame.
   *
   * @return the target frame's label
   */
  public String getFrameLabel() {
    return frameLabel;
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
      size += frameLabel.getBytes("UTF-8").length;
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available
    }
    return size;
  }

  /**
   * Returns a short description of the tag and the label name of the target
   * frame.
   *
   * @return <code>"GoToLabel", label</code>
   */
  public String toString() {
    return "GoToLabel " + frameLabel;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(frameLabel);
  }
}
