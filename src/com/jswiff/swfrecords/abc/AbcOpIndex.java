package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpIndex extends AbcOp {
  private int index;

  public AbcOpIndex(short opcode, int index) {
    super(opcode);
    checkOpcode(opcode);
    this.index = index;
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_getsuper:
      case AbcConstants.Opcodes.OPCODE_setsuper:
      case AbcConstants.Opcodes.OPCODE_getproperty:
      case AbcConstants.Opcodes.OPCODE_initproperty:
      case AbcConstants.Opcodes.OPCODE_setproperty:
      case AbcConstants.Opcodes.OPCODE_getlex:
      case AbcConstants.Opcodes.OPCODE_findpropstrict: 
      case AbcConstants.Opcodes.OPCODE_findproperty:
      case AbcConstants.Opcodes.OPCODE_finddef:
      case AbcConstants.Opcodes.OPCODE_deleteproperty: 
      case AbcConstants.Opcodes.OPCODE_istype:
      case AbcConstants.Opcodes.OPCODE_coerce:
      case AbcConstants.Opcodes.OPCODE_astype:
      case AbcConstants.Opcodes.OPCODE_getdescendants:
      case AbcConstants.Opcodes.OPCODE_debugfile:
      case AbcConstants.Opcodes.OPCODE_pushdouble:
      case AbcConstants.Opcodes.OPCODE_pushint:
      case AbcConstants.Opcodes.OPCODE_pushnamespace:
      case AbcConstants.Opcodes.OPCODE_pushstring:
      case AbcConstants.Opcodes.OPCODE_pushuint:
      case AbcConstants.Opcodes.OPCODE_newfunction:
      case AbcConstants.Opcodes.OPCODE_newclass:
      case AbcConstants.Opcodes.OPCODE_inclocal:
      case AbcConstants.Opcodes.OPCODE_declocal:
      case AbcConstants.Opcodes.OPCODE_inclocal_i:
      case AbcConstants.Opcodes.OPCODE_declocal_i:
      case AbcConstants.Opcodes.OPCODE_getlocal:
      case AbcConstants.Opcodes.OPCODE_kill:
      case AbcConstants.Opcodes.OPCODE_setlocal:
      case AbcConstants.Opcodes.OPCODE_getglobalslot:
      case AbcConstants.Opcodes.OPCODE_getslot:
      case AbcConstants.Opcodes.OPCODE_setglobalslot:
      case AbcConstants.Opcodes.OPCODE_setslot:
      case AbcConstants.Opcodes.OPCODE_newcatch:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  public AbcOpIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public String toString() {
    return getOpName() + " index = " + index;
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_getsuper:
        opName = "getsuper";
        break;
      case AbcConstants.Opcodes.OPCODE_setsuper:
        opName = "setsuper";
        break;
      case AbcConstants.Opcodes.OPCODE_getproperty:
        opName = "getproperty";
        break;
      case AbcConstants.Opcodes.OPCODE_initproperty:
        opName = "initproperty";
        break;
      case AbcConstants.Opcodes.OPCODE_setproperty:
        opName = "setproperty";
        break;
      case AbcConstants.Opcodes.OPCODE_getlex:
        opName = "getlex";
        break;
      case AbcConstants.Opcodes.OPCODE_findpropstrict:
        opName = "findpropstrict";
        break;
      case AbcConstants.Opcodes.OPCODE_findproperty:
        opName = "findproperty";
        break;
      case AbcConstants.Opcodes.OPCODE_finddef:
        opName = "finddef";
        break;
      case AbcConstants.Opcodes.OPCODE_deleteproperty:
        opName = "deleteproperty";
        break;
      case AbcConstants.Opcodes.OPCODE_istype:
        opName = "istype";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce:
        opName = "coerce";
        break;
      case AbcConstants.Opcodes.OPCODE_astype:
        opName = "astype";
        break;
      case AbcConstants.Opcodes.OPCODE_getdescendants:
        opName = "getdescendants";
        break;
      case AbcConstants.Opcodes.OPCODE_debugfile:
        opName = "debugfile";
        break;
      case AbcConstants.Opcodes.OPCODE_pushdouble:
        opName = "pushdouble";
        break;
      case AbcConstants.Opcodes.OPCODE_pushint:
        opName = "pushint";
        break;
      case AbcConstants.Opcodes.OPCODE_pushnamespace:
        opName = "pushnamespace";
        break;
      case AbcConstants.Opcodes.OPCODE_pushstring:
        opName = "pushstring";
        break;
      case AbcConstants.Opcodes.OPCODE_pushuint:
        opName = "pushuint";
        break;
      case AbcConstants.Opcodes.OPCODE_newfunction:
        opName = "newfunction";
        break;
      case AbcConstants.Opcodes.OPCODE_newclass:
        opName = "newclass";
        break;
      case AbcConstants.Opcodes.OPCODE_inclocal:
        opName = "inclocal";
        break;
      case AbcConstants.Opcodes.OPCODE_declocal:
        opName = "declocal";
        break;
      case AbcConstants.Opcodes.OPCODE_inclocal_i:
        opName = "inclocal_i";
        break;
      case AbcConstants.Opcodes.OPCODE_declocal_i:
        opName = "declocal_i";
        break;
      case AbcConstants.Opcodes.OPCODE_getlocal:
        opName = "getlocal";
        break;
      case AbcConstants.Opcodes.OPCODE_kill:
        opName = "kill";
        break;
      case AbcConstants.Opcodes.OPCODE_setlocal:
        opName = "setlocal";
        break;
      case AbcConstants.Opcodes.OPCODE_getglobalslot:
        opName = "getglobalslot";
        break;
      case AbcConstants.Opcodes.OPCODE_getslot:
        opName = "getslot";
        break;
      case AbcConstants.Opcodes.OPCODE_setglobalslot:
        opName = "setglobalslot";
        break;
      case AbcConstants.Opcodes.OPCODE_setslot:
        opName = "setslot";
        break;
      case AbcConstants.Opcodes.OPCODE_newcatch:
        opName = "newcatch";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
    stream.writeAbcInt(index);
  }
}
