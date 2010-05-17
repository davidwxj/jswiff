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
