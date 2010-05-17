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


/**
 * This class provides information used for playing an event sound defined with
 * the <code>DefineSound</code> tag.
 *
 * @see com.jswiff.swfrecords.tags.DefineSound
 */
public final class SoundInfo implements Serializable {
  private boolean syncStop;
  private boolean syncNoMultiple;
  private long inPoint;
  private long outPoint;
  private int loopCount;
  private SoundEnvelope[] envelopeRecords;
  private boolean hasEnvelope;
  private boolean hasLoops;
  private boolean hasOutPoint;
  private boolean hasInPoint;

  /**
   * Creates a new SoundInfo instance.
   */
  public SoundInfo() {
    // empty
  }

  /**
   * Creates a new SoundInfo instance, reading data from a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public SoundInfo(InputBitStream stream) throws IOException {
    stream.readUnsignedBits(2); // 2 reserved bits
    syncStop         = stream.readBooleanBit();
    syncNoMultiple   = stream.readBooleanBit();
    hasEnvelope      = stream.readBooleanBit();
    hasLoops         = stream.readBooleanBit();
    hasOutPoint      = stream.readBooleanBit();
    hasInPoint       = stream.readBooleanBit();
    if (hasInPoint) {
      inPoint = stream.readUI32();
    }
    if (hasOutPoint) {
      outPoint = stream.readUI32();
    }
    if (hasLoops) {
      loopCount = stream.readUI16();
    }
    if (hasEnvelope) {
      short envPoints = stream.readUI8();
      envelopeRecords = new SoundEnvelope[envPoints];
      for (int i = 0; i < envPoints; i++) {
        envelopeRecords[i] = new SoundEnvelope(stream);
      }
    }
  }

  /**
   * Sets the envelope records (used for time-based volume control).
   *
   * @param envelopeRecords envelope records
   */
  public void setEnvelopeRecords(SoundEnvelope[] envelopeRecords) {
    this.envelopeRecords = envelopeRecords;
    if (envelopeRecords != null) {
      hasEnvelope = true;
    }
  }

  /**
   * Returns the envelope records (used for time-based volume control).
   *
   * @return envelope records
   */
  public SoundEnvelope[] getEnvelopeRecords() {
    return envelopeRecords;
  }

  /**
   * Sets the in-point of the sound, i.e. the number of samples to be skipped
   * at the beginning.
   *
   * @param inPoint in point
   */
  public void setInPoint(long inPoint) {
    hasInPoint     = true;
    this.inPoint   = inPoint;
  }

  /**
   * Returns the in-point of the sound, i.e. the number of samples to be
   * skipped at the beginning.
   *
   * @return in-point
   */
  public long getInPoint() {
    return inPoint;
  }

  /**
   * Sets the loop count, i.e. how many times the sound repeats.
   *
   * @param loopCount loop count
   */
  public void setLoopCount(int loopCount) {
    hasLoops         = true;
    this.loopCount   = loopCount;
  }

  /**
   * Returns the loop count, i.e. how many times the sound repeats.
   *
   * @return loop count
   */
  public int getLoopCount() {
    return loopCount;
  }

  /**
   * Sets the sound's out-point, i.e. the position in samples of the last
   * sample to be played.
   *
   * @param outPoint of sound
   */
  public void setOutPoint(long outPoint) {
    hasOutPoint     = true;
    this.outPoint   = outPoint;
  }

  /**
   * Returns the out-point, i.e. the position in samples of the last sample to
   * be played. Check with <code>hasOutPoint()</code> first if the information
   * is supplied.
   *
   * @return out-point of sound
   */
  public long getOutPoint() {
    return outPoint;
  }

  /**
   * Sets the syncNoMultiple flag. If set, a sound isn't started if already
   * playing.
   */
  public void setSyncNoMultiple() {
    syncNoMultiple = true;
  }

  /**
   * Checks the syncNoMultiple flag. If set, a sound isn't started if already
   * playing.
   *
   * @return <code>true</code> if flag set, else <code>false</code>
   */
  public boolean isSyncNoMultiple() {
    return syncNoMultiple;
  }

  /**
   * Sets the syncStop flag. If set, the sound is stoppped.
   */
  public void setSyncStop() {
    syncStop = true;
  }

  /**
   * Checks the syncStop flag. If set, the sound is stoppped.
   *
   * @return <code>true</code> if flag set, else <code>false</code>
   */
  public boolean isSyncStop() {
    return syncStop;
  }

  /**
   * Checks if envelope information is present.
   *
   * @return <code>true</code> if envelope information provided
   */
  public boolean hasEnvelope() {
    return hasEnvelope;
  }

  /**
   * Checks if in-point information (number of samples to skip at beginning) is
   * supplied.
   *
   * @return <code>true</code> if in-point information provided
   */
  public boolean hasInPoint() {
    return hasInPoint;
  }

  /**
   * Checks if loop information is contained.
   *
   * @return <code>true</code> if loop information provided
   */
  public boolean hasLoops() {
    return hasLoops;
  }

  /**
   * Checks if out-point information is present.
   *
   * @return <code>true</code> if out-point information provided
   */
  public boolean hasOutPoint() {
    return hasOutPoint;
  }

  /**
   * Writes the sound information to a bit stream.
   *
   * @param stream target bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream) throws IOException {
    hasEnvelope   = (envelopeRecords != null);
    hasLoops      = (loopCount != 0);
    hasOutPoint   = (outPoint != 0);
    hasInPoint    = (inPoint != 0);
    stream.writeUnsignedBits(0, 2); // 2 reserved bits
    stream.writeBooleanBit(syncStop);
    stream.writeBooleanBit(syncNoMultiple);
    stream.writeBooleanBit(hasEnvelope);
    stream.writeBooleanBit(hasLoops);
    stream.writeBooleanBit(hasOutPoint);
    stream.writeBooleanBit(hasInPoint);
    if (hasInPoint) {
      stream.writeUI32(inPoint);
    }
    if (hasOutPoint) {
      stream.writeUI32(outPoint);
    }
    if (hasLoops) {
      stream.writeUI16(loopCount);
    }
    if (hasEnvelope) {
      stream.writeUI8((short) envelopeRecords.length);
      for (int i = 0; i < envelopeRecords.length; i++) {
        envelopeRecords[i].write(stream);
      }
    }
  }
}
