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
import com.jswiff.swfrecords.RGB;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a Macromedia Flash (SWF) document. Contains an SWF
 * header and a list of tags. Use a <code>SWFReader</code> to parse a file to
 * an <code>SWFDocument</code> instance. Write SWF files using
 * <code>SWFWriter</code>.
 */
public class SWFDocument implements Serializable {

  private static final long serialVersionUID = 1L;

  /** The version of the JSwiff library */
  public static final String JSWIFF_VERSION       = "9-svn";

  /**
   * The default value for the compression flag. By default, compression is on
   */
  public static final boolean DEFAULT_COMPRESSION = true;

  /** The default SWF version */
  public static final short DEFAULT_SWF_VERSION   = 9;

  /**
   * The default frame size (based on the authoring tool from Macromedia: 11000
   * x 8000 twips)
   */
  public static final Rect DEFAULT_FRAME_SIZE     = new Rect(0, 11000, 0, 8000);

  /**
   * The default frame rate of a (newly created) SWF movie (based on the
   * authoring tool from Macromedia: 12 fps)
   */
  public static final short DEFAULT_FRAME_RATE    = 12;

  /** The maximum allowed SWF version */
  public static final short MAX_SWF_VERSION       = 10;

  /** Grants the SWF local file access */
  public static final byte ACCESS_MODE_LOCAL      = 0;

  /** Grants the SWF network access (local access is denied) */
  public static final byte ACCESS_MODE_NETWORK    = 1;

  private SWFHeader header                        = new SWFHeader();
  private int currentCharId;
  private List<Tag> tags                          = new ArrayList<Tag>();
  private RGB backgroundColor                     = new RGB(
      (short) 255, (short) 255, (short) 255);
  private byte accessMode                         = ACCESS_MODE_LOCAL;
  private String metadata;

  /**
   * Creates a new SWFDocument instance.
   */
  public SWFDocument() {
    header.setCompressed(DEFAULT_COMPRESSION);
    header.setVersion(DEFAULT_SWF_VERSION);
    header.setFrameSize(DEFAULT_FRAME_SIZE);
    header.setFrameRate(DEFAULT_FRAME_RATE);
  }

  /**
   * Sets the access mode for the SWF (local or network).
   *
   * @param accessMode either ACCESS_MODE_LOCAL or ACCESS_MODE_NETWORK
   */
  public void setAccessMode(byte accessMode) {
    this.accessMode = accessMode;
  }

  /**
   * Returns the access mode of the SWF (local or network).
   *
   * @return either ACCESS_MODE_LOCAL or ACCESS_MODE_NETWORK
   */
  public byte getAccessMode() {
    return accessMode;
  }

  /**
   * Sets the background color of the document.
   *
   * @param backgroundColor background color (as <code>RGB</code> instance)
   */
  public void setBackgroundColor(RGB backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  /**
   * Returns the background color of the document.
   *
   * @return background color (as <code>RGB</code> instance)
   */
  public RGB getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Sets the compression flag of the SWF
   *
   * @param compressed <code>true</code> activates the compression
   */
  public void setCompressed(boolean compressed) {
    header.setCompressed(compressed);
  }

  /**
   * Checks if the SWF file is compressed.
   *
   * @return <code>true</code> if compression is on, otherwise
   *         <code>false</code>
   */
  public boolean isCompressed() {
    return header.isCompressed();
  }

  /**
   * Sets the file length. This value is computed and set when writing the SWF
   * with <code>SWFWriter</code>.
   *
   * @param fileLength file length in bytes
   */
  public void setFileLength(long fileLength) {
    header.setFileLength(fileLength);
  }

  /**
   * Returns the length of the SWF file. This value is computed and set when
   * writing the SWF with <code>SWFWriter</code>.
   *
   * @return SWF file length
   */
  public long getFileLength() {
    return header.getFileLength();
  }

  /**
   * Sets the number of frames in the SWF movie. This value is computed and set
   * when the document is written with <code>SWFWriter</code>.
   *
   * @param frameCount frame count
   */
  public void setFrameCount(int frameCount) {
    header.setFrameCount(frameCount);
  }

  /**
   * Returns the number of frames in the SWF movie. This value is computed and
   * set when the document is written with <code>SWFWriter</code>.
   *
   * @return frame count
   */
  public int getFrameCount() {
    return header.getFrameCount();
  }

  /**
   * Sets the frame rate of the SWF. The default is 12.
   *
   * @param frameRate the frame rate (in fps)
   */
  public void setFrameRate(short frameRate) {
    header.setFrameRate(frameRate);
  }

  /**
   * Returns the frame rate of the SWF movie.
   *
   * @return the frame rate in fps
   */
  public short getFrameRate() {
    return header.getFrameRate();
  }

  /**
   * Sets the frame size of the SWF. The default is Rect(0, 11000, 0, 8000),
   * i.e. width = 550 px and height = 400 px.
   *
   * @param frameSize the size of the SWF frames.
   */
  public void setFrameSize(Rect frameSize) {
    header.setFrameSize(frameSize);
  }

  /**
   * Returns the frame size of the SWF movie.
   *
   * @return the frame size (as <code>Rect</code> instance)
   */
  public Rect getFrameSize() {
    return header.getFrameSize();
  }

  /**
   * Sets the metadata of the document. Dublin Core RDF is used here.
   *
   * @param metadata document metadata as Dublin Core RDF
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * Returns the metadata of the document (if set). Dublin Core RDF is used
   * here.
   *
   * @return document metadata as Dublin Core RDF
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * Returns a new character ID. Character IDs start at 1 and are consecutive.
   *
   * @return new character ID
   */
  public int getNewCharacterId() {
    return ++currentCharId;
  }

  /**
   * Returns a list containing this SWF document's tags.
   *
   * @return the tag list
   */
  public List<Tag> getTags() {
    return tags;
  }

  public boolean hasSymbolClass() {
    for (Tag t : tags) {
      if (TagType.SYMBOL_CLASS.equals(t.tagType())) return true;
    }
    return false;
  }

  public boolean hasABC() {
    for (Tag t : tags) {
      if (TagType.DO_ABC.equals(t.tagType()) ||
          TagType.DO_ABC_DEFINE.equals(t.tagType()) ) return true;
    }
    return false;
  }

  /**
   * Sets the Flash version of the file, must be between 1 and
   * <code>MAX_SWF_VERSION</code>. The default value is
   * <code>DEFAULT_SWF_VERSION</code>.
   *
   * @param version value for the Flash version
   *
   * @throws IllegalArgumentException if version not in specified interval
   */
  public void setVersion(short version) {
    if (version < 1) {
      throw new IllegalArgumentException("Flash version must be at least 1!");
    } else if (version > MAX_SWF_VERSION) {
      throw new IllegalArgumentException(
        "Flash version > " + MAX_SWF_VERSION + " not supported!");
    }
    header.setVersion(version);
  }

  /**
   * Returns the SWF version of the file.
   *
   * @return SWF version
   */
  public short getVersion() {
    return header.getVersion();
  }

  /**
   * Adds a tag to the SWF document to be written. Warning: no checking of
   * compliance with particular SWF versions!
   *
   * @param tag the SWF tag to be added
   */
  public void addTag(Tag tag) {
    tags.add(tag);
  }

  /**
   * Adds a list of tags to the SWF document to be written. Warning: no
   * checking of compliance with particular SWF versions!
   *
   * @param tagList a list of tags
   */
  public void addTags(List<Tag> tagList) {
    tags.addAll(tagList);
  }

  /**
   * Removes the specified tag from the document.
   *
   * @param tag tag to be removed
   *
   * @return <code>true</code> if document contained the specified tag
   */
  public boolean removeTag(Tag tag) {
    return tags.remove(tag);
  }

  /**
   * Removes the tag at the specified position within the document.
   *
   * @param index index of the tag to be removed
   *
   * @return the tag previously contained at specified position
   */
  public Tag removeTag(int index) {
    return tags.remove(index);
  }
}
