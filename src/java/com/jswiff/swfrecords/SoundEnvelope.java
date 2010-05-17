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

package com.jswiff.swfrecords;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;


/**
 * This class is used for time-based volume control within
 * <code>SoundInfo</code>. Defines the volume level for the left and right
 * channel, starting at a certain sound sample called the 'envelope point'.
 *
 * @see SoundInfo
 */
public final class SoundEnvelope implements Serializable {
  private long pos44;
  private int leftLevel;
  private int rightLevel;

  /**
   * Creates a new SoundEnvelope instance. Specify the position of the envelope
   * point within the sound as a number of 44 kHz samples (multiply
   * accordingly when using a lower sampling rate). Then supply the volume
   * level for the left and right channel. For mono sounds, use identical
   * values.
   *
   * @param pos44 envelope point in number of 44 kHz samples
   * @param leftLevel left volume level (between 0 and 32768)
   * @param rightLevel right volume level (between 0 and 32768)
   */
  public SoundEnvelope(long pos44, int leftLevel, int rightLevel) {
    this.pos44        = pos44;
    this.leftLevel    = leftLevel;
    this.rightLevel   = rightLevel;
  }

  /**
   * Creates a new SoundEnvelope instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public SoundEnvelope(InputBitStream stream) throws IOException {
    pos44        = stream.readUI32();
    leftLevel    = stream.readUI16();
    rightLevel   = stream.readUI16();
  }

  /**
   * Returns the volume level of the left channel.
   *
   * @return left channel volume (between 0 and 32768)
   */
  public int getLeftLevel() {
    return leftLevel;
  }

  /**
   * Position of the envelope point as a number of 44 kHz samples (multiplied
   * accordingly when using a lower sampling rate).
   *
   * @return envelope point in number of 44 kHz samples
   */
  public long getPos44() {
    return pos44;
  }

  /**
   * Returns the volume level of the right channel.
   *
   * @return right channel volume (between 0 and 32768)
   */
  public int getRightLevel() {
    return rightLevel;
  }

  /**
   * Writes the instance to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI32(pos44);
    stream.writeUI16(leftLevel);
    stream.writeUI16(rightLevel);
  }
}
