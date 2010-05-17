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

import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * This class is used to define line styles used in morph sequences. Line
 * styles are defined in pairs: for start and for end shapes.
 *
 * @see MorphLineStyles
 * @see com.jswiff.swfrecords.tags.DefineMorphShape
 */
public interface MorphLineStyleTag {

  /**
   * Returns the color of lines used in the end shape of the morph sequence.
   *
   * @return RGBA color of lines in end shape
   */
  public RGBA getEndColor();

  /**
   * Returns the width of lines used in the end shape of the morph sequence.
   *
   * @return width of lines in end shape in twips (1/20 px)
   */
  public int getEndWidth();

  /**
   * Returns the color of lines used in the start shape of the morph sequence.
   *
   * @return RGBA color of lines in start shape
   */
  public RGBA getStartColor();

  /**
   * Returns the width of lines used in the start shape of the morph sequence.
   *
   * @return width of lines in start shape in twips (1/20 px)
   */
  public int getStartWidth();

  public void write(OutputBitStream stream) throws IOException;

}
