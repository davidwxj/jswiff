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
