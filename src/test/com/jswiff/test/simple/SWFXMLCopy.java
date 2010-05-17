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

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.exception.DocumentException;
import com.jswiff.xml.XMLReader;
import com.jswiff.xml.XMLWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


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
