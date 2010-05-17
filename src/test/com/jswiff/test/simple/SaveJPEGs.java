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
