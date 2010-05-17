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
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.ActionBlock;

import java.io.IOException;

/**
 * <p>
 * This tag contains a series of initialization actions for a particular sprite.
 * These actions are executed only once, before the first instatiation of the
 * sprite. Typically used for class definitions.
 * </p>
 * 
 * <p>
 * This tag is used to implement the <code>#initclip</code> ActionScript
 * compiler directive.
 * </p>
 * 
 * @since SWF 6
 */
public final class DoInitAction extends Tag {

  private static final long serialVersionUID = 1L;

  private int spriteId;
  private ActionBlock initActions;

  /**
   * Creates a new DoInitAction tag. Supply the character ID of the sprite the
   * initialization actions apply to. After creation, use
   * <code>addAction()</code> to add actions to the contained action block.
   * 
   * @param spriteId
   *          character ID of sprite to be initialized
   */
  public DoInitAction(int spriteId) {
    super(TagType.DO_INIT_ACTION);
    this.spriteId = spriteId;
    initActions = new ActionBlock();
  }

  DoInitAction() {
    super(TagType.DO_INIT_ACTION);
  }

  /**
   * Returns the action block containing the initialization action records. Use
   * <code>addAction()</code> to add an action record to this block.
   * 
   * @return initialization action block
   */
  public ActionBlock getInitActions() {
    return initActions;
  }

  /**
   * Sets the character ID of the sprite the initialization actions apply to.
   * 
   * @param spriteId
   *          sprite's character ID
   */
  public void setSpriteId(int spriteId) {
    this.spriteId = spriteId;
  }

  /**
   * Returns the character ID of the sprite the initialization actions apply to.
   * 
   * @return sprite's character ID
   */
  public int getSpriteId() {
    return spriteId;
  }

  /**
   * Adds an initialization action record.
   * 
   * @param action
   *          init action record
   */
  public void addAction(Action action) {
    initActions.addAction(action);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(spriteId);
    initActions.write(outStream, true);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    if (getSWFVersion() < 6) {
      if (isJapanese()) {
        inStream.setShiftJIS(true);
      } else {
        inStream.setANSI(true);
      }
    }
    spriteId = inStream.readUI16();
    initActions = new ActionBlock(inStream);
  }
}
