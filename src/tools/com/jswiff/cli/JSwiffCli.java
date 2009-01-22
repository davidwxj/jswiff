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

package com.jswiff.cli;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.xml.XMLReader;
import com.jswiff.xml.XMLWriter;

/**
 * Basic command line interface
 * @author bstock
 *
 */
public class JSwiffCli {

  private static final String progName = "jswiff";
  private static final String basicUsage = "Usage: java -jar "+progName+".jar [-h] INPUT [OUTPUT]";
  
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println(basicUsage);
      System.out.println("Use -h for more information ...");
      System.exit(1);
    }
    
    if ("-h".equals(args[0])) {
      printHelp();
      return;
    }
    
    File input = new File(args[0]);
    boolean swfInput = isSwfFile(input.getName());
    
    File output;
    boolean swfOutput;
    if (args.length > 1) {
      output = new File(args[1]);
      if (output.equals(input)) {
        System.out.println("The input and output files can not be the same!");
        System.exit(1);
      }
      swfOutput = isSwfFile(output.getName());
    } else {
      String base = input.getPath().substring(0, input.getPath().length()-4);
      output = new File( base.concat( swfInput ? ".xml" : "_REBUILT.swf" ));
      swfOutput = !swfInput;
    }
    
    try {
      SWFDocument doc = swfInput ? readSwf(input) : readXml(input);
      if (swfOutput) { writeSwf(doc, output); } else { writeXml(doc, output); };
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static boolean isSwfFile(String path) {
    boolean swf = path.toLowerCase().endsWith(".swf");
    boolean xml = path.toLowerCase().endsWith(".xml");
    if (!swf && !xml) {
      System.out.println("Input/Output files must be either a Swf or an Xml file");
      System.exit(1);
    }
    return (swf);
  }
  
  private static SWFDocument readSwf(File file) throws Exception {
    BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
    SWFDocument doc = new SWFReader(fin).read();
    fin.close();
    return doc;
  }
  
  private static void writeSwf(SWFDocument doc, File file) throws Exception {
    BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(file));
    new SWFWriter(doc, fout).write();
    fout.close();
  }
  
  private static SWFDocument readXml(File file) throws Exception {
    BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
    SWFDocument doc = new XMLReader(fin).getDocument();
    fin.close();
    return doc;
  }
  
  private static void writeXml(SWFDocument doc, File file) throws Exception {
    BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(file));
    new XMLWriter(doc).write(fout, true);
    fout.close();
  }
  
  private static void printHelp() {
    System.out.println(basicUsage + "\n");
    System.out.printf(
        " -h     : view this help\n" +
        " INPUT  : (Required) A path to either a Swf or Xml file.\n" +
        " OUTPUT : (Optional) A path specifying the name of the output file.\n" +
        "          If this is not supplied, Swf files will be serialised to\n" +
        "          <INPUT>.xml, and Xml files will be deserialised to\n" +
        "          <INPUT>_REBUILT.swf otherwise the output format depends on\n" +
        "          the value given.\n" +
        " EXAMPLES: \n" +
        " Read in an Xml file and deserialise to mySwfFile_REBUILT.swf\n" +
        "   java -jar %s.jar mySwfFile.xml\n\n" +
        " Read in a Swf and serialise to mySwfFile.xml\n" +
        "   java -jar %s.jar mySwfFile.swf\n\n" +
        " As above but output to dump.xml\n" +
        "   java -jar %s.jar mySwfFile.swf dump.xml\n\n" +
        " Same again, but have jswiff output straight back out as a swf\n" +
        "   java -jar %s.jar mySwfFile.swf rebuilt.swf\n",
        progName, progName, progName, progName);
  }
  
}
