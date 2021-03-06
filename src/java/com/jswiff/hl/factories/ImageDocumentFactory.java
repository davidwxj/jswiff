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

package com.jswiff.hl.factories;

import com.jswiff.SWFDocument;
import com.jswiff.SWFWriter;
import com.jswiff.constants.TagConstants;
import com.jswiff.swfrecords.AlphaBitmapData;
import com.jswiff.swfrecords.FillStyle;
import com.jswiff.swfrecords.FillStyleArray;
import com.jswiff.swfrecords.LineStyleArray;
import com.jswiff.swfrecords.Matrix;
import com.jswiff.swfrecords.RGB;
import com.jswiff.swfrecords.RGBA;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeRecord;
import com.jswiff.swfrecords.ShapeWithStyle;
import com.jswiff.swfrecords.StraightEdgeRecord;
import com.jswiff.swfrecords.StyleChangeRecord;
import com.jswiff.swfrecords.tags.DefineBitsJPEG2;
import com.jswiff.swfrecords.tags.DefineBitsLossless2;
import com.jswiff.swfrecords.tags.DefineShape;
import com.jswiff.swfrecords.tags.DefinitionTag;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.ShowFrame;
import com.jswiff.util.ImageUtilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Creates a SWF document from a BufferedImage instance.
 *
 * @author <a href="mailto:ralf@terdic.de">Ralf Terdic</a>
 */
public class ImageDocumentFactory {
  private static final byte[] JPEG_DATA_HEADER = new byte[] {
      (byte) 0xff, (byte) 0xd9, (byte) 0xff, (byte) 0xd8
    };
  private BufferedImage image;
  private int docWidth;
  private int docHeight;
  private boolean dimensionsSet;
  private RGB backgroundColor;
  private SWFDocument doc;
  private int quality                          = -1;
  private boolean lossless;

  /**
   * Creates a new ImageDocFactory instance.
   *
   * @param image BufferedImage instance
   */
  public ImageDocumentFactory(BufferedImage image) {
    this.image = image;
  }

  /**
   * Sets the background color of the document. Default is white.
   *
   * @param backgroundColor background color
   */
  public void setBackgroundColor(RGB backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  /**
   * Sets the dimensions of the document. The default dimensions are the
   * dimesions of the image.
   *
   * @param docWidth doc width (pixels)
   * @param docHeight doc height (pixels)
   */
  public void setDimensions(int docWidth, int docHeight) {
    this.docWidth    = docWidth;
    this.docHeight   = docHeight;
    dimensionsSet    = true;
  }

  /**
   * Creates the SWF document and returns it.
   *
   * @return SWF doc
   */
  public SWFDocument getDocument() {
    initDocument();
    return doc;
  }

  /**
   * Sets the lossless flag. If this flag is set, the image is saved as
   * lossless bitmap in the generated SWF.
   *
   * @param lossless lossless flag
   */
  public void setLossless(boolean lossless) {
    this.lossless = lossless;
  }

  /**
   * Sets the quality of the picture. This affects the JPEG compression used.
   * This is obviously not relevant when the lossless flag is set.
   *
   * @param percent image quality in percent
   */
  public void setQuality(int percent) {
    quality = percent;
  }

  /**
   * Main method for quick tests, pass input file (image format, e.g. jpg or
   * bmp) and output file (swf).
   *
   * @param args image, swf files
   *
   * @throws IOException if an I/O error occured
   */
  public static void main(String[] args) throws IOException {
    String imgFileName                   = args[0];
    String swfFileName                   = args[1];
    BufferedImage img                    = ImageUtilities.loadImage(
        new FileInputStream(imgFileName));
    ImageDocumentFactory documentFactory = new ImageDocumentFactory(img);

    //documentFactory.setDimensions(200, 150);
    //documentFactory.setQuality(80);
    documentFactory.setLossless(true); // use DefineBitsLossless2
    SWFDocument document = documentFactory.getDocument();
    SWFWriter writer     = new SWFWriter(
        document, new FileOutputStream(swfFileName));
    writer.write();
  }

  private Rect getShapeBounds() {
    int imageWidth  = image.getWidth();
    int imageHeight = image.getHeight();
    if (!dimensionsSet) {
      return new Rect(0, imageWidth * 20, 0, imageHeight * 20);
    }

    // make shape fit into document
    int shapeWidth;
    int shapeHeight;
    if (
      (((double) docWidth) / docHeight) > (((double) imageWidth) / imageHeight)) {
      shapeHeight   = docHeight * 20;
      shapeWidth    = (shapeHeight * imageWidth) / imageHeight;
    } else {
      shapeWidth    = docWidth * 20;
      shapeHeight   = (shapeWidth * imageHeight) / imageWidth;
    }
    return new Rect(0, shapeWidth, 0, shapeHeight);
  }

  private DefineBitsJPEG2 createBitsJPEG2() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      if (quality >= 0) {
        ImageUtilities.saveImageAsJPEG(image, baos, quality);
      } else {
        ImageUtilities.saveImageAsJPEG(image, baos);
      }
    } catch (IOException e) {
      // ignore
    }
    byte[] imageData         = baos.toByteArray();
    int imageDataLength      = imageData.length;
    int headerLength         = JPEG_DATA_HEADER.length;
    byte[] extendedImageData = new byte[headerLength + imageDataLength];
    System.arraycopy(JPEG_DATA_HEADER, 0, extendedImageData, 0, headerLength);
    System.arraycopy(
      imageData, 0, extendedImageData, headerLength, imageDataLength);
    DefineBitsJPEG2 bitsJPEG2 = new DefineBitsJPEG2(
        doc.getNewCharacterId(), extendedImageData);
    return bitsJPEG2;
  }

  private DefineBitsLossless2 createBitsLossless2() {
    RGBA[] rgbaArray                  = ImageUtilities.getRGBAArray(image);
    AlphaBitmapData alphaBitmap       = new AlphaBitmapData(rgbaArray);
    DefineBitsLossless2 bitsLossless2 = new DefineBitsLossless2(
        doc.getNewCharacterId(), DefineBitsLossless2.FORMAT_32_BIT_RGBA,
        docWidth, docHeight, alphaBitmap);
    return bitsLossless2;
  }

  private DefineShape createImageShape(int imageCharacterId) {
    Rect shapeBounds      = getShapeBounds();
    ShapeWithStyle styles = createShapeWithStyle(shapeBounds, imageCharacterId);
    DefineShape shape     = new DefineShape(
        doc.getNewCharacterId(), shapeBounds, styles);
    return shape;
  }

  private PlaceObject2 createPlaceObject2Tag(int imageShapeCharacterId) {
    PlaceObject2 placeObject2 = new PlaceObject2(1);
    placeObject2.setCharacterId(imageShapeCharacterId);
    placeObject2.setMatrix(new Matrix(0, 0));
    return placeObject2;
  }

  private ShapeWithStyle createShapeWithStyle(
    Rect shapeBounds, int imageCharacterId) {
    Matrix bitmapMatrix = new Matrix(0, 0);
    int shapeXMax       = (int) shapeBounds.getXMax();
    int shapeYMax       = (int) shapeBounds.getYMax();
    bitmapMatrix.setScale(
      (float) shapeXMax / (image.getWidth()),
      (float) shapeYMax / (image.getHeight()));
    FillStyle fillStyle       = new FillStyle(
        imageCharacterId, bitmapMatrix, TagConstants.FillType.CLIPPED_BITMAP);
    FillStyleArray fillStyles = new FillStyleArray();
    fillStyles.addStyle(fillStyle);
    ShapeRecord[] shapeRecords          = new ShapeRecord[5];
    StyleChangeRecord styleChangeRecord = new StyleChangeRecord();
    styleChangeRecord.setFillStyle1(1);
    styleChangeRecord.setMoveTo(shapeXMax, shapeYMax);
    shapeRecords[0]   = styleChangeRecord;
    shapeRecords[1]   = new StraightEdgeRecord(-shapeXMax, 0);
    shapeRecords[2]   = new StraightEdgeRecord(0, -shapeYMax);
    shapeRecords[3]   = new StraightEdgeRecord(shapeXMax, 0);
    shapeRecords[4]   = new StraightEdgeRecord(0, shapeYMax);
    ShapeWithStyle shapeWithStyle = new ShapeWithStyle(
        fillStyles, new LineStyleArray(), shapeRecords);
    return shapeWithStyle;
  }

  private void initDocument() {
    doc = new SWFDocument();
    if (!dimensionsSet) {
      docWidth    = image.getWidth();
      docHeight   = image.getHeight();
    }
    doc.setFrameSize(new Rect(0, docWidth * 20, 0, docHeight * 20));
    doc.setCompressed(true);
    if (backgroundColor != null) {
      doc.setBackgroundColor(backgroundColor);
    }
    DefinitionTag bitmapTag;
    if (lossless) {
      bitmapTag = createBitsLossless2();
    } else {
      bitmapTag = createBitsJPEG2();
    }
    int imageCharacterId = bitmapTag.getCharacterId();
    doc.addTag(bitmapTag);
    DefineShape imageShape = createImageShape(imageCharacterId);
    doc.addTag(imageShape);
    doc.addTag(createPlaceObject2Tag(imageShape.getCharacterId()));
    doc.addTag(new ShowFrame());
  }
}
