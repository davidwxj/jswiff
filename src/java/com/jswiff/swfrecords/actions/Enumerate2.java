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
 * Iterates over all properties of an object and pushes their names to the
 * stack. The difference to <code>Enumerate</code> is that
 * <code>Enumerate2</code> uses a stack argument of object type rather than
 * the object's name as string.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop obj</code> (object instance)<br>
 * <code>push null</code> (indicates the end of the property list)<br>
 * <code>push prop1</code> (1st property name)<br>
 * <code>push prop2</code> (2nd property name)<br>
 * <code>...</code><br>
 * <code>push propn</code> (n-th property name)<br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>for..in</code> loop
 * </p>
 *
 * @since SWF 6
 */
public final class Enumerate2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new Enumerate2 action.
   */
  public Enumerate2() {
    super(ActionType.ENUMERATE_2);
  }

}
