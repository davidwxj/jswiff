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
import com.jswiff.swfrecords.actions.ActionBlock;

import java.io.IOException;
import java.io.Serializable;


/**
 * This class defines an event handler for a sprite. Used within
 * <code>ClipActions</code>.
 *
 * @see ClipActions
 */
public final class ClipActionRecord implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private ClipEventFlags eventFlags;
  private short keyCode;
  private ActionBlock actions;

  /**
   * Creates a new ClipActionRecord instance.
   *
   * @param eventFlags event flags defining the events this handler is supposed
   *        to react upon.
   */
  public ClipActionRecord(ClipEventFlags eventFlags) {
    this.eventFlags   = eventFlags;
    actions           = new ActionBlock();
  }

  /**
   * Creates a new ClipActionRecord instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   * @param swfVersion SWF version
   *
   * @throws IOException if an I/O error has occurred.
   */
  public ClipActionRecord(InputBitStream stream, short swfVersion)
    throws IOException {
    eventFlags = new ClipEventFlags(stream, swfVersion);
    int actionRecordSize = (int) stream.readUI32();
    if ((swfVersion >= 6) && eventFlags.isKeyPress()) {
      keyCode = stream.readUI8();
      actionRecordSize--;
    }
    InputBitStream actionStream = new InputBitStream(
        stream.readBytes(actionRecordSize));
    actionStream.setANSI(stream.isANSI());
    actionStream.setShiftJIS(stream.isShiftJIS());
    actions = new ActionBlock(actionStream);
  }

  /**
   * Returns the action block to be performed when an event occurs. Use this
   * method to add actions to the action block.
   *
   * @return action block contained in handler
   */
  public ActionBlock getActions() {
    return actions;
  }

  /**
   * Returns the event flags indicating events this handler is supposed to
   * react upon.
   *
   * @return event flags
   */
  public ClipEventFlags getEventFlags() {
    return eventFlags;
  }

  /**
   * <p>
   * Sets the key code to trap. The keyPress event flag must be set, otherwise
   * this value is ignored.
   * </p>
   * 
   * <p>
   * For special keys (e.g. escape), use the constants provided in
   * <code>KeyCodes</code> (e.g. KEY_ESCAPE). For ASCII keys, use their ASCII
   * code.
   * </p>
   *
   * @param keyCode key code to trap
   */
  public void setKeyCode(short keyCode) {
    this.keyCode = keyCode;
  }

  /**
   * Returns the code of the key to trap. The keyPress event flag must be set,
   * otherwise this value is ignored.
   *
   * @return key code to trap
   */
  public short getKeyCode() {
    return keyCode;
  }

  /**
   * Writes this instance to a bit stream.
   *
   * @param stream target bit stream
   * @param swfVersion SWF version
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream, short swfVersion)
    throws IOException {
    eventFlags.write(stream, swfVersion);
    OutputBitStream actionStream = new OutputBitStream();
    actionStream.setANSI(stream.isANSI());
    actionStream.setShiftJIS(stream.isShiftJIS());
    actions.write(actionStream, true);
    byte[] actionBuffer  = actionStream.getData();
    int actionRecordSize = actionBuffer.length;
    if (eventFlags.isKeyPress()) {
      actionRecordSize++; // because of keyCode
    }
    stream.writeUI32(actionRecordSize);
    if (eventFlags.isKeyPress()) {
      stream.writeUI8(keyCode);
    }
    stream.writeBytes(actionBuffer);
  }
}
