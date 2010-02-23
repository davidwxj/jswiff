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

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * Use only with <code>DefineShape4</code>.
 */
public class FocalGradient extends Gradient {

  private static final long serialVersionUID = 1L;
  
  private double focalPointRatio;

  /**
   * Creates a new FocalGradient instance.
   *
   * @param gradientRecords TODO: Comments
   * @param focalPointRatio TODO: Comments
   */
  public FocalGradient(GradRecord[] gradientRecords, double focalPointRatio) {
    super(gradientRecords);
    this.focalPointRatio = focalPointRatio;
  }

  FocalGradient(InputBitStream stream) throws IOException {
    super(stream, true);
    focalPointRatio = stream.readFP16();
  }

  /**
   * TODO: Comments
   *
   * @param focalPointRatio TODO: Comments
   */
  public void setFocalPointRatio(double focalPointRatio) {
    this.focalPointRatio = focalPointRatio;
  }

  /**
   * TODO: Comments
   *
   * @return TODO: Comments
   */
  public double getFocalPointRatio() {
    return focalPointRatio;
  }

  void write(OutputBitStream stream) throws IOException {
    super.write(stream);
    stream.writeFP16(focalPointRatio);
  }
}
