package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcFile implements Serializable {
  private int majorVersion;
  private int minorVersion;
  private AbcConstantPool constantPool;
  
  public static AbcFile read(InputBitStream stream) throws IOException {
    AbcFile abcFile = new AbcFile();
    abcFile.minorVersion = stream.readUI16();
    abcFile.majorVersion = stream.readUI16();
    if (abcFile.majorVersion != 46 || abcFile.minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    abcFile.constantPool = AbcConstantPool.read(stream);
    return abcFile;
  }
}
