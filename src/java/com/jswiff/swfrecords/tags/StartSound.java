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

package com.jswiff.swfrecords.tags;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.SoundInfo;

import java.io.IOException;

/**
 * This tag instructs the Flash Player to play a sound previously defined with
 * the <code>DefineSound</code> tag. Playback parameters can be defined by
 * supplying a <code>SoundInfo</code> instance.
 * 
 * @see SoundInfo
 * @since SWF 1
 */
public final class StartSound extends Tag {

  private static final long serialVersionUID = 1L;

  private int soundId;
  private SoundInfo soundInfo;

  /**
   * Creates a new StartSound tag. Supply the character ID of the sound to be
   * played, and playback otions in an <code>SoundInfo</code> instance.
   * 
   * @param soundId
   *          the sound's character ID
   * @param soundInfo
   *          playback options
   */
  public StartSound(int soundId, SoundInfo soundInfo) {
    super(TagType.START_SOUND);
    this.soundId = soundId;
    this.soundInfo = soundInfo;
  }

  StartSound() {
    super(TagType.START_SOUND);
  }

  /**
   * Sets the character ID of the sound to be played.
   * 
   * @param soundId
   *          sound's character ID
   */
  public void setSoundId(int soundId) {
    this.soundId = soundId;
  }

  /**
   * Returns the character ID of the sound to be played.
   * 
   * @return sound's character ID
   */
  public int getSoundId() {
    return soundId;
  }

  /**
   * Sets the defined playback parameters for the sound to be played.
   * 
   * @param soundInfo
   *          playback options
   */
  public void setSoundInfo(SoundInfo soundInfo) {
    this.soundInfo = soundInfo;
  }

  /**
   * Returns the defined playback parameters for the sound to be played.
   * 
   * @return playback options
   */
  public SoundInfo getSoundInfo() {
    return soundInfo;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(soundId);
    soundInfo.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    soundId = inStream.readUI16();
    soundInfo = new SoundInfo(inStream);
  }
}
