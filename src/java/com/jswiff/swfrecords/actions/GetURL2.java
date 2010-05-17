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


/**
 * Gets contents from an URL or exchanges data with a server.
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop target</code> (see <code>GetURL</code>)<br>
 * <code>pop url</code><br>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>getURL(), loadMovie(), loadMovieNum(),
 * loadVariables()</code>
 * </p>
 *
 * @see GetURL
 * @since SWF 4
 */
public final class GetURL2 extends Action {
  
  private static final long serialVersionUID = 1L;
  
  /** Indicates that the clip's variables should not be encoded and submitted. */
  public static final byte METHOD_NONE = 0;
  /** The clip's variables are encoded and sent with HTTP GET */
  public static final byte METHOD_GET  = 1;
  /** The clip's variables are encoded and sent with HTTP POST */
  public static final byte METHOD_POST = 2;
  private byte sendVarsMethod;
  private boolean loadTarget;
  private boolean loadVariables;

  /**
   * Creates a new GetURL2 action.
   *
   * @param sendVarsMethod HTTP request method (<code>METHOD_NONE,
   *        METHOD_GET</code> or <code>METHOD_POST</code>)
   * @param loadTarget <code>false</code> if target is a browser frame,
   *        <code>true</code> if it is a path to a clip (in slash syntax -
   *        /parentClip/childClip - or dot syntax - parentClip.childClip)
   * @param loadVariables if <code>true</code>, the server is expected to
   *        respond with an url encoded set of variables
   */
  public GetURL2(
    byte sendVarsMethod, boolean loadTarget, boolean loadVariables) {
    super(ActionType.GET_URL_2);
    this.sendVarsMethod   = sendVarsMethod;
    this.loadTarget       = loadTarget;
    this.loadVariables    = loadVariables;
  }

  /*
   * Reads a GetURL2 action from a bit stream.
   */
  GetURL2(InputBitStream stream) throws IOException {
    super(ActionType.GET_URL_2);
    loadVariables   = stream.readBooleanBit();
    loadTarget      = stream.readBooleanBit();
    stream.readUnsignedBits(4); // 4 reserved bits
    sendVarsMethod   = (byte) stream.readUnsignedBits(2);
  }

  /**
   * Returns <code>false</code> if target is a browser frame, <code>true</code>
   * if it is a path to a movie clip (in slash or dot syntax)
   *
   * @return <code>true</code> if target is path to a clip
   */
  public boolean isLoadTarget() {
    return loadTarget;
  }

  /**
   * Returns <code>true</code> if the server is supposed to respond with an url
   * encoded set of variables
   *
   * @return <code>true</code> if server sends variables, otherwise false
   */
  public boolean isLoadVariables() {
    return loadVariables;
  }

  /**
   * Returns the HTTP request method (one of the values <code>METHOD_NONE,
   * METHOD_GET</code> or <code>METHOD_POST</code>)
   *
   * @return request method
   */
  public byte getSendVarsMethod() {
    return sendVarsMethod;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    return 4;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"GetURL2"</code>, the request method and the
   *         <code>loadTarget</code> and <code>loadVariables</code> flags.
   */
  public String toString() {
    String result = super.toString() + " sendVarsMethod: ";
    switch (sendVarsMethod) {
      case METHOD_GET:
        result += "GET";
        break;
      case METHOD_POST:
        result += "POST";
        break;
      default:
        result += "none";
    }
    result += (" loadTarget: " + loadTarget + " loadVariables: " +
    loadVariables);
    return result;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeBooleanBit(loadVariables);
    dataStream.writeBooleanBit(loadTarget);
    dataStream.writeUnsignedBits(0, 4); // 4 reserved bits
    dataStream.writeUnsignedBits(sendVarsMethod, 2);
  }
}
