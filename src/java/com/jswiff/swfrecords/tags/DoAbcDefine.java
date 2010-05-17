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
import com.jswiff.swfrecords.abc.AbcFile;

import java.io.IOException;

/**
 * Defines ActionScript 3 code in ABC (ActionScript byte code) format.
 * 
 * @since SWF 9
 */
public final class DoAbcDefine extends Tag {

  private static final long serialVersionUID = 1L;

  private String abcName;
  private AbcFile abcFile;

  /**
   * Creates a new DoABCDefine instance.
   */
  public DoAbcDefine(String abcName) {
    super(TagType.DO_ABC_DEFINE);
    this.abcName = abcName;
  }

  DoAbcDefine() {
    super(TagType.DO_ABC_DEFINE);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    if (abcName == null) {
      throw new IllegalStateException("abcName not set");
    }
    if (abcFile == null) {
      throw new IllegalStateException("abcFile not set");
    }
    
    this.setForceLongHeader(true);
    outStream.writeBytes(new byte[] { 1, 0, 0, 0 });
    outStream.writeString(abcName);
    abcFile.write(outStream);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    inStream.readBytes(4);
    abcName = inStream.readString();
    abcFile = new AbcFile();
    abcFile.read(inStream);
  }

  public String getAbcName() {
    return abcName;
  }
  
  public void setAbcName(String abcName) {
    this.abcName = abcName;
  }

  public AbcFile getAbcFile() {
    return abcFile;
  }
  
  public void setAbcFile(AbcFile abcFile) {
    this.abcFile = abcFile;
  }
}
