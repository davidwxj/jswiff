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
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.util.UUID;

/**
 * 
 * 
 * @since SWF 9
 */
public final class DebugId extends Tag {

  private static final long serialVersionUID = 1L;

  private UUID id;

  /**
   * Creates a new DebugId instance.
   */
  public DebugId(UUID id) {
    super(TagType.DEBUG_ID);
    this.id = id;
  }
  
  DebugId() {
    super(TagType.DEBUG_ID);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    byte[] bytes = new byte[16];
    long lsb = id.getLeastSignificantBits();
    long msb = id.getMostSignificantBits();

    bytes[0] = (byte) (lsb >>> 56);
    bytes[1] = (byte) (lsb >>> 48);
    bytes[2] = (byte) (lsb >>> 40);
    bytes[3] = (byte) (lsb >>> 32);
    bytes[4] = (byte) (lsb >>> 24);
    bytes[5] = (byte) (lsb >>> 16);
    bytes[6] = (byte) (lsb >>> 8);
    bytes[7] = (byte) (lsb >>> 0);

    bytes[8] = (byte) (msb >>> 56);
    bytes[9] = (byte) (msb >>> 48);
    bytes[10] = (byte) (msb >>> 40);
    bytes[11] = (byte) (msb >>> 32);
    bytes[12] = (byte) (msb >>> 24);
    bytes[13] = (byte) (msb >>> 16);
    bytes[14] = (byte) (msb >>> 8);
    bytes[15] = (byte) (msb >>> 0);

    outStream.writeBytes(bytes);
  }

  void setData(byte[] data) throws IOException {
    long lsb = (((long) data[0] << 56) + ((long) (data[1] & 255) << 48) + ((long) (data[2] & 255) << 40)
        + ((long) (data[3] & 255) << 32) + ((long) (data[4] & 255) << 24) + ((data[5] & 255) << 16)
        + ((data[6] & 255) << 8) + ((data[7] & 255) << 0));
    long msb = (((long) data[8] << 56) + ((long) (data[9] & 255) << 48) + ((long) (data[10] & 255) << 40)
        + ((long) (data[11] & 255) << 32) + ((long) (data[12] & 255) << 24) + ((data[13] & 255) << 16)
        + ((data[14] & 255) << 8) + ((data[15] & 255) << 0));
    id = new UUID(msb, lsb);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
