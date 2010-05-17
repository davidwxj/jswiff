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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jswiff.xml.Transformer;


/**
 * Transforms all SWF files from a directory to XML, writing the XML documents
 * to the specified directory.
 */
public class SWFDirToXML {
  /**
   * Main method.
   *
   * @param args arguments: source and destination dir
   *
   * @throws IOException if an I/O error occured
   */
  public static void main(String[] args) throws IOException {
    File sourceDir      = new File(args[0]);
    File[] sourceFiles  = sourceDir.listFiles();
    File destinationDir = (args.length > 1) ? new File(args[1])
                                            : new File(args[0]);
    for (int i = 0; i < sourceFiles.length; i++) {
      File sourceFile      = sourceFiles[i];
      File destinationFile = new File(
          destinationDir, sourceFile.getName() + ".xml");
      System.out.print("Transforming file " + sourceFile + "... ");
      Transformer.toXML(
        new FileInputStream(sourceFile), new FileOutputStream(destinationFile),
        true);
      System.out.println("done.");
    }
  }
}
