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

import com.jswiff.constants.TagConstants.ActionType;


/**
 * <p>
 * This action allows casting from one data type to another.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop Type<br>
 * pop obj<br>
 * push [(Type)obj] </code> (object <code>obj</code> cast to type
 * <code>Type</code>)
 * </p>
 * 
 * <p>
 * Note: push <code>Type</code> this way to the stack:<br>
 * <code>push 'Type'<br>
 * GetVar </code>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: type cast (<code>Type(obj)</code>)
 * </p>
 *
 * @since SWF 6
 */
public final class CastOp extends Action {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new CastOp action.
   */
  public CastOp() {
    super(ActionType.CAST_OP);
  }

}
