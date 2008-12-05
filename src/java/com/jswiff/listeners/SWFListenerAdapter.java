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

import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagHeader;


/**
 * Adapter class for SWF listeners, provides empty implementations of methods
 * in {@link SWFListener}.
 * 
 * @see com.jswiff.SWFReader
 */
public abstract class SWFListenerAdapter implements SWFListener {

  public void postProcess() { }
  
  public void preProcess() { }
  
  public void processHeader(SWFHeader header) { }
  
  public void processTag(Tag tag, long streamOffset) { }
  
  public void processTagHeader(TagHeader tagHeader) { }

  public void processHeaderReadError(Exception e) { }

  public void processTagHeaderReadError(Exception e) { }

  /**
   * Returns true
   */
  public boolean processTagReadError(TagHeader tagHeader, byte[] tagData, Exception e) {
    return true;
  }
  
}
