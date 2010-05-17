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
 * This tag is used as of SWF 8 to define SWF properties like access mode and
 * the presence of metadata. Do NOT add this tag to your
 * <code>SWFDocument</code>, use its <code>setAccessMode</code> and
 * <code>setMetadata</code> methods instead!
 * 
 * @see com.jswiff.SWFDocument#setAccessMode(byte)
 * @see com.jswiff.SWFDocument#setMetadata(String)
 * @since SWF 8
 */
public class FileAttributes extends Tag {

  private static final long serialVersionUID = 1L;
  
  private boolean allowNetworkAccess;
  private boolean hasMetadata;
  private boolean hasABC;

  /**
   * Creates a new FileAttributes instance.
   */
  public FileAttributes() {
    super(TagType.FILE_ATTRIBUTES);
  }

  /**
   * Specifies whether the SWF is granted network or local access.
   * 
   * @param allowNetworkAccess
   *          true for network access, false for local access
   */
  public void setAllowNetworkAccess(boolean allowNetworkAccess) {
    this.allowNetworkAccess = allowNetworkAccess;
  }

  /**
   * Checks whether the SWF is granted network or local access.
   * 
   * @return true for network access, false for local access
   */
  public boolean isAllowNetworkAccess() {
    return allowNetworkAccess;
  }

  /**
   * Specifies whether the SWF contains metadata (in a Metadata tag).
   * 
   * @param hasMetadata
   *          true if Metadata tag contained
   */
  public void setHasMetadata(boolean hasMetadata) {
    this.hasMetadata = hasMetadata;
  }

  /**
   * Checks whether the SWF contains metadata (in a Metadata tag).
   * 
   * @return true if Metadata tag contained
   */
  public boolean hasMetadata() {
    return hasMetadata;
  }

  public void setHasABC(boolean hasABC) {
    this.hasABC = hasABC;
  }

  public boolean hasABC() {
    return hasABC;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    int flags = 0;
    if (allowNetworkAccess) {
      flags |= 0x01;
    }
    if (hasABC) {
      flags |= 0x08;
    }
    if (hasMetadata) {
      flags |= 0x10;
    }
    outStream.writeSI32(flags);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    int flags = inStream.readSI32();
    allowNetworkAccess = ((flags & 0x01) != 0);
    hasABC = ((flags & 0x08) != 0);
    hasMetadata = ((flags & 0x10) != 0);
  }
}
