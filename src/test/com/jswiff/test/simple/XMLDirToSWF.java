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

import com.jswiff.exception.DocumentException;
import com.jswiff.xml.Transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Transforms all XML files from a directory to SWF, writing the SWF files to
 * the specified directory.
 */
public class XMLDirToSWF {
  /**
   * Main method.
   *
   * @param args arguments: source and destination dir
   *
   * @throws IOException if an I/O error occured
   * @throws DocumentException if the XML could not be parsed
   */
  public static void main(String[] args) throws IOException, DocumentException {
    File sourceDir      = new File(args[0]);
    File[] sourceFiles  = sourceDir.listFiles();
    File destinationDir = (args.length > 1) ? new File(args[1])
                                            : new File(args[0]);
    for (int i = 0; i < sourceFiles.length; i++) {
      File sourceFile      = sourceFiles[i];
      File destinationFile = new File(
          destinationDir, sourceFile.getName() + ".swf");
      System.out.print("Transforming file " + sourceFile + "... ");
      Transformer.toSWF(
        new FileInputStream(sourceFile), new FileOutputStream(destinationFile));
      System.out.println("done.");
    }
  }
}
