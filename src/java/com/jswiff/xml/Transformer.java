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

package com.jswiff.xml;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.exception.DocumentException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;


/**
 * This class offers simple methods for translation between SWF and XML. If you
 * want more control over the transformation process, use
 * <code>XMLReader</code> with <code>SWFWriter</code> or
 * <code>SWFReader</code> with <code>XMLWriter</code>.
 */
public class Transformer {
  
  /**
   * Transforms XML from an input stream to a SWF, which is written to an
   * output stream.
   *
   * @param xmlStream source XML stream
   * @param swfStream target SWF stream
   *
   * @throws DocumentException if the XML document couldn't be parsed
   * @throws IOException if an I/O error occured
   */
  public static void toSWF(InputStream xmlStream, OutputStream swfStream)
    throws DocumentException, IOException {
    XMLReader reader     = new XMLReader(xmlStream);
    SWFDocument document = reader.getDocument();
    SWFWriter writer     = new SWFWriter(document, swfStream);
    writer.write();
  }

  /**
   * Transforms XML read from a reader to a SWF, which is written to an output
   * stream.
   *
   * @param xmlReader source XML reader
   * @param swfStream target SWF stream
   *
   * @throws DocumentException if the XML document couldn't be parsed
   * @throws IOException if an I/O error occured
   */
  public static void toSWF(Reader xmlReader, OutputStream swfStream)
    throws DocumentException, IOException {
    XMLReader reader     = new XMLReader(xmlReader);
    SWFDocument document = reader.getDocument();
    SWFWriter writer     = new SWFWriter(document, swfStream);
    writer.write();
  }

  /**
   * Transforms an SWF to an XML. The SWF is read from an input stream, the
   * generated XML is written to a writer. If the <code>format</code> flag is
   * set, the output XML is formatted to make it more readable.
   *
   * @param swfStream source SWF stream
   * @param writer writer used to write generated XML
   * @param format specifies whether to format the output XML
   *
   * @throws IOException if an I/O error occured
   */
  public static void toXML(
    InputStream swfStream, Writer writer, boolean format)
    throws IOException {
    SWFDocument doc     = parseSWFDocument(swfStream);
    XMLWriter xmlWriter = new XMLWriter(doc);
    xmlWriter.write(writer, format);
  }

  /**
   * Transforms an SWF to an XML. The SWF is read from an input stream, the
   * generated XML is written to an output stream. If the <code>format</code>
   * flag is set, the output XML is formatted to make it more readable.
   *
   * @param swfStream source SWF stream
   * @param xmlStream target XML stream
   * @param format specifies whether to format the output XML
   *
   * @throws IOException if an I/O error occured
   */
  public static void toXML(
    InputStream swfStream, OutputStream xmlStream, boolean format)
    throws IOException {
    SWFDocument doc     = parseSWFDocument(swfStream);
    XMLWriter xmlWriter = new XMLWriter(doc);
    xmlWriter.write(xmlStream, format);
  }
  
  /**
   * Converts a SWF document into an XML string.
   * @param doc the SWF document to convert to XML
   * @param format set to true to have the XML formatted
   * @return a string containing an XML representation of the SWF document
   * @throws IOException if there is a problem during conversion
   */
  public static String toXML(SWFDocument doc, boolean format) throws IOException {
    if (doc == null) return "";
    StringWriter xmlString = new StringWriter();
    new XMLWriter(doc).write(xmlString, format);
    return xmlString.toString();
  }

  private static SWFDocument parseSWFDocument(InputStream swfStream) throws IOException {
    SWFReader swfReader = new SWFReader(swfStream);
    return swfReader.read();
  }
  
}
