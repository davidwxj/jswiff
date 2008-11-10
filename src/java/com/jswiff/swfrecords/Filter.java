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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * TODO: Comments
 */
public abstract class Filter implements Serializable {
  /** TODO: Comments */
  public static final int DROP_SHADOW    = 0;
  /** TODO: Comments */
  public static final int BLUR           = 1;
  /** TODO: Comments */
  public static final int GLOW           = 2;
  /** TODO: Comments */
  public static final int BEVEL          = 3;
  /** TODO: Comments */
  public static final int GRADIENT_GLOW  = 4;
  /** TODO: Comments */
  public static final int CONVOLUTION    = 5;
  /** TODO: Comments */
  public static final int COLOR_MATRIX   = 6;
  /** TODO: Comments */
  public static final int GRADIENT_BEVEL = 7;

  /**
   * TODO: Comments
   *
   * @param stream TODO: Comments
   *
   * @return TODO: Comments
   *
   * @throws IOException TODO: Comments
   */
  public static List<Filter> readFilters(InputBitStream stream)
    throws IOException {
    int count    = stream.readUI8();
    List<Filter> filters = new ArrayList<Filter>(count);
    for (int i = 0; i < count; i++) {
      int filterType = stream.readUI8();
      Filter filter;
      switch (filterType) {
        case Filter.BEVEL:
          filter = new BevelFilter(stream);
          break;
        case Filter.BLUR:
          filter = new BlurFilter(stream);
          break;
        case Filter.COLOR_MATRIX:
          filter = new ColorMatrixFilter(stream);
          break;
        case Filter.CONVOLUTION:
          filter = new ConvolutionFilter(stream);
          break;
        case Filter.DROP_SHADOW:
          filter = new DropShadowFilter(stream);
          break;
        case Filter.GLOW:
          filter = new GlowFilter(stream);
          break;
        case Filter.GRADIENT_BEVEL:
          filter = new GradientBevelFilter(stream);
          break;
        case Filter.GRADIENT_GLOW:
          filter = new GradientGlowFilter(stream);
          break;
        default:
          throw new IOException("Unknown filter type: " + filterType);
      }
      filters.add(filter);
    }
    return filters;
  }

  /**
   * TODO: Comments
   *
   * @param filters TODO: Comments
   * @param stream TODO: Comments
   *
   * @throws IOException TODO: Comments
   * @throws IllegalArgumentException TODO: Comments
   */
  public static void writeFilters(List<Filter> filters, OutputBitStream stream)
    throws IOException {
    int count = filters.size();
    stream.writeUI8((short) count);
    for (int i = 0; i < count; i++) {
      Object filter = filters.get(i);
      if (filter instanceof BevelFilter) {
        stream.writeUI8((short) BEVEL);
        ((BevelFilter) filter).write(stream);
      } else if (filter instanceof BlurFilter) {
        stream.writeUI8((short) BLUR);
        ((BlurFilter) filter).write(stream);
      } else if (filter instanceof ColorMatrixFilter) {
        stream.writeUI8((short) COLOR_MATRIX);
        ((ColorMatrixFilter) filter).write(stream);
      } else if (filter instanceof ConvolutionFilter) {
        stream.writeUI8((short) CONVOLUTION);
        ((ConvolutionFilter) filter).write(stream);
      } else if (filter instanceof DropShadowFilter) {
        stream.writeUI8((short) DROP_SHADOW);
        ((DropShadowFilter) filter).write(stream);
      } else if (filter instanceof GlowFilter) {
        stream.writeUI8((short) GLOW);
        ((GlowFilter) filter).write(stream);
      } else if (filter instanceof GradientBevelFilter) {
        stream.writeUI8((short) GRADIENT_BEVEL);
        ((GradientBevelFilter) filter).write(stream);
      } else if (filter instanceof GradientGlowFilter) {
        stream.writeUI8((short) GRADIENT_GLOW);
        ((GradientGlowFilter) filter).write(stream);
      } else {
        throw new IllegalArgumentException(
          "Filter list contains filter of unknown type: " + filter.getClass());
      }
    }
  }
}
