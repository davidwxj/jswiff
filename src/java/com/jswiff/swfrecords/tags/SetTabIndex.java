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
 * This tag is used to set the tab order index of character instances. This
 * index determines the order in which character instances receive input focus
 * when repeatedly pressing the TAB key (aka 'tab order'). It also affects the
 * access order (aka 'reading order') when using screen readers.
 * 
 * @since SWF 7
 */
public final class SetTabIndex extends Tag {

  private static final long serialVersionUID = 1L;

  private int depth;
  private int tabIndex;

  /**
   * Creates a new SetTabIndex tag. Provide the depth of the character instance
   * and its tab order index.
   * 
   * @param depth
   *          depth the character instance is placed at
   * @param tabIndex
   *          tab order index (up to 65535)
   */
  public SetTabIndex(int depth, int tabIndex) {
    super(TagType.SET_TAB_INDEX);
    this.depth = depth;
    this.tabIndex = tabIndex;
  }

  SetTabIndex() {
    super(TagType.SET_TAB_INDEX);
  }

  /**
   * Sets the depth the character instance is placed at.
   * 
   * @param depth
   *          placement depth
   */
  public void setDepth(int depth) {
    this.depth = depth;
  }

  /**
   * Returns the depth the character instance is placed at.
   * 
   * @return placement depth
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Sets the tab order index of the character instance.
   * 
   * @param tabIndex
   *          tab order index
   */
  public void setTabIndex(int tabIndex) {
    this.tabIndex = tabIndex;
  }

  /**
   * Returns the tab order index of the character instance.
   * 
   * @return tab order index
   */
  public int getTabIndex() {
    return tabIndex;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(depth);
    outStream.writeUI16(tabIndex);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    depth = inStream.readUI16();
    tabIndex = inStream.readUI16();
  }
}
