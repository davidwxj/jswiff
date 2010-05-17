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
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

/**
 * <p>
 * This tag contains raw streaming sound data. The data format must be defined
 * in a preceding <code>SoundStreamHead</code> or <code>SoundStreamHead2</code>
 * tag. There may only be one <code>SoundStreamBlock</code> tag per SWF frame.
 * </p>
 * 
 * <p>
 * Warning: you are responsible for obtaining technology licenses needed for
 * encoding and decoding sound data (see e.g. <a
 * href="http://mp3licensing.com">mp3licensing.com</a> for details on mp3
 * licensing).
 * </p>
 * 
 * @see SoundStreamHead
 * @see SoundStreamHead2
 * @since SWF 1
 */
public final class SoundStreamBlock extends Tag {

  private static final long serialVersionUID = 1L;

  private byte[] streamSoundData;

  /**
   * Creates a new SoundStreamBlock tag. Supply the sound data as byte array
   * 
   * @param streamSoundData
   *          raw sound stream data
   */
  public SoundStreamBlock(byte[] streamSoundData) {
    super(TagType.SOUND_STREAM_BLOCK);
    this.streamSoundData = streamSoundData;
  }

  SoundStreamBlock() {
    super(TagType.SOUND_STREAM_BLOCK);
  }

  /**
   * Sets the contained sound stream data (as a byte array).
   * 
   * @param streamSoundData
   *          raw sound stream data
   */
  public void setStreamSoundData(byte[] streamSoundData) {
    this.streamSoundData = streamSoundData;
  }

  /**
   * Returns the contained sound stream data as a byte array.
   * 
   * @return raw sound stream data
   */
  public byte[] getStreamSoundData() {
    return streamSoundData;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    this.setForceLongHeader(true);
    outStream.writeBytes(streamSoundData);
  }

  void setData(byte[] data) {
    streamSoundData = data;
  }
}
