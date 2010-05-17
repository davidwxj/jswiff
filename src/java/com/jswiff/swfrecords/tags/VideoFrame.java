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
 * This tag provides a single frame of streaming video data. The format of the
 * video stream must be defined in a preceding <code>DefineVideoStream</code>
 * tag. The video frame rate is limited by the SWF frame rate, as an SWF frame
 * can contain at most one video frame.
 * 
 * @see DefineVideoStream
 * @since SWF 6
 */
public final class VideoFrame extends Tag {

  private static final long serialVersionUID = 1L;

  private int streamId;
  private int frameNum;
  private byte[] videoData;

  /**
   * Creates a new VideoFrame tag. Provide the character ID of the video stream,
   * the sequential frame number and the raw video data contained in this frame.
   * 
   * @param streamId
   *          character ID of video stream
   * @param frameNum
   *          frame number
   * @param videoData
   *          raw video frame data
   */
  public VideoFrame(int streamId, int frameNum, byte[] videoData) {
    super(TagType.VIDEO_FRAME);
    this.streamId = streamId;
    this.frameNum = frameNum;
    this.videoData = videoData;
  }
  
  VideoFrame() {
    super(TagType.VIDEO_FRAME);
  }

  /**
   * Sets the frame number. Frame numbers are sequential and start at 0.
   * 
   * @param frameNum
   *          sequential frame number
   */
  public void setFrameNum(int frameNum) {
    this.frameNum = frameNum;
  }

  /**
   * Returns the frame number. Frame numbers are sequential and start at 0.
   * 
   * @return sequential frame number
   */
  public int getFrameNum() {
    return frameNum;
  }

  /**
   * Sets the character ID of the video stream this frame belongs to.
   * 
   * @param streamId
   *          video stream character ID
   */
  public void setStreamId(int streamId) {
    this.streamId = streamId;
  }

  /**
   * Returns the character ID of the video stream this frame belongs to.
   * 
   * @return video stream character ID
   */
  public int getStreamId() {
    return streamId;
  }

  /**
   * Specifies the raw data contained in this video frame.
   * 
   * @param videoData
   *          video frame data (as byte array)
   */
  public void setVideoData(byte[] videoData) {
    this.videoData = videoData;
  }

  /**
   * Returns the raw data contained in this video frame.
   * 
   * @return video frame data (as byte array)
   */
  public byte[] getVideoData() {
    return videoData;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(streamId);
    outStream.writeUI16(frameNum);
    outStream.writeBytes(videoData);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    streamId = inStream.readUI16();
    frameNum = inStream.readUI16();
    int videoDataLength = data.length - 4;
    videoData = new byte[videoDataLength];
    System.arraycopy(data, 4, videoData, 0, videoDataLength);
  }
}
