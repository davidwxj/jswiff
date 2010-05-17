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

package com.jswiff.swfrecords;

import com.jswiff.io.InputBitStream;

import java.io.IOException;
import java.io.Serializable;


/**
 * Instances of this class represent a header of an SWF file.
 */
public final class SWFHeader implements Serializable {
  private boolean compressed;
  private short version;
  private long fileLength;
  private Rect frameSize;
  private short frameRate;
  private int frameCount;

  /**
   * Creates a new SWFHeader instance.
   */
  public SWFHeader() {
    // empty
  }

  /**
   * Reads an SWF header from a bit stream.
   *
   * @param stream a bit stream
   *
   * @throws IOException if an I/O error has occurred
   */
  public SWFHeader(InputBitStream stream) throws IOException {
    read(stream);
  }

  /**
   * Sets the compression flag of the SWF
   *
   * @param compressed <code>true</code> activates the compression
   */
  public void setCompressed(boolean compressed) {
    this.compressed = compressed;
  }

  /**
   * Checks if the SWF file is compressed.
   *
   * @return <code>true</code> if compression is on, otherwise
   *         <code>false</code>
   */
  public boolean isCompressed() {
    return compressed;
  }

  /**
   * Sets the file length. This value is computed and set when writing the SWF
   * with <code>SWFWriter</code>.
   *
   * @param fileLength file length in bytes
   */
  public void setFileLength(long fileLength) {
    this.fileLength = fileLength;
  }

  /**
   * Returns the length of the SWF file.
   *
   * @return SWF file length
   */
  public long getFileLength() {
    return fileLength;
  }

  /**
   * Sets the number of frames in the SWF movie. This value is computed and set
   * when the SWF is written with <code>SWFWriter</code>.
   *
   * @param frameCount frame count
   */
  public void setFrameCount(int frameCount) {
    this.frameCount = frameCount;
  }

  /**
   * Returns the number of frames in the SWF movie.
   *
   * @return frame count
   */
  public int getFrameCount() {
    return frameCount;
  }

  /**
   * Sets the frame rate of the SWF movie.
   *
   * @param frameRate the frame rate in fps
   */
  public void setFrameRate(short frameRate) {
    this.frameRate = frameRate;
  }

  /**
   * Returns the frame rate of the SWF movie.
   *
   * @return the frame rate in fps
   */
  public short getFrameRate() {
    return frameRate;
  }

  /**
   * Sets the frame size of the SWF.
   *
   * @param frameSize the frame size (as <code>Rect</code> instance)
   */
  public void setFrameSize(Rect frameSize) {
    this.frameSize = frameSize;
  }

  /**
   * Returns the frame size of the SWF movie.
   *
   * @return the frame size (as <code>Rect</code> instance)
   */
  public Rect getFrameSize() {
    return frameSize;
  }

  /**
   * Sets the SWF version of the file.
   *
   * @param version SWF version
   */
  public void setVersion(short version) {
    this.version = version;
  }

  /**
   * Returns the SWF version of the file.
   *
   * @return SWF version
   */
  public short getVersion() {
    return version;
  }

  private void read(InputBitStream stream) throws IOException {
    // read signature
    short compressionByte = stream.readUI8();

    // header starts with CWS (0x43 0x57 0x53) for compressed
    // or FWS (0x46 0x57 0x53) for uncompressed files
    if (
      ((compressionByte != 0x43) && (compressionByte != 0x46)) ||
          (stream.readUI8() != 0x57) || (stream.readUI8() != 0x53)) {
      throw new IOException("Invalid SWF file signature!");
    }
    if (compressionByte == 0x43) {
      compressed = true;
    }
    version      = (byte) stream.readUI8();
    fileLength   = stream.readUI32();
    if (compressed) {
      stream.enableCompression();
    }
    frameSize = new Rect(stream);
    stream.readUI8(); // ignore one byte
    frameRate    = stream.readUI8();
    frameCount   = stream.readUI16();
  }
}
