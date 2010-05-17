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
  
  private static final long serialVersionUID = 1L;
  
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
    super(ActionType.WAIT_FOR_FRAME);
    this.frame       = frame;
    this.skipCount   = skipCount;
  }

  /*
   * Reads a WaitForFrame action from a bit stream.
   */
  WaitForFrame(InputBitStream stream) throws IOException {
    super(ActionType.WAIT_FOR_FRAME);
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
    return super.toString() + " frame: " + frame + " skipCount: " + skipCount;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeUI16(frame);
    dataStream.writeUI8(skipCount);
  }
}
