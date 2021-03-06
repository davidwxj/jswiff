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
import com.jswiff.swfrecords.RegisterParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * This action is used to declare a function. It supersedes
 * <code>DefineFunction</code> since SWF 7.<br>
 * With DefineFunction2, a function may allocate its own private set of up to
 * 256 registers, which can be used as parameters or local variables.<br>
 * For performance improvement, you can specify if "common variables"
 * (<code>_parent, _root, super, arguments, this,</code> or
 * <code>_global</code>) are supposed to be preloaded into registers before
 * execution. Additionally, the Flash Player can be instructed to suppress
 * unused variables. (Only <code>super, arguments</code> and <code>this</code>
 * can be suppressed. Naturally, you can either preload or suppress a
 * variable, not both).
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
 * @see DefineFunction
 * @since SWF 7
 */
public final class DefineFunction2 extends Action {

  private static final long serialVersionUID = 1L;
  
  private String name;
  private short registerCount;
  private boolean preloadParent;
  private boolean preloadRoot;
  private boolean suppressSuper;
  private boolean preloadSuper;
  private boolean suppressArguments;
  private boolean preloadArguments;
  private boolean suppressThis;
  private boolean preloadThis;
  private boolean preloadGlobal;
  private RegisterParam[] parameters;
  private ActionBlock body;

  /**
   * Creates a new DefineFunction2 action. Use the empty string ("") as
   * function name for anonymous functions.
   *
   * @param name name of the function
   * @param registerCount number of used registers
   * @param parameters the function's parameters
   */
  public DefineFunction2(
    String name, short registerCount, RegisterParam[] parameters) {
    super(ActionType.DEFINE_FUNCTION_2);
    this.name            = name;
    this.registerCount   = registerCount;
    this.parameters      = parameters;
    body                 = new ActionBlock();
  }

  /*
   * Creates a new DefineFunction2 action. Data is read from a bit stream.
   *
   * @param stream a bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  DefineFunction2(InputBitStream stream, InputBitStream mainStream)
    throws IOException {
    super(ActionType.DEFINE_FUNCTION_2);
    name                = stream.readString();
    int numParams       = stream.readUI16();
    registerCount       = stream.readUI8();
    preloadParent       = stream.readBooleanBit();
    preloadRoot         = stream.readBooleanBit();
    suppressSuper       = stream.readBooleanBit();
    preloadSuper        = stream.readBooleanBit();
    suppressArguments   = stream.readBooleanBit();
    preloadArguments    = stream.readBooleanBit();
    suppressThis        = stream.readBooleanBit();
    preloadThis         = stream.readBooleanBit();
    preloadGlobal       = ((stream.readUI8() & 1) != 0); // 7 reserved bits=0
    parameters          = new RegisterParam[numParams];
    for (int i = 0; i < numParams; i++) {
      parameters[i] = new RegisterParam(stream);
    }
    int codeSize               = stream.readUI16();

    // now read further actions from the main stream
    // read action block
    byte[] blockBuffer         = mainStream.readBytes(codeSize);
    InputBitStream blockStream = new InputBitStream(blockBuffer);
    blockStream.setANSI(mainStream.isANSI());
    blockStream.setShiftJIS(mainStream.isShiftJIS());
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
   * Returns an array containing the function's parameters.
   *
   * @return the function's parameters
   */
  public RegisterParam[] getParameters() {
    return parameters;
  }

  /**
   * Returns the number of registers this function uses (max. 256)
   *
   * @return number of used registers
   */
  public short getRegisterCount() {
    return registerCount;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 11 + body.getSize();
    try {
      size += name.getBytes("UTF-8").length; // function name (Unicode, not null-terminated)
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available
    }
    if (parameters != null) {
      for (int i = 0; i < parameters.length; i++) {
        size += parameters[i].getSize();
      }
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
   * <p>
   * Instructs the Flash Player to preload the <code>arguments</code> variable
   * to a register.
   * </p>
   * 
   * <p>
   * Note: the preloaded variables are copied into registers starting at 1 in
   * the following order: <code>this</code>, <code>arguments</code>,
   * <code>super</code>, <code>_root</code>, <code>_parent</code>, and
   * <code>_global</code>, skipping any that are not to be preloaded.
   * </p>
   */
  public void preloadArguments() {
    preloadArguments = true;
  }

  /**
   * <p>
   * Instructs the Flash Player to preload the <code>_global</code> variable to
   * a register.
   * </p>
   * 
   * <p>
   * Note: the preloaded variables are copied into registers starting at 1 in
   * the following order: <code>this</code>, <code>arguments</code>,
   * <code>super</code>, <code>_root</code>, <code>_parent</code>, and
   * <code>_global</code>, skipping any that are not to be preloaded.
   * </p>
   */
  public void preloadGlobal() {
    preloadGlobal = true;
  }

  /**
   * <p>
   * Instructs the Flash Player to preload the <code>_parent</code> variable to
   * a register.
   * </p>
   * 
   * <p>
   * Note: the preloaded variables are copied into registers starting at 1 in
   * the following order: <code>this</code>, <code>arguments</code>,
   * <code>super</code>, <code>_root</code>, <code>_parent</code>, and
   * <code>_global</code>, skipping any that are not to be preloaded.
   * </p>
   */
  public void preloadParent() {
    preloadParent = true;
  }

  /**
   * <p>
   * Instructs the Flash Player to preload the <code>_root</code> variable to a
   * register.
   * </p>
   * 
   * <p>
   * Note: the preloaded variables are copied into registers starting at 1 in
   * the following order: <code>this</code>, <code>arguments</code>,
   * <code>super</code>, <code>_root</code>, <code>_parent</code>, and
   * <code>_global</code>, skipping any that are not to be preloaded.
   * </p>
   */
  public void preloadRoot() {
    preloadRoot = true;
  }

  /**
   * <p>
   * Instructs the Flash Player to preload the <code>super</code> variable to a
   * register.
   * </p>
   * 
   * <p>
   * Note: the preloaded variables are copied into registers starting at 1 in
   * the following order: <code>this</code>, <code>arguments</code>,
   * <code>super</code>, <code>_root</code>, <code>_parent</code>, and
   * <code>_global</code>, skipping any that are not to be preloaded.
   * </p>
   */
  public void preloadSuper() {
    preloadSuper = true;
  }

  /**
   * <p>
   * Instructs the Flash Player to preload the <code>this</code> variable to a
   * register.
   * </p>
   * 
   * <p>
   * Note: the preloaded variables are copied into registers starting at 1 in
   * the following order: <code>this</code>, <code>arguments</code>,
   * <code>super</code>, <code>_root</code>, <code>_parent</code>, and
   * <code>_global</code>, skipping any that are not to be preloaded.
   * </p>
   */
  public void preloadThis() {
    preloadThis = true;
  }

  /**
   * Checks whether the <code>arguments</code> variable is preloaded before
   * executing the function.
   *
   * @return <code>true</code> if preloaded, else <code>false</code>
   */
  public boolean preloadsArguments() {
    return preloadArguments;
  }

  /**
   * Checks whether the <code>_global</code> variable is preloaded before
   * executing the function.
   *
   * @return <code>true</code> if preloaded, else <code>false</code>
   */
  public boolean preloadsGlobal() {
    return preloadGlobal;
  }

  /**
   * Checks whether the <code>_parent</code> variable is preloaded before
   * executing the function.
   *
   * @return <code>true</code> if preloaded, else <code>false</code>
   */
  public boolean preloadsParent() {
    return preloadParent;
  }

  /**
   * Checks whether the <code>_root</code> variable is preloaded before
   * executing the function.
   *
   * @return <code>true</code> if preloaded, else <code>false</code>
   */
  public boolean preloadsRoot() {
    return preloadRoot;
  }

  /**
   * Checks whether the <code>super</code> variable is preloaded before
   * executing the function.
   *
   * @return <code>true</code> if preloaded, else <code>false</code>
   */
  public boolean preloadsSuper() {
    return preloadSuper;
  }

  /**
   * Checks whether the <code>this</code> variable is preloaded before
   * executing the function.
   *
   * @return <code>true</code> if preloaded, else <code>false</code>
   */
  public boolean preloadsThis() {
    return preloadThis;
  }

  /**
   * Instructs the Flash Player to preload the <code>arguments</code> variable.
   */
  public void suppressArguments() {
    suppressArguments = true;
  }

  /**
   * Instructs the Flash Player to preload the <code>super</code> variable.
   */
  public void suppressSuper() {
    suppressSuper = true;
  }

  /**
   * Instructs the Flash Player to preload the <code>this</code> variable.
   */
  public void suppressThis() {
    suppressThis = true;
  }

  /**
   * Checks whether the <code>arguments</code> variable is suppressed.
   *
   * @return <code>true</code> if suppressed, else <code>false</code>
   */
  public boolean suppressesArguments() {
    return suppressArguments;
  }

  /**
   * Checks whether the <code>super</code> variable is suppressed.
   *
   * @return <code>true</code> if suppressed, else <code>false</code>
   */
  public boolean suppressesSuper() {
    return suppressSuper;
  }

  /**
   * Checks whether the <code>this</code> variable is suppressed.
   *
   * @return <code>true</code> if suppressed, else <code>false</code>
   */
  public boolean suppressesThis() {
    return suppressThis;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"DefineFunction2"</code>, function name
   */
  public String toString() {
    return super.toString() + " " + name;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(name);
    dataStream.writeUI16((parameters == null) ? 0 : parameters.length);
    dataStream.writeUI8(registerCount);
    dataStream.writeBooleanBit(preloadParent);
    dataStream.writeBooleanBit(preloadRoot);
    dataStream.writeBooleanBit(suppressSuper);
    dataStream.writeBooleanBit(preloadSuper);
    dataStream.writeBooleanBit(suppressArguments);
    dataStream.writeBooleanBit(preloadArguments);
    dataStream.writeBooleanBit(suppressThis);
    dataStream.writeBooleanBit(preloadThis);
    dataStream.writeUI8((short) (preloadGlobal ? 1 : 0)); // 7 reserved bits
    if (parameters != null) {
      for (int i = 0; i < parameters.length; i++) {
        parameters[i].write(dataStream);
      }
    }
    dataStream.writeUI16(body.getSize());
    body.write(mainStream, false);
  }
}
