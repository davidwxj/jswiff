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

package com.jswiff;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.SWFHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
