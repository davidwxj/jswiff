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

package com.jswiff.test.simple;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jswiff.SWFReader;
import com.jswiff.listeners.SWFListener;
import com.jswiff.listeners.SWFListenerAdapter;
import com.jswiff.swfrecords.tags.DefineBitsJPEG2;
import com.jswiff.swfrecords.tags.DefineBitsJPEG3;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.util.ImageUtilities;


/**
 * Parses SWF files from a directory and saves contained JPEG images.
 */
public class SaveJPEGs {
  private static String outputDir;
  private static int jpegCounter;
  private static final byte[] HEADER = new byte[] {
      (byte) 0xff, (byte) 0xd9, (byte) 0xff, (byte) 0xd8
    };

  /**
   * Main method.
   *
   * @param args arguments: source and destination directories
   *
   * @throws IOException if an I/O error occured
   */
  public static void main(String[] args) throws IOException {
    File sourceDir = new File(args[0]);
    outputDir = args[1];
    File[] sourceFiles = sourceDir.listFiles();
    for (int i = 0; i < sourceFiles.length; i++) {
      File sourceFile = sourceFiles[i];
      System.out.println("File: " + sourceFile);
      SWFReader reader = new SWFReader(new FileInputStream(sourceFile));
      reader.addListener(new JPEGListener());
      reader.read();
    }
  }

  private static class JPEGListener extends SWFListenerAdapter {
    /**
     * @see SWFListener#processTag(Tag, long)
     */
    public void processTag(Tag tag, long streamOffset) {
      try {
        switch (tag.tagType()) {
          case DEFINE_BITS_JPEG_2:
            saveJPEG(((DefineBitsJPEG2) tag).getJpegData());
            break;
          case DEFINE_BITS_JPEG_3:
            saveJPEG(((DefineBitsJPEG3) tag).getJpegData());
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void saveJPEG(byte[] data) throws IOException {
      byte[] jpegData     = truncateHeader(data);
      BufferedImage image = ImageUtilities.loadImage(
          new ByteArrayInputStream(jpegData));
      if (image == null) {
        throw new IllegalArgumentException("Garbled JPEG data!");
      }
      String jpegFile = outputDir + "/" + (jpegCounter++) + ".jpg";
      ImageUtilities.saveImageAsJPEG(
        image, new FileOutputStream(jpegFile));
    }

    private byte[] truncateHeader(byte[] data) {
      // most, but not all JPEG tags contain this header
      if (
        (data.length < 4) || (data[0] != HEADER[0]) || (data[1] != HEADER[1]) ||
          (data[2] != HEADER[2]) || (data[3] != HEADER[3])) {
        return data;
      }
      byte[] truncatedData = new byte[data.length - 4];
      System.arraycopy(data, 4, truncatedData, 0, truncatedData.length);
      return truncatedData;
    }
  }
}
