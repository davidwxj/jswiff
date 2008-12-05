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

package com.jswiff.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.dom4j.DocumentException;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;


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
