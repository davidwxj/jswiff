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

import com.jswiff.constants.TagConstants.ActionType;


/**
 * <p>
 * Duplicates a sprite, creating a new sprite instance at a given depth.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code> pop depth </code>(depth at which the clone sprite will be created)<br>
 * <code> pop name</code> (instance name of the clone)<br>
 * <code> pop sprite</code> (sprite to be cloned)<br>
 * </p>
 * 
 * <p>
 * Note: use values between 16384 and 1064959 for <code>depth</code>, as this
 * range is reserved for dynamic use (otherwise - among other problems - you
 * won't be able to remove the created sprite).
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>duplicateMovieClip()</code>. The Macromedia
 * Flash compiler internally adds 16384 for convenience to the depth passed as
 * parameter.
 * </p>
 *
 * @since SWF 4
 */
public final class CloneSprite extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new CloneSprite action.
   */
  public CloneSprite() {
    super(ActionType.CLONE_SPRITE);
  }

}
