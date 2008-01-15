package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcMultiname implements Serializable {
  public static final short Q_NAME = 0x07;
  public static final short Q_NAME_A = 0x0D;
  public static final short RTQ_NAME = 0x0F;
  public static final short RTQ_NAME_A = 0x10;
  public static final short RTQ_NAME_L = 0x11;
  public static final short RTQ_NAME_L_A = 0x12;
  public static final short MULTINAME = 0x09;
  public static final short MULTINAME_A = 0x0E;
  public static final short MULTINAME_L = 0x1B;
  public static final short MULTINAME_L_A = 0x1C;

  private short kind;
  private int nameIndex;
  private int namespaceIndex;
  private int namespaceSetIndex;

  public static AbcMultiname read(InputBitStream stream) throws IOException {
    AbcMultiname mn = new AbcMultiname();
    mn.kind = stream.readUI8();
    switch (mn.kind) {
      case Q_NAME:
      case Q_NAME_A:
        mn.namespaceIndex = stream.readAbcInt();
        mn.nameIndex = stream.readAbcInt();
        break;
      case RTQ_NAME:
      case RTQ_NAME_A:
        mn.nameIndex = stream.readAbcInt();
        break;
      case RTQ_NAME_L:
      case RTQ_NAME_L_A:
        // no data associated
        break;
      case MULTINAME:
      case MULTINAME_A:
        mn.nameIndex = stream.readAbcInt();
        mn.namespaceSetIndex = stream.readAbcInt();
        break;
      case MULTINAME_L:
      case MULTINAME_L_A:
        mn.namespaceSetIndex = stream.readAbcInt();
        break;
    }
    return mn;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(kind);
    switch (kind) {
    case Q_NAME:
    case Q_NAME_A:
      stream.writeAbcInt(namespaceIndex);
      stream.writeAbcInt(nameIndex);
      break;
    case RTQ_NAME:
    case RTQ_NAME_A:
      stream.writeAbcInt(nameIndex);
      break;
    case RTQ_NAME_L:
    case RTQ_NAME_L_A:
      // no data associated
      break;
    case MULTINAME:
    case MULTINAME_A:
      stream.writeAbcInt(nameIndex);
      stream.writeAbcInt(namespaceSetIndex);
      break;
    case MULTINAME_L:
    case MULTINAME_L_A:
      stream.writeAbcInt(namespaceSetIndex);
      break;
  }
  }
}
