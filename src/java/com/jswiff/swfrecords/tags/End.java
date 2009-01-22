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

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.OutputBitStream;

/**
 * Defines an End tag used to mark the end of the Swf and DefineSprites.
 * 
 * @since SWF 9
 */
public final class End extends Tag {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new End instance.
   */
  public End() {
    super(TagType.END);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
  }

  void setData(byte[] data) throws IOException, InvalidCodeException {
  }
  
}