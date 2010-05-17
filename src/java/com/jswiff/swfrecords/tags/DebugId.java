/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
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
