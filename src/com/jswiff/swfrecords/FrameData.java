package com.jswiff.swfrecords;

import java.io.Serializable;

public class FrameData implements Serializable {
  private int frameNumber;
  private String frameLabel;
  
  public FrameData(int frameNumber, String frameLabel) {
    this.frameNumber = frameNumber;
    this.frameLabel = frameLabel;
  }
  public int getFrameNumber() {
    return frameNumber;
  }
  public String getFrameLabel() {
    return frameLabel;
  }
}
