package com.jswiff.swfrecords;

import java.io.Serializable;

public class SceneData implements Serializable {
  private int frameOffset;
  private String sceneName;
  
  public SceneData(int frameOffset, String sceneName) {
    this.frameOffset = frameOffset;
    this.sceneName = sceneName;
  }
  public int getFrameOffset() {
    return frameOffset;
  }
  public String getSceneName() {
    return sceneName;
  }
}
