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
import com.jswiff.swfrecords.FrameData;
import com.jswiff.swfrecords.SceneData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This tag defines data describing scenes and frames.
 * 
 * @see SceneData
 * @see FrameData
 * @since SWF 9.
 */
public final class DefineSceneFrameData extends Tag {

  private static final long serialVersionUID = 1L;

  private List<SceneData> sceneEntries = new ArrayList<SceneData>();
  private List<FrameData> frameEntries = new ArrayList<FrameData>();

  public DefineSceneFrameData() {
    super(TagType.DEFINE_SCENE_FRAME_DATA);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    int count = inStream.readAbcInt();
    for (int i = 0; i < count; i++) {
      int frameOffset = inStream.readAbcInt();
      String sceneName = inStream.readString();
      sceneEntries.add(new SceneData(frameOffset, sceneName));
    }
    count = inStream.readAbcInt();
    for (int i = 0; i < count; i++) {
      int frameNumber = inStream.readAbcInt();
      String frameName = inStream.readString();
      frameEntries.add(new FrameData(frameNumber, frameName));
    }
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeAbcInt(sceneEntries.size());
    for (Iterator<SceneData> it = sceneEntries.iterator(); it.hasNext();) {
      SceneData sceneData = it.next();
      outStream.writeAbcInt(sceneData.getFrameOffset());
      outStream.writeString(sceneData.getSceneName());
    }
    outStream.writeAbcInt(frameEntries.size());
    for (Iterator<FrameData> it = frameEntries.iterator(); it.hasNext();) {
      FrameData frameData = it.next();
      outStream.writeAbcInt(frameData.getFrameNumber());
      outStream.writeString(frameData.getFrameLabel());
    }
  }

  public List<SceneData> getSceneEntries() {
    return sceneEntries;
  }

  public List<FrameData> getFrameEntries() {
    return frameEntries;
  }
}
