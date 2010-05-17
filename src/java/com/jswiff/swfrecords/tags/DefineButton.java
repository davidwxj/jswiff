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
import com.jswiff.swfrecords.ButtonRecord;
import com.jswiff.swfrecords.actions.ActionBlock;

import java.io.IOException;
import java.util.Vector;

/**
 * <p>
 * This tag defines a button character. It contains an array of at least one
 * <code>ButtonRecord</code> instance in order to define the button's appearance
 * depending on it's state. See <code>ButtonRecord</code> for details on button
 * states.
 * </p>
 * 
 * <p>
 * DefineButton also includes an action block which contains actions performed
 * when when the button is clicked and released.
 * </p>
 * 
 * @see ButtonRecord
 * @since SWF 1
 */
public final class DefineButton extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private ButtonRecord[] characters;
  private ActionBlock actionBlock;

  /**
   * Creates a new DefineButton tag.
   * 
   * @param characterId
   *          the button's character ID
   * @param characters
   *          array of button records
   * 
   * @throws IllegalArgumentException
   *           if button record array is <code>null</code> or empty
   */
  public DefineButton(int characterId, ButtonRecord[] characters) {
    super(TagType.DEFINE_BUTTON);
    if ((characters == null) || (characters.length == 0)) {
      throw new IllegalArgumentException("At least one button record is needed!");
    }
    this.characterId = characterId;
    this.characters = characters;
  }
  
  DefineButton() {
    super(TagType.DEFINE_BUTTON);
  }

  /**
   * Returns the button's action block. Can be used to add new actions.
   * 
   * @return the action block assigned to the button
   */
  public ActionBlock getActions() {
    if (actionBlock == null) {
      actionBlock = new ActionBlock(); // lazy init
    }
    return actionBlock;
  }

  /**
   * Sets an array of at least one <code>ButtonRecord</code> instance defining
   * the appearance of the button depending on it's state.
   * 
   * @param characters
   *          button records
   */
  public void setCharacters(ButtonRecord[] characters) {
    this.characters = characters;
  }

  /**
   * Returns an array of at least one <code>ButtonRecord</code> instance
   * defining the appearance of the button depending on it's state.
   * 
   * @return button records
   */
  public ButtonRecord[] getCharacters() {
    return characters;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(characterId);
    for (int i = 0; i < characters.length; i++) {
      characters[i].write(outStream, false);
    }
    outStream.writeUI8((short) 0); // CharacterEndFlag
    getActions().write(outStream, true);
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
    characterId = inStream.readUI16();
    Vector<ButtonRecord> buttonRecords = new Vector<ButtonRecord>();
    do {
      // check next byte without using stream (to be able to read it again if !=
      // 0)
      if (data[(int) inStream.getOffset()] == 0) {
        inStream.readUI8(); // ignore CharacterEndFlag
        break;
      }
      buttonRecords.add(new ButtonRecord(inStream, false));
    } while (true);
    characters = new ButtonRecord[buttonRecords.size()];
    buttonRecords.copyInto(characters);
    actionBlock = new ActionBlock(inStream);
  }

}
