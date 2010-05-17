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

import java.io.IOException;

/**
 * This tag is used to override the default values for maximum recursion depth
 * (i.e. how often a function can call itself) and timeout regarding the
 * execution of actions.
 * 
 * @since SWF 7
 */
public final class ScriptLimits extends Tag {

  private static final long serialVersionUID = 1L;

  private int maxRecursionDepth;
  private int scriptTimeoutSeconds;

  /**
   * Creates a new ScriptLimits tag. Supply the maximum recursion depth and the
   * timeout in seconds.
   * 
   * @param maxRecursionDepth
   *          maximum recursion depth (at most 65535)
   * @param scriptTimeoutSeconds
   *          timeout in seconds
   */
  public ScriptLimits(int maxRecursionDepth, int scriptTimeoutSeconds) {
    super(TagType.SCRIPT_LIMITS);
    this.maxRecursionDepth = maxRecursionDepth;
    this.scriptTimeoutSeconds = scriptTimeoutSeconds;
  }

  ScriptLimits() {
    super(TagType.SCRIPT_LIMITS);
  }

  /**
   * Sets the maximum recursion depth, i.e. how often a function can
   * successively call itself.
   * 
   * @param maxRecursionDepth
   *          maximum recursion depth
   */
  public void setMaxRecursionDepth(int maxRecursionDepth) {
    this.maxRecursionDepth = maxRecursionDepth;
  }

  /**
   * Returns the maximum recursion depth, i.e. how often a function can
   * successively call itself.
   * 
   * @return maximum recursion depth
   */
  public int getMaxRecursionDepth() {
    return maxRecursionDepth;
  }

  /**
   * Sets the timeout, i.e. the maximum time allowed for the execution of an
   * action block. If this time has elapsed and the execution hasn't finished,
   * Flash Player asks the user whether to continue or to abort further
   * execution.
   * 
   * @param scriptTimeoutSeconds
   *          timeout value in seconds
   */
  public void setScriptTimeoutSeconds(int scriptTimeoutSeconds) {
    this.scriptTimeoutSeconds = scriptTimeoutSeconds;
  }

  /**
   * Returns the timeout, i.e. the maximum time allowed for the execution of an
   * action block. If this time has elapsed and the execution hasn't finished,
   * Flash Player asks the user whether to continue or to abort further
   * execution.
   * 
   * @return timeout value in seconds
   */
  public int getScriptTimeoutSeconds() {
    return scriptTimeoutSeconds;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeUI16(maxRecursionDepth);
    outStream.writeUI16(scriptTimeoutSeconds);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    maxRecursionDepth = inStream.readUI16();
    scriptTimeoutSeconds = inStream.readUI16();
  }
}
