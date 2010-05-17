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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.swfrecords.tags.Tag;


/**
 * Copies all SWF files from a directory to another. Each file is first parsed
 * to an SWFDocument. Each tag of this document is deep-copied to another
 * document, which is finally written to a file. Useful for testing SWF
 * reading/writing and tag deep-copying.
 */
public class SWFDirTagCopy {
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
    File destinationDir = new File(args[1]);
    for (int i = 0; i < sourceFiles.length; i++) {
      File sourceFile      = sourceFiles[i];
      File destinationFile = new File(destinationDir, sourceFile.getName());
      System.out.print("Duplicating file " + sourceFile + "... ");
      copy(sourceFile, destinationFile);
      System.out.println("done.");
    }
  }

  private static void copy(File source, File destination) throws IOException {
    SWFReader reader = new SWFReader(new FileInputStream(source));
    SWFDocument sourceDoc = reader.read();
    List<Tag> tags        = sourceDoc.getTags();
    List<Tag> tagCopies   = new ArrayList<Tag>();
    for (Tag t : tags) {
      tagCopies.add(t.copy());
    }
    SWFDocument targetDoc = copyDocProperties(sourceDoc);
    targetDoc.addTags(tagCopies);
    SWFWriter writer = new SWFWriter(
        targetDoc, new FileOutputStream(destination));
    writer.write();
  }

  private static SWFDocument copyDocProperties(SWFDocument sourceDoc) {
    SWFDocument targetDoc = new SWFDocument();
    targetDoc.setBackgroundColor(sourceDoc.getBackgroundColor());
    targetDoc.setCompressed(sourceDoc.isCompressed());
    targetDoc.setFrameRate(sourceDoc.getFrameRate());
    targetDoc.setFrameSize(sourceDoc.getFrameSize());
    short version = sourceDoc.getVersion();
    targetDoc.setVersion(version);
    if (version >= 8) {
      targetDoc.setAccessMode(sourceDoc.getAccessMode());
      targetDoc.setMetadata(sourceDoc.getMetadata());
    }
    return targetDoc;
  }
}
