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

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

/**
 * Contains XML metadata in Dublin Core RDF format. Do NOT add this tag to your
 * <code>SWFDocument</code>, use its <code>setMetadata</code> method instead!
 */
public class Metadata extends Tag {

  private static final long serialVersionUID = 1L;

  private String dataString;

  /**
   * Creates a new Metadata instance.
   * 
   * @param dataString
   *          metadata as Dublin Core RDF
   */
  public Metadata(String dataString) {
    super(TagType.METADATA);
    this.dataString = dataString;
  }

  Metadata() {
    super(TagType.METADATA);
  }

  /**
   * Sets the metadata of the document. Use Dublin Core RDF.
   * 
   * @param dataString
   *          metadata as Dublin Core RDF
   */
  public void setDataString(String dataString) {
    this.dataString = dataString;
  }

  /**
   * Returns the metadata of the document (if set). Dublin Core RDF is used.
   * 
   * @return metadata as Dublin Core RDF
   */
  public String getDataString() {
    return dataString;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeString(dataString);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    dataString = inStream.readString();
  }
}
