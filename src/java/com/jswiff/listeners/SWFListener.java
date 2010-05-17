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

import com.jswiff.SWFReader;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagHeader;


/**
 * Listener interface for classes wishing to receive parsing events from
 * <code>SWFReader</code>, use {@link SWFReader#addListener(SWFListener)} 
 * to register the listener.
 * 
 * {@link SWFListenerAdapter} provides an adapter for this interface.
 *
 * @see com.jswiff.SWFReader
 */
public interface SWFListener {
  
  /**
   * Triggered after reading last tag in the SWF
   */
  public void postProcess();

  /**
   * Triggered prior to reading the SWF header
   */
  public void preProcess();

  /**
   * Triggered after reading the SWF header
   *
   * @param header the header of the SWF file
   */
  public void processHeader(SWFHeader header);

  /**
   * Triggered if an error occurs while reading the SWF header
   *
   * @param e the exception which occurred while parsing the header
   */
  public void processHeaderReadError(Exception e);

  /**
   * Triggered for each tag read while parsing the SWF
   *
   * @param tag the current tag read by the <code>SWFReader</code>
   * @param streamOffset the current stream offset
   */
  public void processTag(Tag tag, long streamOffset);

  /**
   * Triggered for each tag header read while parsing the SWF
   *
   * @param tagHeader the tag header
   */
  public void processTagHeader(TagHeader tagHeader);

  /**
   * Triggered if an error occurs while reading a tag header 
   *
   * @param e the exception which occurred during tag header parsing
   */
  public void processTagHeaderReadError(Exception e);

  /**
   * <p>
   * Triggered if an error occurs while reading a tag.<br />
   * Returning true will cause the reader (<code>SWFReader</code>) to
   * stop reading the rest of the file and raise an exception.<br />
   * Returning false will cause the reader to continue and insert a
   * <code>MalformedTag</code> instance from the tag header and data.
   * This can be useful if you wish to continue regardless of tag read errors,
   * and deal with them later.<br />
   * Note that this is triggered for all listeners, and if at least one returns
   * true then the overall result is considered as true.
   * </p>
   *
   * @param tagHeader header of the malformed tag
   * @param tagData the tag data as byte array
   * @param e the exception which occured while parsing the tag
   *
   * @return <code>true</code> if reader is supposed to stop reading further
   *         tags after error processing, else <code>false</code>
   */
  public boolean processTagReadError(TagHeader tagHeader, byte[] tagData, Exception e);
  
}
