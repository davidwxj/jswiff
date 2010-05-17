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

import com.jswiff.constants.TagConstants.FilterType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO: Comments
 */
public abstract class Filter implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  public static List<Filter> readFilters(InputBitStream stream) throws IOException {
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
