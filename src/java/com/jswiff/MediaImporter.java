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

import com.jswiff.hl.factories.ImageDocumentFactory;
import com.jswiff.hl.factories.MP3DocumentFactory;
import com.jswiff.hl.factories.WAVDocumentFactory;
import com.jswiff.util.ImageUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Helper class for media import. Creates SWF documents from media files.
 */
public class MediaImporter {
  
  public enum SoundType { MP3, WAV; }

  /**
   * Reads an image from a stream and creates a SWF document containing this
   * image. The image type is determined at runtime.
   *
   * @param imageStream image input stream
   * @param qualityPercent JPEG quality in percent
   *
   * @return SWF document containing image
   *
   * @throws IOException if an I/O error occurred
   */
  public static SWFDocument importImageAsJPEG(
    InputStream imageStream, int qualityPercent) throws IOException {
    BufferedImage image          = ImageUtilities.loadImage(imageStream);
    ImageDocumentFactory factory = new ImageDocumentFactory(image);
    factory.setQuality(qualityPercent);
    return factory.getDocument();
  }

  /**
   * Reads an image from a stream and creates a SWF document containing this
   * image using lossless compression. The image type is determined at
   * runtime.
   *
   * @param imageStream image input stream
   *
   * @return SWF document containing image
   *
   * @throws IOException if an I/O error occurred
   */
  public static SWFDocument importImageAsLossless(InputStream imageStream)
    throws IOException {
    BufferedImage image          = ImageUtilities.loadImage(imageStream);
    ImageDocumentFactory factory = new ImageDocumentFactory(image);
    factory.setLossless(true);
    return factory.getDocument();
  }

  /**
   * Reads a sound file from a stream.
   *
   * @param soundStream sound input stream
   * @param soundType one of the TYPE_... constants
   *
   * @return SWF document containing sound
   *
   * @throws IOException if an I/O error occurred
   * @throws IllegalArgumentException in case of an illegal sound type
   */
  public static SWFDocument importSound(InputStream soundStream, SoundType soundType)
    throws IOException {
    switch (soundType) {
      case MP3:
        MP3DocumentFactory mp3Factory = new MP3DocumentFactory(soundStream);
        return mp3Factory.getDocument();
      case WAV:
        WAVDocumentFactory wavFactory = new WAVDocumentFactory(soundStream);
        return wavFactory.getDocument();
      default:
        throw new IllegalArgumentException("Unknown sound type: " + soundType);
    }
  }

  /**
   * Reads an image from an input stream, creates a SWF document containing
   * this image using JPEG compression and writes it to an output stream. The
   * image type is determined at runtime.
   *
   * @param imageInStream image input stream
   * @param swfOutStream SWF output stream
   * @param qualityPercent JPEG quality in percent
   * @param compressed if true, SWF compression is activated
   *
   * @throws IOException if an I/O error occurred
   */
  public static void writeJPEGImageDocument(
    InputStream imageInStream, OutputStream swfOutStream, int qualityPercent,
    boolean compressed) throws IOException {
    SWFDocument doc = importImageAsJPEG(imageInStream, qualityPercent);
    doc.setCompressed(compressed);
    SWFWriter writer = new SWFWriter(doc, swfOutStream);
    writer.write();
  }

  /**
   * Reads an image from an input stream, creates a SWF document containing
   * this image using lossless compression and writes it to an output stream.
   * The image type is determined at runtime.
   *
   * @param imageInStream image input stream
   * @param swfOutStream SWF output stream
   * @param compressed if true, SWF compression is activated
   *
   * @throws IOException if an I/O error occured
   */
  public static void writeLosslessImageDocument(
    InputStream imageInStream, OutputStream swfOutStream, boolean compressed)
    throws IOException {
    SWFDocument doc = importImageAsLossless(imageInStream);
    doc.setCompressed(compressed);
    SWFWriter writer = new SWFWriter(doc, swfOutStream);
    writer.write();
  }

  /**
   * Reads a sound file from a stream and writes it to an output stream.
   *
   * @param soundInStream sound input stream
   * @param soundType one of the TYPE_... constants
   * @param swfOutStream SWF output stream
   * @param compressed if true, SWF compression is activated
   *
   * @throws IOException if an I/O error occured
   */
  public static void writeSoundDocument(
    InputStream soundInStream, SoundType soundType, OutputStream swfOutStream,
    boolean compressed) throws IOException {
    SWFDocument doc = importSound(soundInStream, soundType);
    doc.setCompressed(compressed);
    SWFWriter writer = new SWFWriter(doc, swfOutStream);
    writer.write();
  }
}
