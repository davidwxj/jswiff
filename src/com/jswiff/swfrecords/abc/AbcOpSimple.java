package com.jswiff.swfrecords.abc;

public class AbcOpSimple extends AbcOp {
  public AbcOpSimple(short opcode) {
    setOpcode(opcode);
  }

  public String toString() {
    return getOpName();
  }

  public String getOpName() {
    String opName;
    switch (getOpcode()) {
      case AbcConstants.Opcodes.OPCODE_bkpt:
        opName = "bkpt";
        break;
      case AbcConstants.Opcodes.OPCODE_nop:
        opName = "nop";
        break;
      case AbcConstants.Opcodes.OPCODE_throw:
        opName = "throw";
        break;
      case AbcConstants.Opcodes.OPCODE_dxns:
        opName = "dxns";
        break;
      case AbcConstants.Opcodes.OPCODE_dxnslate:
        opName = "dxnslate";
        break;
      case AbcConstants.Opcodes.OPCODE_label:
        opName = "label";
        break;
      case AbcConstants.Opcodes.OPCODE_pushwith:
        opName = "pushwith";
        break;
      case AbcConstants.Opcodes.OPCODE_popscope:
        opName = "popscope";
        break;
      case AbcConstants.Opcodes.OPCODE_nextname:
        opName = "nextname";
        break;
      case AbcConstants.Opcodes.OPCODE_hasnext:
        opName = "hasnext";
        break;
      case AbcConstants.Opcodes.OPCODE_pushnull:
        opName = "pushnull";
        break;
      case AbcConstants.Opcodes.OPCODE_pushundefined:
        opName = "pushundefined";
        break;
      case AbcConstants.Opcodes.OPCODE_pushconstant:
        opName = "pushconstant";
        break;
      case AbcConstants.Opcodes.OPCODE_nextvalue:
        opName = "nextvalue";
        break;
      case AbcConstants.Opcodes.OPCODE_pushtrue:
        opName = "pushtrue";
        break;
      case AbcConstants.Opcodes.OPCODE_pushfalse:
        opName = "pushfalse";
        break;
      case AbcConstants.Opcodes.OPCODE_pushnan:
        opName = "pushnan";
        break;
      case AbcConstants.Opcodes.OPCODE_pop:
        opName = "pop";
        break;
      case AbcConstants.Opcodes.OPCODE_dup:
        opName = "dup";
        break;
      case AbcConstants.Opcodes.OPCODE_swap:
        opName = "swap";
        break;
      case AbcConstants.Opcodes.OPCODE_pushscope:
        opName = "pushscope";
        break;
      case AbcConstants.Opcodes.OPCODE_callmethod:
        opName = "callmethod";
        break;
      case AbcConstants.Opcodes.OPCODE_returnvoid:
        opName = "returnvoid";
        break;
      case AbcConstants.Opcodes.OPCODE_returnvalue:
        opName = "returnvalue";
        break;
      case AbcConstants.Opcodes.OPCODE_callsuperid:
        opName = "callsuperid";
        break;
      case AbcConstants.Opcodes.OPCODE_callinterface:
        opName = "callinterface";
        break;
      case AbcConstants.Opcodes.OPCODE_newactivation:
        opName = "newactivation";
        break;
      case AbcConstants.Opcodes.OPCODE_getglobalscope:
        opName = "getglobalscope";
        break;
      case AbcConstants.Opcodes.OPCODE_getpropertylate:
        opName = "getpropertylate";
        break;
      case AbcConstants.Opcodes.OPCODE_setpropertylate:
        opName = "setpropertylate";
        break;
      case AbcConstants.Opcodes.OPCODE_deletepropertylate:
        opName = "deletepropertylate";
        break;
      case AbcConstants.Opcodes.OPCODE_convert_s:
        opName = "convert_s";
        break;
      case AbcConstants.Opcodes.OPCODE_esc_xelem:
        opName = "esc_xelem";
        break;
      case AbcConstants.Opcodes.OPCODE_esc_xattr:
        opName = "esc_xattr";
        break;
      case AbcConstants.Opcodes.OPCODE_convert_i:
        opName = "convert_i";
        break;
      case AbcConstants.Opcodes.OPCODE_convert_u:
        opName = "convert_u";
        break;
      case AbcConstants.Opcodes.OPCODE_convert_d:
        opName = "convert_d";
        break;
      case AbcConstants.Opcodes.OPCODE_convert_b:
        opName = "convert_b";
        break;
      case AbcConstants.Opcodes.OPCODE_convert_o:
        opName = "convert_o";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_b:
        opName = "coerce_b";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_a:
        opName = "coerce_a";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_i:
        opName = "coerce_i";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_d:
        opName = "coerce_d";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_s:
        opName = "coerce_s";
        break;
      case AbcConstants.Opcodes.OPCODE_astypelate:
        opName = "astypelate";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_u:
        opName = "coerce_u";
        break;
      case AbcConstants.Opcodes.OPCODE_coerce_o:
        opName = "coerce_o";
        break;
      case AbcConstants.Opcodes.OPCODE_negate:
        opName = "negate";
        break;
      case AbcConstants.Opcodes.OPCODE_increment:
        opName = "increment";
        break;
      case AbcConstants.Opcodes.OPCODE_decrement:
        opName = "decrement";
        break;
      case AbcConstants.Opcodes.OPCODE_typeof:
        opName = "typeof";
        break;
      case AbcConstants.Opcodes.OPCODE_not:
        opName = "not";
        break;
      case AbcConstants.Opcodes.OPCODE_bitnot:
        opName = "bitnot";
        break;
      case AbcConstants.Opcodes.OPCODE_concat:
        opName = "concat";
        break;
      case AbcConstants.Opcodes.OPCODE_add_d:
        opName = "add_d";
        break;
      case AbcConstants.Opcodes.OPCODE_add:
        opName = "add";
        break;
      case AbcConstants.Opcodes.OPCODE_subtract:
        opName = "subtract";
        break;
      case AbcConstants.Opcodes.OPCODE_multiply:
        opName = "multiply";
        break;
      case AbcConstants.Opcodes.OPCODE_divide:
        opName = "divide";
        break;
      case AbcConstants.Opcodes.OPCODE_modulo:
        opName = "modulo";
        break;
      case AbcConstants.Opcodes.OPCODE_lshift:
        opName = "lshift";
        break;
      case AbcConstants.Opcodes.OPCODE_rshift:
        opName = "rshift";
        break;
      case AbcConstants.Opcodes.OPCODE_urshift:
        opName = "urshift";
        break;
      case AbcConstants.Opcodes.OPCODE_bitand:
        opName = "bitand";
        break;
      case AbcConstants.Opcodes.OPCODE_bitor:
        opName = "bitor";
        break;
      case AbcConstants.Opcodes.OPCODE_bitxor:
        opName = "bitxor";
        break;
      case AbcConstants.Opcodes.OPCODE_equals:
        opName = "equals";
        break;
      case AbcConstants.Opcodes.OPCODE_strictequals:
        opName = "strictequals";
        break;
      case AbcConstants.Opcodes.OPCODE_lessthan:
        opName = "lessthan";
        break;
      case AbcConstants.Opcodes.OPCODE_lessequals:
        opName = "lessequals";
        break;
      case AbcConstants.Opcodes.OPCODE_greaterthan:
        opName = "greaterthan";
        break;
      case AbcConstants.Opcodes.OPCODE_greaterequals:
        opName = "greaterequals";
        break;
      case AbcConstants.Opcodes.OPCODE_instanceof:
        opName = "instanceof";
        break;
      case AbcConstants.Opcodes.OPCODE_istypelate:
        opName = "istypelate";
        break;
      case AbcConstants.Opcodes.OPCODE_in:
        opName = "in";
        break;
      case AbcConstants.Opcodes.OPCODE_increment_i:
        opName = "increment_i";
        break;
      case AbcConstants.Opcodes.OPCODE_decrement_i:
        opName = "decrement_i";
        break;
      case AbcConstants.Opcodes.OPCODE_negate_i:
        opName = "negate_i";
        break;
      case AbcConstants.Opcodes.OPCODE_add_i:
        opName = "add_i";
        break;
      case AbcConstants.Opcodes.OPCODE_subtract_i:
        opName = "subtract_i";
        break;
      case AbcConstants.Opcodes.OPCODE_multiply_i:
        opName = "multiply_i";
        break;
      case AbcConstants.Opcodes.OPCODE_getlocal0:
        opName = "getlocal0";
        break;
      case AbcConstants.Opcodes.OPCODE_getlocal1:
        opName = "getlocal1";
        break;
      case AbcConstants.Opcodes.OPCODE_getlocal2:
        opName = "getlocal2";
        break;
      case AbcConstants.Opcodes.OPCODE_getlocal3:
        opName = "getlocal3";
        break;
      case AbcConstants.Opcodes.OPCODE_setlocal0:
        opName = "setlocal0";
        break;
      case AbcConstants.Opcodes.OPCODE_setlocal1:
        opName = "setlocal1";
        break;
      case AbcConstants.Opcodes.OPCODE_setlocal2:
        opName = "setlocal2";
        break;
      case AbcConstants.Opcodes.OPCODE_setlocal3:
        opName = "setlocal3";
        break;
      case AbcConstants.Opcodes.OPCODE_bkptline:
        opName = "bkptline";
        break;
      case AbcConstants.Opcodes.OPCODE_checkfilter:
        opName = "checkfilter";
        break;
      case AbcConstants.Opcodes.OPCODE_timestamp:
        opName = "timestamp";
        break;
      default:
        opName = "unknown";
    }
    return opName;
  }
}
