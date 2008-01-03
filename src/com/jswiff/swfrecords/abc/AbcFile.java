package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcFile implements Serializable {
  private int majorVersion;
  private int minorVersion;
  private AbcConstantPool constantPool;
  private AbcMethodSignature[] methods;
  private AbcMetadata[] metadataEntries;
  private AbcInstance[] instances;
  private AbcClass[] classes;
  private AbcScript[] scripts;
  private AbcMethodBody[] methodBodies;
  
  public static AbcFile read(InputBitStream stream) throws IOException {
    AbcFile abcFile = new AbcFile();
    abcFile.minorVersion = stream.readUI16();
    abcFile.majorVersion = stream.readUI16();
    if (abcFile.majorVersion != 46 || abcFile.minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    abcFile.constantPool = AbcConstantPool.read(stream);
    int methodCount = stream.readU30();
    abcFile.methods = new AbcMethodSignature[methodCount];
    for (int i = 0; i < methodCount; i++) {
      abcFile.methods[i] = AbcMethodSignature.read(stream);
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
    }
    int scriptCount = stream.readU30();
    abcFile.scripts = new AbcScript[scriptCount];
    for (int i = 0; i < scriptCount; i++) {
      abcFile.scripts[i] = AbcScript.read(stream);
    }
    int methodBodyCount = stream.readU30();
    abcFile.methodBodies = new AbcMethodBody[methodBodyCount];
    for (int i = 0; i < methodBodyCount; i++) {
      abcFile.methodBodies[i] = AbcMethodBody.read(stream);
    }
    return abcFile;
  }
}
