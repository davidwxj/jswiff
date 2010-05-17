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
 * Creates an object and initializes it with values from the stack.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code> pop n</code> (property number)<br>
 * <code> pop value_1</code> (1st property value)<br>
 * <code> pop name_1</code> (1st property name)<br>
 * <code> pop value_2</code> (2nd property value)<br>
 * <code> pop name_2</code> (2nd property name)<br>
 * <code> ...<br>
 * pop value_n</code> (n-th property value)<br>
 * <code> pop name_n</code> (n-th property name)<br>
 * <code> push obj</code> (the new object)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: object literal (e.g. <code>{width: 150, height:
 * 100}</code>)
 * </p>
 *
 * @since SWF 5
 */
public final class InitObject extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates a new InitObject action.
   */
  public InitObject() {
    super(ActionType.INIT_OBJECT);
  }

}
