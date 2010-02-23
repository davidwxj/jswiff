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
