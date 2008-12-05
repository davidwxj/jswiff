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

package com.jswiff.constants;

import java.util.HashMap;
import java.util.Map;

public class AbcConstants {
  
  public enum ValueTypeKind {
    
    UNDEFINED ((short)0x00, "undefined"),
    STRING    ((short)0x01, "string"),
    INT       ((short)0x03, "int"),
    U_INT     ((short)0x04, "u_int"),
    DOUBLE    ((short)0x06, "double"),
    FALSE     ((short)0x0A, "false"),
    TRUE      ((short)0x0B, "true"),
    NULL      ((short)0x0C, "null");
    
    public static ValueTypeKind getKind(short code) {
      for (ValueTypeKind bk : ValueTypeKind.values()) {
        if (bk.code == code) return bk;
      }
      return null;
    }
    
    private final short code;
    private final String name;
    
    ValueTypeKind(short code, String name) {
      this.code = code;
      this.name = name;
    }
    
    public short getCode() {
      return this.code;
    }
    
    @Override
    public String toString() {
      return this.name;
    }
  }
  
  public enum OpCodeType {
    ARGS,
    BRANCH, 
    DEBUG, 
    HAS_NEXT2, 
    INDEX, 
    INDEX_ARGS, 
    LOOKUP_SWITCH, 
    SIMPLE, 
    VALUE_BYTE, 
    VALUE_INT,
    PROFILING;
  }

  public enum OpCode {
    
    DEBUG              (OpCodeType.DEBUG, (short)0xEF, "debug"),
    
    HASNEXT2           (OpCodeType.HAS_NEXT2, (short)0x32, "hasnext2"),
    
    LOOKUPSWITCH       (OpCodeType.LOOKUP_SWITCH, (short)0x1B, "lookupswitch"),
    
    GETSCOPEOBJECT     (OpCodeType.VALUE_BYTE, (short)0x65, "getscopeobject"),
    PUSHBYTE           (OpCodeType.VALUE_BYTE, (short)0x24, "pushbyte"),
    
    DEBUGLINE          (OpCodeType.VALUE_INT, (short)0xF0, "debugline"),
    PUSHSHORT          (OpCodeType.VALUE_INT, (short)0x25, "pushshort"),
    
    CALLSTATIC         (OpCodeType.INDEX_ARGS, (short)0x44, "callstatic"),
    CALLSUPER          (OpCodeType.INDEX_ARGS, (short)0x45, "callsuper"),
    CALLPROPERTY       (OpCodeType.INDEX_ARGS, (short)0x46, "callproperty"),
    CONSTRUCTPROP      (OpCodeType.INDEX_ARGS, (short)0x4A, "constructprop"),
    CALLPROPLEX        (OpCodeType.INDEX_ARGS, (short)0x4C, "callproplex"),
    CALLSUPERVOID      (OpCodeType.INDEX_ARGS, (short)0x4E, "callsupervoid"),
    CALLPROPVOID       (OpCodeType.INDEX_ARGS, (short)0x4F, "callpropvoid"),

    CALL               (OpCodeType.ARGS, (short)0x41, "call"),
    CONSTRUCT          (OpCodeType.ARGS, (short)0x42, "construct"),
    CONSTRUCTSUPER     (OpCodeType.ARGS, (short)0x49, "constructsuper"),
    NEWOBJECT          (OpCodeType.ARGS, (short)0x55, "newobject"),
    NEWARRAY           (OpCodeType.ARGS, (short)0x56, "newarray"),

    ASTYPE             (OpCodeType.INDEX, (short)0x86, "astype"),
    COERCE             (OpCodeType.INDEX, (short)0x80, "coerce"),
    DEBUGFILE          (OpCodeType.INDEX, (short)0xF1, "debugfile"),
    DECLOCAL           (OpCodeType.INDEX, (short)0x94, "declocal"),
    DECLOCAL_I         (OpCodeType.INDEX, (short)0xC3, "declocal_i"),
    DELETEPROPERTY     (OpCodeType.INDEX, (short)0x6A, "deleteproperty"),
    FINDDEF            (OpCodeType.INDEX, (short)0x5F, "finddef"),
    FINDPROPERTY       (OpCodeType.INDEX, (short)0x5E, "findproperty"),
    FINDPROPSTRICT     (OpCodeType.INDEX, (short)0x5D, "findpropstrict"),
    GETDESCENDANTS     (OpCodeType.INDEX, (short)0x59, "getdescendants"),
    GETGLOBALSLOT      (OpCodeType.INDEX, (short)0x6E, "getglobalslot"),
    GETLEX             (OpCodeType.INDEX, (short)0x60, "getlex"),
    GETLOCAL           (OpCodeType.INDEX, (short)0x62, "getlocal"),
    GETPROPERTY        (OpCodeType.INDEX, (short)0x66, "getproperty"),
    GETSLOT            (OpCodeType.INDEX, (short)0x6C, "getslot"),
    GETSUPER           (OpCodeType.INDEX, (short)0x04, "getsuper"),
    INCLOCAL           (OpCodeType.INDEX, (short)0x92, "inclocal"),
    INCLOCAL_I         (OpCodeType.INDEX, (short)0xC2, "inclocal_i"),
    INITPROPERTY       (OpCodeType.INDEX, (short)0x68, "initproperty"),
    ISTYPE             (OpCodeType.INDEX, (short)0xB2, "istype"),
    KILL               (OpCodeType.INDEX, (short)0x08, "kill"),
    NEWCATCH           (OpCodeType.INDEX, (short)0x5A, "newcatch"),
    NEWCLASS           (OpCodeType.INDEX, (short)0x58, "newclass"),
    NEWFUNCTION        (OpCodeType.INDEX, (short)0x40, "newfunction"),
    PUSHDOUBLE         (OpCodeType.INDEX, (short)0x2F, "pushdouble"),
    PUSHINT            (OpCodeType.INDEX, (short)0x2D, "pushint"),
    PUSHNAMESPACE      (OpCodeType.INDEX, (short)0x31, "pushnamespace"),
    PUSHSTRING         (OpCodeType.INDEX, (short)0x2C, "pushstring"),
    PUSHUINT           (OpCodeType.INDEX, (short)0x2E, "pushuint"),
    SETGLOBALSLOT      (OpCodeType.INDEX, (short)0x6F, "setglobalslot"),
    SETLOCAL           (OpCodeType.INDEX, (short)0x63, "setlocal"),
    SETPROPERTY        (OpCodeType.INDEX, (short)0x61, "setproperty"),
    SETSLOT            (OpCodeType.INDEX, (short)0x6D, "setslot"),
    SETSUPER           (OpCodeType.INDEX, (short)0x05, "setsuper"),
    
    IFEQ               (OpCodeType.BRANCH, (short)0x13, "ifeq"),
    IFFALSE            (OpCodeType.BRANCH, (short)0x12, "iffalse"),
    IFGE               (OpCodeType.BRANCH, (short)0x18, "ifge"),
    IFGT               (OpCodeType.BRANCH, (short)0x17, "ifgt"),
    IFLE               (OpCodeType.BRANCH, (short)0x16, "ifle"),
    IFLT               (OpCodeType.BRANCH, (short)0x15, "iflt"),
    IFNE               (OpCodeType.BRANCH, (short)0x14, "ifne"),
    IFNGE              (OpCodeType.BRANCH, (short)0x0F, "ifnge"),
    IFNGT              (OpCodeType.BRANCH, (short)0x0E, "ifngt"),
    IFNLE              (OpCodeType.BRANCH, (short)0x0D, "ifnle"),
    IFNLT              (OpCodeType.BRANCH, (short)0x0C, "ifnlt"),
    IFSTRICTEQ         (OpCodeType.BRANCH, (short)0x19, "ifstricteq"),
    IFSTRICTNE         (OpCodeType.BRANCH, (short)0x1A, "ifstrictne"),
    IFTRUE             (OpCodeType.BRANCH, (short)0x11, "iftrue"),
    JUMP               (OpCodeType.BRANCH, (short)0x10, "jump"),
    
    ADD                (OpCodeType.SIMPLE, (short)0xA0, "add"),
    ADD_D              (OpCodeType.SIMPLE, (short)0x9B, "add_d"),
    ADD_I              (OpCodeType.SIMPLE, (short)0xC5, "add_i"),
    ASTYPELATE         (OpCodeType.SIMPLE, (short)0x87, "astypelate"),
    BITAND             (OpCodeType.SIMPLE, (short)0xA8, "bitand"),
    BITNOT             (OpCodeType.SIMPLE, (short)0x97, "bitnot"),
    BITOR              (OpCodeType.SIMPLE, (short)0xA9, "bitor"),
    BITXOR             (OpCodeType.SIMPLE, (short)0xAA, "bitxor"),
    BKPT               (OpCodeType.SIMPLE, (short)0x01, "bkpt"),
    BKPTLINE           (OpCodeType.SIMPLE, (short)0xF2, "bkptline"),
    CALLINTERFACE      (OpCodeType.SIMPLE, (short)0x4D, "callinterface"),
    CALLMETHOD         (OpCodeType.SIMPLE, (short)0x43, "callmethod"),
    CALLSUPERID        (OpCodeType.SIMPLE, (short)0x4B, "callsuperid"),
    CHECKFILTER        (OpCodeType.SIMPLE, (short)0x78, "checkfilter"),
    COERCE_A           (OpCodeType.SIMPLE, (short)0x82, "coerce_a"),
    COERCE_B           (OpCodeType.SIMPLE, (short)0x81, "coerce_b"),
    COERCE_D           (OpCodeType.SIMPLE, (short)0x84, "coerce_d"),
    COERCE_I           (OpCodeType.SIMPLE, (short)0x83, "coerce_i"),
    COERCE_O           (OpCodeType.SIMPLE, (short)0x89, "coerce_o"),
    COERCE_S           (OpCodeType.SIMPLE, (short)0x85, "coerce_s"),
    COERCE_U           (OpCodeType.SIMPLE, (short)0x88, "coerce_u"),
    CONCAT             (OpCodeType.SIMPLE, (short)0x9A, "concat"),
    CONVERT_B          (OpCodeType.SIMPLE, (short)0x76, "convert_b"),
    CONVERT_D          (OpCodeType.SIMPLE, (short)0x75, "convert_d"),
    CONVERT_I          (OpCodeType.SIMPLE, (short)0x73, "convert_i"),
    CONVERT_O          (OpCodeType.SIMPLE, (short)0x77, "convert_o"),
    CONVERT_S          (OpCodeType.SIMPLE, (short)0x70, "convert_s"),
    CONVERT_U          (OpCodeType.SIMPLE, (short)0x74, "convert_u"),
    DECREMENT          (OpCodeType.SIMPLE, (short)0x93, "decrement"),
    DECREMENT_I        (OpCodeType.SIMPLE, (short)0xC1, "decrement_i"),
    DELETEPROPERTYLATE (OpCodeType.SIMPLE, (short)0x6B, "deletepropertylate"),
    DIVIDE             (OpCodeType.SIMPLE, (short)0xA3, "divide"),
    DUP                (OpCodeType.SIMPLE, (short)0x2A, "dup"),
    DXNS               (OpCodeType.SIMPLE, (short)0x06, "dxns"),
    DXNSLATE           (OpCodeType.SIMPLE, (short)0x07, "dxnslate"),
    EQUALS             (OpCodeType.SIMPLE, (short)0xAB, "equals"),
    ESC_XATTR          (OpCodeType.SIMPLE, (short)0x72, "esc_xattr"),
    ESC_XELEM          (OpCodeType.SIMPLE, (short)0x71, "esc_xelem"),
    GETGLOBALSCOPE     (OpCodeType.SIMPLE, (short)0x64, "getglobalscope"),
    GETLOCAL0          (OpCodeType.SIMPLE, (short)0xD0, "getlocal0"),
    GETLOCAL1          (OpCodeType.SIMPLE, (short)0xD1, "getlocal1"),
    GETLOCAL2          (OpCodeType.SIMPLE, (short)0xD2, "getlocal2"),
    GETLOCAL3          (OpCodeType.SIMPLE, (short)0xD3, "getlocal3"),
    GETPROPERTYLATE    (OpCodeType.SIMPLE, (short)0x67, "getpropertylate"),
    GREATEREQUALS      (OpCodeType.SIMPLE, (short)0xB0, "greaterequals"),
    GREATERTHAN        (OpCodeType.SIMPLE, (short)0xAF, "greaterthan"),
    HASNEXT            (OpCodeType.SIMPLE, (short)0x1F, "hasnext"),
    IN                 (OpCodeType.SIMPLE, (short)0xB4, "in"),
    INCREMENT          (OpCodeType.SIMPLE, (short)0x91, "increment"),
    INCREMENT_I        (OpCodeType.SIMPLE, (short)0xC0, "increment_i"),
    INSTANCEOF         (OpCodeType.SIMPLE, (short)0xB1, "instanceof"),
    ISTYPELATE         (OpCodeType.SIMPLE, (short)0xB3, "istypelate"),
    LABEL              (OpCodeType.SIMPLE, (short)0x09, "label"), // ignored by avm
    LESSEQUALS         (OpCodeType.SIMPLE, (short)0xAE, "lessequals"),
    LESSTHAN           (OpCodeType.SIMPLE, (short)0xAD, "lessthan"),
    LSHIFT             (OpCodeType.SIMPLE, (short)0xA5, "lshift"),
    MODULO             (OpCodeType.SIMPLE, (short)0xA4, "modulo"),
    MULTIPLY           (OpCodeType.SIMPLE, (short)0xA2, "multiply"),
    MULTIPLY_I         (OpCodeType.SIMPLE, (short)0xC7, "multiply_i"),
    NEGATE             (OpCodeType.SIMPLE, (short)0x90, "negate"),
    NEGATE_I           (OpCodeType.SIMPLE, (short)0xC4, "negate_i"),
    NEWACTIVATION      (OpCodeType.SIMPLE, (short)0x57, "newactivation"),
    NEXTNAME           (OpCodeType.SIMPLE, (short)0x1E, "nextname"),
    NEXTVALUE          (OpCodeType.SIMPLE, (short)0x23, "nextvalue"),
    NOP                (OpCodeType.SIMPLE, (short)0x02, "nop"), // ignored by avm
    NOT                (OpCodeType.SIMPLE, (short)0x96, "not"),
    POP                (OpCodeType.SIMPLE, (short)0x29, "pop"),
    POPSCOPE           (OpCodeType.SIMPLE, (short)0x1D, "popscope"),
    PUSHCONSTANT       (OpCodeType.SIMPLE, (short)0x22, "pushconstant"),
    PUSHFALSE          (OpCodeType.SIMPLE, (short)0x27, "pushfalse"),
    PUSHNAN            (OpCodeType.SIMPLE, (short)0x28, "pushnan"),
    PUSHNULL           (OpCodeType.SIMPLE, (short)0x20, "pushnull"),
    PUSHSCOPE          (OpCodeType.SIMPLE, (short)0x30, "pushscope"),
    PUSHTRUE           (OpCodeType.SIMPLE, (short)0x26, "pushtrue"),
    PUSHUNDEFINED      (OpCodeType.SIMPLE, (short)0x21, "pushundefined"),
    PUSHWITH           (OpCodeType.SIMPLE, (short)0x1C, "pushwith"),
    RETURNVALUE        (OpCodeType.SIMPLE, (short)0x48, "returnvalue"),
    RETURNVOID         (OpCodeType.SIMPLE, (short)0x47, "returnvoid"),
    RSHIFT             (OpCodeType.SIMPLE, (short)0xA6, "rshift"),
    SETLOCAL0          (OpCodeType.SIMPLE, (short)0xD4, "setlocal0"),
    SETLOCAL1          (OpCodeType.SIMPLE, (short)0xD5, "setlocal1"),
    SETLOCAL2          (OpCodeType.SIMPLE, (short)0xD6, "setlocal2"),
    SETLOCAL3          (OpCodeType.SIMPLE, (short)0xD7, "setlocal3"),
    SETPROPERTYLATE    (OpCodeType.SIMPLE, (short)0x69, "setpropertylate"),
    STRICTEQUALS       (OpCodeType.SIMPLE, (short)0xAC, "strictequals"),
    SUBTRACT           (OpCodeType.SIMPLE, (short)0xA1, "subtract"),
    SUBTRACT_I         (OpCodeType.SIMPLE, (short)0xC6, "subtract_i"),
    SWAP               (OpCodeType.SIMPLE, (short)0x2B, "swap"),
    THROW              (OpCodeType.SIMPLE, (short)0x03, "throw"),
    TIMESTAMP          (OpCodeType.SIMPLE, (short)0xF3, "timestamp"), // ignored by avm
    TYPEOF             (OpCodeType.SIMPLE, (short)0x95, "typeof"),
    URSHIFT            (OpCodeType.SIMPLE, (short)0xA7, "urshift"),
    
    // NO real opcodes, but values used for dynamic profiling
    VERIFYPASS         (OpCodeType.PROFILING, (short)0xF5, "verifypass"), // for VM profiling (abc verify overhead)
    ALLOC              (OpCodeType.PROFILING, (short)0xF6, "alloc"), // for GC profiling
    MARK               (OpCodeType.PROFILING, (short)0xF7, "mark"), // for GC profiling
    WB                 (OpCodeType.PROFILING, (short)0xF8, "wb"), // for GC profiling
    PROLOGUE           (OpCodeType.PROFILING, (short)0xF9, "prologue"), // for codegen profiling
    SENDENTER          (OpCodeType.PROFILING, (short)0xFA, "sendenter"), // ignored by AVM (profiling?)
    DOUBLETOATOM       (OpCodeType.PROFILING, (short)0xFB, "doubletoatom"), // for VM profiling (doubleToAtom method in avmcore)
    SWEEP              (OpCodeType.PROFILING, (short)0xFC, "sweep"), // for GC profiling
    CODEGENOP          (OpCodeType.PROFILING, (short)0xFD, "codegenop"), // for codegen profiling
    VERIFYOP           (OpCodeType.PROFILING, (short)0xFE, "verifyop"), // for VM profiling (abc verify overhead)
    DECODE             (OpCodeType.PROFILING, (short)0xFF, "decode"); // for VM profiling (abc parsing overhead)
    
    private static final Map<Short, OpCode>  codeLookupMap = new HashMap<Short, OpCode>();
    private static final Map<String, OpCode>  nameLookupMap = new HashMap<String, OpCode>();
    
    static {
      for (OpCode opCode : OpCode.values()) {
        codeLookupMap.put(opCode.getCode(), opCode);
        nameLookupMap.put(opCode.toString(), opCode);
      }
    }
    
    public static OpCode lookupOpCode(short code) {
      return codeLookupMap.get(code);
    }
    
    public static OpCode lookupOpCode(String name) {
      return nameLookupMap.get(name);
    }
    
    private final OpCodeType type;
    private final short code;
    private final String name;
    
    OpCode(OpCodeType type, short code, String name) {
      this.type = type;
      this.code = code;
      this.name = name;
    }
    
    public OpCodeType getType() {
      return this.type;
    }
    
    public short getCode() {
      return this.code;
    }
    
    @Override
    public String toString() {
      return this.name;
    }
    
  }
  
  public enum TraitKind {
    
    SLOT     ((short)0x00, "slot"),
    METHOD   ((short)0x01, "method"),
    GETTER   ((short)0x02, "getter"),
    SETTER   ((short)0x03, "setter"),
    CLASS    ((short)0x04, "class"),
    FUNCTION ((short)0x05, "function"),
    CONST    ((short)0x06, "const");
    
    public static TraitKind getKind(short code) {
      for (TraitKind type : TraitKind.values()) {
        if (type.code == code) return type;
      }
      return null;
    }
    
    private final short code;
    private final String name;
    
    TraitKind(short code, String name) {
      this.code = code;
      this.name = name;
    }
    
    public short getCode() {
      return this.code;
    }
    
    @Override
    public String toString() {
      return this.name;
    }
  }
  
  public enum NamespaceKind {
    
    PRIVATE          ((short)0x05, "private"),
    NAMESPACE        ((short)0x08, "namespace"),
    PACKAGE          ((short)0x16, "package"),
    PACKAGE_INTERNAL ((short)0x17, "package_internal"),
    PROTECTED        ((short)0x18, "protected"),
    EXPLICIT         ((short)0x19, "explicit"),
    STATIC_PROTECTED ((short)0x1A, "static_protected");
    
    public static NamespaceKind getKind(short code) {
      for (NamespaceKind nsKind : NamespaceKind.values()) {
        if (nsKind.code == code) return nsKind;
      }
      return null;
    }
    
    private final short code;
    private final String name;
    
    NamespaceKind(short code, String name) {
      this.code = code;
      this.name = name;
    }
    
    public short getCode() {
      return this.code;
    }
    
    @Override
    public String toString() {
      return this.name;
    }
  }
  
  public enum MultiNameKind {
    
    Q_NAME        ((short)0x07, "q_name"),
    Q_NAME_A      ((short)0x0D, "q_name_a"),
    RTQ_NAME      ((short)0x0F, "rtq_name"),
    RTQ_NAME_A    ((short)0x10, "rtq_name_a"),
    RTQ_NAME_L    ((short)0x11, "rtq_name_l"),
    RTQ_NAME_L_A  ((short)0x12, "rtq_name_l_a"),
    MULTINAME     ((short)0x09, "multiname"),
    MULTINAME_A   ((short)0x0E, "multiname_a"),
    MULTINAME_L   ((short)0x1B, "multiname_l"),
    MULTINAME_L_A ((short)0x1C, "multiname_l_a");
    
    public static MultiNameKind getKind(short code) {
      for (MultiNameKind mnKind : MultiNameKind.values()) {
        if (mnKind.code == code) return mnKind;
      }
      return null;
    }
    
    private final short code;
    private final String name;
    
    MultiNameKind(short code, String name) {
      this.code = code;
      this.name = name;
    }
    
    public short getCode() {
      return this.code;
    }
    
    @Override
    public String toString() {
      return this.name;
    }
  }
  
}
