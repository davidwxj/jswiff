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

package com.jswiff.listeners;

import com.jswiff.SWFDocument;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.FileAttributes;
import com.jswiff.swfrecords.tags.Metadata;
import com.jswiff.swfrecords.tags.SetBackgroundColor;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;


/**
 * Simple implementation of an <code>SWFListener</code>, can be used to create
 * a <code>SWFDocument</code>. During file parsing, the header and the tags
 * are stored in this document and some properties (e.g. background color,
 * metadata, access mode) are set. Usage:
 * <pre>
 * <code>
 * SWFReader reader            = new SWFReader(inputStream);
 * SWFDocumentReader docReader = new SWFDocumentReader();
 * reader.addListener(docReader);
 * reader.read();
 * SWFDocument doc             = docReader.getDocument();
 * </code>
 * </pre>
 */
public class SWFDocumentReader extends SWFListener {
  private SWFDocument document = new SWFDocument();

  /**
   * Returns the SWF document created during parsing.
   *
   * @return the parsed <code>SWFDocument</code> instance
   */
  public SWFDocument getDocument() {
    return document;
  }

  /**
   * Stores the SWF header in the document.
   *
   * @param header the parsed SWF header
   *
   * @see SWFListener#processHeader(SWFHeader)
   */
  public void processHeader(SWFHeader header) {
    document.setFrameRate(header.getFrameRate());
    document.setFrameSize(header.getFrameSize());
    document.setVersion(header.getVersion());
    document.setFileLength(header.getFileLength());
    document.setFrameCount(header.getFrameCount());
    document.setCompressed(header.isCompressed());
  }

  /**
   * Stores every tag in the document.
   *
   * @param tag the current tag read by the <code>SWFReader</code>
   * @param streamOffset the current stream offset
   *
   * @see SWFListener#processTag(Tag, long)
   */
  public void processTag(Tag tag, long streamOffset) {
    switch (tag.getCode()) {
      case TagConstants.SET_BACKGROUND_COLOR:
        document.setBackgroundColor(((SetBackgroundColor) tag).getColor());
        return;
      case TagConstants.FILE_ATTRIBUTES:
        if ( ((FileAttributes) tag).isAllowNetworkAccess() ) {
            document.setAccessMode(SWFDocument.ACCESS_MODE_NETWORK);
        }
        return;
      case TagConstants.METADATA:
        document.setMetadata( ((Metadata) tag).getDataString() );
        return;
    }
    document.addTag(tag);
  }

}
