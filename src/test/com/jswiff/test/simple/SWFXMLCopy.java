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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.DocumentException;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.xml.XMLReader;
import com.jswiff.xml.XMLWriter;


/**
 * Parses an SWF file, transforms it to XML, parses it back to a SWF document
 * and writes it to a file. Useful for testing SWF and XML read/write.
 */
public class SWFXMLCopy {
  /**
   * Main method.
   *
   * @param args arguments: source and destination file
   *
   * @throws IOException if an I/O error occured
   * @throws DocumentException if XML is malformed
   */
  public static void main(String[] args) throws IOException, DocumentException {
    SWFReader reader           = new SWFReader(new FileInputStream(args[0]));
    SWFDocument doc            = reader.read();
    XMLWriter xmlWriter        = new XMLWriter(doc);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xmlWriter.write(baos, false);
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    XMLReader xmlReader       = new XMLReader(bais);
    doc                       = xmlReader.getDocument();
    SWFWriter writer          = new SWFWriter(
        doc, new FileOutputStream(args[1]));
    writer.write();
  }
}
