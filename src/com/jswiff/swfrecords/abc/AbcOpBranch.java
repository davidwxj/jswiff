package com.jswiff.swfrecords.abc;

public class AbcOpBranch extends AbcOp {
  private int branchOffset;

  public AbcOpBranch(short opcode, int branchOffset) {
    setOpcode(opcode);
    this.branchOffset = branchOffset;
  }

  public int getBranchOffset() {
    return branchOffset;
  }

  public String toString() {
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
    return opName + " branchOffset = " + branchOffset;
  }
}
