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
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * <p>
 * This class is used within <code>DefineBitsLossless2</code> tags (with 8-bit
 * colormapped images). It contains a color table and an array of pixel data.
 * The color table contains a palette of up to 256 RGBA colors (i.e.
 * transparency is also supported). The pixel data array contains color table
 * indices. Its size is the product of padded image width and image height.
 * </p>
 * 
 * <p>
 * Each line is padded with a scanline pad which makes sure the internal
 * representation starts and ends at a 32-bit boundary. Use
 * <code>getScanlinePadLength()</code> to compute this padding length
 * depending on the width of the image. The computed number of pixels must be
 * added as pad to the end of each image line. The color of the pad pixels is
 * ignored.
 * </p>
 */
public final class AlphaColorMapData extends ZlibBitmapData {
  private RGBA[] colorTableRGBA;
  private short[] colorMapPixelData;

  /**
   * Creates a new AlphaColorMapData instance. Supply a color table (of up to
   * 256 RGBA values) and an array of pixel data of size [paddedWidth x
   * height]. The pixel data consists of color table indices.
   *
   * @param colorTableRGBA color table, i.e. an array of up to 256 RGBA values
   * @param colorMapPixelData array of color table indices
   */
  public AlphaColorMapData(RGBA[] colorTableRGBA, short[] colorMapPixelData) {
    this.colorTableRGBA      = colorTableRGBA;
    this.colorMapPixelData   = colorMapPixelData;
  }

  /**
   * Creates a new ColorMapData instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   * @param colorTableSize color of table size (up to 256)
   * @param width image width in pixels (without padding!)
   * @param height image height in pixels
   *
   * @throws IOException if an I/O error occured
   */
  public AlphaColorMapData(
    InputBitStream stream, short colorTableSize, int width, int height)
    throws IOException {
    colorTableRGBA = new RGBA[colorTableSize];
    for (int i = 0; i < colorTableSize; i++) {
      colorTableRGBA[i] = new RGBA(stream);
    }
    int imageDataSize = (width + getScanlinePadLength(width)) * height;
    colorMapPixelData = new short[imageDataSize];
    for (int i = 0; i < imageDataSize; i++) {
      colorMapPixelData[i] = stream.readUI8();
    }
  }

  /**
   * Computes the length of the scanline padding for a given image width. The
   * internal representation of the bitmap data requires a line to start and
   * end at a 32-bit boundary. As pixel data consists of one byte per pixel,
   * the padding can be 0, 1, 2 or 3 pixels long, depending on the width of
   * the image.
   *
   * @param width image width (in pixels)
   *
   * @return padding length (in bytes)
   */
  public static int getScanlinePadLength(int width) {
    int pad = 0;
    if ((width & 3) != 0) {
      pad = 4 - (width & 3); // 1, 2 or 3 pad pixels
    }
    return pad;
  }

  /**
   * Returns the pixel data array, for each pixel a color table index. Warning:
   * the image data may contain up to 4 pad pixels at the end of each line as
   * the internal representation of the pixel data requires lines to start and
   * end on 32-bit boundaries. Use <code>getScanLinePadLength()</code> to
   * compute the number of pad pixels per line.
   *
   * @return image data
   */
  public short[] getColorMapPixelData() {
    return colorMapPixelData;
  }

  /**
   * Returns the color table which contains up to 256 RGBA values which can be
   * referenced by indices contained in the image data array.
   *
   * @return color table (as RGBA array)
   */
  public RGBA[] getColorTableRGBA() {
    return colorTableRGBA;
  }

  /**
   * Writes the instance to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error occured
   */
  public void write(OutputBitStream stream) throws IOException {
    for (int i = 0; i < colorTableRGBA.length; i++) {
      colorTableRGBA[i].write(stream);
    }
    for (int i = 0; i < colorMapPixelData.length; i++) {
      stream.writeUI8(colorMapPixelData[i]);
    }
  }
}
