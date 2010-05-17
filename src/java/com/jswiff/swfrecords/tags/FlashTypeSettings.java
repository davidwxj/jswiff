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
 * @since SWF 8
 */
public final class FlashTypeSettings extends Tag {

  private static final long serialVersionUID = 1L;

  /** TODO: Comments */
  public static final byte GRID_FIT_NONE = 0;
  /** TODO: Comments */
  public static final byte GRID_FIT_PIXEL = 1;
  /** TODO: Comments */
  public static final byte GRID_FIT_SUBPIXEL = 2;
  private int textId;
  private boolean flashType;
  private byte gridFit;
  private float thickness;
  private float sharpness;

  /**
   * Creates a new FlashTypeSettings instance.
   * 
   * @param textId
   *          TODO: Comments
   * @param flashType
   *          TODO: Comments
   */
  public FlashTypeSettings(int textId, boolean flashType) {
    super(TagType.FLASHTYPE_SETTINGS);
    this.textId = textId;
    this.flashType = flashType;
  }

  FlashTypeSettings() {
    super(TagType.FLASHTYPE_SETTINGS);
  }

  /**
   * TODO: Comments
   * 
   * @param flashType
   *          TODO: Comments
   */
  public void setFlashType(boolean flashType) {
    this.flashType = flashType;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public boolean isFlashType() {
    return flashType;
  }

  /**
   * TODO: Comments
   * 
   * @param gridFit
   *          TODO: Comments
   */
  public void setGridFit(byte gridFit) {
    this.gridFit = gridFit;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public byte getGridFit() {
    return gridFit;
  }

  /**
   * TODO: Comments
   * 
   * @param sharpness
   *          TODO: Comments
   */
  public void setSharpness(float sharpness) {
    this.sharpness = sharpness;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public double getSharpness() {
    return sharpness;
  }

  /**
   * TODO: Comments
   * 
   * @param textId
   *          TODO: Comments
   */
  public void setTextId(int textId) {
    this.textId = textId;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public int getTextId() {
    return textId;
  }

  /**
   * TODO: Comments
   * 
   * @param thickness
   *          TODO: Comments
   */
  public void setThickness(float thickness) {
    this.thickness = thickness;
  }

  /**
   * TODO: Comments
   * 
   * @return TODO: Comments
   */
  public double getThickness() {
    return thickness;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(textId);
    outStream.writeUnsignedBits(flashType ? 1 : 0, 2);
    outStream.writeUnsignedBits(gridFit, 3);
    outStream.writeUnsignedBits(0, 3);
    outStream.writeFloat(thickness);
    outStream.writeFloat(sharpness);
    outStream.writeUI8((short) 0);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    textId = inStream.readUI16();
    flashType = (inStream.readUnsignedBits(2) == 1);
    gridFit = (byte) inStream.readUnsignedBits(3);
    inStream.readUnsignedBits(3);
    thickness = inStream.readFloat();
    sharpness = inStream.readFloat();
    inStream.readUI8();
  }
}
