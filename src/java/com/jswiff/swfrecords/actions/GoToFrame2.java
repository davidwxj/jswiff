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
 * Instructs Flash Player to go to the specified frame.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop frame</code> (frame number or label)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>gotoAndPlay(), gotoAndStop()</code>
 * </p>
 *
 * @since SWF 4
 */
public final class GoToFrame2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private boolean play;
  private int sceneBias;

  /**
   * Creates a new GoToFrame2 action. If the <code>play</code> flag is set, the
   * movie starts playing at the specified frame, otherwise it stops at that
   * frame. The <code>sceneBias</code> parameter is a non-negative offset to
   * be added to the specified frame.
   *
   * @param play play flag
   * @param sceneBias offset added to target frame
   */
  public GoToFrame2(boolean play, int sceneBias) {
    super(ActionType.GO_TO_FRAME_2);
    this.play        = play;
    this.sceneBias   = sceneBias;
  }

  GoToFrame2(InputBitStream stream) throws IOException {
    super(ActionType.GO_TO_FRAME_2);
    short bits            = stream.readUI8();

    // 6 reserved bits
    boolean sceneBiasFlag = ((bits & 2) != 0);
    play                  = ((bits & 1) != 0);
    if (sceneBiasFlag) {
      sceneBias = stream.readUI16();
    }
  }

  /**
   * Returns the scene bias, i.e. the non-negative offset to be added to the
   * target frame.
   *
   * @return offset added to target frame
   */
  public int getSceneBias() {
    return sceneBias;
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
    if (sceneBias > 0) {
      size += 2;
    }
    return size;
  }

  /**
   * Returns the state of the <code>play</code> flag. If <code>true</code>, the
   * movie starts playing at the specified frame. Otherwise, the movie stops
   * at that frame.
   *
   * @return state of <code>play</code> flag
   */
  public boolean play() {
    return play;
  }

  /**
   * Returns a short description of this action, the state of the
   * <code>play</code> flag and an eventual <code>sceneBias</code>.
   *
   * @return <code>"GoToFrame2", play, sceneBias</code>
   */
  public String toString() {
    String result = super.toString() + "  play: " + play;
    if (sceneBias > 0) {
      result += (" sceneBias: " + sceneBias);
    }
    return result;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeUnsignedBits(0, 6);
    boolean sceneBiasFlag = (sceneBias > 0);
    dataStream.writeBooleanBit(sceneBiasFlag);
    dataStream.writeBooleanBit(play);
    if (sceneBiasFlag) {
      dataStream.writeUI16(sceneBias);
    }
  }
}
