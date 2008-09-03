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

package com.jswiff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.SWFHeader;

public class SWFCompressor {
  private static final int BUFFER_SIZE = 1024;
  
  public static boolean compressSWF(InputStream swfIn, OutputStream swfOut) throws IOException {
    InputBitStream inStream = null;
    OutputBitStream outStream = null;
    try {
      inStream = new InputBitStream(swfIn);
      SWFHeader header = new SWFHeader(inStream);
      if (header.isCompressed()) {
        return false;
      }
      outStream = new OutputBitStream(swfOut);
      writeHeader(header, outStream, true);
      compress(inStream, outStream);
    } finally {
      if (outStream != null) {
        outStream.close();
      }
      if (inStream != null) {
        inStream.close();
      }
    }
    return true;
  }

  private static void compress(InputBitStream inStream,
      OutputBitStream outStream) throws IOException {
    int bufferSize;
    while((bufferSize = Math.min(inStream.available(), BUFFER_SIZE)) > 0) {
      byte[] buffer = inStream.readBytes(bufferSize);
      outStream.writeBytes(buffer);
    }
  }
  
  public static boolean decompressSWF(InputStream swfIn, OutputStream swfOut) throws IOException {
    InputBitStream inStream = null;
    OutputBitStream outStream = null;
    try {
      inStream = new InputBitStream(swfIn);
      SWFHeader header = new SWFHeader(inStream);
      if (!header.isCompressed()) {
        return false;
      }
      outStream = new OutputBitStream(swfOut);
      writeHeader(header, outStream, false);
      decompress(inStream, outStream);
    } finally {
      if (outStream != null) {
        outStream.close();
      }
      if (inStream != null) {
        inStream.close();
      }
    }
    return true;
  }

  private static void decompress(InputBitStream inStream, OutputBitStream outStream) throws IOException {
    InputStream zipStream = inStream.getBaseStream();
    byte[] buffer = new byte[BUFFER_SIZE];
    while (true) {
      int count = zipStream.read(buffer, 0, BUFFER_SIZE);
      if (count == -1) {
          break;
      }
      outStream.writeBytes(buffer);
    }
  }

  private static void writeHeader(SWFHeader header, OutputBitStream outStream, boolean isCompressed)
      throws IOException {
    outStream.writeBytes(new byte[] { (byte) (isCompressed ? 0x43 : 0x46), 0x57, 0x53 });
    outStream.writeUI8(header.getVersion());
    outStream.writeUI32(header.getFileLength());
    if (isCompressed) {
      outStream.enableCompression();
    }
    header.getFrameSize().write(outStream);
    outStream.writeUI8((short) 0);
    outStream.writeUI8(header.getFrameRate());
    outStream.writeUI16(header.getFrameCount());
  }
}
