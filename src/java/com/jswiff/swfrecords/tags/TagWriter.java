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

package com.jswiff.swfrecords.tags;

import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.util.List;

/**
 * This class contains methods used for writing tag headers and tags.
 */
public final class TagWriter {

  private static final long serialVersionUID = 1L;

  private TagWriter() {
    // prohibits instantiation
  }

  /**
   * Writes a tag to a bit stream.
   * 
   * @param stream
   *          target bit stream
   * @param tag
   *          tag to be written
   * @param swfVersion
   *          SWF version
   * 
   * @throws IOException
   *           if an I/O error occured
   */
  public static void writeTag(OutputBitStream stream, Tag tag, short swfVersion) throws IOException {
    tag.setSWFVersion(swfVersion);
    tag.write(stream);
  }

  /**
   * Writes a tag to a data buffer.
   * 
   * @param tag
   *          tag to be written
   * @param swfVersion
   *          SWF version
   * 
   * @return byte array containing tag
   * 
   * @throws IOException
   *           if an I/O error occured
   */
  public static byte[] writeTag(Tag tag, short swfVersion) throws IOException {
    OutputBitStream stream = new OutputBitStream();
    writeTag(stream, tag, swfVersion);
    return stream.getData();
  }

  /**
   * Writes the tags from the passed list to a byte array. Additionally, an end
   * tag is written at the end.
   * 
   * @param tags
   *          list of tags to be written
   * @param swfVersion
   *          SWF version
   * @param japanese
   *          specifies whether japanese encoding is to be used for strings
   * 
   * @return byte array containing tags
   * 
   * @throws IOException
   *           if an I/O error occured
   */
  public static byte[] writeTags(List<Tag> tags, short swfVersion, boolean japanese) throws IOException {
    OutputBitStream stream = new OutputBitStream();
    if (swfVersion < 6) {
      if (japanese) {
        stream.setShiftJIS(true);
      } else {
        stream.setANSI(true);
      }
    }
    writeTags(stream, tags, swfVersion);
    return stream.getData();
  }

  /**
   * Writes the tags from the passed list to a bit stream. Additionally, an end
   * tag is written at the end.
   * 
   * @param stream
   *          target bit stream
   * @param tags
   *          list of tags to be written
   * @param swfVersion
   *          SWF version
   * 
   * @throws IOException
   *           if an I/O error occured
   */
  public static void writeTags(OutputBitStream stream, List<Tag> tags, short swfVersion) throws IOException {
    for (Tag tag : tags) {
      writeTag(stream, tag, swfVersion);
    }

    // write end tag
    stream.writeUI16(0);
  }
}
