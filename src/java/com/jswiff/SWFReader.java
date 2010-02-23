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
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;
import com.jswiff.listeners.SWFListener;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.FileAttributes;
import com.jswiff.swfrecords.tags.MalformedTag;
import com.jswiff.swfrecords.tags.Metadata;
import com.jswiff.swfrecords.tags.SetBackgroundColor;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagHeader;
import com.jswiff.swfrecords.tags.TagReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads a SWF file from a stream and builds a {@link SWFDocument}.
 * Use the following code to parse a SWF into a <code>SWFDocument</code> instance:
 * <pre>
 * <code>
 * SWFReader reader = new SWFReader(inputStream);
 * SWFDocument doc  = reader.read();
 * </code>
 * </pre>
 * Additionally classes implementing the {@link SWFListener} interface can be 
 * registered to receive parsing events, for example:
 * <pre>
 * <code>
 * SWFReader reader     = new SWFReader(inputStream);
 * SWFListener listener = new SWFListenerAdapter() {
 *     public void processHeader(SWFHeader header) {
 *         System.out.println("Frame Rate: " + header.getFrameRate());
 *     }
 * };
 * reader.addListener(listener);
 * SWFDocument doc = reader.read();
 * </code>
 * </pre>
 */
public final class SWFReader {
  
  private final SWFDocument document = new SWFDocument();
  
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
   * Returns the SWF document created during parsing.
   * 
   * Read errors while processing a tag will cause an exception to be thrown,
   * this behaviour can changed to instead create a MalformedTag (and thus
   * allowing the code to continue albeit with corrupt data) by using
   * {@link SWFListener}. See {@link SWFListener#processTagReadError(TagHeader, byte[], Exception)}.
   * 
   * @see SWFListener
   * @return the parsed <code>SWFDocument</code> instance
   * @throws IOException if an error occured while reading
   */
  public SWFDocument read() throws IOException {
    preProcess();
    SWFHeader header;
    try {
      header = new SWFHeader(bitStream);
    } catch (Exception e) {
      // invoke error processing, without header we cannot do anything...
      processHeaderReadError(e);
      IOException ioe = new IOException("Error while reading SWF header");
      ioe.initCause(e);
      throw ioe;
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
        // cannot continue without tag header
        processTagHeaderReadError(e);
        IOException ioe =  new IOException("Error while reading Tag header");
        ioe.initCause(e);
        throw ioe;
      }
      processTagHeader(tagHeader);
      Tag tag        = null;
      byte[] tagData = null;
      try {
        tagData   = TagReader.readTagData(bitStream, tagHeader);
        tag       = TagReader.readTag(
            tagHeader, tagData, header.getVersion(), japanese);
        if (TagType.END.equals(tag.tagType())) {
          break;
        }
      } catch (Exception e) {
        // invoke error processing
        if (processTagReadError(tagHeader, tagData, e)) {
          TagType tt = null;
          try {
            tt = TagType.lookup(tagHeader.getCode());
          } catch (InvalidCodeException ive) { }
          IOException ioe = new IOException(
              "Error while reading Tag (" + (tt != null ? tt.getNiceName() : "UNKOWN")
              + ", code:" + tagHeader.getCode()
              + ", length:" + tagHeader.getLength() + ")");
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
    return document;
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
    document.setFrameRate(header.getFrameRate());
    document.setFrameSize(header.getFrameSize());
    document.setVersion(header.getVersion());
    document.setFileLength(header.getFileLength());
    document.setFrameCount(header.getFrameCount());
    document.setCompressed(header.isCompressed());
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
    switch (tag.tagType()) {
    case SET_BACKGROUND_COLOR:
      document.setBackgroundColor(((SetBackgroundColor) tag).getColor());
      return;
    case FILE_ATTRIBUTES:
      if ( ((FileAttributes) tag).isAllowNetworkAccess() ) {
        document.setAccessMode(SWFDocument.ACCESS_MODE_NETWORK);
      }
      return;
    case METADATA:
      document.setMetadata( ((Metadata) tag).getDataString() );
      return;
    }
    document.addTag(tag);
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
    if (listeners.size() == 0) return true;
    boolean result = false;
    for (SWFListener l : listeners) {
      result = l.processTagReadError(tagHeader, tagData, e) || result;
    }
    return result;
  }
}
