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

import com.jswiff.constants.TagConstants.FilterType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * TODO: Comments
 */
public final class BevelFilter extends Filter {
  
  private static final long serialVersionUID = 1L;
  
  private RGBA highlightColor;
  private RGBA shadowColor;
  private double x;
  private double y;
  private double angle;
  private double distance;
  private double strength;
  private boolean inner;
  private int quality;
  private boolean knockout;
  private boolean onTop;

  /**
   * Creates a new BevelFilter instance.
   */
  public BevelFilter() {
    super(FilterType.BEVEL);
    initDefaults();
  }

  /**
   * Creates a new BevelFilter instance.
   *
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public BevelFilter(InputBitStream stream) throws IOException {
    super(FilterType.BEVEL);
    highlightColor   = new RGBA(stream);
    shadowColor      = new RGBA(stream);
    x                = stream.readFP32();
    y                = stream.readFP32();
    angle            = stream.readFP32();
    distance         = stream.readFP32();
    strength         = stream.readFP16();
    inner            = stream.readBooleanBit();
    knockout         = stream.readBooleanBit();
    stream.readBooleanBit();
    onTop     = stream.readBooleanBit();
    quality   = (int) stream.readUnsignedBits(4);
  }

  /**
   * TODO: Comments
   *
   * @param angle TODO: Comments
   */
  public void setAngle(double angle) {
    this.angle = angle;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getAngle() {
    return angle;
  }

  /**
   * TODO: Comments
   *
   * @param distance TODO: Comments
   */
  public void setDistance(double distance) {
    this.distance = distance;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getDistance() {
    return distance;
  }

  /**
   * TODO: Comments
   *
   * @param color TODO: Comments
   */
  public void setHighlightColor(RGBA color) {
    this.highlightColor = color;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public RGBA getHighlightColor() {
    return highlightColor;
  }

  /**
   * TODO: Comments
   *
   * @param inner TODO: Comments
   */
  public void setInner(boolean inner) {
    this.inner = inner;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean isInner() {
    return inner;
  }

  /**
   * TODO: Comments
   *
   * @param knockout TODO: Comments
   */
  public void setKnockout(boolean knockout) {
    this.knockout = knockout;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean isKnockout() {
    return knockout;
  }

  /**
   * TODO: Comments
   *
   * @param onTop TODO: Comments
   */
  public void setOnTop(boolean onTop) {
    this.onTop = onTop;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public boolean isOnTop() {
    return onTop;
  }

  /**
   * TODO: Comments
   *
   * @param quality TODO: Comments
   */
  public void setQuality(int quality) {
    this.quality = quality;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public int getQuality() {
    return quality;
  }

  /**
   * TODO: Comments
   *
   * @param color TODO: Comments
   */
  public void setShadowColor(RGBA color) {
    this.shadowColor = color;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public RGBA getShadowColor() {
    return shadowColor;
  }

  /**
   * TODO: Comments
   *
   * @param strength TODO: Comments
   */
  public void setStrength(double strength) {
    this.strength = strength;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getStrength() {
    return strength;
  }

  /**
   * TODO: Comments
   *
   * @param x TODO: Comments
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getX() {
    return x;
  }

  /**
   * TODO: Comments
   *
   * @param y TODO: Comments
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getY() {
    return y;
  }

  /**
   * TODO: Comments
   *
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public void write(OutputBitStream stream) throws IOException {
    highlightColor.write(stream);
    shadowColor.write(stream);
    stream.writeFP32(x);
    stream.writeFP32(y);
    stream.writeFP32(angle);
    stream.writeFP32(distance);
    stream.writeFP16(strength);
    stream.writeBooleanBit(inner);
    stream.writeBooleanBit(knockout);
    stream.writeBooleanBit(true);
    stream.writeBooleanBit(onTop);
    stream.writeUnsignedBits(quality, 4);
  }

  private void initDefaults() {
    highlightColor   = RGBA.WHITE;
    shadowColor      = RGBA.BLACK;
    x                = 4;
    y                = 4;
    angle            = Math.PI / 4;
    distance         = 4;
    strength         = 1;
    quality          = 1;
    inner            = true;
  }
}
