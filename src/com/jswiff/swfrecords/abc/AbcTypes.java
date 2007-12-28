package com.jswiff.swfrecords.abc;

public class AbcTypes {
  public interface Basic {
    public static final short UNDEFINED = 0x00;
    public static final short STRING = 0x01;
    public static final short INT = 0x03;
    public static final short U_INT = 0x04;
    public static final short DOUBLE = 0x06;
    public static final short FALSE = 0x0A;
    public static final short TRUE = 0x0B;
    public static final short NULL = 0x0C;
  }
  
  public interface Namespace {
    public static final short PRIVATE_NAMESPACE = 0x05;
    public static final short NAMESPACE = 0x08;
    public static final short PACKAGE_NAMESPACE = 0x16;
    public static final short PACKAGE_INTERNAL_NAMESPACE = 0x17;
    public static final short PROTECTED_NAMESPACE = 0x18;
    public static final short EXPLICIT_NAMESPACE = 0x19;
    public static final short STATIC_PROTECTED_NAMESPACE = 0x1A;
  }
}
