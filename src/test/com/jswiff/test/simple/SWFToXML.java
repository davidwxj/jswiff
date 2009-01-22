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

import com.jswiff.xml.Transformer;


/**
 * Transforms an SWF to XML and outputs it to the console.
 */
public class SWFToXML {
  /**
   * Main method.
   *
   * @param args pass one SWF file path
   *
   * @throws IOException if an I/O error occured
   */
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Please pass a source SWF and a target XML file path!");
      return;
    }
    Transformer.toXML(new FileInputStream(args[0]), new FileOutputStream(args[1]), true);
  }
}
