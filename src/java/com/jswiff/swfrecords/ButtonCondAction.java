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
 * <p>
 * This record is used to define the button's event handlers. An event handler
 * assigns an action block to a mouse or key event.
 * </p>
 * 
 * <p>
 * Mouse events are transitions between mouse states relative to the button.
 * There are four states:
 * 
 * <ul>
 * <li>
 * idle = mouse pointer outside button area, mouse key up
 * </li>
 * <li>
 * outDown = mouse pointer outside button area, mouse key down
 * </li>
 * <li>
 * overUp = mouse pointer inside button area, mouse key up
 * </li>
 * <li>
 * overDown = mouse pointer inside button area, mouse key down
 * </li>
 * </ul>
 * </p>
 * 
 * <p>
 * An event handler reacts to one or more mouse events if the corresponding
 * event flags are set with the appropriate setter methods (e.g. with
 * <code>setIdleToOverUp(true);</code>)
 * </p>
 * 
 * <p>
 * A key event occurs when a certain specified key is pressed. The key the
 * handler is supposed to react to is specified through
 * <code>setKeyPress()</code>. For special keys (like insert or escape), use
 * the <code>KEY_...</code> constants. For ASCII keys, use their ASCII codes.
 * </p>
 * 
 * <p>
 * A particular event handler can be defined to react to more than one mouse
 * event and to (at most) one key event.
 * </p>
 */
public final class ButtonCondAction implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  public static final byte KEY_LEFT      = 1;
  public static final byte KEY_RIGHT     = 2;
  public static final byte KEY_HOME      = 3;
  public static final byte KEY_END       = 4;
  public static final byte KEY_INSERT    = 5;
  public static final byte KEY_DELETE    = 6;
  public static final byte KEY_BACKSPACE = 8;
  public static final byte KEY_ENTER     = 13;
  public static final byte KEY_UP        = 14;
  public static final byte KEY_DOWN      = 15;
  public static final byte KEY_PAGE_UP   = 16;
  public static final byte KEY_PAGE_DOWN = 17;
  public static final byte KEY_TAB       = 18;
  public static final byte KEY_ESCAPE    = 19;
  public static final byte KEY_SPACE     = 32;
  
  private boolean outDownToIdle; // releaseOutside
  private boolean outDownToOverDown; // dragOver
  private boolean idleToOverDown; // dragOver
  private boolean overDownToOutDown; // dragOut
  private boolean overDownToIdle; // dragOut
  private boolean overUpToOverDown; // press
  private boolean overDownToOverUp; // release
  private boolean overUpToIdle; // rollOut
  private boolean idleToOverUp; // rollOver
  private byte keyPress;
  private ActionBlock actions;

  /**
   * Creates a new ButtonCondAction instance.
   */
  public ButtonCondAction() {
    actions = new ActionBlock();
  }

  /**
   * Reads a ButtonCondAction instance from a bit stream.
   *
   * @param stream the input bit stream
   *
   * @throws IOException if an I/O error has occurred.
   */
  public ButtonCondAction(InputBitStream stream) throws IOException {
    idleToOverDown      = stream.readBooleanBit();
    outDownToIdle       = stream.readBooleanBit();
    outDownToOverDown   = stream.readBooleanBit();
    overDownToOutDown   = stream.readBooleanBit();
    overDownToOverUp    = stream.readBooleanBit();
    overUpToOverDown    = stream.readBooleanBit();
    overUpToIdle        = stream.readBooleanBit();
    idleToOverUp        = stream.readBooleanBit();
    keyPress            = (byte) stream.readUnsignedBits(7);
    overDownToIdle      = stream.readBooleanBit();
    actions             = new ActionBlock(stream);
  }

  /**
   * Returns the action block containing the actions this handler is supposed
   * to perform when the defined event occures.
   *
   * @return event handler action block
   */
  public ActionBlock getActions() {
    return actions;
  }

  /**
   * Sets the flag for the idleToOverDown event.  Equivalent to  <code>on
   * (dragOver)</code> in ActionScript.
   */
  public void setIdleToOverDown() {
    this.idleToOverDown = true;
  }

  /**
   * Gets the status of the flag for the idleToOverDown event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isIdleToOverDown() {
    return idleToOverDown;
  }

  /**
   * Sets the flag for the idleToOverUp event.  Equivalent to  <code>on
   * (rollOver)</code> in ActionScript.
   */
  public void setIdleToOverUp() {
    this.idleToOverUp = true;
  }

  /**
   * Gets the status of the flag for the idleToOverUp event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isIdleToOverUp() {
    return idleToOverUp;
  }

  /**
   * Sets the key code in order to react on pressing the corresponding key. For
   * special keys (e.g. escape) use the constants provided. For ASCII keys, 
   * use their ASCII code.
   *
   * @param keyPress the key code
   */
  public void setKeyPress(byte keyPress) {
    this.keyPress = keyPress;
  }

  /**
   * Returns the key code assigned to this handler in order to react on
   * pressing the corresponding key.
   *
   * @return the key code
   */
  public byte getKeyPress() {
    return keyPress;
  }

  /**
   * Sets the flag for the outDownToIdle event. Equivalent to  <code>on
   * (releaseOutside)</code> in ActionScript.
   */
  public void setOutDownToIdle() {
    this.outDownToIdle = true;
  }

  /**
   * Gets the status of the flag for the outDownToIdle event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOutDownToIdle() {
    return outDownToIdle;
  }

  /**
   * Sets the flag for the outDownToOverDown event. Equivalent to  <code>on
   * (dragOver)</code> in ActionScript.
   */
  public void setOutDownToOverDown() {
    this.outDownToOverDown = true;
  }

  /**
   * Gets the status of the flag for the outDownToOverDown event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOutDownToOverDown() {
    return outDownToOverDown;
  }

  /**
   * Sets the flag for the overDownToIdle event. Equivalent to  <code>on
   * (dragOut)</code> in ActionScript.
   */
  public void setOverDownToIdle() {
    this.overDownToIdle = true;
  }

  /**
   * Gets the status of the flag for the overDownToIdle event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOverDownToIdle() {
    return overDownToIdle;
  }

  /**
   * Sets the flag for the overDownToOutDown event. Equivalent to  <code>on
   * (dragOut)</code> in ActionScript.
   */
  public void setOverDownToOutDown() {
    this.overDownToOutDown = true;
  }

  /**
   * Gets the status of the flag for the overDownToOutDown event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOverDownToOutDown() {
    return overDownToOutDown;
  }

  /**
   * Sets the flag for the overDownToOverUp event. Equivalent to  <code>on
   * (release)</code> in ActionScript.
   */
  public void setOverDownToOverUp() {
    this.overDownToOverUp = true;
  }

  /**
   * Gets the status of the flag for the overDownToOverUp event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOverDownToOverUp() {
    return overDownToOverUp;
  }

  /**
   * Sets the flag for the overUpToIdle event. Equivalent to  <code>on
   * (rollOut)</code> in ActionScript.
   */
  public void setOverUpToIdle() {
    this.overUpToIdle = true;
  }

  /**
   * Gets the status of the flag for the overUpToIdle event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOverUpToIdle() {
    return overUpToIdle;
  }

  /**
   * Sets the flag for the overUpToOverDown event. Equivalent to  <code>on
   * (press)</code> in ActionScript.
   */
  public void setOverUpToOverDown() {
    this.overUpToOverDown = true;
  }

  /**
   * Gets the status of the flag for the overUpToOverDown event.
   *
   * @return <code>true</code> if flag is set, else <code>false</code>
   */
  public boolean isOverUpToOverDown() {
    return overUpToOverDown;
  }

  /**
   * Writes this record to a bit strea,
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    stream.writeBooleanBit(idleToOverDown);
    stream.writeBooleanBit(outDownToIdle);
    stream.writeBooleanBit(outDownToOverDown);
    stream.writeBooleanBit(overDownToOutDown);
    stream.writeBooleanBit(overDownToOverUp);
    stream.writeBooleanBit(overUpToOverDown);
    stream.writeBooleanBit(overUpToIdle);
    stream.writeBooleanBit(idleToOverUp);
    stream.writeUnsignedBits(keyPress, 7);
    stream.writeBooleanBit(overDownToIdle);
    actions.write(stream, true);
  }
}
