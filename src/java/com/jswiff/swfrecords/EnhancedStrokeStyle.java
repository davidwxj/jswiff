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

package com.jswiff.swfrecords;

import com.jswiff.constants.TagConstants.CapStyle;
import com.jswiff.constants.TagConstants.JointStyle;
import com.jswiff.constants.TagConstants.ScaleStrokeMethod;

import java.io.Serializable;

/**
 * Base class for the new enhanced stroke line styles introduced in SWF 8.
 */
public abstract class EnhancedStrokeStyle implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private CapStyle startCapStyle  = CapStyle.ROUND;
  private CapStyle endCapStyle    = CapStyle.ROUND;
  private JointStyle jointStyle   = JointStyle.ROUND;
  private ScaleStrokeMethod scaleStroke = ScaleStrokeMethod.BOTH;
  private boolean close           = true;
  private double miterLimit       = 3;
  private boolean pixelHinting;
  
  public void setClose(boolean close) {
    this.close = close;
  }

  public boolean isClose() {
    return close;
  }
  
  public void setStartCapStyle(CapStyle startCapStyle) {
    this.startCapStyle = startCapStyle;
  }

  public CapStyle getStartCapStyle() {
    return startCapStyle;
  }
  
  public void setEndCapStyle(CapStyle endCapStyle) {
    this.endCapStyle = endCapStyle;
  }

  public CapStyle getEndCapStyle() {
    return endCapStyle;
  }
  
  public void setJointStyle(JointStyle jointStyle) {
    this.jointStyle = jointStyle;
  }

  public JointStyle getJointStyle() {
    return jointStyle;
  }
  
  public void setScaleStroke(ScaleStrokeMethod scaleStroke) {
    this.scaleStroke = scaleStroke;
  }

  public ScaleStrokeMethod getScaleStroke() {
    return scaleStroke;
  }

  public void setMiterLimit(double miterLimit) {
    this.miterLimit = miterLimit;
  }

  public double getMiterLimit() {
    return miterLimit;
  }

  public void setPixelHinting(boolean pixelHinting) {
    this.pixelHinting = pixelHinting;
  }

  public boolean isPixelHinting() {
    return pixelHinting;
  }
  
}
