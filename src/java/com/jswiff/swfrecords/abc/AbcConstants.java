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

public class AbcConstants {
  public interface BasicKinds {
    public static final short UNDEFINED = 0x00;
    public static final short STRING = 0x01;
    public static final short INT = 0x03;
    public static final short U_INT = 0x04;
    public static final short DOUBLE = 0x06;
    public static final short FALSE = 0x0A;
    public static final short TRUE = 0x0B;
    public static final short NULL = 0x0C;
  }
  
  public interface NamespaceKinds {
    public static final short PRIVATE_NAMESPACE = 0x05;
    public static final short NAMESPACE = 0x08;
    public static final short PACKAGE_NAMESPACE = 0x16;
    public static final short PACKAGE_INTERNAL_NAMESPACE = 0x17;
    public static final short PROTECTED_NAMESPACE = 0x18;
    public static final short EXPLICIT_NAMESPACE = 0x19;
    public static final short STATIC_PROTECTED_NAMESPACE = 0x1A;
  }
  
  public interface Opcodes {
    public static final short OPCODE_bkpt = 0x01;
    public static final short OPCODE_nop = 0x02; // ignored by AVM
    public static final short OPCODE_throw = 0x03;
    public static final short OPCODE_getsuper = 0x04;
    public static final short OPCODE_setsuper = 0x05;
    public static final short OPCODE_dxns = 0x06;
    public static final short OPCODE_dxnslate = 0x07;
    public static final short OPCODE_kill = 0x08;
    public static final short OPCODE_label = 0x09; // ignored by AVM
    public static final short OPCODE_ifnlt = 0x0C;
    public static final short OPCODE_ifnle = 0x0D;
    public static final short OPCODE_ifngt = 0x0E;
    public static final short OPCODE_ifnge = 0x0F;
    public static final short OPCODE_jump = 0x10;
    public static final short OPCODE_iftrue = 0x11;
    public static final short OPCODE_iffalse = 0x12;
    public static final short OPCODE_ifeq = 0x13;
    public static final short OPCODE_ifne = 0x14;
    public static final short OPCODE_iflt = 0x15;
    public static final short OPCODE_ifle = 0x16;
    public static final short OPCODE_ifgt = 0x17;
    public static final short OPCODE_ifge = 0x18;
    public static final short OPCODE_ifstricteq = 0x19;
    public static final short OPCODE_ifstrictne = 0x1A;
    public static final short OPCODE_lookupswitch = 0x1B;
    public static final short OPCODE_pushwith = 0x1C;
    public static final short OPCODE_popscope = 0x1D;
    public static final short OPCODE_nextname = 0x1E;
    public static final short OPCODE_hasnext = 0x1F;
    public static final short OPCODE_pushnull = 0x20;
    public static final short OPCODE_pushundefined = 0x21;
    public static final short OPCODE_pushconstant = 0x22;
    public static final short OPCODE_nextvalue = 0x23;
    public static final short OPCODE_pushbyte = 0x24;
    public static final short OPCODE_pushshort = 0x25;
    public static final short OPCODE_pushtrue = 0x26;
    public static final short OPCODE_pushfalse = 0x27;
    public static final short OPCODE_pushnan = 0x28;
    public static final short OPCODE_pop = 0x29;
    public static final short OPCODE_dup = 0x2A;
    public static final short OPCODE_swap = 0x2B;
    public static final short OPCODE_pushstring = 0x2C;
    public static final short OPCODE_pushint = 0x2D;
    public static final short OPCODE_pushuint = 0x2E;
    public static final short OPCODE_pushdouble = 0x2F;
    public static final short OPCODE_pushscope = 0x30;
    public static final short OPCODE_pushnamespace = 0x31;
    public static final short OPCODE_hasnext2 = 0x32;
    public static final short OPCODE_newfunction = 0x40;
    public static final short OPCODE_call = 0x41;
    public static final short OPCODE_construct = 0x42;
    public static final short OPCODE_callmethod = 0x43;
    public static final short OPCODE_callstatic = 0x44;
    public static final short OPCODE_callsuper = 0x45;
    public static final short OPCODE_callproperty = 0x46;
    public static final short OPCODE_returnvoid = 0x47;
    public static final short OPCODE_returnvalue = 0x48;
    public static final short OPCODE_constructsuper = 0x49;
    public static final short OPCODE_constructprop = 0x4A;
    public static final short OPCODE_callsuperid = 0x4B;
    public static final short OPCODE_callproplex = 0x4C;
    public static final short OPCODE_callinterface = 0x4D;
    public static final short OPCODE_callsupervoid = 0x4E;
    public static final short OPCODE_callpropvoid = 0x4F;
    public static final short OPCODE_newobject = 0x55;
    public static final short OPCODE_newarray = 0x56;
    public static final short OPCODE_newactivation = 0x57;
    public static final short OPCODE_newclass = 0x58;
    public static final short OPCODE_getdescendants = 0x59;
    public static final short OPCODE_newcatch = 0x5A;
    public static final short OPCODE_findpropstrict = 0x5D;
    public static final short OPCODE_findproperty = 0x5E;
    public static final short OPCODE_finddef = 0x5F;
    public static final short OPCODE_getlex = 0x60;
    public static final short OPCODE_setproperty = 0x61;
    public static final short OPCODE_getlocal = 0x62;
    public static final short OPCODE_setlocal = 0x63;
    public static final short OPCODE_getglobalscope = 0x64;
    public static final short OPCODE_getscopeobject = 0x65;
    public static final short OPCODE_getproperty = 0x66;
    public static final short OPCODE_getpropertylate = 0x67;
    public static final short OPCODE_initproperty = 0x68;
    public static final short OPCODE_setpropertylate = 0x69;
    public static final short OPCODE_deleteproperty = 0x6A;
    public static final short OPCODE_deletepropertylate = 0x6B;
    public static final short OPCODE_getslot = 0x6C;
    public static final short OPCODE_setslot = 0x6D;
    public static final short OPCODE_getglobalslot = 0x6E;
    public static final short OPCODE_setglobalslot = 0x6F;
    public static final short OPCODE_convert_s = 0x70;
    public static final short OPCODE_esc_xelem = 0x71;
    public static final short OPCODE_esc_xattr = 0x72;
    public static final short OPCODE_convert_i = 0x73;
    public static final short OPCODE_convert_u = 0x74;
    public static final short OPCODE_convert_d = 0x75;
    public static final short OPCODE_convert_b = 0x76;
    public static final short OPCODE_convert_o = 0x77;
    public static final short OPCODE_checkfilter = 0x78;
    public static final short OPCODE_coerce = 0x80;
    public static final short OPCODE_coerce_b = 0x81;
    public static final short OPCODE_coerce_a = 0x82;
    public static final short OPCODE_coerce_i = 0x83;
    public static final short OPCODE_coerce_d = 0x84;
    public static final short OPCODE_coerce_s = 0x85;
    public static final short OPCODE_astype = 0x86;
    public static final short OPCODE_astypelate = 0x87;
    public static final short OPCODE_coerce_u = 0x88;
    public static final short OPCODE_coerce_o = 0x89;
    public static final short OPCODE_negate = 0x90;
    public static final short OPCODE_increment = 0x91;
    public static final short OPCODE_inclocal = 0x92;
    public static final short OPCODE_decrement = 0x93;
    public static final short OPCODE_declocal = 0x94;
    public static final short OPCODE_typeof = 0x95;
    public static final short OPCODE_not = 0x96;
    public static final short OPCODE_bitnot = 0x97;
    public static final short OPCODE_concat = 0x9A;
    public static final short OPCODE_add_d = 0x9B;
    public static final short OPCODE_add = 0xA0;
    public static final short OPCODE_subtract = 0xA1;
    public static final short OPCODE_multiply = 0xA2;
    public static final short OPCODE_divide = 0xA3;
    public static final short OPCODE_modulo = 0xA4;
    public static final short OPCODE_lshift = 0xA5;
    public static final short OPCODE_rshift = 0xA6;
    public static final short OPCODE_urshift = 0xA7;
    public static final short OPCODE_bitand = 0xA8;
    public static final short OPCODE_bitor = 0xA9;
    public static final short OPCODE_bitxor = 0xAA;
    public static final short OPCODE_equals = 0xAB;
    public static final short OPCODE_strictequals = 0xAC;
    public static final short OPCODE_lessthan = 0xAD;
    public static final short OPCODE_lessequals = 0xAE;
    public static final short OPCODE_greaterthan = 0xAF;
    public static final short OPCODE_greaterequals = 0xB0;
    public static final short OPCODE_instanceof = 0xB1;
    public static final short OPCODE_istype = 0xB2;
    public static final short OPCODE_istypelate = 0xB3;
    public static final short OPCODE_in = 0xB4;
    public static final short OPCODE_increment_i = 0xC0;
    public static final short OPCODE_decrement_i = 0xC1;
    public static final short OPCODE_inclocal_i = 0xC2;
    public static final short OPCODE_declocal_i = 0xC3;
    public static final short OPCODE_negate_i = 0xC4;
    public static final short OPCODE_add_i = 0xC5;
    public static final short OPCODE_subtract_i = 0xC6;
    public static final short OPCODE_multiply_i = 0xC7;
    public static final short OPCODE_getlocal0 = 0xD0;
    public static final short OPCODE_getlocal1 = 0xD1;
    public static final short OPCODE_getlocal2 = 0xD2;
    public static final short OPCODE_getlocal3 = 0xD3;
    public static final short OPCODE_setlocal0 = 0xD4;
    public static final short OPCODE_setlocal1 = 0xD5;
    public static final short OPCODE_setlocal2 = 0xD6;
    public static final short OPCODE_setlocal3 = 0xD7;
    public static final short OPCODE_debug = 0xEF;
    public static final short OPCODE_debugline = 0xF0;
    public static final short OPCODE_debugfile = 0xF1;
    public static final short OPCODE_bkptline = 0xF2;
    public static final short OPCODE_timestamp = 0xF3; // ignored by AVM
    
    // no real opcodes, but values used for dynamic profiling
    public static final short OPCODE_verifypass = 0xF5; // for VM profiling (abc verify overhead)
    public static final short OPCODE_alloc = 0xF6; // for GC profiling
    public static final short OPCODE_mark = 0xF7; // for GC profiling
    public static final short OPCODE_wb = 0xF8; // for GC profiling
    public static final short OPCODE_prologue = 0xF9; // for codegen profiling
    public static final short OPCODE_sendenter = 0xFA; // ignored by AVM (profiling?)
    public static final short OPCODE_doubletoatom = 0xFB; // for VM profiling (doubleToAtom method in AvmCore)
    public static final short OPCODE_sweep = 0xFC; // for GC profiling
    public static final short OPCODE_codegenop = 0xFD; // for codegen profiling
    public static final short OPCODE_verifyop = 0xFE; // for VM profiling (abc verify overhead)
    public static final short OPCODE_decode = 0xFF; // for VM profiling (abc parsing overhead)
  }
}
