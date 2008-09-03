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

package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpBranch extends AbcOp {
  private int branchOffset;

  public AbcOpBranch(short opcode, int branchOffset) {
    super(opcode);
    checkOpcode(opcode);
    this.branchOffset = branchOffset;
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_jump:
      case AbcConstants.Opcodes.OPCODE_iftrue:
      case AbcConstants.Opcodes.OPCODE_iffalse:
      case AbcConstants.Opcodes.OPCODE_ifeq:
      case AbcConstants.Opcodes.OPCODE_ifne:
      case AbcConstants.Opcodes.OPCODE_ifge:
      case AbcConstants.Opcodes.OPCODE_ifnge:
      case AbcConstants.Opcodes.OPCODE_ifgt:
      case AbcConstants.Opcodes.OPCODE_ifngt:
      case AbcConstants.Opcodes.OPCODE_ifle:
      case AbcConstants.Opcodes.OPCODE_ifnle:
      case AbcConstants.Opcodes.OPCODE_iflt:
      case AbcConstants.Opcodes.OPCODE_ifnlt:
      case AbcConstants.Opcodes.OPCODE_ifstricteq:
      case AbcConstants.Opcodes.OPCODE_ifstrictne:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  AbcOpBranch(int branchOffset) {
    this.branchOffset = branchOffset;
  }

  public int getBranchOffset() {
    return branchOffset;
  }

  public String toString() {
    return getOpName() + " branchOffset = " + branchOffset;
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_jump:
        opName = "jump";
        break;
      case AbcConstants.Opcodes.OPCODE_iftrue:
        opName = "iftrue";
        break;
      case AbcConstants.Opcodes.OPCODE_iffalse:
        opName = "iffalse";
        break;
      case AbcConstants.Opcodes.OPCODE_ifeq:
        opName = "ifeq";
        break;
      case AbcConstants.Opcodes.OPCODE_ifne:
        opName = "ifne";
        break;
      case AbcConstants.Opcodes.OPCODE_ifge:
        opName = "ifge";
        break;
      case AbcConstants.Opcodes.OPCODE_ifnge:
        opName = "ifnge";
        break;
      case AbcConstants.Opcodes.OPCODE_ifgt:
        opName = "ifgt";
        break;
      case AbcConstants.Opcodes.OPCODE_ifngt:
        opName = "ifngt";
        break;
      case AbcConstants.Opcodes.OPCODE_ifle:
        opName = "ifle";
        break;
      case AbcConstants.Opcodes.OPCODE_ifnle:
        opName = "ifnle";
        break;
      case AbcConstants.Opcodes.OPCODE_iflt:
        opName = "iflt";
        break;
      case AbcConstants.Opcodes.OPCODE_ifnlt:
        opName = "ifnlt";
        break;
      case AbcConstants.Opcodes.OPCODE_ifstricteq:
        opName = "ifstricteq";
        break;
      case AbcConstants.Opcodes.OPCODE_ifstrictne:
        opName = "ifstrictne";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeSI24(branchOffset);
  }
}
