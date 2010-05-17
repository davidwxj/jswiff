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

package com.jswiff.swfrecords.actions;

/**
 * This class contains constants used as movie property indexes by
 * <code>GetProperty</code> and <code>SetProperty</code>.
 */
public final class MovieProperties {
  /** x coordinate */
  public static final int X              = 0;
  /** y coordinate */
  public static final int Y              = 1;
  /** Horizontal scale in percent */
  public static final int X_SCALE        = 2;
  /** Vertical scale in percent */
  public static final int Y_SCALE        = 3;
  /** Frame number in which the playhead is located */
  public static final int CURRENT_FRAME  = 4;
  /** The total number of frames */
  public static final int TOTAL_FRAMES   = 5;
  /** Transparency value */
  public static final int ALPHA          = 6;
  /** Indicates whether visible or not */
  public static final int VISIBLE        = 7;
  /** Width in pixels */
  public static final int WIDTH          = 8;
  /** Height in pixels */
  public static final int HEIGHT         = 9;
  /** Rotation in degrees */
  public static final int ROTATION       = 10;
  /** Target path */
  public static final int TARGET         = 11;
  /** Number of frames loaded from a streaming movie */
  public static final int FRAMES_LOADED  = 12;
  /** Instance name */
  public static final int NAME           = 13;
  /** Absolute path in slash syntax notation */
  public static final int DROP_TARGET    = 14;
  /** URL of the SWF file */
  public static final int URL            = 15;
  /** Level of anti-aliasing - superseded by <code>QUALITY</code> as of SWF 5 */
  public static final int HIGH_QUALITY   = 16;
  /**
   * Specifies whether a yellow rectangle appears around the movie when having
   * keyboard focus
   */
  public static final int FOCUS_RECT     = 17;
  /** Seconds of streaming sound to prebuffer */
  public static final int SOUND_BUF_TIME = 18;
  /** Stores a string that dictates the rendering quality of the Flash Player */
  public static final int QUALITY        = 19;
  /** x coordinate of the mouse position */
  public static final int X_MOUSE        = 20;
  /** y coordinate of the mouse position */
  public static final int Y_MOUSE        = 21;

  private MovieProperties() {
    // no need to instantiate
  }
}
