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
 * This tag defines the format of a streaming video data contained in subsequent
 * <code>VideoFrame</code> tags.
 * 
 * @see VideoFrame
 * @since SWF 6
 */
public final class DefineVideoStream extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  /** Use deblocking setting from video packet. */
  public static final byte DEBLOCKING_PACKET = 0;
  /** Do not use deblocking filter. */
  public static final byte DEBLOCKING_OFF = 1;
  /** Use deblocking filter. */
  public static final byte DEBLOCKING_ON = 2;
  /**
   * Some movies do not specify a codec ID. Do not use this value when creating
   * movies from scratch.
   */
  public static final short CODEC_UNDEFINED = 0;
  /** Use Sorenson H.263 codec (an enhanced subset of ITU H.263v1). */
  public static final short CODEC_SORENSON_H263 = 2;
  /**
   * Use Screen Video codec (optimized for screen captures in motion) (since SWF
   * 7).
   */
  public static final short CODEC_SCREEN_VIDEO = 3;
  /** TODO: Comments */
  public static final short CODEC_VP6 = 4;
  /** TODO: Comments */
  public static final short CODEC_VP6_ALPHA = 5;
  /** TODO: Comments */
  public static final short CODEC_SCREEN_VIDEO_V2 = 6;
  private int numFrames;
  private int width;
  private int height;
  private byte deblocking;
  private boolean smoothing;
  private short codecId;

  /**
   * Creates a new DefineVideoStream tag. Supply the character ID, the number of
   * frames (i.e. subsequent <code>VideoFrame</code> tags) and the dimensions of
   * the video. Specify if a deblocking filter should be used at playback to
   * reduce blocking artifacts (use <code>DEBLOCKING_...</code> constants) and
   * whether to apply smoothing. Finally, specify which codec is used for video
   * encoding. Supported codecs are Sorenson H.263 (an enhanced subset of ITU
   * H.263v1) and, since SWF 7, Screen Video, a format optimized for screen
   * captures in motion (use either <code>CODEC_SORENSON_H263</code> or
   * <code>CODEC_SCREEN_VIDEO</code>).
   * 
   * @param characterId
   *          character ID of video
   * @param numFrames
   *          number of video frames
   * @param width
   *          video width in pixels
   * @param height
   *          video height in pixels
   * @param deblocking
   *          deblocking setting (on /off / use packet setting - see
   *          <code>DEBLOCKING_...</code> constants)
   * @param smoothing
   *          if <code>true</code>, video is smoothed
   * @param codecId
   *          video encoding algorithm (<code>CODEC_SORENSON_H263</code> or
   *          <code>CODEC_SCREEN_VIDEO</code>)
   */
  public DefineVideoStream(int characterId, int numFrames, int width, int height, byte deblocking, boolean smoothing,
      short codecId) {
    super(TagType.DEFINE_VIDEO_STREAM);
    this.characterId = characterId;
    this.numFrames = numFrames;
    this.width = width;
    this.height = height;
    this.deblocking = deblocking;
    this.smoothing = smoothing;
    this.codecId = codecId;
  }

  DefineVideoStream() {
    super(TagType.DEFINE_VIDEO_STREAM);
  }

  /**
   * Sets the codec used for video encoding. Supported codecs are the Sorenson
   * H.263 bitstream format (an enhanced subset of ITU H.263v1) and, as of SWF
   * 7, the Screen Video bitstream format, a format optimized for screen
   * captures in motion.
   * 
   * @param codecId
   *          video codec (<code>CODEC_SORENSON_H263</code> or
   *          <code>CODEC_SCREEN_VIDEO</code>)
   */
  public void setCodecId(short codecId) {
    this.codecId = codecId;
  }

  /**
   * Returns the codec used for video encoding. Supported codecs are the
   * Sorenson H.263 bitstream format (an enhanced subset of ITU H.263v1) and, as
   * of SWF 7, the Screen Video bitstream format, a format optimized for screen
   * captures in motion.
   * 
   * @return video codec (<code>CODEC_SORENSON_H263</code> or
   *         <code>CODEC_SCREEN_VIDEO</code>)
   */
  public short getCodecId() {
    return codecId;
  }

  /**
   * Sets the deblocking setting for the video, i.e. if a deblocking filter
   * should be used at playback to reduce blocking artifacts and improve
   * subjective visual quality.
   * 
   * @param deblocking
   *          deblocking setting (one of the <code>DEBLOCKING_...</code>
   *          constants)
   */
  public void setDeblocking(byte deblocking) {
    this.deblocking = deblocking;
  }

  /**
   * Checks the deblocking setting for the video, i.e. if a deblocking filter
   * should be used at playback to reduce blocking artifacts and improve
   * subjective visual quality.
   * 
   * @return deblocking setting (one of the <code>DEBLOCKING_...</code>
   *         constants)
   */
  public byte getDeblocking() {
    return deblocking;
  }

  /**
   * Sets the height of the video in pixels.
   * 
   * @param height
   *          video height in px
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Returns the height of the video in pixels.
   * 
   * @return video height in px
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the number of video frames, i.e. the number of <code>VideoFrame</code>
   * tags following this video stream definition.
   * 
   * @param numFrames
   *          video frame count
   */
  public void setNumFrames(int numFrames) {
    this.numFrames = numFrames;
  }

  /**
   * Returns the number of video frames, i.e. the number of
   * <code>VideoFrame</code> tags following this video stream definition.
   * 
   * @return video frame count
   */
  public int getNumFrames() {
    return numFrames;
  }

  /**
   * Specifies whether smoothing is supposed to be applied to the video at
   * playback.
   * 
   * @param smoothing
   *          <code>true</code> if smoothing enabled, otherwise
   *          <code>false</code>
   */
  public void setSmoothing(boolean smoothing) {
    this.smoothing = smoothing;
  }

  /**
   * Checks whether smoothing is supposed to be applied to the video at
   * playback.
   * 
   * @return <code>true</code> if smoothing enabled, otherwise
   *         <code>false</code>
   */
  public boolean isSmoothing() {
    return smoothing;
  }

  /**
   * Sets the width of the video in pixels.
   * 
   * @param width
   *          video width in px
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Returns the width of the video in pixels.
   * 
   * @return video width in px
   */
  public int getWidth() {
    return width;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    outStream.writeUI16(numFrames);
    outStream.writeUI16(width);
    outStream.writeUI16(height);
    outStream.writeUnsignedBits(0, 5); // 5 reserved bits
    outStream.writeUnsignedBits(deblocking, 2);
    outStream.writeBooleanBit(smoothing);
    outStream.writeUI8(codecId);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    numFrames = inStream.readUI16();
    width = inStream.readUI16();
    height = inStream.readUI16();
    inStream.readUnsignedBits(5); // 5 reserved bits
    deblocking = (byte) inStream.readUnsignedBits(2);
    smoothing = inStream.readBooleanBit();
    codecId = inStream.readUI8();
  }
}
