package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public abstract class AbcOp implements Serializable {
  protected short opcode;
  
  protected AbcOp(short opcode) {
    this.opcode = opcode;
  }
  
  AbcOp() {
    // empty
  }
  
  public static AbcOp read(InputBitStream stream) throws IOException {
    short opcode = stream.readUI8();
    AbcOp op;
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
        op = new AbcOpIndex(stream.readAbcInt());
        break;
      case AbcConstants.Opcodes.OPCODE_debugline:
      case AbcConstants.Opcodes.OPCODE_pushshort:
        op = new AbcOpValueInt(stream.readAbcInt());
        break;
      case AbcConstants.Opcodes.OPCODE_pushbyte:
      case AbcConstants.Opcodes.OPCODE_getscopeobject:
        op = new AbcOpValueByte(stream.readSI8());
        break;
      case AbcConstants.Opcodes.OPCODE_constructprop:
      case AbcConstants.Opcodes.OPCODE_callproperty:
      case AbcConstants.Opcodes.OPCODE_callproplex:
      case AbcConstants.Opcodes.OPCODE_callsuper:
      case AbcConstants.Opcodes.OPCODE_callsupervoid:
      case AbcConstants.Opcodes.OPCODE_callpropvoid:
      case AbcConstants.Opcodes.OPCODE_callstatic:
        op = new AbcOpIndexArgs(stream.readAbcInt(), stream.readAbcInt());
        break;
      case AbcConstants.Opcodes.OPCODE_lookupswitch:
        int defaultOffset = stream.readSI24();
        int caseCount = stream.readAbcInt() + 1;
        AbcOpLookupSwitch switchOp = new AbcOpLookupSwitch(defaultOffset);
        for (int i = 0; i < caseCount; i++) {
          switchOp.addCaseOffset(stream.readSI24());
        }
        op = switchOp;
        break;
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
        op = new AbcOpBranch(stream.readSI24());
        break;
      case AbcConstants.Opcodes.OPCODE_debug:
        op = new AbcOpDebug(stream.readUI8(), stream.readAbcInt(), stream.readUI8(), stream.readAbcInt());
        break;
      case AbcConstants.Opcodes.OPCODE_newobject:
      case AbcConstants.Opcodes.OPCODE_newarray:
      case AbcConstants.Opcodes.OPCODE_call:
      case AbcConstants.Opcodes.OPCODE_construct:
      case AbcConstants.Opcodes.OPCODE_constructsuper:
        op = new AbcOpArgs(stream.readAbcInt());
        break;
      case AbcConstants.Opcodes.OPCODE_hasnext2:
        op = new AbcOpHasNext2(stream.readAbcInt(), stream.readAbcInt());
        break;
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
        op = new AbcOpSimple();
        break;
      default:
        throw new IOException("Unknown abc byte code operation, opcode = " + opcode);
    }
    op.opcode = opcode;
    return op;
  }

  public abstract String getOpName();
  
  public short getOpcode() {
    return opcode;
  }

  public abstract void write(OutputBitStream stream) throws IOException;
}
