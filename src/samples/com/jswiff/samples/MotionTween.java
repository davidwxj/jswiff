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

package com.jswiff.samples;

import java.io.FileOutputStream;
import java.io.IOException;

import com.jswiff.SWFDocument;
import com.jswiff.SWFWriter;
import com.jswiff.swfrecords.FillStyle;
import com.jswiff.swfrecords.FillStyleArray;
import com.jswiff.swfrecords.LineStyle;
import com.jswiff.swfrecords.LineStyleArray;
import com.jswiff.swfrecords.Matrix;
import com.jswiff.swfrecords.RGB;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeRecord;
import com.jswiff.swfrecords.ShapeWithStyle;
import com.jswiff.swfrecords.StraightEdgeRecord;
import com.jswiff.swfrecords.StyleChangeRecord;
import com.jswiff.swfrecords.tags.DefineShape;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.ShowFrame;


/**
 * TODO: Comments
 */
public class MotionTween {
  /**
   * TODO: Comments
   *
   * @param args TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public static void main(String[] args) throws IOException {
    SWFDocument doc  = createDocument();
    SWFWriter writer = new SWFWriter(doc, new FileOutputStream("d:/tween.swf"));
    writer.write();
  }

  private static SWFDocument createDocument() {
    //Create a new SWF document
    SWFDocument document = new SWFDocument();

    //Set movie background color
    document.setBackgroundColor(new RGB((short) 0, (short) 255, (short) 0));
    //Define shape
    int shapeID                   = 1;
    Rect shapebounds              = new Rect(-10, 1330, -10, 1330);
    FillStyleArray fillStyleArray = new FillStyleArray();
    {
      fillStyleArray.addStyle(
        new FillStyle(new RGB((short) 255, (short) 0, (short) 0)));
    }
    LineStyleArray lineStyleArray = new LineStyleArray();
    {
      lineStyleArray.addStyle(
        new LineStyle(20, new RGB((short) 0, (short) 0, (short) 0)));
    }
    ShapeRecord[] shapes = new ShapeRecord[5];
    {
      StyleChangeRecord styleChangeRecord = new StyleChangeRecord();
      styleChangeRecord.setLineStyle(1);
      styleChangeRecord.setFillStyle1(1);
      shapes[0]   = styleChangeRecord;
      shapes[1]   = new StraightEdgeRecord(1320, 0);
      shapes[2]   = new StraightEdgeRecord(0, 1320);
      shapes[3]   = new StraightEdgeRecord(-1320, 0);
      shapes[4]   = new StraightEdgeRecord(0, -1320);
    }
    ShapeWithStyle shapeWithStyle = new ShapeWithStyle(
        fillStyleArray, lineStyleArray, shapes);
    DefineShape defineShape       = new DefineShape(
        shapeID, shapebounds, shapeWithStyle);
    document.addTag(defineShape);
    //Frame 1
    PlaceObject2 placeObject1 = new PlaceObject2(1);
    placeObject1.setCharacterId(shapeID);
    placeObject1.setMatrix(new Matrix(500, 820));
    document.addTag(placeObject1);
    document.addTag(new ShowFrame());
    //Frame 2
    PlaceObject2 placeObject2 = new PlaceObject2(1);
    placeObject2.setCharacterId(shapeID);
    placeObject2.setMove();
    placeObject2.setMatrix(new Matrix(1018, 1107));
    document.addTag(placeObject2);
    document.addTag(new ShowFrame());
    //Frame 3
    PlaceObject2 placeObject3 = new PlaceObject2(1);
    placeObject3.setCharacterId(shapeID);
    placeObject3.setMove();
    placeObject3.setMatrix(new Matrix(1535, 1393));
    document.addTag(placeObject3);
    document.addTag(new ShowFrame());
    //Frame 4
    PlaceObject2 placeObject4 = new PlaceObject2(1);
    placeObject4.setCharacterId(shapeID);
    placeObject4.setMove();
    placeObject4.setMatrix(new Matrix(2053, 1680));
    document.addTag(placeObject4);
    document.addTag(new ShowFrame());
    //Frame 5
    PlaceObject2 placeObject5 = new PlaceObject2(1);
    placeObject5.setCharacterId(shapeID);
    placeObject5.setMove();
    placeObject5.setMatrix(new Matrix(2571, 1967));
    document.addTag(placeObject5);
    document.addTag(new ShowFrame());
    //Frame 6
    PlaceObject2 placeObject6 = new PlaceObject2(1);
    placeObject6.setCharacterId(shapeID);
    placeObject6.setMove();
    placeObject6.setMatrix(new Matrix(3088, 2253));
    document.addTag(placeObject6);
    document.addTag(new ShowFrame());
    //Frame 7
    PlaceObject2 placeObject7 = new PlaceObject2(1);
    placeObject7.setCharacterId(shapeID);
    placeObject7.setMove();
    placeObject7.setMatrix(new Matrix(3606, 2540));
    document.addTag(placeObject7);
    document.addTag(new ShowFrame());
    //Frame 8
    PlaceObject2 placeObject8 = new PlaceObject2(1);
    placeObject8.setCharacterId(shapeID);
    placeObject8.setMove();
    placeObject8.setMatrix(new Matrix(4124, 2827));
    document.addTag(placeObject8);
    document.addTag(new ShowFrame());
    //Frame 9
    PlaceObject2 placeObject9 = new PlaceObject2(1);
    placeObject9.setCharacterId(shapeID);
    placeObject9.setMove();
    placeObject9.setMatrix(new Matrix(4641, 3113));
    document.addTag(placeObject9);
    document.addTag(new ShowFrame());
    //Frame 10
    PlaceObject2 placeObject10 = new PlaceObject2(1);
    placeObject10.setCharacterId(shapeID);
    placeObject10.setMove();
    placeObject10.setMatrix(new Matrix(5159, 3400));
    document.addTag(placeObject10);
    document.addTag(new ShowFrame());
    return document;
  }
}
