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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;


/**
 * Parses an SWF file and writes it to another file
 */
public class SWFCopy {
  /**
   * Main method.
   *
   * @param args arguments: source and destination file
   *
   * @throws IOException if an I/O error occured
   */
  public static void main(String[] args) throws IOException {
    SWFReader reader = new SWFReader(new FileInputStream(args[0]));
    SWFDocument doc  = reader.read();
    SWFWriter writer = new SWFWriter(doc, new FileOutputStream(args[1]));
    writer.write();
  }
}
