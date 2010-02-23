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
