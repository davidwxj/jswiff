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

/**
 * TODO: Comments
 */
public class BlendMode {
  /** TODO: Comments */
  public static final short NORMAL     = 1;
  /** TODO: Comments */
  public static final short LAYER      = 2;
  /** TODO: Comments */
  public static final short MULTIPLY   = 3;
  /** TODO: Comments */
  public static final short SCREEN     = 4;
  /** TODO: Comments */
  public static final short LIGHTEN    = 5;
  /** TODO: Comments */
  public static final short DARKEN     = 6;
  /** TODO: Comments */
  public static final short DIFFERENCE = 7;
  /** TODO: Comments */
  public static final short ADD        = 8;
  /** TODO: Comments */
  public static final short SUBTRACT   = 9;
  /** TODO: Comments */
  public static final short INVERT     = 10;
  /** TODO: Comments */
  public static final short ALPHA      = 11;
  /** TODO: Comments */
  public static final short ERASE      = 12;
  /** TODO: Comments */
  public static final short OVERLAY    = 13;
  /** TODO: Comments */
  public static final short HARD_LIGHT = 14;

  /**
   * TODO: Comments
   *
   * @param blendMode TODO: Comments
   *
   * @return TODO: Comments
   */
  public static String getDescription(short blendMode) {
    switch (blendMode) {
      case 0:
      case NORMAL:
        return "normal";
      case LAYER:
        return "layer";
      case MULTIPLY:
        return "multiply";
      case SCREEN:
        return "screen";
      case LIGHTEN:
        return "lighten";
      case DARKEN:
        return "darken";
      case DIFFERENCE:
        return "difference";
      case ADD:
        return "add";
      case SUBTRACT:
        return "subtract";
      case INVERT:
        return "invert";
      case ALPHA:
        return "alpha";
      case ERASE:
        return "erase";
      case OVERLAY:
        return "overlay";
      case HARD_LIGHT:
        return "hard light";
      default:
        return "unknown value: " + blendMode;
    }
  }

  /**
   * TODO: Comments
   *
   * @param description TODO: Comments
   *
   * @return TODO: Comments
   */
  public static short getFromDescription(String description) {
    if (description.equals("normal")) {
      return NORMAL;
    } else if (description.equals("layer")) {
      return LAYER;
    } else if (description.equals("multiply")) {
      return MULTIPLY;
    } else if (description.equals("screen")) {
      return SCREEN;
    } else if (description.equals("lighten")) {
      return LIGHTEN;
    } else if (description.equals("darken")) {
      return DARKEN;
    } else if (description.equals("difference")) {
      return DIFFERENCE;
    } else if (description.equals("add")) {
      return ADD;
    } else if (description.equals("subtract")) {
      return SUBTRACT;
    } else if (description.equals("invert")) {
      return INVERT;
    } else if (description.equals("alpha")) {
      return ALPHA;
    } else if (description.equals("erase")) {
      return ERASE;
    } else if (description.equals("overlay")) {
      return OVERLAY;
    } else if (description.equals("hard light")) {
      return HARD_LIGHT;
    } else {
      return -1;
    }
  }
}
