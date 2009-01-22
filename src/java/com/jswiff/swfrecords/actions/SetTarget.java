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

package com.jswiff.swfrecords.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * <p>
 * Instructs Flash Player to change the context of subsequent actions, so they
 * apply to an object with the specified name. This action can be used e.g. to
 * control the timeline of a sprite object.
 * </p>
 * 
 * <p>
 * Note: as of SWF 5, this action is deprecated. Use <code>With</code> instead.
 * </p>
 * 
 * <p>
 * Performed stack operations: none
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>tellTarget()</code>
 * </p>
 *
 * @since SWF 3
 */
public final class SetTarget extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private String name;

  /**
   * Creates a new SetTarget action. The target object's name is passed as a
   * string.
   *
   * @param name target object name
   */
  public SetTarget(String name) {
    super(ActionType.SET_TARGET);
    this.name   = name;
  }

  /*
   * Reads a SetTarget action from a bit stream.
   */
  SetTarget(InputBitStream stream) throws IOException {
    super(ActionType.SET_TARGET);
    name   = stream.readString();
  }

  /**
   * Returns the name of the target object.
   *
   * @return target object's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 4;
    try {
      size += name.getBytes("UTF-8").length;
    } catch (UnsupportedEncodingException e) {
      // UTF-8 should be available
    }
    return size;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"SetTarget"</code>
   */
  public String toString() {
    return super.toString() + " " + name;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeString(name);
  }
}
