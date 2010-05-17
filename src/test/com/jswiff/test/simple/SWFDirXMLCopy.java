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

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.exception.DocumentException;
import com.jswiff.xml.XMLReader;
import com.jswiff.xml.XMLWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Copies all SWF files from a directory to another. Each file is first parsed
 * to an SWFDocument, which is exported to an in-memory XML. This XML is
 * parsed to another SWF document, which is finally written to a file. Useful
 * for SWF and XML read/write tests.
 */
public class SWFDirXMLCopy {
  /**
   * Main method.
   *
   * @param args arguments: source and destination dir
   *
   * @throws IOException if an I/O error occured
   * @throws DocumentException
   */
  public static void main(String[] args) throws IOException, DocumentException {
    File sourceDir      = new File(args[0]);
    File[] sourceFiles  = sourceDir.listFiles();
    File destinationDir = new File(args[1]);
    for (int i = 0; i < sourceFiles.length; i++) {
      File sourceFile      = sourceFiles[i];
      File destinationFile = new File(destinationDir, sourceFile.getName());
      System.out.print("Duplicating file " + sourceFile + "... ");
      copy(sourceFile, destinationFile);
      System.out.println("done.");
    }
  }

  private static void copy(File source, File destination)
    throws IOException, DocumentException {
    SWFReader reader           = new SWFReader(new FileInputStream(source));
    SWFDocument sourceDoc      = reader.read();
    XMLWriter xmlWriter        = new XMLWriter(sourceDoc);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xmlWriter.write(baos, false);
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    XMLReader xmlReader       = new XMLReader(bais);
    SWFDocument targetDoc     = xmlReader.getDocument();
    SWFWriter writer          = new SWFWriter(
        targetDoc, new FileOutputStream(destination));
    writer.write();
  }
}
