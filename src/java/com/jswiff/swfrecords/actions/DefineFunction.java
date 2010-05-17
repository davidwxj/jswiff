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
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * <p>
 * This action defines a function.
 * </p>
 * 
 * <p>
 * Note: DefineFunction is rarely used as of SWF 7 and later; it has been
 * superseded by DefineFunction2.
 * </p>
 * 
 * <p>
 * Performed stack operations:
 * 
 * <ul>
 * <li>
 * standard function declarations do not touch the stack
 * </li>
 * <li>
 * when a function name is not specified, it is assumed that a function literal
 * (anonymous function) is declared. In this case, the declared function is
 * pushed to the stack so it can either be assigned or invoked:<br>
 * </li>
 * </ul>
 * </p>
 * 
 * <p>
 * Note: Use <code>Return</code> to declare the function's result. Otherwise
 * the function has no result, and <code>undefined</code> is pushed to stack
 * upon invocation.
 * </p>
 * 
 * <p>
 * ActionScript equivalents:
 * 
 * <ul>
 * <li>
 * standard function declaration, e.g.<br>
 * <code>myFunction(x) {<br>
 * return (x + 3);<br>}</code><br>
 * </li>
 * <li>
 * anonymous function declaration, e.g.<br>
 * <code>function (x) { x + 3 };</code><br>
 * </li>
 * <li>
 * anonymous function invocation, e.g.<br>
 * <code>function (x) { x + 3 } (1);</code><br>
 * </li>
 * <li>
 * method declaration
 * </li>
 * </ul>
 * </p>
 *
 * @see DefineFunction2
 * @since SWF 5
 */
public final class DefineFunction extends Action {

  private static final long serialVersionUID = 1L;

  private String name;
  private String[] parameters;
  private ActionBlock body;

  /**
   * Creates a new DefineFunction action. Use the empty string ("") as function
   * name for anonymous functions.
   *
   * @param functionName name of the function
   * @param parameters array of parameter names
   */
  public DefineFunction(String functionName, String[] parameters) {
    super(ActionType.DEFINE_FUNCTION);
    this.name         = functionName;
    this.parameters   = parameters;
    body              = new ActionBlock();
  }

  /*
   * Creates a new DefineFunction action. Data is read from a bit stream.
   */
  DefineFunction(InputBitStream stream, InputBitStream mainStream)
    throws IOException {
    super(ActionType.DEFINE_FUNCTION);
    name   = stream.readString();
    int numParams = stream.readUI16();
    if (numParams >= 0) {
      parameters = new String[numParams];
      for (int i = 0; i < numParams; i++) {
        parameters[i] = stream.readString();
      }
    }
    int codeSize               = stream.readUI16();

    // now read further actions from the main stream
    // read action block
    byte[] blockBuffer         = mainStream.readBytes(codeSize);
    InputBitStream blockStream = new InputBitStream(blockBuffer);
    blockStream.setANSI(stream.isANSI());
    blockStream.setShiftJIS(stream.isShiftJIS());
    body                       = new ActionBlock(blockStream);
  }

  /**
   * Returns an <code>ActionBlock</code> containing the function's body. Use
   * <code>addAction()</code> to add actions to the body.
   *
   * @return function body as action block
   */
  public ActionBlock getBody() {
    return body;
  }

  /**
   * Returns the name of the function (or the empty string for anonymous
   * functions).
   *
   * @return function name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the names of the function parameters.
   *
   * @return array of parameter names
   */
  public String[] getParameters() {
    return parameters;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 8 + body.getSize();
    try {
      // function name (unicode, not null-terminated)
      // (+ parameters.length: for each param a terminating 0)
      int paramLength = (parameters == null) ? 0 : parameters.length; 
      size += (name.getBytes("UTF-8").length + paramLength);
      for (int i = 0; i < paramLength; i++) {
        size += parameters[i].getBytes("UTF-8").length; // unicode, null-terminated
      }
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available
    }
    return size;
  }

  /**
   * Adds an action record to the function body.
   *
   * @param action action record
   */
  public void addAction(Action action) {
    body.addAction(action);
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"DefineFunction"</code>, function name
   */
  public String toString() {
    return super.toString() + " " + name;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(name);
    dataStream.writeUI16((parameters == null) ? 0 : parameters.length);
    if (parameters != null) {
      for (int i = 0; i < parameters.length; i++) {
        dataStream.writeString(parameters[i]);
      }
    }
    dataStream.writeUI16(body.getSize()); // codeSize
    body.write(mainStream, false);
  }
}
