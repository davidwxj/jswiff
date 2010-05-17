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
  
  private static final long serialVersionUID = 1L;
  
  private String frameLabel;

  /**
   * Creates a new GoToLabel action.
   *
   * @param frameLabel the label of the target frame
   */
  public GoToLabel(String frameLabel) {
    super(ActionType.GO_TO_LABEL);
    this.frameLabel   = frameLabel;
  }

  /*
   * Reads a GoToLabel action from a bit stream.
   */
  GoToLabel(InputBitStream stream) throws IOException {
    super(ActionType.GO_TO_LABEL);
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
    return super.toString() + " " + frameLabel;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(frameLabel);
  }
}
