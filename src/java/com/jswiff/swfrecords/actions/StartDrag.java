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

package com.jswiff.swfrecords.actions;

import com.jswiff.constants.TagConstants.ActionType;


/**
 * <p>
 * This action makes the target sprite draggable, i.e. it makes sure users can
 * drag it to another location.
 * </p>
 * 
 * <p>
 * Note: Only one sprite can be dragged at a time. The sprite remains draggable
 * until it is explicitly stopped by <code>ActionStopDrag</code> or until
 * <code>StartDrag</code> is called for another sprite.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop sprite</code> (the sprite to be dragged)<br>
 * <code>pop lockCenter</code> (if nonzero, mouse is locked to sprite center,
 * otherwise it is locked to the mouse position at the time the dragging started)<br>
 * <code>pop constrain</code> (if nonzero, four values which define a
 * constraint window are popped off the stack)<br>
 * <code>pop y2</code> (bottom constraint coordinate)<br>
 * <code>pop x2</code> (right constraint coordinate)<br>
 * <code>pop y1</code> (top constraint coordinate)<br>
 * <code>pop x1</code> (left constraint coordinate)<br>
 * Constraint values are relative to the coordinates of the sprite's parent.
 * </p>
 * 
 * <p>
 * ActionScript equivalents: <code>startDrag()</code>,
 * <code>MovieClip.startDrag()</code>
 * </p>
 *
 * @since SWF 5
 */
public final class StartDrag extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new StartDrag action.
   */
  public StartDrag() {
    super(ActionType.START_DRAG);
  }

}
