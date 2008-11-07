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

package com.jswiff.swfrecords;

import java.io.Serializable;

/**
 * Base class for the new enhanced stroke line styles introduced in SWF 8.
 */
public abstract class EnhancedStrokeStyle implements Serializable {
  /** TODO: Comments */
  public static final byte SCALE_NONE       = 0;
  /** TODO: Comments */
  public static final byte SCALE_VERTICAL   = 1;
  /** TODO: Comments */
  public static final byte SCALE_HORIZONTAL = 2;
  /** TODO: Comments */
  public static final byte SCALE_BOTH       = 3;
  /** TODO: Comments */
  public static final byte CAPS_ROUND       = 0;
  /** TODO: Comments */
  public static final byte CAPS_NONE        = 1;
  /** TODO: Comments */
  public static final byte CAPS_SQUARE      = 2;
  /** TODO: Comments */
  public static final byte JOINT_ROUND      = 0;
  /** TODO: Comments */
  public static final byte JOINT_BEVEL      = 1;
  /** TODO: Comments */
  public static final byte JOINT_MITER      = 2;
}
