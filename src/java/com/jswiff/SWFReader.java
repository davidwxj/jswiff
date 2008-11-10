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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.listeners.SWFListener;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.MalformedTag;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;
import com.jswiff.swfrecords.tags.TagHeader;
import com.jswiff.swfrecords.tags.TagReader;

/**
 * This class reads an SWF file from a stream, invoking registered listeners to
 * process the SWF. Use the following code to parse a SWF into a
 * <code>SWFDocument</code> instance:
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
public final class SWFReader {
  private InputBitStream bitStream;
  private List<SWFListener> listeners = new ArrayList<SWFListener>();
  private boolean japanese;

  /**
   * Creates a new SWF reader which reads from the specified stream.
   *
   * @param stream the input stream the SWF file is read from
   */
  public SWFReader(InputStream stream) {
    this.bitStream = new InputBitStream(stream);
  }

  /**
   * Specifies whether strings should be decoded using Japanese encoding
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
   * Registers a listener in order to process the SWF content.
   *
   * @param listener a <code>SWFListener</code> instance
   */
  public void addListener(SWFListener listener) {
    listeners.add(listener);
  }

  /**
   * Reads the SWF content from the stream passed to the constructor, and
   * invokes the methods of the registered listeners. Finally, the stream is
   * closed.
   *
   * @see SWFListener
   * 
   * @throws IOException if an error occured while reading
   */
  public void read() throws IOException {
    preProcess();
    SWFHeader header;
    try {
      header = new SWFHeader(bitStream);
    } catch (Exception e) {
      // invoke error processing
      processHeaderReadError(e);
      return; // without header we cannot do anything...
    }
    processHeader(header);
    do {
      // we check this because of an OpenOffice export bug
      // (END tag written as a UI8 (00)instead of an UI16 (00 00))
      if ((header.getFileLength() - bitStream.getOffset()) < 2) {
        break;
      }
      TagHeader tagHeader = null;
      try {
        tagHeader = TagReader.readTagHeader(bitStream);
      } catch (Exception e) {
        processTagHeaderReadError(e);
        break; // cannot continue without tag header
      }
      processTagHeader(tagHeader);
      Tag tag        = null;
      byte[] tagData = null;
      try {
        tagData   = TagReader.readTagData(bitStream, tagHeader);
        tag       = TagReader.readTag(
            tagHeader, tagData, header.getVersion(), japanese);
        if (tag.getCode() == TagConstants.END) {
          break;
        }
      } catch (Exception e) {
        // invoke error processing
        if (processTagReadError(tagHeader, tagData, e)) {
          IOException ioe = new IOException("Error while parsing SWF");
          ioe.initCause(e);
          throw ioe;
        }
        tag = new MalformedTag(tagHeader, tagData, e);
      }
      processTag(tag, bitStream.getOffset());
    } while (true);
    postProcess();
    try {
      bitStream.close();
    } catch (Exception e) {
      // empty on purpose - don't need to propagate errors which occur while closing
    }
  }

  private void postProcess() {
    for (SWFListener l : listeners) {
      l.postProcess();
    }
  }

  private void preProcess() {
    for (SWFListener l : listeners) {
      l.preProcess();
    }
  }

  private void processHeader(SWFHeader header) {
    for (SWFListener l : listeners) {
      l.processHeader(header);
    }
  }

  private void processHeaderReadError(Exception e) {
    for (SWFListener l : listeners) {
      l.processHeaderReadError(e);
    }
  }

  private void processTag(Tag tag, long streamOffset) {
    for (SWFListener l : listeners) {
      l.processTag(tag, streamOffset);
    }
  }

  private void processTagHeader(TagHeader tagHeader) {
    for (SWFListener l : listeners) {
      l.processTagHeader(tagHeader);
    }
  }

  private void processTagHeaderReadError(Exception e) {
    for (SWFListener l : listeners) {
      l.processTagHeaderReadError(e);
    }
  }

  private boolean processTagReadError(
    TagHeader tagHeader, byte[] tagData, Exception e) {
    boolean result = false;
    for (SWFListener l : listeners) {
      result = l.processTagReadError(tagHeader, tagData, e) || result;
    }
    return result;
  }
}
