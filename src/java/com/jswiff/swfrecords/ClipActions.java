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

package com.jswiff.swfrecords;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class is used for defining event handlers for sprites. Used within the
 * <code>PlaceObject2</code> tag.
 *
 * @see com.jswiff.swfrecords.tags.PlaceObject2
 */
public final class ClipActions implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private ClipEventFlags eventFlags;
  private List<ClipActionRecord> clipActionRecords = new ArrayList<ClipActionRecord>();

  /**
   * Creates a new ClipActions instance. Supply event flags and handlers.
   *
   * @param eventFlags all events used in the clip actions
   * @param clipActionRecords list of one or more event handlers
   *        (<code>ClipActionRecord</code> instances)
   *
   * @see ClipActionRecord
   */
  public ClipActions(ClipEventFlags eventFlags, List<ClipActionRecord> clipActionRecords) {
    this.eventFlags          = eventFlags;
    this.clipActionRecords   = clipActionRecords;
  }

  /**
   * Creates a new ClipActions instance, reading data from a bit stream.
   *
   * @param stream source bit stream
   * @param swfVersion swf version used
   *
   * @throws IOException if an I/O error has occured
   */
  public ClipActions(InputBitStream stream, short swfVersion)
    throws IOException {
    stream.readUI16(); // reserved, =0
    eventFlags = new ClipEventFlags(stream, swfVersion);
    while (true) {
      int available = stream.available();
      if (
        ((swfVersion <= 5) && (available == 2)) ||
            ((swfVersion > 5) && (available == 4))) {
        // ClipActionEndFlag is UI16 for pre-MX, UI32 for MX and higher
        break;
      }
      ClipActionRecord record = new ClipActionRecord(stream, swfVersion);
      clipActionRecords.add(record);
    }
  }

  /**
   * Returns a list containing the event handlers (as
   * <code>ClipActionRecord</code> instance).
   *
   * @return list with event handlers
   *
   * @see ClipActionRecord
   */
  public List<ClipActionRecord> getClipActionRecords() {
    return clipActionRecords;
  }

  /**
   * Returns all event flags for this sprite.
   *
   * @return event flags
   */
  public ClipEventFlags getEventFlags() {
    return eventFlags;
  }

  /**
   * Writes this instance to a bit stream.
   *
   * @param stream target bit stream
   * @param swfVersion used
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream, short swfVersion)
    throws IOException {
    stream.writeUI16(0); // reserved
    eventFlags.write(stream, swfVersion);
    for (Iterator<ClipActionRecord> iter = clipActionRecords.iterator(); iter.hasNext();) {
      ClipActionRecord record = iter.next();
      record.write(stream, swfVersion);
    }

    // write clipActionEndFlag (0, UI16 for flash <= 5, else UI32!)
    if (swfVersion <= 5) {
      stream.writeUI16(0);
    } else {
      stream.writeUI32(0);
    }
  }
}
