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

import java.io.IOException;

/**
 * <p>
 * This tag is used to define the format of streaming sound data (contained in
 * <code>SoundStreamBlock</code> tags). Streaming sounds defined with this tag
 * are always compressed and their sample size is always 16 bit. For more
 * flexibility, use <code>SoundStreamHead2</code>.
 * </p>
 * 
 * <p>
 * Warning: you are responsible for obtaining technology licenses needed for
 * encoding and decoding sound data (see e.g. <a
 * href="http://mp3licensing.com">mp3licensing.com</a> for details on mp3
 * licensing).
 * </p>
 * 
 * @see SoundStreamBlock
 * @see SoundStreamHead2
 * @since SWF 1
 */
public final class SoundStreamHead extends Tag {

  private static final long serialVersionUID = 1L;

  /**
   * ADPCM compressed sound format (simple compression algorithm without
   * licensing issues).
   */
  public static final byte FORMAT_ADPCM = 1;
  /**
   * mp3 compressed sound format (for high-quality sound encoding) (since SWF
   * 4).
   */
  public static final byte FORMAT_MP3 = 2;
  /** 5.5 kHz sampling rate */
  public static final byte RATE_5500_HZ = 0;
  /** 11 kHz sampling rate */
  public static final byte RATE_11000_HZ = 1;
  /** 22 kHz sampling rate */
  public static final byte RATE_22000_HZ = 2;
  /** 44 kHz sampling rate */
  public static final byte RATE_44000_HZ = 3;
  private byte playbackRate;
  private boolean playbackStereo;
  private byte streamFormat;
  private byte streamRate;
  private boolean streamStereo;
  private int streamSampleCount;
  private short latencySeek;

  /**
   * <p>
   * Creates a new SoundStreamHead tag. Supply the encoding format of the stream
   * (one of the provided <code>FORMAT_...</code> constants), its sampling rate
   * (use <code>RATE_...</code> constants), and provide the channel count (mono
   * / stereo) and the average number of samples (for stereo sound: sample
   * pairs) per SoundStreamBlock.
   * </p>
   * 
   * <p>
   * The advisory playback parameters (sampling rate and channel count) are set
   * to be identical to the stream's parameters specified here. Use
   * <code>setPlayback...()</code> methods for changing these values.
   * </p>
   * 
   * @param streamFormat
   *          encoding format (mp3 or ADPCM)
   * @param rate
   *          sampling rate
   * @param stereo
   *          if <code>true</code>, sound is stereo, otherwise mono
   * @param sampleCount
   *          average number of samples (stereo: sample pairs) per block
   */
  public SoundStreamHead(byte streamFormat, byte rate, boolean stereo, int sampleCount) {
    super(TagType.SOUND_STREAM_HEAD);
    this.streamFormat = streamFormat;
    this.streamRate = rate;
    this.playbackRate = rate;
    this.streamStereo = stereo;
    this.playbackStereo = stereo;
    this.streamSampleCount = sampleCount;
  }

  SoundStreamHead() {
    super(TagType.SOUND_STREAM_HEAD);
  }

  /**
   * Used only with mp3 streaming sounds; sets the number of mp3 samples to be
   * skipped at the beginning of the sound stream (initial latency). This value
   * must match the number of mp3 samples to be skipped (i.e. the SeekSamples
   * value) from the first SoundStreamBlock.
   * 
   * @param latencySeek
   *          initial latency of sound stream
   */
  public void setLatencySeek(short latencySeek) {
    this.latencySeek = latencySeek;
  }

  /**
   * Used only with mp3 streaming sounds; returns the number of mp3 samples to
   * be skipped at the beginning of the sound stream (initial latency).
   * 
   * @return initial latency of sound stream
   */
  public short getLatencySeek() {
    return latencySeek;
  }

  /**
   * Sets the playback sampling rate.
   * 
   * @param rate
   *          advisory playback rate
   */
  public void setPlaybackRate(byte rate) {
    this.playbackRate = rate;
  }

  /**
   * Returns the playback sampling rate.
   * 
   * @return advisory playback rate
   */
  public byte getPlaybackRate() {
    return playbackRate;
  }

  /**
   * Sets the number of channels used at playback (mono/stereo).
   * 
   * @param isStereo
   *          <code>true</code> for stereo, <code>false for
   * 		  mono</code>
   */
  public void setPlaybackStereo(boolean isStereo) {
    this.playbackStereo = isStereo;
  }

  /**
   * Checks the number of channels used at playback (mono/stereo).
   * 
   * @return <code>true</code> for stereo, <code>false</code> for mono
   */
  public boolean isPlaybackStereo() {
    return playbackStereo;
  }

  /**
   * Specifies the encoding format of the sound stream (one of the
   * <code>FORMAT_...</code> constants).
   * 
   * @param streamFormat
   *          sound encoding format
   */
  public void setStreamFormat(byte streamFormat) {
    this.streamFormat = streamFormat;
  }

  /**
   * Returns the encoding format of the sound stream (one of the
   * <code>FORMAT_...</code> constants).
   * 
   * @return sound encoding format
   */
  public byte getStreamFormat() {
    return streamFormat;
  }

  /**
   * Specifies the sampling rate of the sound stream (one of the
   * <code>RATE_...</code> constants).
   * 
   * @param streamRate
   *          sampling rate
   */
  public void setStreamRate(byte streamRate) {
    this.streamRate = streamRate;
  }

  /**
   * Returns the sampling rate of the sound stream (one of the
   * <code>RATE_...</code> constants).
   * 
   * @return sampling rate
   */
  public byte getStreamRate() {
    return streamRate;
  }

  /**
   * Specifies the average number of samples (for stereo sound: sample pairs)
   * per SoundStreamBlock.
   * 
   * @param streamSampleCount
   *          average sample count per block
   */
  public void setStreamSampleCount(int streamSampleCount) {
    this.streamSampleCount = streamSampleCount;
  }

  /**
   * Returns the average number of samples (for stereo sound: sample pairs) per
   * SoundStreamBlock.
   * 
   * @return average sample count per block
   */
  public int getStreamSampleCount() {
    return streamSampleCount;
  }

  /**
   * Specifies whether the streaming sound is stereo or not.
   * 
   * @param streamStereo
   *          <code>true</code> if stereo, otherwise <code>false</code>
   */
  public void setStreamStereo(boolean streamStereo) {
    this.streamStereo = streamStereo;
  }

  /**
   * Checks whether the streaming sound is stereo or not.
   * 
   * @return <code>true</code> if stereo, otherwise <code>false</code>
   */
  public boolean isStreamStereo() {
    return streamStereo;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUnsignedBits(0, 4); // 4 reserved bits
    outStream.writeUnsignedBits(playbackRate, 2);
    outStream.writeUnsignedBits(1, 1); // playback sample size is always 1
    outStream.writeBooleanBit(playbackStereo);
    outStream.writeUnsignedBits(streamFormat, 4);
    outStream.writeUnsignedBits(streamRate, 2);
    outStream.writeUnsignedBits(1, 1); // streaming sample size is always 1
    outStream.writeBooleanBit(streamStereo);
    outStream.writeUI16(streamSampleCount);
    if (streamFormat == FORMAT_MP3) {
      outStream.writeSI16(latencySeek);
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    inStream.readUnsignedBits(4); // 4 reserved bits
    playbackRate = (byte) inStream.readUnsignedBits(2);
    inStream.readUnsignedBits(1); // playback sample size is always 1
    playbackStereo = inStream.readBooleanBit();
    streamFormat = (byte) inStream.readUnsignedBits(4);
    streamRate = (byte) inStream.readUnsignedBits(2);
    inStream.readUnsignedBits(1); // streaming sample size is always 1
    streamStereo = inStream.readBooleanBit();
    streamSampleCount = inStream.readUI16();
    if ((streamFormat == FORMAT_MP3) && (data.length > 4)) {
      latencySeek = inStream.readSI16();
    }
  }
}
