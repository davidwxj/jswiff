package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcFile implements Serializable {
  private int majorVersion;
  private int minorVersion;
  private AbcConstantPool constantPool;
  private AbcMethodSignature[] methodSignatures;
  private AbcMetadata[] metadataEntries;
  private AbcInstance[] instances;
  private AbcClass[] classes;
  
  public static AbcFile read(InputBitStream stream) throws IOException {
    AbcFile abcFile = new AbcFile();
    abcFile.minorVersion = stream.readUI16();
    abcFile.majorVersion = stream.readUI16();
    if (abcFile.majorVersion != 46 || abcFile.minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    abcFile.constantPool = AbcConstantPool.read(stream);
    int methodCount = stream.readU30();
    abcFile.methodSignatures = new AbcMethodSignature[methodCount];
    for (int i = 0; i < methodCount; i++) {
      abcFile.methodSignatures[i] = AbcMethodSignature.read(stream);
    }
    int metadataCount = stream.readU30();
    abcFile.metadataEntries = new AbcMetadata[metadataCount];
    for (int i = 0; i < metadataCount; i++) {
      abcFile.metadataEntries[i] = AbcMetadata.read(stream);
    }
    int classCount = stream.readU30();
    abcFile.instances = new AbcInstance[classCount];
    for (int i = 0; i < classCount; i++) {
      abcFile.instances[i] = AbcInstance.read(stream);
    }
    abcFile.classes = new AbcClass[classCount];
    for (int i = 0; i < classCount; i++) {
      abcFile.classes[i] = AbcClass.read(stream);
      System.out.println("CLASS");
    }
    int scriptCount = stream.readU30();
    
    int methodBodyCount = stream.readU30();
    
    return abcFile;
  }
}
