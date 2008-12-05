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

package com.jswiff.swfrecords.abc.opcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.constants.AbcConstants.OpCode;
import com.jswiff.constants.AbcConstants.OpCodeType;
import com.jswiff.io.OutputBitStream;

public class AbcOpLookupSwitch extends AbcOp {

  private static final long serialVersionUID = 1L;
  
  private int defaultOffset;
  private List<Integer> caseOffsets = new ArrayList<Integer>();

  public AbcOpLookupSwitch(int defaultOffset) {
    super(OpCode.LOOKUPSWITCH, OpCodeType.LOOKUP_SWITCH);
    this.defaultOffset = defaultOffset;
  }

  public int getDefaultOffset() {
    return defaultOffset;
  }

  public List<Integer> getCaseOffsets() {
    return caseOffsets;
  }
  
  public void addCaseOffset(int caseOffset) {
    caseOffsets.add(caseOffset);
  }

  public String toString() {
    String result = getOpcode().toString() + ": defaultOffset = " + defaultOffset + ", caseOffsets = [ ";
    for (int offset : caseOffsets) {
      result = result.concat( offset + " " );
    }
    result += "]";
    return result;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(getOpcode().getCode());
    stream.writeSI24(defaultOffset);
    stream.writeAbcInt(caseOffsets.size() - 1);
    for (Iterator<Integer> it = caseOffsets.iterator(); it.hasNext(); ) {
      stream.writeSI24(it.next());
    }
  }
   
}