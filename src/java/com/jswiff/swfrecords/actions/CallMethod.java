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
 * Invokes a method and pushes its result to the stack.
 * </p>
 * 
 * <p>
 * Performed stack operations (e.g. for a method <code>myObject.myMethod(arg1,
 * arg2, ..., argn))</code>:<br>
 * <code>pop instance</code> (the instance this method is invoked on, e.g. <code>myObject</code>)<br>
 * <code>pop name</code> (the method name, e.g. <code>"myMethod"</code>)<br>
 * <code>pop n</code> (the number of arguments as an integer)<br>
 * <code>pop arg1</code> (first argument)<br>
 * <code>pop arg2</code> (second argument)<br>
 * <code>...<br>
 * pop argn</code> (nth argument)<br>
 * <code> push result<br>
 * </code> The instance the method is invoked on has to be pushed to the stack
 * before method invocation e.g. like this:<br>
 * <code>push &quot;myObject&quot;<br>
 * GetVariable<br>
 * </code> If the method has no result, <code>undefined</code> is pushed. In
 * this case, use <code>Pop</code> to discard it.
 * </p>
 * 
 * <p>
 * ActionScript equivalent: method invocation using dot syntax
 * </p>
 *
 * @since SWF 5
 */
public final class CallMethod extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new CallMethod action.
   */
  public CallMethod() {
    super(ActionType.CALL_METHOD);
  }

}
