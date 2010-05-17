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
import com.jswiff.swfrecords.CXform;
import com.jswiff.swfrecords.Matrix;

import java.io.IOException;

/**
 * This tag adds a character instance to the display list. When a
 * <code>ShowFrame</code> tag is encountered, the instance is displayed at the
 * specified depth. A transform matrix affects the position, scale and rotation
 * of the character. A color effect can be applied by using an (optional) color
 * transform.
 * 
 * @see PlaceObject2
 * @see ShowFrame
 * @since SWF 1
 */
public final class PlaceObject extends Tag {

  private static final long serialVersionUID = 1L;

  private int characterId;
  private int depth;
  private Matrix matrix;
  private CXform colorTransform;

  /**
   * Creates a new PlaceObject tag.
   * 
   * @param characterId
   *          ID of the character to be placed
   * @param depth
   *          placement depth
   * @param matrix
   *          transform matrix (for translation, scaling, rotation etc.)
   * @param colorTransform
   *          color transform for color effects, optional (use <code>null</code>
   *          if not needed)
   */
  public PlaceObject(int characterId, int depth, Matrix matrix, CXform colorTransform) {
    super(TagType.PLACE_OBJECT);
    this.characterId = characterId;
    this.depth = depth;
    this.matrix = matrix;
    this.colorTransform = colorTransform;
  }

  PlaceObject() {
    super(TagType.PLACE_OBJECT);
  }

  /**
   * Sets the ID of the character to be placed to the display list.
   * 
   * @param characterId
   *          ID of character to be placed
   */
  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  /**
   * Returns the ID of the character to be placed to the display list.
   * 
   * @return character ID
   */
  public int getCharacterId() {
    return characterId;
  }

  /**
   * Returns the (optional) color transform.
   * 
   * @return color transform (<code>null</code> if not specified)
   */
  public CXform getColorTransform() {
    return colorTransform;
  }

  /**
   * Sets the depth the character will be placed at.
   * 
   * @param depth
   *          display depth
   */
  public void setDepth(int depth) {
    this.depth = depth;
  }

  /**
   * Returns the depth (i.e. the stacking order) the character instance is
   * supposed to be placed at.
   * 
   * @return display depth
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Returns the transform matrix used for affine transforms like translation,
   * scaling, rotation, shearing etc.
   * 
   * @return transform matrix
   */
  public Matrix getMatrix() {
    return matrix;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    outStream.writeUI16(depth);
    matrix.write(outStream);
    if (colorTransform != null) {
      colorTransform.write(outStream);
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    characterId = inStream.readUI16();
    depth = inStream.readUI16();
    matrix = new Matrix(inStream);
    try {
      // optional
      colorTransform = new CXform(inStream);
    } catch (IOException e) {
      // nothing to do, cxform missing
    }
  }
}
