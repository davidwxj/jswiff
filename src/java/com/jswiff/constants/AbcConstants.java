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

import com.jswiff.exception.InvalidCodeException;
import com.jswiff.exception.InvalidNameException;
import com.jswiff.swfrecords.abc.AbcSlotTrait;
import com.jswiff.swfrecords.abc.opcode.AbcOpArgs;
import com.jswiff.swfrecords.abc.opcode.AbcOpBranch;
import com.jswiff.swfrecords.abc.opcode.AbcOpDebug;
import com.jswiff.swfrecords.abc.opcode.AbcOpHasNext2;
import com.jswiff.swfrecords.abc.opcode.AbcOpIndex;
import com.jswiff.swfrecords.abc.opcode.AbcOpIndexArgs;
import com.jswiff.swfrecords.abc.opcode.AbcOpLookupSwitch;
import com.jswiff.swfrecords.abc.opcode.AbcOpSimple;
import com.jswiff.swfrecords.abc.opcode.AbcOpValueByte;
import com.jswiff.swfrecords.abc.opcode.AbcOpValueInt;

public class AbcConstants {
  
  /**
   * Represents AS3 value types.
   */
  public static enum ValueTypeKind implements ByteCodeConstant {
    
    UNDEFINED ((short)0x00, "undefined"),
    STRING    ((short)0x01, "string"),
    INT       ((short)0x03, "int"),
    U_INT     ((short)0x04, "u_int"),
    DOUBLE    ((short)0x06, "double"),
    FALSE     ((short)0x0A, "false"),
    TRUE      ((short)0x0B, "true"),
    NULL      ((short)0x0C, "null");
    
    private static ByteCodeConstantHelper<ValueTypeKind> helper;
    
    static {
      helper = new ByteCodeConstantHelper<ValueTypeKind>(ValueTypeKind.values(), "Value Type Kind");
    }
    
    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static ValueTypeKind lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }
    
    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static ValueTypeKind lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
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
  
  /**
   * Represents possible categories of ABC OpCodes.
   * @author bstock
   *
   */
  public static enum OpCodeType {
    /** Op is a {@link AbcOpArgs} */
    ARGS,
    /** Op is a {@link AbcOpBranch} */
    BRANCH,
    /** Op is a {@link AbcOpDebug} */
    DEBUG,
    /** Op is a {@link AbcOpHasNext2} */
    HAS_NEXT2,
    /** Op is a {@link AbcOpIndex} */
    INDEX,
    /** Op is a {@link AbcOpIndexArgs} */
    INDEX_ARGS,
    /** Op is a {@link AbcOpLookupSwitch} */
    LOOKUP_SWITCH,
    /** Op is a {@link AbcOpSimple} */
    SIMPLE,
    /** Op is a {@link AbcOpValueByte} */
    VALUE_BYTE,
    /** Op is a {@link AbcOpValueInt} */
    VALUE_INT,
    /** Op is a dynamic profiling instruction */
    PROFILING;
  }

  /**
   * Represents ABC OpCodes.
   */
  public static enum OpCode implements ByteCodeConstant {
    
    DEBUG                 (OpCodeType.DEBUG, (short)0xEF, "debug"),
    
    HAS_NEXT_2            (OpCodeType.HAS_NEXT2, (short)0x32, "hasnext2"),

    LOOK_UP_SWITCH        (OpCodeType.LOOKUP_SWITCH, (short)0x1B, "lookupswitch"),

    GET_SCOPE_OBJECT      (OpCodeType.VALUE_BYTE, (short)0x65, "getscopeobject"),
    PUSH_BYTE             (OpCodeType.VALUE_BYTE, (short)0x24, "pushbyte"),

    DEBUG_LINE            (OpCodeType.VALUE_INT, (short)0xF0, "debugline"),
    PUSH_SHORT            (OpCodeType.VALUE_INT, (short)0x25, "pushshort"),

    CALL_STATIC           (OpCodeType.INDEX_ARGS, (short)0x44, "callstatic"),
    CALL_SUPER            (OpCodeType.INDEX_ARGS, (short)0x45, "callsuper"),
    CALL_PROPERTY         (OpCodeType.INDEX_ARGS, (short)0x46, "callproperty"),
    CONSTRUCT_PROP        (OpCodeType.INDEX_ARGS, (short)0x4A, "constructprop"),
    CALL_PROP_LEX         (OpCodeType.INDEX_ARGS, (short)0x4C, "callproplex"),
    CALL_SUPER_VOID       (OpCodeType.INDEX_ARGS, (short)0x4E, "callsupervoid"),
    CALL_PROP_VOID        (OpCodeType.INDEX_ARGS, (short)0x4F, "callpropvoid"),

    CALL                  (OpCodeType.ARGS, (short)0x41, "call"),
    CONSTRUCT             (OpCodeType.ARGS, (short)0x42, "construct"),
    CONSTRUCT_SUPER       (OpCodeType.ARGS, (short)0x49, "constructsuper"),
    NEW_OBJECT            (OpCodeType.ARGS, (short)0x55, "newobject"),
    NEW_ARRAY             (OpCodeType.ARGS, (short)0x56, "newarray"),

    AS_TYPE               (OpCodeType.INDEX, (short)0x86, "astype"),
    COERCE                (OpCodeType.INDEX, (short)0x80, "coerce"),
    DEBUG_FILE            (OpCodeType.INDEX, (short)0xF1, "debugfile"),
    DEC_LOCAL             (OpCodeType.INDEX, (short)0x94, "declocal"),
    DEC_LOCAL_I           (OpCodeType.INDEX, (short)0xC3, "declocal_i"),
    DELETE_PROPERTY       (OpCodeType.INDEX, (short)0x6A, "deleteproperty"),
    FIND_DEF              (OpCodeType.INDEX, (short)0x5F, "finddef"),
    FIND_PROPERTY         (OpCodeType.INDEX, (short)0x5E, "findproperty"),
    FIND_PROP_STRICT      (OpCodeType.INDEX, (short)0x5D, "findpropstrict"),
    GET_DESCENDANTS       (OpCodeType.INDEX, (short)0x59, "getdescendants"),
    GET_GLOBAL_SLOT       (OpCodeType.INDEX, (short)0x6E, "getglobalslot"),
    GET_LEX               (OpCodeType.INDEX, (short)0x60, "getlex"),
    GET_LOCAL             (OpCodeType.INDEX, (short)0x62, "getlocal"),
    GET_PROPERTY          (OpCodeType.INDEX, (short)0x66, "getproperty"),
    GET_SLOT              (OpCodeType.INDEX, (short)0x6C, "getslot"),
    GET_SUPER             (OpCodeType.INDEX, (short)0x04, "getsuper"),
    INC_LOCAL             (OpCodeType.INDEX, (short)0x92, "inclocal"),
    INC_LOCAL_I           (OpCodeType.INDEX, (short)0xC2, "inclocal_i"),
    INIT_PROPERTY         (OpCodeType.INDEX, (short)0x68, "initproperty"),
    IS_TYPE               (OpCodeType.INDEX, (short)0xB2, "istype"),
    KILL                  (OpCodeType.INDEX, (short)0x08, "kill"),
    NEW_CATCH             (OpCodeType.INDEX, (short)0x5A, "newcatch"),
    NEW_CLASS             (OpCodeType.INDEX, (short)0x58, "newclass"),
    NEW_FUNCTION          (OpCodeType.INDEX, (short)0x40, "newfunction"),
    PUSH_DOUBLE           (OpCodeType.INDEX, (short)0x2F, "pushdouble"),
    PUSH_INT              (OpCodeType.INDEX, (short)0x2D, "pushint"),
    PUSH_NAMESPACE        (OpCodeType.INDEX, (short)0x31, "pushnamespace"),
    PUSH_STRING           (OpCodeType.INDEX, (short)0x2C, "pushstring"),
    PUSH_UINT             (OpCodeType.INDEX, (short)0x2E, "pushuint"),
    SET_GLOBAL_SLOT       (OpCodeType.INDEX, (short)0x6F, "setglobalslot"),
    SET_LOCAL             (OpCodeType.INDEX, (short)0x63, "setlocal"),
    SET_PROPERTY          (OpCodeType.INDEX, (short)0x61, "setproperty"),
    SET_SLOT              (OpCodeType.INDEX, (short)0x6D, "setslot"),
    SET_SUPER             (OpCodeType.INDEX, (short)0x05, "setsuper"),
    
    IF_EQ                 (OpCodeType.BRANCH, (short)0x13, "ifeq"),
    IF_FALSE              (OpCodeType.BRANCH, (short)0x12, "iffalse"),
    IF_GE                 (OpCodeType.BRANCH, (short)0x18, "ifge"),
    IF_GT                 (OpCodeType.BRANCH, (short)0x17, "ifgt"),
    IF_LE                 (OpCodeType.BRANCH, (short)0x16, "ifle"),
    IF_LT                 (OpCodeType.BRANCH, (short)0x15, "iflt"),
    IF_NE                 (OpCodeType.BRANCH, (short)0x14, "ifne"),
    IF_NGE                (OpCodeType.BRANCH, (short)0x0F, "ifnge"),
    IF_NGT                (OpCodeType.BRANCH, (short)0x0E, "ifngt"),
    IF_NLE                (OpCodeType.BRANCH, (short)0x0D, "ifnle"),
    IF_NLT                (OpCodeType.BRANCH, (short)0x0C, "ifnlt"),
    IF_STRICT_EQ          (OpCodeType.BRANCH, (short)0x19, "ifstricteq"),
    IF_STRICT_NE          (OpCodeType.BRANCH, (short)0x1A, "ifstrictne"),
    IF_TRUE               (OpCodeType.BRANCH, (short)0x11, "iftrue"),
    JUMP                  (OpCodeType.BRANCH, (short)0x10, "jump"),
    
    ADD                   (OpCodeType.SIMPLE, (short)0xA0, "add"),
    ADD_D                 (OpCodeType.SIMPLE, (short)0x9B, "add_d"),
    ADD_I                 (OpCodeType.SIMPLE, (short)0xC5, "add_i"),
    AS_TYPE_LATE          (OpCodeType.SIMPLE, (short)0x87, "astypelate"),
    BIT_AND               (OpCodeType.SIMPLE, (short)0xA8, "bitand"),
    BIT_NOT               (OpCodeType.SIMPLE, (short)0x97, "bitnot"),
    BIT_OR                (OpCodeType.SIMPLE, (short)0xA9, "bitor"),
    BIT_XOR               (OpCodeType.SIMPLE, (short)0xAA, "bitxor"),
    BKPT                  (OpCodeType.SIMPLE, (short)0x01, "bkpt"),
    BKPT_LINE             (OpCodeType.SIMPLE, (short)0xF2, "bkptline"),
    CALL_INTERFACE        (OpCodeType.SIMPLE, (short)0x4D, "callinterface"),
    CALL_METHOD           (OpCodeType.SIMPLE, (short)0x43, "callmethod"),
    CALL_SUPERID          (OpCodeType.SIMPLE, (short)0x4B, "callsuperid"),
    CHECK_FILTER          (OpCodeType.SIMPLE, (short)0x78, "checkfilter"),
    COERCE_A              (OpCodeType.SIMPLE, (short)0x82, "coerce_a"),
    COERCE_B              (OpCodeType.SIMPLE, (short)0x81, "coerce_b"),
    COERCE_D              (OpCodeType.SIMPLE, (short)0x84, "coerce_d"),
    COERCE_I              (OpCodeType.SIMPLE, (short)0x83, "coerce_i"),
    COERCE_O              (OpCodeType.SIMPLE, (short)0x89, "coerce_o"),
    COERCE_S              (OpCodeType.SIMPLE, (short)0x85, "coerce_s"),
    COERCE_U              (OpCodeType.SIMPLE, (short)0x88, "coerce_u"),
    CONCAT                (OpCodeType.SIMPLE, (short)0x9A, "concat"),
    CONVERT_B             (OpCodeType.SIMPLE, (short)0x76, "convert_b"),
    CONVERT_D             (OpCodeType.SIMPLE, (short)0x75, "convert_d"),
    CONVERT_I             (OpCodeType.SIMPLE, (short)0x73, "convert_i"),
    CONVERT_O             (OpCodeType.SIMPLE, (short)0x77, "convert_o"),
    CONVERT_S             (OpCodeType.SIMPLE, (short)0x70, "convert_s"),
    CONVERT_U             (OpCodeType.SIMPLE, (short)0x74, "convert_u"),
    DECREMENT             (OpCodeType.SIMPLE, (short)0x93, "decrement"),
    DECREMENT_I           (OpCodeType.SIMPLE, (short)0xC1, "decrement_i"),
    DELETE_PROPERTY_LATE  (OpCodeType.SIMPLE, (short)0x6B, "deletepropertylate"),
    DIVIDE                (OpCodeType.SIMPLE, (short)0xA3, "divide"),
    DUP                   (OpCodeType.SIMPLE, (short)0x2A, "dup"),
    DXNS                  (OpCodeType.SIMPLE, (short)0x06, "dxns"),
    DXNS_LATE             (OpCodeType.SIMPLE, (short)0x07, "dxnslate"),
    EQUALS                (OpCodeType.SIMPLE, (short)0xAB, "equals"),
    ESC_XATTR             (OpCodeType.SIMPLE, (short)0x72, "esc_xattr"),
    ESC_XELEM             (OpCodeType.SIMPLE, (short)0x71, "esc_xelem"),
    GET_GLOBAL_SCOPE      (OpCodeType.SIMPLE, (short)0x64, "getglobalscope"),
    GET_LOCAL_0           (OpCodeType.SIMPLE, (short)0xD0, "getlocal0"),
    GET_LOCAL_1           (OpCodeType.SIMPLE, (short)0xD1, "getlocal1"),
    GET_LOCAL_2           (OpCodeType.SIMPLE, (short)0xD2, "getlocal2"),
    GET_LOCAL_3           (OpCodeType.SIMPLE, (short)0xD3, "getlocal3"),
    GET_PROPERTY_LATE     (OpCodeType.SIMPLE, (short)0x67, "getpropertylate"),
    GREATER_EQUALS        (OpCodeType.SIMPLE, (short)0xB0, "greaterequals"),
    GREATER_THAN          (OpCodeType.SIMPLE, (short)0xAF, "greaterthan"),
    HAS_NEXT              (OpCodeType.SIMPLE, (short)0x1F, "hasnext"),
    IN                    (OpCodeType.SIMPLE, (short)0xB4, "in"),
    INCREMENT             (OpCodeType.SIMPLE, (short)0x91, "increment"),
    INCREMENT_I           (OpCodeType.SIMPLE, (short)0xC0, "increment_i"),
    INSTANCE_OF           (OpCodeType.SIMPLE, (short)0xB1, "instanceof"),
    IS_TYPE_LATE          (OpCodeType.SIMPLE, (short)0xB3, "istypelate"),
    LABEL                 (OpCodeType.SIMPLE, (short)0x09, "label"), // ignored by avm
    LESS_EQUALS           (OpCodeType.SIMPLE, (short)0xAE, "lessequals"),
    LESS_THAN             (OpCodeType.SIMPLE, (short)0xAD, "lessthan"),
    L_SHIFT               (OpCodeType.SIMPLE, (short)0xA5, "lshift"),
    MODULO                (OpCodeType.SIMPLE, (short)0xA4, "modulo"),
    MULTIPLY              (OpCodeType.SIMPLE, (short)0xA2, "multiply"),
    MULTIPLY_I            (OpCodeType.SIMPLE, (short)0xC7, "multiply_i"),
    NEGATE                (OpCodeType.SIMPLE, (short)0x90, "negate"),
    NEGATE_I              (OpCodeType.SIMPLE, (short)0xC4, "negate_i"),
    NEW_ACTIVATION        (OpCodeType.SIMPLE, (short)0x57, "newactivation"),
    NEXT_NAME             (OpCodeType.SIMPLE, (short)0x1E, "nextname"),
    NEXT_VALUE            (OpCodeType.SIMPLE, (short)0x23, "nextvalue"),
    NOP                   (OpCodeType.SIMPLE, (short)0x02, "nop"), // ignored by avm
    NOT                   (OpCodeType.SIMPLE, (short)0x96, "not"),
    POP                   (OpCodeType.SIMPLE, (short)0x29, "pop"),
    POP_SCOPE             (OpCodeType.SIMPLE, (short)0x1D, "popscope"),
    PUSH_CONSTANT         (OpCodeType.SIMPLE, (short)0x22, "pushconstant"),
    PUSH_FALSE            (OpCodeType.SIMPLE, (short)0x27, "pushfalse"),
    PUSH_NAN              (OpCodeType.SIMPLE, (short)0x28, "pushnan"),
    PUSH_NULL             (OpCodeType.SIMPLE, (short)0x20, "pushnull"),
    PUSH_SCOPE            (OpCodeType.SIMPLE, (short)0x30, "pushscope"),
    PUSH_TRUE             (OpCodeType.SIMPLE, (short)0x26, "pushtrue"),
    PUSH_UNDEFINED        (OpCodeType.SIMPLE, (short)0x21, "pushundefined"),
    PUSH_WITH             (OpCodeType.SIMPLE, (short)0x1C, "pushwith"),
    RETURN_VALUE          (OpCodeType.SIMPLE, (short)0x48, "returnvalue"),
    RETURN_VOID           (OpCodeType.SIMPLE, (short)0x47, "returnvoid"),
    R_SHIFT               (OpCodeType.SIMPLE, (short)0xA6, "rshift"),
    SET_LOCAL_0           (OpCodeType.SIMPLE, (short)0xD4, "setlocal0"),
    SET_LOCAL_1           (OpCodeType.SIMPLE, (short)0xD5, "setlocal1"),
    SET_LOCAL_2           (OpCodeType.SIMPLE, (short)0xD6, "setlocal2"),
    SET_LOCAL_3           (OpCodeType.SIMPLE, (short)0xD7, "setlocal3"),
    SET_PROPERTY_LATE     (OpCodeType.SIMPLE, (short)0x69, "setpropertylate"),
    STRICT_EQUALS         (OpCodeType.SIMPLE, (short)0xAC, "strictequals"),
    SUBTRACT              (OpCodeType.SIMPLE, (short)0xA1, "subtract"),
    SUBTRACT_I            (OpCodeType.SIMPLE, (short)0xC6, "subtract_i"),
    SWAP                  (OpCodeType.SIMPLE, (short)0x2B, "swap"),
    THROW                 (OpCodeType.SIMPLE, (short)0x03, "throw"),
    TIMESTAMP             (OpCodeType.SIMPLE, (short)0xF3, "timestamp"), // ignored by avm
    TYPE_OF               (OpCodeType.SIMPLE, (short)0x95, "typeof"),
    URSHIFT               (OpCodeType.SIMPLE, (short)0xA7, "urshift"),
    
    // NO real opcodes, but values used for dynamic profiling
    VERIFY_PASS           (OpCodeType.PROFILING, (short)0xF5, "verifypass"), // for VM profiling (abc verify overhead)
    ALLOC                 (OpCodeType.PROFILING, (short)0xF6, "alloc"), // for GC profiling
    MARK                  (OpCodeType.PROFILING, (short)0xF7, "mark"), // for GC profiling
    WB                    (OpCodeType.PROFILING, (short)0xF8, "wb"), // for GC profiling
    PROLOGUE              (OpCodeType.PROFILING, (short)0xF9, "prologue"), // for codegen profiling
    SEND_ENTER            (OpCodeType.PROFILING, (short)0xFA, "sendenter"), // ignored by AVM (profiling?)
    DOUBLE_TO_ATOM        (OpCodeType.PROFILING, (short)0xFB, "doubletoatom"), // for VM profiling (doubleToAtom method in avmcore)
    SWEEP                 (OpCodeType.PROFILING, (short)0xFC, "sweep"), // for GC profiling
    CODE_GEN_OP           (OpCodeType.PROFILING, (short)0xFD, "codegenop"), // for codegen profiling
    VERIFY_OP             (OpCodeType.PROFILING, (short)0xFE, "verifyop"), // for VM profiling (abc verify overhead)
    DECODE                (OpCodeType.PROFILING, (short)0xFF, "decode"); // for VM profiling (abc parsing overhead)
    
    private static ByteCodeConstantHelper<OpCode> helper;
    
    static {
      helper = new ByteCodeConstantHelper<OpCode>(OpCode.values(), "ABC Op Code");
    }
    
    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static OpCode lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }
    
    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static OpCode lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
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
  
  /**
   * Represents ABC trait types.
   */
  public static enum TraitKind implements ByteCodeConstant {
    
    SLOT     ((short)0x00, "slot"),
    METHOD   ((short)0x01, "method"),
    GETTER   ((short)0x02, "getter"),
    SETTER   ((short)0x03, "setter"),
    CLASS    ((short)0x04, "class"),
    FUNCTION ((short)0x05, "function"),
    CONST    ((short)0x06, "const");
    
    private static ByteCodeConstantHelper<TraitKind> helper;
    
    static {
      helper = new ByteCodeConstantHelper<TraitKind>(TraitKind.values(), "ABC Trait Kind");
    }
    
    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static TraitKind lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }
    
    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static TraitKind lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
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
  
  /**
   * Represents ABC namespace types.
   */
  public static enum NamespaceKind implements ByteCodeConstant {
    
    PRIVATE          ((short)0x05, "private"),
    NAMESPACE        ((short)0x08, "namespace"),
    PACKAGE          ((short)0x16, "package"),
    PACKAGE_INTERNAL ((short)0x17, "package_internal"),
    PROTECTED        ((short)0x18, "protected"),
    EXPLICIT         ((short)0x19, "explicit"),
    STATIC_PROTECTED ((short)0x1A, "static_protected");
    
    private static ByteCodeConstantHelper<NamespaceKind> helper;
    
    static {
      helper = new ByteCodeConstantHelper<NamespaceKind>(NamespaceKind.values(), "ABC Namespace Kind");
    }
    
    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static NamespaceKind lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }
    
    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static NamespaceKind lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
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
  
  /**
   * Represents ABC multiname types.
   */
  public static enum MultiNameKind implements ByteCodeConstant {
    
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
    
    private static ByteCodeConstantHelper<MultiNameKind> helper;
    
    static {
      helper = new ByteCodeConstantHelper<MultiNameKind>(MultiNameKind.values(), "ABC Multiname Kind");
    }
    
    /**
     * Lookup a value corresponding to the given code
     * @param code the bytecode id
     * @return the enum mapped to the given code
     * @throws InvalidCodeException if no value exists for the given code.
     */
    public static MultiNameKind lookup(short code) throws InvalidCodeException {
      return helper.codeLookup(code);
    }
    
    /**
     * Lookup a value based corresponding to the given string
     * @param name the string id
     * @return the enum mapped to the given name
     * @throws InvalidNameException if no value exists for the given name
     */
    public static MultiNameKind lookup(String name) throws InvalidNameException {
      return helper.nameLookup(name);
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
  
  /**
   * Represents Constant types. Indicates which constant-pool to locate a value in based
   * on type code. For types {@code UNDEFINED}, {@code TRUE}, {@code FALSE}, and {@code NULL}
   * the constant-pool should be considered as a special type containing only one value 
   * (e.g. 'True' for TRUE).
   */
  public static enum ConstantType {
    
    UNDEFINED ("undefined", (short)0x00),
    FALSE     ("false",     (short)0x0A),
    TRUE      ("true",      (short)0x0B),
    NULL      ("null",      (short)0x0C),
    STRING    ("string",    (short)0x01),
    INT       ("int",       (short)0x03),
    U_INT     ("u_int",     (short)0x04),
    DOUBLE    ("double",    (short)0x06),
    NAMESPACE ("namespace", (short)0x05, 
                            (short)0x08,
                            (short)0x16,
                            (short)0x17,
                            (short)0x18,
                            (short)0x19,
                            (short)0x1A);
    
    private static final Map<Short, ConstantType> lookupMap = 
      new HashMap<Short, ConstantType>(values().length);
    
    static {
      for (ConstantType type : values()) {
        for (short code : type.codes) {
          lookupMap.put(code, type);
        }
      }
    }
    
    /**
     * Get the type of constant pool the given code references, for example
     * determining which constant pool type {@link AbcSlotTrait#getValueIndex()}
     * is an index into based on {@link AbcSlotTrait#getValueKind()}.
     * @param code the code used to determine the constant pool type
     * @return the appropriate constant pool type, or null if there is none.
     */
    public static ConstantType getConstantType(short code) {
      return lookupMap.get(code);
    }
    
    private final Short[] codes;
    private final String name;
    
    ConstantType(String name, Short... codes) {
      this.codes = codes;
      this.name = name;
    }
    
    @Override
    public String toString() {
      return this.name;
    }
  }
  
}
