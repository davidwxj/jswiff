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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * Unlike <code>StraightEdgeRecord</code> and <code>CurvedEdgeRecord</code>,
 * this shape record is used for changing the actual style rather than
 * defining new shapes. There are three ways to do this:
 * 
 * <ul>
 * <li>
 * select a previously defined fill or line style
 * </li>
 * <li>
 * move current drawing position to specified coordinates
 * </li>
 * <li>
 * replace current fill and line style arrays with new ones
 * </li>
 * </ul>
 */
public final class StyleChangeRecord extends ShapeRecord {

  private static final long serialVersionUID = 1L;
  
  private int moveToX;
  private int moveToY;
  private int fillStyle0;
  private int fillStyle1;
  private int lineStyle;
  private FillStyleArray fillStyles;
  private LineStyleArray lineStyles;
  private byte numFillBits; // Set only when hasNewStyles! Initial value in Shape/ShapeWithStyle
  private byte numLineBits; // Set only when hasNewStyles! Initial value in Shape/ShapeWithStyle
  private boolean hasNewStyles;
  private boolean hasLineStyle;
  private boolean hasFillStyle1;
  private boolean hasFillStyle0;
  private boolean hasMoveTo;

  /**
   * Creates a new StyleChangeRecord instance.
   */
  public StyleChangeRecord() {
    // empty
  }

  StyleChangeRecord(
    InputBitStream stream, byte flags, byte numFillBits, byte numLineBits,
    boolean useNewLineStyle, boolean hasAlpha) throws IOException {
    hasNewStyles    = ((flags & 16) != 0);
    hasLineStyle    = ((flags & 8) != 0);
    hasFillStyle1   = ((flags & 4) != 0);
    hasFillStyle0   = ((flags & 2) != 0);
    hasMoveTo       = ((flags & 1) != 0);
    if (hasMoveTo) {
      int moveBits = (int) stream.readUnsignedBits(5);
      moveToX   = (int) stream.readSignedBits(moveBits);
      moveToY   = (int) stream.readSignedBits(moveBits);
    }
    if (hasFillStyle0) {
      fillStyle0 = (int) stream.readUnsignedBits(numFillBits);
    }
    if (hasFillStyle1) {
      fillStyle1 = (int) stream.readUnsignedBits(numFillBits);
    }
    if (hasLineStyle) {
      lineStyle = (int) stream.readUnsignedBits(numLineBits);
    }
    if (hasNewStyles) {
      fillStyles         = new FillStyleArray(stream, hasAlpha);
      if (useNewLineStyle) {
        lineStyles         = new LineStyleArray(stream);
      } else {
        lineStyles         = new LineStyleArray(stream, hasAlpha);
      }
      this.numFillBits   = (byte) stream.readUnsignedBits(4); // set new values
      this.numLineBits   = (byte) stream.readUnsignedBits(4);
    } else {
      this.numFillBits   = numFillBits; // set passed values, no new values available
      this.numLineBits   = numLineBits;
    }
  }

  /**
   * Sets the value for fillStyle0 (i.e. the fill style for non-overlapping
   * shape portions). This value is used as index in the current fill style
   * array. Warning: style array indexes start with 1. A fill style value of 0
   * means the path is not filled.
   *
   * @param fillStyle0 fill style array index
   */
  public void setFillStyle0(int fillStyle0) {
    this.fillStyle0   = fillStyle0;
    hasFillStyle0     = true;
  }

  /**
   * Returns the value for fillStyle0 (i.e. the fill style for non-overlapping
   * shape portions). This value is used as index in the current fill style
   * array. A fill style value of 0 means the path is not filled. Check with
   * <code>hasFillStyle0()</code> first if the style has been specified.
   *
   * @return the value for fillStyle0
   */
  public int getFillStyle0() {
    return fillStyle0;
  }

  /**
   * Sets the value for fillStyle1 (i.e. the fill style for overlapping shape
   * portions). This value is used as index in the current fill style array.
   * Warning: style array indexes start with 1. A fill style value of 0 means
   * the path is not filled.
   *
   * @param fillStyle1 fill style array index
   */
  public void setFillStyle1(int fillStyle1) {
    this.fillStyle1   = fillStyle1;
    hasFillStyle1     = true;
  }

  /**
   * Returns the value for fillStyle1 (i.e. the fill style for overlapping
   * shape portions). This value is used as index in the current fill style
   * array. A fill style value of 0 means the path is not filled. Check with
   * <code>hasFillStyle1()</code> first if the style has been specified.
   *
   * @return the value for fillStyle1
   */
  public int getFillStyle1() {
    return fillStyle1;
  }

  /**
   * Sets the value for the line style (i.e. the fill style for overlapping
   * shapes). This value is used as index in the current line style array.
   * Warning: style array indexes start with 1. A line style value of 0 means
   * the path has no stroke.
   *
   * @param lineStyle line style array index
   */
  public void setLineStyle(int lineStyle) {
    this.lineStyle   = lineStyle;
    hasLineStyle     = true;
  }

  /**
   * Returns the value for the line style (i.e. the fill style for overlapping
   * shapes). This value is used as index in the current line style array. A
   * line style value of 0 means the path has no stroke. Check with
   * <code>hasLineStyle()</code> first if the style has been specified.
   *
   * @return line style array index
   */
  public int getLineStyle() {
    return lineStyle;
  }

  /**
   * Sets the current drawing position to new coordinates.
   *
   * @param x x coordinate in twips
   * @param y y coordinate in twips
   */
  public void setMoveTo(int x, int y) {
    moveToX     = x;
    moveToY     = y;
    hasMoveTo   = true;
  }

  /**
   * Returns the x coordinate of the current drawing position in twips (i.e.
   * 1/20 px). Check with <code>hasMoveTo()</code> first if the value is set.
   *
   * @return x coordinate in twips
   */
  public int getMoveToX() {
    return moveToX;
  }

  /**
   * Returns the y coordinate of the current drawing position in twips (i.e.
   * 1/20 px). Check with <code>hasMoveTo()</code> first if the value is set.
   *
   * @return y coordinate in twips
   */
  public int getMoveToY() {
    return moveToY;
  }

  /**
   * Returns the new set of fill styles which is supposed to replace the old
   * one. Check with <code>hasNewStyles()</code> if new styles have been
   * specified.
   *
   * @return new fill style array
   */
  public FillStyleArray getNewFillStyles() {
    return fillStyles;
  }

  /**
   * Returns the new set of line styles which is supposed to replace the old
   * one. Check with <code>hasNewStyles()</code> if new styles have been
   * specified.
   *
   * @return new line style array
   */
  public LineStyleArray getNewLineStyles() {
    return lineStyles;
  }

  /**
   * Specifies a new set of line and fill styles, thus replacing the old ones.
   * This is not supported within the <code>DefineShape</code> tag.
   *
   * @param lineStyles a line style array
   * @param fillStyles a fill style array
   */
  public void setNewStyles(
    LineStyleArray lineStyles, FillStyleArray fillStyles) {
    this.lineStyles   = lineStyles;
    this.fillStyles   = fillStyles;
    hasNewStyles      = true;
  }

  /**
   * Checks if a value for fillStyle0 is selected. This value is used as index
   * in the current fill style array.
   *
   * @return <code>true</code> if fillStyle0 specified, else <code>false</code>
   */
  public boolean hasFillStyle0() {
    return hasFillStyle0;
  }

  /**
   * Checks if a value for fillStyle1 is selected. This value is used as index
   * in the current fill style array.
   *
   * @return <code>true</code> if fillStyle1 specified, else <code>false</code>
   */
  public boolean hasFillStyle1() {
    return hasFillStyle1;
  }

  /**
   * Checks if a value for the line style is selected. This value is used as
   * index in the current line style array.
   *
   * @return <code>true</code> if line style specified, else <code>false</code>
   */
  public boolean hasLineStyle() {
    return hasLineStyle;
  }

  /**
   * Checks if the style change record moves the current drawing position.
   *
   * @return <code>true</code> if new coordinates for the current drawing
   *         position were specified, else <code>false</code>
   */
  public boolean hasMoveTo() {
    return hasMoveTo;
  }

  /**
   * Checks if the style change record specifies a new set of line and fill
   * styles, thus replacing the old ones.
   *
   * @return <code>true</code> if line and fill style array specified
   */
  public boolean hasNewStyles() {
    return hasNewStyles;
  }

  void setNumFillBits(byte numFillBits) {
    this.numFillBits = numFillBits;
  }

  byte getNumFillBits() {
    return numFillBits;
  }

  void setNumLineBits(byte numLineBits) {
    this.numLineBits = numLineBits;
  }

  byte getNumLineBits() {
    return numLineBits;
  }

  void write(OutputBitStream stream, byte fillBits, byte lineBits)
    throws IOException {
    stream.writeUnsignedBits(0, 1); // non-edge
    stream.writeBooleanBit(hasNewStyles);
    stream.writeBooleanBit(hasLineStyle);
    stream.writeBooleanBit(hasFillStyle1);
    stream.writeBooleanBit(hasFillStyle0);
    stream.writeBooleanBit(hasMoveTo);
    if (hasMoveTo) {
      int moveBits = OutputBitStream.getSignedBitsLength(moveToX);
      moveBits = Math.max(
          moveBits, OutputBitStream.getSignedBitsLength(moveToY));
      stream.writeUnsignedBits(moveBits, 5);
      stream.writeSignedBits(moveToX, moveBits);
      stream.writeSignedBits(moveToY, moveBits);
    }
    if (hasFillStyle0) {
      stream.writeUnsignedBits(fillStyle0, fillBits);
    }
    if (hasFillStyle1) {
      stream.writeUnsignedBits(fillStyle1, fillBits);
    }
    if (hasLineStyle) {
      stream.writeUnsignedBits(lineStyle, lineBits);
    }
    if (hasNewStyles) {
      fillStyles.write(stream);
      lineStyles.write(stream);
      stream.writeUnsignedBits(this.numFillBits, 4);
      stream.writeUnsignedBits(this.numLineBits, 4);
    }
  }
}
