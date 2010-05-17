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

package com.jswiff;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.tags.FileAttributes;
import com.jswiff.swfrecords.tags.Metadata;
import com.jswiff.swfrecords.tags.SetBackgroundColor;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagWriter;

import java.io.IOException;
import java.io.OutputStream;


/**
 * This class writes an SWF document (an <code>SWFDocument</code> instance) to
 * an output stream. Usage:
 * <pre>
 * <code>
 * SWFWriter writer = new SWFWriter(doc, outputStream);
 * writer.write();
 * </code>
 * </pre>
 */
public class SWFWriter {
  private OutputBitStream bitStream;
  private SWFDocument document;
  private boolean japanese;

  /**
   * Creates a new SWF writer which writes the specified SWF document to the
   * stream supplied here.
   *
   * @param document the SWF document to be written
   * @param stream the output stream the SWF file is written to
   */
  public SWFWriter(SWFDocument document, OutputStream stream) {
    bitStream       = new OutputBitStream(stream);
    this.document   = document;
  }

  /**
   * Specifies whether strings should be encoded using Japanese encoding
   * (Shift-JIS). This is relevant only for SWF 5 or earlier, where strings
   * are encoded using either ANSI or Shift-JIS. In Flash Player, the decoding
   * choice is made depending on the locale, as this information is not stored
   * in the SWF. Later SWF versions use Unicode (UTF-8) and ignore this
   * option.
   *
   * @param japanese <code>true</code> if Shift-JIS encoding is to be used
   */
  public void setJapanese(boolean japanese) {
    this.japanese = japanese;
  }

  /**
   * Writes the SWF to the stream passed to the constructor. The stream is then
   * closed.
   *
   * @throws IOException if an I/O error occured
   */
  public void write() throws IOException {
    try {
      byte[] docPropertiesTagsBuffer = getDocPropertiesTagsBuffer();
      byte[] tagsBuffer              = TagWriter.writeTags(
          document.getTags(), document.getVersion(), japanese);
      byte[] headerEndData           = getHeaderEndData();
      long fileLength                = 8 + headerEndData.length +
        tagsBuffer.length + docPropertiesTagsBuffer.length;
      writeHeaderStart();
      bitStream.writeUI32(fileLength);
      if (document.isCompressed()) {
        bitStream.enableCompression();
      }
      bitStream.writeBytes(headerEndData);
      // header written, now write document property tags (background, file attrs, metadata)
      bitStream.writeBytes(docPropertiesTagsBuffer);
      // write all remaining tags
      bitStream.writeBytes(tagsBuffer);
    } finally {
      try {
        bitStream.close();
      } catch (Exception e) {
        // empty on purpose - don't need to propagate errors which occur while closing
      }
    }
  }

  private byte[] getDocPropertiesTagsBuffer() throws IOException {
    OutputBitStream tagStream = new OutputBitStream();
    if (document.getVersion() >= 8) {
      FileAttributes fileAttributes = new FileAttributes();
      fileAttributes.setAllowNetworkAccess(
        document.getAccessMode() == SWFDocument.ACCESS_MODE_NETWORK);
      fileAttributes.setHasABC(document.hasABC());
      String metadata = document.getMetadata();
      if (metadata != null || (document.getVersion() >= 9 && document.hasSymbolClass())) {
        fileAttributes.setHasMetadata(true);
      }
      TagWriter.writeTag(tagStream, fileAttributes, document.getVersion());
      if (metadata != null) {
        TagWriter.writeTag(
          tagStream, new Metadata(metadata), document.getVersion());
      }
    }
    TagWriter.writeTag(
      tagStream, new SetBackgroundColor(document.getBackgroundColor()),
      document.getVersion());
    return tagStream.getData();
  }

  private int getFrameCount() {
    int count = 0;
    for (Tag t : document.getTags()) {
      if (TagType.SHOW_FRAME.equals(t.tagType())) {
        count++;
      }
    }
    return count;
  }

  private byte[] getHeaderEndData() throws IOException {
    OutputBitStream headerStream = new OutputBitStream();

    // frame size
    document.getFrameSize().write(headerStream);
    // frame rate
    headerStream.writeUI8((short) 0); // this byte is ignored
    headerStream.writeUI8(document.getFrameRate());
    // frame count
    headerStream.writeUI16(getFrameCount());
    byte[] headerData = headerStream.getData();
    return headerData;
  }

  private void writeHeaderStart() throws IOException {
    // writes CWS/FWS and version - that's 4 bytes
    // C (0x43) for compressed or F (0x46) for uncompressed files
    bitStream.writeUI8((short) (document.isCompressed() ? 0x43 : 0x46));
    // WS (0x57 0x53)
    bitStream.writeBytes(new byte[] { 0x57, 0x53 });
    // version
    bitStream.writeUI8(document.getVersion());
  }
}
