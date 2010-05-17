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

package com.jswiff.util;

import com.jswiff.swfrecords.RGBA;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;


/**
 * Provides utility methods for image processing.
 *
 * @author <a href="mailto:ralf@terdic.de">Ralf Terdic</a>
 */
public class ImageUtilities {
  /**
   * Returns the format of an image (as string).
   *
   * @param stream image source stream
   *
   * @return format stream (e.g. "JPEG")
   *
   * @throws IOException if an I/O error occured
   */
  public static String getFormat(InputStream stream) throws IOException {
    ImageInputStream iis       = ImageIO.createImageInputStream(stream);
    Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
    if (!iter.hasNext()) {
      throw new IOException("Unsupported image format!");
    }
    ImageReader reader = iter.next();
    iis.close();
    return reader.getFormatName();
  }

  /**
   * Returns the content of an image as RGBA array.
   *
   * @param image buffered image
   *
   * @return image as RGBA array
   */
  public static RGBA[] getRGBAArray(BufferedImage image) {
    BufferedImage argbImage;
    if (image.getType() != BufferedImage.TYPE_INT_ARGB) {
      argbImage = convertToARGB(image);
    } else {
      argbImage = image;
    }
    int height    = argbImage.getHeight();
    int width     = argbImage.getWidth();
    RGBA[] values = new RGBA[height * width];
    int i         = 0;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb     = argbImage.getRGB(x, y); // 0xaarrggbb
        int a       = ((rgb & 0xff000000) >> 24) & 0xff;
        int r       = ((rgb & 0xff0000) >> 16) & 0xff;
        int g       = ((rgb & 0xff00) >> 8) & 0xff;
        int b       = rgb & 0xff;
        if(a != 255) {
          // thx to Tom Rathbone for this fix
          float alpha = a / 255f;
          r *= alpha;
          g *= alpha;
          b *= alpha;
        }
        values[i++] = new RGBA(r, g, b, a);
      }
    }
    return values;
  }

  /**
   * Loads an image from a stream.
   *
   * @param stream input stream
   *
   * @return the loaded image
   *
   * @throws IOException if the image could not be loaded
   */
  public static BufferedImage loadImage(InputStream stream)
    throws IOException {
    return ImageIO.read(stream);
  }

  /**
   * Writes an image to an output stream as a JPEG file.
   *
   * @param image image to be written
   * @param stream target stream
   *
   * @throws IOException if an I/O error occured
   */
  public static void saveImageAsJPEG(BufferedImage image, OutputStream stream)
    throws IOException {
    ImageIO.write(image, "jpg", stream);
  }

  /**
   * Writes an image to an output stream as a JPEG file. The JPEG quality can
   * be specified in percent.
   *
   * @param image image to be written
   * @param stream target stream
   * @param qualityPercent JPEG quality in percent
   *
   * @throws IOException if an I/O error occured
   * @throws IllegalArgumentException if qualityPercent not between 0 and 100
   */
  public static void saveImageAsJPEG(
    BufferedImage image, OutputStream stream, int qualityPercent)
    throws IOException {
    if ((qualityPercent < 0) || (qualityPercent > 100)) {
      throw new IllegalArgumentException("Quality out of bounds!");
    }
    float quality              = qualityPercent / 100f;
    ImageWriter writer         = null;
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
    if (iter.hasNext()) {
      writer = iter.next();
    } else {
      throw new AssertionError("No image writer provided for format 'jpg'");
    }
    ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
    writer.setOutput(ios);
    ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
    iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    iwparam.setCompressionQuality(quality);
    writer.write(null, new IIOImage(image, null, null), iwparam);
    ios.flush();
    writer.dispose();
    ios.close();
  }

  /**
   * Writes an image to an output stream as a PNG file.
   *
   * @param image image to be written
   * @param stream target stream
   *
   * @throws IOException if an I/O error occured
   */
  public static void saveImageAsPNG(BufferedImage image, OutputStream stream)
    throws IOException {
    ImageIO.write(image, "png", stream);
  }

  private static BufferedImage convertToARGB(BufferedImage srcImage) {
    BufferedImage newImage = new BufferedImage(
        srcImage.getWidth(null), srcImage.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
    Graphics bg            = newImage.getGraphics();
    bg.drawImage(srcImage, 0, 0, null);
    bg.dispose();
    return newImage;
  }
}
