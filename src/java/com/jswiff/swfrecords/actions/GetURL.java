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
 * Instructs Flash Player to get a specified URL (e.g. an HTML file, an image
 * or another SWF movie) and to display it using a particular target (either a
 * browser frame or a level in Flash Player).
 * </p>
 * 
 * <p>
 * Several protocols are supported:
 * 
 * <ul>
 * <li>
 * conventional internet protocols (<code>http, https, ftp, mailto,
 * telnet</code>)
 * </li>
 * <li>
 * <code>file:///drive:/filename</code> for local file access
 * </li>
 * <li>
 * <code>print</code> used for printing a movie clip
 * </li>
 * <li>
 * <code>javascript</code> and  <code>vbscript</code> to execute script code in
 * the browser
 * </li>
 * <li>
 * <code>event</code> and <code>lingo</code> for Macromedia Director
 * interaction
 * </li>
 * </ul>
 * </p>
 * 
 * <p>
 * Usually, the specified target directs the URL content to a particular
 * browser frame (e.g. <code>_self</code>, <code>_parent</code>,
 * <code>_blank</code>). If the URL points to an SWF, the target can be a
 * string specifying the name of a movie clip instance or a document level
 * (e.g. <code>_level1</code>).
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>getURL(), loadMovie()</code> operator
 * </p>
 *
 * @since SWF 3
 */
public final class GetURL extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private String url;
  private String target;

  /**
   * Creates a new GetURL action. The <code>url</code> content will be
   * displayed at the specified <code>target</code>.
   *
   * @param url the URL to be loaded
   * @param target the target used to display the URL
   */
  public GetURL(String url, String target) {
    super(ActionType.GET_URL);
    this.url      = url;
    this.target   = target;
  }

  /*
   * Creates a new GetURL action from a bit stream
   */
  GetURL(InputBitStream stream) throws IOException {
    super(ActionType.GET_URL);
    url      = stream.readString();
    target   = stream.readString();
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 5;
    try {
      size += (url.getBytes("UTF-8").length + target.getBytes("UTF-8").length);
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available
    }
    return size;
  }

  /**
   * Returns the target used to display the URL.
   *
   * @return target string
   */
  public String getTarget() {
    return target;
  }

  /**
   * Returns the URL to be loaded.
   *
   * @return URL string
   */
  public String getURL() {
    return url;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"GetURL"</code>, the <code>url</code> and the
   *         <code>target</code>
   */
  public String toString() {
    return super.toString() + " url: '" + url + "' target: '" + target + "'";
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(url);
    dataStream.writeString(target);
  }
}
