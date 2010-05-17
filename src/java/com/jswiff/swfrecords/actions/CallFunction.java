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
 * Invokes a function, e.g. a user-defined ActionScript function (defined with
 * <code>DefineFunction</code> or <code>DefineFunction2</code>), or a native
 * function, and pushes its result to the stack.
 * </p>
 * 
 * <p>
 * Performed stack operations (for a function <code>myFunction(arg1, arg2, ..., argn))</code>:<br>
 * <code>pop function</code> (the function to be called, either as function
 * name or as anonymous function declaration)<br>
 * <code>pop n</code> (the number of arguments as an integer)<br>
 * <code>pop arg1</code> (first argument)<br>
 * <code>pop arg2</code> (second argument)<br>
 * <code>...<br>
 * pop argn</code> (nth argument)<br>
 * <code>push result<br></code>
 * </p>
 * 
 * <p>
 * Use <code>Return</code> to push the function's result when declaring the
 * function. Otherwise, the function has no result, and <code>undefined</code>
 * is pushed. In this case, use <code>Pop</code> to discard it after the
 * function call.
 * </p>
 * 
 * <p>
 * ActionScript equivalents:
 * 
 * <ul>
 * <li>
 * standard function call, e.g.<br><code>parseInt(numString));</code><br>
 * </li>
 * <li>
 * anonymous function call, e.g.<br><code>function (x) { x + 3 } (1);</code>
 * </li>
 * </ul>
 * </p>
 *
 * @since SWF 5
 */
public final class CallFunction extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new CallFunction action.
   */
  public CallFunction() {
    super(ActionType.CALL_FUNCTION);
  }

}
