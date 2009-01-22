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

import com.jswiff.constants.TagConstants.FilterType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * TODO: Comments
 */
public abstract class Filter implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  public static List<Filter> readFilters(InputBitStream stream) throws IOException, InvalidCodeException {
    int count = stream.readUI8();
    List<Filter> filters = new ArrayList<Filter>(count);
    for (int i = 0; i < count; i++) {
      FilterType filterType = FilterType.lookup(stream.readUI8());
      Filter filter;
      switch (filterType) {
      case BEVEL:
        filter = new BevelFilter(stream);
        break;
      case BLUR:
        filter = new BlurFilter(stream);
        break;
      case COLOR_MATRIX:
        filter = new ColorMatrixFilter(stream);
        break;
      case CONVOLUTION:
        filter = new ConvolutionFilter(stream);
        break;
      case DROP_SHADOW:
        filter = new DropShadowFilter(stream);
        break;
      case GLOW:
        filter = new GlowFilter(stream);
        break;
      case GRADIENT_BEVEL:
        filter = new GradientBevelFilter(stream);
        break;
      case GRADIENT_GLOW:
        filter = new GradientGlowFilter(stream);
        break;
      default:
        throw new AssertionError("Unhandled filter type: " + filterType);
      }
      filters.add(filter);
    }
    return filters;
  }

  
  public static void writeFilters(List<Filter> filters, OutputBitStream stream) throws IOException {
    stream.writeUI8((short) filters.size());
    for (Filter filter : filters) {
      stream.writeUI8(filter.type.getCode());
      filter.write(stream);
    }
  }
  
  public final FilterType type;
  
  public abstract void write(OutputBitStream stream) throws IOException;
  
  public Filter(FilterType type) {
    this.type = type;
  }  
  
}
