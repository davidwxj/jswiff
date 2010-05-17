/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
 */

package com.jswiff.swfrecords.abc;

import com.jswiff.constants.AbcConstants.MultiNameKind;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class AbcMultiname implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private MultiNameKind kind;
  private int nameIndex;
  private int namespaceIndex;
  private int[] typeNameIndexes;

  private AbcMultiname() { } // empty
  
  public static AbcMultiname createQName(int nsIndex, int nameIndex, boolean attribute) {
    AbcMultiname mn = new AbcMultiname();
    mn.kind = attribute ? MultiNameKind.Q_NAME_A : MultiNameKind.Q_NAME;
    mn.namespaceIndex = nsIndex;
    mn.nameIndex = nameIndex;
    return mn;
  }
  
  public static AbcMultiname createRTQName(int nameIndex, boolean attribute) {
    AbcMultiname mn = new AbcMultiname();
    mn.kind = attribute ? MultiNameKind.RTQ_NAME_A : MultiNameKind.RTQ_NAME;
    mn.nameIndex = nameIndex;
    return mn;
  }
  
  public static AbcMultiname createRTQNameL(boolean attribute) {
    AbcMultiname mn = new AbcMultiname();
    mn.kind = attribute ? MultiNameKind.RTQ_NAME_L_A : MultiNameKind.RTQ_NAME_L;
    return mn;
  }
  
  public static AbcMultiname createMultiname(int nameIndex, int nsSetIndex, boolean attribute) {
    AbcMultiname mn = new AbcMultiname();
    mn.kind = attribute ? MultiNameKind.MULTINAME_A : MultiNameKind.MULTINAME;
    mn.nameIndex = nameIndex;
    mn.namespaceIndex = nsSetIndex;
    return mn;
  }
  
  public static AbcMultiname createMultinameL(int nsSetIndex, boolean attribute) {
    AbcMultiname mn = new AbcMultiname();
    mn.kind = attribute ? MultiNameKind.MULTINAME_L_A : MultiNameKind.MULTINAME_L;
    mn.namespaceIndex = nsSetIndex;
    return mn;
  }
  
  public static AbcMultiname read(InputBitStream stream) throws IOException {
    AbcMultiname mn = new AbcMultiname();
    short kind = stream.readUI8();
    MultiNameKind mnKind = MultiNameKind.lookup(kind);
    
    switch (mnKind) {
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
        mn.namespaceIndex = stream.readAbcInt();
        break;
      case MULTINAME_L:
      case MULTINAME_L_A:
        mn.namespaceIndex = stream.readAbcInt();
        break;
      case TYPENAME:
        // This is a quick and dirty hack, there is no documentation for this
        // in the public AVM2 spec, and it should really be a different class.
        mn.nameIndex = stream.readAbcInt();
        int count = stream.readAbcInt();
        int[] types = new int[count];
        for (int i = 0; i < count; i++) {
          types[i] = stream.readAbcInt();
        }
        mn.typeNameIndexes = types;
        break;
    }
    mn.kind = mnKind;
    return mn;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI8(getKind().getCode());
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
      stream.writeAbcInt(namespaceIndex);
      break;
    case MULTINAME_L:
    case MULTINAME_L_A:
      stream.writeAbcInt(namespaceIndex);
      break;
    }
  }

  public MultiNameKind getKind() {
    return kind;
  }

  /**
   * Gets the index of the name value in the strings constant pool.
   * @return an index into the strings constant pool
   */
  public int getNameIndex() {
    return nameIndex;
  }

  /**
   * Gets an index into the nameSpace constant pool for QNames,
   * or for multiNames the index into the NameSpaceSet constant pool.
   * @return the constant pool index for either the NameSpace or NameSpaceSet
   */
  public int getNamespaceIndex() {
    return namespaceIndex;
  }
  
  /**
   * Gets the type-name indexes for a TypeName
   * @return the type-name indexes if this is a TypeName, null otherwise.
   */
  public int[] getTypeNameIndexes() {
    if (this.typeNameIndexes != null)
      return Arrays.copyOf(this.typeNameIndexes, this.typeNameIndexes.length);
    return null;
  }
  
  @Override
  public String toString() {
    String str = this.kind.toString();
    switch (this.kind) {
    case Q_NAME:
    case Q_NAME_A:
    case MULTINAME:
    case MULTINAME_A:
      str = str + ": nameIndex = " + this.nameIndex
        + ", nameSpaceIndex = " + this.namespaceIndex;
      break;
    case RTQ_NAME:
    case RTQ_NAME_A:
      str = str + ": nameIndex = " + this.nameIndex;
      break;
    case MULTINAME_L:
    case MULTINAME_L_A:
      str = str + ": nameSpaceIndex = " + namespaceIndex;
      break;
    case TYPENAME:
      String typesString = "";
      int i = 0;
      for (int ti : this.typeNameIndexes) {
        typesString += ti;
        if (i < (this.typeNameIndexes.length - 1))
          typesString += ", ";
        i++;
      }
      str = str + ": nameIndex = " + this.nameIndex
        + ", types = " + typesString;
    }
    return str;
  }

}
