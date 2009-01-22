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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.FrameData;
import com.jswiff.swfrecords.SceneData;

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
