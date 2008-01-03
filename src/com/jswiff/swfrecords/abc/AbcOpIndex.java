package com.jswiff.swfrecords.abc;

public class AbcOpIndex extends AbcOp {
  private int index;

  public AbcOpIndex(short opcode, int index) {
    setOpcode(opcode);
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public String toString() {
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
    return opName + " index = " + index;
  }
}
