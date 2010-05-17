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
 * <p>
 * This tag determines the Flash Player to play sounds on a button's state
 * transitions. Consult <code>ButtonCondAction</code> for more details on state
 * transitions.
 * </p>
 * 
 * <p>
 * Note: despite its name, this tag isn't a definition tag. It doesn't define a
 * new character, it specifies attributes for an existing character.
 * </p>
 * 
 * @see com.jswiff.swfrecords.ButtonCondAction
 * @since SWF 2
 */
public final class DefineButtonSound extends Tag {

  private static final long serialVersionUID = 1L;

  private int buttonId;
  private int overUpToIdleSoundId;
  private SoundInfo overUpToIdleSoundInfo;
  private int idleToOverUpSoundId;
  private SoundInfo idleToOverUpSoundInfo;
  private int overUpToOverDownSoundId;
  private SoundInfo overUpToOverDownSoundInfo;
  private int overDownToOverUpSoundId;
  private SoundInfo overDownToOverUpSoundInfo;

  /**
   * Creates a new DefineButtonSound tag.
   * 
   * @param buttonId
   *          character ID of the button
   */
  public DefineButtonSound(int buttonId) {
    super(TagType.DEFINE_BUTTON_SOUND);
    this.buttonId = buttonId;
  }
  
  DefineButtonSound() {
    super(TagType.DEFINE_BUTTON_SOUND);
  }

  /**
   * Sets the character ID of the button this tag specifies sounds for.
   * 
   * @param buttonId
   *          character ID of button
   */
  public void setButtonId(int buttonId) {
    this.buttonId = buttonId;
  }

  /**
   * Returns the character ID of the button this tag specifies sounds for.
   * 
   * @return button character ID
   */
  public int getButtonId() {
    return buttonId;
  }

  /**
   * Sets the sound character ID for the idleToOverUp transition.
   * 
   * @param soundId
   *          sound ID
   */
  public void setIdleToOverUpSoundId(int soundId) {
    this.idleToOverUpSoundId = soundId;
  }

  /**
   * Returns the sound character ID for the idleToOverUp transition.
   * 
   * @return sound ID
   */
  public int getIdleToOverUpSoundId() {
    return idleToOverUpSoundId;
  }

  /**
   * Sets the sound info for the idleToOverUp transition.
   * 
   * @param soundInfo
   *          sound info
   */
  public void setIdleToOverUpSoundInfo(SoundInfo soundInfo) {
    this.idleToOverUpSoundInfo = soundInfo;
  }

  /**
   * Returns the sound info for the idleToOverUp transition.
   * 
   * @return sound info
   */
  public SoundInfo getIdleToOverUpSoundInfo() {
    return idleToOverUpSoundInfo;
  }

  /**
   * Sets the sound character ID for the overDownToOverUp transition.
   * 
   * @param soundId
   *          sound ID
   */
  public void setOverDownToOverUpSoundId(int soundId) {
    this.overDownToOverUpSoundId = soundId;
  }

  /**
   * Returns the sound character ID for the overDownToOverUp transition.
   * 
   * @return sound ID
   */
  public int getOverDownToOverUpSoundId() {
    return overDownToOverUpSoundId;
  }

  /**
   * Sets the sound info for the overDownToOverUp transition.
   * 
   * @param soundInfo
   *          sound info
   */
  public void setOverDownToOverUpSoundInfo(SoundInfo soundInfo) {
    this.overDownToOverUpSoundInfo = soundInfo;
  }

  /**
   * Returns the sound info for the overDownToOverUp transition.
   * 
   * @return sound info
   */
  public SoundInfo getOverDownToOverUpSoundInfo() {
    return overDownToOverUpSoundInfo;
  }

  /**
   * Sets the sound character ID for the overUpToIdle transition.
   * 
   * @param soundId
   *          sound ID
   */
  public void setOverUpToIdleSoundId(int soundId) {
    this.overUpToIdleSoundId = soundId;
  }

  /**
   * Returns the sound character ID for the overUpToIdle transition.
   * 
   * @return sound ID
   */
  public int getOverUpToIdleSoundId() {
    return overUpToIdleSoundId;
  }

  /**
   * Sets the sound info for the overUpToIdle transition.
   * 
   * @param soundInfo
   *          sound info
   */
  public void setOverUpToIdleSoundInfo(SoundInfo soundInfo) {
    this.overUpToIdleSoundInfo = soundInfo;
  }

  /**
   * Returns the sound info for the overUpToIdle transition.
   * 
   * @return sound info
   */
  public SoundInfo getOverUpToIdleSoundInfo() {
    return overUpToIdleSoundInfo;
  }

  /**
   * Sets the sound character ID for the overUpToOverDown transition.
   * 
   * @param soundId
   *          sound ID
   */
  public void setOverUpToOverDownSoundId(int soundId) {
    this.overUpToOverDownSoundId = soundId;
  }

  /**
   * Returns the sound character ID for the overUpToOverDown transition.
   * 
   * @return sound info
   */
  public int getOverUpToOverDownSoundId() {
    return overUpToOverDownSoundId;
  }

  /**
   * Sets the sound info for the overUpToOverDown transition.
   * 
   * @param soundInfo
   *          sound info
   */
  public void setOverUpToOverDownSoundInfo(SoundInfo soundInfo) {
    this.overUpToOverDownSoundInfo = soundInfo;
  }

  /**
   * Returns the sound info for the overUpToOverDown transition.
   * 
   * @return sound info
   */
  public SoundInfo getOverUpToOverDownSoundInfo() {
    return overUpToOverDownSoundInfo;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(buttonId);
    outStream.writeUI16(overUpToIdleSoundId);
    if (overUpToIdleSoundId != 0) {
      overUpToIdleSoundInfo.write(outStream);
    }
    outStream.writeUI16(idleToOverUpSoundId);
    if (idleToOverUpSoundId != 0) {
      idleToOverUpSoundInfo.write(outStream);
    }
    outStream.writeUI16(overUpToOverDownSoundId);
    if (overUpToOverDownSoundId != 0) {
      overUpToOverDownSoundInfo.write(outStream);
    }
    outStream.writeUI16(overDownToOverUpSoundId);
    if (overDownToOverUpSoundId != 0) {
      overDownToOverUpSoundInfo.write(outStream);
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    buttonId = inStream.readUI16();
    overUpToIdleSoundId = inStream.readUI16();
    if (overUpToIdleSoundId != 0) {
      overUpToIdleSoundInfo = new SoundInfo(inStream);
    }
    idleToOverUpSoundId = inStream.readUI16();
    if (idleToOverUpSoundId != 0) {
      idleToOverUpSoundInfo = new SoundInfo(inStream);
    }
    overUpToOverDownSoundId = inStream.readUI16();
    if (overUpToOverDownSoundId != 0) {
      overUpToOverDownSoundInfo = new SoundInfo(inStream);
    }
    overDownToOverUpSoundId = inStream.readUI16();
    if (overDownToOverUpSoundId != 0) {
      overDownToOverUpSoundInfo = new SoundInfo(inStream);
    }
  }
}
