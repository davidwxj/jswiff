package com.jswiff.swfrecords.abc;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class AbcOpSimple extends AbcOp {
  public AbcOpSimple(short opcode) {
    super(opcode);
    checkOpcode(opcode);
  }

  private void checkOpcode(short opcode) {
    switch(opcode) {
      case AbcConstants.Opcodes.OPCODE_bkpt:
      case AbcConstants.Opcodes.OPCODE_nop:
      case AbcConstants.Opcodes.OPCODE_throw:
      case AbcConstants.Opcodes.OPCODE_dxns:
      case AbcConstants.Opcodes.OPCODE_dxnslate:
      case AbcConstants.Opcodes.OPCODE_label:
      case AbcConstants.Opcodes.OPCODE_pushwith:
      case AbcConstants.Opcodes.OPCODE_popscope:
      case AbcConstants.Opcodes.OPCODE_nextname:
      case AbcConstants.Opcodes.OPCODE_hasnext:
      case AbcConstants.Opcodes.OPCODE_pushnull:
      case AbcConstants.Opcodes.OPCODE_pushundefined:
      case AbcConstants.Opcodes.OPCODE_pushconstant:
      case AbcConstants.Opcodes.OPCODE_nextvalue:
      case AbcConstants.Opcodes.OPCODE_pushtrue:
      case AbcConstants.Opcodes.OPCODE_pushfalse:
      case AbcConstants.Opcodes.OPCODE_pushnan:
      case AbcConstants.Opcodes.OPCODE_pop:
      case AbcConstants.Opcodes.OPCODE_dup:
      case AbcConstants.Opcodes.OPCODE_swap:
      case AbcConstants.Opcodes.OPCODE_pushscope:
      case AbcConstants.Opcodes.OPCODE_callmethod:
      case AbcConstants.Opcodes.OPCODE_returnvoid:
      case AbcConstants.Opcodes.OPCODE_returnvalue:
      case AbcConstants.Opcodes.OPCODE_callsuperid:
      case AbcConstants.Opcodes.OPCODE_callinterface:
      case AbcConstants.Opcodes.OPCODE_newactivation:
      case AbcConstants.Opcodes.OPCODE_getglobalscope:
      case AbcConstants.Opcodes.OPCODE_getpropertylate:
      case AbcConstants.Opcodes.OPCODE_setpropertylate:
      case AbcConstants.Opcodes.OPCODE_deletepropertylate:
      case AbcConstants.Opcodes.OPCODE_convert_s:
      case AbcConstants.Opcodes.OPCODE_esc_xelem:
      case AbcConstants.Opcodes.OPCODE_esc_xattr:
      case AbcConstants.Opcodes.OPCODE_convert_i:
      case AbcConstants.Opcodes.OPCODE_convert_u:
      case AbcConstants.Opcodes.OPCODE_convert_d:
      case AbcConstants.Opcodes.OPCODE_convert_b:
      case AbcConstants.Opcodes.OPCODE_convert_o:
      case AbcConstants.Opcodes.OPCODE_coerce_b:
      case AbcConstants.Opcodes.OPCODE_coerce_a:
      case AbcConstants.Opcodes.OPCODE_coerce_i:
      case AbcConstants.Opcodes.OPCODE_coerce_d:
      case AbcConstants.Opcodes.OPCODE_coerce_s:
      case AbcConstants.Opcodes.OPCODE_astypelate:
      case AbcConstants.Opcodes.OPCODE_coerce_u:
      case AbcConstants.Opcodes.OPCODE_coerce_o:
      case AbcConstants.Opcodes.OPCODE_negate:
      case AbcConstants.Opcodes.OPCODE_increment:
      case AbcConstants.Opcodes.OPCODE_decrement:
      case AbcConstants.Opcodes.OPCODE_typeof:
      case AbcConstants.Opcodes.OPCODE_not:
      case AbcConstants.Opcodes.OPCODE_bitnot:
      case AbcConstants.Opcodes.OPCODE_concat:
      case AbcConstants.Opcodes.OPCODE_add_d:
      case AbcConstants.Opcodes.OPCODE_add:
      case AbcConstants.Opcodes.OPCODE_subtract:
      case AbcConstants.Opcodes.OPCODE_multiply:
      case AbcConstants.Opcodes.OPCODE_divide:
      case AbcConstants.Opcodes.OPCODE_modulo:
      case AbcConstants.Opcodes.OPCODE_lshift:
      case AbcConstants.Opcodes.OPCODE_rshift:
      case AbcConstants.Opcodes.OPCODE_urshift:
      case AbcConstants.Opcodes.OPCODE_bitand:
      case AbcConstants.Opcodes.OPCODE_bitor:
      case AbcConstants.Opcodes.OPCODE_bitxor:
      case AbcConstants.Opcodes.OPCODE_equals:
      case AbcConstants.Opcodes.OPCODE_strictequals:
      case AbcConstants.Opcodes.OPCODE_lessthan:
      case AbcConstants.Opcodes.OPCODE_lessequals:
      case AbcConstants.Opcodes.OPCODE_greaterthan:
      case AbcConstants.Opcodes.OPCODE_greaterequals:
      case AbcConstants.Opcodes.OPCODE_instanceof:
      case AbcConstants.Opcodes.OPCODE_istypelate:
      case AbcConstants.Opcodes.OPCODE_in:
      case AbcConstants.Opcodes.OPCODE_increment_i:
      case AbcConstants.Opcodes.OPCODE_decrement_i:
      case AbcConstants.Opcodes.OPCODE_negate_i:
      case AbcConstants.Opcodes.OPCODE_add_i:
      case AbcConstants.Opcodes.OPCODE_subtract_i:
      case AbcConstants.Opcodes.OPCODE_multiply_i:
      case AbcConstants.Opcodes.OPCODE_getlocal0:
      case AbcConstants.Opcodes.OPCODE_getlocal1:
      case AbcConstants.Opcodes.OPCODE_getlocal2:
      case AbcConstants.Opcodes.OPCODE_getlocal3:
      case AbcConstants.Opcodes.OPCODE_setlocal0:
      case AbcConstants.Opcodes.OPCODE_setlocal1:
      case AbcConstants.Opcodes.OPCODE_setlocal2:
      case AbcConstants.Opcodes.OPCODE_setlocal3:
      case AbcConstants.Opcodes.OPCODE_bkptline:
      case AbcConstants.Opcodes.OPCODE_checkfilter:
      case AbcConstants.Opcodes.OPCODE_timestamp:
        break;
      default:
        throw new IllegalArgumentException("Illegal opcode for class " + getClass().getName() + ": " + opcode);
    }
  }

  AbcOpSimple() {
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

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(opcode);
  }
}
