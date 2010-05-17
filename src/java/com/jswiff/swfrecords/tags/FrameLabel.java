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
 * <p>
 * This tag assigns a certain name to the current frame. This name can be used
 * by <code>GoToLabel</code> to identify this frame.
 * </p>
 * 
 * <p>
 * As of SWF 6, labels can additionally be defined as named anchors for better
 * browser integration. Named anchors are similar to HTML anchors (i.e. fragment
 * identifiers, as specified in RFC 2396). If the named anchor is supplied at
 * the end of the SWF file's URL (like
 * <code>http://servername/filename.swf#named_anchor</code>) in the browser, the
 * Flash Player plugin starts playback at the frame labeled as
 * <code>named_anchor</code>. Additionally, if the Flash Player plugin
 * encounters a frame containing a named anchor during playback of an SWF, it
 * adds the anchor to the URL of the HTML page embedding the SWF in the address
 * bar (or updates it if an anchor is already there), so the frame can be
 * bookmarked and the browser's "back" and "forward" buttons can be used for
 * navigation.
 * </p>
 * 
 * @since SWF 3 (named anchors since SWF 6)
 */
public final class FrameLabel extends Tag {

  private static final long serialVersionUID = 1L;

  private String name;
  private boolean isNamedAnchor;

  /**
   * Creates a new FrameLabel tag.
   * 
   * @param name
   *          label name
   * @param isNamedAnchor
   *          set to <code>true</code> if label is named anchor, otherwise
   *          <code>false</code>
   */
  public FrameLabel(String name, boolean isNamedAnchor) {
    super(TagType.FRAME_LABEL);
    this.name = name;
    this.isNamedAnchor = isNamedAnchor;
  }

  FrameLabel() {
    super(TagType.FRAME_LABEL);
  }

  /**
   * Sets the name of the label assigned to the frame.
   * 
   * @param name
   *          label name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the label assigned to the frame.
   * 
   * @return label name
   */
  public String getName() {
    return name;
  }

  /**
   * Specifies whether the label is defined as named anchor.
   * 
   * @param isNamedAnchor
   *          <code>true</code> if label is named anchor, otherwise
   *          <code>false</code>
   */
  public void setNamedAnchor(boolean isNamedAnchor) {
    this.isNamedAnchor = isNamedAnchor;
  }

  /**
   * Checks whether the label is defined as named anchor.
   * 
   * @return <code>true</code> if label is named anchor, otherwise
   *         <code>false</code>
   */
  public boolean isNamedAnchor() {
    return isNamedAnchor;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    this.setForceLongHeader(true);
    outStream.writeString(name);
    if ((getSWFVersion() >= 6) && isNamedAnchor) {
      outStream.writeUI8((short) 1);
    }
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    if (getSWFVersion() < 6) {
      if (isJapanese()) {
        inStream.setShiftJIS(true);
      } else {
        inStream.setANSI(true);
      }
    }
    name = inStream.readString();
    if ((getSWFVersion() >= 6) && (inStream.available() > 0)) {
      isNamedAnchor = (inStream.readUI8() != 0);
    }
  }
}
